package es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation;

import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.elementpanels.adaptation.AdaptationProfilesPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentProfileTreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Javier Torrente
 */
public class AdaptationProfilesTreeNode extends TreeNode {

	private AdaptationProfilesDataControl dataControl;
	
	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public AdaptationProfilesTreeNode( TreeNode parent, AdaptationProfilesDataControl dataControl ) {
		super( parent );

		this.dataControl = dataControl;
		
		for (AdaptationProfileDataControl profile: dataControl.getProfiles( ))
			children.add( new AdaptationProfileTreeNode( this, profile ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// Add the last adaptation profile
		AdaptationProfileDataControl lastDataControl = dataControl.getLastProfile( );

			addedTreeNode = new AdaptationProfileTreeNode( this, lastDataControl );
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
		return Controller.ADAPTATION_PROFILES;
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
		return new AdaptationProfilesPanel();
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.ADAPTATION_PROFILES);
	}
}