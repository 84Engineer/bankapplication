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
public class LoadClientCommand implements Command {

    @Autowired
    public BankCommanderSpring bc;

    @Override
    public void execute() {
        String clientName;
        System.out.println("Input client's name: ");
        while (true) {
            clientName = bc.sc.nextLine();
            if (clientName == null) {
                continue;
            }
            break;
        }
        BankService bs = new BankServiceImpl();
        try {
            bs.loadClient(clientName, bc.currentBank);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Load saved client");
    }

}
