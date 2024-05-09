package com.shopping.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.shopping.Exceptions.PaymentTransactionNotFoundException;
import com.shopping.cart.PaymentTransactionApp;
import com.shopping.cart.SendEmail;
import com.shopping.model.CartItem;
import com.shopping.model.EmailParams;
import com.shopping.model.Orders;
import com.shopping.model.PaymentTransaction;
import com.shopping.model.User;
import com.shopping.util.ApplicationConstants.OrderStatus;
import com.shopping.util.ApplicationConstants.PaymentStatus;
import com.shopping.util.ApplicationUtil;

import jakarta.persistence.TemporalType;

public class PaymentTransactionService {
	Configuration config;
	SessionFactory sessionFactory;
	Session session;
	Transaction transaction;
	SendEmail se = new SendEmail();

	public void connect() {
		config = ApplicationUtil.configAnnotatedClass();
		sessionFactory = config.buildSessionFactory();		
	}

	public void beginTransaction() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	public void commitTransaction() {
		
		transaction.commit();
		session.close();
	}
	
	public PaymentTransaction persistPaymentTransaction(PaymentTransaction paymentTransaction) {
//		PaymentTransaction.setMetaTitle(PaymentTransaction.getTitle() + " " + PaymentTransaction.getSummary());
		System.out.println(paymentTransaction);
		beginTransaction();
		session.persist(paymentTransaction);
		commitTransaction();
		//write infiti loop to check the payment status
		

		
		
		
		int i = 0;
		while(i <= 30) {
//			System.out.println(ptIdForStatus.getStatus());
			System.out.println(i);
			i++;
			try {
				Thread.sleep(5000);
				PaymentTransaction ptIdForStatus = null;
//				PaymentTransaction ptIdForStatusafter = null;
				try {
					ptIdForStatus = getByPaymentTransactionId(paymentTransaction.getId());
					System.out.println("payobject for process" + ptIdForStatus);
					System.out.println("paystatus:"  + ptIdForStatus.getStatus());
				} catch (PaymentTransactionNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// check order total amount recevied, if not update payment status to partial payment and send email to user to completed payment.
				
				Orders order = paymentTransaction.getOrders();
				System.out.println("order for payment object" +order);
				float orderTotal = order.getGrandTotal();
				System.out.println("ordertotal before payment" + orderTotal);
				float amountRemains = orderTotal - ptIdForStatus.getTotalAmount();
				
				if(ptIdForStatus.getTotalAmount() > 0) {
					
					if(ptIdForStatus.getTotalAmount() == orderTotal) {
						System.out.println("total amount received");
						ptIdForStatus.setStatus(PaymentStatus.valueOf("Success").ordinal());
						updatePaymentTransaction(ptIdForStatus); //updating payment status here
						OrdersService orderService = new OrdersService();
						orderService.connect();
						order.setStatus(OrderStatus.valueOf("OrderPlaced").ordinal());
						System.out.println("Order has been placed after payment");
						orderService.updateOrders(order);
						
						break;						
					}
					else {
						ptIdForStatus.setStatus(PaymentStatus.valueOf("PartiallyPaid").ordinal());
						updatePaymentTransaction(ptIdForStatus); //updating payment status here
						System.out.println("partially paid. pay remaining amount:" + amountRemains);
						
						EmailParams parameter = new EmailParams();						
						User user = paymentTransaction.getUser();
						parameter.setEmailTo(user.getEmail());
						parameter.setSubject("Pay remaining amount to place the order");
						parameter.setEmailBody("Hello, Please pay remaing amount Rs. " + amountRemains + "to place your order #" + order.getId());						
						se.sendEmail(parameter);
						System.out.println("email sent to user" + user.getEmail());
						//create new payment record //check beansutil.copy
						
						beginTransaction();
						PaymentTransaction partialPayRecord = new PaymentTransaction();
						try {
							BeanUtils.copyProperties(partialPayRecord, ptIdForStatus);
							System.out.println("new partial payment record:" + partialPayRecord );
							//if we do not pass id, it will create new. if we pass id it will throw error saying  "detached entity passed to persist".
							partialPayRecord.setId(0); //if there is id then it will throw detached object passing error. 							
							partialPayRecord.setTotalAmount(amountRemains);
							session.persist(partialPayRecord);
							
							commitTransaction();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						
						break;
					}					
				}
				else {
					if(i==30 && ptIdForStatus.getStatus() == PaymentStatus.valueOf("Pending").ordinal()) {
						System.out.println("inside 60 block");
						ptIdForStatus.setStatus(PaymentStatus.valueOf("Failed").ordinal());
						updatePaymentTransaction(ptIdForStatus);
						
						EmailParams parameter = new EmailParams();						
						User user = paymentTransaction.getUser();
						parameter.setEmailTo(user.getEmail());
						parameter.setSubject("Payment has failed");
						parameter.setEmailBody("Hello, Your payment has been failed for order #" + order.getId() + " Please complete your payment to place the order.");						
						se.sendEmail(parameter);
						break;		
						
					}					
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("PaymentTransaction Added :: "+paymentTransaction);

		return paymentTransaction;
	}
	
	public PaymentTransaction getByPaymentTransactionId(int id) throws PaymentTransactionNotFoundException {
		session = sessionFactory.openSession();
		PaymentTransaction paymentTransaction = null;
		paymentTransaction = session.get(PaymentTransaction.class, id);
		System.out.println("getByPaymentTransactionId :: " +paymentTransaction);
		session.close();
		if(paymentTransaction == null) {
			throw new PaymentTransactionNotFoundException("Payment Transaction not found with id : "+id);
		}
		return paymentTransaction;

	}
	
	public List<PaymentTransaction> getAllPaymentTransactions() {
		String queryString = "select c from PaymentTransaction c";
		session = sessionFactory.openSession();
		Query query = session.createQuery(queryString, PaymentTransaction.class);
		List<PaymentTransaction> paymentTransaction = query.list();
		if (paymentTransaction.size() > 0) {
			System.out.println("List Of PaymentTransactions :: "+ paymentTransaction);
			return paymentTransaction;
		}
		session.close();

		return null;
	}
	
	public void deletePaymentTransactionById(int id) {
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		paymentTransaction.setId(id);
		session = sessionFactory.openSession();
		beginTransaction();
		session.remove(paymentTransaction);
		commitTransaction();		
		System.out.println("PaymentTransaction Deleted By id : "+id);
	}
	
	public PaymentTransaction updatePaymentTransaction(PaymentTransaction paymentTransaction) {
//		PaymentTransaction.setMetaTitle(PaymentTransaction.getTitle() + " " + PaymentTransaction.getSummary());
		beginTransaction();
		session.merge(paymentTransaction);
		commitTransaction();
		System.out.println("Updated PaymentTransaction : "+ paymentTransaction);
		return paymentTransaction;

	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentTransaction> getListOfPaymentTransactionsByOrderId(int orderId) {
		session = sessionFactory.openSession();
		String queryString = "select c from PaymentTransaction c where c.orderId = :search";
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, PaymentTransaction.class);
		query.setParameter("search", orderId);
		List<PaymentTransaction> paymentTransaction = query.list();
		session.close();
		if (paymentTransaction.size() > 0) {
			System.out.println("getListOfPaymentTransactionsByMetaTitle :: "+paymentTransaction);
			return paymentTransaction;
		}

		return null;
	}
	
	public List<PaymentTransaction> getListOfPaymentTransactionsTwoDaysOlderCreatedDate(Date searchDate, int status) {
		System.out.println("SearchDate:" + searchDate);
		session = sessionFactory.openSession();
		String queryString = "select c from PaymentTransaction c where c.createdAt >= :searchDate and c.status = :statusSearch and c.notification < 2";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String frmDate = format.parse(searchDate);
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery(queryString, PaymentTransaction.class);
		query.setParameter("searchDate", searchDate);
		query.setParameter("statusSearch", status);
		List<PaymentTransaction> paymentTransaction = query.list();
		session.close();
		if (paymentTransaction.size() > 0) {
//			System.out.println("getListOfPaymentTransactionsTwoDaysOlderCreatedDate :: "+paymentTransaction);
			for(PaymentTransaction pt : paymentTransaction) {
				System.out.println("Paymenttran:" + pt);
				User user = pt.getUser();
				
				
				EmailParams parameter = new EmailParams();
				parameter.setEmailTo(user.getEmail());
				parameter.setSubject("You payamnet is due to complete the order");
				parameter.setEmailBody("Please complete the payment");
				se.sendEmail(parameter);	
				int notification = pt.getNotification();
				notification++;
				pt.setNotification(notification);
				updatePaymentTransaction(pt);
				
				
			}
			return paymentTransaction;
		}

		return null;
	}

}
