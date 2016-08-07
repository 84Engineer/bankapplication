package com.luxoft.bankapp.service;

import com.luxoft.bankapp.command.BankCommander;
import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.command.CommandHelper;

public class FindClientCommand implements Command {

	@Override
	public void execute() {

		BankCommander.currentClient = CommandHelper.getClient();
		if (BankCommander.currentClient != null)
			System.out.println("Client set as current client.");
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find client");
	}
}
