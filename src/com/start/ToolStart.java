package com.start;

import com.exceltool.data.IsCloseData;
import com.exceltool.frame.ExcelFrame;

/**
 *  excel工具启动类
*
* @Description: TODO
* @author zhaowei 
* @Ceatetime 2014年6月11日
*
 */
public class ToolStart {

	public static void main(String[] args) {
		IsCloseData.setClose(true);
		ExcelFrame.instance() ;
	}

}
