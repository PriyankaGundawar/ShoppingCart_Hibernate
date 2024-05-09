package com.shopping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="addresses")
@Data
public class Address {
	@Id
	@Column(name="address_id")
	private int addressId;
//	private int userId;
	@Column(name="address_type")
	private String addressType;
	@Column(name="address_line_1")
	private String addressLine1;
	@Column(name="address_line_2")
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	@Column(name="postal_code")
	private String postalCode;	
	
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "id")
	private User user;
	
//	@OneToOne
//	private Cart cart;

}
