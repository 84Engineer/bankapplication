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
public class ServerThread implements Runnable {

    private Socket connection;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ServerThread(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            try {
                RequestData rd = (RequestData) in.readObject();
                RequestProcessor rp = new RequestProcessor(rd);
                String message = rp.process();
                sendMessage(message);
            } catch (ClassNotFoundException classnot) {
                System.err.println("Data received in unknown format");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                connection.close();
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

}
