package es.eucm.eadventure.engine.core.control;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class DebugTableModel extends AbstractTableModel implements TableCellRenderer {

	private FlagSummary flagSummary;
	
	private VarSummary varSummary;
	
	private List<String> ids;
	
	private List<String> changes;
	
	private int flags;
	
	private int vars;
	
	public DebugTableModel(FlagSummary flagSummary, VarSummary varSummary) {
		this.flagSummary = flagSummary;
		this.varSummary = varSummary;
		ids = new ArrayList<String>();

		flags = flagSummary.getFlags().size();
		vars = varSummary.getVars().size();
		
		for (String id : flagSummary.getFlags().keySet()) {
			ids.add(id);
		}
		for (String id : varSummary.getVars().keySet()) {
			ids.add(id);
		}
	}
	
    public String getColumnName(int col) {
        if (col == 0)
        	return "id";
        if (col == 1)
        	return "value";
    	return "";
    }
    
    public int getRowCount() {
    	return (ids.size());
    }
    
    public int getColumnCount() {
    	return 2;
    }
    
    public String getValueAt(int row, int col) {
    	if (col == 0) {
    		return ids.get(row);
    	} else {
    		if (col < flags) {
    			if (flagSummary.getFlagValue(ids.get(row)))
    				return "true";
    			else
    				return "false";
    		} else {
    			return "" + varSummary.getValue(ids.get(row));
    		}
    	}
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
		
		if (changes != null && changes.contains(ids.get(row))) {
			label.setBackground(Color.YELLOW);
		}
		
		return label;
	}

	public void setChanges(List<String> changes) {
		this.changes = changes;
	}
}
