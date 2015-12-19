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
package org.wso2.carbon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketServerHandler extends Thread{
private Socket serverSocket ;
private BufferedReader inFromClient;
private String datafromClient;

public SocketServerHandler(Socket socket) throws IOException {
	super("SocketHandler (" + socket.getInetAddress().getHostAddress() + ")");
	this.serverSocket = socket ;
	this.inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
}
@Override
public void run() {
	try {	
		if(serverSocket.isConnected()) 
		{
			datafromClient = inFromClient.readLine();
			System.out.println("Data From Client : "+datafromClient);
		}
	} catch (IOException ioe) {
		System.out.println("Error while receiving the messages" + ioe);
	} finally {
		try {
			if (inFromClient != null) inFromClient.close();
			if (serverSocket != null) serverSocket.close();
		} catch (IOException e) {
			System.out.println("Couldn't close I/O Streams" + e);
		}
	}
}
}
