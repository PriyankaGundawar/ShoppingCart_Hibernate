package com.shopping.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="payment_transaction")
public class PaymentTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int userId;
//	private int orderId;
	private String code;
	private int type;
	private int mode;
	private int status;
	private Date createdAt;
	private Date updatedAt;
	private String content;
	private float totalAmount;
	private int notification;
	
	@ManyToOne //(cascade = CascadeType.MERGE) //newly added cascade type
	@JoinColumn(name="orderId",referencedColumnName = "id")
	private Orders orders;
	
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "id")
	private User user;

}
