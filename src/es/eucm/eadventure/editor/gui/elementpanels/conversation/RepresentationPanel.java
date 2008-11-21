package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.ConversationConfigData;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.TreeConversationDataControl;
import es.eucm.eadventure.editor.gui.displaydialogs.ConversationDialog;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.GraphGraphicRepresentation;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.GraphicRepresentation;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.TreeGraphicRepresentation;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode.GraphicNode;

/**
 * This class is the panel used to display the graphical representation of the current conversation. It paints the
 * conversation depending of the implementation of the conversation
 */
class RepresentationPanel extends JPanel {

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * State of the panel: normal state
	 */
	public static final int NORMAL = 0;

	/**
	 * State of the panel: when it's waiting for a destination node to move the selected one
	 */
	public static final int WAITING_SECOND_NODE_TO_MOVE = 1;

	/**
	 * State of the panel: when it's waiting for a node to link it to the selected node as a child
	 */
	public static final int WAITING_SECOND_NODE_TO_LINK = 2;

	/**
	 * State of the panel
	 */
	private int state = NORMAL;

	/**
	 * Conversation controller.
	 */
	private ConversationDataControl conversationDataControl;

	/**
	 * Graphic representation of the conversation ADT (it can change with different ADTs, such as tree, graphs...)
	 */
	private GraphicRepresentation conversationRepresentation;

	/**
	 * Link to the principal panel
	 */
	private ConversationPanel conversationPanel;
	
	
	/**
	 * Constructor.
	 * 
	 * @param principalPanel
	 *            Link to the principal panel
	 * @param conversationDataControl
	 *            Conversation controller
	 */
	public RepresentationPanel( ConversationPanel principalPanel, ConversationDataControl conversationDataControl ) {
		this.conversationPanel = principalPanel;
		this.conversationDataControl = conversationDataControl;

		// If the conversation is a tree conversation, create a new tree graphic representation
		if( conversationDataControl.getType( ) == Controller.CONVERSATION_TREE )
			conversationRepresentation = new TreeGraphicRepresentation( (TreeConversationDataControl) conversationDataControl, getSize( ) );

		// If the conversation is a graph conversation, create a new graph graphic representation
		else if( conversationDataControl.getType( ) == Controller.CONVERSATION_GRAPH )
			conversationRepresentation = new GraphGraphicRepresentation( (GraphConversationDataControl) conversationDataControl, getSize( ) );

		// Add the mouse and resize listeners to the panel
		addMouseListener( new ConversationPanelMouseListener( ) );
		addMouseMotionListener( new ConversationPanelMouseListener( ) );
		addComponentListener( new ConversationPanelComponentListener( ) );

		// Set the preferred size of the panel, with the size of the graphical represented conversation
		setPreferredSize( conversationRepresentation.getConversationSize( ) );
		revalidate( );
	}

	/**
	 * Updates the graphic representation, when a node or link has been added, removed or moved
	 */
	public void updateRepresentation( ) {
		// Rebuilds the graphic representation and the preferred size of the panel
		conversationRepresentation.update( );
		setPreferredSize( conversationRepresentation.getConversationSize( ) );
		revalidate( );
		repaint( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint( Graphics g ) {
		super.paint( g );

		// Set antialiasing
		// TODO Antialiased disabled due to problems with large areas
		// Graphics2D g2 = (Graphics2D)g;
		// g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

		// If there is a current conversation
		if( conversationRepresentation != null ) {

			// If there is a selected node, draw a big red filled circle (to make it seem selected)
			if( conversationPanel.getSelectedNode( ) != null ) {
				Point point = conversationRepresentation.getPositionOfNode( conversationPanel.getSelectedNode( ) );
				g.setColor( Color.RED );
				g.fillOval( point.x - GraphicNode.NODE_SELECTED_RADIUS, point.y - GraphicNode.NODE_SELECTED_RADIUS, GraphicNode.NODE_SELECTED_DIAMETER, GraphicNode.NODE_SELECTED_DIAMETER );
			}

			// If there is a selected child, draw a big blue filled circle (to make it seem selected)
			if( conversationPanel.getSelectedChild( ) != null ) {
				Point point = conversationRepresentation.getPositionOfNode( conversationPanel.getSelectedChild( ) );
				g.setColor( Color.BLUE );
				g.fillOval( point.x - GraphicNode.NODE_SELECTED_RADIUS, point.y - GraphicNode.NODE_SELECTED_RADIUS, GraphicNode.NODE_SELECTED_DIAMETER, GraphicNode.NODE_SELECTED_DIAMETER );
			}

			// Draw the conversation
			conversationRepresentation.drawConversation( g );
		}
	}

	/**
	 * Changes the state of the panel to the given state
	 * 
	 * @param newState
	 *            New state for the panel
	 */
	public void changeState( int newState ) {
		state = newState;

		// Change the status bar text in the main panel
		switch( state ) {
			case NORMAL:
				conversationPanel.setStatusBarText( TextConstants.getText( "Conversation.StatusBarNormal" ) );
				break;
			case WAITING_SECOND_NODE_TO_MOVE:
				conversationPanel.setStatusBarText( TextConstants.getText( "Conversation.StatusWaitingToMove" ) );
				break;
			case WAITING_SECOND_NODE_TO_LINK:
				conversationPanel.setStatusBarText( TextConstants.getText( "Conversation.StatusWaitingToLink" ) );
				break;
		}
	}

	/**
	 * Mouse listener for the panel
	 */
	private class ConversationPanelMouseListener implements MouseListener, MouseMotionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked( MouseEvent e ) {
			// Takes the node in which the click has been made (null if none) and send it to the principal panel
			ConversationNodeView clickedNode = conversationRepresentation.getNodeInPosition( e.getPoint( ) );

			// If state is normal, select the clicked node
			if( state == NORMAL ) {
				conversationPanel.setSelectedNode( clickedNode );

				// If some node was selected and the right button was clicked, show the menu
				if( clickedNode != null && e.getButton( ) == MouseEvent.BUTTON3 ) {
					// Create the popup menu
					JPopupMenu nodePopupMenu = new JPopupMenu( );

					// Add the preview options
					JMenuItem previewConversationItem = new JMenuItem( TextConstants.getText( "Conversation.OptionPreviewConversation" ) );
					previewConversationItem.addActionListener( new PreviewConversationActionListener( true ) );
					nodePopupMenu.add( previewConversationItem );
					JMenuItem previewPartialConversationItem = new JMenuItem( TextConstants.getText( "Conversation.OptionPreviewPartialConversation" ) );
					previewPartialConversationItem.addActionListener( new PreviewConversationActionListener( false ) );
					nodePopupMenu.add( previewPartialConversationItem );

					// Add separator
					nodePopupMenu.addSeparator( );

					// Add the add operations
					for( int nodeType : conversationDataControl.getAddableNodes( clickedNode ) ) {
						JMenuItem addNodeItem = new JMenuItem( );
						if( nodeType == ConversationNode.DIALOGUE )
							addNodeItem.setText( TextConstants.getText( "Conversation.OptionAddDialogueNode" ) );
						else if( nodeType == ConversationNode.OPTION )
							addNodeItem.setText( TextConstants.getText( "Conversation.OptionAddOptionNode" ) );
						addNodeItem.setEnabled( conversationDataControl.canAddChild( clickedNode, nodeType ) );
						addNodeItem.addActionListener( new AddChildActionListener( nodeType ) );
						nodePopupMenu.add( addNodeItem );
					}

					// Add separator
					nodePopupMenu.addSeparator( );
					
					// Add the random option, if current node is option node
					if (clickedNode.getType()==ConversationNode.OPTION){
						JCheckBoxMenuItem itShowRandomly = new JCheckBoxMenuItem( TextConstants.getText( "Conversation.OptionRandomly"), conversationDataControl.isRandomActivate(clickedNode) );
						itShowRandomly.addActionListener( new ChangeOptionRandomActionListener(clickedNode) );
						nodePopupMenu.add( itShowRandomly );
					}

					// Add separator
					nodePopupMenu.addSeparator( );
					
					// Add delete element
					JMenuItem deleteNodeItem = new JMenuItem( TextConstants.getText( "Conversation.OptionDeleteNode" ) );
					deleteNodeItem.setEnabled( conversationDataControl.canDeleteNode( clickedNode ) );
					deleteNodeItem.addActionListener( new DeleteNodeActionListener( ) );
					nodePopupMenu.add( deleteNodeItem );

					// Add separator
					nodePopupMenu.addSeparator( );

					// Add the link and move operations
					JMenuItem linkNodeItem = new JMenuItem( TextConstants.getText( "Conversation.OptionLinkNode" ) );
					linkNodeItem.setEnabled( conversationDataControl.canLinkNode( clickedNode ) );
					linkNodeItem.addActionListener( new LinkNodeActionListener( ) );
					nodePopupMenu.add( linkNodeItem );

					JMenuItem moveNodeItem = new JMenuItem( TextConstants.getText( "Conversation.OptionMoveNode" ) );
					moveNodeItem.setEnabled( conversationDataControl.canMoveNode( clickedNode ) );
					moveNodeItem.addActionListener( new MoveNodeActionListener( ) );
					nodePopupMenu.add( moveNodeItem );

					// If it is a tree conversation, add the go back tag option
					if( conversationDataControl.getType( ) == Controller.CONVERSATION_TREE ) {
						// Add separator
						nodePopupMenu.addSeparator( );

						// Add the go back tag operation
						JMenuItem setGoBackTagItem = new JMenuItem( );
						if( !TreeConversationDataControl.thereIsGoBackTag( clickedNode ) )
							setGoBackTagItem.setText( TextConstants.getText( "Conversation.OptionAddGoBackTag" ) );
						else
							setGoBackTagItem.setText( TextConstants.getText( "Conversation.OptionRemoveGoBackTag" ) );
						setGoBackTagItem.setEnabled( ( (TreeConversationDataControl) conversationDataControl ).canToggleGoBackTag( clickedNode ) );
						setGoBackTagItem.addActionListener( new ToggleGoBackActionListener( ) );
						nodePopupMenu.add( setGoBackTagItem );
					}

					// Display the menu
					nodePopupMenu.show( e.getComponent( ), e.getX( ), e.getY( ) );
				}

				// If the right button was pressed, show a dialog with the "Preview conversation" option only
				else if( e.getButton( ) == MouseEvent.BUTTON3 ) {
					// Create the popup menu
					JPopupMenu nodePopupMenu = new JPopupMenu( );

					// Add the preview options
					JMenuItem previewConversationItem = new JMenuItem( TextConstants.getText( "Conversation.OptionPreviewConversation" ) );
					previewConversationItem.addActionListener( new PreviewConversationActionListener( true ) );
					nodePopupMenu.add( previewConversationItem );

					// Display the menu
					nodePopupMenu.show( e.getComponent( ), e.getX( ), e.getY( ) );
				}
			}

			// If we are waiting for a destination node to move the selected one
			else if( state == WAITING_SECOND_NODE_TO_MOVE ) {

				// If the node is moved, update the representation
				if( clickedNode != null && conversationDataControl.moveNode( conversationPanel.getSelectedNode( ), clickedNode ) )
					updateRepresentation( );

				// Switch to normal state
				changeState( NORMAL );
			}

			// If we are waiting for a destination node to link with the selected one
			else if( state == WAITING_SECOND_NODE_TO_LINK ) {

				// If the node is linked, update the representation and reload the options
				if( clickedNode != null && conversationDataControl.linkNode( conversationPanel.getSelectedNode( ), clickedNode ) ) {
					conversationPanel.reloadOptions( );
					updateRepresentation( );
				}

				// Switch to normal state
				changeState( NORMAL );
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed( MouseEvent e ) {
			// Spread the call into the conversation representation
			conversationRepresentation.mousePressed( e.getPoint( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased( MouseEvent e ) {
			// Spread the call into the conversation representation
			if( conversationRepresentation.mouseReleased( ) ) {
				setPreferredSize( conversationRepresentation.getConversationSize( ) );
				conversationPanel.reloadScroll( );
				conversationPanel.repaint( );
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		public void mouseDragged( MouseEvent e ) {
			// Spread the call into the conversation representation
			if( conversationRepresentation.mouseDragged( e.getPoint( ) ) )
				repaint( );
		}

		// Not implemented
		public void mouseEntered( MouseEvent e ) {}

		public void mouseExited( MouseEvent e ) {}

		public void mouseMoved( MouseEvent e ) {}
	}

	/**
	 * Component listener for the panel (used for resize)
	 */
	private class ConversationPanelComponentListener extends ComponentAdapter {

		@Override
		public void componentResized( ComponentEvent e ) {
			boolean posConfigured =  ConversationConfigData.isConversationConfig ( conversationDataControl.getId( ) );
			// When resized, give the panel size to the graphic representation
			if (!posConfigured){
				conversationRepresentation.setContainerSize( getSize( ) );
			}
			repaint( );
		}
	}

	/**
	 * Listener for the "Preview conversation" and "Preview conversation from this node" options
	 */
	private class PreviewConversationActionListener implements ActionListener {

		/**
		 * True if the conversation must be played from the root node, false from the selected node.
		 */
		private boolean completePreview;

		/**
		 * Constructor.
		 * 
		 * @param completePreview
		 *            True if the conversation must be played from the root node, false from the selected node.
		 */
		public PreviewConversationActionListener( boolean completePreview ) {
			this.completePreview = completePreview;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// If it is a complete preview, show the dialog for the root node
			if( completePreview )
				new ConversationDialog( conversationDataControl, conversationDataControl.getRootNode( ) );

			// If not, take the selected node
			else
				new ConversationDialog( conversationDataControl, conversationPanel.getSelectedNode( ) );
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// If the child was added successfully
			if( conversationDataControl.addChild( conversationPanel.getSelectedNode( ), nodeType ) ) {
				// Switch state to normal
				changeState( NORMAL );

				// Update the conversation panel and reload the options
				conversationPanel.reloadOptions( );
				updateRepresentation( );
			}
		}
	}

	/**
	 * Listener for the "Order Options Randomly" option
	 */
	private class ChangeOptionRandomActionListener implements ActionListener {
		
		
		/**
		 * Current selected node.
		 */
		private ConversationNodeView clickedNode;
		
		/**
		 * Constructor.
		 * 
		 * @param clickedNode
		 * 					Selected node to change random option.
		 */
		
		public ChangeOptionRandomActionListener(ConversationNodeView clickedNode){
			this.clickedNode = clickedNode;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {

			conversationDataControl.setRandomlyOptions(clickedNode);
			//( (JCheckBoxMenuItem) e.getSource( ) ).isSelected( )
			//updateRepresentation();
			
		}
	}
	
	
	/**
	 * Listener for the "Delete node" option
	 */
	private class DeleteNodeActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {

			// If the node is deleted
			if( conversationDataControl.deleteNode( conversationPanel.getSelectedNode( ) ) ) {
				// Empty the selection
				conversationPanel.setSelectedNode( null );

				// Switch state to normal
				changeState( NORMAL );

				// Update the conversation panel
				updateRepresentation( );
			}
		}
	}

	/**
	 * Listener for the "Add link to..." option
	 */
	private class LinkNodeActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			if( state == NORMAL )
				changeState( WAITING_SECOND_NODE_TO_LINK );
		}
	}

	/**
	 * Listener for the "Move node to..." option
	 */
	private class MoveNodeActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			if( state == NORMAL )
				changeState( WAITING_SECOND_NODE_TO_MOVE );
		}
	}

	/**
	 * Listener for the "Perform go-back" option. This action can only take place when the active conversation is a tree
	 * conversation
	 */
	private class ToggleGoBackActionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {

			// This method should only be called with tree conversations
			TreeConversationDataControl treeConversationDataControl = (TreeConversationDataControl) conversationDataControl;

			// Add or remove the tag, depending if the node has the tag or not
			if( treeConversationDataControl.toggleGoBackTag( conversationPanel.getSelectedNode( ) ) ) {
				// Switch state to normal
				changeState( NORMAL );

				// Update the node and conversation panel and reload the options
				conversationPanel.reloadOptions( );
				updateRepresentation( );
			}
		}
	}
}
