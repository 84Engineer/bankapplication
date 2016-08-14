package com.luxoft.bankapp.command;

import com.luxoft.bankapp.command.BankCommander;
import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.command.CommandHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class FindClientCommand implements Command {

    @Autowired
    public BankCommanderSpring bc;

    @Override
    public void execute() {

        bc.currentClient = CommandHelper.getClient();
        if (bc.currentClient != null) {
            System.out.println("Client set as current client.");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Find client");
    }
}
