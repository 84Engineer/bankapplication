/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import java.util.Set;

/**
 *
 * @author LocalUser
 */
public class SaveAllClientsCommand implements Command {

    @Override
    public void execute() {
        /*if (BankCommander.currentClient == null) {
            System.out.println("Client is not set.");
            return;
        }*/

        Set<Client> clients = BankCommander.currentBank.getClients();

        BankService bs = new BankServiceImpl();
        for (Client client : clients) {
            try {
                bs.saveClient(client);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save all clients");
    }

}
