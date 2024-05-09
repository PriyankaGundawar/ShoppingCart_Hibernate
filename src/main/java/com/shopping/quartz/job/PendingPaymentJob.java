package com.shopping.quartz.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.shopping.service.PaymentTransactionService;

public class PendingPaymentJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		PaymentTransactionService pts = new PaymentTransactionService();
		pts.connect();
		
		DateTime twoDays = new DateTime().minusDays(10);
		Date sysMinusTwoDays = twoDays.toDate();
		sysMinusTwoDays.setHours(0);
		sysMinusTwoDays.setMinutes(0);
		sysMinusTwoDays.setSeconds(1);
		
		pts.getListOfPaymentTransactionsTwoDaysOlderCreatedDate(sysMinusTwoDays, 11);
		
	}

}
