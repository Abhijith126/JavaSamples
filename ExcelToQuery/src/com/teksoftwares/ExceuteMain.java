package com.teksoftwares;

import java.io.IOException;
import java.util.Scanner;

public class ExceuteMain {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ReadExcel excel = new ReadExcel();
		GenerateQuery query = new GenerateQuery();
		System.out.println("Ready to read");
		try {
			query.generateQueries(excel.readExcel());
			scan.close();
			System.out.println("Well That was quick..!!! Done writing queries... :)");
		} catch (IOException e) {
			System.out.println(
					"Error.!! File not found in the root directory.. Please place the file in root directory with name as test.xlsx");
		}
	}
}
