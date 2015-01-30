package com.exceltool.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JTable;

public class ReadyButtonRender extends ButtonRender
{

	private static final long serialVersionUID = 1L;

	public ReadyButtonRender(Map<Integer, String> fileList)
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
		setButtonLable(table, color, a, title, "正在解析", Color.lightGray, false);
		
        return this.panel;
    }

}