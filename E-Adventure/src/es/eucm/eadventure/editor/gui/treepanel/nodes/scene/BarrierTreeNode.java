package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.BarrierPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class BarrierTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private BarrierDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/barrier.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            ActiveArea to be contained
	 */
	public BarrierTreeNode( TreeNode parent, BarrierDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

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
		return Controller.BARRIER;
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
		return new BarrierPanel( dataControl );
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
