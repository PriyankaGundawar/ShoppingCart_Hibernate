package com.shopping.cart;

import java.util.Date;
import java.util.List;

import com.shopping.Exceptions.OrdersNotFoundException;
import com.shopping.Exceptions.PaymentTransactionNotFoundException;
import com.shopping.model.Orders;
import com.shopping.model.PaymentTransaction;
import com.shopping.model.User;
import com.shopping.service.OrdersService;
import com.shopping.service.PaymentTransactionService;
import com.shopping.service.UserService;
import com.shopping.util.ApplicationConstants.PaymentStatus;


public class PaymentTransactionApp extends Thread{

	public static void main(String[] args) {
		
		OrdersService orderService = new OrdersService();
		orderService.connect();
		Orders order = null;
		try {
			order = orderService.getByOrdersId(19);
		} catch (OrdersNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		PaymentTransaction pt = createPayment(order);
		pt.setOrders(order);
		
		User user = order.getUser();
		pt.setUser(user);
		
		
		
		PaymentTransactionService ts = new PaymentTransactionService();		
		ts.connect();
		ts.persistPaymentTransaction(pt);
//		
//		PaymentTransaction addedpt = ts.addPaymentTransaction(pt);
		
//		try {
//			PaymentTransaction ptFromdb = ts.getByPaymentTransactionId(7);
//		} catch (PaymentTransactionNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ptFromdb.setMode(1);
//		ptFromdb.setStatus(1);
//		ts.updatePaymentTransaction(ptFromdb);
//		
//		List<PaymentTransaction> lpt = ts.getAllPaymentTransactions();
//		
//		List<PaymentTransaction> lpto = ts.getListOfPaymentTransactionsByOrderId(addedpt.getOrderId());
//		
//		ts.deletePaymentTransactionById(addedpt.getId());
		
		

	}

	public static PaymentTransaction createPayment(Orders order) {
		PaymentTransaction pt = new PaymentTransaction();		
//		pt.setUserId();
//		pt.setOrderId(3);
		pt.setCode("code3");
		pt.setType(1);
		pt.setMode(1);
		pt.setStatus(PaymentStatus.valueOf("Pending").ordinal());
		pt.setCreatedAt(new Date(System.currentTimeMillis()));
		return pt;
	}

}
