package es.eucm.eadventure.engine.core.control.functionaldata;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.VarSummary;

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
        VarSummary vars = Game.getInstance( ).getVars( );
        
        for( Condition condition : conditions.getMainConditions() ){
            if( evaluation ){
            	if (condition.getType() == Condition.FLAG_CONDITION){
            		evaluation = condition.isActiveState() == flags.isActiveFlag( condition.getId( ) );            		
            	} else if (condition.getType() == Condition.VAR_CONDITION ){
            		VarCondition varCondition = (VarCondition)condition;
            		int actualValue = vars.getValue( varCondition.getId());
            		int state = varCondition.getState();
            		int value = varCondition.getValue();
            		evaluation = evaluateVarCondition ( state, value, actualValue );
            	} else if (condition.getType() == Condition.GLOBAL_STATE_CONDITION ){
            		String globalStateId = condition.getId( );
            		GlobalState gs = Game.getInstance( ).getCurrentChapterData( ).getGlobalState( globalStateId );
            		evaluation = new FunctionalConditions ( gs ).allConditionsOk();
            	}
            } 
        }
        return evaluation;
    }

    /**
     * Evaluates a var condition according to the state (function to use for evaluation), value of comparison, and the actual value of the var
     * @param state >, >=, =, < or <=
     * @param value The value to compare with
     * @param actualValue The actual value assigned to the var so far
     * @return True if condition is true; false otherwise
     */
    private boolean evaluateVarCondition (int state, int value, int actualValue ){
    	if (state == VarCondition.VAR_EQUALS ){
    		return actualValue == value;
    	} else if (state == VarCondition.VAR_GREATER_EQUALS_THAN ){
    		return actualValue >= value;
    	} else if (state == VarCondition.VAR_GREATER_THAN ){
    		return actualValue > value;
    	} else if (state == VarCondition.VAR_LESS_EQUALS_THAN ){
    		return actualValue <= value;
    	} else if (state == VarCondition.VAR_LESS_THAN ){
    		return actualValue < value;
    	} else
    		return false;
    }
    
    /**
     * Returns whether at least one condition is satisfied
     * @return true if at least one condition is satisfied, false otherwise
     */
    private boolean evaluateSimpleConditionsWithOR( ) {
        boolean evaluation = false;
        
        FlagSummary flags = Game.getInstance( ).getFlags( );
        VarSummary vars = Game.getInstance( ).getVars( );
        
        for( Condition condition : conditions.getMainConditions() )
            if( !evaluation ){
            	if ( condition.getType() == Condition.FLAG_CONDITION ){
            		evaluation = condition.isActiveState() == flags.isActiveFlag( condition.getId( ) );	
            	} else if ( condition.getType() == Condition.VAR_CONDITION ){
            		VarCondition varCondition = (VarCondition)condition;
            		int actualValue = vars.getValue( varCondition.getId());
            		int state = varCondition.getState();
            		int value = varCondition.getValue();
            		evaluation = evaluateVarCondition ( state, value, actualValue );
            	} else if (condition.getType() == Condition.GLOBAL_STATE_CONDITION ){
            		String globalStateId = condition.getId( );
            		GlobalState gs = Game.getInstance( ).getCurrentChapterData( ).getGlobalState( globalStateId );
            		evaluation = new FunctionalConditions ( gs ).allConditionsOk();
            	}
            } 
        
        return evaluation;
    }

}
