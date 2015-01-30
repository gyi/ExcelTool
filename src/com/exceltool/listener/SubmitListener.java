package com.exceltool.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import com.exceltool.configload.Right;
import com.exceltool.data.IsCloseData;
import com.exceltool.data.NoteKilledFile;
import com.exceltool.message.MessageLogTable;
import com.exceltool.panel.ExcelPanel;
import com.exceltool.table.CompleteButtonRender;
import com.exceltool.table.ExcelTableModel;
import com.exceltool.table.ReadyButtonRender;
import com.exceltool.tool.ExcelTool;
import com.exceltool.tool.SSSFTransUnit;

public class SubmitListener implements ActionListener {

	private ExcelPanel panel;

	private JTextField field;

	private ExcelTableModel model;

	private JTextField field_output;

	public SubmitListener(ExcelPanel panel) {
		this.panel = panel;
		this.field = panel.getField();
		this.model = panel.getModel();
		this.field_output = panel.getField_output();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			IsCloseData.setClose(false);
			for (int i = 0; i < model.getRowCount(); i++) {
				model.setValueAt("", i, 2);
				model.setValueAt("", i, 3);
			}

			// 传出文件路径
			String file_output_path = field_output.getText();
			// 传出文件
			File file_output = new File(file_output_path);
			// 判断文件是否存在此路径
			if (file_output_path == null || file_output_path.trim().equals("") || file_output == null
					|| !file_output.exists()) {
				// JOptionPane.showMessageDialog(null, "请确认文件路径");
				JOptionPane.showMessageDialog(null, "请确认文件路径");
				return;
			}
			deleteExistFile(new File(file_output_path + "/client"));
			deleteExistFile(new File(file_output_path + "/server"));
			Map<Integer, String> fileList = findCheckFile();
			TableColumn column = panel.getTable().getColumn("状态");
			column.setCellRenderer(new ReadyButtonRender(fileList));
			NoteKilledFile.toInit();
			MessageLogTable.loadSystemConfig();
			for (int index : fileList.keySet()) {
				MessageLogTable.getInstance(fileList.get(index), index);

				convertFile(fileList.get(index));

				if (index == -1) {
					JOptionPane.showMessageDialog(null, MessageLogTable.getInstance(fileList.get(index))
							.formatMessageListAll());
					return;
				}
				model.setValueAt(MessageLogTable.getInstance(fileList.get(index)).formatMessageListOnlyFail(), index, 2);
			}

			column.setCellRenderer(new CompleteButtonRender(fileList));
			IsCloseData.setClose(true);
		} catch (Exception e2) {
			e2.printStackTrace();
			// JOptionPane.showMessageDialog(null, "解析异常，如若多次尝试后仍然失败，请找程序!!!");
			JOptionPane.showMessageDialog(null, e2);
			// MessageLogTable.getInstance(filePath).setCommonMessage("解析失败：如若多次尝试后仍然失败，请找程序!!! Σ(っ °Д °;)っ");
		} finally {
			IsCloseData.setClose(true);
		}

	}

	private static void deleteExistFile(File file) {
		File[] isExistFiles = file.listFiles();
		if (isExistFiles == null) {
			return;
		}
		for (File isExistFile : isExistFiles) {
			if (isExistFile.isFile()) {
				isExistFile.delete();
			}
		}

	}

	private Map<Integer, String> findCheckFile() {
		// 行数
		int rowNum = model.getRowCount();
		// 列数
		int colNum = model.getColumnCount();

		Map<Integer, String> fileMap = new HashMap<Integer, String>();
		if (rowNum == 0 && colNum == 0) {
			fileMap.put(-1, field.getText());
		}
		for (int i = 0; i < rowNum; i++) {
			if (model.getValueAt(i, 1).equals(true)) {
				fileMap.put(i, field.getText() + "\\" + (String) model.getValueAt(i, 0));
			}
		}
		return fileMap;
	}

	private void convertFile(String filePath) {
		if (filePath == null)
			return;
		// 传入文件
		File file = new File(filePath);
		// 判断文件是否存在
		if (filePath.trim().equals("") || file == null || !file.exists()) {
			// JOptionPane.showMessageDialog(null, "请确认文件");
			MessageLogTable.getInstance(filePath).setCommonMessage("解析失败：请确认文件");
			return;
		}
		String fileName = file.getName();
		// 判断是否为Excel文件
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			// JOptionPane.showMessageDialog(null, "传入文件必须为excel文件");
			MessageLogTable.getInstance(filePath).setCommonMessage("解析失败：传入文件必须为excel文件");
			return;
		}
		SSSFTransUnit.FileType(fileName);
		// 传出文件路径
		String file_output_path = field_output.getText();
		// 传出文件
		File file_output = new File(file_output_path);

		try {
			ExcelTool.convertXmlConvenient(file, "client", "客户端", Right.CLIENT, file_output.getAbsolutePath());
			ExcelTool.convertXmlConvenient(file, "server", "服务器端", Right.SERVER, file_output.getAbsolutePath());

			return;
		} catch (Exception e2) {
			e2.printStackTrace();
			// JOptionPane.showMessageDialog(null, "解析异常，如若多次尝试后仍然失败，请找程序!!!");
			JOptionPane.showMessageDialog(null, e2);
			MessageLogTable.getInstance(filePath).setCommonMessage("解析失败：如若多次尝试后仍然失败，请找程序!!! Σ(っ °Д °;)っ");
			return;
		}
	}

}
