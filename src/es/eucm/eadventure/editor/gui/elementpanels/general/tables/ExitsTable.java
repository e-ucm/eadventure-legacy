package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ExitsListPanel;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ExitsTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	protected ExitsListDataControl dataControl;
	
	protected IrregularAreaEditionPanel iaep;
	
	protected ScenePreviewEditionPanel spep;
	
	public ExitsTable (ExitsListDataControl dControl, IrregularAreaEditionPanel iaep2, JSplitPane previewAuxSplit){
		super();
		this.spep = iaep2.getScenePreviewEditionPanel();
		this.iaep = iaep2;
		this.dataControl = dControl;
		
		
		this.setModel( new ElementsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		this.setColumnSelectionAllowed(false);
		
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (getSelectedRow() >= 0) {
					iaep.setRectangular(dataControl.getExits().get(getSelectedRow()));
					iaep.repaint();
				}
			}
		});
		
		String[] ids = Controller.getInstance().getIdentifierSummary().getGeneralSceneIds();
		
		this.getColumnModel().getColumn(0).setCellRenderer(new NextSceneCellRendererEditor(ids));
		this.getColumnModel().getColumn(0).setCellEditor(new NextSceneCellRendererEditor(ids));
		this.getColumnModel().getColumn(0).setMinWidth(140);
		this.getColumnModel().getColumn(1).setCellRenderer(new TransitionCellRendererEditor());
		this.getColumnModel().getColumn(1).setCellEditor(new TransitionCellRendererEditor());
		this.getColumnModel().getColumn(1).setMaxWidth(110);
		this.getColumnModel().getColumn(1).setMinWidth(110);
		this.getColumnModel().getColumn(2).setCellRenderer(new ExitLooksCellRendererEditor());
		this.getColumnModel().getColumn(2).setCellEditor(new ExitLooksCellRendererEditor());
		this.getColumnModel().getColumn(2).setMinWidth(160);
		String text = TextConstants.getText("ExitsList.Edit");
		this.getColumnModel().getColumn(3).setCellRenderer(new AuxEditCellRendererEditor(previewAuxSplit, ExitsListPanel.VERTICAL_SPLIT_POSITION, text));
		this.getColumnModel().getColumn(3).setCellEditor(new AuxEditCellRendererEditor(previewAuxSplit, ExitsListPanel.VERTICAL_SPLIT_POSITION, text));
		this.getColumnModel().getColumn(3).setMaxWidth(120);
		this.getColumnModel().getColumn(3).setMinWidth(120);
		
		this.setRowHeight(25);
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ExitsTable.this.setRowHeight(25);
				ExitsTable.this.setRowHeight(ExitsTable.this.getSelectedRow(), 70);
			}
		});
		this.setSize(200, 150);
	}
	
	private class ElementsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public int getColumnCount( ) {
			return 4;
		}

		public int getRowCount( ) {
			return dataControl.getExits().size();
		}
				
		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0)
				return dataControl.getExits().get(rowIndex);
			if (columnIndex == 1)
				return dataControl.getExits().get(rowIndex);
			if (columnIndex == 2)
				return dataControl.getExits().get(rowIndex).getExitLookDataControl();
			if (columnIndex == 3)
				return dataControl.getExits().get(rowIndex).getConditions();
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0)
				return TextConstants.getText( "ExitsList.NextScene" );
			if (columnIndex == 1)
				return TextConstants.getText( "ExitsList.Transition" );
			if (columnIndex == 2)
				return TextConstants.getText( "ExitsList.Appearance" );
			if (columnIndex == 3)
				return TextConstants.getText( "ExitsList.ConditionsAndEffects" );
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 2) {
				this.fireTableDataChanged();
			} 
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return getSelectedRow() == row;
		}
	}
}
