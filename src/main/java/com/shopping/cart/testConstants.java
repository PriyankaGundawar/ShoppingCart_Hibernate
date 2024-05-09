package com.shopping.cart;



import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.joda.time.DateTime;

import com.shopping.Exceptions.PaymentTransactionNotFoundException;
import com.shopping.model.PaymentTransaction;
import com.shopping.service.PaymentTransactionService;
import com.shopping.util.HashingUtil;

public class testConstants {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		ApplicationConstants ac = new ApplicationConstants();
//		
////		PaymentStatus ps1 = PaymentStatus.
//		
//		PaymentStatus ps1 = PaymentStatus.Denied;
//		System.out.println(ps1);
//		
////		PaymentStatus ps2 = PaymentStatus.Denied;
//		System.out.println(PaymentStatus.valueOf("Expired").ordinal());
		
//		int i = 0;
//		while(i < 30) {
//			System.out.println(i);
//			i++;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(i==20)
//				break;
//		}

		

//		 String emailAddress = "username@gmail.com";
//		 String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
//		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";		 
//		 Pattern p = Pattern.compile(regexPattern);
//		 
//		 Matcher m = p.matcher(emailAddress);
//		 
//		 if(m.find()) {
//			 System.out.println("correct !!");
//		 }
//		 else
//		 System.out.println("Enter valid one");
		 
//		 String fname = "John D'Largy";
////		 String firstNamePattern = "^([a-zA-Z]{2,}\\s[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)";
//		 String firstNamePattern = "^[a-zA-Z][0-9a-zA-Z .,'-]*$";
//		 Pattern p = Pattern.compile(firstNamePattern);		 
//		 Matcher m = p.matcher(fname);
//		 
//		 if(m.find()) {			 
//			 
//			 System.out.println("correct !!");		 }
//		 else		
//			 System.out.println("Enter valid one");
		 
//		 String mobile = "1234567890";
////		 String mobilepattern = "^([0-9\\(\\)\\/\\+ \\-]*)$";
//		 String mobilepattern = "^(\\+?\\d{1,3})?[- \\.]?\\d{3}[- \\.]?\\d{3}[- \\.]?\\d{4}$";
//
//		 Pattern p = Pattern.compile(mobilepattern);		 
//		 Matcher m = p.matcher(mobile);		 
//		 if(m.find()) {				 
//			 System.out.println("correct !!");		 }
//		 else		
//			 System.out.println("Enter valid one");
		
//		String pwd = "Ptyyuu621235#$&";
//		String pwdpattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{8,}$";
//
//		 Pattern ppwd = Pattern.compile(pwdpattern);		 
//		 Matcher mpwd = ppwd.matcher(pwd);		 
//		 if(mpwd.find()) {				 
//			 System.out.println("correct !!");		 }
//		 else		
//			 System.out.println("Enter valid one");
		
//		PaymentTransactionService pts = new PaymentTransactionService();
//		pts.connect();
//		PaymentTransaction pt1 = null;
//		
//		try {
//			pt1 = pts.getByPaymentTransactionId(33);
//			System.out.println("Actual paytran 33 detail" + pt1);
//		} catch (PaymentTransactionNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		PaymentTransaction pt2 = new PaymentTransaction();
//		try {
//			BeanUtils.copyProperties(pt2, pt1);
//			System.out.println("Coppied tran details: " + pt2);
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		DateTime twoDays = new DateTime().minusDays(3);
//		Date sysMinusTwoDays = twoDays.toDate();
//		sysMinusTwoDays.setHours(0);
//		sysMinusTwoDays.setMinutes(0);
//		sysMinusTwoDays.setSeconds(1);
//		
//		
//		
//		pts.getListOfPaymentTransactionsTwoDaysOlderCreatedDate(sysMinusTwoDays, 11);
		//how to create todays date or date object -- syadate 
//		Date todayDate = new Date(System.currentTimeMillis());
//		System.out.println(todayDate);
		//remove 2 days from the current date syadate - 2 days
		

		//convert date to string
		//pass string converted date to method param
		
		
		String text1 = "Firstlast#123456"; //[B@445b84c0 //[B@2133c8f8
		String text2 = "Firstlast#123456";
//		MessageDigest digest = null;
//		try {
//			digest = MessageDigest.getInstance("SHA-256");
//			
//
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		byte[] hash1 = digest.digest(text1.getBytes(StandardCharsets.UTF_8));		
//		String HashString = hash1.toString();
//		System.out.println("H1 String:" +HashString);
//				
//		byte[] hash2 = digest.digest(text2.getBytes(StandardCharsets.UTF_8));
//		System.out.println(hash2);
//		
////		boolean b = MessageDigest.isEqual(hash1, hash2);
////		System.out.println(b);
//		
//		if(MessageDigest.isEqual(hash1, hash2)) {
//			System.out.println("Same !!");
//		}
//		else {
//			System.out.println("Different !!");
//		}
		
//		String sha256hex1 = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text1);		
//		System.out.println("Apache text1" +sha256hex1);
//		
//		String sha256hex2 = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text2);
//		System.out.println("Apache text2" +sha256hex2);
		

		
//		if(sha256hex1.equals(sha256hex2)) {
//			System.out.println("Same !!");
//		}
//		else {
//			System.out.println("Different !!");
//		}
		
		HashingUtil util = new HashingUtil();
		String s1 = util.stringHashing(text1);
		System.out.println(s1); //�"}9\�FP�%榦�G�x�)�5�𞎷3��Y //�"}9\�FP�%榦�G�x�)�5�𞎷3��Y
		
		String s2 = util.stringHashing(text2);
		System.out.println(s2);
		
		if(s1.equals(s2)) {
			System.out.println("same");
		}
		else {
			System.out.println("Not same !!");
		}
		
		
		
		
		

	}
	
	
	


}
