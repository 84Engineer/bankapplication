/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.command;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

/**
 *
 * @author LocalUser
 */
public class SaveClientCommand implements Command {

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            System.out.println("Client is not set.");
            return;
        }

        BankService bs = new BankServiceImpl();
        try {
            bs.saveClient(BankCommander.currentClient);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save client");
    }

}
