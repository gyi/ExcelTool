package com.exceltool.data;

/**
 * 信息级别
 */
public enum RankStatus {
	/**
	 * 其他
	 */
	OTHER(0),
	/**
	 * 数据为空
	 */
	DATA(1),
	/**
	 * 权限未分配
	 */
	PRIVILEGE(2);
	
	private int value ;
	
	private RankStatus(int value) {
		this.value = value ;
	}
	
	public int getValue(){
		return value ;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
