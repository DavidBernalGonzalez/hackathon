package com.example.exceptions;

public class DuplicateCityException extends RuntimeException {
	private static final long serialVersionUID = 378758289166145294L;

	public DuplicateCityException(String message) {
        super(message);
    }

    public DuplicateCityException(String message, Throwable cause) {
        super(message, cause);
    }
}
