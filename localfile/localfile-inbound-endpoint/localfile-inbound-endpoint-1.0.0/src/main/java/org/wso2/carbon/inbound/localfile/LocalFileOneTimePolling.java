/**
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * <p/>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.inbound.localfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;
import org.wso2.carbon.inbound.endpoint.protocol.generic.GenericPollingConsumer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class LocalFileOneTimePolling extends GenericPollingConsumer {
    private static final Log log = LogFactory.getLog(LocalFileOneTimePolling.class);
    private String injectingSeq;
    private String watchedDir;
    private String contentType;
    private String actionAfterProcess;
    private String moveFileUri;
    private boolean isPolled = false;

    public LocalFileOneTimePolling(Properties localFileProperties, String name,
                                   SynapseEnvironment synapseEnvironment, long scanInterval,
                                   String injectingSeq, String onErrorSeq, boolean coordination,
                                   boolean sequential) {
        super(localFileProperties, name, synapseEnvironment, scanInterval, injectingSeq, onErrorSeq,
                coordination, sequential);
        this.injectingSeq = injectingSeq;
        this.onErrorSeq = onErrorSeq;
        this.coordination = coordination;
        this.sequential = sequential;
        setUpParameters(localFileProperties);
        log.info("Initialized the custom LocalFile inbound consumer.");
    }

    /**
     * Load needed parameters from the localFile inbound endpoint.
     *
     * @param properties the local file properties
     */
    private void setUpParameters(Properties properties) {
        watchedDir = properties.getProperty(LocalFileConstants.FILEURI);
        contentType = properties.getProperty(LocalFileConstants.CONTENTTYPE);
        actionAfterProcess = properties.getProperty(LocalFileConstants.ACTIONAFTERPROCESS);
        moveFileUri = properties.getProperty(LocalFileConstants.MOVEFILEURI);
        if (StringUtils.isEmpty(watchedDir)) {
            log.error("watchedDir can not be empty");
        }
    }

    @Override
    public Object poll() {
        log.info("within the poll method");
        if (!isPolled) {
            beforeWatch();
        isPolled=true;
        }
        return null;
    }

    private void beforeWatch() {
        Path dir = FileSystems.getDefault().getPath(watchedDir);
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(dir);
            for (final Path path : stream) {
                processFile(path,contentType);
            }
            stream.close();
            //using executor service
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            executorService.execute(new Runnable() {
                public void run() {
                    log.info("Start watch service");
                    watchDirectory();
                }
            });
            executorService.shutdown();

            //log.info("Start watch service");
            //watchDirectory();
              /*MyRunnable myRunnable = new MyRunnable();
                Thread t = new Thread(myRunnable);
                t.start();*/
        } catch (IOException e) {
            log.error("Error while processing the directory.", e);
        }
    }

    /*
    * Using background thread
    * */
   /* public class MyRunnable implements Runnable {
        public void run() {
            watchDirectory();
        }
    }*/
    /**
     * Injecting the file contents to the sequence
     *
     * @param string the contents of file
     */
    public void injectFileContent(String string, String contentType) {
        if (injectingSeq != null) {
            injectMessage(string, contentType);
            if (log.isDebugEnabled()) {
                log.debug("injecting localFile content message to the sequence : " + injectingSeq);
            }
        } else {
            log.error("the Sequence is not found");
        }
    }

    private void processFile(final Path path, String contentType) {
        log.info("The file path to be processed : " + path);
        try {
            if (StringUtils.isEmpty(contentType)) {
                contentType = Files.probeContentType(path);
            }
            String readAllBytes = new String(Files.readAllBytes(path));
            injectFileContent(readAllBytes, contentType);
            if (actionAfterProcess != null && actionAfterProcess.toUpperCase().equals("MOVE")) {
                if (log.isDebugEnabled()) {
                    log.debug("actionAfterProcess is MOVE");
                }
                if (Files.exists(Paths.get(moveFileUri))) {
                    moveFile(path.toString(), moveFileUri);
                } else {
                    Files.createDirectory(Paths.get(moveFileUri));
                    moveFile(path.toString(), moveFileUri);
                }
            } else {
                if (actionAfterProcess != null && actionAfterProcess.toUpperCase().equals("DELETE")) {
                    if (log.isDebugEnabled()) {
                        log.debug("actionAfterProcess is DELETE");
                    }
                    Files.delete(path);
                }
            }
        } catch (IOException e) {
            log.error("Error while processing file : " + e.getMessage(), e);
        }
    }

    /*
    * to watch the directory using java nio
    * */
    @SuppressWarnings("unchecked")
    private Object watchDirectory() {
        try {
            Path newPath = Paths.get(watchedDir);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            newPath.register(watchService, ENTRY_CREATE, ENTRY_MODIFY);
            while (true) {
                final WatchKey key = watchService.take();
                if (key != null) {
                    for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        final WatchEvent.Kind<?> kind = watchEvent.kind();
                        final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                        final Path entry = watchEventPath.context();
                        if (log.isDebugEnabled()) {
                            log.debug("Processing file is : " + entry);
                        }
                        Path filePath = Paths.get(watchedDir, entry.toString());
                        if (kind == ENTRY_MODIFY) {
                            processFile(filePath, contentType);
                        } else if (kind == OVERFLOW) {
                            continue;
                        }
                    }
                    key.reset();
                    if (!key.isValid()) {
                        break;
                    }
                }
            }
            watchService.close();
        } catch (IOException e) {
            log.error("Error while watching directory: " + e.getMessage(), e);
        } catch (InterruptedException ie) {
            log.error("Error while get the WatchKey : " + ie.getMessage(), ie);
        }
        return null;
    }

    private void moveFile(String source, String destination) {
        Path fromPath = Paths.get(source);
        Path toPath = Paths.get(destination);
        try {
            Files.move(fromPath, toPath.resolve(fromPath.getFileName()), REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (IOException ie) {
            log.error("Error while move file : " + ie.getMessage(), ie);
        }
    }
}