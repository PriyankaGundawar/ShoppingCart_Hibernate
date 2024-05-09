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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int userId;
	private String title;
	private String metaTitle;
	private String slug;
	private String summary;
	private int type;
	private int sku;
	private float price;
	private float discount;
	private int quantity;
	private int shop;
	private Date createdAt;
	private Date updatedAt;
	private Date publishedAt;
	private Date startsAt;
	private Date endsAt;
	private String content;	
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<ProductMeta> productMeta = new ArrayList<ProductMeta>();
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<ProductReview> productReviews = new ArrayList<ProductReview>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<OrderItem> orderItem = new ArrayList<OrderItem>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<CartItem> cartItem = new ArrayList<CartItem>();
	
	@ManyToMany(fetch = FetchType.EAGER) //(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinTable(name="product_category", joinColumns = @JoinColumn(name="productId"), inverseJoinColumns = @JoinColumn(name="categoryId"))
	@ToString.Exclude
	private List<Category> categories = new ArrayList<Category>();
	
	//TO-DO remove eager
	
	@ManyToMany(fetch = FetchType.EAGER) //(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinTable(name="product_tag", joinColumns = @JoinColumn(name="product_id"), inverseJoinColumns = @JoinColumn(name="tag_id"))
	@ToString.Exclude
	private List<Tag> tags = new ArrayList<Tag>();
	
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "id")
	private User user;

}


