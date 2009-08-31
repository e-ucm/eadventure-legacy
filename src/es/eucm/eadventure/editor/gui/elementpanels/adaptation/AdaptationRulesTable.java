/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
