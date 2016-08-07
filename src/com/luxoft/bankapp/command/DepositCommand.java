package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.NotEnoughFundsException;

public class DepositCommand implements Command {

	@Override
	public void execute() {
		
		if(BankCommander.currentClient == null) {
			System.out.println("Client is not set.");
			return;
		}

		System.out.println("To quit type \"quit\".");

		Account account = CommandHelper.getAccount();
		
		if(account == null)
			return;
		
		float depositSum = CommandHelper.getOperationSum();
		
		if(depositSum == 0)
			return;
		
		try {
			account.deposit(depositSum);
		} catch (NotEnoughFundsException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit");
	}

}
