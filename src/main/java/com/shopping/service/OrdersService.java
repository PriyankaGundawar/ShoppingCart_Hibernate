package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.OrdersNotFoundException;
import com.shopping.model.Category;
import com.shopping.model.Orders;
import com.shopping.util.ApplicationUtil;

public class OrdersService {
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
	
	public Orders persistOrders(Orders orders) {
		System.out.println("inside add" + orders);
		beginTransaction();
		float cartsubTotal = orders.getSubTotal();
		float tax = orders.getTax();
		float shipping = orders.getShipping();		
		float itemDiscount = orders.getItemDiscount();
		float GrandTotal = 0;
		float promoCodeDiscount = orders.getDiscount();
		
		float total = cartsubTotal + tax + shipping + itemDiscount;
		System.out.println(total);
		
		orders.setTotal(total);
		String promoCode = orders.getPromo();
		if(promoCode.isEmpty()) {
			GrandTotal = total;
			orders.setGrandTotal(GrandTotal);	
			System.out.println(GrandTotal + " if promo empty");
		}
		else {
			GrandTotal = total - promoCodeDiscount;
			orders.setGrandTotal(GrandTotal);
			System.out.println(GrandTotal + " if promo not empty");
		}
			
		
		session.persist(orders);
		commitTransaction();
		System.out.println("Orders Added :: "+orders);
		return orders;
	}
	
	public Orders getByOrdersId(int id) throws OrdersNotFoundException {
		session = sessionFactory.openSession();
		Orders orders=null;
		orders = session.get(Orders.class, id);
		System.out.println("getByOrdersId :: " +orders);
		session.close();
		if(orders==null) {
			throw new OrdersNotFoundException("Order not fount with id : "+id);
		}
		return orders;
	}
	
	public List<Orders> getAllOrderss() {
		String queryString = "select c from Orders c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, Orders.class);
		List<Orders> orders = query.list();
		if (orders.size() > 0) {
			System.out.println("List Of Orderss :: "+ orders);
			return orders;
		}
		session.close();
		return null;
	}
	
	public void deleteOrdersById(int id) {
		Orders orders = new Orders();
		orders.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(orders);
		commitTransaction();		
		System.out.println("Orders Deleted By id : "+id);
	}
	
	public Orders updateOrders(Orders orders) {
//		Orders.setMetaTitle(Orders.getTitle() + " " + Orders.getSummary());
		beginTransaction();
		session.merge(orders);
		commitTransaction();
		System.out.println("Updated Orders : "+ orders);
		return orders;
	}
	
	@SuppressWarnings("unchecked")
	public List<Orders> getListOfOrderssByFirstName(String firstName) {
		session = sessionFactory.openSession();
		String queryString = "select c from Orders c where c.firstName like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, Orders.class);
		query.setParameter("search", "%" + firstName + "%", String.class);
		List<Orders> orders = query.list();
		session.close();
		if (orders.size() > 0) {
			System.out.println("getListOfOrderssByMetaTitle :: "+orders);
			return orders;
		}
		return null;
	}

}
