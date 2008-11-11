package es.eucm.eadventure.editor.control.controllers.cutscene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.NextScene;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;
import es.eucm.eadventure.common.data.chapterdata.scenes.Cutscene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class CutsceneDataControl extends DataControlWithResources {

	/**
	 * Contained cutscene data.
	 */
	private Cutscene cutscene;

	/**
	 * Holds the type of the cutscene.
	 */
	private int cutsceneType;

	/**
	 * List of resources.
	 */
	private List<Resources> resourcesList;

	/**
	 * Contained set of next scenes in the cutscene.
	 */
	private List<NextScene> nextScenesList;

	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * List of next scene controllers.
	 */
	private List<NextSceneDataControl> nextScenesDataControlList;

	/**
	 * End scene controller.
	 */
	private EndSceneDataControl endSceneDataControl;

	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;

	/**
	 * Constructor.
	 * 
	 * @param cutscene
	 *            Contained cutscene data
	 */
	public CutsceneDataControl( Cutscene cutscene ) {
		this.cutscene = cutscene;
		this.resourcesList = cutscene.getResources( );
		this.nextScenesList = cutscene.getNextScenes( );

		switch( cutscene.getType( ) ) {
			case Cutscene.SLIDESCENE:
				cutsceneType = Controller.CUTSCENE_SLIDES;
				break;
			case Cutscene.VIDEOSCENE:
				cutsceneType = Controller.CUTSCENE_VIDEO;
				break;
		}

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, cutsceneType ) );

		nextScenesDataControlList = new ArrayList<NextSceneDataControl>( );
		for( NextScene nextScene : nextScenesList )
			nextScenesDataControlList.add( new NextSceneDataControl( nextScene ) );

		endSceneDataControl = cutscene.isEndScene( ) ? new EndSceneDataControl( ) : null;
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
	 * Returns the next scene list controller.
	 * 
	 * @return Next scene list controller
	 */
	public List<NextSceneDataControl> getNextScenes( ) {
		return nextScenesDataControlList;
	}

	/**
	 * Returns the last next scene controller of the list.
	 * 
	 * @return Last next scene controller
	 */
	public NextSceneDataControl getLastNextScene( ) {
		return nextScenesDataControlList.get( nextScenesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the controller of the "end-scene" tag (if present).
	 * 
	 * @return End scene controller
	 */
	public EndSceneDataControl getEndScene( ) {
		return endSceneDataControl;
	}

	/**
	 * Returns the type of the contained cutscene.
	 * 
	 * @return Type of the contained cutscene
	 */
	public int getType( ) {
		return cutsceneType;
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
	 * Returns the id of the contained cutscene.
	 * 
	 * @return If of the contained cutscene
	 */
	public String getId( ) {
		return cutscene.getId( );
	}

	/**
	 * Returns the documentation of the scene.
	 * 
	 * @return Cutscene's documentation
	 */
	public String getDocumentation( ) {
		return cutscene.getDocumentation( );
	}

	/**
	 * Returns the name of the cutscene.
	 * 
	 * @return Cutscene's name
	 */
	public String getName( ) {
		return cutscene.getName( );
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
	 * Sets the new documentation of the cutscene.
	 * 
	 * @param documentation
	 *            Documentation of the cutscene
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( cutscene.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			cutscene.setDocumentation( documentation );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new name of the cutscene.
	 * 
	 * @param name
	 *            Name of the cutscene
	 */
	public void setName( String name ) {
		// If the value is different
		if( !name.equals( cutscene.getName( ) ) ) {
			// Set the new name and modify the data
			cutscene.setName( name );
			controller.dataModified( );
		}
	}

	/**
	 * Returns whether the cutscene ends the game or not.
	 * 
	 * @return True if it has an "end-scene" tag, false otherwise
	 */
	public boolean isEndScene( ) {
		return cutscene.isEndScene( );
	}

	@Override
	public Object getContent( ) {
		return cutscene;
	}

	@Override
	public int[] getAddableElements( ) {
		//return new int[] { Controller.RESOURCES, Controller.NEXT_SCENE, Controller.END_SCENE };
		return new int[] { Controller.NEXT_SCENE, Controller.END_SCENE };
	}

	@Override
	public boolean canAddElement( int type ) {
		boolean canAddElement = false;

		//if( type == Controller.RESOURCES )
		//	canAddElement = true;

		if( type == Controller.NEXT_SCENE && !cutscene.isEndScene( ) )
			canAddElement = true;

		if( type == Controller.END_SCENE && nextScenesList.size( ) == 0 && !cutscene.isEndScene( ) )
			canAddElement = true;

		return canAddElement;
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

		// If the element is a resources block
		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, cutsceneType ) );
			controller.dataModified( );
			elementAdded = true;
		}

		// If the element is a next scene
		else if( type == Controller.NEXT_SCENE ) {
			// Take the list of the scenes
			String[] generalScenes = controller.getIdentifierSummary( ).getGeneralSceneIds( );

			// If the list has elements, show the dialog with the options
			if( generalScenes.length > 0 ) {
				String selectedScene = controller.showInputDialog( TextConstants.getText( "Operation.AddNextSceneTitle" ), TextConstants.getText( "Operation.AddNextSceneMessage" ), generalScenes );

				// If some value was selected
				if( selectedScene != null ) {
					NextScene newNextScene = new NextScene( selectedScene );
					nextScenesList.add( newNextScene );
					nextScenesDataControlList.add( new NextSceneDataControl( newNextScene ) );
					controller.dataModified( );
					elementAdded = true;
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddNextSceneTitle" ), TextConstants.getText( "Operation.AddNextSceneErrorNoScenes" ) );
		}

		// If the element is an end scene
		else if( type == Controller.END_SCENE ) {
			cutscene.setEndScene( true );
			endSceneDataControl = new EndSceneDataControl( );
			controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		// If the element is a resources block
		if( resourcesList.contains( dataControl.getContent( ) ) ) {
			// Delete the block only if it is not the last one
			if( resourcesList.size( ) > 1 ) {
				if( resourcesList.remove( dataControl.getContent( ) ) ) {
					int resourcesIndex = resourcesDataControlList.indexOf( dataControl );
					resourcesDataControlList.remove( dataControl );

					// Decrease the selected index if necessary
					if( selectedResources > 0 && selectedResources >= resourcesIndex )
						selectedResources--;

					controller.dataModified( );
					elementDeleted = true;
				}
			}

			// If it was the last one, show an error message
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.DeleteResourcesTitle" ), TextConstants.getText( "Operation.DeleteResourcesErrorLastResources" ) );
		}

		// If the element is a next scene
		else if( nextScenesList.contains( dataControl.getContent( ) ) ) {
			nextScenesList.remove( dataControl.getContent( ) );
			nextScenesDataControlList.remove( dataControl );
			elementDeleted = true;
		}

		// If no resources blocks or next scenes were deleted, try with the end scene element
		else if( endSceneDataControl == dataControl ) {
			cutscene.setEndScene( false );
			endSceneDataControl = null;
			elementDeleted = true;
		}

		if( elementDeleted )
			controller.dataModified( );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;

		// If the element to move is a resources block
		if( resourcesList.contains( dataControl.getContent( ) ) ) {
			int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

			if( elementIndex > 0 ) {
				resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
				resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
				controller.dataModified( );
				elementMoved = true;
			}
		}

		// If the element to move is a next scene
		else if( nextScenesList.contains( dataControl.getContent( ) ) ) {
			int elementIndex = nextScenesList.indexOf( dataControl.getContent( ) );

			if( elementIndex > 0 ) {
				nextScenesList.add( elementIndex - 1, nextScenesList.remove( elementIndex ) );
				nextScenesDataControlList.add( elementIndex - 1, nextScenesDataControlList.remove( elementIndex ) );
				controller.dataModified( );
				elementMoved = true;
			}
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;

		// If the element to move is a resources block
		if( resourcesList.contains( dataControl.getContent( ) ) ) {
			int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

			if( elementIndex < resourcesList.size( ) - 1 ) {
				resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
				resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
				controller.dataModified( );
				elementMoved = true;
			}
		}

		// If the element to move is a next scene
		else if( nextScenesList.contains( dataControl.getContent( ) ) ) {
			int elementIndex = nextScenesList.indexOf( dataControl.getContent( ) );

			if( elementIndex < nextScenesList.size( ) - 1 ) {
				nextScenesList.add( elementIndex + 1, nextScenesList.remove( elementIndex ) );
				nextScenesDataControlList.add( elementIndex + 1, nextScenesDataControlList.remove( elementIndex ) );
				controller.dataModified( );
				elementMoved = true;
			}
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		boolean elementRenamed = false;
		String oldCutsceneId = cutscene.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldCutsceneId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameCutsceneTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldCutsceneId, references } ) ) ) {

			// Show a dialog asking for the new cutscnee id
			String newCutsceneId = controller.showInputDialog( TextConstants.getText( "Operation.RenameCutsceneTitle" ), TextConstants.getText( "Operation.RenameCutsceneMessage" ), oldCutsceneId );

			// If some value was typed and the identifiers are different
			if( newCutsceneId != null && !newCutsceneId.equals( oldCutsceneId ) && controller.isElementIdValid( newCutsceneId ) ) {
				cutscene.setId( newCutsceneId );
				controller.replaceIdentifierReferences( oldCutsceneId, newCutsceneId );
				controller.getIdentifierSummary( ).deleteCutsceneId( oldCutsceneId );
				controller.getIdentifierSummary( ).addCutsceneId( newCutsceneId );
				controller.dataModified( );
				elementRenamed = true;
			}
		}

		return elementRenamed;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			nextSceneDataControl.updateFlagSummary( flagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}

		// Iterate through the next scenes
		for( int i = 0; i < nextScenesDataControlList.size( ); i++ ) {
			String nextScenePath = currentPath + " >> " + TextConstants.getElementName( Controller.NEXT_SCENE ) + " #" + ( i + 1 ) + " (" + nextScenesDataControlList.get( i ).getNextSceneId( ) + ")";
			valid &= nextScenesDataControlList.get( i ).isValid( nextScenePath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			count += nextSceneDataControl.countAssetReferences( assetPath );

		return count;
	}

	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			nextSceneDataControl.getAssetReferences( assetPaths, assetTypes );
	}
	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );

		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			nextSceneDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			count += nextSceneDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			nextSceneDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		int i = 0;

		// Check every next scene structure
		while( i < nextScenesList.size( ) ) {
			if( nextScenesList.get( i ).getNextSceneId( ).equals( id ) ) {
				nextScenesList.remove( i );
				nextScenesDataControlList.remove( i );
			}

			else
				i++;
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
}
