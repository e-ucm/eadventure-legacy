package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;

public class AdaptationRulesTable extends JTable {

	private static final long serialVersionUID = 647135614707098595L;

	private AdaptationProfileDataControl dataControl;
	
	public AdaptationRulesTable(AdaptationProfileDataControl dataControl) {
		this.dataControl = dataControl;
		setModel(new AdaptationRulesTableModel());
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
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader1" );
			else if( columnIndex == 2 )
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader2" );
			else if( columnIndex == 3 )
				columnName = TextConstants.getText( "AdaptationRulesList.ColumnHeader3" );
			return columnName;
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return rowIndex;
			if (columnIndex == 1)
				return dataControl.getAdaptationRules().get(rowIndex).getDescription();
			if (columnIndex == 2)
				return dataControl.getAdaptationRules().get(rowIndex).getInitialScene();
			return null;
		}
	}

}
