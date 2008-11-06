package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.timer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.assessment.AssessmentRulePanel;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.timer.TimerPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class TimerTreeNode extends TreeNode{

	/**
	 * Contained micro-controller.
	 */
	private TimerDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;
	
	private int index;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/timer.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public TimerTreeNode( TreeNode parent, TimerDataControl dataControl, int index ) {
		super( parent );
		this.dataControl = dataControl;
		this.index = index;
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		// This node don't accept new children
		return null;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// Spread the call to the children
		for( TreeNode treeNode : children )
			treeNode.checkForDeletedReferences( );
	}

	@Override
	protected int getNodeType( ) {
		return Controller.TIMER;
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
		return TextConstants.getElementName(Controller.TIMER);
	}

	@Override
	public JComponent getEditPanel( ) {
		return new TimerPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName( Controller.TIMER )+":#"+index;
	}

	
}
