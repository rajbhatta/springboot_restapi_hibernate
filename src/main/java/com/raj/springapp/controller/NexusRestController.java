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
