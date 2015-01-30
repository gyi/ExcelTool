package com.exceltool.patchboard;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.exceltool.data.FieldExcel;

public interface IExcelToContainerTool {
	
	/**
	 * 将Excel的Title转换为List<Map<String, List<FieldExcel>>>型的Container
	 * @param file Excel文件
	 * @param right 权限
	 * @return List<Map<String, List<FieldExcel>>>
	 */
	public List<Map<String, List<FieldExcel>>> ExcelToContainer(File file, String right);
	
}
