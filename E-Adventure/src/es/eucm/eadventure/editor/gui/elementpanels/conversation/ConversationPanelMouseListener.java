package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.TreeConversationDataControl;
import es.eucm.eadventure.editor.gui.displaydialogs.ConversationDialog;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.GraphicRepresentation;

public class ConversationPanelMouseListener implements MouseListener, MouseMotionListener {

	/**
	 * Graphic representation of the conversation ADT (it can change with different ADTs, such as tree, graphs...)
	 */
	private GraphicRepresentation conversationRepresentation;

	/**
	 * Conversation controller.
	 */
	private ConversationDataControl conversationDataControl;

	/**
	 * Link to the principal panel
	 */
	private ConversationPanel conversationPanel;

	private RepresentationPanel representationPanel;
	
	public ConversationPanelMouseListener(GraphicRepresentation conversationRepresentation, ConversationDataControl conversationDataControl, ConversationPanel conversationPanel, RepresentationPanel representationPanel) {
		this.conversationRepresentation = conversationRepresentation;
		this.conversationDataControl = conversationDataControl;
		this.conversationPanel = conversationPanel;
		this.representationPanel = representationPanel;
	}
	
	
	
	public void mouseClicked( MouseEvent e ) {
		// Takes the node in which the click has been made (null if none) and send it to the principal panel
		ConversationNodeView clickedNode = conversationRepresentation.getNodeInPosition( e.getPoint( ) );

		// If state is normal, select the clicked node
		if( representationPanel.getState() == RepresentationPanel.NORMAL ) {
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
		else if( representationPanel.getState() == RepresentationPanel.WAITING_SECOND_NODE_TO_MOVE ) {

			// If the node is moved, update the representation
			if( clickedNode != null && conversationDataControl.moveNode( conversationPanel.getSelectedNode( ), clickedNode ) )
				representationPanel.updateRepresentation( );

			// Switch to normal state
			representationPanel.changeState( RepresentationPanel.NORMAL );
		}

		// If we are waiting for a destination node to link with the selected one
		else if( representationPanel.getState() == RepresentationPanel.WAITING_SECOND_NODE_TO_LINK ) {

			// If the node is linked, update the representation and reload the options
			if( clickedNode != null && conversationDataControl.linkNode( conversationPanel.getSelectedNode( ), clickedNode ) ) {
				conversationPanel.reloadOptions( );
				representationPanel.updateRepresentation( );
			}

			// Switch to normal state
			representationPanel.changeState( RepresentationPanel.NORMAL );
		}
	}

	public void mousePressed( MouseEvent e ) {
		// Spread the call into the conversation representation
		conversationRepresentation.mousePressed( e.getPoint( ) );
	}

	public void mouseReleased( MouseEvent e ) {
		// Spread the call into the conversation representation
		if( conversationRepresentation.mouseReleased( ) ) {
			representationPanel.setPreferredSize( conversationRepresentation.getConversationSize( ) );
			conversationPanel.reloadScroll( );
			conversationPanel.repaint( );
		}
	}

	public void mouseDragged( MouseEvent e ) {
		// Spread the call into the conversation representation
		if( conversationRepresentation.mouseDragged( e.getPoint( ) ) )
			representationPanel.repaint( );
		
		int x = conversationPanel.getScrollXValue();
		if (e.getPoint().x - x  < 20)
			conversationPanel.changeScrollX(-20);
		if (e.getPoint().x - x > conversationPanel.getScrollSize().width - 30)
			conversationPanel.changeScrollX(20);
		
		int y = conversationPanel.getScrollYValue();
		if (e.getPoint().y - y  < 20)
			conversationPanel.changeScrollY(-20);
		if (e.getPoint().y - y > conversationPanel.getScrollSize().height - 30)
			conversationPanel.changeScrollY(20);

	}

	// Not implemented
	public void mouseEntered( MouseEvent e ) {}

	public void mouseExited( MouseEvent e ) {}

	public void mouseMoved( MouseEvent e ) {}


	
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

		public void actionPerformed( ActionEvent e ) {
			// If the child was added successfully
			if( conversationDataControl.addChild( conversationPanel.getSelectedNode( ), nodeType ) ) {
				// Switch state to normal
				representationPanel.changeState( RepresentationPanel.NORMAL );

				// Update the conversation panel and reload the options
				conversationPanel.reloadOptions( );
				representationPanel.updateRepresentation( );
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

		public void actionPerformed( ActionEvent e ) {

			// If the node is deleted
			if( conversationDataControl.deleteNode( conversationPanel.getSelectedNode( ) ) ) {
				// Empty the selection
				conversationPanel.setSelectedNode( null );

				// Switch state to normal
				representationPanel.changeState( RepresentationPanel.NORMAL );

				// Update the conversation panel
				representationPanel.updateRepresentation( );
			}
		}
	}

	/**
	 * Listener for the "Add link to..." option
	 */
	private class LinkNodeActionListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			if( representationPanel.getState() == RepresentationPanel.NORMAL )
				representationPanel.changeState( RepresentationPanel.WAITING_SECOND_NODE_TO_LINK );
		}
	}

	/**
	 * Listener for the "Move node to..." option
	 */
	private class MoveNodeActionListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			if( representationPanel.getState() == RepresentationPanel.NORMAL )
				representationPanel.changeState( RepresentationPanel.WAITING_SECOND_NODE_TO_MOVE );
		}
	}

	/**
	 * Listener for the "Perform go-back" option. This action can only take place when the active conversation is a tree
	 * conversation
	 */
	private class ToggleGoBackActionListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {

			// This method should only be called with tree conversations
			TreeConversationDataControl treeConversationDataControl = (TreeConversationDataControl) conversationDataControl;

			// Add or remove the tag, depending if the node has the tag or not
			if( treeConversationDataControl.toggleGoBackTag( conversationPanel.getSelectedNode( ) ) ) {
				// Switch state to normal
				representationPanel.changeState( RepresentationPanel.NORMAL );

				// Update the node and conversation panel and reload the options
				conversationPanel.reloadOptions( );
				representationPanel.updateRepresentation( );
			}
		}
	}




}
