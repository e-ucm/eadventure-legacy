package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.AdvancedFeaturesPanel;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.ChapterPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.adaptation.AdaptationProfilesTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.assessment.AssessmentProfilesTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.book.BooksListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character.NPCsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character.PlayerTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.conversation.ConversationsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.cutscene.CutscenesListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.item.ItemsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene.ScenesListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.timer.TimersListTreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Javier Torrente
 */
public class AdvancedFeaturesTreeNode extends TreeNode {

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/advanced.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public AdvancedFeaturesTreeNode( TreeNode parent, TimersListDataControl timersDataControl, AdaptationProfilesDataControl adaptationProfiles, AssessmentProfilesDataControl assessmentProfiles ) {
		super( parent );

		children.add( new TimersListTreeNode( this, timersDataControl ) );
		children.add( new AdaptationProfilesTreeNode( this, adaptationProfiles ) );
		children.add( new AssessmentProfilesTreeNode( this, assessmentProfiles ) );
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
		return Controller.ADVANCED_FEATURES;
	}

	@Override
	public DataControl getDataControl( ) {
		return null;
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
		return new AdvancedFeaturesPanel();
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.ADVANCED_FEATURES);
	}
}