package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreaPanel;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ExitPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ActionsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.NextSceneTreeNode;

public class ActiveAreaTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ActiveAreaDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/activeArea.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            ActiveArea to be contained
	 */
	public ActiveAreaTreeNode( TreeNode parent, ActiveAreaDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		children.add( new ActionsListTreeNode ( this , dataControl.getActionsList( )) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

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
		return Controller.ACTIVE_AREA;
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
		return new ActiveAreaPanel( dataControl );
	}
	
	public String toString( ) {
		String name=TextConstants.getElementName( getNodeType( ) );
		if (dataControl.getName( )!=null && !dataControl.getName( ).equals( "" )){
			name +=":"+dataControl.getName( );
		} else {
			name +=" #"+dataControl.getId( );
		}
		return name;
	}

}
