package com.exceltool.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
*
* @Description: TODO 表格模板
* @author zhaowei 
* @Ceatetime 2014年7月1日
*
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExcelTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	//行
	private Vector vector = new Vector();
	//列
	private Vector<String> vName = new Vector<String>();
	
	/**
	 * 无参构造方法------初始化数据
	 */
	public ExcelTableModel(){
		vName.add("") ;
	}
	
	/**
	 * ------------------------------------------------------
	 * 重写父类中的抽象方法
	 * 
	 * 获得表格中的列数
	 */
	@Override
	public int getColumnCount() {
		return vName.size();
	}

	@Override
	public int getRowCount() {
		return vector==null?0:vector.size();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = ((Vector)vector.get(rowIndex)).get(columnIndex) ;
		return obj;
	}
	
	@Override
	public void addColumn(Object columnName) {
		String name = (String) columnName ;
		vName.add(name) ;
		super.addColumn(columnName);
	}
	
	
	/**
	 * 重写父类中非抽象的方法------覆盖父类中的方法
	 */
	 public String getColumnName(int columnIndex) {
		 return vName.get(columnIndex);
	}
	
	 /**
	  * 重写父类中的方法=======获得输入数据的类型,实现复选框的显示
	  */
	 public Class getColumnClass(int columnIndex){
		 return getValueAt(0,columnIndex).getClass();
	 }
	 
	/**
	 * 让表格中某些值可以进行修改 return false,说明不能进行修改
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			return true;
		}
		return false;
	}
	 
	 /**
	  * 重写父类中的方法=====实现表格的数据可操作
	  */
	 @Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		 if(!(this.getColumnCount()<=columnIndex)) {
			//先删除，再添加
			 ((Vector) vector.get(rowIndex)).remove(columnIndex);
			 ((Vector) vector.get(rowIndex)).add(columnIndex,value);
		 }else {
			 Vector v= ((Vector) vector.get(rowIndex)) ;
			 v.add(value) ;
		 }
		
		 this.fireTableCellUpdated(rowIndex, columnIndex);
	 }
		
	/**
	 * 往行中添加数据----这个方法名可以随意，由用户进行自行调用，
	 * 否则table.updateUI()是不会自动调用的
	 */
	@Override
	public void addRow(Object[] data){
		int size = data.length;
		Vector v = new Vector();
		for(int i=0; i<size; i++){
			v.add(data[i]);
		}
		vector.add(v);
	}
	
	public void removeAll(){
		this.vector = new Vector();
		this.vName = new Vector<String>();
	}
}
