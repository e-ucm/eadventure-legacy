package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdvancedFeaturesDataControl extends DataControl {

	
	private TimersListDataControl timersListDataControl;
		
	private GlobalStateListDataControl globalStatesListDataControl;
	
	private MacroListDataControl macrosListDataControl;


	/**
	 * Constructor.
	 * 
	 * @param scene
	 *            Contained scene data
	 */
	public AdvancedFeaturesDataControl( ) {
	}


	@Override
	public Object getContent( ) {
		return null;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { /*Controller.RESOURCES*/ };
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
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
	public boolean addElement( int type, String id ) {
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
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
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		timersListDataControl.updateVarFlagSummary( varFlagSummary );
		globalStatesListDataControl.updateVarFlagSummary( varFlagSummary );
		macrosListDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		valid &= timersListDataControl.isValid( currentPath , incidences);
		valid &= globalStatesListDataControl.isValid( currentPath, incidences );
		valid &= macrosListDataControl.isValid( currentPath, incidences );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;
		
		count += timersListDataControl.countAssetReferences( assetPath );
		count += globalStatesListDataControl.countAssetReferences( assetPath );
		count += macrosListDataControl.countAssetReferences( assetPath );
		
		return count;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		timersListDataControl.getAssetReferences( assetPaths, assetTypes );
		globalStatesListDataControl.getAssetReferences( assetPaths, assetTypes );
		macrosListDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		timersListDataControl.deleteAssetReferences(assetPath);
		globalStatesListDataControl.deleteAssetReferences(assetPath);
		macrosListDataControl.deleteAssetReferences(assetPath);
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		count += timersListDataControl.countIdentifierReferences(id);
		count += globalStatesListDataControl.countIdentifierReferences(id);
		count += macrosListDataControl.countIdentifierReferences(id);
		
		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		timersListDataControl.replaceIdentifierReferences(oldId, newId);
		globalStatesListDataControl.replaceIdentifierReferences(oldId, newId);
		macrosListDataControl.replaceIdentifierReferences(oldId, newId);
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		timersListDataControl.deleteIdentifierReferences( id );
		globalStatesListDataControl.deleteIdentifierReferences( id );
		macrosListDataControl.deleteIdentifierReferences( id );

	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}


	@Override
	public void recursiveSearch() {
		this.getMacrosListDataControl().recursiveSearch();
		this.getGlobalStatesListDataControl().recursiveSearch();
		this.getTimersList().recursiveSearch();
	}


	public void setTimerListDataControl(TimersListDataControl timersList) {
		this.timersListDataControl = timersList;
	}

	public void setGlobalStatesListDataContorl(
			GlobalStateListDataControl globalStatesListDataControl) {
		this.globalStatesListDataControl = globalStatesListDataControl;
	}


	public void setMacrosListDataControl(
				MacroListDataControl macrosListDataControl) {
		this.macrosListDataControl = macrosListDataControl;
	}


	public TimersListDataControl getTimersList() {
		return timersListDataControl;
	}


	public GlobalStateListDataControl getGlobalStatesListDataControl() {
		return globalStatesListDataControl;
	}


	public MacroListDataControl getMacrosListDataControl() {
		return macrosListDataControl;
	}
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		List<DataControl> path = getPathFromChild(dataControl, globalStatesListDataControl);
		if (path != null) return path;
		path = getPathFromChild(dataControl, timersListDataControl);
		if (path != null) return path;
		return getPathFromChild(dataControl, macrosListDataControl);
	}

}
