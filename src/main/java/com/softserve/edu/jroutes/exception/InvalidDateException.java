package com.softserve.edu.jroutes.exception;

public class InvalidDateException extends Exception {
	private static final long serialVersionUID = 1034088156596050753L;

	public InvalidDateException() {
	}

	public InvalidDateException(String messsage) {
		super(messsage);
	}

}