package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.NotEnoughFundsException;
import org.springframework.beans.factory.annotation.Autowired;

public class WithdrawCommand implements Command {

    @Autowired
    public BankCommanderSpring bc;

    @Override
    public void execute() {

        if (bc.currentClient == null) {
            System.out.println("Client is not set.");
            return;
        }

        System.out.println("To quit type \"quit\".");

        Account account = CommandHelper.getAccount();

        if (account == null) {
            return;
        }

        float withdrawSum = CommandHelper.getOperationSum();

        if (withdrawSum == 0) {
            return;
        }

        try {
            account.withdraw(withdrawSum);
        } catch (NotEnoughFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw");
    }

}
