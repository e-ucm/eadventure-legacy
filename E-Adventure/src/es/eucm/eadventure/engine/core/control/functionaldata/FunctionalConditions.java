package es.eucm.eadventure.engine.core.control.functionaldata;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.Game;

public class FunctionalConditions{

	private Conditions conditions;

	public FunctionalConditions ( Conditions conditions ){
		this.conditions = conditions;
	}
	
    /**
     * Returns whether all the conditions are ok
     * @return true if all the conditions are ok, false otherwise
     */
    public boolean allConditionsOk( ) {
        boolean conditionsOK = true;

        conditionsOK = evaluateSimpleConditionsWithAND( );
        
        for ( int i=0; i<conditions.getEitherConditionsBlockCount(); i++){
        	Conditions eitherCondition = conditions.getEitherBlock(i);
            if( conditionsOK )
                conditionsOK = new FunctionalConditions (eitherCondition).evaluateSimpleConditionsWithOR( );

        }

        return conditionsOK;
    }

    /**
     * Returns whether all the conditions are satisfied
     * @return true if all the conditions are satisfied, false otherwise
     */
    private boolean evaluateSimpleConditionsWithAND( ) {
        boolean evaluation = true;
        
        FlagSummary flags = Game.getInstance( ).getFlags( );
        
        for( Condition condition : conditions.getMainConditions() )
            if( evaluation )
                evaluation = condition.getState( ) == flags.isActiveFlag( condition.getFlag( ) );
        
        return evaluation;
    }
    
    /**
     * Returns whether at least one condition is satisfied
     * @return true if at least one condition is satisfied, false otherwise
     */
    private boolean evaluateSimpleConditionsWithOR( ) {
        boolean evaluation = false;
        
        FlagSummary flags = Game.getInstance( ).getFlags( );
        
        for( Condition condition : conditions.getMainConditions() )
            if( !evaluation )
                evaluation = condition.getState( ) == flags.isActiveFlag( condition.getFlag( ) );
        
        return evaluation;
    }

}
