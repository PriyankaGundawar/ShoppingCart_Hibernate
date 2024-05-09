package com.shopping.cart;

import com.shopping.Exceptions.AddressNotFoundException;
import com.shopping.Exceptions.UserNotFoundException;
import com.shopping.model.Address;
import com.shopping.model.User;
import com.shopping.service.AddressService;
import com.shopping.service.UserService;

public class AddressApp {

	public static void main(String[] args) {
		
		UserService userService = new UserService();
		userService.connect();
		User user = null;
		try {
			user = userService.getByUserId(8);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Address address = createAddress();
		address.setUser(user);
		
		AddressService addressService = new AddressService();
		addressService.connect();
		addressService.persistAddress(address);
		
//		try {
//			addressService.getByAddressId(2);
//		} catch (AddressNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		addressService.getAllAddresss();
//		addressService.getListOfAddresssByCity("KRNL");
//		addressService.deleteAddressById(2);
		

	}

	public static Address createAddress() {
		Address address = new Address();
//		address.setUserId(102);
		address.setAddressType("shipping");
		address.setAddressLine1("aLine1");
		address.setAddressLine2("aLine2");		
		address.setCity("KRNL");
		address.setState("AP");
		address.setCountry("Ind");
		address.setPostalCode("142678");
		return address;
	}

}
