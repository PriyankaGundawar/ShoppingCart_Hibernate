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
@Table(name="order_item")
@Data
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int productId;
//	private int orderId;
	private String sku;
	private float price;
	private float discount;
	private int quantity;
	private Date createdAt;
	private Date updatedAt;
	private String content;
	
	@ManyToOne
	@JoinColumn(name="orderId",referencedColumnName = "id")
	private Orders orders;
	
	@ManyToOne
	@JoinColumn(name="productId",referencedColumnName = "id")
	private Product product;

}
