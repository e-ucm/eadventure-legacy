package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ScenesListDataControl extends DataControl {

	/**
	 * List of scenes.
	 */
	private List<Scene> scenesList;

	/**
	 * List of scene controllers.
	 */
	private List<SceneDataControl> scenesDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param scenesList
	 *            List of scenes
	 */
	public ScenesListDataControl( List<Scene> scenesList, String playerImagePath) {
		this.scenesList = scenesList;

		// Create subcontrollers
		scenesDataControlList = new ArrayList<SceneDataControl>( );
		for( Scene scene : scenesList )
			scenesDataControlList.add( new SceneDataControl( scene, playerImagePath) );
	}

	/**
	 * Returns the list of scene controllers.
	 * 
	 * @return List of scene controllers.
	 */
	public List<SceneDataControl> getScenes( ) {
		return scenesDataControlList;
	}

	/**
	 * Returns the last scene controller of the list.
	 * 
	 * @return Last scene controller
	 */
	public SceneDataControl getLastScene( ) {
		return scenesDataControlList.get( scenesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the scenes contained in the list.
	 * 
	 * @return Array with the information of the scenes. It contains the identifier of each scene, and the number of
	 *         exits, items and characters
	 */
	public String[][] getScenesInfo( ) {
		String[][] scenesInfo = null;

		// Create the list for the scenes
		scenesInfo = new String[scenesList.size( )][4];

		// Fill the array with the info
		for( int i = 0; i < scenesList.size( ); i++ ) {
			Scene scene = scenesList.get( i );
			scenesInfo[i][0] = scene.getId( );
			scenesInfo[i][1] = TextConstants.getText( "ScenesList.ExitsNumber", String.valueOf( scene.getExits( ).size( ) ) );
			scenesInfo[i][2] = TextConstants.getText( "ScenesList.ItemsNumber", String.valueOf( scene.getItemReferences( ).size( ) ) );
			scenesInfo[i][3] = TextConstants.getText( "ScenesList.NPCsNumber", String.valueOf( scene.getCharacterReferences( ).size( ) ) );
		}

		return scenesInfo;
	}

	@Override
	public Object getContent( ) {
		return scenesList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.SCENE };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new scenes
		return type == Controller.SCENE;
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
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.SCENE ) {

			// Show a dialog asking for the scene id
			String sceneId = controller.showInputDialog( TextConstants.getText( "Operation.AddSceneTitle" ), TextConstants.getText( "Operation.AddSceneMessage" ), TextConstants.getText( "Operation.AddSceneDefaultValue" ) );

			// If some value was typed and the identifier is valid
			if( sceneId != null && controller.isElementIdValid( sceneId ) ) {
				// Add thew new scene
				Scene newScene = new Scene( sceneId );
				scenesList.add( newScene );
				scenesDataControlList.add( new SceneDataControl( newScene, controller.getPlayerImagePath()) );
				controller.getIdentifierSummary( ).addSceneId( sceneId );
				//controller.dataModified( );
				elementAdded = true;
			}
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		boolean elementDeleted = false;

		// Take the number of general scenes in the chapter
		int generalScenesCount = controller.getIdentifierSummary( ).getGeneralSceneIds( ).length;

		// If there are at least two scenes, this one can be deleted
		if( generalScenesCount > 1 ) {
			String sceneId = ( (SceneDataControl) dataControl ).getId( );
			String references = String.valueOf( controller.countIdentifierReferences( sceneId ) );

			// Ask for confirmation
			if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { sceneId, references } ) ) ) {
				if( scenesList.remove( dataControl.getContent( ) ) ) {
					scenesDataControlList.remove( dataControl );
					controller.deleteIdentifierReferences( sceneId );
					controller.getIdentifierSummary( ).deleteSceneId( sceneId );
					//controller.dataModified( );
					elementDeleted = true;
				}
			}
		}

		// If this is the last scene, it can't be deleted
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.ErrorLastScene" ) );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = scenesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			scenesList.add( elementIndex - 1, scenesList.remove( elementIndex ) );
			scenesDataControlList.add( elementIndex - 1, scenesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = scenesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < scenesList.size( ) - 1 ) {
			scenesList.add( elementIndex + 1, scenesList.remove( elementIndex ) );
			scenesDataControlList.add( elementIndex + 1, scenesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Iterate through each scene
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			sceneDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Update the current path
		currentPath += " >> " + TextConstants.getElementName( Controller.SCENES_LIST );

		// Iterate through the scenes
		for( SceneDataControl sceneDataControl : scenesDataControlList ) {
			String scenePath = currentPath + " >> " + sceneDataControl.getId( );
			valid &= sceneDataControl.isValid( scenePath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through each scene
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			count += sceneDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Iterate through each scnee
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			sceneDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through each scene
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			sceneDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each scene
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			count += sceneDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each scene
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			sceneDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// Spread the call to every scene
		for( SceneDataControl sceneDataControl : scenesDataControlList )
			sceneDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
	
	/**
	 * Adds a player reference in all scenes
	 */
	public void addPlayerToAllScenes(){
		for ( SceneDataControl sceneDataControl : scenesDataControlList)
			sceneDataControl.addPlayerInReferenceList();
	}
	
	/**
	 * Delete player reference in all scenes
	 */
	public void deletePlayerToAllScenes(){
		for ( SceneDataControl sceneDataControl : scenesDataControlList)
			sceneDataControl.deletePlayerInReferenceList();
	}
	

}
