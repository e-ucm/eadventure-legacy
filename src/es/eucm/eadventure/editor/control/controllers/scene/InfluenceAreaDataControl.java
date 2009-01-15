package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class InfluenceAreaDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference (used to extract the id of the scene).
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * Contained influenceArea.
	 */
	private InfluenceArea influenceArea;
	
	private ElementReferenceDataControl elementReferenceDataControl;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Parent scene controller
	 * @param elementReference 
	 * @param activeArea
	 *            Exit of the data control structure
	 */
	public InfluenceAreaDataControl( SceneDataControl sceneDataControl, InfluenceArea influenceArea, ElementReferenceDataControl elementReference ) {
		this.sceneDataControl = sceneDataControl;
		this.influenceArea = influenceArea;
		this.elementReferenceDataControl = elementReference;
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
	 * Returns the X coordinate of the upper left position of the influenceArea.
	 * 
	 * @return X coordinate of the upper left point
	 */
	public int getX( ) {
		return influenceArea.getX( );
	}

	/**
	 * Returns the Y coordinate of the upper left position of the influenceArea.
	 * 
	 * @return Y coordinate of the upper left point
	 */
	public int getY( ) {
		return influenceArea.getY( );
	}

	/**
	 * Returns the width of the influenceArea.
	 * 
	 * @return Width of the influenceArea
	 */
	public int getWidth( ) {
		return influenceArea.getWidth( );
	}

	/**
	 * Returns the height of the influenceArea.
	 * 
	 * @return Height of the influenceArea
	 */
	public int getHeight( ) {
		return influenceArea.getHeight( );
	}

	/**
	 * Sets the new values for the influenceArea.
	 * 
	 * @param x
	 *            X coordinate of the upper left point
	 * @param y
	 *            Y coordinate of the upper left point
	 * @param width
	 *            Width of the influenceArea area
	 * @param height
	 *            Height of the influenceArea area
	 */
	public void setInfluenceArea( int x, int y, int width, int height ) {
		influenceArea.setValues( x, y, width, height );
		influenceArea.setExists(true);
		controller.dataModified( );
	}

	@Override
	public Object getContent( ) {
		return influenceArea;
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
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
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
		return true;
	}

	public boolean hasInfluenceArea() {
		return influenceArea.isExists();
	}

	public ElementReferenceDataControl getElementReferenceDataControl() {
		return elementReferenceDataControl;
	}

	public void referenceScaleChanged(int incrementX, int incrementY) {
		if (influenceArea.isExists())  {
			influenceArea.setWidth(influenceArea.getWidth() + incrementX);
			influenceArea.setHeight(influenceArea.getHeight() + incrementY);
		}
	}

}
