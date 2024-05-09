package com.shopping.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;
import com.shopping.Exceptions.AddressNotFoundException;
import com.shopping.model.Address;
import com.shopping.util.ApplicationUtil;

public class AddressService {
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
	
	public Address persistAddress(Address address) {
		System.out.println(address);
		beginTransaction();
		session.persist(address);
		commitTransaction();
		System.out.println("Address Added :: "+address);
		return address;
	}
	
	public Address getByAddressId(int id) throws AddressNotFoundException {
		session = sessionFactory.openSession();
		Address address=null;
		address = session.get(Address.class, id);
		System.out.println("getByAddressId :: " +address);
		session.close();
		if(address ==null) {
			throw new AddressNotFoundException("Address not found with id : "+id);
		}
		return address;
	}
	
	public List<Address> getAllAddresss() {
		String queryString = "select c from Address c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, Address.class);
		List<Address> addresses = query.list();
		if (addresses.size() > 0) {
			System.out.println("List Of Addresss :: "+ addresses);
			return addresses;
		}
		session.close();
		return null;
	}
	
	public void deleteAddressById(int id) {
		Address address = new Address();
		address.setAddressId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(address);
		commitTransaction();		
		System.out.println("Address Deleted By id : "+id);
	}
	
	public Address updateAddress(Address address) {
		beginTransaction();
		session.merge(address);
		commitTransaction();
		System.out.println("Updated Address : "+ address);
		return address;
	}
	
	@SuppressWarnings("unchecked")
	public List<Address> getListOfAddresssByCity(String city) {
		session = sessionFactory.openSession();
		String queryString = "select c from Address c where c.city like :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, Address.class);
		query.setParameter("search", "%" + city + "%", String.class);
		List<Address> addressess = query.list();
		session.close();
		if (addressess.size() > 0) {
			System.out.println("getListOfAddresssByMetaTitle :: "+addressess);
			return addressess;
		}
		return null;
	}

}
