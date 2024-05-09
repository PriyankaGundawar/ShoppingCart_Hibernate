package com.shopping.cart;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.core.jmx.SimpleTriggerSupport;
import org.quartz.impl.StdSchedulerFactory;

import com.shopping.util.MyQuartzJob;

public class CartScheduler {

	public static void main(String[] args) throws SchedulerException {
		
		JobDetail j = JobBuilder.newJob(MyQuartzJob.class).build();
		
		Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(05).repeatForever()).build();
		
		Scheduler s = StdSchedulerFactory.getDefaultScheduler();
		
		s.start();
		s.scheduleJob(j, t);
	}

}
