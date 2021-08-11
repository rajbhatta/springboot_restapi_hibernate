package com.raj.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.raj.springapp.service.UserService;

@Controller
public class UserController 
{
	@Autowired
	private UserService userService;
	
//	@RequestMapping("/validcredit")
//	public String provide

	

}
