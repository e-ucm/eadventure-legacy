package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.adaptation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.adaptation.AdaptationRulePanel;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.assessment.AssessmentRulePanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class AdaptationRuleTreeNode extends TreeNode{

	/**
	 * Contained micro-controller.
	 */
	private AdaptationRuleDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/adaptationRule.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public AdaptationRuleTreeNode( TreeNode parent, AdaptationRuleDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;
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
		return Controller.ADAPTATION_RULE;
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
		return TextConstants.getElementName(Controller.ADAPTATION_RULE);
	}

	@Override
	public JComponent getEditPanel( ) {
		//return new ChapterPanel( dataControl );
		return new AdaptationRulePanel( dataControl );
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.ADAPTATION_RULE)+":"+dataControl.getId();
	}

	
}
