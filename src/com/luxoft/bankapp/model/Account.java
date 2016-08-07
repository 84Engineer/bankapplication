package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.Map;

public interface Account extends Report, Serializable {

    float getBalance();

    void deposit(float x) throws NotEnoughFundsException;

    void withdraw(float x) throws NotEnoughFundsException;

    AbstractAccount.AccountType getAccountType();

    public long getAccountNumber();

    public float decimalValue();

    public void parseFeed(Map<String, String> feed);
}
