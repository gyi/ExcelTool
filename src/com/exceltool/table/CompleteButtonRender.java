package com.exceltool.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JTable;

import com.exceltool.message.MessageLogTable;

public class CompleteButtonRender extends ButtonRender
{

	private static final long serialVersionUID = 1L;

	public CompleteButtonRender(Map<Integer, String> fileList)
    {
    	super(fileList);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
    {
    	//Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 3, 0);
    	Color color = null;
    	if (row % 2 == 0)
    		color = Color.white; // 设置奇数行底色
		else if (row % 2 == 1)
			color = new Color(224,255,255); // 设置偶数行底色
    	initPanel();
    	String title = (String) table.getModel().getValueAt(0, column);
    	String a = (String) value;
    	if(a==null||a.equals("")){
			setButtonLable(table, color, a, title, "", Color.white, false);
		}else if (MessageLogTable.getInstance(fileList.get(row)).isResult()) {
			setButtonLable(table, color, a, title, "全部成功", Color.green, false);
			table.isCellEditable(row, column);
		}else if (!MessageLogTable.getInstance(fileList.get(row)).isResult()&&MessageLogTable.getInstance(fileList.get(row)).isBlank()) {
			setButtonLable(table, color, a, title, "数据为空", Color.orange, true);
		}else {
			setButtonLable(table, color, a, title, "部分失败", Color.red, true);
		}	
		
        return this.panel;
    }

}