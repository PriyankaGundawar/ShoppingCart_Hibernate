package com.shopping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.shopping.cart.GenerateRandomString;
import com.shopping.model.Address;
import com.shopping.model.User;
import com.shopping.model.UserToken;
import com.shopping.util.ApplicationUtil;
import com.shopping.util.ApplicationConstants.TokenType;

public class UserTokenService {
	
	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;
	
	public void connect() {
		config = ApplicationUtil.configAnnotatedClass();
		sessionFactory = config.buildSessionFactory();		
	}

	public void beginTransaction() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	public void commitTransaction() {
		
		transaction.commit();
		session.close();
	}
	
	public UserToken persistUserToken(UserToken userToken) {
		System.out.println(userToken);
		beginTransaction();
		session.persist(userToken);
		commitTransaction();
		System.out.println("Address Added :: "+userToken);
		return userToken;
	}
	
	public UserToken forgotPasswordToken(User user) {
		UserTokenService uts = new UserTokenService();
		uts.connect();
		UserToken userToken = new UserToken();
		
		GenerateRandomString generateRandomString = new GenerateRandomString();
		String token = generateRandomString.getAlphaNumericString(20);
		
		userToken.setUser(user);
		userToken.setToken(token);
		userToken.setType(TokenType.ForgotPassword.toString());
		userToken.setCreatedAt(new Date(System.currentTimeMillis()));
//		System.out.println("");
		uts.persistUserToken(userToken);
		
		return userToken;
	}

}
