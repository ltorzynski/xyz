package com.acme.calc;

public class DivisorCannotBeZeroException extends RuntimeException {

	public DivisorCannotBeZeroException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -6276655021609125666L;

}
