package com.shopping.cart;

import org.hibernate.mapping.List;

import com.shopping.Exceptions.CategoryNotFoundException;
import com.shopping.Exceptions.IllegalCategoryDeletionException;
import com.shopping.model.Category;
import com.shopping.service.CategoryService;

public class CategoryApp {

	public static void main(String[] args) {
		
//		Category parent = new Category();
//		parent.setMetaTitle("Clothing");
//		parent.setSlug("clothingurl");
//		parent.setTitle("Clothing");
//		parent.setContent("ClothingContent");		
		
		CategoryService categoryservice = new CategoryService();		
		categoryservice.connect();
		
		try {
			try {
				categoryservice.deleteCategoryById(17);
			} catch (CategoryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalCategoryDeletionException e) {
			System.out.println("error :: "+e.getMessage());	
//			e.printStackTrace();
		}
		
//		Category parentfromDB = categoryservice.addCategory(parent);
//		Category menCategoryFromDB = categoryservice.getByCategoryId(12);
//		
//		System.out.println();
//		
//		Category category1 = new Category();		
//		category1.setMetaTitle("Casuals");
//		category1.setSlug("Casualsurl2");
//		category1.setTitle("Casuals");
//		category1.setContent("Casuals in Women clothing");
//		
//		Category category2 = new Category();		
//		category2.setMetaTitle("Jeans");
//		category2.setSlug("Jeansurl2");
//		category2.setTitle("Jeans");
//		category2.setContent("Jeans in Women clothing");
//		
//		Category category3 = new Category();		
//		category3.setMetaTitle("Polos");
//		category3.setSlug("Polosurl1");
//		category3.setTitle("Polos");
//		category3.setContent("Shirts in Women clothing");
//		
//		category1.setCategory(menCategoryFromDB);
//		category2.setCategory(menCategoryFromDB);
//		category3.setCategory(menCategoryFromDB);
//		
//		Category addedCategory1 = categoryservice.addCategory(category1);
//		Category addedCategory2 = categoryservice.addCategory(category2);
//		Category addedCategory3 = categoryservice.addCategory(category3);
		
		
//		-----------------------------
		
		try {
			Category categoryFromDB = categoryservice.getByCategoryId(15);
		} catch (CategoryNotFoundException e) {
			System.out.println("error :: "+e.getMessage());	
			e.printStackTrace();
		}
//		
//		categoryFromDB.setTitle("Shirts");
//		categoryFromDB.setMetaTitle("Shirts");
//		categoryFromDB.setSlug("Shirtsurl9");	
//		categoryFromDB.setContent("Shirts in Mens clothing");
//		categoryservice.updateCategory(categoryFromDB);
//		
//		categoryservice.getAllCategorys();
//		
//		categoryservice.getListOfCategorysByMetaTitle(category.getMetaTitle());
//		
//		categoryservice.deleteCategoryById(category.getId());
		
		
		
	}
	


}
