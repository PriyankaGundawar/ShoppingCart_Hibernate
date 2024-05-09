package com.shopping.cart;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import com.shopping.Exceptions.ProductNotFoundException;
import com.shopping.Exceptions.ProductReviewNotFoundException;
import com.shopping.model.Product;
import com.shopping.model.ProductReview;
import com.shopping.service.ProductReviewService;
import com.shopping.service.ProductService;

public class ProductReviewApp {

	public static void main(String[] args) {
		
//		Product product = ProductApp.createProduct();
//		ProductService ps = new ProductService();
//		ps.connect();
//		ps.addProduct(product);
		
		ProductService productService = new ProductService();
		productService.connect();
		Product product = null;
		try {
			product = productService.getByProductId(35);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		ProductReview productreview = new ProductReview();	
		productreview.setTitle("TitleforProductReview");
		productreview.setRating(4);
		productreview.setPublished(100);
		productreview.setCreatedAt(new Date(System.currentTimeMillis()));		
		productreview.setProduct(product);		
		
		ProductReviewService prs = new ProductReviewService();		
		prs.connect();
		ProductReview addedpr = prs.addProductReview(productreview);
		
		ProductReview childReview = new ProductReview();
		childReview.setRating(5);
		childReview.setCreatedAt(new Date(System.currentTimeMillis()));
		childReview.setPublished(20);
		childReview.setTitle("ChildTitle");
		childReview.setProduct(product);
		childReview.setProductReview(addedpr);
		
		ProductReview childAddedpr = prs.addProductReview(childReview);		
		
		
		System.out.println("Product added from ProductReview App");
		
		try {
			ProductReview dbpr = prs.getByProductReviewId(3);
		} catch (ProductReviewNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		dbpr.setPublished(19);
//		prs.updateProductReview(dbpr);
//		
//		prs.getAllProductReviews();
//		
//		prs.getListOfProductReviewsByRating(4);
	}
	
	
}
