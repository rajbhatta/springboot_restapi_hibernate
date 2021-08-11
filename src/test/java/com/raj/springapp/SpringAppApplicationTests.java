package com.raj.springapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.raj.springapp.exception.UserDaoException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.raj.springapp.dao.DaoService;
import com.raj.springapp.modal.CreditCard;
import com.raj.springapp.modal.User;
import com.raj.springapp.service.CardService;
import com.raj.springapp.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringAppApplicationTests 
{
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CardService creditCardService;
	
	@MockBean
	private DaoService userDao;
	
	
	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/user/get",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}
	
	@Test
	public void getAllUserTestAlternative()
	{
		when(userDao.getList()).thenReturn(Stream.of(new User("raj","10 Barrymore Road","raj@gmail.com"),new User("raj","10 Barrymore Road","raj@gmail.com")).collect(Collectors.toList()));
		assertEquals(2,userService.getList().size());
	}
	

	@Test
	public void creditCardTest()
	{
		CreditCard creditCard=new CreditCard("123456789123123","05-2022","123", "10 Barrymore Road, Scarborough");
		assertEquals(creditCard, creditCardService.payByCreditCard(creditCard));
	}
	
	@Test
	public void saveUserTest() throws UserDaoException {
		User user=new User("raj","10 Barrymore Road","raj@gmail.com");
		
//		userService.saveUser(user);
//		verify(userDao, times(0)).saveUser(user);
		
		when(userDao.save(user)).thenReturn(true);
		assertEquals(user, userService.save(user));
	}


	
	

}
