package com.exceltool.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.exceltool.table.ExcelTableModel;
import com.exceltool.listener.FileChooseListner;
import com.exceltool.listener.PackageFindListener;
import com.exceltool.listener.SelectedAllListner;
import com.exceltool.listener.SubmitListener;
import com.exceltool.message.MessageLogTable;


public class ExcelPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//传入文件标签
	private JLabel jLabel ;
	//传入文件框
	private JTextField field ;
	//文件选择按钮
	private JButton jButton ;
	
	//传入文件标签
	private JLabel jLabel_output ;
	//传入文件框
	private JTextField field_output ;
	//文件选择按钮
	private JButton jButton_output ;
	
	//表格模板
	private ExcelTableModel model ;
	//表格
	private JTable table ;

	//保存按钮
	private JButton jsubmit ;
	//全选按钮
	private JButton jButton_selectAll ;
	
	private boolean selectAll = true;
	
	public ExcelPanel(int width, int height) {
		//设置大小
		this.setSize(width, height);
		//设置背景颜色
		this.setBackground(Color.LIGHT_GRAY);
		//设置焦点
		this.setFocusable(true);
		
		this.setLayout(null);
		
		this.setLocation(0, 0);
		
		this.setOpaque(true);
		
		//传入文件标签
		jLabel = new JLabel("文件：") ;
		jLabel.setLocation(50, 50);
		jLabel.setSize(80, 30);
		//传入文件框
		field = new JTextField("尚未选择文件") ;
		field.setLocation(120, 50);
		field.setSize(150, 30);
		//不可编辑
		field.setEditable(false);
		field.setVisible(true);
		//文件选择按钮
		jButton = new JButton("选择文件") ;
		jButton.setLocation(290, 50);
		jButton.setSize(90, 30);
//		jButton.setIcon();
		
		//输出文件标签
		jLabel_output = new JLabel("输出路径：") ;
		jLabel_output.setLocation(50, 100);
		jLabel_output.setSize(80, 30);
		//输出文件框
		field_output = new JTextField("尚未选择输出文件路径") ;
		//不可编辑
		field_output.setEditable(false);
		field_output.setVisible(true);
		field_output.setLocation(120, 100);
		field_output.setSize(150, 30);
		
		//输出文件路径选择按钮
		jButton_output = new JButton("选择路径") ;
		jButton_output.setLocation(290, 100);
		jButton_output.setSize(90, 30);
		
		//提交
		jsubmit = new JButton("开始解析") ;
		jsubmit.setLocation(290, 150);
		jsubmit.setSize(90, 30);
		
		//输出文件路径选择按钮
		jButton_selectAll = new JButton("全选") ;
		jButton_selectAll.setLocation(50, 150);
		jButton_selectAll.setSize(65, 30);
		
		//表格
		model = new ExcelTableModel() ;
		table = new JTable(model) ;
		table.setFont(new Font("微软雅黑", 0, 20));
		table.setRowHeight(30);
		
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (table.isCellSelected(table.getSelectedRow(),
						table.getSelectedColumn())) {

					int col = table.getSelectedColumn();
					if(col==2&&MessageLogTable.getInstance(table.getSelectedRow())!=null&&!MessageLogTable.getInstance(table.getSelectedRow()).isResult()){
						String value = ((String) table.getValueAt(
								table.getSelectedRow(), table.getSelectedColumn()));
						String title = ((String) table.getValueAt(
								0, table.getSelectedColumn()));
						
						JOptionPane.showMessageDialog(null, value, title, JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			}
		});
		
		JScrollPane paneTable = new JScrollPane(table);
		paneTable.setLocation(50, 200);
		paneTable.setSize(1800, 750);
		
		FileChooseListner.setDefaultPath(this);
		
		this.add(field) ;
		this.add(jLabel) ;
		this.add(jButton) ;
		this.add(field_output) ;
		this.add(jLabel_output) ;
		this.add(jButton_output) ;
		this.add(jsubmit) ;
		this.add(jButton_selectAll);
		this.add(paneTable) ;
		
		jButton.addActionListener(new FileChooseListner(this));
		jButton_output.addActionListener(new PackageFindListener(field_output));
		jButton_selectAll.addActionListener(new SelectedAllListner(this, this.selectAll));
		jsubmit.addActionListener(new SubmitListener(this));
		
		repaint();
		
	}
	

	public JLabel getjLabel() {
		return jLabel;
	}

	public void setjLabel(JLabel jLabel) {
		this.jLabel = jLabel;
	}

	public JTextField getField() {
		return field;
	}

	public void setField(JTextField field) {
		this.field = field;
	}

	public JButton getjButton() {
		return jButton;
	}

	public void setjButton(JButton jButton) {
		this.jButton = jButton;
	}

	public JLabel getjLabel_output() {
		return jLabel_output;
	}

	public void setjLabel_output(JLabel jLabel_output) {
		this.jLabel_output = jLabel_output;
	}

	public JTextField getField_output() {
		return field_output;
	}

	public void setField_output(JTextField field_output) {
		this.field_output = field_output;
	}

	public JButton getjButton_output() {
		return jButton_output;
	}

	public void setjButton_output(JButton jButton_output) {
		this.jButton_output = jButton_output;
	}

	public ExcelTableModel getModel() {
		return model;
	}

	public void setModel(ExcelTableModel model) {
		this.model = model;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getJsubmit() {
		return jsubmit;
	}

	public void setJsubmit(JButton jsubmit) {
		this.jsubmit = jsubmit;
	}

	public JButton getjButton_selectAll() {
		return jButton_selectAll;
	}

	public void setjButton_selectAll(JButton jButton_selectAll) {
		this.jButton_selectAll = jButton_selectAll;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}
	
	
}
