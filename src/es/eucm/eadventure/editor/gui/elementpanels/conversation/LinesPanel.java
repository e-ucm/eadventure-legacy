package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapterdata.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;

/**
 * This class is the panel used to display and edit nodes. It holds node operations, like adding and removing lines,
 * editing end effects, remove links and reposition lines and children
 */
class LinesPanel extends JPanel {

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Conversation data controller.
	 */
	private ConversationDataControl conversationDataControl;

	/**
	 * Reference to the principal panel
	 */
	private ConversationPanel conversationPanel;

	/**
	 * Border of the panel
	 */
	private TitledBorder border;

	/**
	 * Table in which the node lines are represented
	 */
	private JTable lineTable;

	/**
	 * Scroll panel that holds the table
	 */
	private JScrollPane tableScrollPanel;

	/**
	 * Move line up ( /\ ) button
	 */
	private JButton moveLineUpButton;

	/**
	 * Move line down ( \/ ) button
	 */
	private JButton moveLineDownButton;

	/**
	 * "Insert line" button
	 */
	private JButton insertLineButton;

	/**
	 * "Delete line" button
	 */
	private JButton deleteLineButton;
	
	/**
	 * "Edit audio" button
	 */
	private JButton editAudioButton;
	
	/**
	 * "Edit effect" button
	 */
	private JButton editEffectButton;

	/**
	 * "Delete link" button
	 */
	private JButton deleteLinkButton;

	/**
	 * "Delete option" button
	 */
	private JButton deleteOptionButton;

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param principalPanel
	 *            Link to the principal panel, for sending signals
	 * @param conversationDataControl
	 *            Data controller to edit the lines
	 */
	public LinesPanel( ConversationPanel principalPanel, ConversationDataControl conversationDataControl ) {
		// Set the initial values
		this.conversationPanel = principalPanel;
		this.conversationDataControl = conversationDataControl;

		// Create and set border (titled border in this case)
		border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "LinesPanel.NoNodeSelected" ), TitledBorder.CENTER, TitledBorder.TOP );
		setBorder( border );

		// Set a GridBagLayout
		setLayout( new GridBagLayout( ) );

		/* Common elements (for Node and Option panels) */
		// Create the table with an empty model
		lineTable = new JTable( new NodeTableModel( null ) );

		// Column size properties
		lineTable.setAutoCreateColumnsFromModel( false );
		lineTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 30 );
		lineTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );
		lineTable.getColumnModel( ).getColumn( 3 ).setMaxWidth( 30 );

		// Selection properties
		lineTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		lineTable.setCellSelectionEnabled( false );
		lineTable.setColumnSelectionAllowed( false );
		lineTable.setRowSelectionAllowed( true );

		// Misc properties
		lineTable.setTableHeader( null );
		lineTable.setIntercellSpacing( new Dimension( 1, 1 ) );

		// Add selection listener to the table
		lineTable.getSelectionModel( ).addListSelectionListener( new NodeTableSelectionListener( ) );

		// Table scrollPane
		tableScrollPanel = new JScrollPane( lineTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

		// Create the set of values for the characters
		List<String> charactersList = new ArrayList<String>( );
		charactersList.add( TextConstants.getText( "ConversationLine.PlayerName" ) );
		String[] charactersArray = Controller.getInstance( ).getIdentifierSummary( ).getNPCIds( );
		for( String npc : charactersArray )
			charactersList.add( npc );
		charactersArray = charactersList.toArray( new String[] {} );

		// Set the character name editor to a ComboBox with all character names
		JComboBox charactersComboBox = new JComboBox( charactersArray );
		lineTable.getColumnModel( ).getColumn( 1 ).setCellEditor( new DefaultCellEditor( charactersComboBox ) );

		// Up and down buttons
		moveLineUpButton = new JButton( "/\\" );
		moveLineUpButton.addActionListener( new ListenerButtonMoveLineUp( ) );
		moveLineDownButton = new JButton( "\\/" );
		moveLineDownButton.addActionListener( new ListenerButtonMoveLineDown( ) );
		/* End of common elements */

		/* Dialogue panel elements */
		insertLineButton = new JButton( TextConstants.getText( "Conversations.InsertLine" ) );
		insertLineButton.addActionListener( new ListenerButtonInsertLine( ) );
		deleteLineButton = new JButton( TextConstants.getText( "Conversations.DeleteLine" ) );
		deleteLineButton.addActionListener( new ListenerButtonDeleteLine( ) );
		editAudioButton = new JButton( TextConstants.getText( "Conversations.EditAudio" ) );
		editAudioButton.addActionListener( new ListenerButtonEditAudio( ) );
		editEffectButton = new JButton( TextConstants.getText( "Conversations.EditEffect" ) );
		editEffectButton.addActionListener( new ListenerButtonEditEffect( ) );
		deleteLinkButton = new JButton( TextConstants.getText( "Conversations.DeleteLink" ) );
		deleteLinkButton.addActionListener( new ListenerButtonDeleteLink( ) );
		/* End of dialogue panel elements */

		/* Option panel elements */
		deleteOptionButton = new JButton( TextConstants.getText( "Conversations.DeleteOption" ) );
		deleteOptionButton.addActionListener( new ListenerButtonDeleteOption( ) );
		/* End of option panel elements */
	}

	/**
	 * Removes all elements in the panel, and sets a dialogue node panel
	 */
	private void setDialoguePanel( ) {
		// Remove all elements
		removeAll( );

		// Disable all buttons
		moveLineUpButton.setEnabled( false );
		moveLineDownButton.setEnabled( false );
		deleteLineButton.setEnabled( false );
		editAudioButton.setEnabled( false );

		// Create constraints
		GridBagConstraints c = new GridBagConstraints( );

		// Add the scroll panel (with the table)
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets( 2, 2, 2, 2 );
		c.weightx = 0.98;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 4;
		add( tableScrollPanel, c );

		// Add the up and down buttons
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.005;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		add( moveLineUpButton, c );

		c.gridy = 3;
		add( moveLineDownButton, c );

		// Add the insert, delete and edit buttons
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.015;
		c.gridx = 2;
		c.gridy = 0;
		add( insertLineButton, c );

		c.gridy = 1;
		add( deleteLineButton, c );
		
		c.gridy = 2;
		add( editAudioButton, c );

		c.gridy = 3;
		add( editEffectButton, c );

		c.gridy = 4;
		add( deleteLinkButton, c );
	}

	/**
	 * Removes all elements in the panel,and sets a option node panel
	 */
	private void setOptionPanel( ) {
		// Remove all elements
		removeAll( );

		// Disable all buttons
		moveLineUpButton.setEnabled( false );
		moveLineDownButton.setEnabled( false );
		deleteOptionButton.setEnabled( false );

		// Create constraints
		GridBagConstraints c = new GridBagConstraints( );

		// Add the scroll panel (with the table)
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets( 2, 2, 2, 2 );
		c.weightx = 0.98;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 3;
		add( tableScrollPanel, c );

		// Add the up and down buttons
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.02;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		add( moveLineUpButton, c );

		c.gridy = 2;
		add( moveLineDownButton, c );

		// Add the go to child button
		c.gridy = 1;
		add( deleteOptionButton, c );
	}

	/**
	 * Called when a new node has been selected, it is taken from the principal panel
	 */
	public void newSelectedNode( ) {
		// Take the selected node from the principal panel
		ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

		// Set the new model for the table
		lineTable.setModel( new NodeTableModel( selectedNode ) );

		// Cancel the current cell being edited
		if( lineTable.isEditing( ) )
			lineTable.getCellEditor( ).cancelCellEditing( );

		// If no node has been selected, remove all elements and change the panel title
		if( selectedNode == null ) {
			border.setTitle( TextConstants.getText( "LinesPanel.NoNodeSelected" ) );
			removeAll( );
		}

		// If a dialogue node has been selected, set the dialogue node panel and change the panel title
		else if( selectedNode.getType( ) == ConversationNodeView.DIALOGUE ) {
			border.setTitle( TextConstants.getText( "LinesPanel.DialogueNode" ) );
			//TODO MODIFIED
			//editEffectButton.setEnabled( selectedNode.isTerminal( ) );
			editEffectButton.setEnabled( true );
			deleteLinkButton.setEnabled( !selectedNode.isTerminal( ) );
			setDialoguePanel( );
		}

		// If a option node has been selected, set the option node panel and change the panel title
		else if( selectedNode.getType( ) == ConversationNodeView.OPTION ) {
			border.setTitle( TextConstants.getText( "LinesPanel.OptionNode" ) );
			setOptionPanel( );
		}

		// Repaint the panel
		repaint( );
	}

	/**
	 * Reload options at the menu, enabling, disabling and selecting them
	 */
	public void reloadOptions( ) {
		// Take the selected node from the principal panel
		ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

		// If it is a dialogue node, update the buttons
		if( selectedNode.getType( ) == ConversationNodeView.DIALOGUE ) {
			//TODO MODIFIED
			//editEffectButton.setEnabled( selectedNode.isTerminal( ) );
			editEffectButton.setEnabled( true );
			deleteLinkButton.setEnabled( !selectedNode.isTerminal( ) );
		}

		// Update the table
		lineTable.revalidate( );
	}

	/**
	 * Listener for the move line up ( /\ ) button
	 */
	private class ListenerButtonMoveLineUp implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// If the line was moved
			if( conversationDataControl.moveNodeLineUp( selectedNode, selectedRow ) ) {

				// Move the selection along with the line
				lineTable.setRowSelectionInterval( selectedRow - 1, selectedRow - 1 );
				lineTable.scrollRectToVisible( lineTable.getCellRect( selectedRow - 1, 0, true ) );

				// Update the view if the node is an option node
				if( selectedNode.getType( ) == ConversationNode.OPTION )
					conversationPanel.changePerformedInNode( );
			}
		}
	}

	/**
	 * Listener for the move line down ( \/ ) button
	 */
	private class ListenerButtonMoveLineDown implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// If the line was moved
			if( conversationDataControl.moveNodeLineDown( selectedNode, selectedRow ) ) {

				// Move the selection along with the line
				lineTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
				lineTable.scrollRectToVisible( lineTable.getCellRect( selectedRow + 1, 0, true ) );

				// Update the view if the node is an option node
				if( selectedNode.getType( ) == ConversationNode.OPTION )
					conversationPanel.changePerformedInNode( );
			}
		}
	}

	/**
	 * Listener for the "Insert line" button
	 */
	private class ListenerButtonInsertLine implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// Set the default name for the new line
			String name = ConversationLine.PLAYER;

			// If no row is selected, set the insertion position at the end
			if( selectedRow == -1 )
				selectedRow = lineTable.getRowCount( ) - 1;

			// If there is a row selected, pick the name of the row (so the inserted row has the same name as the last
			// one)
			else
				name = selectedNode.getLineName( selectedRow );

			// Insert the dialogue line in the selected position
			conversationDataControl.addNodeLine( selectedNode, selectedRow + 1, name );

			// Select the inserted line
			lineTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
			lineTable.scrollRectToVisible( lineTable.getCellRect( selectedRow + 1, 0, true ) );

			// Update the table
			lineTable.revalidate( );
		}
	}

	/**
	 * Listener for the "Delete line" button
	 */
	private class ListenerButtonDeleteLine implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// Delete the selected line
			conversationDataControl.deleteNodeLine( selectedNode, selectedRow );

			// If there are no more lines, clear selection (this disables the "Delete line" button)
			if( selectedNode.getLineCount( ) == 0 )
				lineTable.clearSelection( );

			// If the deleted line was the last one, select the new last line in the node
			else if( selectedNode.getLineCount( ) == selectedRow )
				lineTable.setRowSelectionInterval( selectedRow - 1, selectedRow - 1 );

			// Update the table
			lineTable.revalidate( );
		}
	}

	/**
	 * Listener for the "Edit audio" button
	 */
	private class ListenerButtonEditAudio implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// Edit the audio of the selected line
			if(conversationDataControl.editLineAudioPath( selectedNode, selectedRow)){
				lineTable.updateUI( );
			}
		}
	}

	
	/**
	 * Listener for the "Edit effect" button
	 */
	private class ListenerButtonEditEffect implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected node
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// Edit the effects of the node
			conversationDataControl.editNodeEffects( selectedNode );

			// Repaint the conversation panel
			conversationPanel.repaint( );
		}
	}

	/**
	 * Listener for the "Delete link" button
	 */
	private class ListenerButtonDeleteLink implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selectedNode
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// If the link with the child was deleted
			if( conversationDataControl.deleteNodeLink( selectedNode ) ) {

				// Reload the panel and update the view
				conversationPanel.changePerformedInNode( );
				reloadOptions( );
			}
		}
	}

	/**
	 * Listener for the "Delete option" button
	 */
	private class ListenerButtonDeleteOption implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// If the option was successfully deleted
			if( conversationDataControl.deleteNodeOption( selectedNode, selectedRow ) ) {

				// If there are no more children, clear selection (this disables the "Delete option" button)
				if( selectedNode.getChildCount( ) == 0 )
					lineTable.clearSelection( );

				// If the deleted child was the last one, select the new last child in the node
				else if( selectedNode.getChildCount( ) == selectedRow )
					lineTable.setRowSelectionInterval( selectedRow - 1, selectedRow - 1 );

				// If there is a selection in the table (there are more children) select the children
				if( lineTable.getSelectedRow( ) >= 0 )
					conversationPanel.setSelectedChild( conversationPanel.getSelectedNode( ).getChildView( lineTable.getSelectedRow( ) ) );
				// If there is no selection delete the children
				else
					conversationPanel.setSelectedChild( null );

				// Update the view and the table
				conversationPanel.changePerformedInNode( );
				lineTable.revalidate( );
			}
		}
	}

	/**
	 * Private class managing the selection listener of the table
	 */
	private class NodeTableSelectionListener implements ListSelectionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged( ListSelectionEvent e ) {
			// Extract the selection model of the list
			ListSelectionModel lsm = (ListSelectionModel) e.getSource( );

			// If there is no line selected
			if( lsm.isSelectionEmpty( ) ) {
				// Disable all options
				moveLineUpButton.setEnabled( false );
				moveLineDownButton.setEnabled( false );
				deleteOptionButton.setEnabled( false );
				deleteLineButton.setEnabled( false );
				editAudioButton.setEnabled( false );
			}

			// If there is a line selected
			else {
				// Enable all options
				moveLineUpButton.setEnabled( true );
				moveLineDownButton.setEnabled( true );
				deleteOptionButton.setEnabled( true );
				deleteLineButton.setEnabled( true );
				editAudioButton.setEnabled( true );
				

				// If the node is an option node
				if( conversationPanel.getSelectedNode( ).getType( ) == ConversationNodeView.OPTION ) {
					int selectedRow = lsm.getMinSelectionIndex( );

					// Take the selected child (at the same position of the selected line) and set it in the principal
					// panel
					// so it become painted in the conversational panel
					ConversationNodeView selectedChild = conversationPanel.getSelectedNode( ).getChildView( selectedRow );
					conversationPanel.setSelectedChild( selectedChild );
				}
			}
		}
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			int rowCount = 0;

			// If there is a node, the number of rows is the same as the number of lines
			if( node != null )
				rowCount = node.getLineCount( );

			return rowCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// All line tables has three columns
			return 4;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnClass(int)
		 */
		public Class<?> getColumnClass( int c ) {
			return getValueAt( 0, c ).getClass( );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#isCellEditable(int, int)
		 */
		public boolean isCellEditable( int rowIndex, int columnIndex ) {
			boolean isEditable = false;;

			// If the node is a dialogue node, the character name and the text are editable
			if( node.getType( ) == ConversationNodeView.DIALOGUE )
				isEditable = columnIndex > 0 && columnIndex<3;

			// If the node is an option node, only the text is editable
			else if( node.getType( ) == ConversationNodeView.OPTION )
				isEditable = columnIndex > 1 && columnIndex<3;

			return isEditable;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
		 */
		public void setValueAt( Object value, int rowIndex, int columnIndex ) {

			// If the value isn't an empty string
			if( !value.toString( ).trim( ).equals( "" ) ) {
				// If the name is being edited, and it has really changed
				if( columnIndex == 1 )
					if( value.toString( ).equals( TextConstants.getText( "ConversationLine.PlayerName" ) ) )
						conversationDataControl.setNodeLineName( node, rowIndex, ConversationLine.PLAYER );
					else
						conversationDataControl.setNodeLineName( node, rowIndex, value.toString( ) );

				// If the text is being edited, and it has really changed
				if( columnIndex == 2 )
					conversationDataControl.setNodeLineText( node, rowIndex, value.toString( ) );


				fireTableCellUpdated( rowIndex, columnIndex );
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;

			// If there is a valid node
			if( node != null ) {
				// Return value depending of the selected row
				switch( columnIndex ) {
					case 0:
						// Number of line
						value = rowIndex + 1;
						break;
					case 1:
						// Character name of the line
						if( node.isPlayerLine( rowIndex ) )
							value = TextConstants.getText( "ConversationLine.PlayerName" );
						else
							value = node.getLineName( rowIndex );
						break;
					case 2:
						// Text of the line
						value = node.getLineText( rowIndex );
						break;
					case 3:
						// Has audio or not
						value = node.hasAudioPath( rowIndex )?"Audio":"";
						break;
				}
			}

			return value;
		}
	}
}
