package com.exceltool.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.exceltool.data.ExcelMessage;
import com.exceltool.data.FieldExcel;
import com.exceltool.data.MyField;
import com.exceltool.data.RankStatus;
import com.exceltool.message.MessageLogTable;

public class ExcelTool {

	public static String downpath = "F:/workspace/maveneclipse/cs_resource/src/main/webapp/excel/";

	/**
	 * 将指定数据转换为EXCEL
	 * 
	 * @param treeMap
	 * @return void 返回类型
	 * @throws
	 */
	public static void converToExcel(TreeMap<String, List<MyField>> treeMap) throws IOException {

		for (Entry<String, List<MyField>> entry : treeMap.entrySet()) {
			String name = entry.getKey();

			String sheetName_clazzName[] = name.split("=");

			// 创建Excel文档
			HSSFWorkbook hwb = new HSSFWorkbook();
			String outputFile = downpath + sheetName_clazzName[0] + ".xls";

			HSSFCellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setFillBackgroundColor(HSSFColor.TEAL.index);
			cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			// 前景颜色
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// 填充方式，前色填�?
			HSSFCellStyle cellStyleSec = hwb.createCellStyle();
			cellStyleSec.setFillBackgroundColor(HSSFColor.BLUE.index);
			cellStyleSec.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			// 前景颜色
			cellStyleSec.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			List<MyField> fileds = entry.getValue();

			// sheet 对应�?��工作�?
			HSSFSheet sheet = hwb.createSheet(sheetName_clazzName[1]);
			HSSFRow typerow = sheet.createRow(0);
			HSSFRow firstrow = sheet.createRow(1);
			// 下标�?的行�?��
			HSSFRow secondrow = sheet.createRow(2);
			// 下标�?的行�?��
			for (int i = 0; i < fileds.size(); i++) {

				MyField filed = fileds.get(i);
				String desc = filed.getDescrible();
				HSSFCell hssfCellDesc = firstrow.createCell(i);
				hssfCellDesc.setCellStyle(cellStyle);
				hssfCellDesc.setCellValue(desc);
				String fieldName = filed.getName();
				HSSFCell hssfCellName = secondrow.createCell(i);
				hssfCellName.setCellStyle(cellStyleSec);
				int num = desc.getBytes().length > fieldName.getBytes().length ? desc.getBytes().length : fieldName
						.getBytes().length;
				sheet.setColumnWidth(i, num * 512);
				hssfCellName.setCellValue(fieldName);
				HSSFCell hssfCellType = typerow.createCell(i);
				hssfCellType.setCellStyle(cellStyleSec);
				String type = filed.getClazz().getName();
				if (type.contains(".")) {
					type = type.substring(type.lastIndexOf(".") + 1, type.length());
				}
				hssfCellType.setCellValue(type);
			}

			FileOutputStream outputStream = new FileOutputStream(outputFile);

			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellFormatValue(Cell cell) {
		DataFormatter FORMATTER = new DataFormatter();
		return FORMATTER.formatCellValue(cell);
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	@SuppressWarnings("deprecation")
	public static TreeMap<Integer, List<String>> readExcelContent(InputStream is) {
		TreeMap<Integer, List<String>> content = new TreeMap<Integer, List<String>>();
		try {
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			// 得到总行�?
			int rowNum = sheet.getLastRowNum();
			HSSFRow row = sheet.getRow(0);
			int colNum = row.getPhysicalNumberOfCells();

			// 正文内容应该从第二行�?��,第一行为表头的标�?
			for (int i = 0; i <= rowNum; i++) {
				row = sheet.getRow(i);
				List<String> list = new ArrayList<String>();
				int j = 0;
				while (j <= colNum) {
					list.add(getCellFormatValue(row.getCell((short) j)).trim());
					j++;
				}
				content.put(i, list);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}

		return content;
	}

	private static boolean checkFileIsExist(Row rowClass, String right, Sheet sheet) {
		if (rowClass == null) {
			MessageLogTable.addMessageSheetList(false, right, sheet.getSheetName() + "", "第一行类名不存在",
					RankStatus.OTHER.getValue());
			return false;
		}
		return true;
	}

	private static boolean checkRightContent(Row row, String right, Sheet sheet) {
		if (row == null) {
			MessageLogTable.addMessageSheetList(false, right, sheet.getSheetName() + "", "第二行权限不存在",
					RankStatus.OTHER.getValue());

			return false;
		}
		return true;
	}

	private static boolean checkFirstIsExist(String value, String right, Sheet sheet) {
		if (StringUtils.isEmpty(value)) {
			MessageLogTable.addMessageSheetList(false, right, sheet.getSheetName() + "", "第一行类名不存在",
					RankStatus.OTHER.getValue());

			return false;
		}
		return true;
	}

	private static boolean checkFirstContent(Row rowClass, String right, Sheet sheet) {
		int num = rowClass.getPhysicalNumberOfCells();
		if (num > 1) {
			for (int i = 1; i < num; i++) {
				Cell cell = rowClass.getCell(i);
				if (!StringUtils.isEmpty(getCellFormatValue(cell).trim())) {
					MessageLogTable.addMessageSheetList(false, right, sheet.getSheetName() + "",
							"第一行的应只有一个格子，且格子中的内容为类名", RankStatus.OTHER.getValue());
					return false;
				}
			}
		}
		return true;
	}

	private static TreeMap<Integer, List<String>> doRow(String right, int rowNumber, int rowNum, int colNum, Row row,
			Sheet sheet, Set<Integer> rowNumRightSet, Set<Integer> rowNumRequiredSet,
			TreeMap<Integer, List<String>> content) {
		int maxKey = rowNumber - 1;
		for (int key : content.keySet()) {
			if (key > maxKey) {
				maxKey = key;
			}
		}
		maxKey -= rowNumber - 1;
		for (int i = rowNumber; i <= rowNum; i++) {
			row = sheet.getRow(i);
			// 判断此行是否存在
			if (row == null) {
				break;
			}
			List<String> list = new ArrayList<String>();
			int j = 0;
			while (j <= colNum) {
				if (rowNumRightSet.contains(j)) {
					String cellValue = getCellFormatValue(row.getCell((short) j)).trim();
					if (rowNumRequiredSet.contains(j) && (cellValue == null || cellValue.equals(""))) {
						MessageLogTable.addMessageSheetList(false, right, sheet.getSheetName() + "", "第"
								+ (char) (j + 65) + "列数据为必填项", RankStatus.OTHER.getValue());
						return null;
					}
					list.add(cellValue);
				}
				j++;
			}
			content.put(i + maxKey, list);
		}
		return content;
	}

	private static TreeMap<Integer, List<String>> doCol(int rowNumber, int rowNum, int colNum, Row row, Sheet sheet,
			Set<Integer> rowNumRightSet, Set<Integer> rowNumRequiredSet, TreeMap<Integer, List<String>> content) {
		int j = 0;
		while (j <= colNum) {
			if (rowNumRightSet.contains(j)) {
				List<String> colList = new ArrayList<String>();
				for (int i = 2; i <= rowNumber; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						colList.add(getCellFormatValue(row.getCell((short) j)).trim());
					}

					else {
						colList.add("");
					}
				}

				content.put(j, colList);
			}
			j++;
		}
		return content;
	}

	public static List<ExcelMessage> readRightExcelContent(InputStream is, String right, int rowNumber, Boolean isRow) {
		List<ExcelMessage> excelMessages = new ArrayList<ExcelMessage>();

		try {
			// POIFSFileSystem fs = new POIFSFileSystem(is);
			Workbook wb = SSSFTransUnit.SSSFWorkbook(is);
			// sheet数量
			int sheetNum = wb.getNumberOfSheets();
			for (int z = 0; z < sheetNum; z++) {
				TreeMap<Integer, List<String>> content = new TreeMap<Integer, List<String>>();
				ExcelMessage excelMessage = new ExcelMessage();
				Sheet sheet = wb.getSheetAt(z);
				Row rowClass = sheet.getRow(0);
				if (!checkFileIsExist(rowClass, right, sheet))
					return null;
				Cell cellClass = rowClass.getCell(0);
				String value = getCellFormatValue(cellClass).trim();
				if (!checkFirstIsExist(value, right, sheet))
					return null;

				if (!isRow) {
					if (!checkFirstContent(rowClass, right, sheet))
						return null;
				}

				String sheetName = "";
				for (int i = 0; i < excelMessages.size(); i++) {

					if (excelMessages.get(i).getSheetName().split("=", 2)[0].equals(value)) {
						if (isRow) {
							content.putAll(excelMessages.get(i).getRowData());
						} else {
							content.putAll(excelMessages.get(i).getColData());
						}

						sheetName = excelMessages.get(i).getSheetName().split("=", 2)[1] + "&";
						excelMessages.remove(i);
					}
				}

				// 得到总行数
				// int rowNum = sheet.getPhysicalNumberOfRows();
				int rowNum = sheet.getLastRowNum();
				Row row = sheet.getRow(2);
				Row requiredRow = sheet.getRow(1);
				if (!checkRightContent(row, right, sheet))
					return null;
				int colNum = row.getLastCellNum();
				excelMessage.setRowNum(rowNum);
				excelMessage.setColNum(colNum);
				Set<Integer> rowNumRightSet = new HashSet<Integer>();
				Set<Integer> rowNumRequiredSet = new HashSet<Integer>();

				for (int i = 0; i <= colNum; i++) {
					if (getCellFormatValue(row.getCell(i)).contains(right)) {
						rowNumRightSet.add(i);
						if (requiredRow != null && getCellFormatValue(requiredRow.getCell(i)).contains("*")) {
							rowNumRequiredSet.add(i);
						}
					}
				}

				// 正文内容应该从第二行查起,第一行为表头
				if (isRow) {
					content = doRow(right, rowNumber, rowNum, colNum, row, sheet, rowNumRightSet, rowNumRequiredSet,
							content);
					if (content == null) {
						return null;
					}
					excelMessage.setRowData(content);
				} else {
					content = doCol(rowNumber, rowNum, colNum, row, sheet, rowNumRightSet, rowNumRequiredSet, content);
					excelMessage.setColData(content);
				}
				excelMessage.setSheetName(value + "=" + sheetName + sheet.getSheetName());

				excelMessages.add(excelMessage);

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}

		return excelMessages;
	}

	/**
	 * 读取Excel权限数据内容(按行查询)
	 * 
	 * @param InputStream
	 * @param right
	 *            权限
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public static List<ExcelMessage> readRightRowExcelContent(InputStream is, String right, int rowNumber) {

		return readRightExcelContent(is, right, rowNumber, true);
	}

	/**
	 * 读取Excel权限数据内容(按列查询)
	 * 
	 * @param InputStream
	 * @param right
	 *            权限
	 * @param rowNumber
	 *            (从第�?���?��查起,rolNumber=0表示为第�?��,以此类推)要查到的行数
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public static List<ExcelMessage> readRightColExcelContent(InputStream is, String right, int rowNumber) {

		return readRightExcelContent(is, right, rowNumber, false);
	}

	public static boolean convertXmlConvenient(File file, String version, String rightMessage, String right, String path) {
		boolean judge = false;
		try {
			FileInputStream inputStreamTitle = new FileInputStream(file);
			FileInputStream inputStreamContent = new FileInputStream(file);
			List<ExcelMessage> excelMessageContents = ExcelTool.readRightRowExcelContent(inputStreamTitle, right, 6);
			List<ExcelMessage> excelMessageTitles = ExcelTool.readRightColExcelContent(inputStreamContent, right, 5);
			if (excelMessageContents == null || excelMessageTitles == null) {
				// JOptionPane.showMessageDialog(null, "请确认您的excel");
				MessageLogTable.getInstance(file.getAbsolutePath(), 0).setCommonMessage("解析失败：请确认您的excel");
				return false;
			}
			if (excelMessageTitles.size() != excelMessageContents.size()) {
				// JOptionPane.showMessageDialog(null, "请确认您的excel");
				MessageLogTable.getInstance(file.getAbsolutePath(), 0).setCommonMessage("解析失败：请确认您的excel");
				return false;
			}

			for (int i = 0; i < excelMessageContents.size(); i++) {
				ExcelMessage excelMessageTitle = excelMessageTitles.get(i);
				ExcelMessage excelMessageContent = excelMessageContents.get(i);
				judge = (XmlTool.convertExcel2XML2(
						ExcelTool.getFieldExcel(excelMessageTitle, excelMessageContent, right), version,
						excelMessageTitle.getSheetName(), i + 1, right, path)) | judge;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
			return false;
		}
		return judge;
	}

	/**
	 * 获得转成具体xml的信息
	 * 
	 * @param @param messsagesTitle 标题以及相关字段描述信息
	 * @param @param messsagesContent 配置具体内容
	 * @return List<FieldExcel> 返回转换成xml的信息
	 * @throws
	 */
	public static List<List<FieldExcel>> getFieldExcel(ExcelMessage messsagesTitle, ExcelMessage messsagesContent,
			String right) {
		// 标题
		List<List<String>> excelTitleData = new ArrayList<List<String>>(messsagesTitle.getColData().values());

		if (excelTitleData.size() == 0) {
			MessageLogTable.addMessageSheetList(false, right, messsagesTitle.getSheetName().split("=", 2)[1],
					"请确认您的sheet表的第一行是否有包名或权限是否分配正确", RankStatus.PRIVILEGE.getValue());
			return null;
		}

		// 内容
		List<List<String>> excelContentData = new ArrayList<List<String>>(messsagesContent.getRowData().values());
		// MessageLog.addAllMessage();
		if (excelContentData.size() == 0) {
			MessageLogTable.addMessageSheetList(false, right, messsagesTitle.getSheetName().split("=", 2)[1],
					"请确认您的sheet表是否有数据", RankStatus.DATA.getValue());
			return null;
		}
		// 返回xml�?��信息
		List<List<FieldExcel>> resultColl = new ArrayList<List<FieldExcel>>();
		for (int i = 0; i < excelContentData.size(); i++) {
			List<String> listContent = excelContentData.get(i);
			List<FieldExcel> excels = new ArrayList<FieldExcel>();
			for (int j = 0; j < listContent.size(); j++) {
				List<String> list = excelTitleData.get(j);
				String value = listContent.get(j);
				FieldExcel excel = new FieldExcel();
				excel.setRight(list.get(0));
				excel.setType(list.get(1));
				excel.setDesc(list.get(2));
				excel.setName(list.get(3));
				excel.setValue(value);
				excels.add(excel);
			}
			resultColl.add(excels);
		}
		return resultColl;
	}
}
