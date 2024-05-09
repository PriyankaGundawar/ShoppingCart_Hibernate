package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.CategoryNotFoundException;
import com.shopping.Exceptions.IllegalCategoryDeletionException;
import com.shopping.model.Category;
import com.shopping.model.Product;
import com.shopping.util.ApplicationUtil;

public class CategoryService {
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
	
	public Category addCategory(Category category) {
//		category.setMetaTitle(category.getTitle() + " " + category.getSummary());
		System.out.println(category);
		beginTransaction();
		session.persist(category);
		commitTransaction();
		System.out.println("Category Added :: "+category);

		return category;
	}
	
	public Category getByCategoryId(int id) throws CategoryNotFoundException {
		session = sessionFactory.openSession();
		Category category=null;
		category = session.get(Category.class, id);
		System.out.println("getByCategoryId :: " +category);
		session.close();
		if(category==null) {
			throw new CategoryNotFoundException("Category not found with id : "+ id);
		}
		return category;

	}
	
	public List<Category> getAllCategorys() {
		String queryString = "select c from Category c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, Category.class);
		List<Category> categorys = query.list();
		if (categorys.size() > 0) {
			System.out.println("List Of Categorys :: "+ categorys);
			return categorys;
		}
		session.close();

		return null;
	}
	
//	public void deleteCategoryById(int id) {
//		Category category = new Category();
//		category.setId(id);
//		session = sessionFactory.openSession();
//		beginTransaction();
//		session.remove(category);
//		commitTransaction();		
//		System.out.println("Category Deleted By id : "+id);
//	}
	
	public Category updateCategory(Category category) {
//		category.setMetaTitle(category.getTitle() + " " + category.getSummary());
		beginTransaction();
		session.merge(category);
		commitTransaction();
		System.out.println("Updated Category : "+ category);
		return category;

	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getListOfCategorysByMetaTitle(String metaTitle) {
		session = sessionFactory.openSession();
		String queryString = "select c from Category c where c.metaTitle like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, Category.class);
		query.setParameter("search", "%" + metaTitle + "%", String.class);
		List<Category> categorys = query.list();
		session.close();
		if (categorys.size() > 0) {
			System.out.println("getListOfCategorysByMetaTitle :: "+categorys);
			return categorys;
		}

		return null;
	}
	
	public void deleteCategoryById(int id) throws IllegalCategoryDeletionException, CategoryNotFoundException {
		session = sessionFactory.openSession();
		List<Product> product = getByCategoryId(id).getProducts();
		System.out.println(product.size());
		if(product.size()>0) {
			throw new IllegalCategoryDeletionException(id +"Category assigned to multiple products");
		}
		else {
			Category category = new Category();
			category.setId(id);
			beginTransaction();
			session.remove(category);
			commitTransaction();			
		}
	}

}
