package es.eucm.eadventure.editor.gui.elementpanels.general;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ExitsTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ExitsListDataControl dataControl;
	
	protected IrregularAreaEditionPanel iaep;
	
	protected ScenePreviewEditionPanel spep;
	
	public ExitsTable (ExitsListDataControl dControl, IrregularAreaEditionPanel iaep2){
		super();
		this.spep = iaep2.getScenePreviewEditionPanel();
		this.iaep = iaep2;
		this.dataControl = dControl;
		
		this.setModel( new ElementsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (getSelectedRow() >= 0) {
					iaep.setRectangular(dataControl.getExits().get(getSelectedRow()));
					iaep.repaint();
				}
			}
		});
		
		this.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setSize(200, 150);
	}
	
	
	private class ElementsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 3;
		}

		public int getRowCount( ) {
			return dataControl.getExits().size();
		}
		
		@SuppressWarnings("unchecked")
		public Class getColumnClass(int columnIndex) {
			if (columnIndex == 0 || columnIndex == 2)
				return Boolean.class;
			return super.getColumnClass(columnIndex);
		}
		
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return new Boolean(dataControl.getExits().get(rowIndex).getExitLookDataControl().isTextCustomized());
			if (columnIndex == 1) {
				String temp = dataControl.getExits().get(rowIndex).getExitLookDataControl().getCustomizedText();
				if (temp == null)
					temp = "";
				return temp;
			}
			if (columnIndex == 2)
				return new Boolean(dataControl.getExits().get(rowIndex).isRectangular());
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0)
				return TextConstants.getText( "ActiveAreasList.Id" );
			if (columnIndex == 1)
				return TextConstants.getText( "ActiveAreasList.Name" );
			if (columnIndex == 2)
				return TextConstants.getText( "ActiveAreasList.Rectangular" );
			if (columnIndex == 3)
				return TextConstants.getText( "ActiveAreasList.Conditions" );
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 2) {
				dataControl.getExits().get(rowIndex).setRectangular(((Boolean) value).booleanValue());
				if (getSelectedRow() >= 0) {
					iaep.setRectangular(dataControl.getExits().get(getSelectedRow()));
					iaep.repaint();
				}
			} else if (columnIndex == 1) {
				dataControl.getExits().get(rowIndex).getExitLookDataControl().setExitText((String) value);
				this.fireTableDataChanged();
			} else if (columnIndex == 0) {
				dataControl.getExits().get(rowIndex).getExitLookDataControl().setExitText(((Boolean) value).booleanValue() ? "" : null);
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return getSelectedRow() == row && (column != 1 || dataControl.getExits().get(row).getExitLookDataControl().isTextCustomized());
		}
	}
}
