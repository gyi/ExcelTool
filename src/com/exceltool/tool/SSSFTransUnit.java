package com.exceltool.tool;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SSSFTransUnit {
	private static int fileType;

	public static void FileType(String fileName) {
		if (fileName.endsWith(".xls")) {
			fileType = 0;
		} else if (fileName.endsWith(".xlsx")) {
			fileType = 1;
		}
	}

	public static Workbook SSSFWorkbook(InputStream is) {

		try {
			if (fileType == 0) {
				HSSFWorkbook wb = new HSSFWorkbook(is);
				return wb;
			} else if (fileType == 1) {
				XSSFWorkbook wb = new XSSFWorkbook(is);
				return wb;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
