package es.eucm.eadventure.editor.gui.treepanel.nodes.character;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.ConversationReferenceDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.character.ConversationReferencePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

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
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
	
	@Override
	public TreeNode isObjectContentTreeNode(Object object) {
		if (dataControl.getContent() == object)
			return this;
		return null;
	}

}

