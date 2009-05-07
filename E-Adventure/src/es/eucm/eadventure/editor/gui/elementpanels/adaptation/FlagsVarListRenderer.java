package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.control.Controller;


public class FlagsVarListRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    
   
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Combo box to show the flags and vars
     */
    private JComboBox value;
    
    /**
     * store all introduced elements
     */
   // private ArrayList<String> newElements;
    
    /**
     * 
     * 
     */
    
    public FlagsVarListRenderer(){
	this.value = new JComboBox();
    }
    
    
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean isFocus, int row, int col) {
	    
	    
	    if (col==2){
		this.value = setOperations((Boolean)table.getModel().getValueAt(row, 1),value);
		
	    }else if (col==3){
		this.value =setValues((Boolean)table.getModel().getValueAt(row, 1),value); 
	    }
	    
	    this.value.addActionListener(new ComboListener(table,col));
	    return this.value;
		
		
	}
	
	

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
	    
	    if (col==2){
		this.value = setOperations((Boolean)table.getModel().getValueAt(row, 1),value);
		
	    }else if (col==3){
		this.value =setValues((Boolean)table.getModel().getValueAt(row, 1),value); 
	    }
	    this.value.addActionListener(new ComboListener(table,col));
	    return this.value;
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	
	private JComboBox setOperations(boolean isFlag,Object value){
	   JComboBox operations = null;
	    if (isFlag)
		    operations = new JComboBox(new String[]{"activate", "deactivate"});
		else 
		    operations = new JComboBox(new String[]{"increment", "decrement", "set-value"});
		operations.setEditable(false);
		operations.setSelectedItem(value);
		return operations;
	}
	
	private JComboBox setValues(boolean isFlag,Object value){
	    JComboBox values=null;
	    if (isFlag)
		values = new JComboBox (Controller.getInstance( ).getVarFlagSummary( ).getFlags( ));
	    else
		values = new JComboBox (Controller.getInstance( ).getVarFlagSummary( ).getVars());
	    values.setEditable(true);
	 
	    values.setSelectedItem(value);
	    return values;
	}
	
	
	
	private class ComboListener implements ActionListener{

	    
	    private JTable table;
	    
	    private int col;
	    
	    public ComboListener(JTable table,int col){
		this.table = table;
		this.col = col;
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		if (col == 2 || col==3){
		  table.getColumnModel().getColumn(col).getCellEditor().stopCellEditing();
		}
	    }
	    }
  
    
}
