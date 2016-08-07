package com.luxoft.bankapp.service;

import java.util.List;
import java.util.Set;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Client.Gender;
import com.luxoft.bankapp.model.ClientExistsException;
import com.luxoft.bankapp.model.IllegalArgumentException;
import com.luxoft.bankapp.model.NotEnoughFundsException;
import com.luxoft.bankapp.model.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.SavingAccount;

public class BankApplication {

	private static Bank bank;
	private static BankService bankService;

	private static void initialize() {
		bank = new Bank("MyBank");
		bankService = new BankServiceImpl();
		for (int i = 1; i <= TaskHelper.randomInt(5, 15); i++) {
			int genderType = TaskHelper.randomInt(0, 1);
			Gender gender;
			if (genderType == 0)
				gender = Gender.MALE;
			else
				gender = Gender.FEMALE;
			Client client = new Client("Client " + i, gender);
			switch (i % 3) {
			case 0:
				client.setCity("Kiev");
				break;
			case 1:
				client.setCity("NewYork");
				break;
			case 2:
				client.setCity("Sofia");
				break;
			default:
				client.setCity("Brovari");
				break;
			}
			
			try {
				Account activeAccount = new CheckingAccount(TaskHelper.randomFloat(100f, 100000f),
						TaskHelper.randomFloat(0f, 2000f));
				bankService.addAccount(client, activeAccount);
				client.setActiveAccount(activeAccount);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			for (int j = 1; j <= TaskHelper.randomInt(1, 5); j++) {
				int accountType = TaskHelper.randomInt(0, 1);
				Account account;
				if (accountType == 0) {
					try {
						account = new SavingAccount(TaskHelper.randomFloat(100f, 100000f));
						bankService.addAccount(client, account);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				} else {
					try {
						account = new CheckingAccount(TaskHelper.randomFloat(100f, 100000f),
								TaskHelper.randomFloat(0f, 2000f));
						bankService.addAccount(client, account);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				bankService.addClient(bank, client);
			} catch (ClientExistsException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws ClientExistsException {
		initialize();
		
		if(args[0].equals("–report")) {
			System.out.println("");
			System.out.println("REPORTS");
			BankReport.getNumberOfClients(bank);
			BankReport.getAccountsNumber(bank);
			BankReport.getClientsSorted(bank);
			BankReport.getBankCreditSum(bank);
			BankReport.getClientsByCity(bank);
			return;
		}
		
		Set<Client> clients = bank.getClients();
		for (Client client : clients) {
			System.out.println(client);
			Set<Account> accounts = client.getAccounts();
			for (Account account : accounts) {
				System.out.println(account);
				System.out.println("Decimal value is: " + account.decimalValue());
			}
		}

		/*
		 * printBankReport(); System.out.println(""); modifyBank();
		 * printBankReport();
		 */
	}

/*	private static void modifyBank() {
		Client clientToRemove = bank.getClients().get(TaskHelper.randomInt(0, bank.getClients().size() - 1));
		System.out.println("Removing client " + clientToRemove.getName() + "...");

		bankService.removeClient(bank, clientToRemove);

		for (Client client : bank.getClients()) {
			for (Account account : client.getAccounts()) {
				int operation = TaskHelper.randomInt(0, 1);
				if (operation == 0) {
					try {
						account.deposit(TaskHelper.randomFloat(0f, 10000f));
					} catch (NotEnoughFundsException e) {
						e.printStackTrace();
					}
				} else {
					try {
						account.withdraw(TaskHelper.randomFloat(0f, 150000f));
					} catch (OverDraftLimitExceededException e) {
						System.out.println("Exception occured.");
						System.out.println(e.getAmount());
						e.getAccount().printReport();
						e.printStackTrace();
					} catch (NotEnoughFundsException e) {
						e.printStackTrace();
					}
				}
			}

			Account account = client.getAccounts().get(TaskHelper.randomInt(0, client.getAccounts().size() - 1));
			bankService.setActiveAccount(client, account);
		}

	}*/

	private static void printBankReport() {
		bank.printReport();
	}

}
