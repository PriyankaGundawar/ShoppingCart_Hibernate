package com.shopping.cart;

import java.util.Date;
import java.util.List;

import com.shopping.Exceptions.CartNotFoundException;
import com.shopping.Exceptions.ProductNotFoundException;
import com.shopping.model.Address;
import com.shopping.model.Cart;
import com.shopping.model.CartItem;
import com.shopping.model.Product;
import com.shopping.model.User;
import com.shopping.service.CartService;
import com.shopping.service.ProductService;
import com.shopping.service.UserService;

public class CartApp {

	public static void main(String[] args) {
		
//		Cart cart = createCart();
		
//		CartService cs = new CartService();		
//		cs.connect();
//		Cart addedCart = cs.addCart(cart);
		
//		Cart c = cs.getByCartId(addedCart.getId());
//		c.setFirstName("Chinna");
//		c.setLastName("P");
//		c.setMobile("98765");
//		cs.updateCart(c);
//		
//		List<Cart> clist = cs.getAllCarts();
//		cs.getListOfCartsByMobile(c.getMobile());
//		
//		cs.deleteCartById(addedCart.getId());	
		
		

//		Cart cartToSave = CartApp.createCart();
//		
//		UserService us = new UserService();
//		us.connect();
//		User userFromDb = us.getByUserId(2);
//		
//		cartToSave.setUser(userFromDb);
//		
//		CartService cs = new CartService();		
//		cs.connect();
//		Cart addedCart = cs.addCart(cartToSave);	
		
		CartService cs = new CartService();
		cs.connect();
		Cart cartFromDB = null;
		try {
			cartFromDB = cs.getByCartId(7);
		} catch (CartNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ProductService productService = new ProductService();
		productService.connect();
		Product product1 = null;
		try {
			product1 = productService.getByProductId(37);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Product product2 = null;
		try {
			product2 = productService.getByProductId(36);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		CartItem cartItem1 = CartItemApp.createCartItem(product1);
		cartItem1.setCart(cartFromDB);
		cartItem1.setQuantity(2);
		
		CartItem cartItem2 = CartItemApp.createCartItem(product2);
		cartItem2.setCart(cartFromDB);
		cartItem2.setQuantity(2);
		
		List<CartItem> cartItemsList = cartFromDB.getCartItems(); //.add(cartItem);
		cartItemsList.add(cartItem1);
		cartItemsList.add(cartItem2);
		
		cartFromDB.setCartItems(cartItemsList);
		
		UserService userService = new UserService();
		userService.connect();
		
		User user = cartFromDB.getUser();
		
		
		Address address = null;
		String updatedAddressType =  "Office";
		List<Address> addresses = user.getAddress();
		for(Address a : addresses) {
			if(a.getAddressId()==4) {
				address = a;
			}
		}
		
		cartFromDB.setLine1(address.getAddressLine1());
		cartFromDB.setLine2(address.getAddressLine2());
		cartFromDB.setCity(address.getCity());
		cartFromDB.setProvince(address.getState());
		cartFromDB.setCountry(address.getCountry());
		cs.connect();
		cs.updateCart(cartFromDB);	
	}

	public static Cart createCart(User user) { //pass user parameter to create cart
		Address address = null;
		String defaultAddressType =  "Home";
		List<Address> addresses = user.getAddress();
		for(Address a : addresses) {
			if(a.getAddressType() == defaultAddressType) {
				address = a;
			}
		}
		Cart cart = new Cart();	
//		cart.setUserId(5);
		cart.setSessionId(5);
		cart.setToken("token12345");
		cart.setStatus(1);
		cart.setFirstName(user.getFirstName());
		cart.setLastName(user.getLastName());
		cart.setMobile(user.getMobile());
		cart.setEmail(user.getEmail());
		cart.setUser(user);
		cart.setLine1(address.getAddressLine1());
		cart.setLine2(address.getAddressLine2());
		cart.setCity(address.getCity());
		cart.setProvince(address.getState());
		cart.setCountry(address.getCountry());
		cart.setCreatedAt(new Date(System.currentTimeMillis()));
		return cart;
	}
	


}
