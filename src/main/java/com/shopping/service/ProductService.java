package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.ProductNotFoundException;
import com.shopping.model.Product;
import com.shopping.util.ApplicationUtil;

public class ProductService {
	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;

	public void connect() {
//		config = new Configuration().configure().addAnnotatedClass(Product.class).addAnnotatedClass(ProductMeta.class).addAnnotatedClass(ProductReview.class);
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

	public Product addProduct(Product product) {
		product.setMetaTitle(product.getTitle() + " " + product.getSummary());
		System.out.println(product);
		beginTransaction();
		session.persist(product);
		commitTransaction();
		System.out.println("Product Added :: " + product);

		return product;
	}

//	public Product getByProductId(int id) {
//		session = sessionFactory.openSession();
//		Product product = session.get(Product.class, id);
//		System.out.println("getByProductId :: " + product);
//		session.close();
//		return product;
//
//	}

	public List<Product> getAllProducts() {
		String queryString = "select p from Product p";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, Product.class);
		List<Product> products = query.list();
		if (products.size() > 0) {
			System.out.println("List Of Products :: " + products);
			return products;
		}
		session.close();

		return null;
	}

	public void deleteProductById(int id) {
		Product product = new Product();
		product.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(product);
		commitTransaction();

		System.out.println("Product Deleted By id : " + id);
	}

	public Product updateProduct(Product product) {
		product.setMetaTitle(product.getTitle() + " " + product.getSummary());
		beginTransaction();
		session.merge(product);
		commitTransaction();
		System.out.println("Updated Product : " + product);
		return product;

	}

	@SuppressWarnings("unchecked")
	public List<Product> getListOfProductsByMetaTitle(String metaTitle) {
		session = sessionFactory.openSession();
		String queryString = "select p from Product p where p.title like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, Product.class);
		query.setParameter("search", "%" + metaTitle + "%", String.class);
		List<Product> products = query.list();
		session.close();
		if (products.size() > 0) {
			System.out.println("getListOfProductsByMetaTitle :: " + products);
			return products;
		}

		return null;
	}

	public Product getByProductId(int id) throws ProductNotFoundException {
		session = sessionFactory.openSession();
		Product product = null;
		System.out.println(product);
		product = session.get(Product.class, id);
		session.close();
		System.out.println(product);
		if (product == null)
			throw new ProductNotFoundException("product not found with id:" + id);

		return product;
	}
}
