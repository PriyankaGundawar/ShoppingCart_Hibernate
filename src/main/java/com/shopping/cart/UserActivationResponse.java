package com.shopping.cart;

import com.shopping.Exceptions.UserNotFoundException;
import com.shopping.model.User;
import com.shopping.service.UserService;

public class UserActivationResponse {

	public static void main(String[] args) {
		
		UserService userService = new UserService();
		userService.connect();
		User user = null;
		try {
			user = userService.getByUserId(50);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tokenFromDB = user.getToken();
		
		String tokenFromUser = "maa9jHttmXXZoRs";
		if(tokenFromDB.contentEquals(tokenFromUser)) {
			System.out.println("User has been activated");
		}
			else {
				System.out.println("Wrong user activation");
			}
			
		}		
		

	}


