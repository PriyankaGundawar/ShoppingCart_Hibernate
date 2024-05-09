package com.shopping.cart;

import java.sql.Date;

import com.shopping.Exceptions.OrderItemNotFoundException;
import com.shopping.model.CartItem;
import com.shopping.model.OrderItem;
import com.shopping.model.Product;
import com.shopping.service.OrderItemService;
import com.shopping.service.ProductService;

public class OrderItemApp {

	public static void main(String[] args) {
		

	
		
		OrderItemService ois = new OrderItemService();		
		ois.connect();
//		OrderItem addedOrder = ois.addOrderItem(oitem);
		
		try {
			OrderItem oiFromDb = ois.getByOrderItemId(2);
		} catch (OrderItemNotFoundException e) {
			System.out.println("error :: "+e.getMessage());	
			e.printStackTrace();
		}
		
//		oiFromDb.setContent("Content5");
//		ois.updateOrderItem(oiFromDb);
//		
//		ois.getAllOrderItems();
//		
//		ois.getListOfOrderItemsByOrderId(4);
////		
//		ois.deleteOrderItemById(3);
		
		

	}

	public static OrderItem createOredrItem(CartItem cartItem) {
		OrderItem oitem = new OrderItem();		
		oitem.setProduct(cartItem.getProduct());
//		oitem.setOrderId(4);
		oitem.setSku("sku4");
		oitem.setPrice(cartItem.getPrice());
		oitem.setDiscount(cartItem.getDiscount());
		oitem.setQuantity(cartItem.getQuantity());
		oitem.setCreatedAt(new Date(System.currentTimeMillis()));
		return oitem;
	}

}
