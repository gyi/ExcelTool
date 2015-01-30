package com.exceltool.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exceltool.data.ExcelMessage;
import com.exceltool.data.FieldExcel;
import com.exceltool.patchboard.IExcelToContainerTool;

public class ExcelToContainerTool implements IExcelToContainerTool{

	@Override
	public List<Map<String, List<FieldExcel>>> ExcelToContainer(File file,
			String right) {
		List<Map<String, List<FieldExcel>>> ExcelContainer = new ArrayList<Map<String,List<FieldExcel>>>();
		try {
			FileInputStream inputStreamTitle = new FileInputStream(file);
    		List<ExcelMessage> excelMessageTitles = ExcelTool.readRightColExcelContent(inputStreamTitle, right, 4) ;
    		if(excelMessageTitles==null) {
    			//JOptionPane.showMessageDialog(null, "请确认您的excel");
    			System.out.print(" 转换失败：请确认您的excel\n ");
    			return null;
    		}
    		
    		for (int i = 0; i < excelMessageTitles.size(); i++) {
				ExcelMessage excelMessageTitle = excelMessageTitles.get(i) ;
				
				List<List<String>> excelTitleData = new ArrayList<List<String>>(
						excelMessageTitle.getColData().values());
				List<FieldExcel> excelFieldExcelList = new ArrayList<FieldExcel>();
				for (int j = 0; j < excelTitleData.size(); j++) {
					List<String> list = excelTitleData.get(j);
					FieldExcel excel = new FieldExcel();
					excel.setRight(list.get(0));
					excel.setType(list.get(1));
					excel.setDesc(list.get(2));
					excel.setName(list.get(3));
					excelFieldExcelList.add(excel);
				}
				Map<String, List<FieldExcel>> ExcelMap = new HashMap<String,List<FieldExcel>>();
				ExcelMap.put(excelMessageTitle.getSheetName(), excelFieldExcelList);
				ExcelContainer.add(ExcelMap);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return ExcelContainer;
	}

	public static void main(String[] args) {
		ExcelToContainerTool excelToContainerTool = new ExcelToContainerTool(); 
		excelToContainerTool.ExcelToContainer(new File("F:\\tool\\forms\\等级配置表.xls"),"l");
	}

}
