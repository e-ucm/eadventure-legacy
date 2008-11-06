package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.ChapterPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.book.BooksListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character.NPCsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character.PlayerTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.conversation.ConversationsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.cutscene.CutscenesListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.item.ItemsListTreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene.ScenesListTreeNode;

/**
 * This class holds a list of game scenes.
 * 
 * @author Bruno Torijano Bueno
 */
public class ChapterTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ChapterDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/gameData.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Contained game data
	 */
	public ChapterTreeNode( TreeNode parent, ChapterDataControl dataControl, AssessmentProfilesDataControl assessmentDataControl, AdaptationProfilesDataControl adaptationDataControl ) {
		super( parent );
		this.dataControl = dataControl;

		children.add( new ScenesListTreeNode( this, dataControl.getScenesList( ) ) );
		children.add( new CutscenesListTreeNode( this, dataControl.getCutscenesList( ) ) );
		children.add( new BooksListTreeNode( this, dataControl.getBooksList( ) ) );
		children.add( new ItemsListTreeNode( this, dataControl.getItemsList( ) ) );
		children.add( new PlayerTreeNode( this, dataControl.getPlayer( ) ) );
		children.add( new NPCsListTreeNode( this, dataControl.getNPCsList( ) ) );
		children.add( new ConversationsListTreeNode( this, dataControl.getConversationsList( ) ) );
		children.add( new AdvancedFeaturesTreeNode( this, dataControl.getTimersList( ), adaptationDataControl, assessmentDataControl ) );
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
		return Controller.CHAPTER;
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
		return new ChapterPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return dataControl.getTitle( );
	}
}