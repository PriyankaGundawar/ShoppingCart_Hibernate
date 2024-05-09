package com.shopping.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Data
@Setter
@Getter
public class Tag {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		private String Title;
		private String metaTitle;
		private String slug;
		private String content;
		
		@ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
//		@ToString.Exclude
		private List<Product> products = new ArrayList<Product>();

	}



