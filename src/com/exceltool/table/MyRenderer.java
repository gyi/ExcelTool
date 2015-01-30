package com.exceltool.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyRenderer  implements TableCellRenderer{
  public Component getTableCellRendererComponent(JTable table,Object value,
                                         boolean isSelected,
                                         boolean hasFocus,
                                         int row,
                                         int column){
	  JLabel jl=new JLabel();
	  jl.setFont(new Font("Dialog", 0, 15));   
	  if((Integer)value>0){
		  jl.setForeground(Color.RED);
	  }
	  jl.setBackground(Color.WHITE);
	  jl.setOpaque(true);
	  jl.setText(value.toString());
   return jl;
  }
}