package es.eucm.eadventure.engine.core.data.gamedata.conditions;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * This class holds a list of conditions
 */
public class Conditions {
    
    /**
     * List of simple conditions to be checked
     */
    private ArrayList<Condition> conditions;
    
    /**
     * List of conditions to be evaluated with OR logic
     */
    private ArrayList<Conditions> eitherConditions;

    /**
     * Create a new Conditions
     */
    public Conditions( ) {
        conditions = new ArrayList<Condition>( );
        eitherConditions = new ArrayList<Conditions>( );
    }

    /**
     * Adds a new condition to the list
     * @param condition the condition to add
     */
    public void addCondition( Condition condition ) {
        conditions.add( condition );
    }
    
    /**
     * Adds a list of conditions, such that at least one of these must be ok
     * @param conditions the conditions to add
     */
    public void addEitherCondition( Conditions conditions ) {
        eitherConditions.add( conditions );
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
                evaluation = condition.isActive( ) == flags.isActiveFlag( condition.getId( ) );
        
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
                evaluation = condition.isActive( ) == flags.isActiveFlag( condition.getId( ) );
        
        return evaluation;
    }
}
