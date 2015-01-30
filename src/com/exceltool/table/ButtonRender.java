package com.exceltool.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.exceltool.listener.ShowDetailListener;

public abstract class ButtonRender extends DefaultTableCellRenderer
{
	protected static final long serialVersionUID = 1L;

	protected JPanel panel;

    protected JLabel label ;
    
    protected JButton button;
    
    Map<Integer, String> fileList;

    public ButtonRender(Map<Integer, String> fileList)
    {
    	this.fileList = fileList;
        this.initPanel();
    }

    protected void initPanel()
    {
        this.panel = new JPanel();

        // panel使用绝对定位，这样button就不会充满整个单元格。
        this.panel.setLayout(null);
    }
    
    public void setButtonLable(JTable table, Color backgroundColor, String value, String title, String text, Color color, boolean isbutton){
    	this.label = new JLabel(text);
    	this.label.setForeground(color);
    	this.label.setFont(table.getFont());
    	this.label.setBackground(backgroundColor);
    	this.label.setBounds(0, 0, text.length()*20, table.getRowHeight());
    	this.panel.add(this.label);
    	if(isbutton){
    		String buttonText = "查看详情";
    		this.button = new JButton(buttonText);
    		this.button.setFont(new Font(table.getFont().getFamily(), 0, (int)(table.getFont().getSize()/1.3)));
    		this.button.setBounds(label.getWidth(), 0, buttonText.length()*30, table.getRowHeight());
    		this.button.setBackground(backgroundColor);
    		this.button.addActionListener(new ShowDetailListener(value, title));
            this.panel.add(this.button);
    	}
    	this.panel.setBackground(backgroundColor);
		
    }

    public abstract Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
            int column);

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

}