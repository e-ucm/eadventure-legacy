package es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.cutscene.CutscenesListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class CutscenesListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private CutscenesListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/cutscenes.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of cutscenes
	 */
	public CutscenesListTreeNode( TreeNode parent, CutscenesListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( CutsceneDataControl cutsceneDataControl : dataControl.getCutscenes( ) ) {
			if( cutsceneDataControl.getType( ) == Controller.CUTSCENE_SLIDES )
				children.add( new SlidesceneTreeNode( this, cutsceneDataControl ) );
			else if( cutsceneDataControl.getType( ) == Controller.CUTSCENE_VIDEO )
				children.add( new VideosceneTreeNode( this, cutsceneDataControl ) );
		}
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// Add the last book of the list
		CutsceneDataControl lastCutsceneDataControl = dataControl.getLastCutscene( );

		if( lastCutsceneDataControl.getType( ) == Controller.CUTSCENE_SLIDES ) {
			addedTreeNode = new SlidesceneTreeNode( this, lastCutsceneDataControl );
			children.add( addedTreeNode );
		}

		else if( lastCutsceneDataControl.getType( ) == Controller.CUTSCENE_VIDEO ) {
			addedTreeNode = new VideosceneTreeNode( this, lastCutsceneDataControl );
			children.add( addedTreeNode );
		}

		// Spread the owner panel to the children
		spreadOwnerPanel( );

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
		return Controller.CUTSCENES_LIST;
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
		return new CutscenesListPanel( dataControl );
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

