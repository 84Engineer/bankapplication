/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.command;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author LocalUser
 */
public class SaveClientCommand implements Command {

    @Autowired
    public BankCommanderSpring bc;

    @Override
    public void execute() {
        if (bc.currentClient == null) {
            System.out.println("Client is not set.");
            return;
        }

        BankService bs = new BankServiceImpl();
        try {
            bs.saveClient(bc.currentClient);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save client");
    }

}
