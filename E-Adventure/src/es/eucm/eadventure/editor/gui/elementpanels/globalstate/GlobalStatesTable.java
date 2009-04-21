package es.eucm.eadventure.editor.gui.elementpanels.globalstate;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.StringCellRendererEditor;

public class GlobalStatesTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	protected GlobalStateListDataControl dataControl;
	
	public GlobalStatesTable (GlobalStateListDataControl dControl){
		super();
		this.dataControl = dControl;
		
		this.setModel( new BarriersTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
				
		this.getColumnModel().getColumn(0).setCellEditor(new StringCellRendererEditor());
		this.getColumnModel().getColumn(0).setCellRenderer(new StringCellRendererEditor());
		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		this.setSize(200, 150);
	}
	
	
	private class BarriersTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 1;
		}

		public int getRowCount( ) {
			return dataControl.getGlobalStates().size();
		}
				
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return dataControl.getGlobalStates().get(rowIndex).getId();
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0)
				return TextConstants.getText( "GlobalStatesList.ID" );
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				dataControl.getGlobalStates().get(rowIndex).renameElement((String) value);
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return getSelectedRow() == row;
		}
	}
}
