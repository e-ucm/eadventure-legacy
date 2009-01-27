package es.eucm.eadventure.engine.core.control;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class DebugTableModel extends AbstractTableModel implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FlagSummary flagSummary;
	
	private VarSummary varSummary;
	
	private List<String> ids;
	
	private List<String> changes;
	
	private boolean onlyChanges;
	
	public DebugTableModel(FlagSummary flagSummary, VarSummary varSummary) {
		this.flagSummary = flagSummary;
		this.varSummary = varSummary;
		ids = new ArrayList<String>();
		for (String id : flagSummary.getFlags().keySet()) {
			ids.add(id);
		}
		for (String id : varSummary.getVars().keySet()) {
			ids.add(id);
		}
		Collections.sort(ids);
		this.onlyChanges = false;
	}
	
	public DebugTableModel(FlagSummary flagSummary, VarSummary varSummary, boolean onlyChanges) {
		this(flagSummary, varSummary);
		this.onlyChanges = onlyChanges;
	}
	
    public String getColumnName(int col) {
        if (col == 0)
        	return "id";
        if (col == 1)
        	return "value";
    	return "";
    }
    
    public int getRowCount() {
    	if (!onlyChanges)
    		return (ids.size());
    	else if (changes != null)
    		return changes.size();
    	else
    		return 0;
    }
    
    public int getColumnCount() {
    	return 2;
    }
    
    public String getValueAt(int row, int col) {
    	String id = "";
    	
		if (!onlyChanges)
			id = ids.get(row);
		else if (changes != null)
			id = changes.get(row);
    	
    	if (col == 0) {
    		return id;
    	} else {
    		if (flagSummary.getFlags().containsKey(id)) {
    			if (flagSummary.getFlagValue(id))
    				return "true";
    			else
    				return "false";
    		} else if (varSummary.getVars().containsKey(id)) {
    			return "" + varSummary.getValue(id);
    		}
    	}
    	return "";
    }

    public boolean isCellEditable(int row, int col) {
    	return false;
    }

    public void setValueAt(Object value, int row, int col) {
    }

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		String string = (String) value;
		JTextArea label = new JTextArea((String) value);
		label.setBorder(new LineBorder(Color.BLACK, 1));
		
		if (string.equals("false")) {
			label.setForeground(Color.RED);
		} else if (string.equals("true")) {
			label.setForeground(Color.GREEN);
		}
		
		if (!onlyChanges && changes != null && changes.contains(ids.get(row))) {
			label.setBackground(Color.YELLOW);
		}
		
		return label;
	}

	public void setChanges(List<String> changes) {
		this.changes = changes;
	}
}
