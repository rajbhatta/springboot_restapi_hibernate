# Spring Boot REST API using Hibernate and Mockito

This application, first creates a user account by calling http://localhost:8080/adduser.

For payment processing, this application calls http://localhost:8080/addcreditcard.

I have created an orchestration REST API that will orchestrate the calls between the user creation, and the payment processing APIs. 
using http://localhost:8080/orchestra-call

Note: for REST CLIENT, I have created two files using HTML and angular:  creditcard.html and user.html. So, you can call:
http://localhost:8080/creditcard.html or http://localhost:8080/user.html if you don't wanna use postman for generating JSON data.


