package com.shopping.cart;

import java.util.Date;

import com.shopping.Exceptions.ProductMetaNotFoundException;
import com.shopping.model.Product;
import com.shopping.model.ProductMeta;
import com.shopping.service.ProductMetaService;
import com.shopping.service.ProductService;

public class ProductMetaApp {

	public static void main(String[] args) {
		
		
		
		Product product = new Product();
		product.setTitle("Lenovo");
		product.setQuantity(10);
		product.setSummary("Lenovo ulta slim");
		product.setPrice(200000);
//		product.setUserId(1);
		product.setSlug("slug2");
		product.setSku(1);
		product.setCreatedAt(new Date(System.currentTimeMillis()));
		
//		ProductService ps = new ProductService();
//		ps.connect();
//		Product addedProduct = ps.addProduct(product);
//		
//		
//		
		ProductMetaService pmService = new ProductMetaService();		
		pmService.connect();
//		
//		
//		ProductMeta productMeta = new ProductMeta();		
//		productMeta.setPkey("Lenovo");
//		productMeta.setContent("Lenovo Mobile phone");
//		productMeta.setProduct(addedProduct);
//		
//		ProductMeta addedProductMeta = pmService.addProductMeta(productMeta);
//		
//		ProductMeta productMeta1 = new ProductMeta();		
//		productMeta1.setPkey("Lenovooos");
//		productMeta1.setContent("Lenovooos Mobile phone");
//		productMeta1.setProduct(addedProduct);
//		
//		ProductMeta addedProductMeta1 = pmService.addProductMeta(productMeta1);
		
		try {
			ProductMeta productMetaFromDB = pmService.getByProductMetaId(5);
		} catch (ProductMetaNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		productMetaFromDB.setContent("oppo reno Mobile phone 32MP camera");
//		pmService.updateProductMeta(productMetaFromDB);
//		
//		pmService.getAllProductMetas();
//		
//		pmService.getListOfProductMetasByMetaTitle(productMeta.getPkey());
//		
//		
//		pmService.deleteProductMetaById(productMeta.getId());
		
		System.out.println("ProductMeta have been added with product");
	}

}
