package com.luxoft.bankapp.command;

public class GetAccountCommand implements Command {

	@Override
	public void execute() {
		if (BankCommander.currentClient == null) {
			System.out.println("Client is not set.");
			return;
		}
		BankCommander.currentClient.printReport();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Print list of client's accounts");
	}

}
