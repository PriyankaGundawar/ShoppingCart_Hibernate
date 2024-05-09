package com.shopping.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="cart_item")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int productId;
//	private int cartId;
	private String sku;
	private float price;
	private float discount;
	private int quantity;
	private int active;
	private Date createdAt;
	private Date updatedAt;
	private String content;
	
	@ManyToOne
	@JoinColumn(name="cartId",referencedColumnName = "id")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name="productId",referencedColumnName = "id")
	private Product product;

}
