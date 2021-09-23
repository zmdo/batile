package com.outbina.sgengine.game.exception;

public class InvalidParameterException extends Exception{
	private static final long serialVersionUID = 7910376637459285273L;
	
	public InvalidParameterException() {}
	
	public InvalidParameterException(String str) {
		super(str);
	}
	
}
