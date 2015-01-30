package com.exceltool.data;

import java.util.ArrayList;
import java.util.List;

/** 
*
* @Description: TODO	字段描述�?用于将xml转换成对�?
* @author zhaowei 
* @Ceatetime 2014-5-30
* 
*/
public class MyField {
	//字段�?
	private String name ;
	//描述
	private String describle ;
	/**
	 * 0:基础数据
	 * 1:复合数据
	 * 2:基础类型数组
	 * 3:复合类型数组
	 */
	private int type ;
	private List<MyField> filed = new ArrayList<MyField>();
	//复杂数据类型的class
	private Class<?> clazz ;
	private int arrNum ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrible() {
		return describle;
	}
	public void setDescrible(String describle) {
		this.describle = describle;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public List<MyField> getFiled() {
		return filed;
	}
	public void setFiled(List<MyField> filed) {
		this.filed = filed;
	}
	public int getArrNum() {
		return arrNum;
	}
	public void setArrNum(int arrNum) {
		this.arrNum = arrNum;
	}
}
