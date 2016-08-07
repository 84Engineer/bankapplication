package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.ClientExistsException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class BankServiceImpl implements BankService {

    @Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(client);
    }

    @Override
    public void removeClient(Bank bank, Client client) {
        bank.removeClient(client);
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccount(Client client, Account account) {
        try {
            client.setActiveAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getClient(Bank bank, String name) {
        return bank.getClientByName(name);
    }

    @Override
    public void loadClients(Bank bank) throws Exception {
        List<Map<String, String>> feedData = BankFeedService.loadFeed("D:\\IDEfiles\\BankApplication\\src\\feedfiles\\");
        for (Map feedRecord : feedData) {
            bank.parseFeed(feedRecord);
        }
    }

    @Override
    public void saveClient(Client client) throws Exception {
        String fileName = client.getName().replaceAll(" ", "").concat(".ser");
        String dir = "D:\\IDEfiles\\BankApplication\\src\\saved\\";
        File savedClient = new File(dir + fileName);
        if (savedClient.exists()) {
            savedClient.delete();
        }
        savedClient.createNewFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savedClient))) {
            oos.writeObject(client);
        }
    }

    @Override
    public void loadClient(String name, Bank bank) throws Exception {
        String fileName = name.replaceAll(" ", "").concat(".ser");
        File clientFile = new File("D:\\IDEfiles\\BankApplication\\src\\saved\\" + fileName);
        if (!clientFile.exists()) {
            System.out.println("File for client is not found.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(clientFile))) {
            Client client = (Client) ois.readObject();
            bank.addClient(client);
        }
        //clientFile.delete();
    }

    @Override
    public void loadAllClients(Bank bank) throws Exception {
        File dir = new File("D:\\IDEfiles\\BankApplication\\src\\saved\\");
        File[] savedClientsFiles = dir.listFiles();
        for (File file : savedClientsFiles) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Client client = (Client) ois.readObject();
                bank.addClient(client);
            }
        }
    }
}
