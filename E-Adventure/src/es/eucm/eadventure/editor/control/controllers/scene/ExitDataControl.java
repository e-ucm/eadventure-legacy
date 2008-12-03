package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ExitLookDataControl;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ExitDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference (used to extract the id of the scene).
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * Contained exit.
	 */
	private Exit exit;

	/**
	 * Contained set of next scenes in the exit.
	 */
	private List<NextScene> nextScenesList;

	/**
	 * List of next scene controllers.
	 */
	private List<NextSceneDataControl> nextScenesDataControlList;
	
	private ExitLookDataControl exitLookDataControl;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Parent scene controller
	 * @param exit
	 *            Exit of the data control structure
	 */
	public ExitDataControl( SceneDataControl sceneDataControl, Exit exit ) {
		this.sceneDataControl = sceneDataControl;
		this.exit = exit;
		this.nextScenesList = exit.getNextScenes( );

		// Create subcontrollers
		nextScenesDataControlList = new ArrayList<NextSceneDataControl>( );
		for( NextScene nextScene : nextScenesList )
			nextScenesDataControlList.add( new NextSceneDataControl( nextScene ) );
		
		exitLookDataControl = new ExitLookDataControl ( exit );
	}

	/**
	 * Returns the list of next scene controllers.
	 * 
	 * @return Next scene controllers
	 */
	public List<NextSceneDataControl> getNextScenes( ) {
		return nextScenesDataControlList;
	}

	/**
	 * Returns the last next scene controller in the list.
	 * 
	 * @return Last next scene controller
	 */
	public NextSceneDataControl getLastNextScene( ) {
		return nextScenesDataControlList.get( nextScenesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the id of the scene that contains this element reference.
	 * 
	 * @return Parent scene id
	 */
	public String getParentSceneId( ) {
		return sceneDataControl.getId( );
	}

	/**
	 * Returns the X coordinate of the upper left position of the exit.
	 * 
	 * @return X coordinate of the upper left point
	 */
	public int getX( ) {
		return exit.getX( );
	}

	/**
	 * Returns the Y coordinate of the upper left position of the exit.
	 * 
	 * @return Y coordinate of the upper left point
	 */
	public int getY( ) {
		return exit.getY( );
	}

	/**
	 * Returns the width of the exit.
	 * 
	 * @return Width of the exit
	 */
	public int getWidth( ) {
		return exit.getWidth( );
	}

	/**
	 * Returns the height of the exit.
	 * 
	 * @return Height of the exit
	 */
	public int getHeight( ) {
		return exit.getHeight( );
	}

	/**
	 * Returns the documentation of the exit.
	 * 
	 * @return Exit's documentation
	 */
	public String getDocumentation( ) {
		return exit.getDocumentation( );
	}

	/**
	 * Sets the new values for the exit.
	 * 
	 * @param x
	 *            X coordinate of the upper left point
	 * @param y
	 *            Y coordinate of the upper left point
	 * @param width
	 *            Width of the exit area
	 * @param height
	 *            Height of the exit area
	 */
	public void setExit( int x, int y, int width, int height ) {
		exit.setValues( x, y, width, height );
		controller.dataModified( );
	}

	/**
	 * Sets the new documentation of the exit.
	 * 
	 * @param documentation
	 *            Documentation of the exit
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( exit.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			exit.setDocumentation( documentation );
			controller.dataModified( );
		}
	}

	@Override
	public Object getContent( ) {
		return exit;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.NEXT_SCENE };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new exit
		return type == Controller.NEXT_SCENE;
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
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.NEXT_SCENE ) {
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

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		// Delete the next scene only if it is not the last one
		if( nextScenesList.size( ) > 1 ) {
			if( nextScenesList.remove( dataControl.getContent( ) ) ) {
				nextScenesDataControlList.remove( dataControl );
				controller.dataModified( );
				elementDeleted = true;
			}
		}

		// If it was the last one, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteNextSceneTitle" ), TextConstants.getText( "Operation.DeleteNextSceneErrorLastNextScene" ) );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = nextScenesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			nextScenesList.add( elementIndex - 1, nextScenesList.remove( elementIndex ) );
			nextScenesDataControlList.add( elementIndex - 1, nextScenesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = nextScenesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < nextScenesList.size( ) - 1 ) {
			nextScenesList.add( elementIndex + 1, nextScenesList.remove( elementIndex ) );
			nextScenesDataControlList.add( elementIndex + 1, nextScenesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			nextSceneDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

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

		// Iterate through each next scene
		for( NextSceneDataControl nextSceneDataControl : nextScenesDataControlList )
			count += nextSceneDataControl.countAssetReferences( assetPath );

		return count;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
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
			// Update nextscene
			nextScenesDataControlList.get( i ).deleteIdentifierReferences( id );
			
			if( nextScenesList.get( i ).getNextSceneId( ).equals( id ) ) {
				nextScenesList.remove( i );
				nextScenesDataControlList.remove( i );
				
			}

			else{
				i++;
			}
		}
	}

	/**
	 * @return the exitLookDataControl
	 */
	public ExitLookDataControl getExitLookDataControl( ) {
		return exitLookDataControl;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		exitLookDataControl.getAssetReferences( assetPaths, assetTypes );
		for (NextSceneDataControl nextScene: nextScenesDataControlList){
			nextScene.getAssetReferences( assetPaths, assetTypes );
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
}
