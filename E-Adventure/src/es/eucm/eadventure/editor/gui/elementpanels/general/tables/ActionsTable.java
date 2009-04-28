package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;

public class ActionsTable extends JTable{
	
   	private static final long serialVersionUID = -777111416961485368L;

	private ActionsListDataControl dataControl;
	
	public ActionsTable (ActionsListDataControl dControl){
		super();
		this.dataControl = dControl;
		
		this.setModel( new ActionsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.getColumnModel().getColumn(0).setHeaderRenderer(new InfoHeaderRenderer("test.html"));
		this.getColumnModel().getColumn(1).setHeaderRenderer(new InfoHeaderRenderer());
		this.getColumnModel().getColumn(2).setHeaderRenderer(new InfoHeaderRenderer());
		this.setDragEnabled( false );
		
		this.getColumnModel().getColumn(0).setCellRenderer(new ActionCellRendererEditor());
		this.getColumnModel().getColumn(0).setCellEditor(new ActionCellRendererEditor());
		this.getColumnModel().getColumn(1).setCellRenderer(new ActionDetailsCellRendererEditor());
		this.getColumnModel().getColumn(1).setCellEditor(new ActionDetailsCellRendererEditor());
		this.getColumnModel().getColumn(2).setCellRenderer(new ConditionsCellRendererEditor());
		this.getColumnModel().getColumn(2).setCellEditor(new ConditionsCellRendererEditor());
		this.getColumnModel().getColumn(2).setMaxWidth(120);
		this.getColumnModel().getColumn(2).setMinWidth(120);
		this.getColumnModel().getColumn(2).setWidth(120);
		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		this.setRowHeight(20);
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setRowHeight(20);
				setRowHeight(getSelectedRow(), 27);
			}
		});
		this.setSize(200, 150);
	}
	
	
	private class ActionsTableModel extends AbstractTableModel{

	    private static final long serialVersionUID = -243535410363608581L;
		
		public int getColumnCount( ) {
			return 3;
		}

		public int getRowCount( ) {
			return dataControl.getActions().size();
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
		    List<ActionDataControl> actions = dataControl.getActions();
		    if (columnIndex == 0)
		    	return actions.get(rowIndex);
		    if (columnIndex == 1)
		    	return actions.get(rowIndex);
		    if (columnIndex == 2)
		    	return actions.get(rowIndex).getConditions();
		    return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex==0)
				return TextConstants.getText( "ActionsList.ActionName" );
			if (columnIndex==1)
				return TextConstants.getText( "ActionsList.NeedsGoTo" );
			if (columnIndex==2)
				return TextConstants.getText( "ActionsList.Conditions" );
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
		    	
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
		    return row == getSelectedRow();
		}
	}
}
