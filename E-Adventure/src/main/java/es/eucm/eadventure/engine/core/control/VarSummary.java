/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * This class stores the state of the vars in the game
 */
public class VarSummary implements Serializable, Cloneable {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map storing the vars (indexed by a String)
     */
    private Map<String, Var> vars;

    /**
     * Default value for Vars
     */
    private static final int DEFAULT_VALUE = 0;

    private boolean debug;

    private List<String> changes;

    /**
     * Default constructor
     * 
     * @param varNames
     *            Arraylist containing the name of the vars on the game
     */
    public VarSummary( List<String> varNames, boolean debug ) {

        vars = new HashMap<String, Var>( );
        if( debug )
            changes = new ArrayList<String>( );
        this.debug = debug;
        for( String name : varNames ) {
            Var var = new Var( name, DEFAULT_VALUE, false );
            vars.put( name, var );
        }
    }

    /**
     * Sets the value of a var
     * 
     * @param varName
     *            Name of the var to be set
     */
    public void setVarValue( String varName, int value ) {

        Var var = vars.get( varName );
        // Controls problems with Load games with less vars than original game.
        // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new var and use it in some
        // game parts, and later load the "game_0", the last added vars will not appear in it.
        if( var != null )
            var.setValue( value );
        if( debug )
            changes.add( varName );
    }

    /**
     * Increments the value of a var
     * 
     * @param varName
     *            Name of the var to be set
     */
    public void incrementVar( String varName, int increment ) {

        Var var = vars.get( varName );
        // Controls problems with Load games with less vars than original game.
        // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new var and use it in some
        // game parts, and later load the "game_0", the last added vars will not appear in it.
        if( var != null )
            var.increment( increment );
        if( debug )
            changes.add( varName );
    }

    /**
     * Decrements the value of a var
     * 
     * @param varName
     *            Name of the var to be set
     */
    public void decrementVar( String varName, int decrement ) {

        Var var = vars.get( varName );
        // Controls problems with Load games with less vars than original game.
        // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new var and use it in some
        // game parts, and later load the "game_0", the last added vars will not appear in it.
        if( var != null )
            var.decrement( decrement );
        if( debug )
            changes.add( varName );
    }

    /**
     * Looks for "varName" is in the stored vars
     * 
     * @param varName
     *            Key to look for
     * @return
     */
    public boolean existVar( String varName ) {

        return vars.containsKey( varName );
    }

    /**
     * Returns the value of a variable
     * 
     * @param varName
     * @return
     */
    public int getValue( String varName ) {

        Var var = vars.get( varName );
        if( var != null )
            return var.getValue( );
        else
            return 0;
    }

    /**
     * Returns an array with all the names of the vars (for printing)
     * 
     * @return
     */
    public String[] getVarNames( ) {

        return vars.keySet( ).toArray( new String[] {} );
    }

    /**
     * Returns an array with all the values of the vars in String format (for
     * printing)
     * 
     * @return
     */
    public String[] getVarValues( ) {

        String[] names = getVarNames( );
        String[] values = new String[ names.length ];
        for( int i = 0; i < names.length; i++ ) {
            // Controls problems with Load games with less vars than original game.
            // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new var and use it in some
            // game parts, and later load the "game_0", the last added vars will not appear in it.
            if( vars.get( names[i] ) != null )
                values[i] = Integer.toString( vars.get( names[i] ).getValue( ) );
        }
        return values;
    }

    /**
     * Private class, contains a single var
     */
    private class Var implements Cloneable {

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
         * 
         * @param name
         *            Name of the flag
         * @param state
         *            State of the flag
         * @param external
         *            Tells if the flag is external
         */
        Var( String name, int value, boolean external ) {

            this.name = name;
            this.value = value;
            this.external = external;
        }

        /**
         * Returns the value of the var
         * 
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
         * 
         * @return True if the var is external, false otherwise
         */
        boolean isExternal( ) {

            return external;
        }
        
        @Override
        public Object clone( ) throws CloneNotSupportedException {
            Var v = (Var) super.clone( );
            v.value = value;
            v.external = external;
            v.name = new String(name);
            
            return v;
        }
    }
    
    /**
     * Copy the value for those Flags which exist in externalFs parameter
     * 
     * @param externalFs
     */
    public void copyValuesOfExistingsKeys(VarSummary externalVs){
        try {
        Set<Entry<String,Var>> set1 = vars.entrySet();
        for(Entry<String,Var> entry:set1){
            Var currentVar= externalVs.getVarFromKey( entry.getKey( ) );
            // current flag is null if the key doesn't exists
            if ( currentVar != null)
                
                    entry.setValue( (Var)currentVar.clone( ) );
                }
                
        }
        catch( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        
    }
    
    private Var getVarFromKey(String key){
        return vars.get( key );
    }
    

    public Map<String, Var> getVars( ) {

        return this.vars;
    }

    public int getVarValue( String name ) {

        Var var = vars.get( name );
        return var.getValue( );
    }

    public void addVar( String name ) {

        if( !vars.containsKey( name ) ) {
            Var var = new Var( name, DEFAULT_VALUE, false );
            vars.put( name, var );
        }
    }

    public List<String> getChanges( ) {

        if( debug ) {
            List<String> temp = new ArrayList<String>( changes );
            changes.clear( );
            return temp;
        }
        return new ArrayList<String>( );
    }

    public String processText( String text ) {

        String newText = "";
        if (text!=null){ 
        String[] parts = text.split( "\\(" );
        if( parts.length == 1 )
            return text;

        for( int i = 0; i < parts.length; i++ ) {
            String part = parts[i];
            if( part.length( ) > 0 && part.charAt( 0 ) == '#' ) {
                String[] parts2 = part.split( "\\)" );

                parts2[0] = evaluateExpression( parts2[0] );

                parts[i] = parts2[0];
                for( int j = 1; j < parts2.length; j++ ) {
                    parts[i] += parts2[j];
                }

            }
            else if( i > 0 ) {
                parts[i] = "(" + part;
            }

            newText += parts[i];
        }
        }

        return newText;
    }

    public String evaluateExpression( String expression ) {

        if( expression.contains( "?" ) && expression.contains( ":" ) ) {
            String[] values = expression.substring( 1 ).split( "\\?|\\:" );

            if( values.length != 3 )
                return "(" + expression + ")";

            int op = 0;
            String[] comparison = new String[] { new String( values[0] ) };
            if( values[0].contains( ">=" ) ) {
                op = 1;
                comparison = values[0].split( ">=" );
            }
            else if( values[0].contains( ">" ) ) {
                op = 2;
                comparison = values[0].split( ">" );
            }
            else if( values[0].contains( "<=" ) ) {
                op = 3;
                comparison = values[0].split( "<=" );
            }
            else if( values[0].contains( "<" ) ) {
                op = 4;
                comparison = values[0].split( "<" );
            }
            else if( values[0].contains( "==" ) ) {
                op = 5;
                comparison = values[0].split( "==" );
            }

            for( int i = 0; i < comparison.length; i++ ) {
                comparison[i].replaceAll( " ", "" );
            }

            if( op == 0 ) {
                return "(" + expression + ")";
            }
            else {
                int numValue = 0;
                try {
                    numValue = Integer.parseInt( comparison[1] );
                }
                catch( NumberFormatException e ) {
                    return "(" + expression + ")";
                }
                Var var = vars.get( comparison[0] );
                if( var == null )
                    return "(" + expression + ")";
                int varValue = var.getValue( );
                if( op == 1 ) {
                    if( varValue >= numValue )
                        return values[1];
                    else
                        return values[2];
                }
                else if( op == 2 ) {
                    if( varValue > numValue )
                        return values[1];
                    else
                        return values[2];
                }
                else if( op == 3 ) {
                    if( varValue <= numValue )
                        return values[1];
                    else
                        return values[2];
                }
                else if( op == 4 ) {
                    if( varValue < numValue )
                        return values[1];
                    else
                        return values[2];
                }
                else if( op == 5 ) {
                    if( varValue == numValue )
                        return values[1];
                    else
                        return values[2];
                }
                return "(" + expression + ")";
            }
        }
        else {
            String varname = expression.substring( 1 );
            Var var = vars.get( varname );
            if( var != null )
                return "" + var.getValue( );
            return "(" + expression + ")";
        }
    }
    
    
    @Override
    public Object clone( ) throws CloneNotSupportedException {
        VarSummary vs = (VarSummary)super.clone( );
        
        if( changes != null ) {
            vs.changes = new ArrayList<String>( );
            for( String aa : changes)
                vs.changes.add( new String(aa) );
        }
        
        if (vars !=null){
            vs.vars = new HashMap<String, Var>( );
            
            Set<Entry<String,Var>> set1 = vars.entrySet();
            for(Entry<String,Var> entry:set1)
                vs.vars.put( new String(entry.getKey()) , (Var)entry.getValue().clone());
            
            
        }
        
        vs.debug = debug;
        
        return vs;
    }

}
