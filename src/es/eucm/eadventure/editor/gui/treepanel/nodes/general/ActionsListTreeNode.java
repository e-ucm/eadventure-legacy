package es.eucm.eadventure.editor.gui.treepanel.nodes.general;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ActionsListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class ActionsListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ActionsListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/actions.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of actions
	 */
	public ActionsListTreeNode( TreeNode parent, ActionsListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ActionDataControl actionDataControl : dataControl.getActions( ) ) {
			switch( actionDataControl.getType( ) ) {
				case Controller.ACTION_EXAMINE:
					children.add( new ExamineActionTreeNode( this, actionDataControl ) );
					break;
				case Controller.ACTION_GRAB:
					children.add( new GrabActionTreeNode( this, actionDataControl ) );
					break;
				case Controller.ACTION_USE:
					children.add( new UseActionTreeNode( this, actionDataControl ) );
					break;
				case Controller.ACTION_CUSTOM:
					children.add( new CustomActionTreeNode (this ,actionDataControl));
					break;
				case Controller.ACTION_USE_WITH:
					children.add( new UseWithActionTreeNode( this, actionDataControl ) );
					break;
				case Controller.ACTION_GIVE_TO:
					children.add( new GiveToActionTreeNode( this, actionDataControl ) );
					break;
				case Controller.ACTION_CUSTOM_INTERACT:
					children.add( new CustomInteractActionTreeNode( this, actionDataControl));
					break;
			}
		}
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;
		// Add the last scene of the list
		ActionDataControl lastActionDataControl = dataControl.getLastAction( );
		switch( lastActionDataControl.getType( ) ) {
			case Controller.ACTION_EXAMINE:
				addedTreeNode = new ExamineActionTreeNode( this, lastActionDataControl );
				children.add( addedTreeNode );
				break;
			case Controller.ACTION_GRAB:
				addedTreeNode = new GrabActionTreeNode( this, lastActionDataControl );
				children.add( addedTreeNode );
				break;
			case Controller.ACTION_USE:
				addedTreeNode = new UseActionTreeNode( this, lastActionDataControl );
				children.add( addedTreeNode );
				break;
			case Controller.ACTION_CUSTOM:
				addedTreeNode = new CustomActionTreeNode (this, lastActionDataControl);
				children.add( addedTreeNode);
				break;
			case Controller.ACTION_USE_WITH:
				addedTreeNode = new UseWithActionTreeNode( this, lastActionDataControl );
				children.add( addedTreeNode );
				break;
			case Controller.ACTION_GIVE_TO:
				addedTreeNode = new GiveToActionTreeNode( this, lastActionDataControl );
				children.add( addedTreeNode );
				break;
			case Controller.ACTION_CUSTOM_INTERACT:
				addedTreeNode = new CustomInteractActionTreeNode( this, lastActionDataControl);
				children.add(addedTreeNode);
				break;
		}

		// Spread the owner panel to the children
		spreadOwnerPanel( );

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some action is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getActions( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getActions( ).get( i ) ) )
				children.remove( i );
			else
				i++;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.ACTIONS_LIST;
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
		return new ActionsListPanel( dataControl );
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
