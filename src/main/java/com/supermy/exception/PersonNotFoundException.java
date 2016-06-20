package com.supermy.exception;

public class PersonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2859292084648724403L;
	private final int personId;
	
	public PersonNotFoundException(int id) {
		personId = id;
	}
	
	public int getPersonId() {
		return personId;
	}

}
