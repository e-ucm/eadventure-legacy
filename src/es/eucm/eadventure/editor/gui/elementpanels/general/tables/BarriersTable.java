package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class BarriersTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	protected BarriersListDataControl dataControl;
	
	protected ScenePreviewEditionPanel spep;
	
	public BarriersTable (BarriersListDataControl dControl, ScenePreviewEditionPanel spep2){
		super();
		this.spep = spep2;
		this.dataControl = dControl;
		
		this.setModel( new BarriersTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (getSelectedRow() >= 0) {
					spep.setSelectedElement(dataControl.getBarriers().get(getSelectedRow()));
					spep.repaint();
				}
			}
		});
		
		this.getColumnModel().getColumn(1).setCellRenderer(new ConditionsCellRendererEditor());
		this.getColumnModel().getColumn(1).setCellEditor(new ConditionsCellRendererEditor());
		
		
		this.setRowHeight(25);
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		this.setSize(200, 150);
	}
	
	
	private class BarriersTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 2;
		}

		public int getRowCount( ) {
			return dataControl.getBarriers().size();
		}
				
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return rowIndex;
			if (columnIndex == 1)
				return dataControl.getBarriers().get(rowIndex).getConditions();
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
