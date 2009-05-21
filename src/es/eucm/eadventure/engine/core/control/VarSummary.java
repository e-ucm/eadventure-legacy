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
        if (debug)
        	changes.add(varName);
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
     * Looks for "varName" is in the stored vars
     * 
     * @param varName
     * 		Key to look for
     * @return
     */
    public boolean existVar(String varName){
	return vars.containsKey(varName);
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
		return new ArrayList<String>();
	}

	public String processText(String text) {
		String newText = "";
		
		String[] parts = text.split("\\(");
		if (parts.length == 1)
			return text;
		
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			if (part.length() > 0 && part.charAt(0) == '#') {
				String[] parts2 = part.split("\\)");

				parts2[0] = evaluateExpression(parts2[0]);
				
				parts[i] = parts2[0];
				for (int j = 1; j < parts2.length; j++) {
					parts[i] += parts2[j];
				}
				
			} else if (i > 0){
				parts[i] = "(" + part;
			}
			
			newText += parts[i];
		}
		
		
		return newText;
	}
	
	public String evaluateExpression(String expression) {
		if (expression.contains("?") && expression.contains(":")) {
			String[] values = expression.substring(1).split("\\?|\\:");
			
			if (values.length != 3)
				return "(" + expression + ")";
			
			int op = 0;
			String[] comparison = new String[]{new String(values[0])};
			if (values[0].contains(">=")) {
				op = 1;
				comparison = values[0].split(">=");
			} else if (values[0].contains(">")) {
				op = 2;
				comparison = values[0].split(">");
			} else if (values[0].contains("<=")) {
				op = 3;
				comparison = values[0].split("<=");
			} else if (values[0].contains("<")) {
				op = 4;
				comparison = values[0].split("<");
			} else if (values[0].contains("==")) {
				op = 5;
				comparison = values[0].split("==");
			}
			
			for (int i = 0; i < comparison.length; i++) {
				comparison[i].replaceAll(" ", "");
			}
			
			if (op == 0) {
				return "(" + expression + ")";
			} else {
				int numValue = 0;
				try {
					numValue = Integer.parseInt(comparison[1]);
				} catch (NumberFormatException e) {
					return "(" + expression + ")";
				}
				Var var = vars.get(comparison[0]);
				if (var == null)
					return "(" + expression + ")";
				int varValue = var.getValue();
				if (op == 1) {
					if (varValue >= numValue)
						return values[1];
					else
						return values[2];
				} else if (op == 2) {
					if (varValue > numValue)
						return values[1];
					else
						return values[2];
				} else if (op == 3) {
					if (varValue <= numValue)
						return values[1];
					else
						return values[2];
				} else if (op == 4) {
					if (varValue < numValue)
						return values[1];
					else
						return values[2];
				} else if (op == 5) {
					if (varValue == numValue)
						return values[1];
					else
						return values[2];
				}
				return "(" + expression + ")";
			}
		} else {
			String varname = expression.substring(1);
			Var var = vars.get(varname);
			if (var != null)
				return "" + var.getValue();
			return "(" + expression + ")";
		}
	}
	
}
