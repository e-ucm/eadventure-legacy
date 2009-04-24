package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;

import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;

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
	private LinesTable lineTable;

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
	 * "Edit effect" button
	 */
	private JButton editEffectButton;

	/**
	 * "Delete option" button
	 */
	private JButton deleteOptionButton;

	private JButton insertOptionButton;
	
	private JButton addOptionButton;
	
	private JButton addDialogButton;
	
	private JButton deleteNodeButton;

	private JButton linkToButton;
	
	/**
	 * Select the player or any exists character to speak selected conversation line
	 */
	//private JComboBox charactersComboBox; 
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
		lineTable = new LinesTable( conversationDataControl , this);
		lineTable.getSelectionModel( ).addListSelectionListener( new NodeTableSelectionListener( ) );

		// Table scrollPane
		tableScrollPanel = new JScrollPane( lineTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

		// Create the set of values for the characters

		// Up and down buttons
		moveLineUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveLineUpButton.setContentAreaFilled( false );
		moveLineUpButton.setMargin( new Insets(0,0,0,0) );
		moveLineUpButton.setFocusable(false);
		moveLineUpButton.setToolTipText( TextConstants.getText( "Conversations.MoveLineUp" ) );
		moveLineUpButton.addActionListener( new ListenerButtonMoveLineUp( ) );
		
		
		moveLineDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
		moveLineDownButton.setContentAreaFilled( false );
		moveLineDownButton.setMargin( new Insets(0,0,0,0) );
		moveLineDownButton.setFocusable(false);
		moveLineDownButton.setToolTipText( TextConstants.getText( "Conversations.MoveLineDown" ) );
		moveLineDownButton.addActionListener( new ListenerButtonMoveLineDown( ) );
		
		
		// Add the add operations
		ImageIcon dialog = new ImageIcon("img/icons/dialogNode.png");
		addDialogButton = new JButton(TextConstants.getText( "Conversation.OptionAddDialogueNode" ), dialog);
		addDialogButton.addActionListener( new AddChildActionListener( ConversationNode.DIALOGUE ) );

		ImageIcon option = new ImageIcon("img/icons/optionNode.png");
		addOptionButton = new JButton(TextConstants.getText( "Conversation.OptionAddOptionNode" ), option);
		addOptionButton.addActionListener( new AddChildActionListener( ConversationNode.OPTION ));

		ImageIcon delete = new ImageIcon("img/icons/deleteNode.png");
		deleteNodeButton = new JButton(TextConstants.getText( "Conversation.OptionDeleteNode" ), delete);
		deleteNodeButton.addActionListener( new DeleteNodeActionListener ());
		
		linkToButton = new JButton( TextConstants.getText( "Conversation.OptionLinkNode" ));
		linkToButton.addActionListener( new LinkNodeActionListener());
		
		
		/* End of common elements */

		/* Dialogue panel elements */
		insertLineButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		insertLineButton.setContentAreaFilled( false );
		insertLineButton.setMargin( new Insets(0,0,0,0) );
		insertLineButton.setFocusable(false);
		insertLineButton.setToolTipText( TextConstants.getText( "Conversations.InsertLine" ) );
		insertLineButton.addActionListener( new ListenerButtonInsertLine( ) );
		deleteLineButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteLineButton.setContentAreaFilled( false );
		deleteLineButton.setMargin( new Insets(0,0,0,0) );
		deleteLineButton.setToolTipText(TextConstants.getText( "Conversations.DeleteLine" ));
		deleteLineButton.addActionListener( new ListenerButtonDeleteLine( ) );
		editEffectButton = new JButton( TextConstants.getText( "Conversations.EditEffect" ) );
		editEffectButton.addActionListener( new ListenerButtonEditEffect( ) );
		/* End of dialogue panel elements */

		/* Option panel elements */
		deleteOptionButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteOptionButton.setContentAreaFilled( false );
		deleteOptionButton.setMargin( new Insets(0,0,0,0) );
		deleteOptionButton.setToolTipText(TextConstants.getText( "Conversations.DeleteOption" ));
		deleteOptionButton.addActionListener( new ListenerButtonDeleteOption( ) );
		insertOptionButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		insertOptionButton.setContentAreaFilled( false );
		insertOptionButton.setMargin( new Insets(0,0,0,0) );
		insertOptionButton.setFocusable(false);
		insertOptionButton.setToolTipText( TextConstants.getText( "Conversations.InsertLine" ) );
		insertOptionButton.addActionListener( new ListenerButtonInsertOption( ) );
		/* End of option panel elements */
	}

	/**
	 * Removes all elements in the panel, and sets a dialogue node panel
	 */
	private void setDialoguePanel( ) {
		removeAll( );
		moveLineUpButton.setEnabled( false );
		moveLineDownButton.setEnabled( false );
		deleteLineButton.setEnabled( false );
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add( insertLineButton , c );
		c.gridy++;
		buttonsPanel.add( deleteLineButton , c );
		c.anchor = GridBagConstraints.SOUTH;
		c.gridy++;
		buttonsPanel.add( moveLineUpButton , c );
		c.gridy++;
		buttonsPanel.add( moveLineDownButton , c );

		JPanel menuPanel = conversationPanel.getMenuPanel();
		menuPanel.removeAll();
		menuPanel.setLayout(new GridLayout(0,1));
		menuPanel.add(editEffectButton);
		menuPanel.add(addDialogButton);
		addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ) );
		menuPanel.add(addOptionButton);
		addOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.OPTION ) );
		menuPanel.setVisible(true);
		menuPanel.add(deleteNodeButton);
		deleteNodeButton.setEnabled( conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode() ));
		menuPanel.add(linkToButton);
		linkToButton.setEnabled(conversationDataControl.canLinkNode( conversationPanel.getSelectedNode() ));

		menuPanel.setSize(200, 100);

		
		setLayout(new BorderLayout());
		add(buttonsPanel, BorderLayout.EAST);
		add(tableScrollPanel, BorderLayout.CENTER);
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
		 
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add( insertOptionButton , c );
		c.gridy++;
		buttonsPanel.add( deleteOptionButton , c );
		c.anchor = GridBagConstraints.SOUTH;
		c.gridy++;
		buttonsPanel.add( moveLineUpButton , c );
		c.gridy++;
		buttonsPanel.add( moveLineDownButton , c );
		
		JPanel menuPanel = conversationPanel.getMenuPanel();
		menuPanel.removeAll();
		menuPanel.setLayout(new GridLayout(0,1));
		menuPanel.add(editEffectButton);
		menuPanel.add(addDialogButton);
		addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ) );
		menuPanel.add(deleteNodeButton);
		deleteNodeButton.setEnabled( conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode() ));
		menuPanel.add(linkToButton);
		linkToButton.setEnabled(conversationDataControl.canLinkNode( conversationPanel.getSelectedNode() ));

		menuPanel.setSize(200, 80);

		JCheckBox randomOrder = new JCheckBox(TextConstants.getText( "Conversation.OptionRandomly"), conversationDataControl.isRandomActivate(conversationPanel.getSelectedNode()));
		randomOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conversationDataControl.setRandomlyOptions(conversationPanel.getSelectedNode());
			}
		});
		
		setLayout(new BorderLayout());
		add(buttonsPanel, BorderLayout.EAST);
		add(tableScrollPanel, BorderLayout.CENTER);
		add(randomOrder, BorderLayout.NORTH);
	}

	/**
	 * Called when a new node has been selected, it is taken from the principal panel
	 */
	public void newSelectedNode( ) {
		// Take the selected node from the principal panel
		ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

		// Set the new model for the table
		lineTable.newSelectedNode(selectedNode);

		// If no node has been selected, remove all elements and change the panel title
		if( selectedNode == null ) {
			border.setTitle( TextConstants.getText( "LinesPanel.NoNodeSelected" ) );
			removeAll( );
			conversationPanel.getMenuPanel().setVisible(false);
		}

		// If a dialogue node has been selected, set the dialogue node panel and change the panel title
		else if( selectedNode.getType( ) == ConversationNodeView.DIALOGUE ) {
			border.setTitle( TextConstants.getText( "LinesPanel.DialogueNode" ) );
			//TODO MODIFIED
			//editEffectButton.setEnabled( selectedNode.isTerminal( ) );
			editEffectButton.setEnabled( true );
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
			this.addDialogButton.setEnabled(conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ));
			this.addOptionButton.setEnabled(conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode() ));
		}

		// Update the table
		lineTable.revalidate( );
	}

	/**
	 * Listener for the move line up button
	 */
	private class ListenerButtonMoveLineUp implements ActionListener {

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
	 * Listener for the move line down button
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
			conversationDataControl.addNodeLine( selectedNode, selectedRow + 1, name,((GraphConversationDataControl)conversationDataControl).getAllConditions().get(selectedNode));

			// Select the inserted line
			lineTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
			lineTable.scrollRectToVisible( lineTable.getCellRect( selectedRow + 1, 0, true ) );

			// Update the table
			lineTable.revalidate( );
		}
	}
	
	public void editNextLine() {
		final int selectedRow = lineTable.getSelectedRow( );
		if (selectedRow == lineTable.getRowCount() - 1) {
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );
	
			// Set the default name for the new line
			String name = selectedNode.getLineName( selectedRow );
	
			// Insert the dialogue line in the selected position
			conversationDataControl.addNodeLine( selectedNode, selectedRow + 1, name,((GraphConversationDataControl)conversationDataControl).getAllConditions().get(selectedNode));
		}
		((AbstractTableModel) lineTable.getModel()).fireTableDataChanged();
		lineTable.changeSelection(selectedRow + 1, selectedRow + 1, false, false);
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		        if (lineTable.editCellAt(selectedRow + 1, 1))
		            ((JScrollPane) lineTable.getEditorComponent()).requestFocusInWindow();        
		    }
		});
	}

	/**
	 * Listener for the "Insert line" button
	 */
	private class ListenerButtonInsertOption implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// If no row is selected, set the insertion position at the end
			if( selectedRow == -1 )
				selectedRow = lineTable.getRowCount( ) - 1;

			// Insert the dialogue line in the selected position
			conversationDataControl.addChild(selectedNode, ConversationNodeView.DIALOGUE,((GraphConversationDataControl)conversationDataControl).getAllConditions());

			// Update the conversation panel and reload the options
			conversationPanel.reloadOptions( );
			conversationPanel.updateRepresentation();

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

		public void actionPerformed( ActionEvent e ) {
			// Take the selected row, and the selected node
			int selectedRow = lineTable.getSelectedRow( );
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

			// Delete the selected line
			conversationDataControl.deleteNodeLine( selectedNode, selectedRow ,((GraphConversationDataControl)conversationDataControl).getAllConditions().get(selectedNode));

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
	 * Listener for the "Edit effect" button
	 */
	private class ListenerButtonEditEffect implements ActionListener {

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
			}

			// If there is a line selected
			else {
				// Enable all options
				moveLineUpButton.setEnabled( true );
				moveLineDownButton.setEnabled( true );
				deleteOptionButton.setEnabled( true );
				deleteLineButton.setEnabled( true );

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
	 * Listener for the "Add child" option
	 */
	private class AddChildActionListener implements ActionListener {

		/**
		 * Type of node that this listener adds.
		 */
		private int nodeType;

		/**
		 * Constructor.
		 * 
		 * @param nodeType
		 *            Type of the node to be added
		 */
		public AddChildActionListener( int nodeType ) {
			this.nodeType = nodeType;
		}

		public void actionPerformed( ActionEvent e ) {
			if( conversationDataControl.addChild( conversationPanel.getSelectedNode( ), nodeType ,((GraphConversationDataControl)conversationDataControl).getAllConditions())){
				// Switch state to normal
				conversationPanel.changeState( RepresentationPanel.NORMAL );

				// Update the conversation panel and reload the options
				conversationPanel.reloadOptions( );
				conversationPanel.updateRepresentation( );
			}
		}
	}

	/**
	 * Listener for the "Delete node" option
	 */
	private class DeleteNodeActionListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {

			// If the node is deleted
			if( conversationDataControl.deleteNode( conversationPanel.getSelectedNode( ) ) ) {
				// Empty the selection
				conversationPanel.setSelectedNode( null );

				// Switch state to normal
				conversationPanel.changeState( RepresentationPanel.NORMAL );

				// Update the conversation panel
				conversationPanel.updateRepresentation( );
			}
		}
	}

	/**
	 * Listener for the "Add link to..." option
	 */
	private class LinkNodeActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if( conversationPanel.getState() == RepresentationPanel.NORMAL )
				conversationPanel.changeState( RepresentationPanel.WAITING_SECOND_NODE_TO_LINK );
		}
	}

}
