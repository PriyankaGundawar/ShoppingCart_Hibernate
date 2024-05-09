package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.OrderItemNotFoundException;
import com.shopping.model.OrderItem;
import com.shopping.util.ApplicationUtil;

public class OrderItemService {
	
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
	
	public OrderItem addOrderItem(OrderItem orderItem) {
//		OrderItem.setMetaTitle(OrderItem.getTitle() + " " + OrderItem.getSummary());
		System.out.println(orderItem);
		beginTransaction();
		session.persist(orderItem);
		commitTransaction();
		System.out.println("OrderItem Added :: "+orderItem);
		return orderItem;
	}
	
	public OrderItem getByOrderItemId(int id) throws OrderItemNotFoundException {
		session = sessionFactory.openSession();
		OrderItem orderItem = null;
		orderItem = session.get(OrderItem.class, id);
		System.out.println("getByOrderItemId :: " +orderItem);
		session.close();
		if(orderItem==null) {
			throw new OrderItemNotFoundException("OrderItem not found with id : "+id);
		}
		return orderItem;
	}
	
	public List<OrderItem> getAllOrderItems() {
		String queryString = "select c from OrderItem c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, OrderItem.class);
		List<OrderItem> orderItem = query.list();
		if (orderItem.size() > 0) {
			System.out.println("List Of OrderItems :: "+ orderItem);
			return orderItem;
		}
		session.close();
		return null;
	}
	
	public void deleteOrderItemById(int id) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(orderItem);
		commitTransaction();		
		System.out.println("OrderItem Deleted By id : "+id);
	}
	
	public OrderItem updateOrderItem(OrderItem orderItem) {
//		OrderItem.setMetaTitle(OrderItem.getTitle() + " " + OrderItem.getSummary());
		beginTransaction();
		session.merge(orderItem);
		commitTransaction();
		System.out.println("Updated OrderItem : "+ orderItem);
		return orderItem;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderItem> getListOfOrderItemsByOrderId(int orderId) {
		session = sessionFactory.openSession();
		String queryString = "select c from OrderItem c where c.orderId = :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, OrderItem.class);
		query.setParameter("search",orderId);
		List<OrderItem> orderItem = query.list();
		session.close();
		if (orderItem.size() > 0) {
			System.out.println("getListOfOrderItemsByMetaTitle :: "+orderItem);
			return orderItem;
		}
		return null;
	}

}
