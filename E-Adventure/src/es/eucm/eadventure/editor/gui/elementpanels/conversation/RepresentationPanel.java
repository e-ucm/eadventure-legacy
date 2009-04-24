package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.config.ConversationConfigData;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.GraphicRepresentation;
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
	
	private JPanel menuPanel;
	
	
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
		
		conversationRepresentation = new GraphicRepresentation( (GraphConversationDataControl) conversationDataControl, getSize( ) );

		this.setLayout(null);
		menuPanel = new MenuPanel();
		menuPanel.setVisible(false);
		this.add(menuPanel);
		
		// Add the mouse and resize listeners to the panel
		ConversationPanelMouseListener mouseListener = new ConversationPanelMouseListener(conversationRepresentation, conversationDataControl, conversationPanel, this);
		addMouseListener( mouseListener );
		addMouseMotionListener( mouseListener );
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
	
	public void paint( Graphics g ) {
		super.paint( g );

		// If there is a current conversation
		if( conversationRepresentation != null ) {
			conversationRepresentation.setSelectedNode(conversationPanel.getSelectedNode());
			conversationRepresentation.setSelectedChildNode(conversationPanel.getSelectedChild());
			
			// Draw the conversation
			conversationRepresentation.drawConversation( g );
		}
		
		Point node = conversationRepresentation.getSelectedNodePosition();
		if (node == null) {
			menuPanel.setVisible(false);
		} else {
			int x = (int) (node.getX() + GraphicNode.NODE_RADIUS * conversationRepresentation.getScale());
			int y = (int) (node.getY() + GraphicNode.NODE_RADIUS * conversationRepresentation.getScale() / 2);

			if (x + menuPanel.getSize().getWidth() > conversationPanel.getScrollXValue() + conversationPanel.getScrollSize().getWidth()) {
				x = (int) (node.getX() - GraphicNode.NODE_RADIUS * conversationRepresentation.getScale() - menuPanel.getWidth());
			}
			if (y + menuPanel.getSize().getHeight() > conversationPanel.getScrollYValue() + conversationPanel.getScrollSize().getHeight()) {
				y = (int) (node.getY() - GraphicNode.NODE_RADIUS * conversationRepresentation.getScale() - menuPanel.getHeight());
			}
			
			menuPanel.setLocation(x, y);
			
			menuPanel.setVisible(true);
		}
		menuPanel.repaint();
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

	public int getState() {
		return state;
	}
	
	public void setScale(float scale) {
		conversationRepresentation.setScale(scale);
	}
	
	public JPanel getMenuPanel() {
		return menuPanel;
	}

}
