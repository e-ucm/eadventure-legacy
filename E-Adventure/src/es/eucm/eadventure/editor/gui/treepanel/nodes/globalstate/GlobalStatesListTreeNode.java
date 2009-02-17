package es.eucm.eadventure.editor.gui.treepanel.nodes.globalstate;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.globalstate.GlobalStatesListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Javier
 */
public class GlobalStatesListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private GlobalStateListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/groups16.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of scenes
	 */
	public GlobalStatesListTreeNode( TreeNode parent, GlobalStateListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( GlobalStateDataControl globalStateDataControl : dataControl.getGlobalStates( ) )
			children.add( new GlobalStateTreeNode( this, globalStateDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a scene was added
		if( type == Controller.GLOBAL_STATE ) {
			// Add the last scene
			addedTreeNode = new GlobalStateTreeNode( this, dataControl.getLastGlobalState( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// Spread the call to the children
		for( TreeNode treeNode : children )
			treeNode.checkForDeletedReferences( );
	}

	@Override
	protected int getNodeType( ) {
		return Controller.GLOBAL_STATE_LIST;
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
		return new GlobalStatesListPanel ( dataControl );
	}
	
	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.GLOBAL_STATE_LIST);
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
