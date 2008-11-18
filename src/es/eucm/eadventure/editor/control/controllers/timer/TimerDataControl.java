package es.eucm.eadventure.editor.control.controllers.timer;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class TimerDataControl extends DataControl {

	/**
	 * Contained timer structure.
	 */
	private Timer timer;

	/**
	 * Conditions controller (init).
	 */
	private ConditionsController initConditionsController;
	
	/**
	 * Conditions controller (end).
	 */
	private ConditionsController endConditionsController;

	/**
	 * FunctionalEffects controller
	 */
	private EffectsController effectsController;

	/**
	 * Post effects controller.
	 */
	private EffectsController postEffectsController;
	
	/**
	 * Contructor.
	 * 
	 * @param timer
	 *            Timer structure
	 */
	public TimerDataControl( Timer timer ) {
		this.timer = timer;

		// Create subcontrollers
		initConditionsController = new ConditionsController( timer.getInitCond( ) );
		endConditionsController = new ConditionsController( timer.getEndCond( ) );
		effectsController = new EffectsController( timer.getEffects( ) );
		postEffectsController = new EffectsController( timer.getPostEffects( ) );

	}

	/**
	 * Returns the init conditions of the timer.
	 * 
	 * @return Init Conditions of the timer
	 */
	public ConditionsController getInitConditions( ) {
		return initConditionsController;
	}
	
	/**
	 * Returns the end conditions of the timer.
	 * 
	 * @return end Conditions of the timer
	 */
	public ConditionsController getEndConditions( ) {
		return endConditionsController;
	}


	/**
	 * Returns the effects of the next scene.
	 * 
	 * @return FunctionalEffects of the next scene
	 */
	public EffectsController getEffects( ) {
		return effectsController;
	}

	/**
	 * Returns the post-effects of the next scene.
	 * 
	 * @return Post-effects of the next scene
	 */
	public EffectsController getPostEffects( ) {
		return postEffectsController;
	}


	@Override
	public Object getContent( ) {
		return timer;
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
	public void updateFlagSummary( FlagSummary flagSummary ) {
		// Update the summary with conditions and both blocks of effects
		ConditionsController.updateFlagSummary( flagSummary, timer.getInitCond( ) );
		ConditionsController.updateFlagSummary( flagSummary, timer.getEndCond( ) );
		EffectsController.updateFlagSummary( flagSummary, timer.getEffects( ) );
		EffectsController.updateFlagSummary( flagSummary, timer.getPostEffects( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Valid if the effects and the post effects are valid
		valid &= EffectsController.isValid( currentPath + " >> " + TextConstants.getText( "Element.Effects" ), incidences, timer.getEffects( ) );
		valid &= EffectsController.isValid( currentPath + " >> " + TextConstants.getText( "Element.PostEffects" ), incidences, timer.getPostEffects( ) );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Add to the counter the values of the effects and posteffects
		count += EffectsController.countAssetReferences( assetPath, timer.getEffects( ) );
		count += EffectsController.countAssetReferences( assetPath, timer.getPostEffects( ) );

		return count;
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		EffectsController.deleteAssetReferences( assetPath, timer.getEffects( ) );
		EffectsController.deleteAssetReferences( assetPath, timer.getPostEffects( ) );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Add to the counter the values of the effects and posteffects
		count += EffectsController.countIdentifierReferences( id, timer.getEffects( ) );
		count += EffectsController.countIdentifierReferences( id, timer.getPostEffects( ) );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {

		EffectsController.replaceIdentifierReferences( oldId, newId, timer.getEffects( ) );
		EffectsController.replaceIdentifierReferences( oldId, newId, timer.getPostEffects( ) );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		EffectsController.deleteIdentifierReferences( id, timer.getEffects( ) );
		EffectsController.deleteIdentifierReferences( id, timer.getPostEffects( ) );
	}

	/**
	 * Returns the time of the timer represented as hours:minutes:seconds. The string returned will look like:
	 * HHh:MMm:SSs
	 * @return The time as HHh:MMm:SSs
	 */
	public String getTimeHhMmSs (){
		String time = "";
		long seconds = timer.getTime( );
		
		// Less than 60 seconds
		if (seconds <60 && seconds>=0){
			time = Long.toString( seconds )+"s";
		}
		
		// Between 1 minute and 60 minutes
		else if (seconds < 3600 && seconds>=60){
			long minutes = seconds / 60;
			long lastSeconds = seconds % 60;
			time = Long.toString( minutes )+"m:"+Long.toString( lastSeconds )+"s";
		}
		
		// One hour or more
		else if (seconds>=3600){
			long hours = seconds / 3600;
			long minutes = (seconds % 3600) / 60;
			long lastSeconds = (seconds % 3600) % 60;
			time = Long.toString( hours )+"h:"+Long.toString( minutes )+"m:"+Long.toString( lastSeconds )+"s";
		}
		
		return time;
	}
	
	/**
	 * Returns the time in total seconds
	 * @return The time in seconds
	 */
	public long getTime(){
		return timer.getTime( );
	}
	
	public String getDocumentation(){
		return timer.getDocumentation( );
	}
	
	public void setDocumentation(String newDoc){
		timer.setDocumentation( newDoc );
		controller.dataModified( );
	}
	
	public void setTime( long newTime ){
		timer.setTime( newTime );
		controller.dataModified( );
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		EffectsController.getAssetReferences( assetPaths, assetTypes, timer.getEffects( ) );
		EffectsController.getAssetReferences( assetPaths, assetTypes, timer.getPostEffects( ) );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
