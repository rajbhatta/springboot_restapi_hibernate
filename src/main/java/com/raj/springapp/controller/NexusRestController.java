package com.raj.springapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.raj.springapp.model.CreditCard;
import com.raj.springapp.model.User;
import com.raj.springapp.service.CreditCardService;
import com.raj.springapp.service.UserService;

@RestController
public class NexusRestController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@RequestMapping(value = "/adduser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<String> addCustomer(@Valid @RequestBody User user)
	{
		boolean persistStatus=userService.saveUser(user);
		
		if(!persistStatus)
		{
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		else
		{
			return new ResponseEntity(HttpStatus.CREATED);
		}
	}
	
	@RequestMapping(value = "/addcreditcard", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addCreditCard(@Valid @RequestBody CreditCard creditCard)
	{
		 creditCardService.payByCreditCard(creditCard);
	}
	
	@RequestMapping("/orchestra-call")
	public  @ResponseBody String userPaymentOrchestra()
	{
		 ResponseEntity<User> response= orchestraCreateUser();
		 if(response.getStatusCodeValue()==201)
		 {
			 orchestraMakePayment();
			 return "Payment is completed";
		 }
		 else
		 {
			 return "Unable to create user account";
		 }
	}
	
	public ResponseEntity<User> orchestraCreateUser()
	{
		final String USER_CREATION_URI = "http://localhost:8080/adduser";
		 
		User user=new User("Samon Bhatta","10 Barrymore Road, Scarborough","samon@gmail.com");
	    RestTemplate restTemplate = new RestTemplate();
	    User resonseEntity=restTemplate.postForObject( USER_CREATION_URI, user, User.class);
	    return new ResponseEntity<User>(resonseEntity, HttpStatus.CREATED);
	}
	
	public void orchestraMakePayment()
	{
		final String CREDIT_CARD_INFO_URI = "http://localhost:8080/addcreditcard";
		 
		CreditCard creditCard=new CreditCard("123456789123123","05-2022","123", "10 Barrymore Road, Scarborough");
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.postForObject( CREDIT_CARD_INFO_URI, creditCard, CreditCard.class);
	}
		
	
	@RequestMapping("/user/get")
	public @ResponseBody List<User> showUserList()
	{
	    return userService.getUserList();
	}

}
