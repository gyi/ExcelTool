package com.exceltool.data;

import java.util.List;
import java.util.TreeMap;

/**
 * Excelæ•°æ®
*
* @author zhaowei 
* @Ceatetime 2014å¹?æœ?æ—?
*
 */
public class ExcelMessage {
	/**
	 * sheetåå­—
	 */
	private String sheetName ;
	
	/**
	 * è¡Œæ•°
	 */
	private int rowNum ;
	
	/**
	 *	åˆ—æ•°
	 */
	private int colNum ;
	
	/**
	 * æ‰?œ‰è¡Œçš„è¡Œæ•°æ?
	 */
	private TreeMap<Integer, List<String>> rowData ;
	
	/**
	 * æ‰?œ‰åˆ—çš„åˆ—æ•°æ?
	 * 
	* @param @return 
	* @return String    è¿”å›ç±»å‹ 
	* @throws
	 */
	
	private TreeMap<Integer, List<String>> colData ;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public TreeMap<Integer, List<String>> getRowData() {
		return rowData;
	}

	public void setRowData(TreeMap<Integer, List<String>> rowData) {
		this.rowData = rowData;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public TreeMap<Integer, List<String>> getColData() {
		return colData;
	}

	public void setColData(TreeMap<Integer, List<String>> colData) {
		this.colData = colData;
	}
}
