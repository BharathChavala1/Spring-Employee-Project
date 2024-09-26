package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
public class ICICIPayment {
	@Id
	@Column(name = "customerName")
	private String customerName;
	private String accountNumber;
	private String cardNumber;
	private int cvvNumber;

	public ICICIPayment() {
		super();
	}

	public ICICIPayment(String customerName, String accountNumber, String cardNumber, int cvvNumber) {
		super();
		this.customerName = customerName;
		this.accountNumber = accountNumber;
		this.cardNumber = cardNumber;
		this.cvvNumber = cvvNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvvNumber() {
		return cvvNumber;
	}

	public void setCvvNumber(int cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	@Override
	public String toString() {
		return "ICICIPayment [customerName=" + customerName + ", accountNumber=" + accountNumber + ", cardNumber="
				+ cardNumber + ", cvvNumber=" + cvvNumber + "]";
	}

}
