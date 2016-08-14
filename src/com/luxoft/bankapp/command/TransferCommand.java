package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.NotEnoughFundsException;
import org.springframework.beans.factory.annotation.Autowired;

public class TransferCommand implements Command {

    @Autowired
    public BankCommanderSpring bc;

    @Override
    public void execute() {
        if (bc.currentClient == null) {
            System.out.println("Client is not set.");
            return;
        }

        if (bc.currentClient.getActiveAccount() == null) {
            System.out.println("Current client doesn't have the active account. Cannot proceed with operation.");
            return;
        }
        System.out.println("Select client for transfer.");
        Client clientToTransfer = CommandHelper.getClient();
        if (clientToTransfer == null) {
            return;
        }

        if (clientToTransfer.getActiveAccount() == null) {
            System.out.println("Destination client doesn't have the active account. Cannot proceed with operation.");
            return;
        }

        float sumToTransfer = CommandHelper.getOperationSum();
        if (sumToTransfer == 0) {
            return;
        }

        try {
            bc.currentClient.getActiveAccount().withdraw(sumToTransfer);
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            clientToTransfer.getActiveAccount().deposit(sumToTransfer);
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");

    }

}
