package es.eucm.eadventure.editor.control.controllers.character;

import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class PlayerDataControl extends NPCDataControl {

	/**
	 * Contained player data.
	 */
	private Player player;

	/**
	 * Constructor.
	 * 
	 * @param player
	 *            Contained player data
	 */
	public PlayerDataControl( Player player ) {
		super(player);
		this.player = player;
	}

	/**
	 * Notify to all scenes that the player image has been changed
	 */
	public void playerImageChange(){
		String preview = getPreviewImage();
		if (preview!=null){
			for (SceneDataControl scene : Controller.getInstance().getSelectedChapterDataControl().getScenesList().getScenes()){
				scene.imageChangeNotify(preview);
			}
		}
	}
	
	@Override
	public Object getContent( ) {
		return player;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return false;
	}

	@Override
	public boolean canBeMoved( ) {
		return false;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type , String id) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES && !Controller.getInstance( ).isPlayTransparent( ) ) {
			return super.addElement(type, id);
		}

		return elementAdded;
	}

	public boolean buildResourcesTab( ) {
		return !Controller.getInstance( ).isPlayTransparent( );
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.updateVarFlagSummary(varFlagSummary);
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {

	}

	@Override
	public void deleteIdentifierReferences( String id ) {

	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;

	}

	@Override
	public void recursiveSearch() {
		super.recursiveSearch();
	}
}