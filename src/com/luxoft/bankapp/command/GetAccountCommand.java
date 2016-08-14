package com.luxoft.bankapp.command;

import org.springframework.beans.factory.annotation.Autowired;

public class GetAccountCommand implements Command {

    @Autowired
    public BankCommanderSpring bc;

    @Override
    public void execute() {
        if (bc.currentClient == null) {
            System.out.println("Client is not set.");
            return;
        }
        bc.currentClient.printReport();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Print list of client's accounts");
    }

}
