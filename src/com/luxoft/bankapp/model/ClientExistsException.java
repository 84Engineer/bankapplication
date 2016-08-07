package com.luxoft.bankapp.model;

@SuppressWarnings("serial")
public class ClientExistsException extends BankException{

	public ClientExistsException(String message) {
		super(message);
	}

}
