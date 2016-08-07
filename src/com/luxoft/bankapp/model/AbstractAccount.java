package com.luxoft.bankapp.model;

import java.util.Map;

public abstract class AbstractAccount implements Account {

    protected float balance;
    private long accountNumber;
    private AccountType accountType;

    public enum AccountType {
        SAVING_ACCOUNT("Saving"), CHECKING_ACCOUNT("Checking");

        private String accountType;

        private AccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getType() {
            return accountType;
        }
    }

    public AbstractAccount(AccountType accountType, float balance) throws IllegalArgumentException {
        if (balance < 0) {
            throw new IllegalArgumentException("The balance cannot be negative. Balance is: " + balance, balance);
        }
        this.accountNumber = generateAccountNumber();
        this.balance = balance;
        this.accountType = accountType;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        float balance = Float.parseFloat(feed.get("balance"));
        this.balance = balance;
    }

    private long generateAccountNumber() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    @Override
    public long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public float getBalance() {
        return balance;
    }

    public float decimalValue() {
        float beforeRound = getBalance() * 100;
        float afterRound = Math.round(beforeRound);
        return afterRound / 100;
    }

    @Override
    public void deposit(float x) throws NotEnoughFundsException {
        if (x < 0) {
            throw new NotEnoughFundsException("Cannot deposit negative amount. Amount: " + x, x);
        }
        balance += x;
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (balance < x) {
            throw new NotEnoughFundsException(
                    "Cannot withdraw specified amount, not enough funds. Amount: " + x + " Funds: " + balance, x);
        }
        if (x < 0) {
            throw new NotEnoughFundsException("Cannot withdraw negative amount. Amount is: " + x, x);
        }

        balance -= x;

    }

    @Override
    public void printReport() {
        System.out.println("Account number is: " + getAccountNumber());
        System.out.println("Account type is: " + getAccountType().getType());
        System.out.println("Balance is: " + balance);
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractAccount)) {
            return false;
        }
        AbstractAccount account = (AbstractAccount) obj;
        if (!(account.getAccountType() == getAccountType())) {
            return false;
        }
        if (!(account.getAccountNumber() == getAccountNumber())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hashCode = 0;
        char[] accountTypeArray = getAccountType().getType().toCharArray();
        for (char letter : accountTypeArray) {
            hashCode += (int) letter;
        }
        hashCode = (int) (hashCode * prime + getAccountNumber());
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder accountInfo = new StringBuilder("Account No: ").append(getAccountNumber()).append(".\n")
                .append("Balance is: ").append(getBalance()).append(".");
        return accountInfo.toString();
    }
}
