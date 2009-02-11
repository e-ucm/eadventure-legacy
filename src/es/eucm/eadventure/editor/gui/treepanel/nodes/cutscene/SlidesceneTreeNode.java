package es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.cutscene.CutscenePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.NextSceneTreeNode;

//import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ResourcesTreeNode;

public class SlidesceneTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private CutsceneDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/slidescene.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Slidescene to be contained
	 */
	public SlidesceneTreeNode( TreeNode parent, CutsceneDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		/*for( ResourcesDataControl resourcesDataControl : dataControl.getResources( ) )
			children.add( new ResourcesTreeNode( this, resourcesDataControl ) );*/

		for( NextSceneDataControl nextSceneDataControl : dataControl.getNextScenes( ) )
			children.add( new NextSceneTreeNode( this, nextSceneDataControl ) );

		if( dataControl.isEndScene( ) )
			children.add( new EndSceneTreeNode( this, dataControl.getEndScene( ) ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		/*if( type == Controller.RESOURCES ) {
			// Add the new resources node to last place of the resources elements
			addedTreeNode = new ResourcesTreeNode( this, dataControl.getLastResources( ) );
			children.add( dataControl.getResources( ).size( ) - 1, addedTreeNode );
		}*/

		if( type == Controller.NEXT_SCENE ) {
			addedTreeNode = new NextSceneTreeNode( this, dataControl.getLastNextScene( ) );
			children.add( addedTreeNode );
		} else if( type == Controller.END_SCENE ) {
			addedTreeNode = new EndSceneTreeNode( this, dataControl.getEndScene( ) );
			children.add( addedTreeNode );
		}

		// Spread the owner panel to the children
		spreadOwnerPanel( );

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// Check the references only if the cutscene is not an end scene
		if( !dataControl.isEndScene( ) ) {
			// Check all the next scene data. Start in position 1 because of the Resources list
			int i = 1;
			while( i < children.size( ) )
				if( ( i - 1 ) == dataControl.getNextScenes( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getNextScenes( ).get( i - 1 ) ) )
					children.remove( i );
				else
					i++;
		}
	}

	@Override
	protected int getNodeType( ) {
		return Controller.CUTSCENE_SLIDES;
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
		return new CutscenePanel( dataControl );
	}

	@Override
	public String toString( ) {
		return super.toString( ) + ": " + dataControl.getId( );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
}