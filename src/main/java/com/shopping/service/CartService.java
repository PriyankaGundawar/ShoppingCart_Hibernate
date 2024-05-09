package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.CartNotFoundException;
import com.shopping.model.Cart;
import com.shopping.model.CartItem;
import com.shopping.util.ApplicationUtil;

public class CartService {
	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;

	public void connect() {
//		config = new Configuration().configure().addAnnotatedClass(Cart.class);
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
	
	public Cart addCart(Cart cart) {
//		Cart.setMetaTitle(Cart.getTitle() + " " + Cart.getSummary());
		System.out.println(cart);
		beginTransaction();
		session.persist(cart);
		commitTransaction();
		System.out.println("Cart Added :: "+cart);
		return cart;
	}
	
	public Cart getByCartId(int id) throws CartNotFoundException {
		session = sessionFactory.openSession();
		Cart cart=null;
		cart = session.get(Cart.class, id);
		System.out.println("getByCartId :: " +cart);
		session.close();
		if(cart==null) {
			throw new CartNotFoundException("Cart not found with id: "+id);
		}
		return cart;
	}
	
	public List<Cart> getAllCarts() {
		String queryString = "select c from Cart c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, Cart.class);
		List<Cart> carts = query.list();
		if (carts.size() > 0) {
			System.out.println("List Of Carts :: "+ carts);
			return carts;
		}
		session.close();
		return null;
	}
	
	public void deleteCartById(int id) {
		Cart cart = new Cart();
		cart.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(cart);
		commitTransaction();		
		System.out.println("Cart Deleted By id : "+id);
	}
	
	public Cart updateCart(Cart cart) {
//		Cart.setMetaTitle(Cart.getTitle() + " " + Cart.getSummary());
		beginTransaction();
		List<CartItem> cartItemsList = cart.getCartItems(); //calculate price based on quantity
		float price =0;
		int quantity =0;
		float totalAmount=0;
		float productTotal=0;
		System.out.println("cartItemsList::" + cartItemsList.size());
		for(CartItem c : cartItemsList) {
			price = c.getPrice();
			quantity = c.getQuantity();
			productTotal = price*quantity;
			totalAmount = totalAmount+productTotal;	
			System.out.println("quantity::" + quantity);
			System.out.println("price::" + price);
			System.out.println("productTotal::" + productTotal);
			System.out.println("totalAmount::" +totalAmount);
		}
		
		System.out.println(totalAmount);
		cart.setCartTotal(totalAmount);
		session.merge(cart);
		commitTransaction();
		System.out.println("Updated Cart : "+ cart);
		return cart;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cart> getListOfCartsByMobile(String mobile) {
		session = sessionFactory.openSession();
		String queryString = "select c from Cart c where c.mobile like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, Cart.class);
		query.setParameter("search", "%" + mobile + "%", String.class);
		List<Cart> carts = query.list();
		session.close();
		if (carts.size() > 0) {
			System.out.println("getListOfCartsByMetaTitle :: "+carts);
			return carts;
		}
		return null;
	}

}
