package com.shopping.model;

import java.util.ArrayList;
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
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int parentId;
	private String Title;
	private String metaTitle;
	private String slug;
	private String content;
	
	//Added fetchtype
	@ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Product> products = new ArrayList<Product>();
	
	
	@ManyToOne //(cascade = CascadeType.ALL)
	@JoinColumn(name="parentId", referencedColumnName = "id")
	private Category category;
	
	@OneToMany(mappedBy = "category")
	@ToString.Exclude
	private List<Category> categories = new ArrayList<Category>();

}
