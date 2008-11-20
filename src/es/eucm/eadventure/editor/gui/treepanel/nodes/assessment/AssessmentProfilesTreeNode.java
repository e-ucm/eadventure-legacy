package es.eucm.eadventure.editor.gui.treepanel.nodes.assessment;

import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.assessment.AssessmentProfilesPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Javier Torrente
 */
public class AssessmentProfilesTreeNode extends TreeNode {

	private AssessmentProfilesDataControl dataControl;
	
	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/assessmentProfiles.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public AssessmentProfilesTreeNode( TreeNode parent, AssessmentProfilesDataControl dataControl ) {
		super( parent );
		
		this.dataControl = dataControl;

		for (AssessmentProfileDataControl profile: dataControl.getProfiles( ))
			children.add( new AssessmentProfileTreeNode( this, profile ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// Add the last assessment profile
		AssessmentProfileDataControl lastDataControl = dataControl.getLastProfile( );

			addedTreeNode = new AssessmentProfileTreeNode( this, lastDataControl );
			children.add( addedTreeNode );

		// Spread the owner panel to the children
		spreadOwnerPanel( );

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
		return Controller.ASSESSSMENT_PROFILES;
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
		return new AssessmentProfilesPanel();
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.ASSESSSMENT_PROFILES);
	}
}