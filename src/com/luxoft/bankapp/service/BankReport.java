package com.luxoft.bankapp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;

public class BankReport {

	public static void getNumberOfClients(Bank bank) {
		System.out.println("Clients count of \"" + bank.getBankName() + "\" is: " + bank.getClients().size() + ".");
	}

	public static void getAccountsNumber(Bank bank) {
		int accountsCount = 0;
		Set<Client> clients = bank.getClients();
		Iterator<Client> it = clients.iterator();
		while (it.hasNext()) {
			Client client = it.next();
			accountsCount += client.getAccounts().size();
		}
		System.out.println("Accounts count of \"" + bank.getBankName() + "\" is: " + accountsCount + ".");
	}

	public static void getClientsSorted(Bank bank) {
		Set<Client> clientsSorted = new TreeSet<Client>((c1, c2) -> c1.getName().compareTo(c2.getName()));
		clientsSorted.addAll(bank.getClients());
		Iterator<Client> it = clientsSorted.iterator();

		while (it.hasNext()) {
			System.out.println(it.next().getClientSalutation());
		}
	}

	public static void getBankCreditSum(Bank bank) {
		float totalCredit = 0f;
		Set<Client> clients = bank.getClients();
		Iterator<Client> it = clients.iterator();
		while (it.hasNext()) {
			Client client = it.next();
			Set<Account> accounts = client.getAccounts();
			for (Account account : accounts) {
				if (account instanceof CheckingAccount) {
					CheckingAccount checkingAccount = (CheckingAccount) account;
					totalCredit += checkingAccount.getOverdraft();
				}
			}
		}
		System.out.println("Total sum of credits granted by \"" + bank.getBankName() + "\" is: " + totalCredit + ".");
	}

	public static void getClientsByCity(Bank bank) {
		Map<String, List<Client>> cityMap = new TreeMap<String, List<Client>>((s1, s2) -> s1.compareTo(s2));
		Set<Client> bankClients = bank.getClients();
		Set<String> citySet = new TreeSet<String>();

		for (Client client : bankClients)
			citySet.add(client.getCity());

		for(String city : citySet) {
			List<Client> cityClients = new ArrayList<Client>();
			for(Client client : bankClients) {
				if(city.equals(client.getCity()))
					cityClients.add(client);
			}
			cityMap.put(city, cityClients);
		}
		
        @SuppressWarnings("rawtypes")
		Iterator it = cityMap.entrySet().iterator();
        while(it.hasNext()) {
            StringBuilder sb = new StringBuilder("");
            @SuppressWarnings("unchecked")
			Map.Entry<String, List<Client>> pair = (Map.Entry<String, List<Client>>) it.next();
            sb.append(pair.getKey());
            sb.append(" =>> ");
            for(Client client : (List<Client>) pair.getValue()) {
                sb.append(client.getName());
                sb.append(",");
            }
            System.out.println(sb.toString());
        }
	}
}
