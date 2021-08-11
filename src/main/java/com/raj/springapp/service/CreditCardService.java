package com.raj.springapp.service;

import org.springframework.stereotype.Service;

import com.raj.springapp.modal.CreditCard;

@Service
public class CreditCardService implements CardService {

	@Override
	public CreditCard payByCreditCard(CreditCard creditCard) 
	{
		System.out.println("Pay By Credit Card Here");
		
		return creditCard;
	}

}
