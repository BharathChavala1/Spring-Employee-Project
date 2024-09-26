package com.example.demo.customException;

public class EmployeeIdNotFoundException extends RuntimeException {
	public EmployeeIdNotFoundException(String message) {
		super(message);
	}

}
