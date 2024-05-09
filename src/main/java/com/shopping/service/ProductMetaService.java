package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.ProductMetaNotFoundException;
import com.shopping.model.ProductMeta;
import com.shopping.util.ApplicationUtil;

public class ProductMetaService {

	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;

	public void connect() {
//		config = new Configuration().configure().addAnnotatedClass(ProductMeta.class).addAnnotatedClass(Product.class).addAnnotatedClass(ProductReview.class);
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
	
	public ProductMeta addProductMeta(ProductMeta productMeta) {
//		ProductMeta.setMetaTitle(productMeta.getTitle() + " " + productMeta.getSummary());
		System.out.println(productMeta);
		beginTransaction();
		session.persist(productMeta);
		commitTransaction();
		System.out.println("ProductMeta Added :: "+productMeta);
		return productMeta;
	}
	
	public ProductMeta getByProductMetaId(int id) throws ProductMetaNotFoundException {
		session = sessionFactory.openSession();
		ProductMeta productMeta = null;
		productMeta = session.get(ProductMeta.class, id);
		System.out.println("getByProductMetaId :: " +productMeta);
		session.close();
		if(productMeta==null) {
			throw new ProductMetaNotFoundException("Product meta not found with id : "+id);
		}
		return productMeta;
	}
	
	public List<ProductMeta> getAllProductMetas() {
		String queryString = "select pm from ProductMeta pm";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, ProductMeta.class);
		List<ProductMeta> productMetas = query.list();
		if (productMetas.size() > 0) {
			System.out.println("List Of ProductMetas :: "+ productMetas);
			return productMetas;
		}
		session.close();
		return null;
	}
	
	public void deleteProductMetaById(int id) {
		ProductMeta productMeta = new ProductMeta();
		productMeta.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(productMeta);
		commitTransaction();		
		System.out.println("ProductMeta Deleted By id : "+id);
	}
	
	public ProductMeta updateProductMeta(ProductMeta productMeta) {
//		ProductMeta.setMetaTitle(productMeta.getTitle() + " " + productMeta.getSummary());
		beginTransaction();
		session.merge(productMeta);
		commitTransaction();
		System.out.println("Updated ProductMeta : "+ productMeta);
		return productMeta;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductMeta> getListOfProductMetasByMetaTitle(String pkey) {
		session = sessionFactory.openSession();
		String queryString = "select pm from ProductMeta pm where pm.pkey like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, ProductMeta.class);
		query.setParameter("search", "%" + pkey + "%", String.class);
		List<ProductMeta> productMetas = query.list();
		session.close();
		if (productMetas.size() > 0) {
			System.out.println("getListOfProductMetasByKey :: "+productMetas);
			return productMetas;
		}

		return null;
	}
}
