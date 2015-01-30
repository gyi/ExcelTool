package com.exceltool.message;

import com.exceltool.data.RankStatus;

public class MessageLogSheet {
	private String sheetName;
	private String right;
	private Boolean result;
	private String message;
	private boolean isShow;
	/**
	 * 0为权限
	 * 1为其他
	 * 2为数据为空
	 */
	private int rank;
	
	public MessageLogSheet(boolean isSuccess, String right, String sheet, String message, int rank){
		this.result = isSuccess;
		this.right = right;
		this.sheetName = sheet;
		this.message = message;
		this.rank = rank;
		this.isShow = true;
	}
	
	public MessageLogSheet(boolean isSuccess, String right, String sheet, String message, int rank, boolean isShow){
		this.result = isSuccess;
		this.right = right;
		this.sheetName = sheet;
		this.message = message;
		this.rank = rank;
		this.isShow = isShow;
	}
	
	public String toString(){
		String rightInfo = "";
		if(right.equals("s")){
			rightInfo = "服务器";
		}else if (right.equals("l")) {
			rightInfo = "客户端";
		}
		String resultInfo = "";
		if(result){
			resultInfo = "解析成功";
		}else if (rank==RankStatus.DATA.getValue()) {
			resultInfo = "数据为空";
		}else {
			resultInfo = "解析失败";
		}
		return sheetName+"在"+rightInfo+"中"+resultInfo+": "+message;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	
	
}
