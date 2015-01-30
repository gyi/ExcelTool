package com.exceltool.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties解析类
*
* @Description: TODO
* @author zhaowei 
* @Ceatetime 2014年6月11日
*
 */
public class PropertiesTool {
	
	/**
	 * 加载properties
	* @param @param inputStream
	* @param @return 
	* @return Properties    返回类型 
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
