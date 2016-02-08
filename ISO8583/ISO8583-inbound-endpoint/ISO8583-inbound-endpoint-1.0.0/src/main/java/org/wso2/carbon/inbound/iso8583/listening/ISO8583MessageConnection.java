/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.inbound.iso8583.listening;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseException;
import org.apache.synapse.inbound.InboundProcessorParams;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.*;

public class ISO8583MessageConnection extends Thread {
    private static final Log log = LogFactory.getLog(ISO8583MessageConnection.class);
    private int port;
    private ServerSocket server;
    private ExecutorService  threadPool;
    private InboundProcessorParams params;
    private boolean listening = false;

    public ISO8583MessageConnection(Properties properties, int port, InboundProcessorParams params) {
        this.port = port;
        this.params = params;
        String coreThreads = properties.getProperty(ISO8583Constant.INBOUND_CORE_THREADS);
        String maxThreads = properties.getProperty(ISO8583Constant.INBOUND_MAX_THREADS);
        String keepAlive = properties.getProperty(ISO8583Constant.INBOUND_KEEP_ALIVE);
        String queueLength = properties.getProperty(ISO8583Constant.INBOUND_THREAD_QLEN);
        try {
            if ((!StringUtils.isEmpty(coreThreads)) && (!StringUtils.isEmpty(maxThreads)) &&
                    (!StringUtils.isEmpty(keepAlive)) && (!StringUtils.isEmpty(queueLength))) {
                BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(Integer.parseInt(queueLength));
                threadPool = new ThreadPoolExecutor(Integer.parseInt(coreThreads), Integer.parseInt(maxThreads)
                        , Integer.parseInt(keepAlive), TimeUnit.SECONDS, workQueue);
            } else {
                BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(
                        Integer.parseInt(ISO8583Constant.THREAD_QLEN));
                threadPool = new ThreadPoolExecutor(Integer.parseInt(ISO8583Constant.CORE_THREADS),
                        Integer.parseInt(ISO8583Constant.MAX_THREADS), Integer.parseInt(ISO8583Constant.KEEP_ALIVE),
                        TimeUnit.SECONDS, workQueue);
            }
        } catch (NumberFormatException e) {
            handleException("One of the property or properties of thread specified is of an invalid type", e);
        }
    }

    public void run() {
        try {
            server = new ServerSocket(port);
            log.info("Server is listening on port :" + port);
            while (!listening) {
                    Socket socketConnection = server.accept();
                    handleClientRequest(socketConnection,params);
                }
        } catch (IOException e) {
            handleException("Server could not listen on the port", e);
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                log.error ("Error while closing the serverSocket", e);
            }
        }
    }

    public void destroyConnection() {
        log.info("destroy the connection");
        try {
            if (!server.isClosed()) {
                server.close();
                log.info("Server stop the listening on port:" + port);
                listening = false;
            }
        } catch (IOException e) {
            handleException("Couldn't close the server", e);
        }
    }

    private void handleClientRequest(Socket connection, InboundProcessorParams params) {
        try {
            threadPool.execute(new ConnectionRequestHandler(connection, params));
        } catch (RejectedExecutionException re) {
            // If the pool is full complete the execution with the same thread
            log.warn("Worker pool has reached the maximum capacity.");
            handleException("Worker pool has reached the maximum capacity.",re);
        }
    }

    private void handleException(String msg,Exception e) {
        log.error(msg,e);
        throw new SynapseException(msg);
    }
}
