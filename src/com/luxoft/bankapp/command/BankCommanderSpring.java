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
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BankCommanderSpring {

    private static AbstractApplicationContext context;
    private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "classpath:resources/application-context.xml";

    public Bank currentBank = new Bank("MyBank");
    public Client currentClient;
    public Scanner sc = new Scanner(System.in);
    
    private Map<String, Command> commandMap;
    
    public BankCommanderSpring() {
        System.out.println(currentBank);
        System.out.println("CREATING NEW COMMANDER: " + this);
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public static void main(String args[]) {

        context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML_FILE_NAME);
        
        BankCommanderSpring bc = context.getBean(BankCommanderSpring.class);

        bc.initializeCommands();

        while (true) {

            Iterator<Entry<String, Command>> it = bc.commandMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Command> pair = (Map.Entry<String, Command>) it.next();
                System.out.print(pair.getKey() + ") ");
                pair.getValue().printCommandInfo();
            }

            String command = bc.sc.nextLine();

            if (!bc.commandMap.containsKey(command)) {
                System.out.println("Please enter valid command.");
                continue;
            }
            bc.commandMap.get(command).execute();
        }
    }

    private void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    private void removeCommand(String name) {
        commandMap.remove(name);
    }

    private void initializeCommands() {
        /*registerCommand("1", new FindClientCommand());
        registerCommand("2", new GetAccountCommand());
        registerCommand("3", new DepositCommand());
        registerCommand("4", new WithdrawCommand());
        registerCommand("5", new TransferCommand());
        registerCommand("6", new AddClientCommand());
        registerCommand("7", new FeedCommand());
        registerCommand("8", new SaveClientCommand());
        registerCommand("9", new LoadClientCommand());
        registerCommand("10", new SaveAllClientsCommand());
        registerCommand("11", new LoadAllClientsCommand());*/
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
}
