package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.ProductReviewNotFoundException;
import com.shopping.model.ProductReview;
import com.shopping.util.ApplicationUtil;

public class ProductReviewService {
	
	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;

	public void connect() {
//		config = new Configuration().configure().addAnnotatedClass(ProductReview.class).addAnnotatedClass(Product.class).addAnnotatedClass(ProductMeta.class);
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
	
	public ProductReview addProductReview(ProductReview productReview) {
//		ProductReview.setMetaTitle(productReview.getTitle() + " " + productReview.getSummary());
		System.out.println(productReview);
		beginTransaction();
		session.persist(productReview);
		commitTransaction();
		System.out.println("ProductReview Added :: "+productReview);
		return productReview;
	}
	
	public ProductReview getByProductReviewId(int id) throws ProductReviewNotFoundException {
		session = sessionFactory.openSession();
		ProductReview productReview=null;
		productReview = session.get(ProductReview.class, id);
		System.out.println("getByProductReviewId :: " +productReview);
		session.close();
		if(productReview==null) {
			throw new ProductReviewNotFoundException("PRoduct review not found with id : "+id);
		}
		return productReview;
	}
	
	public List<ProductReview> getAllProductReviews() {
		String queryString = "select c from ProductReview c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, ProductReview.class);
		List<ProductReview> productReviews = query.list();
		if (productReviews.size() > 0) {
			System.out.println("List Of ProductReviews :: "+ productReviews);
			return productReviews;
		}
		session.close();
		return null;
	}
	
	public void deleteProductReviewById(int id) {
		ProductReview productReview = new ProductReview();
		productReview.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(productReview);
		commitTransaction();		
		System.out.println("ProductReview Deleted By id : "+id);
	}
	
	public ProductReview updateProductReview(ProductReview productReview) {
//		ProductReview.setMetaTitle(ProductReview.getTitle() + " " + ProductReview.getSummary());
		beginTransaction();
		session.merge(productReview);
		commitTransaction();
		System.out.println("Updated ProductReview : "+ productReview);
		return productReview;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductReview> getListOfProductReviewsByRating(int rating) {
		session = sessionFactory.openSession();
		String queryString = "select c from ProductReview c where c.rating = :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, ProductReview.class);
		query.setParameter("search", rating);
		List<ProductReview> productReviews = query.list();
		session.close();
		if (productReviews.size() > 0) {
			System.out.println("getListOfProductReviewsByMetaTitle :: "+productReviews);
			return productReviews;
		}
		return null;
	}

}
