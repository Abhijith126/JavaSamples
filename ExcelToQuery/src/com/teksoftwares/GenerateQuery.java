package com.teksoftwares;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GenerateQuery {

	public void generateQueries(List<PersonData> personData) throws IOException {
		System.out.println("Uff thats a lot of queries...Writing Queries..!!");
		int count = 0;
		PrintWriter pw = new PrintWriter(new FileWriter("query.txt"));
		for (PersonData x : personData) {

			if (count == 0) {
				count++;
				pw.write("----------------------------------------------------------- \r\n");
				pw.write("--PERSON DETAILS TO MYSQL QUERY - STARTS \r\n");
				pw.write("-----------------------------------------------------------\r\n");
			} else {
				count++;
				String doj = x.getDoj().replace("/", "-");
				pw.write("--PERSON NAME " + x.getName() + " JOINING DATE " + x.getDoj() + "\r\n");
				pw.write("----------------------------------------------------------- \r\n");
				pw.write("INSERT INTO COMPANY.EMPLOYEES VALUES\r\n");
				pw.write("("+x.getName()+","+x.getAge()+","+x.getPhno()+","+x.getDob()+","+doj+");\r\n");
				pw.write("-----------------------------------------------------------\r\n");
			}
		}
		pw.write("-----------------------------------------------------------\r\n");
		pw.write("--PERSON DETAILS TO MYSQL QUERY - STARTS- END \r\n");
		pw.write("-----------------------------------------------------------\r\n");
		pw.close();
	}

}
