package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.controllers.DataControl;

public class ResizableTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<DataControl> list;
	
	private int size = 1;
	
	/**
	 * Constructor.
	 * 
	 * @param itemsInfo
	 *            Container array of the information of the items
	 */
	public ResizableTableModel( List<DataControl> list, int size ) {
		this.list = list;
		this.size = size;
	}

	public int getColumnCount( ) {
		if (size == 0)
			return 1;
		if (size == 1)
			return 4;
		if (size == 2)
			return 2;
		return 2;
	}

	public int getRowCount( ) {
		if (size == 0)
			return list.size();
		if (size == 1)
			return list.size() / 4 + (list.size() % 4 > 0 ? 1 : 0);
		if (size == 2)
			return list.size() / 2 + list.size() % 2;
		return 0;
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		if (size == 2 && rowIndex * 2 + columnIndex < list.size())
			return list.get(rowIndex * 2 + columnIndex);
		if (size == 1 && rowIndex * 4 + columnIndex < list.size())
			return list.get(rowIndex * 4 + columnIndex);
		if (size == 0)
			return list.get(rowIndex);
		return null;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

}
