package com.exceltool.listener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang3.StringUtils;

import com.exceltool.panel.ExcelPanel;
import com.exceltool.table.ExcelTableModel;
import com.exceltool.table.MyCheckboxRender;

public class FileChooseListner implements ActionListener {

	private static List<String> fileList;
	// private List<JTextField> fieldList ;
	private static JTextField field;
	private static ExcelPanel panel;

	public FileChooseListner(ExcelPanel panel) {
		FileChooseListner.panel = panel;
		FileChooseListner.field = panel.getField();
	}

	public static void setDefaultPath(ExcelPanel panel) {
		FileChooseListner.panel = panel;
		FileChooseListner.field = panel.getField();
		File file = new File(System.getProperty("user.dir")).getParentFile();
		String[] children = showFileList(selectPathButtonActionPerformedByFile(file));
		FileListOnTable(children);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			String path = new File(System.getProperty("user.dir")).getParent();
			JFileChooser chooser = new JFileChooser(StringUtils.isEmpty(field.getText())
					|| field.getText().equals("尚未选择文件") ? path : field.getText());
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			// 过滤
			final List<String> suffixList = new ArrayList<String>();
			suffixList.add("xls");
			suffixList.add("xlsx");
			filterSuffixByXls(chooser, suffixList);
			int temp = chooser.showOpenDialog(null);

			String[] children = showFileList(selectPathButtonActionPerformedByChooser(chooser, temp));
			FileListOnTable(children);

		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return;

	}

	@SuppressWarnings("serial")
	private static void FileListOnTable(String[] children) {
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (row % 2 == 0)
					cell.setBackground(Color.white); // 设置奇数行底色
				else if (row % 2 == 1)
					cell.setBackground(new Color(224, 255, 255)); // 设置偶数行底色
				return cell;
			}
		};
		// 获得面板中的表格
		ExcelTableModel model = panel.getModel();
		model.removeAll();
		if (children != null) {
			fileList = new ArrayList<String>(children.length);
			for (int i = 0; i < children.length; i++) {
				fileList.add(i, children[i]);
			}

			model.addColumn("文件");
			model.addColumn("选择");
			model.addColumn("状态");
			for (String fileName : fileList) {
				List<Object> valResult = new ArrayList<Object>();
				valResult.add(fileName);
				valResult.add(false);
				valResult.add("");

				Object rowData[] = valResult.toArray();
				// model.addColumn(fileName);
				model.addRow(rowData);
			}
			for (int i = 0; i < panel.getTable().getColumnCount(); i++) {
				panel.getTable().getColumn(panel.getTable().getColumnName(i)).setCellRenderer(tcr);

			}

			panel.getTable().getColumn("选择").setCellRenderer(new MyCheckboxRender());
		}

		panel.getTable().updateUI();

	}

	/**
	 * 选择文件
	 * 
	 * @param chooser
	 */
	private String selectPathButtonActionPerformedByChooser(JFileChooser chooser, int temp) {
		chooser.setVisible(true);
		if (temp == JFileChooser.APPROVE_OPTION) {

			File selectedFile = chooser.getSelectedFile();
			if (selectedFile == null)
				return null;

			return selectPathButtonActionPerformedByFile(selectedFile);

		}
		return null;

	}

	/**
	 * 选择文件
	 * 
	 * @param chooser
	 */
	private static String selectPathButtonActionPerformedByFile(File selectedFile) {

		field.setText(selectedFile.getAbsolutePath());
		if (selectedFile.isDirectory()) {
			panel.getField_output().setText(selectedFile.getAbsolutePath() + "\\xml");
		}
		if (selectedFile.isFile()) {
			panel.getField_output().setText(selectedFile.getParentFile().getAbsolutePath() + "\\xml");
		}

		return selectedFile.getAbsolutePath();

	}

	/**
	 * 过滤
	 * 
	 * @param chooser
	 */
	private void filterSuffixByXls(JFileChooser chooser, final List<String> suffixList) {
		// chooser.setCurrentDirectory(new File("."));

		chooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				String name = f.getName();
				int p = name.lastIndexOf('.');
				if (p == -1)
					return false;
				String suffix = name.substring(p + 1).toLowerCase();
				return suffixList.contains(suffix);
			}

			@Override
			public String getDescription() {
				return "xls files";
			}

		});
	}

	public static String[] showFileList(String path) {
		if (path == null) {
			return null;
		}
		File dir = new File(path);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".xls") || name.endsWith(".xlsx")) {
					return true;
				}
				return false;
			}
		};
		String[] children = dir.list(filter);
		if (children == null) {
			System.out.println("Either dir does not exist or is not a directory");
		} else {
			/*
			 * for (int i = 0; i < children.length; i++) { children[i] = path +
			 * "\\" + children[i]; }
			 */
			return children;
		}
		return null;
	}
}
