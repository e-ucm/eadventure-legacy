package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;

public class ResourcesTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	protected DataControlWithResources dataControl;
	
	protected LooksPanel looksPanel;
	
	public ResourcesTable (DataControlWithResources dControl, LooksPanel looksPanel2){
		super();
		this.looksPanel = looksPanel2;
		this.dataControl = dControl;
		
		this.setModel( new ElementsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		
		
		this.getColumnModel().getColumn(1).setCellRenderer(new ConditionsCellRendererEditor());
		this.getColumnModel().getColumn(1).setCellEditor(new ConditionsCellRendererEditor());
		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		this.setSize(200, 150);
	}
	
	public int getSelectedIndex() {
		return getSelectedRow();
	}
	
	public void setSelectedIndex(int index) {
		getSelectionModel().setSelectionInterval(index, index);
	}
	
	private class ElementsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 2;
		}

		public int getRowCount( ) {
			return dataControl.getResourcesCount();
		}
				
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return rowIndex;
			if (columnIndex == 1)
				return dataControl.getResources().get(rowIndex).getConditions();
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0)
				return TextConstants.getText( "ExitsList.NextScene" );
			if (columnIndex == 1)
				return TextConstants.getText( "ExitsList.Transition" );
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {

		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return getSelectedRow() == row && column == 1;
		}
	}
}
