package com.shopping.cart;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.shopping.Exceptions.CartItemNotFoundException;
import com.shopping.Exceptions.CartNotFoundException;
import com.shopping.Exceptions.OrdersNotFoundException;
import com.shopping.Exceptions.ProductNotFoundException;
import com.shopping.Exceptions.UserNotFoundException;
import com.shopping.model.Cart;
import com.shopping.model.CartItem;
import com.shopping.model.OrderItem;
import com.shopping.model.Orders;
import com.shopping.model.PaymentTransaction;
import com.shopping.model.Product;
import com.shopping.model.User;
import com.shopping.service.CartItemService;
import com.shopping.service.CartService;
import com.shopping.service.OrdersService;
import com.shopping.service.ProductService;
import com.shopping.service.UserService;

public class OrdersApp {

	public static void main(String[] args) {
		
//		UserService userService = new UserService();
//		userService.connect();
//		User user = null;
//		try {
//			user = userService.getByUserId(4);
//		} catch (UserNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
//		ProductService productService = new ProductService();
//		productService.connect();
//		Product product = null;
//		try {
//			product = productService.getByProductId(21);
//		} catch (ProductNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		CartService cartService = new CartService();
		cartService.connect();
		Cart cart = null;
		try {
			cart = cartService.getByCartId(7);
		} catch (CartNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		Orders order = createOrder(cart);
		
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		
		List<CartItem> cartItemsList = cart.getCartItems();
		
		for(CartItem ci: cartItemsList) {			
			
			OrderItem orderItem = OrderItemApp.createOredrItem(ci);
			
			orderItem.setOrders(order);
			
			System.out.println(ci);
			orderItemList.add(orderItem);
		}
		
		order.setOrderItem(orderItemList);
		
		
		User user = cart.getUser();		
		order.setUser(user);
		
		
		OrdersService orderService = new OrdersService();
		orderService.connect();
		orderService.persistOrders(order);
		
		

		
//		PaymentTransaction payment = PaymentTransactionApp.createPayment();
//		order.getPaymentTransaction().add(payment);
//		payment.setOrders(order);
		
		
		
//		payment.setUserId(user.getId()); //not current one
//		payment.setUser(user);
//		
//		order.setUser(user);
		
		OrdersService orderService2 = new OrdersService();
		orderService.connect();	
//		orderService.addOrders(order);		
		
		try {
			Orders o = orderService.getByOrdersId(2);
		} catch (OrdersNotFoundException e) {
			System.out.println("error :: "+e.getMessage());	
//			e.printStackTrace();
		}
//		o.setEmail("email2.com");
//		
//		orderService.updateOrders(o);		
//		
//		orderService.getAllOrderss();
//		
//		orderService.getListOfOrderssByFirstName("priya");
//		
//		orderService.deleteOrdersById(3);
		
		

	}

	public static Orders createOrder(Cart cart) {
		Orders order = new Orders();		
		order.setSessionID("ss2");
		order.setToken("token2");
		order.setStatus(3);
		order.setSubTotal(cart.getCartTotal());
		order.setItemDiscount(10);
		order.setTax(5);
		order.setShipping(30);
//		order.setTotal(order.getTotal());		
		order.setPromo("dis10");
		order.setDiscount(10);
//		order.setGrandTotal(order.getGrandTotal());
		order.setFirstName(cart.getFirstName());
		order.setLastName(cart.getLastName());
		order.setMiddleName(cart.getMiddleName());
		order.setLine1(cart.getLine1());
		order.setLine2(cart.getLine2());
		order.setCity(cart.getCity());
		order.setCountry(cart.getCountry());
		order.setMobile(cart.getMobile());
		order.setContent("qqqqqqqq");
		order.setCreatedAt(new Date(System.currentTimeMillis()));
		order.setProvince(cart.getProvince());
		order.setUpdatedAt(new Date(System.currentTimeMillis()));
//		order.setUserId(123);
		order.setEmail("email1.com");
		return order;
	}

}
