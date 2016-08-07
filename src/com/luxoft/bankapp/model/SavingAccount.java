package com.luxoft.bankapp.model;

public class SavingAccount extends AbstractAccount {

	public SavingAccount(float balance) throws IllegalArgumentException {
		super(AccountType.SAVING_ACCOUNT, balance);
	}	
}
