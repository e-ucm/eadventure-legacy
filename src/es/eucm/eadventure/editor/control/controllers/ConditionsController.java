package es.eucm.eadventure.editor.control.controllers;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Controller for the conditions. This class operates on the conditions blocks to add new single conditions, and either
 * conditions blocks.
 * 
 * @author Bruno Torijano Bueno
 */
public class ConditionsController {

	public static final int VAR_CONDITION = Condition.VAR_CONDITION;
	public static final int FLAG_CONDITION = Condition.FLAG_CONDITION;
	
	/**
	 * Constant for the index of the main conditions block.
	 */
	public static final int MAIN_CONDITIONS_BLOCK = -1;

	/**
	 * String values for the states of a condition
	 */
	public static final String[] STATE_VALUES = { "Active", "Inactive", 
		">", ">=","<", "<=", "="
	};
	
	/**
	 * String values for the states of a flag condition
	 */
	public static final String[] STATE_VALUES_FLAGS = { "Active", "Inactive" };
	
	/**
	 * String values for the states of a varcondition
	 */
	public static final String[] STATE_VALUES_VARS = { ">", ">=","<", "<=", "=" };

	/**
	 * Link to the main controller.
	 */
	private Controller controller;

	/**
	 * Link to the flags summary.
	 */
	private VarFlagSummary varFlagSummary;

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
		varFlagSummary = controller.getVarFlagSummary( );
	}

	/**
	 * Returns if the controller can add conditions to the blocks.
	 * 
	 * @return True if the controller can add conditions, false otherwise
	 */
	public boolean canAddConditions( ) {
		return controller.getVarFlagSummary( ).getFlagCount( ) > 0 || 
		controller.getVarFlagSummary( ).getVarCount( ) > 0;
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
			conditionId = conditions.getMainConditions( ).get( conditionIndex ).getId( );

		else
			conditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getId( );

		return conditionId;
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
	public int getConditionType( int blockIndex, int conditionIndex ) {
		int conditionType;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			conditionType = conditions.getMainConditions( ).get( conditionIndex ).getType();

		else
			conditionType = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType();

		return conditionType;
	}
	
	
	/**
	 * Checks if the selected condition is a var or a flag condition
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public boolean isFlagCondtion( int blockIndex, int conditionIndex ) {
		boolean isFlag = false;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			isFlag = conditions.getMainConditions( ).get( conditionIndex ).getType() == Condition.FLAG_CONDITION;
		else
			isFlag = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType() == Condition.FLAG_CONDITION;

		return isFlag;
	}
	
	/**
	 * Checks if the selected condition is a var or a flag condition
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @return Id of the condition
	 */
	public boolean isVarCondtion( int blockIndex, int conditionIndex ) {
		boolean isFlag = false;

		if( blockIndex == MAIN_CONDITIONS_BLOCK )
			isFlag = conditions.getMainConditions( ).get( conditionIndex ).getType() == Condition.VAR_CONDITION;
		else
			isFlag = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType() == Condition.VAR_CONDITION;

		return isFlag;
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
			varFlagSummary.deleteFlagReference( condition.getId( ) );

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
	public void addCondition( int blockIndex, String conditionId, String conditionState, String value ) {

		
		if (Controller.getInstance( ).getVarFlagSummary( ).existsId( conditionId )){
			if( blockIndex == MAIN_CONDITIONS_BLOCK ){
				if ( isFlagState ( getStateFromString( conditionState ) ) ){
					conditions.getMainConditions( ).add( new Condition( conditionId, getStateFromString( conditionState ) ) );
					varFlagSummary.addFlagReference( conditionId );
				}else if ( isVarState ( getStateFromString( conditionState ) ) ){
					conditions.getMainConditions( ).add( new VarCondition( conditionId, getStateFromString( conditionState ), Integer.parseInt(value) ) );
					varFlagSummary.addVarReference( conditionId );
				}
	
			}else{
				if ( isFlagState ( getStateFromString( conditionState ) ) ){
					conditions.getEitherConditions( blockIndex ).add( new Condition( conditionId, getStateFromString( conditionState ) ) );
					varFlagSummary.addFlagReference( conditionId );
				}else if ( isVarState ( getStateFromString( conditionState ) ) ){
					conditions.getEitherConditions( blockIndex ).add( new VarCondition( conditionId, getStateFromString( conditionState ), Integer.parseInt(value) ) ) ;
					varFlagSummary.addVarReference( conditionId );
				}
			}
	
			// Add the flag reference
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
			conditionId = conditions.getMainConditions( ).get( conditionIndex ).getId( );
			conditions.getMainConditions( ).remove( conditionIndex );
		}

		else {
			conditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getId( );
			conditions.getEitherConditions( blockIndex ).remove( conditionIndex );
		}

		// Delete the flag reference
		varFlagSummary.deleteReference( conditionId );
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
		if ( id!=null ){
			// Stores the old flag
			String oldConditionId;
			int type;
	
			if( blockIndex == MAIN_CONDITIONS_BLOCK ) {
				oldConditionId = conditions.getMainConditions( ).get( conditionIndex ).getId( );
				type = conditions.getMainConditions( ).get( conditionIndex ).getType();
				conditions.getMainConditions( ).get( conditionIndex ).setId( id );
			}
	
			else {
				oldConditionId = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getId( );
				type = conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType();
				conditions.getEitherConditions( blockIndex ).get( conditionIndex ).setId( id );
			}
	
			// Updates the flag references
			varFlagSummary.deleteReference( oldConditionId );
			if ( type == Condition.FLAG_CONDITION )
				varFlagSummary.addFlagReference( id );
			else if ( type == Condition.VAR_CONDITION )
				varFlagSummary.addVarReference( id );
			controller.dataModified( );
		}
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
		if ( state!=null ){
			if( blockIndex == MAIN_CONDITIONS_BLOCK )
				conditions.getMainConditions( ).get( conditionIndex ).setState( getStateFromString( state ) );
	
			else
				conditions.getEitherConditions( blockIndex ).get( conditionIndex ).setState( getStateFromString( state ) );
		}
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
	 * @param value
	 *            New state of the condition
	 */
	public void setConditionValue( int blockIndex, int conditionIndex, String value ) {
		if ( value!=null ){
			if( blockIndex == MAIN_CONDITIONS_BLOCK ){
				if ( conditions.getMainConditions( ).get( conditionIndex ).getType() == Condition.VAR_CONDITION ){
					VarCondition varCond = ((VarCondition)(conditions.getMainConditions( ).get( conditionIndex )));
					varCond.setValue( Integer.parseInt( value ) );
				}
			}
	
			else {
				if ( conditions.getEitherConditions( blockIndex ).get( conditionIndex ).getType() == Condition.VAR_CONDITION ){
					VarCondition varCond = ((VarCondition)(conditions.getEitherConditions( blockIndex ).get( conditionIndex )));
					varCond.setValue( Integer.parseInt( value ) );
				}
			}
	
			controller.dataModified( );
		}
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
	 * @param value
	 *            New state of the condition
	 */
	public void setConditionType( int blockIndex, int conditionIndex, int type ) {
		
		Condition newCondition = null;
		
			if( blockIndex == MAIN_CONDITIONS_BLOCK ){
				Condition oldCondition = conditions.getMainConditions( ).get( conditionIndex ); 
				if ( oldCondition.getType() != type ){
					if ( type == ConditionsController.FLAG_CONDITION ){
						newCondition = new Condition ( oldCondition.getId(), Condition.FLAG_ACTIVE ) ;
						conditions.getMainConditions().remove( conditionIndex );
						conditions.getMainConditions().add( conditionIndex, newCondition);
					} else if ( type == ConditionsController.VAR_CONDITION ){
						newCondition = new VarCondition ( oldCondition.getId(), Condition.VAR_EQUALS, 1 ) ;
						conditions.getMainConditions().remove( conditionIndex );
						conditions.getMainConditions().add( conditionIndex, newCondition);
					}
				}
			}
	
			else {
				Condition oldCondition = conditions.getEitherConditions( blockIndex ).get( conditionIndex );
				if ( oldCondition.getType() != type ){
					if ( type == ConditionsController.FLAG_CONDITION ){
						newCondition = new Condition ( oldCondition.getId(), Condition.FLAG_ACTIVE ) ;
						conditions.getEitherConditions( blockIndex ).remove( conditionIndex );
						conditions.getEitherConditions( blockIndex ).add( conditionIndex, newCondition);
					} else if ( type == ConditionsController.VAR_CONDITION ){
						newCondition = new VarCondition ( oldCondition.getId(), Condition.VAR_EQUALS, 1 ) ;
						conditions.getEitherConditions( blockIndex ).remove( conditionIndex );
						conditions.getEitherConditions( blockIndex ).add( conditionIndex, newCondition);
					}
				}
			}
	
			if ( newCondition!= null ){
				controller.dataModified( );	
			}
			
	}


	/**
	 * Sets the new value (only vars) of the given condition of the given block.
	 * 
	 * @param blockIndex
	 *            The index of the conditions block. Use MAIN_CONDITIONS_BLOCK (-1) to select the main block of
	 *            conditions, and values from 0 to getEitherConditionsBlockCount( ) to access the either blocks of
	 *            conditions
	 * @param conditionIndex
	 *            Index of the condition
	 * @param value
	 *            New value of the var condition
	 */

	public String getConditionValue(int blockIndex, int conditionIndex) {
		// Stores the old flag
		String value = null;

		if( blockIndex == MAIN_CONDITIONS_BLOCK ) {
			if ( conditions.getMainConditions( ).get( conditionIndex ) instanceof VarCondition ){
				value = Integer.toString( ((VarCondition)(conditions.getMainConditions( ).get( conditionIndex ))).getValue( ) );	
			}
			
		}

		else {
			if ( conditions.getEitherConditions( blockIndex ).get( conditionIndex ) instanceof VarCondition ){
				value = Integer.toString( ((VarCondition)(conditions.getEitherConditions( blockIndex ).get( conditionIndex ))).getValue( ) );
			}
		}

		// Updates the flag references
		controller.dataModified( );

		return value;
	}
	
	/**
	 * Returns the int value of the string state given.
	 * 
	 * @param stringState
	 *            Condition state in String form
	 * @return Int value of state
	 */
	private int getStateFromString( String stringState ) {
		int state = Condition.NO_STATE;

		if( stringState.equals( STATE_VALUES[0] ) )
			state = Condition.FLAG_ACTIVE;

		else if( stringState.equals( STATE_VALUES[1] ) )
			state = Condition.FLAG_INACTIVE;
		
		else if( stringState.equals( STATE_VALUES[2] ) )
			state = Condition.VAR_GREATER_THAN;

		else if( stringState.equals( STATE_VALUES[3] ) )
			state = Condition.VAR_GREATER_EQUALS_THAN;
		
		else if( stringState.equals( STATE_VALUES[4] ) )
			state = Condition.VAR_LESS_THAN;
		
		else if( stringState.equals( STATE_VALUES[5] ) )
			state = Condition.VAR_LESS_EQUALS_THAN;
		
		else if( stringState.equals( STATE_VALUES[6] ) )
			state = Condition.VAR_EQUALS;
		return state;
	}

	/**
	 * Returns the string value of the boolean state given.
	 * 
	 * @param state
	 *            Condition state in int form
	 * @return String value of state
	 */
	private String getStringFromState( int state ) {
		String stringState = null;

		if( state == Condition.FLAG_ACTIVE )
			stringState = STATE_VALUES[0];

		else if( state == Condition.FLAG_INACTIVE )
			stringState = STATE_VALUES[1];

		else if( state == Condition.VAR_GREATER_THAN )
			stringState = STATE_VALUES[2];
		
		else if( state == Condition.VAR_GREATER_EQUALS_THAN )
			stringState = STATE_VALUES[3];

		else if( state == Condition.VAR_LESS_THAN )
			stringState = STATE_VALUES[4];
		
		else if( state == Condition.VAR_LESS_EQUALS_THAN )
			stringState = STATE_VALUES[5];

		else if( state == Condition.VAR_EQUALS )
			stringState = STATE_VALUES[6];

		return stringState;
	}

	/**
	 * Checks if the given state is valid for a flag
	 * @param state
	 * @return
	 */
	private boolean isFlagState ( int state ){
		return state == Condition.FLAG_ACTIVE || state == Condition.FLAG_INACTIVE;
	}
	
	/**
	 * Checks if the given state is valid for a var
	 * @param state
	 * @return
	 */
	private boolean isVarState ( int state ){
		return !isFlagState ( state );
	}

	
	/**
	 * Updates the given flag summary, adding the flag references contained in the given conditions.
	 * 
	 * @param varFlagSummary
	 *            Flag summary to update
	 * @param conditions
	 *            Set of conditions to search in
	 */
	public static void updateVarFlagSummary( VarFlagSummary varFlagSummary, Conditions conditions ) {
		// First check the main block of conditions
		for( Condition condition : conditions.getMainConditions( ) ){
			if ( condition.getType() == Condition.FLAG_CONDITION )
				varFlagSummary.addFlagReference( condition.getId( ) );
			else if ( condition.getType() == Condition.VAR_CONDITION )
				varFlagSummary.addVarReference( condition.getId( ) );
		}

		// Then add the references from the either blocks
		for( int i = 0; i < conditions.getEitherConditionsBlockCount( ); i++ )
			for( Condition condition : conditions.getEitherConditions( i ) ){
				if ( condition.getType() == Condition.FLAG_CONDITION )
					varFlagSummary.addFlagReference( condition.getId( ) );
				else if ( condition.getType() == Condition.VAR_CONDITION )
					varFlagSummary.addVarReference( condition.getId( ) );
			}
	}


}