package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.ClientExistsException;

public interface BankService {

    void addClient(Bank bank, Client client) throws ClientExistsException;

    void removeClient(Bank bank, Client client);

    void addAccount(Client client, Account account);

    void setActiveAccount(Client client, Account account);

    Client getClient(Bank bank, String name);

    void loadClients(Bank bank) throws Exception;
    
    void saveClient(Client client) throws Exception;
    
    void loadClient(String name, Bank bank) throws Exception;
    
    void loadAllClients(Bank bank) throws Exception;
}
