package com.raj.springapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.raj.springapp.model.User;
import com.raj.springapp.service.UserService;

@Controller
public class UserController 
{
	@Autowired
	private UserService userService;
	
//	@RequestMapping("/validcredit")
//	public String provide

	

}
