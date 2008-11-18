package es.eucm.eadventure.editor.control.controllers;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;

/**
 * Controller for the conditions. This class operates on the conditions blocks to add new single conditions, and either
 * conditions blocks.
 * 
 * @author Bruno Torijano Bueno
 */
public class ConditionsController {

	/**
	 * Constant for the index of the main conditions block.
	 */
	public static final int MAIN_CONDITIONS_BLOCK = -1;

	/**
	 * String values for the states of a condition
	 */
	public static final String[] STATE_VALUES = { "Active", "Inactive" };

	/**
	 * Link to the main controller.
	 */
	private Controller controller;

	/**
	 * Link to the flags summary.
	 */
	private FlagSummary flagSummary;

	/**
	 * Conditions data.
	 */
	private Conditions conditions;

	/**
	 * Constructor.
	 * 
	 * @param conditions
	 *            Conditions block of data
	 */
	public ConditionsController( Conditions conditions ) {
		this.conditions = conditions;
		controller = Controller.getInstance( );
		flagSummary = controller.getFlagSummary( );
	}

	/**
	 * Returns if the controller can add conditions to the blocks.
	 * 
	 * @return True if the controller can add conditions, false otherwise
	 */
	public boolean canAddConditions( ) {
		return controller.getFlagSummary( ).getFlagCount( ) > 0;
	}

	/**
	 * Returns the number of either conditions blocks present in the conditions data.
	 * 
	 * @return Number of either conditions blocks
	 */
	public int getEitherConditionsBlockCount( ) {
		return conditions.getEitherConditionsBlockCount( );
	}

	/**
	 * Returns the number of conditions of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @return Number of conditions
	 */
	public int getConditionCount( int blockIndex ) {
		int conditionCount;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionCount = conditions.getMainConditions( ).size( );

		else
			conditionCount = conditions.getEitherConditions( blockIndex ).size( );

		return conditionCount;
	}

	/**
	 * Returns the id of the condition in the given position of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public String getConditionId( int blockIndex, int conditionIndex ) {
		String conditionId;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionId = conditions.getMainConditions( ).get( conditionIndex ).getFlag( );

		else
			conditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getFlag( );

		return conditionId;
	}

	/**
	 * Returns the state of the condition in the index position of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return State of the condition
	 */
	public String getConditionState( int blockIndex, int conditionIndex ) {
		String conditionState;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionState = getStringFromState( conditions.getMainConditions( ).get( conditionIndex ).getState( ) );

		else
			conditionState = getStringFromState( conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getState( ) );

		return conditionState;
	}

	/**
	 * Adds a new either conditions block.
	 */
	public void addEitherConditionsBlock( ) {
		conditions.addEitherCondition( new Conditions( ) );
		controller.dataModified( );
	}

	/**
	 * Deletes the given either conditions block.
	 * 
	 * @param index
	 *            Index of the either conditions block
	 */
	public void deleteEitherConditionsBlock( int index ) {
		// Delete the flag references
		for( Condition condition : conditions.getEitherConditions( index ) )
			flagSummary.deleteReference( condition.getFlag( ) );

		// Delete the block
		conditions.deleteEitherCondition( index );
		controller.dataModified( );
	}

	/**
	 * Adds a new condition to the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionId
	 *            Id of the condition
	 * @param conditionState
	 *            State of the condition
	 */
	public void addCondition( int blockIndex, String conditionId, String conditionState ) {

		
		if (Controller.getInstance( ).getFlagSummary( ).existsFlag( conditionId )){
		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditions.getMainConditions( ).add( new Condition( conditionId, getStateFromString( conditionState ) ) );

		else
			conditions.getEitherConditions( blockIndex ).add( new Condition( conditionId, getStateFromString( conditionState ) ) );

		// Add the flag reference
		flagSummary.addReference( conditionId );
		controller.dataModified( );
		}
	}

	/**
	 * Deletes the given condition from the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 */
	public void deleteCondition( int blockIndex, int conditionIndex ) {
		// Stores the flag reference
		String conditionId;

		if( blockIndex == MAIN_CONDITIONS_BLOCK ) {
			conditionId = conditions.getMainConditions( ).get( conditionIndex ).getFlag( );
			conditions.getMainConditions( ).remove( conditionIndex );
		}

		else {
			conditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getFlag( );
			conditions.getEitherConditions( blockIndex ).remove( conditionIndex );
		}

		// Delete the flag reference
		flagSummary.deleteReference( conditionId );
		controller.dataModified( );
	}

	/**
	 * Sets the new id of the given condition of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @param id
	 *            New id of the condition
	 */
	public void setConditionId( int blockIndex, int conditionIndex, String id ) {
		// Stores the old flag
		String oldConditionId;

		if( blockIndex == MAIN_CONDITIONS_BLOCK ) {
			oldConditionId = conditions.getMainConditions( ).get( conditionIndex ).getFlag( );
			conditions.getMainConditions( ).get( conditionIndex ).setFlag( id );
		}

		else {
			oldConditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getFlag( );
			conditions.getEitherConditions( blockIndex ).get( conditionIndex ).setFlag( id );
		}

		// Updates the flag references
		flagSummary.deleteReference( oldConditionId );
		flagSummary.addReference( id );
		controller.dataModified( );
	}

	/**
	 * Sets the new state of the given condition of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @param state
	 *            New state of the condition
	 */
	public void setConditionState( int blockIndex, int conditionIndex, String state ) {
		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditions.getMainConditions( ).get( conditionIndex ).setState( getStateFromString( state ) );

		else
			conditions.getEitherConditions( blockIndex ).get( conditionIndex ).setState( getStateFromString( state ) );

		controller.dataModified( );
	}

	/**
	 * Returns the boolean value of the string state given.
	 * 
	 * @param stringState
	 *            Condition state in String form
	 * @return Boolean value of state
	 */
	private boolean getStateFromString( String stringState ) {
		boolean state = false;

		if( stringState.equals( STATE_VALUES[0] ) )
			state = Condition.FLAG_ACTIVE;

		else if( stringState.equals( STATE_VALUES[1] ) )
			state = Condition.FLAG_INACTIVE;

		return state;
	}

	/**
	 * Returns the string value of the boolean state given.
	 * 
	 * @param state
	 *            Condition state in boolean form
	 * @return String value of state
	 */
	private String getStringFromState( boolean state ) {
		String stringState = null;

		if( state == Condition.FLAG_ACTIVE )
			stringState = STATE_VALUES[0];

		else if( state == Condition.FLAG_INACTIVE )
			stringState = STATE_VALUES[1];

		return stringState;
	}

	/**
	 * Updates the given flag summary, adding the flag references contained in the given conditions.
	 * 
	 * @param flagSummary
	 *            Flag summary to update
	 * @param conditions
	 *            Set of conditions to search in
	 */
	public static void updateFlagSummary( FlagSummary flagSummary, Conditions conditions ) {
		// First check the main block of conditions
		for( Condition condition : conditions.getMainConditions( ) )
			flagSummary.addReference( condition.getFlag( ) );

		// Then add the references from the either blocks
		for( int i = 0; i < conditions.getEitherConditionsBlockCount( ); i++ )
			for( Condition condition : conditions.getEitherConditions( i ) )
				flagSummary.addReference( condition.getFlag( ) );
	}
}