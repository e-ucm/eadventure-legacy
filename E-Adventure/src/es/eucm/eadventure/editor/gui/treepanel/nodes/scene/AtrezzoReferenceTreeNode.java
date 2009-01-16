package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.AtrezzoReferencePanel;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class AtrezzoReferenceTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ElementReferenceDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/Atrezzo-Refs-1.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Atrezzo item reference to be contained
	 */
	public AtrezzoReferenceTreeNode( TreeNode parent, ElementReferenceDataControl dataControl ) {
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
		return Controller.ATREZZO_REFERENCE;
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
		return new ElementReferencePanel( dataControl );
	}

	@Override
	public String toString( ) {
		return TextConstants.getText( "Element.Ref", dataControl.getElementId( ) );
	}
}

