package com.exceltool.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyCheckboxRender implements TableCellRenderer
{
    private JPanel panel;

    private JCheckBox checkBox ;

    public MyCheckboxRender()
    {
        this.initPanel();
    }

    private void initPanel()
    {
        this.panel = new JPanel();

        // panel使用绝对定位，这样button就不会充满整个单元格。
        //this.panel.setLayout(null);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
    {
    	Color color = null;
    	if (row % 2 == 0)
    		color = Color.white; // 设置奇数行底色
		else if (row % 2 == 1)
			color = new Color(224,255,255); // 设置偶数行底色
    	initPanel();
    	checkBox = new JCheckBox();
		checkBox.setSelected((boolean)value);
		checkBox.setBackground(color);
		panel.setBackground(color);
    	panel.add(checkBox);

        return this.panel;
    }

}