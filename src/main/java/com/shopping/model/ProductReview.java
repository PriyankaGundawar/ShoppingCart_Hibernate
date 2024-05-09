package com.shopping.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name="product_review")
@Data
public class ProductReview {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int id;
//	  private int parentId;
//	  private int productId;
	  private String title;
	  private int rating;
	  private int published;
	  private Date createdAt;
	  private Date publishedAt;
	  private String content;
	  
	  @ManyToOne	  
	  @JoinColumn(name="productId",referencedColumnName = "id")
	  private Product product;
	  
	  @ManyToOne
	  @JoinColumn(name="parentId", referencedColumnName = "id")
	  private ProductReview productReview;
	  
	  @OneToMany(mappedBy = "productReview")
	  @ToString.Exclude
	  private List<ProductReview> productReviews = new ArrayList<ProductReview>();
	  
	  }
