package com.teksoftwares;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class DownloadFiles {

	static Logger log = Logger.getLogger(DownloadFiles.class.getName());

	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String curDate = sdf.format(date);
	static String directoryPath = "";

	public String beginDownload() throws IOException {
		DownloadFiles downloadFiles = new DownloadFiles();
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("user", "pass".toCharArray());
			}
		});

		LocalDate end = LocalDate.now();
		LocalDate start;
		if (end.getDayOfWeek().name().equals("MONDAY"))
			start = end.minus(Period.ofDays(3));
		else
			start = end.minus(Period.ofDays(2));

		String prod[] = {"paths/urls"};

		DownloadFiles.directoryPath = "local direcory path to save the files";

		IntStream.range(0, prod.length).forEach(i -> {
			String outputFile = "_" + prod[i].substring(prod[i].indexOf("to get the node number for the file name") + 4, prod[i].indexOf("to get the node number for the file name") + 8) + "_";
			if (prod[i].contains("contains some part of the file name| used to split for different file names.")) {
				outputFile = "based on above expression the name can be selected." + outputFile;
				downloadFiles.parse(prod[i], outputFile, start, end);
			} else {
				downloadFiles.parse(prod[i], outputFile, start, end);
			}
		});

		return directoryPath + curDate.replaceAll("-", "");
	}

	private void parse(String urlString, String outputFile, LocalDate startDate, LocalDate endDate) {
		try {
			new File(directoryPath + curDate.replaceAll("-", "")).mkdir();
			String path = directoryPath + curDate.replaceAll("-", "") + "/";

			String inputLine;
			LocalDate next = startDate.minusDays(1);
			while ((next = next.plusDays(1)).isBefore(endDate.plusDays(1))) {
				try {
					URL url = new URL(String.format(urlString));
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String dateString = next.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
					while ((inputLine = in.readLine()) != null) {
						if ((inputLine.contains("application") || inputLine.contains("SystemOut"))) {
							if (inputLine.contains(dateString)) {
								System.out.println("working");
								String opFileName = "";
								Pattern p = Pattern.compile("<a href=\"(.+?)\">");
								Matcher m = p.matcher(inputLine);
								while (m.find()) {
									opFileName = m.group(1);
								}
								this.downloadFile(urlString + opFileName,
										path + dateString + outputFile + opFileName + ".txt");

							}
						}
					}
				} catch (Exception e) {
					log.fatal("Some error occured. Exception: "+e.getMessage());
				}
			}
		} catch (Exception e) {
			log.fatal("Some error occured. Exception: "+e.getMessage());
		}

	}

	private void downloadFile(String url, String filename) {
		try {
			FileUtils.copyURLToFile(new URL(String.format(url)), new File(filename));
		} catch (MalformedURLException e) {
			log.fatal("URL IS incorrect. Exception: " + e.getMessage());
		} catch (IOException e) {
			log.fatal("Error while trying to access files. Exception: " + e.getMessage());
		}
	}
}