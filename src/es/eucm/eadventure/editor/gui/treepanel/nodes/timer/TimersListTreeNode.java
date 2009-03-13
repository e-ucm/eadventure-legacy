package es.eucm.eadventure.editor.gui.treepanel.nodes.timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.timer.TimersListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class TimersListTreeNode extends TreeNode{

	/**
	 * Contained micro-controller.
	 */
	private TimersListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/timers.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public TimersListTreeNode( TreeNode parent, TimersListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		//Add the children
		int index = 0;
		for (TimerDataControl timerDataControl : dataControl.getTimers( )){
			index++;
			children.add( new TimerTreeNode (this, timerDataControl, index) );
		}
	
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a scene was added
		if( type == Controller.TIMER ) {
			// Add the last scene
			int index = dataControl.getTimers( ).size( );
			addedTreeNode = new TimerTreeNode( this, dataControl.getLastTimer( ), index);
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
		return Controller.TIMERS_LIST;
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
		return toString();
	}

	@Override
	public JComponent getEditPanel( ) {
		return new TimersListPanel(dataControl);
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.TIMERS_LIST);
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
