package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.TagNotFoundException;
import com.shopping.model.Tag;
import com.shopping.util.ApplicationUtil;

public class TagService {
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
	
	public Tag addTag(Tag tag) {
		System.out.println(tag);
		beginTransaction();
		session.persist(tag);
		commitTransaction();
		System.out.println("Tag Added :: "+tag);
		return tag;
	}
	
	public Tag getByTagId(int id) throws TagNotFoundException {
		session = sessionFactory.openSession();
		Tag tag=null;
		tag = session.get(Tag.class, id);
		System.out.println("getByTagId :: " +tag);
		session.close();
		if(tag ==null) {
			throw new TagNotFoundException("Tag not found with id : "+id);
		}
		return tag;
	}
	
	public List<Tag> getAllTags() {
		String queryString = "select c from Tag c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, Tag.class);
		List<Tag> tags = query.list();
		if (tags.size() > 0) {
			System.out.println("List Of Tags :: "+ tags);
			return tags;
		}
		session.close();
		return null;
	}
	
	public void deleteTagById(int id) {
		Tag tag = new Tag();
		tag.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(tag);
		commitTransaction();		
		System.out.println("Tag Deleted By id : "+id);
	}
	
	public Tag updateTag(Tag tag) {
//		Tag.setMetaTitle(Tag.getTitle() + " " + Tag.getSummary());
		beginTransaction();
		session.merge(tag);
		commitTransaction();
		System.out.println("Updated Tag : "+ tag);
		return tag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tag> getListOfTagsByMetaTitle(String metaTitle) {
		session = sessionFactory.openSession();
		String queryString = "select c from Tag c where c.metaTitle like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, Tag.class);
		query.setParameter("search", "%" + metaTitle + "%", String.class);
		List<Tag> tags = query.list();
		session.close();
		if (tags.size() > 0) {
			System.out.println("getListOfTagsByMetaTitle :: "+tags);
			return tags;
		}
		return null;
	}

}
