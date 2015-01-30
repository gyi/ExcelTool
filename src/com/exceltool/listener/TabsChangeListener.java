package com.exceltool.listener;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabsChangeListener implements ChangeListener {
	//当前所选中的面板
	private JTabbedPane tabs ;
	
	public TabsChangeListener(JTabbedPane tabs) {
		this.tabs = tabs ;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(tabs.getSelectedIndex()!=1)return ;
		JOptionPane.showInputDialog(null, "传入路径") ;
	}

	public JTabbedPane getTabs() {
		return tabs;
	}

	public void setTabs(JTabbedPane tabs) {
		this.tabs = tabs;
	}
}
