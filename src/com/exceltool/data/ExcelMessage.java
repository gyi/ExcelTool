package com.exceltool.data;

import java.util.List;
import java.util.TreeMap;

/**
 * Excel数据
*
* @author zhaowei 
* @Ceatetime 2014�?�?�?
*
 */
public class ExcelMessage {
	/**
	 * sheet名字
	 */
	private String sheetName ;
	
	/**
	 * 行数
	 */
	private int rowNum ;
	
	/**
	 *	列数
	 */
	private int colNum ;
	
	/**
	 * �?��行的行数�?
	 */
	private TreeMap<Integer, List<String>> rowData ;
	
	/**
	 * �?��列的列数�?
	 * 
	* @param @return 
	* @return String    返回类型 
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
