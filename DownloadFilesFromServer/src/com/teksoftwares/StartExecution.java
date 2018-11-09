package com.teksoftwares;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class StartExecution implements Job {
	static Logger log = Logger.getLogger(StartExecution.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Staring to Download Files");
		Instant start = Instant.now();
		DownloadFiles downloadFiles = new DownloadFiles();
		try {
			downloadFiles.beginDownload();
		} catch (IOException e) {
			log.fatal("Error occured while downloading Files. Error: " + e.getMessage());
		}
		Instant end = Instant.now();
		log.info("Time taken to download Files " + (Duration.between(start, end)));
	}
}