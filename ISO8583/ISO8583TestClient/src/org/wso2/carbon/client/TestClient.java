package src.org.wso2.carbon.client;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

public class TestClient {
    private Logger log = Logger.getLogger(getClass());
    private final String QUIT = "QUIT";

    public TestClient() {
        try {
            String host = clientConstant.TargetHost;
            int port = Integer.parseInt(clientConstant.PORT);
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket(host, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            boolean isQuit = false;
            while (!isQuit) {
                log.info("ISO8583 data : ");
                String cmd = inFromUser.readLine(); // Read the command line input
                cmd = cmd.toUpperCase();
                if (cmd.equals(QUIT)) {
                    isQuit = true;
                }
                outToServer.writeUTF(cmd + "\n");
            }
            outToServer.close();
            clientSocket.close();
        } catch (IOException ioe) {
            log.info("Error while sending the message:" + ioe);
        } catch (NumberFormatException e) {
            log.error("The String does not contain a parsable integer" + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        new TestClient();
    }
}
