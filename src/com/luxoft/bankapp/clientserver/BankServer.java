/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.clientserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author LocalUser
 */
public class BankServer {
    
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    //String message;
    //RequestData rd;

    void run() {
        try {
            // 1. creating a server socket
            providerSocket = new ServerSocket(2004, 10);
            // 2. Wait for connection
            //System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            //System.out.println("Connection received from "
            //        + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            //sendMessage("Connection successful");
            // 4. The two parts communicate via the input and output streams
            //do {
            try {
                RequestData rd = (RequestData) in.readObject();
                RequestProcessor rp = new RequestProcessor(rd);
                String message = rp.process();
                sendMessage(message);
                /*System.out.println("client>" + message);
                    if (message.equals("bye")) {
                        sendMessage("bye");
                    }*/
            } catch (ClassNotFoundException classnot) {
                System.err.println("Data received in unknown format");
            }
            //} while (!message.equals("bye"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    
    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    
    public static void main(final String args[]) {
        BankServer server = new BankServer();
        while (true) {
            server.run();
        }
    }
}
