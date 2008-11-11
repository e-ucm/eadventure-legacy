package es.eucm.eadventure.editor.gui.treepanel.nodes.general;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.NextScenePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class NextSceneTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private NextSceneDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/nextScene.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Next scene to be contained
	 */
	public NextSceneTreeNode( TreeNode parent, NextSceneDataControl dataControl ) {
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
		return Controller.NEXT_SCENE;
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
		return new NextScenePanel( dataControl );
	}

	@Override
	public String toString( ) {
		return super.toString( ) + ": " + dataControl.getNextSceneId( );
	}
}
