package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.scene.ExitPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general.NextSceneTreeNode;

public class ExitTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ExitDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/exit.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Exit to be contained
	 */
	public ExitTreeNode( TreeNode parent, ExitDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( NextSceneDataControl nextSceneDataControl : dataControl.getNextScenes( ) )
			children.add( new NextSceneTreeNode( this, nextSceneDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a next scene was added
		if( type == Controller.NEXT_SCENE ) {
			// Add the last scene of the list
			addedTreeNode = new NextSceneTreeNode( this, dataControl.getLastNextScene( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some next scene is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getNextScenes( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getNextScenes( ).get( i ) ) )
				children.remove( i );
			else
				i++;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.EXIT;
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
		return new ExitPanel( dataControl );
	}
}
