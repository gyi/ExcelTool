package com.exceltool.frame;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.exceltool.listener.TabsChangeListener;
import com.exceltool.configload.SystemConfig;
import com.exceltool.configload.SystemLoad;
import com.exceltool.data.IsCloseData;
import com.exceltool.panel.ExcelPanel;

/**
 * 图形框架
*
* @Description: TODO
* @author zhaowei 
* @Ceatetime 2014年6月11日
*
 */
public class ExcelFrame extends JFrame {
	
	private static ExcelFrame frame ;

	private static final long serialVersionUID = 1L;
	
	private Properties properties ;
	
	private ExcelFrame() {

		//加载配置文件
		properties = SystemLoad.getSysProperties(SystemConfig.BASISC) ;
		//标题
		String title = (String) properties.get("exceltool.title") ;
		//获取屏幕大小
	    Rectangle rect=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		//宽度
	    int width = (int) rect.getWidth();
		//高度
//		int height = Integer.valueOf((String)properties.get("exceltool.height")) ;
	    int height = (int) rect.getHeight();
		//图标
		String imgPath = (String) properties.get("exceltool.littleImg") ;
		String realPath = SystemLoad.getPathInputStream(imgPath) ;
		ImageIcon imageIcon = new ImageIcon(realPath) ;
		//设置标题
		this.setTitle(title);
		//设置宽高
		this.setSize(width, height);
		//设置位置
		this.setLocationRelativeTo(null);
		//设置小图标
		this.setIconImage(imageIcon.getImage());
		//关闭监听
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				if(!IsCloseData.isClose()){
					Object[] options ={ "等待", "退出"};  
					int result = JOptionPane.showOptionDialog(null, "没有解析完，确定要退出么", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if(result==0){
						
					}else if(result==1){
						setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					}
				}
				else{
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}

		});
		
		this.setLayout(null);
		//大小不可变
		this.setResizable(false);
		//可见
		this.setVisible(true);
		
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.NORTH,
				   JTabbedPane.SCROLL_TAB_LAYOUT);
		
		String[] funcNames = { "文件转换工具"};
		
		ExcelPanel excelPanel = new ExcelPanel(width, height) ;
		
		excelPanel.setBackground(Color.white);
	    tabs.addTab(funcNames[0], excelPanel); // 加入一个页面
		
		this.add(excelPanel) ;
		
		tabs.addChangeListener(new TabsChangeListener(tabs)) ;
		//大小不可变
		this.setResizable(false);
		//可见
		this.setVisible(true);
		
		repaint();
	}
	
	public static ExcelFrame instance() {
		if(frame==null) {
			frame = new ExcelFrame() ;
		}
		return frame ;
	}
}
