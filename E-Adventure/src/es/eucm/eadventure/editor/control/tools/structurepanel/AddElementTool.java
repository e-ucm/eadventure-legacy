package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementCell;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public class AddElementTool extends Tool {

	private int type;
	
	private StructureListElement element;
		
	private JTable table;
	
	private StructureElement newElement;
	
	public AddElementTool(StructureListElement element, JTable table) {
		this.type = element.getDataControl().getAddableElements()[0];
		this.element = element;
		this.table = table;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean doTool() {
		if( element.getDataControl( ).canAddElement( type )) {
			String defaultId = element.getDataControl().getDefaultId(type);
			String id = defaultId;
			int count = 0;
			while (!Controller.getInstance().isElementIdValid( id, false )) {
				count++;
				id = defaultId + count;
			}
			if (element.getDataControl( ).addElement( type, id ) ) {
				((StructureElement) table.getModel().getValueAt(element.getChildCount() - 1, 0)).setJustCreated(true);
				((AbstractTableModel) table.getModel()).fireTableDataChanged();
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				        if (table.editCellAt(element.getChildCount() - 1, 0))
				            ((StructureElementCell) table.getEditorComponent()).requestFocusInWindow();        
				    }
				});
				table.changeSelection(element.getChildCount() - 1, 0, false, false);
				newElement = element.getChild(element.getChildCount() - 1);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Add child";
	}

	@Override
	public boolean undoTool() {
		newElement.delete(false);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		table.clearSelection();
		return true;
	}
	
	public boolean canRedo() {
		return false;
	}
	
	@Override
	public boolean redoTool() {
		return false;
	}
	
	@Override
	public boolean combine(Tool other) {
		return false;
	}


}
