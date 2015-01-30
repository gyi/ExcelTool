package com.exceltool.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

public class PackageFindListener implements ActionListener {
	private JTextField field ;
	
	public PackageFindListener(JTextField field) {
		this.field = field ;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String path = System.getProperty("user.dir") ;
		File files[] = new File(path).getParentFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.equals("xml")) {
					return true ;
				}
				return false;
			}
		}) ;
		File filepath = files==null||files.length==0?new File(path):files[0] ;
		
		JFileChooser chooser = new JFileChooser(filepath) ;
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showSaveDialog(null) ;
		
		File dir = chooser.getSelectedFile() ;
		
		if(dir==null||StringUtils.isEmpty(dir.getAbsolutePath())){
			JOptionPane.showMessageDialog(null, "请传入路径");
			return ;
		}
		
		String realPath = dir.getAbsolutePath();
		field.setText(realPath);
	}

}
