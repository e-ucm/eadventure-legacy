package es.eucm.eadventure.common.data.chapter.conditions;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * This class holds a list of conditions
 */
public class Conditions {

	/**
	 * List of simple conditions to be checked
	 */
	private List<Condition> conditions;

	/**
	 * List of conditions to be evaluated with OR logic
	 */
	private List<Conditions> eitherConditions;

	/**
	 * Create a new Conditions
	 */
	public Conditions( ) {
		conditions = new ArrayList<Condition>( );
		eitherConditions = new ArrayList<Conditions>( );
	}

	/**
	 * Adds a new condition to the list
	 * 
	 * @param condition
	 *            the condition to add
	 */
	public void addCondition( Condition condition ) {
		conditions.add( condition );
	}

	/**
	 * Adds a list of conditions, such that at least one of these must be ok
	 * 
	 * @param conditions
	 *            the conditions to add
	 */
	public void addEitherCondition( Conditions conditions ) {
		eitherConditions.add( conditions );
	}

	/**
	 * Returns whether the conditions block is empty or not.
	 * 
	 * @return True if the block has no conditions, false otherwise
	 */
	public boolean isEmpty( ) {
		return conditions.isEmpty( ) && eitherConditions.isEmpty( );
	}

	/**
	 * Deletes the given either conditions block.
	 * 
	 * @param index
	 *            Index of the either conditions block
	 */
	public void deleteEitherCondition( int index ) {
		eitherConditions.remove( index );
	}

	/**
	 * Returns the main block of conditions (evaluated with AND).
	 * 
	 * @return List of conditions
	 */
	public List<Condition> getMainConditions( ) {
		return conditions;
	}

	/**
	 * Returns the number of either conditions blocks present.
	 * 
	 * @return Count of either conditions blocks
	 */
	public int getEitherConditionsBlockCount( ) {
		return eitherConditions.size( );
	}

	/**
	 * Returns the either block of conditions specified.
	 * 
	 * @param index
	 *            Index of the either block of conditions
	 * @return List of conditions
	 */
	public List<Condition> getEitherConditions( int index ) {
		return eitherConditions.get( index ).getMainConditions( );
	}
	
	/**
	 * Returns the either block of conditions specified.
	 * 
	 * @param index
	 *            Index of the either block of conditions
	 * @return List of conditions
	 */
	public Conditions getEitherBlock( int index ) {
		return eitherConditions.get( index );
	}

    /**
     * Returns whether all the conditions are ok
     * @return true if all the conditions are ok, false otherwise
     */
    public boolean allConditionsOk( ) {
        boolean conditionsOK = true;

        conditionsOK = evaluateSimpleConditionsWithAND( );
        
        for( Conditions eitherCondition : eitherConditions )
            if( conditionsOK )
                conditionsOK = eitherCondition.evaluateSimpleConditionsWithOR( );
            

        return conditionsOK;
    }
    
    /**
     * Returns whether all the conditions are satisfied
     * @return true if all the conditions are satisfied, false otherwise
     */
    private boolean evaluateSimpleConditionsWithAND( ) {
        boolean evaluation = true;
        
        FlagSummary flags = Game.getInstance( ).getFlags( );
        
        for( Condition condition : conditions )
            if( evaluation )
                evaluation = condition.getState( ) == flags.isActiveFlag( condition.getFlag() );
        
        return evaluation;
    }
    
    /**
     * Returns whether at least one condition is satisfied
     * @return true if at least one condition is satisfied, false otherwise
     */
    private boolean evaluateSimpleConditionsWithOR( ) {
        boolean evaluation = false;
        
        FlagSummary flags = Game.getInstance( ).getFlags( );
        
        for( Condition condition : conditions )
            if( !evaluation )
                evaluation = condition.getState( ) == flags.isActiveFlag( condition.getFlag() );
        
        return evaluation;
    }
}
