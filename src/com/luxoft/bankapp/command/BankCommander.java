package com.luxoft.bankapp.command;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.ClientExistsException;
import com.luxoft.bankapp.model.IllegalArgumentException;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.model.Client.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.TaskHelper;

public class BankCommander {

    public static Bank currentBank = new Bank("MyBank");
    public static Client currentClient;
    public static Scanner sc = new Scanner(System.in);
    private static Map<String, Command> commandMap = new TreeMap<String, Command>();

    public static void main(String args[]) {

        //initialize();
        initializeCommands();

        while (true) {

            Iterator<Entry<String, Command>> it = commandMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Command> pair = (Map.Entry<String, Command>) it.next();
                System.out.print(pair.getKey() + ") ");
                pair.getValue().printCommandInfo();
            }

            String command = sc.nextLine();

            if (!commandMap.containsKey(command)) {
                System.out.println("Please enter valid command.");
                continue;
            }
            commandMap.get(command).execute();
        }
    }

    private static void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    private static void removeCommand(String name) {
        commandMap.remove(name);
    }

    private static void initializeCommands() {
        registerCommand("1", new FindClientCommand());
        registerCommand("2", new GetAccountCommand());
        registerCommand("3", new DepositCommand());
        registerCommand("4", new WithdrawCommand());
        registerCommand("5", new TransferCommand());
        registerCommand("6", new AddClientCommand());
        registerCommand("7", new FeedCommand());
        registerCommand("8", new SaveClientCommand());
        registerCommand("9", new LoadClientCommand());
        registerCommand("10", new SaveAllClientsCommand());
        registerCommand("11", new LoadAllClientsCommand());
        registerCommand("q", new Command() {
            @Override
            public void execute() {
                sc.close();
                System.out.println("Good buy!");
                System.exit(0);
            }

            @Override
            public void printCommandInfo() {
                System.out.println("Exit");

            }
        });
    }

    private static void initialize() {
        BankService bankService = new BankServiceImpl();
        for (int i = 1; i <= TaskHelper.randomInt(5, 15); i++) {
            int genderType = TaskHelper.randomInt(0, 1);
            Gender gender;
            if (genderType == 0) {
                gender = Gender.MALE;
            } else {
                gender = Gender.FEMALE;
            }
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
                bankService.addClient(currentBank, client);
            } catch (ClientExistsException e) {
                e.printStackTrace();
            }
        }

    }

}
