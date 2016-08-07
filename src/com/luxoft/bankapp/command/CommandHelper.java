package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class CommandHelper {

	public static Account getAccount() {
		long accountNo;
		while (true) {
			System.out.println("Enter account number:");
			String input = BankCommander.sc.nextLine();
			if (input.equalsIgnoreCase("quit"))
				return null;
			try {
				accountNo = Long.parseLong(input);
			} catch (NumberFormatException e) {
				System.out.println("Please enter valid account number.");
				continue;
			}

			for (Account accountToFind : BankCommander.currentClient.getAccounts()) {
				if (accountToFind.getAccountNumber() == accountNo) {
					return accountToFind;
				}
			}
			System.out.println("Account No " + accountNo + " wasn't found.");
			return null;
		}
	}

	public static float getOperationSum() {
		while (true) {
			System.out.println("Enter operation sum:");
			String input = BankCommander.sc.nextLine();
			if (input.equalsIgnoreCase("quit"))
				return 0;
			try {
				float depositSum = Float.parseFloat(input);
				return depositSum;
			} catch (NumberFormatException e) {
				System.out.println("Please enter valid sum.");
				continue;
			}
		}
	}
	
	public static Client getClient() {
		if (BankCommander.currentBank == null) {
			System.out.println("Bank is not set.");
			return null;
		}
		String clientToSearchName;
		System.out.println("To quit type \"quit\".");

		while (true) {
			System.out.println("Enter client's name: ");
			clientToSearchName = BankCommander.sc.nextLine();
			if (clientToSearchName == null)
				continue;
			else if (clientToSearchName.equalsIgnoreCase("quit"))
				return null;
			else
				break;
		}

		BankService bankService = new BankServiceImpl();
		Client client = bankService.getClient(BankCommander.currentBank, clientToSearchName);
		if(client != null) {
			System.out.println("Client found: " + client.getClientSalutation());
			return client;
		}
		/*for (Client bankClient : BankCommander.currentBank.getClients()) {
			if (bankClient.getName().equalsIgnoreCase(clientToSearchName)) {
				System.out.println("Client found: " + bankClient.getClientSalutation());
				return bankClient;
			}
		}*/

		System.out.println("Client with name \"" + clientToSearchName + "\" wasn't found in bank \""
				+ BankCommander.currentBank.getBankName() + "\".");
		return null;
	}
}
