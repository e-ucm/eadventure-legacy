package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;

public class LinesTable extends JTable {

	private static final long serialVersionUID = -6962666312669657936L;

	private ConversationDataControl conversationDataControl;
	
	public LinesTable(ConversationDataControl conversationDataControl, LinesPanel linesPanel) {
		super();
		this.conversationDataControl = conversationDataControl;
		
		setModel(new NodeTableModel( null ));
		setAutoCreateColumnsFromModel( false );
		getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );

		getColumnModel().getColumn(1).setCellEditor(new TextLineCellRendererEditor(linesPanel));
		getColumnModel().getColumn(1).setCellRenderer(new TextLineCellRendererEditor(linesPanel));

		getColumnModel().getColumn(2).setMaxWidth(90);
		getColumnModel().getColumn(2).setMinWidth(90);
		getColumnModel().getColumn(2).setWidth(90);
		getColumnModel().getColumn(2).setCellEditor(new AudioCellRendererEditor());
		getColumnModel().getColumn(2).setCellRenderer(new AudioCellRendererEditor());
		
		getColumnModel().getColumn(3).setMaxWidth(90);
		getColumnModel().getColumn(3).setMinWidth(90);
		getColumnModel().getColumn(3).setWidth(90);
		getColumnModel().getColumn(3).setCellEditor(new SynthesizeCellRendererEditor());
		getColumnModel().getColumn(3).setCellRenderer(new SynthesizeCellRendererEditor());
		
		getColumnModel().getColumn(4).setMaxWidth(120);
		getColumnModel().getColumn(4).setMinWidth(120);
		getColumnModel().getColumn(4).setWidth(120);
		getColumnModel().getColumn(4).setCellRenderer(new ConditionsCellRendererEditor());
		getColumnModel().getColumn(4).setCellEditor(new ConditionsCellRendererEditor());

		
		List<String> charactersList = new ArrayList<String>( );
		charactersList.add( TextConstants.getText( "ConversationLine.PlayerName" ) );
		String[] charactersArray = Controller.getInstance( ).getIdentifierSummary( ).getNPCIds( );
		for( String npc : charactersArray )
			charactersList.add( npc );
		charactersArray = charactersList.toArray( new String[] {} );
		JComboBox charactersComboBox = new JComboBox( charactersArray );
		getColumnModel( ).getColumn( 0 ).setCellEditor( new DefaultCellEditor( charactersComboBox ) );

		
		setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		setCellSelectionEnabled( false );
		setColumnSelectionAllowed( false );
		setRowSelectionAllowed( true );

		setTableHeader( null );
		setIntercellSpacing( new Dimension( 1, 1 ) );

		setRowHeight(20);
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setRowHeight(20);
				if (getSelectedRow() != -1)
					setRowHeight(getSelectedRow(), 48);
			}
		});
		
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

	}

	public void newSelectedNode(ConversationNodeView selectedNode) {
		setModel(new NodeTableModel( selectedNode ));
		if( isEditing( ) )
			getCellEditor( ).cancelCellEditing( );
	}

	
	/**
	 * Private class containing the model for the line table
	 */
	private class NodeTableModel extends AbstractTableModel {

		/**
		 * Required
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Link to the representated conversational node
		 */
		private ConversationNodeView node;

		/**
		 * Constructor
		 * 
		 * @param node
		 *            Node which lines will be in the table
		 */
		public NodeTableModel( ConversationNodeView node ) {
			this.node = node;
		}

		public int getRowCount( ) {
			int rowCount = 0;

			if( node != null )
				rowCount = node.getLineCount( );

			return rowCount;
		}

		public int getColumnCount( ) {
			return 5;
		}

		public Class<?> getColumnClass( int c ) {
			return getValueAt( 0, c ).getClass( );
		}

		public boolean isCellEditable( int rowIndex, int columnIndex ) {
			boolean isEditable = false;;

			if( node.getType( ) == ConversationNodeView.DIALOGUE )
				isEditable = true;

			else if( node.getType( ) == ConversationNodeView.OPTION )
				isEditable = columnIndex != 0;

			return isEditable && rowIndex == getSelectedRow();
		}

		public void setValueAt( Object value, int rowIndex, int columnIndex ) {
			if( !value.toString( ).trim( ).equals( "" ) ) {
				if( columnIndex == 0 )
					if( value.toString( ).equals( TextConstants.getText( "ConversationLine.PlayerName" ) ) )
						conversationDataControl.setNodeLineName( node, rowIndex, ConversationLine.PLAYER );
					else
						conversationDataControl.setNodeLineName( node, rowIndex, value.toString( ) );
				if( columnIndex == 1 )
					conversationDataControl.setNodeLineText( node, rowIndex, value.toString( ) );
				fireTableCellUpdated( rowIndex, columnIndex );
			}
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;
			if( node != null ) {
				switch( columnIndex ) {
					case 0:
						if( node.isPlayerLine( rowIndex ) )
							value = TextConstants.getText( "ConversationLine.PlayerName" );
						else
							value = node.getLineName( rowIndex );
						break;
					case 1:
						value = node.getLineText( rowIndex );
						break;
					case 2:
						value = node;
						break;
					case 3:
						value = node;
						break;
					case 4:
						value = conversationDataControl.getLineConditionController(node.getConversationLine(rowIndex));
						break;
				}
			}
			return value;
		}
	}



}
