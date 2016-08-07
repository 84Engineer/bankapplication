/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luxoft.bankapp.clientserver;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.NotEnoughFundsException;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author LocalUser
 */
public class RequestProcessor {

    private RequestData rd;

    public RequestProcessor(RequestData rd) {
        this.rd = rd;
    }

    public String process() {
        Bank bank = new Bank("Temp Bank");
        BankService bs = new BankServiceImpl();
        String clientName = rd.getClientName();

        try {
            bs.loadClient(clientName, bank);
        } catch (Exception ex) {
            return String.format("Error occurred while processing request: %s", ex.getMessage());
        }

        Client client = bank.getClientByName(clientName);
        if (client == null) {
            return String.format("Client with name %s doesn't exist. Please check spelling.", rd.getClientName());
        }

        Account account = client.getActiveAccount();
        if (account == null) {
            return String.format("Client %s doesn't have active account.", clientName);
        }

        if (rd.getOperation() == RequestData.Operation.DEPOSIT) {
            try {
                account.deposit(rd.getOperationSum());
            } catch (NotEnoughFundsException ex) {
                return String.format("Error occurred while performing operation: %s", ex.getMessage());
            }
        } else if (rd.getOperation() == RequestData.Operation.WITHDRAW) {
            try {
                account.withdraw(rd.getOperationSum());
            } catch (NotEnoughFundsException ex) {
                return String.format("Error occurred while performing operation: %s", ex.getMessage());
            }
        } else {
            return String.format("Operation $s is not supported", rd.getOperation());
        }
        
        try {
            bs.saveClient(client);
        } catch (Exception ex) {
            return String.format("Error occurred while performing operation: %s", ex.getMessage());
        }
        return "Operation performed succesfully";
    }
}
