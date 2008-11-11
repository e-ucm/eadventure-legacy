package es.eucm.eadventure.editor.gui.treepanel.nodes.conversation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.ConversationPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class TreeConversationTreeNode extends TreeNode {

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
		icon = new ImageIcon( "img/icons/treeConversation.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Tree conversation to be contained
	 */
	public TreeConversationTreeNode( TreeNode parent, ConversationDataControl dataControl ) {
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
		return Controller.CONVERSATION_TREE;
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