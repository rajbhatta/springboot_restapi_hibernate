# Spring Boot REST API using Hibernate and Mockito

This application, first creates a user account by calling http://localhost:8080/adduser.

For payment processing, this application calls http://localhost:8080/addcreditcard.

I have created an orchestration REST API that will orchestrate the calls between the user creation, and the payment processing APIs. 
using http://localhost:8080/orchestra-call

Note: for REST CLIENT, I have created two files using HTML and angular:  creditcard.html and user.html. So, you can call:
http://localhost:8080/creditcard.html or http://localhost:8080/user.html, if you don't wanna use postman for generating JSON data.

# Source Code #

## Controller ##

```java
package com.raj.springapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.raj.springapp.Util;
import com.raj.springapp.exception.UserDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.raj.springapp.modal.CreditCard;
import com.raj.springapp.modal.User;
import com.raj.springapp.service.CardService;
import com.raj.springapp.service.UserService;

@RestController
public class NexusRestController {
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private CardService creditCardService;

    public NexusRestController() {
        restTemplate = new RestTemplate();
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    ResponseEntity<String> addCustomer(@Valid @RequestBody User user) throws UserDaoException {
        if (!userService.save(user)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addcreditcard", method = RequestMethod.POST, headers = "Accept=application/json")
    public void addCreditCard(@Valid @RequestBody CreditCard creditCard) {
        creditCardService.payByCreditCard(creditCard);
    }

    @RequestMapping("/orchestra-call")
    public @ResponseBody
    ResponseEntity<String> userPaymentOrchestra() {
        ResponseEntity<User> response = orchestraCreateUser();
        if (response.getStatusCodeValue() == 201) {
            orchestraMakePayment();
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<User> orchestraCreateUser() {
        User user = new User("Samon Bhatta", "10 Barrymore Road, Scarborough", "samon@gmail.com");
        User resonseEntity = restTemplate.postForObject(Util.USER_CREATION_URI, user, User.class);
        return new ResponseEntity<User>(resonseEntity, HttpStatus.CREATED);
    }

    public void orchestraMakePayment() {
        CreditCard creditCard = new CreditCard("123456789123123", "05-2022", "123", "10 Barrymore Road, Scarborough");
        restTemplate.postForObject(Util.CREDIT_CARD_INFO_URI, creditCard, CreditCard.class);
    }


    @RequestMapping("/user/get")
    public @ResponseBody
    List<User> showUserList() {
        return userService.getList();
    }

}

```

## Dao ##

```java
package com.raj.springapp.dao;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import com.raj.springapp.modal.User;

public interface DaoService<T> {

    boolean save(T t) throws UserDaoException;

    User get(int id);

    void delete(int id);

    List<User> getList();

}

```

```java
package com.raj.springapp.dao;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.raj.springapp.modal.User;

@Repository
public class UserDaoImpl implements DaoService<User> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean save(User user) throws UserDaoException {
        try {
            sessionFactory.getCurrentSession().persist(user);
            return true;
        } catch (Exception ee) {
            throw new UserDaoException("UNABLE TO SAVE THE USER TO DATABASE ", ee);
        }

    }

    @Override
    public User get(int id) {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
        return user;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        session.delete(user);

    }

    @Override
    public List<User> getList() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

}

```

## Exception ##
```java
package com.raj.springapp.exception;

public class UserDaoException extends Exception {

    public UserDaoException(String message) {
        super(message);
    }

    public UserDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDaoException(Throwable cause) {
        super(cause);
    }

    public UserDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

## Modal ##
```java
package com.raj.springapp.modal;

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

```

```java
package com.raj.springapp.modal;

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

```

## Service ##
```java
package com.raj.springapp.service;

import com.raj.springapp.modal.CreditCard;

public interface CardService
{
	CreditCard payByCreditCard(CreditCard creditCard);

}

```

```java
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

```

```java
package com.raj.springapp.service;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import com.raj.springapp.modal.User;

public interface UserService<T> {

	boolean save(T t) throws UserDaoException;
	User get(int id);
	void delete(int id);
	List<User> getList();
	
}

```

```java
package com.raj.springapp.service;

import java.util.List;

import com.raj.springapp.exception.UserDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raj.springapp.dao.DaoService;
import com.raj.springapp.modal.User;

@Service
public class UserDaoService implements UserService<User> {

	@Autowired
	private DaoService userDao;
	
	@Override
	@Transactional
	public boolean save(User user) throws UserDaoException {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public User get(int id) {
		return userDao.get(id);
	}

	@Override
	@Transactional
	public void delete(int id)
	{
		userDao.delete(id);
	}

	@Override
	@Transactional
	public List<User> getList() {
		return userDao.getList();
	}

}

```

## Exception ##
```java
package com.raj.springapp.exception;

public class UserDaoException extends Exception {

    public UserDaoException(String message) {
        super(message);
    }

    public UserDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDaoException(Throwable cause) {
        super(cause);
    }

    public UserDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

```

## Hibernate configuration ##
```java
package com.raj.springapp;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Value("${db.driver}")
	private String driver;
	
	@Value("${db.password}")
	private String password;
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.username}")
	private String username;
	
	@Value("${hibernate.dialect}")
	private String dialect;
 
	@Value("${hibernate.show_sql}")
	private String showSql;
 
	@Value("${entitymanager.packagesToScan}")
	private String packagesToScan;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2DdlAuto;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
 
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(packagesToScan);
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", dialect);
		hibernateProperties.put("hibernate.show_sql", showSql);
		hibernateProperties.put("hibernate.hbm2ddl.auto", hbm2DdlAuto);
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory;
	}
 
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

}
```

## Util ##
```java
package com.raj.springapp;

public class Util {
    public static final String CREDIT_CARD_INFO_URI = "http://localhost:8080/addcreditcard";
    public static final String USER_CREATION_URI = "http://localhost:8080/adduser";
}
```

## SprintBoot loader ##
```java
package com.raj.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SpringAppApplication {
		
	public static void main(String[] args)
	{
		SpringApplication.run(SpringAppApplication.class, args);
	}

}
```