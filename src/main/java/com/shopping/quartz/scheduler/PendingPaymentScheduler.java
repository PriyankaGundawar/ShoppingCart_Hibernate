package com.shopping.quartz.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.shopping.quartz.job.PendingPaymentJob;
import com.shopping.util.MyQuartzJob;

public class PendingPaymentScheduler {
	public static void main(String[] args) throws SchedulerException {
		
		JobDetail j = JobBuilder.newJob(PendingPaymentJob.class).build();
		
		Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
		
		Scheduler s = StdSchedulerFactory.getDefaultScheduler();
		
		s.start();
		s.scheduleJob(j, t);
		
	}
	


}
