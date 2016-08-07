package com.luxoft.bankapp.model;

public class NotEnoughFundsException extends BankException {

	private float amount;

	public NotEnoughFundsException(String message) {
		super(message);
	}

	public NotEnoughFundsException(String message, float amount) {
		super(message);
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

}
