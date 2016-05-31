package org.wso2.carbon.client;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class TestClient {
	private final int MY_PORT=5000;
	private final String TargetHost = "localhost";
	private final String QUIT = "QUIT";

	public TestClient() {
	try {
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket = new Socket(TargetHost, MY_PORT);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		boolean isQuit = false;
		while (!isQuit) {
		System.out.print("ISO8583 data : ");
		String cmd = inFromUser.readLine(); // Read the command line input
		cmd = cmd.toUpperCase();
		if (cmd.equals(QUIT)) {
			isQuit = true;
		}
		outToServer.writeBytes(cmd + "\n");
	}
		outToServer.close();
		clientSocket.close();
	} catch (IOException ioe) {
		System.out.println("Error while sending the message:" + ioe);
		} catch (Exception e) {
		System.out.println("Error:" + e);
		}
	}

	public static void main(String[] args) {
	new TestClient();
	}
}