package com.softserve.edu.jroutes.exception;

public class NonUniqueException extends Exception {
	private static final long serialVersionUID = -5912632877220512104L;

	public NonUniqueException() {
	}

	public NonUniqueException(String messsage) {
		super(messsage);
	}
}
