package com.luxoft.bankapp.model;

import java.util.Map;

public class CheckingAccount extends AbstractAccount {

    private float overdraft;

    public CheckingAccount(float balance) throws IllegalArgumentException {
        super(AccountType.CHECKING_ACCOUNT, balance);
        this.overdraft = 0;
    }

    public CheckingAccount(float balance, float overdraft) throws IllegalArgumentException {
        super(AccountType.CHECKING_ACCOUNT, balance);
        if (overdraft < 0) {
            throw new IllegalArgumentException("The overdraft cannot be negative. Overdraft is: " + overdraft,
                    overdraft);
        }
        this.overdraft = overdraft;
    }

    @Override
    public void parseFeed(Map<String, String> feed) {
        float balance = Float.parseFloat(feed.get("balance"));
        float overdraft = Float.parseFloat(feed.get("overdraft"));
        this.balance = balance;
        this.overdraft = overdraft;
    }

    @Override
    public void printReport() {
        super.printReport();
        System.out.println("Overdraft is: " + overdraft);
    }

    @Override
    public void withdraw(float x) throws NotEnoughFundsException {
        if (getBalance() + overdraft < x) {
            throw new OverDraftLimitExceededException("Cannot withdraw specified amount, not enough funds.", this,
                    getBalance() + overdraft);
        }
        if (x < 0) {
            throw new NotEnoughFundsException("Cannot withdraw negative amount. Amount is: " + x, x);
        }

        balance -= x;
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) throws IllegalArgumentException {
        if (overdraft < 0) {
            throw new IllegalArgumentException("The overdraft cannot be negative. Overdraft is: " + overdraft,
                    overdraft);
        }
        this.overdraft = overdraft;
    }
}
