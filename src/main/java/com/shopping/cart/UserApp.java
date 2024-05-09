package com.shopping.cart;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Date;

import com.shopping.Exceptions.InvalidUserOrPasswordEntered;
import com.shopping.Exceptions.InvalidUserTokenException;
import com.shopping.Exceptions.UserNotFoundException;
import com.shopping.model.Cart;
import com.shopping.model.User;
import com.shopping.service.CartService;
import com.shopping.service.UserService;
import com.shopping.util.HashingUtil;
import com.shopping.util.ApplicationConstants.UserStatus;

public class UserApp {

	public static void main(String[] args) throws URISyntaxException {
		
		User user = createUser();
		
		UserService us = new UserService();		
		us.connect();		
//		User addedUser = us.persistUser(user);
		
//		User userFromDb = null;
//		try {
//			userFromDb = us.getUserFromToken("maa9jHttmXXZoR");
//		} catch (InvalidUserTokenException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(userFromDb);
		
		
//		try {
//			us.userLogin("priyadikondawar@gmail.com", "Firstlast#123456");
//		} catch (InvalidUserOrPasswordEntered e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		
//		try {
//			us.userPasswordChange("priyadikondawar@gmail.com", "Firstlast#123456", "Firstlast#1234567");
//		} catch (InvalidUserOrPasswordEntered e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		us.forgotPassword("priyadikondawar@gmail.com");
		
		us.tokenResponseForgotPassword("OhpqPAs58Xc5iD2tnFp");
//		
//		Cart cart = CartApp.createCart();
//		cart.setUser(addedUser);
//		user.getCart().add(cart);
//		
//		us.connect();
//		us.updateUser(addedUser);
		
//		CartService cartService = new CartService();
//		cartService.connect();
//		cartService.addCart(cart);
		
//		System.out.println("User added to cart");
		
		
		
		
		
//		try {
//			User u = us.getByUserId(111);
//		} catch (UserNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		u.setMiddleName("Sai Ram");
//		us.updateUser(u);
//		
//		us.getAllUsers();
//		
//		us.getListOfUsersByLastName("D");
//		
//		us.deleteUserById(2);
		

	}

	public static User createUser() {
		User user = new User();		
		user.setFirstName("Riyansh");
		user.setMiddleName("S");
		user.setLastName("konda");
		user.setMobile("123-456-7890");
		user.setEmail("priyadikondawar@gmail.com");
		user.setPasswordHash("Firstlast#123456");  
		user.setAdmin(0);
		user.setVendor(0);
		user.setIntro(0);
		user.setLastLogin(new Date(System.currentTimeMillis()));
		user.setProfile("profile");
		user.setRegisteredAt(new Date(System.currentTimeMillis()));
		user.setStatus(UserStatus.PendingActive.ordinal());
		return user;
	}

}
