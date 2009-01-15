package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class SceneDataControl extends DataControlWithResources {

	/**
	 * Contained scene data.
	 */
	private Scene scene;

	/**
	 * List of resources.
	 */
	private List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * Exits list controller.
	 */
	private ExitsListDataControl exitsListDataControl;

	/**
	 * Element references list controller.
	 */
	private ReferencesListDataControl referencesListDataControl;
		
	/**
	 * Active areas list controller
	 */
	private ActiveAreasListDataControl activeAreasListDataControl;
	
	/**
	 * Barriers list controller
	 */
	private BarriersListDataControl barriersListDataControl;

	private TrajectoryDataControl trajectoryDataControl;
	
	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;

	/**
	 * Constructor.
	 * 
	 * @param scene
	 *            Contained scene data
	 */
	public SceneDataControl( Scene scene , String playerImagePath) {
		this.scene = scene;
		this.resourcesList = scene.getResources( );

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.SCENE ) );

		exitsListDataControl = new ExitsListDataControl( this, scene.getExits( ) );
		referencesListDataControl = new ReferencesListDataControl(playerImagePath, this, scene.getItemReferences( ), scene.getAtrezzoReferences(), scene.getCharacterReferences() );
		activeAreasListDataControl = new ActiveAreasListDataControl( this, scene.getActiveAreas( ));
		barriersListDataControl = new BarriersListDataControl( this, scene.getBarriers( ));
		trajectoryDataControl = new TrajectoryDataControl( this, scene.getTrajectory());
	}

	/**
	 * Returns the list of resources controllers.
	 * 
	 * @return Resources controllers
	 */
	public List<ResourcesDataControl> getResources( ) {
		return resourcesDataControlList;
	}

	/**
	 * Returns the number of resources blocks contained.
	 * 
	 * @return Number of resources blocks
	 */
	public int getResourcesCount( ) {
		return resourcesDataControlList.size( );
	}

	/**
	 * Returns the last resources controller of the list.
	 * 
	 * @return Last resources controller
	 */
	public ResourcesDataControl getLastResources( ) {
		return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the exits list controller.
	 * 
	 * @return Exits list controller
	 */
	public ExitsListDataControl getExitsList( ) {
		return exitsListDataControl;
	}
	
	/**
	 * Returns the active areas list controller.
	 * 
	 * @return Exits list controller
	 */
	public ActiveAreasListDataControl getActiveAreasList( ) {
		return this.activeAreasListDataControl;
	}

	/**
	 * Returns the barriers list controller.
	 * 
	 * @return Barriers list controller
	 */
	public BarriersListDataControl getBarriersList( ) {
		return this.barriersListDataControl;
	}

	/**
	 * Returns the item references list controller.
	 * 
	 * @return Item references list controller
	 */
	public ReferencesListDataControl getReferencesList( ) {
		return referencesListDataControl;
	}


	/**
	 * Returns the selected resources block of the list.
	 * 
	 * @return Selected block of resources
	 */
	public int getSelectedResources( ) {
		return selectedResources;
	}

	/**
	 * Returns the path to the selected preview image.
	 * 
	 * @return Path to the image, null if not present
	 */
	public String getPreviewBackground( ) {
		return resourcesDataControlList.get( selectedResources ).getAssetPath( "background" );
	}

	/**
	 * Returns the id of the scene.
	 * 
	 * @return Scene's id
	 */
	public String getId( ) {
		return scene.getId( );
	}

	/**
	 * Returns the documentation of the scene.
	 * 
	 * @return Scene's documentation
	 */
	public String getDocumentation( ) {
		return scene.getDocumentation( );
	}

	/**
	 * Returns the name of the scene.
	 * 
	 * @return Scene's name
	 */
	public String getName( ) {
		return scene.getName( );
	}

	/**
	 * Returns whether the scene has a default initial position or not.
	 * 
	 * @return True if the scene has an initial position, false otherwise
	 */
	public boolean hasDefaultInitialPosition( ) {
		return scene.hasDefaultPosition( );
	}

	/**
	 * Returns the X coordinate of the default initial position
	 * 
	 * @return X coordinate of the initial position
	 */
	public int getDefaultInitialPositionX( ) {
		return scene.getDefaultX( );
	}

	/**
	 * Returns the Y coordinate of the default initial position
	 * 
	 * @return Y coordinate of the initial position
	 */
	public int getDefaultInitialPositionY( ) {
		return scene.getDefaultY( );
	}

	/**
	 * Sets the new selected resources block of the list.
	 * 
	 * @param selectedResources
	 *            New selected block of resources
	 */
	public void setSelectedResources( int selectedResources ) {
		this.selectedResources = selectedResources;
	}

	/**
	 * Sets the new name of the scene.
	 * 
	 * @param name
	 *            Name of the scene
	 */
	public void setName( String name ) {
		// If the value is different
		if( !name.equals( scene.getName( ) ) ) {
			// Set the new name and modify the data
			scene.setName( name );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new documentation of the scene.
	 * 
	 * @param documentation
	 *            Documentation of the scene
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( scene.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			scene.setDocumentation( documentation );
			controller.dataModified( );
		}
	}

	/**
	 * Toggles the default initial position. If the scene has an initial position deletes it, if it doesn't have one,
	 * set initial values for it.
	 */
	public void toggleDefaultInitialPosition( ) {
		if( scene.hasDefaultPosition( ) )
			scene.setDefaultPosition( Integer.MIN_VALUE, Integer.MIN_VALUE );
		else
			scene.setDefaultPosition( 0, 0 );

		// The data has been modified
		controller.dataModified( );
	}

	/**
	 * Sets the new default initial position of the scene.
	 * 
	 * @param positionX
	 *            X coordinate of the initial position
	 * @param positionY
	 *            Y coordinate of the initial position
	 */
	public void setDefaultInitialPosition( int positionX, int positionY ) {
		// If the values are different
		if( positionX != scene.getDefaultX( ) || positionY != scene.getDefaultY( ) ) {
			// Set the new default initial position and modify the data
			scene.setDefaultPosition( positionX, positionY );
			controller.dataModified( );
		}
	}

	@Override
	public Object getContent( ) {
		return scene;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { /*Controller.RESOURCES*/ };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new resources
		//return type == Controller.RESOURCES;
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return true;
	}

	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.SCENE ) );
			controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		// Delete the block only if it is not the last one
		if( resourcesList.size( ) > 1 ) {
			//Show confirmation dialog
//			if (controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteResourcesBlock.Confirmation.Title" ), TextConstants.getText( "Operation.DeleteResourcesBlock.Confirmation.Message" ) )){
				
				if( resourcesList.remove( dataControl.getContent( ) ) ) {
					int resourcesIndex = resourcesDataControlList.indexOf( dataControl );
					resourcesDataControlList.remove( dataControl );

					// Decrease the selected index if necessary
					if( selectedResources > 0 && selectedResources >= resourcesIndex )
						selectedResources--;

					controller.dataModified( );
					elementDeleted = true;
				}
	//		}
		}

		// If it was the last one, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteResourcesTitle" ), TextConstants.getText( "Operation.DeleteResourcesErrorLastResources" ) );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < resourcesList.size( ) - 1 ) {
			resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		boolean elementRenamed = false;
		String oldSceneId = scene.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldSceneId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameSceneTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldSceneId, references } ) ) ) {

			// Show a dialog asking for the new scene id
			String newSceneId = controller.showInputDialog( TextConstants.getText( "Operation.RenameSceneTitle" ), TextConstants.getText( "Operation.RenameSceneMessage" ), oldSceneId );

			// If some value was typed and the identifiers are different
			if( newSceneId != null && !newSceneId.equals( oldSceneId ) && controller.isElementIdValid( newSceneId ) ) {
				scene.setId( newSceneId );
				controller.replaceIdentifierReferences( oldSceneId, newSceneId );
				controller.getIdentifierSummary( ).deleteSceneId( oldSceneId );
				controller.getIdentifierSummary( ).addSceneId( newSceneId );
				controller.dataModified( );
				elementRenamed = true;
			}
		}

		return elementRenamed;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Update the flag summary with the exits, item and character references
		exitsListDataControl.updateVarFlagSummary( varFlagSummary );
		referencesListDataControl.updateVarFlagSummary( varFlagSummary );
		this.activeAreasListDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}

		// Spread the call to the exits
		valid &= exitsListDataControl.isValid( currentPath, incidences );
		valid &= trajectoryDataControl.isValid(currentPath, incidences);
		
		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		// Increase the counter with the references in the exits
		count += exitsListDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Do nothing
		exitsListDataControl.getAssetReferences( assetPaths, assetTypes );
		
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );

		// Delete the references in the exits
		exitsListDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Increase the counter for the exits, items and characters
		count += exitsListDataControl.countIdentifierReferences( id );
		count += referencesListDataControl.countIdentifierReferences( id );
		count += activeAreasListDataControl.countIdentifierReferences( id );
		count += barriersListDataControl.countIdentifierReferences( id );
		count += trajectoryDataControl.countIdentifierReferences(id);

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		exitsListDataControl.replaceIdentifierReferences( oldId, newId );
		referencesListDataControl.replaceIdentifierReferences( oldId, newId );
		activeAreasListDataControl.replaceIdentifierReferences( oldId, newId );
		barriersListDataControl.replaceIdentifierReferences( oldId, newId );
		trajectoryDataControl.replaceIdentifierReferences(oldId, newId);
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		exitsListDataControl.deleteIdentifierReferences( id );
		referencesListDataControl.deleteIdentifierReferences( id );
		activeAreasListDataControl.deleteIdentifierReferences( id );
		barriersListDataControl.deleteIdentifierReferences( id );
		trajectoryDataControl.deleteIdentifierReferences(id);
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	public TrajectoryDataControl getTrajectory() {
		return trajectoryDataControl;
	}

	public void setTrajectory(Trajectory trajectory) {
		scene.setTrajectory(trajectory);
		controller.dataModified();
	}
	
	public void setPlayerLayer(int layer){
		scene.setPlayerLayer(layer);
	}
	
	public int getPlayerLayer(){
		return scene.getPlayerLayer();
	}
	
	public void setAllowPlayerLayer(boolean allow){
		scene.setAllowPlayerLayer(allow);
	}
	
	public void deletePlayerInReferenceList(){
		referencesListDataControl.deletePlayer();
	}
	public void addPlayerInReferenceList(){
		referencesListDataControl.addPlayer();
		
	}
	
}
