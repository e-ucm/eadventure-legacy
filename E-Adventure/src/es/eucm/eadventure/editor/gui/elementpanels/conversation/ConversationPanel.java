package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;

/**
 * This class centralizes all the operations for conversation structures and nodes. It has two panels, a panel to
 * represent the conversation graphically (RepresentationPanel), and a panel to display and edit the content of nodes
 * (LinesPanel). It also has a status bar which informs the user of the status of the application
 */
public class ConversationPanel extends JPanel {

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Scroll pane containing the graphic representation of the tree.
	 */
	private JScrollPane scrollPanel;

	/**
	 * Panel in which the conversation is graphically representated
	 */
	private RepresentationPanel representationPanel;

	/**
	 * Panel in which the nodes are visible and editable
	 */
	private LinesPanel linesPanel;

	/**
	 * Status bar
	 */
	private StatusBar statusBar;

	/**
	 * Selected node
	 */
	private ConversationNodeView selectedNode;

	/**
	 * Selected child (always a child of the selected node)
	 */
	private ConversationNodeView selectedChild;

	/**
	 * Constructor.
	 * 
	 * @param conversationDataControl
	 *            Controller of the conversation
	 */
	public ConversationPanel( ConversationDataControl conversationDataControl ) {
		selectedNode = null;
		selectedChild = null;

		// Create the status bar
		statusBar = new StatusBar( "Status: Normal" );

		// Create the conversation and node panels
		representationPanel = new RepresentationPanel( this, conversationDataControl );
		linesPanel = new LinesPanel( this, conversationDataControl );

		// Create a new panel, to be placed down, containing the node panel and the status bar
		JPanel downPanel = new JPanel( );
		downPanel.setPreferredSize( new Dimension( 0, 200 ) );

		// Add the node panel and the status bar to the down panel
		downPanel.setLayout( new BorderLayout( ) );
		downPanel.add( linesPanel, BorderLayout.CENTER );
		downPanel.add( statusBar, BorderLayout.SOUTH );

		// Create the scroll panel which contains the conversation panel
		scrollPanel = new JScrollPane( representationPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );

		// Add the scroll panel (conversation panel) and the down panel (node panel and status bar) to the principal
		// panel
		setLayout( new BorderLayout( ) );
		add( scrollPanel, BorderLayout.CENTER );
		add( downPanel, BorderLayout.SOUTH );
	}

	/**
	 * Sets a new text in the status bar.
	 * 
	 * @param text
	 *            New text
	 */
	public void setStatusBarText( String text ) {
		statusBar.setText( text );
	}

	/**
	 * Updates the graphic representation of the conversation panel, and informs the main window that the file has been
	 * altered
	 */
	public void changePerformedInNode( ) {
		representationPanel.updateRepresentation( );
	}

	/**
	 * Reloads the button of the lines panel.
	 */
	public void reloadOptions( ) {
		linesPanel.reloadOptions( );
	}

	/**
	 * Called when the scroll needs to be refreshed
	 */
	public void reloadScroll( ) {
		scrollPanel.getViewport( ).revalidate( );
	}

	/**
	 * Sets the currently selected node
	 * 
	 * @param selectedNode
	 *            New selected node
	 */
	public void setSelectedNode( ConversationNodeView selectedNode ) {
		// Sets the new selected node, and sets an empty selected child
		this.selectedNode = selectedNode;
		this.selectedChild = null;

		// Inform the node panel that a new node has been selected, and repaint the conversation panel
		linesPanel.newSelectedNode( );
		representationPanel.repaint( );
		revalidate( );
	}

	/**
	 * Returns a reference to the selected node
	 * 
	 * @return The view node currently selected
	 */
	public ConversationNodeView getSelectedNode( ) {
		return selectedNode;
	}

	/**
	 * Sets the currently selected child (always a child of the selected node)
	 * 
	 * @param selectedChild
	 *            New selected child
	 */
	public void setSelectedChild( ConversationNodeView selectedChild ) {
		// Set selected child and repaint the conversation panel
		this.selectedChild = selectedChild;
		representationPanel.repaint( );
	}

	/**
	 * Returns a reference to the selected child (always a child of the selected node)
	 * 
	 * @return The child view node currently selected
	 */
	public ConversationNodeView getSelectedChild( ) {
		return selectedChild;
	}
}
