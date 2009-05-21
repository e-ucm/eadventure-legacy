package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionContextProperty;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionOwner;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeRectangleValueTool;
import es.eucm.eadventure.editor.control.tools.general.areaedition.AddNewPointTool;
import es.eucm.eadventure.editor.control.tools.general.areaedition.ChangeRectangularValueTool;
import es.eucm.eadventure.editor.control.tools.general.areaedition.DeletePointTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ActiveAreaDataControl extends DataControl implements RectangleArea {

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

	private InfluenceAreaDataControl influenceAreaDataControl;

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
		conditionsController = new ConditionsController(new Conditions());
		this.influenceAreaDataControl = new InfluenceAreaDataControl(sceneDataControl, activeArea.getInfluenceArea(), this);

		// Create subcontrollers
		actionsListDataControl = new ActionsListDataControl( activeArea.getActions( ), this );
		
		
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
		controller.addTool(new ChangeDocumentationTool(activeArea, documentation));
	}

	/**
	 * Sets the new name of the activeArea.
	 * 
	 * @param name
	 *            Name of the activeArea
	 */
	public void setName( String name ) {
		controller.addTool(new ChangeNameTool(activeArea, name));
	}

	
	
	/**
	 * Sets the new brief description of the activeArea.
	 * 
	 * @param description
	 *            Description of the activeArea
	 */
	public void setBriefDescription( String description ) {
		controller.addTool(new ChangeDescriptionTool(activeArea, description));
	}

	/**
	 * Sets the new detailed description of the activeArea.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the activeArea
	 */
	public void setDetailedDescription( String detailedDescription ) {
		controller.addTool(new ChangeDetailedDescriptionTool(activeArea, detailedDescription));
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
		controller.addTool(new ChangeRectangleValueTool(activeArea, x, y, width, height));
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
		return true;
	}

	@Override
	public boolean addElement( int type, String id ) {
		boolean elementAdded = false;
		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
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
	public String renameElement( String name ) {
		boolean elementRenamed = false;
		String oldSceneId = activeArea.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldSceneId ) );

		// Ask for confirmation
		if( name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameSceneTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldSceneId, references } ) ) ) {

			// Show a dialog asking for the new scene id
			String newSceneId = name;
			if (name == null)
				newSceneId = controller.showInputDialog( TextConstants.getText( "Operation.RenameSceneTitle" ), TextConstants.getText( "Operation.RenameSceneMessage" ), oldSceneId );

			// If some value was typed and the identifiers are different
			if( newSceneId != null && !newSceneId.equals( oldSceneId ) && controller.isElementIdValid( newSceneId ) ) {
				activeArea.setId( newSceneId );
				controller.replaceIdentifierReferences( oldSceneId, newSceneId );
				controller.getIdentifierSummary( ).deleteActiveAreaId( oldSceneId );
				controller.getIdentifierSummary( ).addActiveAreaId( newSceneId );
				//controller.dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldSceneId;
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		actionsListDataControl.updateVarFlagSummary( varFlagSummary );
		ConditionsController.updateVarFlagSummary( varFlagSummary, activeArea.getConditions( ) );
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
	    int count=0;
	    count += actionsListDataControl.countIdentifierReferences( id );
	    count += conditionsController.countIdentifierReferences(id);
	    return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		actionsListDataControl.replaceIdentifierReferences( oldId, newId );
		conditionsController.replaceIdentifierReferences(oldId, newId);
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		actionsListDataControl.deleteIdentifierReferences( id );
		conditionsController.deleteIdentifierReferences(id);
	}
	
	/**
	 * Returns the conditions of the element reference.
	 * 
	 * @return Conditions of the element reference
	 */
	public ConditionsController getConditions( ) {
		HashMap<String, ConditionContextProperty> context1 = new HashMap<String, ConditionContextProperty>();
		ConditionOwner parent = new ConditionOwner(Controller.SCENE, sceneDataControl.getId());
		ConditionOwner owner = new ConditionOwner(Controller.ACTIVE_AREA, activeArea.getId(), parent);
		
		context1.put(ConditionsController.CONDITION_OWNER, owner);
		conditionsController = new ConditionsController( activeArea.getConditions( ), context1 );

		return conditionsController;
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	
	@Override
	public void recursiveSearch() {
		this.getActionsList().recursiveSearch();
		check(this.getBriefDescription(), TextConstants.getText("Search.BriefDescription"));
		check(this.getConditions(), TextConstants.getText("Search.Conditions"));
		check(this.getDetailedDescription(), TextConstants.getText("Search.DetailedDescription"));
		check(this.getDocumentation(), TextConstants.getText("Search.Documentation"));
		check(this.getId(), "ID");
		check(this.getName(), TextConstants.getText("Search.Name"));
	}

	public boolean isRectangular() {
		return activeArea.isRectangular();
	}

	public List<Point> getPoints() {
		return activeArea.getPoints();
	}

	public void addPoint(int x, int y) {
		controller.addTool(new AddNewPointTool(activeArea, x, y, influenceAreaDataControl));
	}

	public Point getLastPoint() {
		if (activeArea.getPoints().size() > 0)
			return activeArea.getPoints().get(activeArea.getPoints().size() - 1);
		return null;
	}

	public void deletePoint(Point point) {
		controller.addTool(new DeletePointTool(activeArea, point, influenceAreaDataControl));
	}

	public void setRectangular(boolean selected) {
		controller.addTool(new ChangeRectangularValueTool(activeArea, selected));
	}

	public Rectangle getRectangle() {
		return (Rectangle) this.getContent();
	}

	public InfluenceAreaDataControl getInfluenceArea() {
		return influenceAreaDataControl;
	}
	
	public SceneDataControl getSceneDataControl() {
		return sceneDataControl;
	}
	
	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return getPathFromChild(dataControl, actionsListDataControl);
	}

}
