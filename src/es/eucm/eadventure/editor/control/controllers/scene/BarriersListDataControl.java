package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class BarriersListDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;
	
	/**
	 * List of barriers.
	 */
	private List<Barrier> barriersList;

	/**
	 * List of barriers controllers.
	 */
	private List<BarrierDataControl> barriersDataControlList;
	
	/**
	 * Id of the next active area
	 */
	private int id = 0;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param barriersList
	 *            List of activeAreas
	 */
	public BarriersListDataControl( SceneDataControl sceneDataControl, List<Barrier> barriersList ) {
		this.sceneDataControl = sceneDataControl;
		this.barriersList = barriersList;

		// Create subcontrollers
		barriersDataControlList = new ArrayList<BarrierDataControl>( );
		for( Barrier barrier : barriersList )
			barriersDataControlList.add( new BarrierDataControl( sceneDataControl, barrier ) );
		
		id = barriersList.size( ) +1;
	}

	/**
	 * Returns the list of barriers controllers.
	 * 
	 * @return List of barriers controllers
	 */
	public List<BarrierDataControl> getBarriers( ) {
		return barriersDataControlList;
	}

	/**
	 * Returns the last barrier controller from the list.
	 * 
	 * @return Last barrier controller
	 */
	public BarrierDataControl getLastBarrier( ) {
		return barriersDataControlList.get( barriersDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the id of the scene that contains this activeAreas list.
	 * 
	 * @return Parent scene id
	 */
	public String getParentSceneId( ) {
		return sceneDataControl.getId( );
	}

	@Override
	public Object getContent( ) {
		return barriersList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.BARRIER };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new barrier
		return type == Controller.BARRIER;
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
	public boolean addElement( int type, String barrierId ) {
		boolean elementAdded = false;

		if( type == Controller.BARRIER ) {
			// Creamos una salida y su controlador
			Barrier newBarrier = new Barrier( Integer.toString( id ), 0, 0, 0, 0 );
			id++;
			BarrierDataControl newBarrierDataControl = new BarrierDataControl( sceneDataControl, newBarrier );

				barriersList.add( newBarrier );
				barriersDataControlList.add( newBarrierDataControl );
				//controller.dataModified( );
				elementAdded = true;

		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation ) {
		boolean elementDeleted = false;

		if( barriersList.remove( dataControl.getContent( ) ) ) {
			barriersDataControlList.remove( dataControl );
			//controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = barriersList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			barriersList.add( elementIndex - 1, barriersList.remove( elementIndex ) );
			barriersDataControlList.add( elementIndex - 1, barriersDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = barriersList.indexOf( dataControl.getContent( ) );

		if( elementIndex < barriersList.size( ) - 1 ) {
			barriersList.add( elementIndex + 1, barriersList.remove( elementIndex ) );
			barriersDataControlList.add( elementIndex + 1, barriersDataControlList.remove( elementIndex ) );
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
		// Iterate through each activeArea
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			barrierDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the activeAreas
		for( int i = 0; i < barriersDataControlList.size( ); i++ ) {
			String activeAreaPath = currentPath + " >> " + TextConstants.getElementName( Controller.BARRIER ) + " #" + ( i + 1 );
			valid &= barriersDataControlList.get( i ).isValid( activeAreaPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through each activeArea
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			count += barrierDataControl.countAssetReferences( assetPath );

		return count;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			barrierDataControl.getAssetReferences( assetPaths, assetTypes );
		
	}
	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through each activeArea
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			barrierDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each activeArea
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			count += barrierDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each activeArea
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			barrierDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// Spread the call to every activeArea
		for( BarrierDataControl barrierDataControl : barriersDataControlList )
			barrierDataControl.deleteIdentifierReferences( id );

	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
	
	public TrajectoryDataControl getTrajectoryDataControl() {
		return sceneDataControl.getTrajectory();
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

	public List<ExitDataControl> getParentSceneExits() {
		return sceneDataControl.getExitsList().getExits();
	}

	public List<ActiveAreaDataControl> getParentSceneActiveAreas() {
		return sceneDataControl.getActiveAreasList().getActiveAreas();
	}

	@Override
	public void recursiveSearch() {
		for (DataControl dc : this.barriersDataControlList)
			dc.recursiveSearch();
	}

	public TrajectoryDataControl getParentSceneTrajectory() {
		return sceneDataControl.getTrajectory();
	}
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		return getPathFromChild(dataControl, barriersDataControlList);
	}

}
