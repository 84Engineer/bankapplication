/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.multiclientserver;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LocalUser
 */
public class BankServerMonitor implements Runnable {

    private AtomicInteger counter;

    public BankServerMonitor(AtomicInteger counter) {
        this.counter = counter;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println("Number of connected clients: " + counter);
        }
    }

}
