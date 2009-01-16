package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ElementReferenceDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference (used to extract the id of the scene).
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * Contained element reference.
	 */
	private ElementReference elementReference;

	private InfluenceAreaDataControl influenceAreaDataControl;
	
	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;
	
	/**
	 * The type of the element reference (item, npc or atrezzo)
	 */
	private int type;

	/**
	 * Contructor.
	 * 
	 * @param sceneDataControl
	 *            Parent scene controller
	 * @param elementReference
	 *            Element reference of the data control structure
	 */
	public ElementReferenceDataControl( SceneDataControl sceneDataControl, ElementReference elementReference, int type) {
		this.sceneDataControl = sceneDataControl;
		this.elementReference = elementReference;
		this.type = type;
		if (type == Controller.ITEM_REFERENCE || type == Controller.NPC_REFERENCE)
			this.influenceAreaDataControl = new InfluenceAreaDataControl(sceneDataControl, elementReference.getInfluenceArea(), this);
		// Create subcontrollers
		conditionsController = new ConditionsController( elementReference.getConditions( ) );
	}

	/**
	 * Returns the conditions of the element reference.
	 * 
	 * @return Conditions of the element reference
	 */
	public ConditionsController getConditions( ) {
		return conditionsController;
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
	 * Returns the data controllers of the item references of the scene that contains this element reference.
	 * 
	 * @return List of item references (including the one being edited)
	 */
	public List<ElementReferenceDataControl> getParentSceneItemReferences( ) {
		return sceneDataControl.getReferencesList( ).getItemReferences( );
	}

	/**
	 * Returns the data controllers of the character references of the scene that contains this element reference.
	 * 
	 * @return List of character references (including the one being edited)
	 */
	public List<ElementReferenceDataControl> getParentSceneNPCReferences( ) {
		return sceneDataControl.getReferencesList( ).getNPCReferences( );
	}
	
	/**
	 * Returns the data controllers of the atrezzo items references of the scene that contains this element reference.
	 * 
	 * @return List of atrezzo references (including the one being edited)
	 */
	public List<ElementReferenceDataControl> getParentSceneAtrezzoReferences( ) {
		return sceneDataControl.getReferencesList( ).getAtrezzoReferences( );
	}
	
	public List<ExitDataControl> getParentSceneExitList() {
		return sceneDataControl.getExitsList().getExits();
	}
	
	public List<ActiveAreaDataControl> getParentSceneActiveAreaList() {
		return sceneDataControl.getActiveAreasList().getActiveAreas();
	}
	
	public List<BarrierDataControl> getParentSceneBarrierList() {
		return sceneDataControl.getBarriersList().getBarriers();
	}
 
	/**
	 * Returns the id of the referenced element.
	 * 
	 * @return Id of the referenced element
	 */
	public String getElementId( ) {
		return elementReference.getIdTarget( );
	}

	/**
	 * Returns the x coordinate of the referenced element
	 * 
	 * @return X coordinate of the referenced element
	 */
	public int getElementX( ) {
		return elementReference.getX( );
	}

	/**
	 * Returns the y coordinate of the referenced element
	 * 
	 * @return Y coordinate of the referenced element
	 */
	public int getElementY( ) {
		return elementReference.getY( );
	}

	/**
	 * Returns the documentation of the element reference.
	 * 
	 * @return Element reference's documentation
	 */
	public String getDocumentation( ) {
		return elementReference.getDocumentation( );
	}

	/**
	 * Sets a new next scene id.
	 * 
	 * @param elementId
	 *            New next scene id
	 */
	public void setElementId( String elementId ) {
		// If the value is different
		if( !elementId.equals( elementReference.getIdTarget( ) ) ) {
			// Set the new element id, update the tree and modify the data
			elementReference.setIdTarget( elementId );
			controller.updateTree( );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new position for the element reference.
	 * 
	 * @param x
	 *            X coordinate for the element reference
	 * @param y
	 *            Y coordinate for the element reference
	 */
	public void setElementPosition( int x, int y ) {
		elementReference.setPosition( x, y );
		controller.dataModified( );
	}

	/**
	 * Sets the new documentation of the element reference.
	 * 
	 * @param documentation
	 *            Documentation of the element reference
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( elementReference.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			elementReference.setDocumentation( documentation );
			controller.dataModified( );
		}
	}
	
	/**
	 * Get the scale for the element reference
	 * 
	 * @return the scale for the element reference
	 */
	public float getElementScale( ) {
		return elementReference.getScale();
	}
	
	/**
	 * Set the scale for the element reference
	 * 
	 * @param scale the scale for the element reference
	 */
	public void setElementScale(float scale) {
		elementReference.setScale(scale);
	}

	@Override
	public Object getContent( ) {
		return elementReference;
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
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Update the flag summary with the conditions
		ConditionsController.updateVarFlagSummary( varFlagSummary, elementReference.getConditions( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		return 0;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
	// Do nothing
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return elementReference.getIdTarget( ).equals( id ) ? 1 : 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		if( elementReference.getIdTarget( ).equals( oldId ) )
			elementReference.setIdTarget( newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
	// Do nothing
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Do nothing
	}

	@Override
	public boolean canBeDuplicated( ) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Return the element reference
	 * 
	 * @return
	 * 		the element reference
	 */
	public ElementReference getElementReference() {
		return elementReference;
	}

	/**
	 * 
	 * @return
	 * 		The type of the current element reference
	 */
	public int getType() {
		return type;
	}

	public SceneDataControl getSceneDataControl() {
		return sceneDataControl;
	}

	public InfluenceAreaDataControl getInfluenceArea() {
		return influenceAreaDataControl;
	}

}
