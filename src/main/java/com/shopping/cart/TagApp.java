package com.shopping.cart;



import java.util.List;

import com.shopping.Exceptions.TagNotFoundException;
import com.shopping.model.Tag;
import com.shopping.service.TagService;



public class TagApp {

	public static void main(String[] args) {
		Tag tag = new Tag();
		TagService ts = new TagService();
		
		ts.connect();
		
		tag.setTitle("5% discount on mobiles");
		tag.setMetaTitle("5% discount");
		tag.setSlug("url5.com");
		tag.setContent("5% discount tag on mobiles under 5000");
		
//		Tag addedTag = ts.addTag(tag);
		
		try {
			Tag tagFromdb = ts.getByTagId(4);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		tagFromdb.setContent("updatedContent");
//
//		ts.updateTag(tagFromdb);
//		
//		List<Tag> tlist = ts.getAllTags();
//		
//		List<Tag> tlists = ts.getListOfTagsByMetaTitle(addedTag.getMetaTitle());
//		
//		ts.deleteTagById(2);		

	}

	}

