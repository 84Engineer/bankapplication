package com.luxoft.bankapp.command;

import java.util.HashMap;
import java.util.Map;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Client.Gender;
import com.luxoft.bankapp.model.ClientExistsException;
import com.luxoft.bankapp.model.IllegalArgumentException;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class AddClientCommand implements Command {

	@Override
	public void execute() {
		if (BankCommander.currentBank == null) {
			System.out.println("Bank is not set.");
			return;
		}

		String clientName;
		System.out.println("To quit type \"quit\".");
		System.out.println("Input client's name and surname: ");
		while (true) {
			clientName = BankCommander.sc.nextLine();
			checkForQuit(clientName);
			if (clientName == null)
				continue;
			if (!clientName.matches("^ *[A-Z][a-z]{2,} *[A-Z][a-z]{2,} *$")) {
				System.out.println("Enter valid client name");
				continue;
			}
			clientName = clientName.trim();
			System.out.println("Client name is: " + clientName);
			break;
		}

		Gender gender;
		System.out.println("Select client gender: ");
		while (true) {
			Integer i = 0;
			Map<Integer, Gender> selectMap = new HashMap<Integer, Gender>();
			for (Gender genderToSelect : Gender.values()) {
				selectMap.put(i, genderToSelect);
				System.out.println(i + ") " + genderToSelect);
				i++;
			}
			String option = BankCommander.sc.nextLine();
			checkForQuit(option);
			if (option == null)
				continue;

			int choice;
			try {
				choice = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				System.out.println("Select valid option");
				continue;
			}

			if (choice < 0 || choice > selectMap.size() - 1) {
				System.out.println("Select option from 0 to " + (selectMap.size() - 1));
				continue;
			}

			gender = selectMap.get(choice);
			System.out.println("Gender selected: " + gender);
			break;
		}

		String clientEmail;
		System.out.println("Input client's email:");
		while (true) {
			clientEmail = BankCommander.sc.nextLine();
			checkForQuit(clientEmail);
			if (clientName == null)
				continue;
			if (!clientEmail.matches("^[A-Za-z\\.-0-9]{2,}@[A-Za-z\\.-0-9]{2,}\\.[A-Za-z]{2,3}$")) {
				System.out.println("Enter valid client email");
				continue;
			}
			System.out.println("Client email is: " + clientEmail);
			break;
		}

		String clientTelNumber;
		System.out.println("Input client phone number:");
		while (true) {
			clientTelNumber = BankCommander.sc.nextLine();
			checkForQuit(clientTelNumber);
			if (clientTelNumber == null)
				continue;
			if (!clientTelNumber.matches("^[0-9]{7,10}$")) {
				System.out.println("Enter valid client phone");
				continue;
			}
			System.out.println("Client phone is: " + clientTelNumber);
			break;
		}

		float initialBalance;
		System.out.println("Imput initial balance:");
		while (true) {
			initialBalance = CommandHelper.getOperationSum();
			if (initialBalance < 0) {
				System.out.println("Initial balance cannot be negative.");
				continue;
			}
			break;
		}
		
		float initialOverdraft;
		System.out.println("Imput overdraft:");
		while (true) {
			initialOverdraft = CommandHelper.getOperationSum();
			if (initialOverdraft < 0) {
				System.out.println("Overdraft cannot be negative.");
				continue;
			}
			break;
		}
		
		Client client = new Client(clientName, gender);
		client.setEmail(clientEmail);
		client.setTelNumber(clientTelNumber);
		
		Account account;
		try {
			account = new CheckingAccount(initialBalance, initialOverdraft);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("Operation failed.");
			return;
		}
		
		BankService bs = new BankServiceImpl();
		
		bs.addAccount(client, account);
		bs.setActiveAccount(client, account);
		
		try {
			bs.addClient(BankCommander.currentBank, client);
		} catch (ClientExistsException e) {
			System.out.println(e.getMessage());
			System.out.println("Operation failed.");
			return;
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Add new client");
	}

	private void checkForQuit(String input) {
		if (input.equalsIgnoreCase("quit"))
			return;
	}

}
