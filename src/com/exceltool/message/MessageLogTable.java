package com.exceltool.message;

import java.util.HashMap;
import java.util.Map;

import com.exceltool.data.RankStatus;

public class MessageLogTable {
	private String tableName;
	private Boolean result;
	private static Map<String,MessageLogSheet> messageLogSheetServerMap = new HashMap<String, MessageLogSheet>();
	private static Map<String,MessageLogSheet> messageLogSheetClientMap = new HashMap<String, MessageLogSheet>();
	private String commonMessage;
	private String message;
	private boolean blank = true;
	private static Map<String, MessageLogTable> messageLogTableMap = new HashMap<String, MessageLogTable>();
	private static Map<Integer, String> excelToIndex = new HashMap<Integer, String>();
	
	public MessageLogTable(String fileName) {
		this.tableName = fileName;
		commonMessage = "";
		result = true;
		blank = true;
		messageLogSheetServerMap = new HashMap<String, MessageLogSheet>();
		messageLogSheetClientMap = new HashMap<String, MessageLogSheet>();
	}



	/**
     * 静态工厂方法,返回指定登记对象的唯一实例;
     * 对于已登记的直接取出返回,对于还未登记的,先登记,然后取出返回
     * @param name
     * @return RegSingleton
     */
    public static MessageLogTable getInstance(String fileName) {
        if (fileName == null) {
        	return null;
        }
        if (messageLogTableMap.get(fileName) == null) {
            messageLogTableMap.put(fileName, new MessageLogTable(fileName));
        }
        return messageLogTableMap.get(fileName);
    }
    
    public static MessageLogTable getInstance(String fileName, int fileIndex) {
        if (fileName == null) {
        	return null;
        }
        if (messageLogTableMap.get(fileName) == null) {
            messageLogTableMap.put(fileName, new MessageLogTable(fileName));
            excelToIndex.put(fileIndex, fileName);
        }
        return messageLogTableMap.get(fileName);
    }
    
    public static MessageLogTable getInstance(int fileIndex) {
    	return messageLogTableMap.get(excelToIndex.get(fileIndex));
    }
    
    /**
	 * 重新加载系统文件信息
	 */
	public static void loadSystemConfig() {
		messageLogTableMap = new HashMap<String, MessageLogTable>();
		excelToIndex = new HashMap<Integer, String>();
	}
	
	public static void addMessageSheetList(boolean isSuccess, String right, String sheet, String message, int rank){
		if(right == "s"){
			messageLogSheetServerMap.put(sheet, new MessageLogSheet(isSuccess, right, sheet, message, rank));
			if (messageLogSheetClientMap.get(sheet)!=null&&messageLogSheetClientMap.get(sheet).getRank() > rank) {
				messageLogSheetClientMap.put(sheet, new MessageLogSheet(isSuccess, right, sheet, message, rank, false));
			}
			
		}else if (right == "l") {
			messageLogSheetClientMap.put(sheet, new MessageLogSheet(isSuccess, right, sheet, message, rank));
			if (messageLogSheetServerMap.get(sheet)!=null&&messageLogSheetServerMap.get(sheet).getRank() > rank) {
				messageLogSheetServerMap.put(sheet, new MessageLogSheet(isSuccess, right, sheet, message, rank, false));
			}
			
		}
	}
	
	public boolean judgeResult(String sheetName){
		boolean result = true;
		boolean blank = true;
		/*if((messageLogSheetServerMap.get(sheetName).getRank()==RankStatus.PRIVILEGE.getValue()&&messageLogSheetServerMap.get(sheetName).getResult())||(messageLogSheetClientMap.get(sheetName).getRank()==RankStatus.PRIVILEGE.getValue()&&messageLogSheetClientMap.get(sheetName).getResult())){
			result = true;
		}
		else{
			result = false;
			blank = false;
		}*/
		
		if((messageLogSheetServerMap.get(sheetName).getRank()==RankStatus.DATA.getValue()&&messageLogSheetClientMap.get(sheetName).getRank()==RankStatus.DATA.getValue())){
			result = false;
			blank = true;
			
		}
		if((messageLogSheetClientMap.get(sheetName).getRank()==RankStatus.DATA.getValue()&&messageLogSheetServerMap.get(sheetName).getRank()==RankStatus.DATA.getValue())){
			result = false;
			blank = true;
		}
		
		if(messageLogSheetServerMap.get(sheetName).getRank()==RankStatus.OTHER.getValue()&&!messageLogSheetServerMap.get(sheetName).getResult()){
			result = false;
			blank = false;
		}
		if(messageLogSheetClientMap.get(sheetName).getRank()==RankStatus.OTHER.getValue()&&!messageLogSheetClientMap.get(sheetName).getResult()){
			result = false;
			blank = false;
		}
		if(messageLogSheetClientMap.get(sheetName).getRank()==RankStatus.OTHER.getValue()&&messageLogSheetClientMap.get(sheetName).getResult()){
			result = true;
		}
		if(messageLogSheetClientMap.get(sheetName).getRank()==RankStatus.OTHER.getValue()&&messageLogSheetClientMap.get(sheetName).getResult()){
			result = true;
		}
		this.result = this.result&&result;
		this.blank = this.blank&&blank;
		return result;
	}
	
	public String formatMessageListOnlyFail(){
		message = tableName+"\n"+commonMessage+"\n";
		for(String sheetName: messageLogSheetServerMap.keySet()){
			boolean result = judgeResult(sheetName);
			if(!result&&!messageLogSheetServerMap.get(sheetName).getResult()&&messageLogSheetServerMap.get(sheetName).isShow()){
				message += messageLogSheetServerMap.get(sheetName).toString()+"\n";
			}
			if(!result&&!messageLogSheetClientMap.get(sheetName).getResult()&&messageLogSheetClientMap.get(sheetName).isShow()){
				message += messageLogSheetClientMap.get(sheetName).toString()+"\n";
			}
		}
		return message;
		
	}
	
	public String formatMessageListAll(){
		message = tableName+"\n"+commonMessage+"\n";
		for(String sheetName: messageLogSheetServerMap.keySet()){
			judgeResult(sheetName);
			if(messageLogSheetServerMap.get(sheetName).getResult()){
				message += messageLogSheetServerMap.get(sheetName).toString()+"\n";
			}
			if(messageLogSheetClientMap.get(sheetName).getResult()){
				message += messageLogSheetClientMap.get(sheetName).toString()+"\n";
			}
			if(!messageLogSheetServerMap.get(sheetName).getResult()&&!messageLogSheetClientMap.get(sheetName).getResult()){
				message += messageLogSheetServerMap.get(sheetName).toString()+"\n";
				message += messageLogSheetClientMap.get(sheetName).toString()+"\n";
			}
		}
		return message;
	}

	public void setCommonMessage(String commonMessage) {
		result = false;
		this.commonMessage = commonMessage;
	}

	public Boolean isResult() {
		return result;
	}



	public boolean isBlank() {
		return blank;
	}

	
}
