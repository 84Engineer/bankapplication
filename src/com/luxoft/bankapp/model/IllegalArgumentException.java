package com.luxoft.bankapp.model;

public class IllegalArgumentException extends BankException {

	private float value;

	public IllegalArgumentException(String message, float value) {
		super(message);
		this.value = value;
	}

	public float getValue() {
		return value;
	}

}
