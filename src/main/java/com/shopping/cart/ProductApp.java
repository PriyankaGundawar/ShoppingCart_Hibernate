package com.shopping.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.shopping.Exceptions.CategoryNotFoundException;
import com.shopping.Exceptions.ProductNotFoundException;
import com.shopping.Exceptions.TagNotFoundException;
import com.shopping.Exceptions.UserNotFoundException;
import com.shopping.model.Category;
import com.shopping.model.Product;
import com.shopping.model.Tag;
import com.shopping.model.User;
import com.shopping.service.CategoryService;
import com.shopping.service.ProductService;
import com.shopping.service.TagService;
import com.shopping.service.UserService;

public class ProductApp {
	public static void main(String[] args) {
		
		
//		ProductService service = new ProductService();
//		service.connect();
//		
//		try {
//			Product product = service.getByProductId(35);
//		} catch (ProductNotFoundException e) {
//			System.out.println("error :: "+e.getMessage());	
////			e.printStackTrace();
//		}		
		
		
		//Categories		
//		updateProductAddCategories();
//		updateProductByDeleteAddListCategories();
//		updateProductDeleteAllCategories();
		
		//Tags
//		updateProductDeleteAllTags();		
//		updateProductByDeleteAddListTags();
//		updateAddTagsToProduct();	//working after Exception changes		
//		updateQuantity();	//working after Exception changes	
//		updatePrice(); //working after Exception changes
//		updateDiscount(); //working after Exception changes
//		createProduct(); //working after Exception changes
		
		
//
//		List<Product> productsList = ps.getAllProducts();
//
//    	ps.getListOfProductsByMetaTitle(product.getTitle());
//
//    	ps.deleteProductById(product.getId());

	}

	public static void updateProductDeleteAllCategories() {
		ProductService productService = new ProductService();
		productService.connect();
		
		Product productFromDB = null;
		try {
			productFromDB = productService.getByProductId(37);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserService userService = new UserService();
		userService.connect();
		User user = null;
		try {
			user = userService.getByUserId(1);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		productFromDB.setUser(user);
		productFromDB.setUpdatedAt(new Date(System.currentTimeMillis()));
		
		productFromDB.getCategories().clear();
		
		productService.updateProduct(productFromDB);
	}

	public static void updateProductByDeleteAddListCategories() {
		ProductService productService = new ProductService();
		productService.connect();
		Product productFromDatabase = null;
		try {
			productFromDatabase = productService.getByProductId(37);
		} catch (ProductNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		UserService userService = new UserService();
		userService.connect();
		User userFromDatabase = null;
		try {
			userFromDatabase = userService.getByUserId(1);
		} catch (UserNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		productFromDatabase.setUser(userFromDatabase);
		productFromDatabase.setUpdatedAt(new Date(System.currentTimeMillis()));
		
		List<Category> listOfExistingCategories = productFromDatabase.getCategories();
		
		CategoryService categoryService = new CategoryService();
		categoryService.connect();
		int categoryToBeRemovedId = 13;

//			categoryToBeRemoved = categoryService.getByCategoryId(13);

		List<Category> listOfCategoriesToRemove = new ArrayList<Category>();
		
		listOfExistingCategories.forEach(e -> {
			System.out.println(e+ "before remove if condition" + categoryToBeRemovedId); 
			if(categoryToBeRemovedId==(e.getId())) {
				System.out.println("Insisde remove if condition" + e.getId());
//				listOfExistingCategories.remove(e)		;// note this will throw concurrent modification excption. so to avoid this we need to create the list listOfCategoriesToRemove
				listOfCategoriesToRemove.add(e);
			}
			
		});
		listOfExistingCategories.removeAll(listOfCategoriesToRemove);
		
		System.out.println("listofcatergories to remove" + listOfCategoriesToRemove);
		
		System.out.println("List of remaining existing categories" +listOfExistingCategories);
		
		Category categoryToBeAdd1 = null;
		try {
			categoryToBeAdd1 = categoryService.getByCategoryId(15);
		} catch (CategoryNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Category categoryToBeAdd2 = categoryService.getByCategoryId(14);
		} catch (CategoryNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		listOfExistingCategories.add(categoryToBeAdd1);
//		listOfCategoryToAdd.add(categoryToBeAdd2);
		productFromDatabase.setCategories(listOfExistingCategories);
		
		System.out.println("List of categories to add or update" + listOfExistingCategories);
		
		
		productService.updateProduct(productFromDatabase);
	}

	public static void updateProductAddCategories() {
		ProductService productService = new ProductService();
		productService.connect();
		
//		Product productFromDatabase = productService.getByProductId(38);
//		
//		UserService userService = new UserService();
//		userService.connect();
//		User userFromDatabase = userService.getByUserId(1);
//		
//		productFromDatabase.setUser(userFromDatabase);
//		productFromDatabase.setUpdatedAt(new Date(System.currentTimeMillis()));
		Product productFromDatabase = getProductWithUser(productService);
		
		CategoryService categoryService = new CategoryService();
		categoryService.connect();
		Category category = null;
		try {
			category = categoryService.getByCategoryId(18);
		} catch (CategoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Category> categoryList = productFromDatabase.getCategories();
		categoryList.add(category);
		
		productFromDatabase.setCategories(categoryList);		
		
		productService.updateProduct(productFromDatabase);
	}

	public static void updateProductDeleteAllTags() {
		ProductService productService = new ProductService();
		productService.connect();
		
		Product productFromDatabase = getProductWithUser(productService);		
		
		productFromDatabase.getTags().clear();		
		
		productService.updateProduct(productFromDatabase);
	}

	public static void updateProductByDeleteAddListTags() {
		ProductService productService = new ProductService();
		productService.connect();
		
		Product productFromDatabase = getProductWithUser(productService);
		
		List<Tag> listOfExistingTags = productFromDatabase.getTags();
		
		TagService tagService = new TagService();
		tagService.connect();
		int tagToBeRemoved = 5;

		List<Tag> listOfTagsToRemove = new ArrayList<Tag>();
		
//		productFromDatabase.getTags().remove(listOfTagsToRemove);
		productFromDatabase.getTags().forEach(e -> {
			System.out.println(e+ "before remove if condition" + tagToBeRemoved);
			if(tagToBeRemoved==(e.getId())) {
				System.out.println("Insisde remove if condition");
				listOfTagsToRemove.add(e);					
			}
			
		});
		listOfExistingTags.removeAll(listOfTagsToRemove);
		
		System.out.println(listOfExistingTags);
		
		Tag tagToBeAdd = null;
		try {
			tagToBeAdd = tagService.getByTagId(8);
		} catch (TagNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Tag> listOfTagsToAdd = new ArrayList<Tag>();
		listOfTagsToAdd.add(tagToBeAdd);
		productFromDatabase.setTags(listOfTagsToAdd);
		
		
		productService.updateProduct(productFromDatabase);
	}

	public static void updateAddTagsToProduct() {
		ProductService productService = new ProductService();
		productService.connect();

		Product productFromDatabase = getProductWithUser(productService);
		
		TagService tagService = new TagService();
		tagService.connect();
		Tag tag1 = null;
		try {
			tag1 = tagService.getByTagId(8);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tag tag2 = null;
		try {
			tag2 = tagService.getByTagId(6);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Tag> tags = productFromDatabase.getTags();
		
		System.out.println(tags);
		tags.add(tag1);
//		tags.add(tag2);
		
		productFromDatabase.setTags(tags);

		
		productService.updateProduct(productFromDatabase);
	}
	
	public static void updateDiscount() {
		ProductService productService = new ProductService();
		productService.connect();		
		Product productFromDatabase = getProductWithUser(productService);
		productFromDatabase.setDiscount(30);
		productService.updateProduct(productFromDatabase);
	}

	public static Product getProductWithUser(ProductService productService) {
		Product productFromDatabase = null;
		try {
			productFromDatabase = productService.getByProductId(37);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		UserService userService = new UserService();
		userService.connect();
		User userFromDatabase = null;
		try {
			userFromDatabase = userService.getByUserId(1);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		productFromDatabase.setUpdatedAt(new Date(System.currentTimeMillis()));
		productFromDatabase.setUser(userFromDatabase);
		return productFromDatabase;
	}
	
	public static void updatePrice() {
		ProductService productService = new ProductService();
		productService.connect();
		Product productFromDatabase = getProductWithUser(productService);
		productFromDatabase.setPrice(12000);
		productService.updateProduct(productFromDatabase);
	}

	public static void updateQuantity() {
		ProductService productService = new ProductService();
		productService.connect();	
		Product productFromDatabase = getProductWithUser(productService);
		productFromDatabase.setQuantity(199);
		productService.updateProduct(productFromDatabase);
	}

	public static void createProduct() {
		UserService userService = new UserService();
		userService.connect();
		User user = null;
		try {
			user = userService.getByUserId(1);
		} catch (UserNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		Product product = createProductInfo();
		
		CategoryService cs = new CategoryService();
		cs.connect();
		
		Category category = null;
		try {
			category = cs.getByCategoryId(19);
		} catch (CategoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Category> categories = new ArrayList<Category>();
		categories.add(category);
		product.setCategories(categories);
		
		TagService tagService = new TagService();
		tagService.connect();		
		Tag tag = null;
		try {
			tag = tagService.getByTagId(8);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag);
		product.setTags(tags);		
		product.setUser(user);

		ProductService ps = new ProductService();
		ps.connect();
		Product addedProduct = ps.addProduct(product);
	}

	public static Product createProductInfo() {
		Product product = new Product();
		product.setTitle("Women Casuals Product1");
		product.setQuantity(30);
		product.setSummary("Women wear");
		product.setPrice(800);
		product.setType(2);
//		product.setUserId(2);
		product.setSlug("slug3");
		product.setSku(2);
		product.setCreatedAt(new Date(System.currentTimeMillis()));
		System.out.println("Added from createProduct method");
		return product;
	}
	
	
	
}
