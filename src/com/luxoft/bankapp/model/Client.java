package com.luxoft.bankapp.model;

import com.luxoft.bankapp.model.AbstractAccount.AccountType;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Client implements Report, Serializable {

    private String name;
    private Set<Account> accounts = new HashSet<Account>();
    private Account activeAccount;
    private Gender gender;
    private String email;
    private String telNumber;
    private String city;

    public enum Gender {
        MALE("Mr. "), FEMALE("Ms. ");

        private String salutation;

        private Gender(String salutation) {
            this.salutation = salutation;
        }

        public String getSalutation() {
            return salutation;
        }
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    void parseFeed(Map<String, String> feed) throws IllegalArgumentException {
        processAccount(feed);
    }

    private void processAccount(Map<String, String> feed) throws IllegalArgumentException {
        String accountType = feed.get("accounttype");
        AccountType accType;
        if (accountType.equalsIgnoreCase("s")) {
            accType = AccountType.SAVING_ACCOUNT;
        } else if (accountType.equalsIgnoreCase("c")) {
            accType = AccountType.CHECKING_ACCOUNT;
        } else {
            throw new FeedException(String.format("Account type not found: %s", accountType));
        }
        for (Account acc : accounts) {
            if (acc.getAccountType() == accType) {
                acc.parseFeed(feed);
            }
        }
        createAccount(feed, accType);
    }

    private void createAccount(Map<String, String> feed, AccountType accType) throws IllegalArgumentException {
        Account acc;
        if (accType == AccountType.CHECKING_ACCOUNT) {
            float balance = Float.parseFloat(feed.get("balance"));
            float overdraft = Float.parseFloat(feed.get("overdraft"));
            acc = new CheckingAccount(balance, overdraft);
        } else {
            float balance = Float.parseFloat(feed.get("balance"));
            acc = new SavingAccount(balance);
        }
        accounts.add(acc);
    }

    @Override
    public void printReport() {
        System.out.println(getClientSalutation());
        for (Account account : accounts) {
            account.printReport();
        }
        if (activeAccount != null) {
            System.out.println("Active account info:");
            activeAccount.printReport();
        }
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public void setActiveAccount(Account account) throws Exception {
        if (!accounts.contains(account)) {
            throw new Exception("Passed account doesn't belong to this client.");
        }
        activeAccount = account;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public String getName() {
        return name;
    }

    public String getClientSalutation() {
        if (gender != null) {
            return gender.getSalutation() + name;
        } else {
            return name;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Client)) {
            return false;
        }
        Client compareClient = (Client) obj;
        if (!getName().equals(compareClient.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        char[] nameArray = getName().toCharArray();
        int hashCode = 0;
        for (char letter : nameArray) {
            hashCode += (int) letter;
        }
        return hashCode * prime;
    }

    @Override
    public String toString() {
        int accountsCount = accounts.size();
        StringBuilder clientInfo = new StringBuilder(getClientSalutation()).append(" has ").append(accountsCount)
                .append(" opened account(s).");
        if (getActiveAccount() != null) {
            long activeAccountNumber = activeAccount.getAccountNumber();
            clientInfo.append("\nActive account No: ").append(activeAccountNumber);
        }
        return clientInfo.toString();
    }

}
