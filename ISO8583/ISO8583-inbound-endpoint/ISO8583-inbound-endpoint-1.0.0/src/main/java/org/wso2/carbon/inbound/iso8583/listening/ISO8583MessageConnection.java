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
import org.apache.synapse.inbound.InboundProcessorParams;
import org.jpos.iso.ISOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ISO8583MessageConnection extends Thread {
    private static final Log log = LogFactory.getLog(ISO8583MessageConnection.class);
    private int port;
    private ServerSocket server;
    private ExecutorService  threadPool;
    private InboundProcessorParams params;
    private boolean listening = false;
    private String fixedNoOfThreads;

    public ISO8583MessageConnection(int port, InboundProcessorParams params) {
        Properties properties = params.getProperties();
        this.port = port;
        this.params = params;
        this.fixedNoOfThreads = properties.getProperty(ISO8583Constant.fixedNoOfThreads);
        try {
            if (!StringUtils.isEmpty(fixedNoOfThreads)) {
                this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(fixedNoOfThreads));
            } else {
                this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(ISO8583Constant.noOfThreads));
            }
        } catch (NumberFormatException e) {
            log.error("The String does not contain a parsable integer" + e.getMessage(), e);
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
            log.error("Server could not listen on the port", e);
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                log.error("Error while closing the serverSocket", e);
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
            log.error("Couldn't close the server",e);
        }
    }

    private void handleClientRequest(Socket connection,InboundProcessorParams params) {
        try {
            threadPool.execute(new ConnectionRequestHandler(connection, params));
        } catch (ISOException e) {
            log.error("Exception occurred while get ISOPackager",e);
        }
    }
}
