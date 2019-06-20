package com.raj.springapp.service;

import org.springframework.stereotype.Service;

import com.raj.springapp.model.CreditCard;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Override
	public CreditCard payByCreditCard(CreditCard creditCard) 
	{
		System.out.println("Pay By Credit Card Here");
		
		return creditCard;
	}

}
