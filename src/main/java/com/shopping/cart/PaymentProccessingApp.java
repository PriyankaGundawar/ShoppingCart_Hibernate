package com.shopping.cart;

import com.shopping.Exceptions.PaymentTransactionNotFoundException;
import com.shopping.model.PaymentTransaction;
import com.shopping.service.PaymentTransactionService;

public class PaymentProccessingApp {

	public static void main(String[] args) {
		PaymentTransactionService pts = new PaymentTransactionService();
		pts.connect();
		PaymentTransaction pt = null;;
		try {
			pt = pts.getByPaymentTransactionId(54);
		} catch (PaymentTransactionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		pt.setStatus(1);
		pt.setMode(1);
		pt.setType(0);
		pt.setTotalAmount(101000); //101035
		
		pts.updatePaymentTransaction(pt);

	}

}
