package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class stores the state of the vars in the game
 */
public class VarSummary implements Serializable {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map storing the vars (indexed by a String)
     */
    private Map<String,Var> vars;
    
    /**
     * Default value for Vars
     */
    private static final int DEFAULT_VALUE = 0;
    
    private boolean debug;
    
    private List<String> changes;
    
    /**
     * Default constructor
     * @param varNames Arraylist containing the name of the vars on the game
     */
    public VarSummary( List<String> varNames, boolean debug ) {
        vars = new HashMap<String,Var>( );
        if (debug)
        	changes = new ArrayList<String>();
        this.debug = debug;
        for( String name : varNames ) {
            Var var = new Var(name,DEFAULT_VALUE,false);
            vars.put( name,var );
        }
    }
    
    /**
     * Sets the value of a var
     * @param varName Name of the var to be set
     */
    public void setVarValue( String varName, int value ) {
        Var var = vars.get( varName );
        var.setValue(value);
    }

    /**
     * Increments the value of a var
     * @param varName Name of the var to be set
     */
    public void incrementVar( String varName, int increment ) {
        Var var = vars.get( varName );
        var.increment(increment);
        if (debug)
        	changes.add(varName);
    }

    /**
     * Decrements the value of a var
     * @param varName Name of the var to be set
     */
    public void decrementVar( String varName, int decrement ) {
        Var var = vars.get( varName );
        var.decrement(decrement);
        if (debug)
        	changes.add(varName);
    }
    
    /**
     * Returns the value of a variable
     * @param varName
     * @return
     */
    public int getValue ( String varName ){
    	Var var = vars.get( varName );
    	return var.getValue();
    }
    
    /**
     * Returns an array with all the names of the vars (for printing)
     * @return
     */
	public String[] getVarNames() {
		return vars.keySet().toArray(new String[]{});
	}

	/**
	 * Returns an array with all the values of the vars in String format (for printing)
	 * @return
	 */
	public String[] getVarValues() {
		String[] names = getVarNames();
		String[] values = new String[names.length];
		for (int i=0; i<names.length; i++){
			values[i] = Integer.toString(vars.get(names[i]).getValue());
		}
		return values;
	}   
    
    /**
     * Private class, contains a single var 
     */
    private class Var {
        /**
         * Name of the var
         */
        String name;
        
        /**
         * Value of the var
         */
        int value;
        
        /**
         * Stores if the var is external
         */
        boolean external;
        
        /**
         * Constructor
         * @param name Name of the flag
         * @param state State of the flag
         * @param external Tells if the flag is external
         */
        Var(String name, int value, boolean external) {
            this.name = name;
            this.value = value;
            this.external = external;
        }
        
        /**
         * Returns the value of the var
         * @return 
         */
        public int getValue( ) {
            return value;
        }

        /**
         * Sets the value of the var
         */
        void setValue( int value ) {
            this.value = value;
        }

        /**
         * Increments the value of the var
         */
        void increment( int increment ) {
            this.value += increment;
        }
        
        /**
         * Decrements the value of the var
         */
        void decrement( int decrement ) {
            this.value -= decrement;
        }

        
        /**
         * Returns if the var is external
         * @return True if the var is external, false otherwise
         */
        boolean isExternal() {
            return external;
        }
    }
    
    public Map<String, Var> getVars() {
    	return this.vars;
    }
    
    public int getVarValue(String name) {
    	Var var = vars.get(name);
    	return var.getValue();
    }

	public void addVar(String name ) {
		if (!vars.containsKey(name)){
			Var var = new Var(name,DEFAULT_VALUE,false);
			vars.put( name,var );
		}
	}    

	public List<String> getChanges() {
		if (debug) {
			List<String> temp = new ArrayList<String>(changes);
			changes.clear();
			return temp;
		}
		return null;
	}
 
}
