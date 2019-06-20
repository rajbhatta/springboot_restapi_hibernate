package com.raj.springapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "User")
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "Please provide your full name")
	private String name;
	
	@NotEmpty(message = "Please provide address")
	private String address;
	
	@Email(message = "Email should be valid")
	private String email;
	
	public User() 
	{
		
	}

	public User(@NotEmpty(message = "Please provide your full name") String name,
			@NotEmpty(message = "Please provide address") String address,
			@Email(message = "Email should be valid") String email) 
	{
		this.name = name;
		this.address = address;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
	

}
