package com.exceltool.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
*
* @Description: TODO	文件选择监听器
* @author zhaowei 
* @Ceatetime 2014年6月30日
*
 */
public class ShowDetailListener implements ActionListener {
	
	private String value ;
	private String title ;
	
	public ShowDetailListener(String value, String title) {
		this.value = value;
		this.title = title;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, value, title, JOptionPane.PLAIN_MESSAGE); 
	}
	
	
}
