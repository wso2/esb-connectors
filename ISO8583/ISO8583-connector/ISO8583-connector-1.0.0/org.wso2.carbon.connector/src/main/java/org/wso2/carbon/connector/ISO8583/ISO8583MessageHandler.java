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

package org.wso2.carbon.connector.ISO8583;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Socket;

public class ISO8583MessageHandler implements Runnable {
    private static final Log log = LogFactory.getLog(ISO8583MessageHandler.class);
    private String msg;
    private String host;
    private int port;

    private final ThreadLocal<Socket> threadLocal =
            new ThreadLocal<Socket>() {
                @Override
                protected Socket initialValue() {
                    Socket socket = null;
                    try {
                        socket = new Socket(host, port);
                    } catch (IOException e) {
                        log.error("Couldn't create socket", e);
                    }
                    return socket;
                }
            };

    public ISO8583MessageHandler(String host, int port, String details) {
        this.msg = details;
        this.host = host;
        this.port = port;
    }

    public Socket getSocket() {
        return threadLocal.get();
    }

    public void run() {
        Socket connection = getSocket();
        DataOutputStream outStream = null;
        BufferedReader inFromServer = null;
        try {
            outStream = new DataOutputStream(connection.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            log.info(inFromServer.readLine());
            while (true) {
                if (connection.isConnected()) {
                    outStream.writeUTF(Thread.currentThread().getName() + "\n" + msg);
                    outStream.flush();

                /* Sender will receive the Acknowledgement here */
                    String message = inFromServer.readLine();
                    while (message != null) {
                        log.info("From Server: " + message);
                        message = inFromServer.readLine();
                    }
                }
                connection.close();
            }
        } catch (IOException e) {
            log.error("An exception occurred in sending the ISO8583 message", e);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (inFromServer != null) {
                    inFromServer.close();
                }
            } catch (IOException e) {
                log.error("Couldn't close the I/O Streams", e);
            }
        }
    }
}
