package com.exceltool.configload;

import java.io.InputStream;
import java.util.Properties;

import com.exceltool.tool.PropertiesTool;

public class SystemLoad {
	/**
	 * �����������
	* @param @return 
	* @return Properties    �������� 
	* @throws
	 */
	
	public static Properties getSysProperties(String path) {
		InputStream inputStream = SystemLoad.class.getResourceAsStream(path) ;
		return PropertiesTool.getProperties(inputStream) ;
	}
	
	/**
	 * ����ļ���ʵ·��
	* @param @return 
	* @return String    �������� 
	* @throws
	 */
	public static String getPathInputStream(String path) {
		return SystemLoad.class.getResource(path).getFile() ;
	}
}
