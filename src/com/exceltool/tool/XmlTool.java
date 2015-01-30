package com.exceltool.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exceltool.data.FieldExcel;
import com.exceltool.data.RankStatus;
import com.exceltool.message.MessageLogTable;

/**
 * xml与Excel的生成工具
 * 
 * @Description: TODO
 * @author zhaowei
 * @Ceatetime 2014年6月3日
 * 
 */
public class XmlTool {

	private static Logger logger = LoggerFactory.getLogger(XmlTool.class);

	public static String getDefName(String pathName) {
		return pathName.substring(pathName.lastIndexOf('.') + 1, pathName.length());
	}

	public static boolean allNullData(List<List<FieldExcel>> xmlDef) {
		boolean isNull = false;
		for (List<FieldExcel> excels : xmlDef) {
			for (FieldExcel excel : excels) {
				if (excel != null) {
					isNull = true;
					break;
				}
			}
		}
		return isNull;
	}

	/**
	 * @throws IOException
	 *             将excel中传入的数据转换为xml
	 * 
	 * @param messages
	 *            描述数据
	 * @param treemap
	 *            传入的数据(具体详细数据)
	 * @return void 返回类型
	 * @throws
	 */
	public static boolean convertExcel2XML(List<List<FieldExcel>> xmlDef, String version, String sheetName,
			int sheetId, String right, String path) throws IOException {
		if (xmlDef == null || !allNullData(xmlDef)) {

			return false;
		}
		String str[] = sheetName.split("=");
		String clazzName = str[0];
		String xmlName = getDefName(clazzName) + "_" + sheetId + Suffix.XML;

		// 创建一个xml文档的 根节点
		Document document = new Document();
		// 创建一个xml文档的 根元素节点
		Element root = new Element(XmlLabel.DEFS_LABEL);
		root.setAttribute(XmlLabel.CLASS_ATTR, clazzName);
		// 把这个root根元素加到这个document文档下
		document.addContent(root);

		for (int i = 0; i < xmlDef.size(); i++) {
			List<FieldExcel> listContent = xmlDef.get(i);
			boolean isIn = false;
			for (FieldExcel excel : listContent) {
				if (excel != null && !StringUtils.isEmpty(excel.getValue())) {
					isIn = true;
				}
			}

			if (!isIn)
				continue;
			// def元素添加
			Element def = new Element(XmlLabel.DEF_LABEL);
			root.addContent(def);
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < listContent.size(); j++) {
				FieldExcel fieldExcel = listContent.get(j);
				Element elementField = new Element(XmlLabel.FIELD_LABEL);
				elementField.setAttribute(XmlLabel.NAME_ATTR, fieldExcel.getName());
				elementField.setText(fieldExcel.getValue());
				map.put(fieldExcel.getName(), fieldExcel.getValue());
				def.addContent(elementField);
			}

		}

		// 把创建的xml文档写到流中
		XMLOutputter out = new XMLOutputter();
		// 设置生成xml文档的格式
		Format format = Format.getPrettyFormat();
		// 自定义xml文档的缩进(敲了四个空格，代表四个缩进)
		format.setIndent("    ");
		out.setFormat(format);
		path = path + "/" + version;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}

		// deleteExistFile(file, getDefName(clazzName));
		OutputStream output = new FileOutputStream(path + "/" + xmlName);
		try {
			if (right.equals("s")) {
				// System.out.println("s: "+xmlName);
				out.output(document, output);
			} else if (right.equals("l")) {
				// System.out.println("l: "+path + "/" + xmlName);
				out.output(document, output);
			}

			logger.debug("convert excel to xml success");
			MessageLogTable.addMessageSheetList(true, right, sheetName.split("=", 2)[1], "恭喜",
					RankStatus.OTHER.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// output.flush();
			output.close();
		}
		return true;
	}

	/**
	 * @throws IOException
	 *             将excel中传入的数据转换为xml
	 * 
	 * @param messages
	 *            描述数据
	 * @param treemap
	 *            传入的数据(具体详细数据)
	 * @return void 返回类型
	 * @throws
	 */
	public static boolean convertExcel2XML2(List<List<FieldExcel>> xmlDef, String version, String sheetName,
			int sheetId, String right, String path) throws IOException {
		if (xmlDef == null || !allNullData(xmlDef)) {

			return false;
		}
		String str[] = sheetName.split("=");
		String clazzName = str[0];
		String xmlName = getDefName(clazzName) + "_" + sheetId + Suffix.XML;

		// 创建一个xml文档的 根节点
		Document document = new Document();
		// 创建一个xml文档的 根元素节点
		Element root = new Element(XmlLabel.DEFS_LABEL);
		root.setAttribute(XmlLabel.CLASS_ATTR, getDefName(clazzName));
		// 把这个root根元素加到这个document文档下
		document.addContent(root);

		for (int i = 0; i < xmlDef.size(); i++) {
			List<FieldExcel> listContent = xmlDef.get(i);
			boolean isIn = false;
			for (FieldExcel excel : listContent) {
				if (excel != null && !StringUtils.isEmpty(excel.getValue())) {
					isIn = true;
				}
			}

			if (!isIn)
				continue;
			// def元素添加
			Element def = new Element(XmlLabel.DEF_LABEL);
			root.addContent(def);
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < listContent.size(); j++) {
				FieldExcel fieldExcel = listContent.get(j);
				Element elementField = new Element(fieldExcel.getName());
				elementField.setAttribute(XmlLabel.VALUE_ATTR, fieldExcel.getValue());
				map.put(fieldExcel.getName(), fieldExcel.getValue());
				def.addContent(elementField);
			}

		}

		// 把创建的xml文档写到流中
		XMLOutputter out = new XMLOutputter();
		// 设置生成xml文档的格式
		Format format = Format.getPrettyFormat();
		// 自定义xml文档的缩进(敲了四个空格，代表四个缩进)
		format.setIndent("    ");
		out.setFormat(format);
		path = path + "/" + version;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}

		// deleteExistFile(file, getDefName(clazzName));
		OutputStream output = new FileOutputStream(path + "/" + xmlName);
		try {
			if (right.equals("s")) {
				// System.out.println("s: "+xmlName);
				out.output(document, output);
			} else if (right.equals("l")) {
				// System.out.println("l: "+path + "/" + xmlName);
				out.output(document, output);
			}

			logger.debug("convert excel to xml success");
			MessageLogTable.addMessageSheetList(true, right, sheetName.split("=", 2)[1], "恭喜",
					RankStatus.OTHER.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// output.flush();
			output.close();
		}
		return true;
	}

	/*
	 * private static void deleteExistFile(File file, String fileName) {
	 * for(String killedFile: NoteKilledFile.getKilledFile()){
	 * if(killedFile.equals(fileName)){ return; } } File[] isExistFiles =
	 * file.listFiles(); for(File isExistFile: isExistFiles){
	 * if(isExistFile.isFile()){ Pattern pattern =
	 * Pattern.compile("[^/\\\\]+$"); Matcher matcher =
	 * pattern.matcher(isExistFile.getPath()); if(matcher.find()) { String find
	 * = matcher.group(); Pattern pattern2 = Pattern.compile(fileName+"_\\d*");
	 * Matcher matcher2 = pattern2.matcher(find); if(matcher2.find()){
	 * NoteKilledFile.addKilledFile(fileName); isExistFile.deleteOnExit(); } } }
	 * }
	 * 
	 * }
	 */
}
