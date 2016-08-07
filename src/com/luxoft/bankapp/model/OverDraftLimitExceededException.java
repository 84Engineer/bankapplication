package com.luxoft.bankapp.model;

public class OverDraftLimitExceededException extends NotEnoughFundsException {

	private Account account;
	
	public OverDraftLimitExceededException(String message, Account account, float amount) {
		super(message, amount);
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}
