/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.clientserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author LocalUser
 */
public class BankServerThreaded {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2004);
        ExecutorService pool = Executors.newFixedThreadPool(100);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            pool.execute(new ServerThread(clientSocket));
        }
    }

}
