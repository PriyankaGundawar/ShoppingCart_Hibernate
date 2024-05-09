package com.shopping.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String mobile;
	private String email;
	private String passwordHash;
	private int admin;
	private int vendor;
	private Date registeredAt;
	private Date lastLogin;
	private int intro;
	private String profile;
	private String token;
	private int status;
	@Column(name = "token_createdAt")
	private Date tokenCreatedAt;
	@Column(name = "last_password_updatedAt")
	private Date lastPasswordUpdatedAt;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Cart> cart = new ArrayList<Cart>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Orders> orders = new ArrayList<Orders>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<PaymentTransaction> payment = new ArrayList<PaymentTransaction>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Product> product = new ArrayList<Product>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Address> address = new ArrayList<Address>();
	
//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private UserToken userToken;

}
