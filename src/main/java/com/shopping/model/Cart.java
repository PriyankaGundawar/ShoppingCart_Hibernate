package com.shopping.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int userId;
	private int sessionId;
	private String token;
	private int status;
	private String firstName;
	private String middleName;
	private String lastName;
	private String mobile;
	private String email;
	private String line1;
	private String line2;
	private String city;
	private String province;
	private String country;
	private Date createdAt;
	private Date updatedAt;
	private String content;
	private float cartTotal;
	
	@ManyToOne   //(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
	@JoinColumn(name="userId",referencedColumnName = "id")
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
//	@OneToOne
//	private Address address;
 	

}
