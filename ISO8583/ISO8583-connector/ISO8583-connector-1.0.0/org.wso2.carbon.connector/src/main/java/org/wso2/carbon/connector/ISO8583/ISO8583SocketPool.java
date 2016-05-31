package org.wso2.carbon.connector.ISO8583;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ISO8583SocketPool implements Runnable {
    private static final Log log = LogFactory.getLog(ISO8583SocketPool.class);
    private String msg;
    private  DataOutputStream outStream;
    static Socket socket;
    private static String host;
    private static int port;
    private static final ThreadLocal<Socket> threadLocal =
            new ThreadLocal<Socket>(){
                @Override
                protected Socket initialValue(){
                    try {
                        socket =  new Socket(host,port);
                    } catch (IOException e) {
                       log.error("Couldn't create socket");
                    }
                    return socket;              }
            };

    public ISO8583SocketPool(String host,int port ,String details){
        this.msg = details;
        this.host = host;
        this.port = port;
    }

    public static Socket getSocket(){
        return threadLocal.get();
    }

    public void run(){
        Socket connection = getSocket();
        try {
            outStream = new DataOutputStream(connection.getOutputStream());
            if (connection.isConnected()) {
                outStream .writeBytes(msg);
                outStream.flush();
            }
            if(!connection.isConnected()){
                log.info("Server is not connected");
            }
        } catch (Exception e) {
            log.error("An exception occurred in sending the ISO8583 message", e);
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
               log.error("Couldn't close the connection",e);
            }
        }
    }
}