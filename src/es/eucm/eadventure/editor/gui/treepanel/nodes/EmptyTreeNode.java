package es.eucm.eadventure.editor.gui.treepanel.nodes;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.EmptyDataControl;

public class EmptyTreeNode extends TreeNode{

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;
	
	public EmptyTreeNode( TreeNode parent ) {
		super( parent );
	}

	@Override
	public void checkForDeletedReferences( ) {
		
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		return null;
	}

	@Override
	public DataControl getDataControl( ) {
		return new EmptyDataControl();
	}

	@Override
	public JComponent getEditPanel( ) {
		return new JPanel();
	}

	@Override
	public Icon getIcon( ) {
		return icon;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.CHAPTER;
	}

	@Override
	public String getToolTipText( ) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/gameData.png" );
	}
}
