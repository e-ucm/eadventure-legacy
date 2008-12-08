package es.eucm.eadventure.editor.gui.treepanel.nodes.macro;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.macro.MacrosListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Javier
 */
public class MacrosListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private MacroListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/macros.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of scenes
	 */
	public MacrosListTreeNode( TreeNode parent, MacroListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( MacroDataControl globalStateDataControl : dataControl.getMacros( ) )
			children.add( new MacroTreeNode( this, globalStateDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a scene was added
		if( type == Controller.MACRO ) {
			// Add the last scene
			addedTreeNode = new MacroTreeNode( this, dataControl.getLastMacro( ) );
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
		return Controller.MACRO_LIST;
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
		return new MacrosListPanel ( dataControl );
	}
	
	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.MACRO_LIST);
	}

}
