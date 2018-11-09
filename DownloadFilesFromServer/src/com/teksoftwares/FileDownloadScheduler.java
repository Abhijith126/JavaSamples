package com.teksoftwares;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * This is the class which contains the main method and has functionality for
 * the Job scheduling.
 * 
 * @author Abhijith P
 *
 */
public class FileDownloadScheduler {
	static Logger log = Logger.getLogger(FileDownloadScheduler.class.getName());

	/**
	 * Main method which uses Corn scheduler to automate the working of the
	 * program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			log.info("Entering");
			JobDetail job = JobBuilder.newJob(StartExecution.class).withIdentity("BatchJob", "group1").build();

			// Trigger the job to run on the next round minute
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("LogsTrigger", "group1")
					.withSchedule(CronScheduleBuilder
					// Corn for Per minute mail testing purpose.
					//.cronSchedule("0 0/1 * 1/1 * ? *"))
					// Get Corn Expressions from http://www.cronmaker.com/
					// corn for executing every day at 6/18 hours.
					.cronSchedule("0 0 14,23/12 * * ? *"))
					.build();
			// schedule it
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			log.info("Exiting");
		} catch (SchedulerException e) {
			log.error("Error in Scheduling" + e.getMessage());
		}
	}
}
