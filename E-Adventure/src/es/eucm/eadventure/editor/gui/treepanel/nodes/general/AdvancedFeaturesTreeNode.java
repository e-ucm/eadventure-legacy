package es.eucm.eadventure.editor.gui.treepanel.nodes.general;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.AdvancedFeaturesPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.adaptation.AdaptationProfilesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.assessment.AssessmentProfilesTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.globalstate.GlobalStatesListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.macro.MacrosListTreeNode;
import es.eucm.eadventure.editor.gui.treepanel.nodes.timer.TimersListTreeNode;

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
	public AdvancedFeaturesTreeNode( TreeNode parent, GlobalStateListDataControl globalStatesListDataControl,
			MacroListDataControl macrosListDataControl,
			TimersListDataControl timersDataControl, AdaptationProfilesDataControl adaptationProfiles, AssessmentProfilesDataControl assessmentProfiles ) {
		super( parent );

		children.add( new TimersListTreeNode( this, timersDataControl ) );
		children.add( new AdaptationProfilesTreeNode( this, adaptationProfiles ) );
		children.add( new AssessmentProfilesTreeNode( this, assessmentProfiles ) );
		children.add( new GlobalStatesListTreeNode( this, globalStatesListDataControl ) );
		children.add( new MacrosListTreeNode( this, macrosListDataControl ) );
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
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		return null;
	}
}