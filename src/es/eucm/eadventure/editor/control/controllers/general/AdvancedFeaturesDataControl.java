package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AdvancedFeaturesDataControl extends DataControl {

	
	private TimersListDataControl timersList;
	private AdaptationProfilesDataControl adaptationController;
	private AssessmentProfilesDataControl assessmentController;
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
		//TODO
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
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){

	}


	@Override
	public void deleteAssetReferences( String assetPath ) {

	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		return count;
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


	@Override
	public void recursiveSearch() {

	}


	public void setTimerListDataControl(TimersListDataControl timersList) {
		this.timersList = timersList;
	}


	public void setAdaptationProfilesDataControl(
			AdaptationProfilesDataControl adaptationController) {
		this.adaptationController = adaptationController;
	}


	public void setAssessmentProfilesDataControl(
			AssessmentProfilesDataControl assessmentController) {
		this.assessmentController = assessmentController;
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
		return timersList;
	}


	public AdaptationProfilesDataControl getAdaptationController() {
		return adaptationController;
	}


	public AssessmentProfilesDataControl getAssessmentController() {
		return assessmentController;
	}


	public GlobalStateListDataControl getGlobalStatesListDataControl() {
		return globalStatesListDataControl;
	}


	public MacroListDataControl getMacrosListDataControl() {
		return macrosListDataControl;
	}
	
	
}
