package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.CartItemNotFoundException;
import com.shopping.model.Cart;
import com.shopping.model.CartItem;
import com.shopping.util.ApplicationUtil;

public class CartItemService {
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
	
	public CartItem addCartItem(CartItem cartItem) {
		System.out.println(cartItem);
		beginTransaction();
		session.persist(cartItem);
		commitTransaction();
		System.out.println("CartItem Added :: "+cartItem);
		return cartItem;
	}
	
	public CartItem getByCartItemId(int id) throws CartItemNotFoundException {
		session = sessionFactory.openSession();
		CartItem cartItem=null;
		cartItem = session.get(CartItem.class, id);
		System.out.println("getByCartItemId :: " +cartItem);
		session.close();
		if(cartItem==null) {
			throw new CartItemNotFoundException("Cart Item not found with id:" +id);
		}
		return cartItem;
	}
	
	public List<CartItem> getAllCartItems() {
		String queryString = "select c from CartItem c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, CartItem.class);
		List<CartItem> cartItems = query.list();
		if (cartItems.size() > 0) {
			System.out.println("List Of CartItems :: "+ cartItems);
			return cartItems;
		}
		session.close();
		return null;
	}
	
	public void deleteCartItemById(int id) {
		CartItem cartItem = new CartItem();
		cartItem.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(cartItem);
		commitTransaction();		
		System.out.println("CartItem Deleted By id : "+id);
	}
	
	public CartItem updateCartItem(CartItem cartItem) {
//		CartItem.setMetaTitle(CartItem.getTitle() + " " + CartItem.getSummary());
		beginTransaction();
		session.merge(cartItem);
		commitTransaction();
		System.out.println("Updated CartItem : "+ cartItem);
		return cartItem;
	}
	
	@SuppressWarnings("unchecked")
	public List<CartItem> getListOfCartItemsByCartId(int cartId) {
		session = sessionFactory.openSession();
		String queryString = "select c from CartItem c where c.cartId = :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, CartItem.class);
		query.setParameter("search", cartId);
		List<CartItem> CartItem = query.list();
		session.close();
		if (CartItem.size() > 0) {
			System.out.println("getListOfCartItemsByMetaTitle :: "+CartItem);
			return CartItem;
		}
		return null;
	}

}
