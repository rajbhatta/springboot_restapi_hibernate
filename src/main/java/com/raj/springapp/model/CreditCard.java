package com.raj.springapp.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreditCard
{
	@Size(min=13, message="Credit Card number must be between 13 and 16 ")
	@NotEmpty(message = "Please provide a Credit Card Number")
	private String creditcardno ;
	
	@NotNull
	private String expdate;
	
	@Size(min=3, message="CVV number must be of size 3 and Integer")
	private String cvv;
	
	@NotEmpty(message = "Please provide Address")
	private String address;

	public CreditCard() 
	{
		
	}

	public CreditCard(
			@Size(min = 13, message = "Credit Card number must be between 13 and 16 ") @NotEmpty(message = "Please provide a Credit Card Number") String creditcardno,
			@NotNull String expdate, @Size(min = 3, message = "CVV number must be of size 3 and Integer") String cvv,
			@NotEmpty(message = "Please provide Address") String address) 
	{
		this.creditcardno = creditcardno;
		this.expdate = expdate;
		this.cvv = cvv;
		this.address = address;
	}

	public String getCreditcardno() {
		return creditcardno;
	}

	public void setCreditcardno(String creditcardno) {
		this.creditcardno = creditcardno;
	}

	public String getExpdate() {
		return expdate;
	}

	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	
}
