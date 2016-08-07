/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.command;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LocalUser
 */
public class FeedCommand implements Command {

    @Override
    public void execute() {
        BankService bs = new BankServiceImpl();
        try {
            bs.loadClients(BankCommander.currentBank);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Load clients");
    }

}
