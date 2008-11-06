package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.character.ConversationReferenceDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.character.ConversationReferencePanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class ConversationReferenceTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ConversationReferenceDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/conversationReference.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Conversation reference to be contained
	 */
	public ConversationReferenceTreeNode( TreeNode parent, ConversationReferenceDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		// This node can't add children
		return null;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.CONVERSATION_REFERENCE;
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
		return new ConversationReferencePanel( dataControl );
	}

	@Override
	public String toString( ) {
		return TextConstants.getText( "Element.Ref", dataControl.getIdTarget( ) );
	}
}
