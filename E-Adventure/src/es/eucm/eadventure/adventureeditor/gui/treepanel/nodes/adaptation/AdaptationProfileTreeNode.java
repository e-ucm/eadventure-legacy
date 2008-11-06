package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.adaptation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.net.URL;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.adaptation.AdaptationRulesListPanel;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.ChapterPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.book.BooksListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character.NPCsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character.PlayerTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.conversation.ConversationsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.cutscene.CutscenesListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.item.ItemsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene.SceneTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene.ScenesListTreeNode;

public class AdaptationProfileTreeNode extends TreeNode{

	/**
	 * Contained micro-controller.
	 */
	private AdaptationProfileDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/adaptationRulesList.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public AdaptationProfileTreeNode( TreeNode parent, AdaptationProfileDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		//Add the children
		for (AdaptationRuleDataControl adpRuleDataControl : dataControl.getAdaptationRules( ))
			children.add( new AdaptationRuleTreeNode (this, adpRuleDataControl) );
		//children.add( new ScenesListTreeNode( this, dataControl.getScenesList( ) ) );
		
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a scene was added
		if( type == Controller.ADAPTATION_RULE ) {
			// Add the last scene
			addedTreeNode = new AdaptationRuleTreeNode( this, dataControl.getLastAdaptationRule( ));
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
		return Controller.ADAPTATION_PROFILE;
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
		return new AdaptationRulesListPanel(dataControl);
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.ADAPTATION_PROFILE)+"("+dataControl.getFileName( )+")";
	}
}
