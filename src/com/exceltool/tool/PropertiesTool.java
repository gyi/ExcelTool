package com.exceltool.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties������
*
* @Description: TODO
* @author zhaowei 
* @Ceatetime 2014��6��11��
*
 */
public class PropertiesTool {
	
	/**
	 * ����properties
	* @param @param inputStream
	* @param @return 
	* @return Properties    �������� 
	* @throws
	 */
	public static Properties getProperties(InputStream inputStream) {
		Properties properties = new Properties() ;
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
