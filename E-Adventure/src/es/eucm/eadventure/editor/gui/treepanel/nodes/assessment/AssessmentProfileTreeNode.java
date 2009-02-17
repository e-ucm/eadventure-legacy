package es.eucm.eadventure.editor.gui.treepanel.nodes.assessment;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.net.URL;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.assessment.AssessmentProfilePanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.ChapterPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.book.BooksListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.NPCsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.character.PlayerTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.conversation.ConversationsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.cutscene.CutscenesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.item.ItemsListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.SceneTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ScenesListTreeNode;

public class AssessmentProfileTreeNode extends TreeNode{

	/**
	 * Contained micro-controller.
	 */
	private AssessmentProfileDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/assessmentRulesList.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public AssessmentProfileTreeNode( TreeNode parent, AssessmentProfileDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		//Add the children
		for (AssessmentRuleDataControl assRuleDataControl : dataControl.getAssessmentRules( )){
			if (assRuleDataControl.isTimedRule( )){
				children.add( new TimedAssessmentRuleTreeNode (this, assRuleDataControl) );
			}else {
				children.add( new AssessmentRuleTreeNode (this, assRuleDataControl) );
			}
		}
		//children.add( new ScenesListTreeNode( this, dataControl.getScenesList( ) ) );
		
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a scene was added
		if( type == Controller.ASSESSMENT_RULE ) {
			// Add the last scene
			addedTreeNode = new AssessmentRuleTreeNode( this, dataControl.getLastAssessmentRule( ));
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}
		
		else if( type == Controller.TIMED_ASSESSMENT_RULE ) {
			// Add the last scene
			addedTreeNode = new TimedAssessmentRuleTreeNode( this, dataControl.getLastAssessmentRule( ));
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
		return Controller.ASSESSMENT_PROFILE;
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
		//return new ChapterPanel( dataControl );
		return new AssessmentProfilePanel(dataControl);
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.ASSESSMENT_PROFILE)+"("+dataControl.getFileName( )+")";
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
