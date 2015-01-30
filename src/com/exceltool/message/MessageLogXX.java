package com.exceltool.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageLogXX {
	
	/*
	private static String serverMessageList = "";
	private static String clientMessageList = "";
	*/
	/**
	 * Key="right"
	 * Key="sheet"
	 * Key="message"
	 */
	private static List<List<Map<String, Object>>> messageSheetList ;
	
	private static String messageList = "";
	//private static List<String> clientMessageSheetList ;

	
	public static void addAllMessage(String Message) {
		MessageLogXX.messageList+=Message ;
	}
	
	public static void initAllMessage() {
		messageSheetList = new ArrayList<List<Map<String, Object>>>();
		messageList = "";
	}
	
	public static String getAllMessage() {
		return messageList;
	}
	
	public static void addMessageSheetList(boolean isSuccess, String right, String sheet, String message){
		boolean isFind = false;
		for(List<Map<String, Object>> messageSheet: messageSheetList){
			if(right.equals("l")){
				if(messageSheet.get(0).get("sheet").equals(sheet)){
					messageSheet.get(0).put("message", message);
					messageSheet.get(0).put("isSuccess", isSuccess);
					isFind = true ;
				}
			}
			if(right.equals("s")){
				if(messageSheet.get(1).get("sheet").equals(sheet)){
					messageSheet.get(1).put("message", message);
					messageSheet.get(1).put("isSuccess", isSuccess);
					isFind = true ;
				}
			}
			
		}
		if(!isFind){
			List<Map<String, Object>> messageSheet = new ArrayList<Map<String, Object>> (2);
			Map<String, Object> messageSheetMap = new HashMap<String, Object> ();
			messageSheetMap.put("sheet", sheet);
			messageSheetMap.put("message", message);
			messageSheetMap.put("isSuccess", isSuccess);
			
			Map<String, Object> messageSheetMapNull = new HashMap<String, Object> ();
			messageSheetMapNull.put("sheet", sheet);
			messageSheetMapNull.put("message", null);
			messageSheetMapNull.put("isSuccess", null);
			if(right.equals("l")){
				messageSheet.add(0, messageSheetMap) ;
				messageSheet.add(1, messageSheetMapNull) ;
				
			}
			if(right.equals("s")){
				messageSheet.add(0, messageSheetMapNull) ;
				messageSheet.add(1, messageSheetMap) ;
			}
			messageSheetList.add(messageSheet);
			
		}
		
	}
	
	public static String formatMessageList(String fileName) {
		
		for(List<Map<String, Object>> messageSheet: messageSheetList){			
			
			if(((boolean)messageSheet.get(0).get("isSuccess")&&(boolean)messageSheet.get(1).get("isSuccess"))||(!(boolean)messageSheet.get(0).get("isSuccess")&&!(boolean)messageSheet.get(1).get("isSuccess"))){
				messageList = messageList+messageSheet.get(1).get("sheet")+"在服务器";
				if(!(boolean)messageSheet.get(1).get("isSuccess")){
					messageList = messageList+"解析失败: " ;
				}
				else if((boolean)messageSheet.get(1).get("isSuccess")){
					messageList = messageList+"解析成功: " ;
				}
				messageList = messageList+messageSheet.get(1).get("message")+"\n";
				
				messageList = messageList+messageSheet.get(0).get("sheet")+"在客户端";
				if(!(boolean)messageSheet.get(0).get("isSuccess")){
					messageList = messageList+"解析失败: " ;
				}
				else if((boolean)messageSheet.get(0).get("isSuccess")){
					messageList = messageList+"解析成功: " ;
				}
				messageList = messageList+messageSheet.get(0).get("message")+"\n";
			}
			else{
				if(((boolean)messageSheet.get(0).get("isSuccess")||(boolean)messageSheet.get(1).get("isSuccess"))&&!(boolean)messageSheet.get(0).get("isSuccess")){
					//MessageLog.initClientMessage();
					messageList = messageList+messageSheet.get(1).get("sheet")+"在服务器 ";
					if(!(boolean)messageSheet.get(1).get("isSuccess")){
						messageList = messageList+"解析失败: " ;
					}
					else if((boolean)messageSheet.get(1).get("isSuccess")){
						messageList = messageList+"解析成功: " ;
					}
					messageList = messageList+messageSheet.get(1).get("message")+"\n";
				}
				else if(((boolean)messageSheet.get(0).get("isSuccess")||(boolean)messageSheet.get(1).get("isSuccess"))&&!(boolean)messageSheet.get(1).get("isSuccess")){
					messageList = messageList+messageSheet.get(0).get("sheet")+"在客户端 ";
					if(!(boolean)messageSheet.get(0).get("isSuccess")){
						messageList = messageList+"解析失败: " ;
					}
					else if((boolean)messageSheet.get(0).get("isSuccess")){
						messageList = messageList+"解析成功: " ;
					}
					messageList = messageList+messageSheet.get(0).get("message")+"\n";
				}
			}

		}
		messageList = fileName+"\n" + messageList ;
		return messageList;
	}
	
}
