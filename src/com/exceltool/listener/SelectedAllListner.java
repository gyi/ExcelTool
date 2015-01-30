package com.exceltool.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.exceltool.panel.ExcelPanel;
import com.exceltool.table.ExcelTableModel;


public class SelectedAllListner implements ActionListener {

	private ExcelTableModel model ;
	
	private boolean selectedAll;
	
	public SelectedAllListner(ExcelPanel panel, boolean selectedAll) {
		this.model = panel.getModel();
		this.selectedAll = selectedAll ;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			for(int i = 0; i < model.getRowCount(); i++){
					model.setValueAt(selectedAll, i, 1);
			}
			selectedAll = !selectedAll;
		} 
		catch(Exception e2) {
			e2.printStackTrace();
		}
		return ;
		
	}


}
