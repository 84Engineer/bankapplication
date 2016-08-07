/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.multiclientserver;

import com.luxoft.bankapp.clientserver.RequestData;
import com.luxoft.bankapp.clientserver.RequestProcessor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author LocalUser
 */
public class ServerThread implements Runnable {

    Socket connection;
    ObjectOutputStream out;
    ObjectInputStream in;
    AtomicInteger counter;

    public ServerThread(Socket connection, AtomicInteger counter) {
        this.connection = connection;
        this.counter = counter;
    }

    public void run() {
        try {
            counter.getAndIncrement();
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
            } catch (ClassNotFoundException classnot) {
                System.err.println("Data received in unknown format");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            counter.getAndDecrement();
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
