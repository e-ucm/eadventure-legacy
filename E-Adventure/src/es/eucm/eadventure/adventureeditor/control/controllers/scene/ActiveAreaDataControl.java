package es.eucm.eadventure.adventureeditor.control.controllers.scene;

import java.util.List;

import es.eucm.eadventure.adventureeditor.control.controllers.ConditionsController;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.adventureeditor.data.chapterdata.elements.ActiveArea;
import es.eucm.eadventure.adventureeditor.data.supportdata.FlagSummary;

public class ActiveAreaDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference (used to extract the id of the scene).
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * Contained activeArea.
	 */
	private ActiveArea activeArea;
	
	/**
	 * Actions list controller.
	 */
	private ActionsListDataControl actionsListDataControl;
	
	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;


	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Parent scene controller
	 * @param activeArea
	 *            Exit of the data control structure
	 */
	public ActiveAreaDataControl( SceneDataControl sceneDataControl, ActiveArea activeArea ) {
		this.sceneDataControl = sceneDataControl;
		this.activeArea = activeArea;

		// Create subcontrollers
		actionsListDataControl = new ActionsListDataControl( activeArea.getActions( ) );
		conditionsController = new ConditionsController( activeArea.getConditions( ) );

	}

	/**
	 * Returns the actions list controller.
	 * 
	 * @return Actions list controller
	 */
	public ActionsListDataControl getActionsList( ) {
		return actionsListDataControl;
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
	 * Returns the id of the item.
	 * 
	 * @return Item's id
	 */
	public String getId( ) {
		return activeArea.getId( );
	}
	
	/**
	 * Returns the documentation of the item.
	 * 
	 * @return Item's documentation
	 */
	public String getDocumentation( ) {
		return activeArea.getDocumentation( );
	}

	/**
	 * Returns the name of the item.
	 * 
	 * @return Item's name
	 */
	public String getName( ) {
		return activeArea.getName( );
	}

	/**
	 * Returns the brief description of the item.
	 * 
	 * @return Item's description
	 */
	public String getBriefDescription( ) {
		return activeArea.getDescription( );
	}

	/**
	 * Returns the detailed description of the item.
	 * 
	 * @return Item's detailed description
	 */
	public String getDetailedDescription( ) {
		return activeArea.getDetailedDescription( );
	}
	
	
	/**
	 * Sets the new documentation of the item.
	 * 
	 * @param documentation
	 *            Documentation of the item
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( activeArea.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			activeArea.setDocumentation( documentation );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new name of the activeArea.
	 * 
	 * @param name
	 *            Name of the activeArea
	 */
	public void setName( String name ) {
		// If the value is different
		if( !name.equals( activeArea.getName( ) ) ) {
			// Set the new name and modify the data
			activeArea.setName( name );
			controller.dataModified( );
			controller.reloadData();
		}
	}

	/**
	 * Sets the new brief description of the activeArea.
	 * 
	 * @param description
	 *            Description of the activeArea
	 */
	public void setBriefDescription( String description ) {
		// If the value is different
		if( !description.equals( activeArea.getDescription( ) ) ) {
			// Set the new description and modify the data
			activeArea.setDescription( description );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new detailed description of the activeArea.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the activeArea
	 */
	public void setDetailedDescription( String detailedDescription ) {
		// If the value is different
		if( !detailedDescription.equals( activeArea.getDetailedDescription( ) ) ) {
			// Set the new detailed description and modify the data
			activeArea.setDetailedDescription( detailedDescription );
			controller.dataModified( );
		}
	}
	
	/**
	 * Returns the X coordinate of the upper left position of the exit.
	 * 
	 * @return X coordinate of the upper left point
	 */
	public int getX( ) {
		return activeArea.getX( );
	}

	/**
	 * Returns the Y coordinate of the upper left position of the exit.
	 * 
	 * @return Y coordinate of the upper left point
	 */
	public int getY( ) {
		return activeArea.getY( );
	}

	/**
	 * Returns the width of the exit.
	 * 
	 * @return Width of the exit
	 */
	public int getWidth( ) {
		return activeArea.getWidth( );
	}

	/**
	 * Returns the height of the exit.
	 * 
	 * @return Height of the exit
	 */
	public int getHeight( ) {
		return activeArea.getHeight( );
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
	public void setActiveArea( int x, int y, int width, int height ) {
		activeArea.setValues( x, y, width, height );
		controller.dataModified( );
	}

	@Override
	public Object getContent( ) {
		return activeArea;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { };
	}

	@Override
	public boolean canAddElement( int type ) {
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
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		boolean elementAdded = false;
		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;
		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		actionsListDataControl.updateFlagSummary( flagSummary );
		ConditionsController.updateFlagSummary( flagSummary, activeArea.getConditions( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		valid &= actionsListDataControl.isValid( currentPath, incidences );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Add the references in the actions
		count += actionsListDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		actionsListDataControl.getAssetReferences( assetPaths, assetTypes );
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Delete the references from the actions
		actionsListDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return actionsListDataControl.countIdentifierReferences( id );
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		actionsListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		actionsListDataControl.deleteIdentifierReferences( id );
	}
	
	/**
	 * Returns the conditions of the element reference.
	 * 
	 * @return Conditions of the element reference
	 */
	public ConditionsController getConditions( ) {
		return conditionsController;
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
