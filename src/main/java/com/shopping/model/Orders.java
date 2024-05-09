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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="orders")
@Data
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int userId;
	private String sessionID;
	private String token;
	private int status;
	private float subTotal;
	private float itemDiscount;
	private float tax;
	private float shipping;
	private float total;
	private String promo;
	private float discount;
	private float grandTotal;
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
	
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<OrderItem> orderItem = new ArrayList<OrderItem>();
	
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<PaymentTransaction> paymentTransaction = new ArrayList<PaymentTransaction>();

	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "id")
	private User user;
}
