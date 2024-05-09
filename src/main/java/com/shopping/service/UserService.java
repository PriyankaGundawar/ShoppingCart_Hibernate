package com.shopping.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
//import java.net.URL;
import java.util.List;

import javax.print.DocFlavor.URL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;
import com.shopping.Exceptions.BadRequestException;
import com.shopping.Exceptions.InvalidUserOrPasswordEntered;
import com.shopping.Exceptions.InvalidUserTokenException;
import com.shopping.Exceptions.UserNotFoundException;
import com.shopping.Validations.UserValidation;
import com.shopping.cart.GenerateRandomString;
import com.shopping.cart.SendEmail;
import com.shopping.cart.UserTokenApp;
import com.shopping.model.Cart;
import com.shopping.model.EmailParams;
import com.shopping.model.User;
import com.shopping.model.UserToken;
import com.shopping.util.ApplicationConstants.UserStatus;
import com.shopping.util.ApplicationUtil;
import com.shopping.util.HashingUtil;

public class UserService {
	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;
	HashingUtil hashUtil = new HashingUtil();

	public void connect() {
//		config = new Configuration().configure().addAnnotatedClass(User.class);
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
	
	public User persistUser(User user)  {
//		User.setMetaTitle(User.getTitle() + " " + User.getSummary());
		System.out.println(user);
		beginTransaction();

		
		UserValidation validation = new UserValidation();
		try {
			if(validation.validate(user)) {
				//create activation token method and call here.
				
				GenerateRandomString randomToken = new GenerateRandomString();
				String token = randomToken.getAlphaNumericString(15);
				user.setToken(token);
				user.setTokenCreatedAt(new Date(System.currentTimeMillis()));
				
					
				String pass = hashUtil.stringHashing(user.getPasswordHash());
				user.setPasswordHash(pass);
				
				session.persist(user);
			}
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		commitTransaction();
		EmailParams emailParams = new EmailParams();
		String name = user.getFirstName() + " " + user.getLastName();
		emailParams.setEmailTo(user.getEmail());
		emailParams.setSubject("Registered successfully");
		emailParams.setEmailBody("Hello " + name + ", " + "you have been registered successfully.");

		SendEmail sendEmail = new SendEmail();
		sendEmail.sendEmail(emailParams);
		
		EmailParams userActivationemailParams = new EmailParams();
		String userName = user.getFirstName() + " " + user.getLastName();
		String token = user.getToken();
//		URL url = new URL(token);
		userActivationemailParams.setEmailTo(user.getEmail());
		userActivationemailParams.setSubject("user account Activation");
		userActivationemailParams.setEmailBody("Hello " + userName + ", " + "Please activate your account." + token);

		SendEmail userActivationSendEmail = new SendEmail();
		userActivationSendEmail.sendEmail(userActivationemailParams);
		
		
		
				
		System.out.println("User Added :: "+user);
		return user;
	}
	
	public User getByUserId(int id) throws UserNotFoundException {
		session = sessionFactory.openSession();
		User user=null;
		user = session.get(User.class, id);
		System.out.println("getByUserId :: " +user);
		session.close();
		if(user==null) {
			throw new UserNotFoundException("User not found Excpetion with id: "+id);
		}
		return user;
	}
	
	public List<User> getAllUsers() {
		String queryString = "select c from User c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, User.class);
		List<User> users = query.list();
		if (users.size() > 0) {
			System.out.println("List Of Users :: "+ users);
			return users;
		}
		session.close();
		return null;
		}
	
	public void deleteUserById(int id) {
		User user = new User();
		user.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(user);
		commitTransaction();		
		System.out.println("User Deleted By id : "+id);
	}
	
	public User updateUser(User user) {
//		User.setMetaTitle(User.getTitle() + " " + User.getSummary());
		beginTransaction();
		session.merge(user);
		commitTransaction();
		System.out.println("Updated User : "+ user);
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getListOfUsersByLastName(String lastName) {
		session = sessionFactory.openSession();
		String queryString = "select c from User c where c.lastName like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, User.class);
		query.setParameter("search", "%" + lastName + "%", String.class);
		List<User> users = query.list();
		session.close();
		if (users.size() > 0) {
			System.out.println("getListOfUsersByMetaTitle :: "+users);
			return users;
		}
		return null;
		}
	
	public User getUserFromToken(String token) throws InvalidUserTokenException {
		//get user from token using select query. remove the token for that user in db. add activation column to user table and set status to activated.
		session = sessionFactory.openSession();
		String queryString = "select c from User c where c.token = :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, User.class);
		query.setParameter("search", token);
		User user = (User) query.getSingleResult();
		
		if(user == null) {
			throw new InvalidUserTokenException("Invalid User token sent: ");
			//not throwing exception. check later
		}
		System.out.println(user);
		user.setToken(null);
		user.setStatus(UserStatus.Active.ordinal());
		updateUser(user);
		return user;
	}
	
	public void userLogin(String email, String passwordHash) throws InvalidUserOrPasswordEntered {
		String hashedPasswordFromUser = hashUtil.stringHashing(passwordHash);
		session = sessionFactory.openSession();
		String queryString = "select c from User c where c.email = :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, User.class);
		query.setParameter("search", email);
		User user = (User) query.getSingleResult();
		if(user == null) {
			throw new InvalidUserOrPasswordEntered("Invalid User or Password entered !!");
		}
		String passwrodFromDB = user.getPasswordHash();
		if(passwrodFromDB.equals(hashedPasswordFromUser)) {
			// hash entered password and compare
			System.out.println("Correct pwd !!");
		}
		else {
			throw new InvalidUserOrPasswordEntered("Invalid User or Password entered !!");
		}
	}
	
	public void userPasswordChange(String email, String passwordHash, String newPassword) throws InvalidUserOrPasswordEntered {
		String oldHashedPwd = hashUtil.stringHashing(passwordHash);
		System.out.println(oldHashedPwd);
		String newHashedPwd = hashUtil.stringHashing(newPassword);
		System.out.println(newHashedPwd);
		
		if(oldHashedPwd.equals(newHashedPwd)) { //even we entered wrong pwd sending wrong msg. check
			System.out.println("Already pwd used. please enter new one !!");
			//send bad request excpetion.check existing one
		}
		else {
			session = sessionFactory.openSession();
			String queryString = "select c from User c where c.email = :search";
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery(queryString, User.class);
			query.setParameter("search", email);
			User user = (User) query.getSingleResult();
			String passwrodFromDB = user.getPasswordHash();
			if(passwrodFromDB.equals(oldHashedPwd)) {
				System.out.println("Correct pwd !!");
				user.setPasswordHash(newHashedPwd);
				user.setLastPasswordUpdatedAt(new Date(System.currentTimeMillis()));
				updateUser(user);
				System.out.println("Pwd changed !!");
				
				EmailParams emailParams = new EmailParams();
				String name = user.getFirstName();
				emailParams.setEmailTo(email);
				emailParams.setSubject(name);
				emailParams.setEmailBody("Hi, " +name + " Your new password has been changed.");
				
				SendEmail sendEmail = new SendEmail();
				sendEmail.sendEmail(emailParams);
				
			}
			else {
			System.out.println("Incorrect pwd entered!!");
			throw new InvalidUserOrPasswordEntered("Invalid User or Password entered !!");
				
			}
			
		}		

	}
	

	public void forgotPassword(String email) { 
		session = sessionFactory.openSession();
		String queryString = "select c from User c where c.email = :emailSearch";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, User.class);
		query.setParameter("emailSearch", email);
		User user = (User) query.getSingleResult();
		System.out.println(user);
		
		if(user == null) {
			System.out.println("User not found. please enter valid details");
		}
		else {
			UserTokenService service = new UserTokenService();
			UserToken userToken = service.forgotPasswordToken(user);
			String token = userToken.getToken();
			
			EmailParams emailParams = new EmailParams();
			emailParams.setEmailTo(user.getEmail());
			emailParams.setSubject("Password Reset");
			emailParams.setEmailBody("Please click below link to reset the password" + token);
			
			SendEmail sendEmail = new SendEmail();
			sendEmail.sendEmail(emailParams);
			
			
			System.out.println("Check email to reset the password");
		}
		
	}
	
	public boolean tokenResponseForgotPassword(String token) {
		session = sessionFactory.openSession();
		String queryString = "select c from UserToken c where c.token = :tokenSearch";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, UserToken.class);
		query.setParameter("tokenSearch", token);
		UserToken userToken = (UserToken) query.getSingleResult();
		if(userToken == null) {
			System.out.println("Invalid token" );
			return false;
		}
		System.out.println("Valid token" + userToken);
		return true;
	}
	
	public void resetPassword(String token, String newPassword) {
		session = sessionFactory.openSession();
		String queryString = "select c from UserToken c where c.token = :tokenSearch";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, UserToken.class);
		query.setParameter("tokenSearch", token);
		UserToken userToken = (UserToken) query.getSingleResult();
		if(userToken == null) {
			System.out.println("Invalid token");
		}
		else {
			User user = userToken.getUser();
			String oldPassword = user.getPasswordHash();
			String newHashedPwd = hashUtil.stringHashing(newPassword);
			if(oldPassword.equals(newHashedPwd)) {
				System.out.println("password already been used. plase enter new one !!");
			}
			else {
				user.setPasswordHash(newHashedPwd);
				user.setLastPasswordUpdatedAt(new Date(System.currentTimeMillis()));
				updateUser(user);
				System.out.println("Your password has been updated successfully !!");
				
			}			
			
		}		
		
	}



}
