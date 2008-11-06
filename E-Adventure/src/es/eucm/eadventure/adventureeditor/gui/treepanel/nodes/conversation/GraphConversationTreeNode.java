package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.conversation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.conversation.ConversationPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class GraphConversationTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ConversationDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/graphConversation.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Graph conversation to be contained
	 */
	public GraphConversationTreeNode( TreeNode parent, ConversationDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		// Do nothing (this node can't add children)
		return null;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.CONVERSATION_GRAPH;
	}

	@Override
	public DataControl getDataControl( ) {
		return dataControl;
	}

	@Override
	public Icon getIcon( ) {
		return icon;
	}

	@Override
	public String getToolTipText( ) {
		return null;
	}

	@Override
	public JComponent getEditPanel( ) {
		return new ConversationPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return super.toString( ) + ": " + dataControl.getId( );
	}
}