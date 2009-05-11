package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.StringCellRendererEditor;

public class AdaptationRulesTable extends JTable {

	private static final long serialVersionUID = 647135614707098595L;

	private AdaptationProfileDataControl dataControl;
	
	public AdaptationRulesTable(AdaptationProfileDataControl dataControl) {
		this.dataControl = dataControl;
		setModel(new AdaptationRulesTableModel());
		getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		getColumnModel().getColumn(0).setWidth(20);
		getColumnModel().getColumn(0).setMaxWidth(20);

		getColumnModel().getColumn(1).setCellEditor(new StringCellRendererEditor());
		getColumnModel().getColumn(1).setCellRenderer(new StringCellRendererEditor());
		getColumnModel().getColumn(2).setCellEditor(new InitialSceneCellRendererEditor());
		getColumnModel().getColumn(2).setCellRenderer(new InitialSceneCellRendererEditor());
		
		setRowHeight(22);
		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setRowHeight(22);
				if (getSelectedRow() >= 0)
					setRowHeight(getSelectedRow(), 35);
			}
		});
	}
	
	public void fireTableDataChanged() {
		((AbstractTableModel) getModel()).fireTableDataChanged();
	}
	
	/**
	 * Table model to display the scenes information.
	 */
	public class AdaptationRulesTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 * 
		 * @param assRulesInfo
		 *            Container array of the information of the scenes
		 */
		public AdaptationRulesTableModel( ) {
		}

		public int getColumnCount( ) {
			return 3;
		}

		public int getRowCount( ) {
			return dataControl.getAdaptationRules().size();
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader0" );
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "AdaptationRule.Description" );
			else if( columnIndex == 2 )
				columnName = TextConstants.getText( "AdaptationRule.InitialState.InitialScene" );
			return columnName;
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return rowIndex;
			if (columnIndex == 1)
				return dataControl.getAdaptationRules().get(rowIndex).getDescription();
			if (columnIndex == 2)
				return dataControl.getAdaptationRules().get(rowIndex);
			return null;
		}
		
		@Override
		public void setValueAt( Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 1) {
				Controller.getInstance().addTool(new ChangeStringValueTool(dataControl.getAdaptationRules().get(rowIndex).getContent(), (String) value, "getDescription", "setDescription"));
			}
		}
		
		@Override
		public boolean isCellEditable( int rowIndex, int columnIndex) {
			return rowIndex == getSelectedRow() && columnIndex > 0;
		}
	}

}
