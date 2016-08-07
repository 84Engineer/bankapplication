package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.NotEnoughFundsException;

public class TransferCommand implements Command {

	@Override
	public void execute() {
		if (BankCommander.currentClient == null) {
			System.out.println("Client is not set.");
			return;
		}

		if (BankCommander.currentClient.getActiveAccount() == null) {
			System.out.println("Current client doesn't have the active account. Cannot proceed with operation.");
			return;
		}
		System.out.println("Select client for transfer.");
		Client clientToTransfer = CommandHelper.getClient();
		if (clientToTransfer == null)
			return;

		if (clientToTransfer.getActiveAccount() == null) {
			System.out.println("Destination client doesn't have the active account. Cannot proceed with operation.");
			return;
		}

		float sumToTransfer = CommandHelper.getOperationSum();
		if (sumToTransfer == 0)
			return;

		try {
			BankCommander.currentClient.getActiveAccount().withdraw(sumToTransfer);
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
