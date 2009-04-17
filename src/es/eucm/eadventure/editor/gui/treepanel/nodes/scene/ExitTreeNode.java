package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ExitPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.general.NextSceneTreeNode;

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
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {

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
