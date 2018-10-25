package com.teksoftwares;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	public List<PersonData> readExcel() {
		List<PersonData> resultList = new ArrayList<PersonData>();
		try {
			FileInputStream file = new FileInputStream(new File("test.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row tempRow = sheet.getRow(0);
			int name = 0, age = 0, phno = 0, dob = 0, doj = 0;
			for (int c = 1; c < tempRow.getLastCellNum(); c++) {
				switch (getCellValue(tempRow.getCell(c), 1).trim().toLowerCase()) {
				case "name":
					name = c;
					break;
				case "age":
					age = c;
					break;
				case "phone no":
				case "phno":
					phno = c;
					break;
				case "date_of_birth":
				case "dob":
					dob = c;
					break;
				case "date_of_join":
				case "doj":
					doj = c;
					break;
				}
			}
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
				PersonData person = new PersonData();
				Row row = sheet.getRow(j);
				if (row != null) {
					System.out.println(j);
					person.setName(getCellValue(row.getCell(name), 0));
					person.setAge(getCellValue(row.getCell(age), 2));
					person.setPhno(getCellValue(row.getCell(phno), 10));
					person.setDob(getCellValue(row.getCell(dob), 0));
					person.setDoj(getCellValue(row.getCell(doj), 1));
					resultList.add(person);
				}
			}
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println(
					"Error.!! File not found in the root directory.. Please place the file in root directory with name as test.xlsx");
		} catch (IOException e2) {
			System.out.println(
					"Error.!! File not found in the root directory.. Please place the file in root directory with name as test.xlsx");
		}
		return resultList;
	}

	public String getCellValue(Cell cell, int len) {
		switch (cell.getCellType()) {
		case STRING:
			if (cell.getStringCellValue().contains("/")) {
				return getDate(cell, len, false);
			} else
				return cell.getStringCellValue();

		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return getDate(cell, len, true);
			} else {
				String formatted = String.format("%0" + len + "d", (int) cell.getNumericCellValue());
				return formatted;
			}
		default:
			break;
		}
		return null;
	}

	public String getDate(Cell cell, int format, boolean ind) {
		SimpleDateFormat sdf;
		if (ind) {
			Date tempdate = cell.getDateCellValue();
			if (format == 0)
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			else
				sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(tempdate);
		} else {
			if (format == 0)
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			else
				sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(new Date(cell.getStringCellValue()));
		}
	}
}
