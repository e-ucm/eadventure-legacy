package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Point;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.general.areaedition.ChangePointValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class PointDataControl extends DataControl {

	/**
	 * Contained node.
	 */
	private Point point;

	/**
	 * Constructor.
	 * 
	 * @param activeArea
	 *            Exit of the data control structure
	 */
	public PointDataControl( Point point ) {
		this.point = point;
	}
	
	/**
	 * Returns the X coordinate of the upper left position of the exit.
	 * 
	 * @return X coordinate of the upper left point
	 */
	public int getX( ) {
		return (int) point.getX( );
	}

	/**
	 * Returns the Y coordinate of the upper left position of the exit.
	 * 
	 * @return Y coordinate of the upper left point
	 */
	public int getY( ) {
		return (int) point.getY( );
	}


	/**
	 * Sets the new values for the exit.
	 * 
	 * @param x
	 *            X coordinate of the upper left point
	 * @param y
	 *            Y coordinate of the upper left point
	 */
	public void setPoint( int x, int y ) {
		controller.addTool(new ChangePointValueTool(point, x, y));
	}

	@Override
	public Object getContent( ) {
		return point;
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
	public String renameElement( String name) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// DO nothing
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Delete the references from the actions
		// Do nothing
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		//actionsListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		//actionsListDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}	

	@Override
	public void recursiveSearch() {
	}	
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		return null;
	}

}
