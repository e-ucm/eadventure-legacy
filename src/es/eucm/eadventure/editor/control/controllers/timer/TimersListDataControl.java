package es.eucm.eadventure.editor.control.controllers.timer;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class TimersListDataControl extends DataControl {

	/**
	 * List of timers.
	 */
	private List<Timer> timersList;

	/**
	 * List of timer controllers.
	 */
	private List<TimerDataControl> timersDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param timersList
	 *            List of timers
	 */
	public TimersListDataControl( List<Timer> timersList ) {
		this.timersList = timersList;

		// Create subcontrollers
		timersDataControlList = new ArrayList<TimerDataControl>( );
		for( Timer timer : timersList )
			timersDataControlList.add( new TimerDataControl( timer ) );
	}

	/**
	 * Returns the list of cutscene controllers.
	 * 
	 * @return Cutscene controllers
	 */
	public List<TimerDataControl> getTimers( ) {
		return timersDataControlList;
	}

	/**
	 * Returns the last timer controller of the list.
	 * 
	 * @return Last timer controller
	 */
	public TimerDataControl getLastTimer( ) {
		return timersDataControlList.get( timersDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the timers contained in the list.
	 * 
	 * @return Array with the information of the timers. It contains the index of each timer, its time, and if it has init conditions, end conditions, effects or post-effects
	 */
	public String[][] getTimersInfo( ) {
		String[][] timersInfo = null;

		// Create the list for the timers info
		timersInfo = new String[timersList.size( )][6];

		// Fill the array with the info
		for( int i = 0; i < timersList.size( ); i++ ) {
			Timer timer = timersList.get( i );
			timersInfo[i][0] = Integer.toString( i );
			timersInfo[i][1] = timersDataControlList.get( i ).getTimeHhMmSs( );
			
			if (!timer.getInitCond( ).isEmpty( )){
				timersInfo[i][2] = TextConstants.getText( "GeneralText.Yes" );	
			} else {
				timersInfo[i][2] = TextConstants.getText( "GeneralText.No" );
			}
			
			if (!timer.getEndCond( ).isEmpty( )){
				timersInfo[i][3] = TextConstants.getText( "GeneralText.Yes" );	
			} else {
				timersInfo[i][3] = TextConstants.getText( "GeneralText.No" );
			}
			
			if (!timer.getEffects( ).isEmpty( )){
				timersInfo[i][4] = TextConstants.getText( "GeneralText.Yes" );	
			} else {
				timersInfo[i][4] = TextConstants.getText( "GeneralText.No" );
			}
			
			if (!timer.getPostEffects( ).isEmpty( )){
				timersInfo[i][5] = TextConstants.getText( "GeneralText.Yes" );	
			} else {
				timersInfo[i][5] = TextConstants.getText( "GeneralText.No" );
			}

		}

		return timersInfo;
	}
	
	

	@Override
	public Object getContent( ) {
		return timersList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.TIMER};
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new cutscenes
		return type == Controller.TIMER ;
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
		boolean elementAdded = false;

		if( type == Controller.TIMER ) {
			// Create the new timer with default time
				Timer newTimer = new Timer(  );
				newTimer.setRunsInLoop(false);
				newTimer.setMultipleStarts(false);
				newTimer.setUsesEndCondition(false);
				
				// Add the new timer
				timersList.add( newTimer );
				timersDataControlList.add( new TimerDataControl( newTimer ) );
				//controller.dataModified( );
				elementAdded = true;
		}

		return elementAdded;
	}
	
	private int findDataControlIndex( DataControl dataControl ){
		int index = -1;
		for (int i=0; i<this.timersDataControlList.size( ); i++){
			if (timersDataControlList.get( i ) == dataControl){
				index = i;
				break;
			}
		}
		return index;
		
	}
	
	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean elementDeleted = false;
		
			int index = findDataControlIndex(dataControl);

			// Ask for confirmation
			if(!askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { 
					TextConstants.getElementName( Controller.TIMER )+" #"+Integer.toString( index ), "0" } ) ) ) {
				if( timersList.remove( dataControl.getContent( ) ) ) {
					timersDataControlList.remove( dataControl );
					//controller.dataModified( );
					elementDeleted = true;
				}
			}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = timersList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			timersList.add( elementIndex - 1, timersList.remove( elementIndex ) );
			timersDataControlList.add( elementIndex - 1, timersDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = timersList.indexOf( dataControl.getContent( ) );

		if( elementIndex < timersList.size( ) - 1 ) {
			timersList.add( elementIndex + 1, timersList.remove( elementIndex ) );
			timersDataControlList.add( elementIndex + 1, timersDataControlList.remove( elementIndex ) );
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
		// Iterate through each timer
		for( TimerDataControl timerDataControl : timersDataControlList )
			timerDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Update the current path
		currentPath += " >> " + TextConstants.getElementName( Controller.TIMERS_LIST );

		// Iterate through the timers
		for( int i=0; i< timersDataControlList.size(); i++ ) {
			String cutscenePath = currentPath + " >> " + TextConstants.getElementName( Controller.TIMER ) +" #"+i;
			valid &= timersDataControlList.get( i ).isValid( cutscenePath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through each timer
		for( TimerDataControl timerDataControl : timersDataControlList )
			count += timerDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		for ( TimerDataControl timerDataControl: timersDataControlList ){
			timerDataControl.getAssetReferences( assetPaths, assetTypes );
		}
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through each timer
		for( TimerDataControl timerDataControl : timersDataControlList )
			timerDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each timer
		for( TimerDataControl cutsceneDataControl : timersDataControlList )
			count += cutsceneDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each timer
		for( TimerDataControl timerDataControl : timersDataControlList )
			timerDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// Spread the call to every timer
		for( TimerDataControl timerDataControl : timersDataControlList )
			timerDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}

	@Override
	public void recursiveSearch() {
		for (DataControl dc : this.timersDataControlList)
			dc.recursiveSearch();
	}
}
