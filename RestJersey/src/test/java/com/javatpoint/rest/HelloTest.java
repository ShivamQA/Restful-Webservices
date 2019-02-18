package com.javatpoint.rest;


import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.Test;




public class HelloTest 

{
@Test
public void TestLogin() throws URISyntaxException {
Hello ob = new Hello();
Response rs = ob.login("ankit@gmail.com", "123456");
boolean b = ob.b;

Assert.assertEquals(true,b,"Failed");
}
@Test
public void Testsignup() throws URISyntaxException
{
HelloTest obj = new HelloTest();
Hello ob = new Hello();
Response rs = ob.signup("Prakul","prakul@gmail.com","1234");
obj.TestExample("prakul@gmail.com","1234");

}


public void TestExample(String email,String pwd) throws URISyntaxException {
Hello ob = new Hello();
Response rs = ob.login(email, pwd);
boolean b = ob.b;

Assert.assertEquals(true,b,"Failed");
}


}
