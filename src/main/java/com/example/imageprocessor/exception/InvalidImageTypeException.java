package com.example.imageprocessor.exception;

public class InvalidImageTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidImageTypeException(String param){
		super("ImageType "+param + " is not valid ");
	}

}
