package com.shopping.cart;

import java.util.Date;
import java.util.List;

import com.shopping.Exceptions.CartItemNotFoundException;
import com.shopping.model.CartItem;
import com.shopping.model.Product;
import com.shopping.service.CartItemService;
import com.shopping.service.ProductService;

public class CartItemApp {

	public static void main(String[] args) {
		
		CartItemService service = new CartItemService();
		service.connect();
		CartItem cartItem = null;
		try {
			cartItem = service.getByCartItemId(3);
		} catch (CartItemNotFoundException e) {
			System.out.println("error :: "+e.getMessage());	
		}
		
		
		
//		CartItem cartItem = createCartItem();
		

//		
//		CartItem ciFromdb = cis.getByCartItemId(addedci.getCartId());
//		ciFromdb.setContent("Content1");
//		cis.updateCartItem(ciFromdb);
//		
//		List<CartItem> ciList = cis.getAllCartItems();
//		
//		cis.getByCartItemId(ci.getCartId());
//		
//		cis.deleteCartItemById(addedci.getCartId());
//
	}

	public static CartItem createCartItem(Product product) { //set product parameter while creating
		CartItem ci = new CartItem();		
//		ci.setProductId(103);
//		ci.setCartId(3);
		ci.setSku("sku3");
		ci.setPrice(product.getPrice());
		ci.setDiscount(product.getDiscount());
		ci.setProduct(product);
//		ci.setQuantity(3);
		ci.setActive(1);
		ci.setCreatedAt(new Date(System.currentTimeMillis()));
		return ci;
	}

	}
