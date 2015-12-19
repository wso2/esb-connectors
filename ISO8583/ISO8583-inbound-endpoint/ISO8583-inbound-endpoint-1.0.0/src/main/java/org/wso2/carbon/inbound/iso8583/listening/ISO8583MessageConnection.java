/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.inbound.InboundProcessorParams;
import org.jpos.iso.ISOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ISO8583MessageConnection extends Thread {
    private static final Log log = LogFactory.getLog(ISO8583MessageConnection.class);
    private int port;
    private ServerSocket server;
    private String onErrorSeq;
    private SynapseEnvironment synapseEnvironment;
    private String injectingSeq;
    private String name;
    private InboundProcessorParams params;
    private Socket socketConnection;
    private volatile boolean listening = false;
    private int fixedNoOfThreads = 10000;
    private int numOfConnections = 0;
    private ExecutorService threadPool;

    public ISO8583MessageConnection(int port, InboundProcessorParams params) {
        this.port = port;
        this.injectingSeq = params.getInjectingSeq();
        this.onErrorSeq = params.getOnErrorSeq();
        this.name = params.getName();
        this.synapseEnvironment = params.getSynapseEnvironment();
        this.params = params;
    }

    public void run() {
        try {
            server = new ServerSocket(port);
            log.info("Server is listening on port :" + port);
            while (!listening) {
                threadPool = Executors.newFixedThreadPool(fixedNoOfThreads);
                if (numOfConnections <= fixedNoOfThreads) {
                    socketConnection = server.accept();
                    handleClientRequest(socketConnection, params);
                } else {
                    log.info("server can consume limited number of threads...threads exceeded");
                    break;
                }
                numOfConnections++;
            }
        } catch (IOException e) {
            log.error("Server could not listen on the port", e);
        } catch (ISOException e) {
            log.error("ISOException", e);
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
        try{
            if (!server.isClosed()) {
                server.close();
                log.info("Server stop the listening on port:" + port);
                listening = false;
            }
        } catch (IOException e) {
            log.error("Couldn't close the server",e);
        }
    }

    private void handleClientRequest(Socket connection, InboundProcessorParams params) throws ISOException {
        try {
            threadPool.execute(
                    new ConnectionRequestHandler(connection, params, port));
        } catch (Exception e) {
            log.error("Could not establish the connection", e);
        }
    }
}