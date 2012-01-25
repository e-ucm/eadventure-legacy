/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
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
 * This class stores the state of the flags in the game
 */
public class FlagSummary implements Serializable, Cloneable {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map storing the flags (indexed by a String)
     */
    private Map<String, Flag> flags;

    private boolean debug;

    private List<String> changes;

    /**
     * Default constructor
     * 
     * @param flagNames
     *            Arraylist containing the name of the flags on the game
     */
    public FlagSummary( List<String> flagNames, boolean debug ) {

        flags = new HashMap<String, Flag>( );
        if( debug )
            changes = new ArrayList<String>( );
        this.debug = debug;

        for( String name : flagNames ) {
            Flag flag = new Flag( name, false, false );
            flags.put( name, flag );
        }
    }

    /**
     * Deactivates a flag
     * 
     * @param flagName
     *            Name of the flag to be deactivated
     */
    public void deactivateFlag( String flagName ) {

        Flag flag = flags.get( flagName );
        // Controls problems with Load games with less flags than original game.
        // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new flag and use it in some
        // game parts, and later load the "game_0", the last added flags will not appear in it.
        if( flag != null )
            flag.deactivate( );
        if( debug )
            changes.add( flagName );
    }

    /**
     * Activates a flag
     * 
     * @param flagName
     *            Name of the flag to be activated
     */
    public void activateFlag( String flagName ) {

        Flag flag = flags.get( flagName );
        // Controls problems with Load games with less flags than original game.
        // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new flag and use it in some
        // game parts, and later load the "game_0", the last added flags will not appear in it.
        if( flag != null )
            flag.activate( );
        if( debug )
            changes.add( flagName );
    }

    /**
     * Returns if a flag is active
     * 
     * @param flagName
     *            Name of the flag
     * @return True if the flag is active, false otherwise
     */
    public boolean isActiveFlag( String flagName ) {

        boolean activeFlag = false;
        Flag flag = flags.get( flagName );
        // Controls problems with Load games with less flags than original game.
        // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new flag and use it in some
        // game parts, and later load the "game_0", the last added flags will not appear in it.
        if( flag != null ) {
            activeFlag = flag.isActive( );
        }
        return activeFlag;
    }

    /**
     * Method maintained to avoid breaking savegames
     */
    public ArrayList<String> getActiveFlags( ) {

        ArrayList<String> activeFlags = new ArrayList<String>( );
        Set<String> keys = flags.keySet( );
        for( String key : keys ) {
            Flag flag = flags.get( key );
            // Controls problems with Load games with less flags than original game.
            // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new flag and use it in some
            // game parts, and later load the "game_0", the last added flags will not appear in it.
            if( flag != null ) {
                if( flag.isActive( ) ) {
                    activeFlags.add( key );
                }
            }
        }
        return activeFlags;
    }

    /**
     * Looks for "flagName" is in the stored flags
     * 
     * @param flagName
     *            Key to look for
     * @return
     */
    public boolean existFlag( String flagName ) {

        return flags.containsKey( flagName );
    }

    /**
     * Method maintained to avoid breaking savegames
     */
    public ArrayList<String> getInactiveFlags( ) {

        ArrayList<String> inactiveFlags = new ArrayList<String>( );
        Set<String> keys = flags.keySet( );
        for( String key : keys ) {
            Flag flag = flags.get( key );
            // Controls problems with Load games with less flags than original game.
            // if in game edition stage, the user save one game (i.e "game_0"), and later adds a new flag and use it in some
            // game parts, and later load the "game_0", the last added flags will not appear in it.
            if( flag != null ) {
                if( !flag.isActive( ) ) {
                    inactiveFlags.add( key );
                }
            }
        }
        return inactiveFlags;
    }

    /**
     * Private class, contains a single flag
     */
    private class Flag implements Cloneable{

        /**
         * Name of the flag
         */
        String name;

        /**
         * State of the flag
         */
        boolean active;

        /**
         * Stores if the flag is external
         */
        boolean external;

        /**
         * Constructor
         * 
         * @param name
         *            Name of the flag
         * @param active
         *            State of the flag
         * @param external
         *            Tells if the flag is external
         */
        Flag( String name, boolean active, boolean external ) {

            this.name = name;
            this.active = active;
            this.external = external;
        }

        /**
         * Returns if the flag is active
         * 
         * @return True if the flag is active, false otherwise
         */
        public boolean isActive( ) {

            return active;
        }

        /**
         * Activates the flag
         */
        void activate( ) {

            active = true;
        }

        /**
         * Deactivates the flag
         */
        void deactivate( ) {

            active = false;
        }

        /**
         * Returns if the flag is external
         * 
         * @return True if the flag is external, false otherwise
         */
        boolean isExternal( ) {

            return external;
        }
        
        @Override
        public Object clone( ) throws CloneNotSupportedException {
            Flag f = (Flag) super.clone( );
            f.active = active;
            f.external = external;
            f.name = new String(name);
            
            return f;
        }
        
    }

    public Map<String, Flag> getFlags( ) {

        return flags;
    }

    public boolean getFlagValue( String name ) {

        Flag flag = flags.get( name );
        return flag.active;
    }

    public void addFlag( String name ) {

        if( !flags.containsKey( name ) ) {
            Flag flag = new Flag( name, false, false );
            flags.put( name, flag );
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

            Flag flag = flags.get( values[0] );
            if( flag == null )
                return "(" + expression + ")";

            if( flag.isActive( ) )
                return values[1];
            else
                return values[2];
        }
        else
            return "(" + expression + ")";
    }

    /**
     * Copy the value for those Flags which exist in externalFs parameter
     * 
     * @param externalFs
     */
    public void copyValuesOfExistingsKeys(FlagSummary externalFs){
        
       try{ 
        Set<Entry<String,Flag>> set1 = flags.entrySet();
        for(Entry<String,Flag> entry:set1){
            Flag currentFlag = externalFs.getFlagFromKey( entry.getKey( ) );
            // current flag is null if the key doesn'r exists
            if ( currentFlag != null)
                entry.setValue( (Flag)currentFlag.clone( ));
        }
    }
    catch( CloneNotSupportedException e ) {
        e.printStackTrace();
    }
        
    }
    
    
    private Flag getFlagFromKey(String key){
        return flags.get( key );
    }
    
    @Override
    public Object clone( ) throws CloneNotSupportedException {
        FlagSummary fs = (FlagSummary)super.clone( );
        
        if( changes != null ) {
            fs.changes = new ArrayList<String>( );
            for( String aa : changes)
                fs.changes.add( new String(aa) );
        }
        
        if (flags!=null){
            fs.flags = new HashMap<String, Flag>( );
            
            Set<Entry<String,Flag>> set1 = flags.entrySet();
            for(Entry<String,Flag> entry:set1)
                fs.flags.put( new String(entry.getKey()) , (Flag)entry.getValue().clone());
            
            
        }
        
        fs.debug = debug;
        
        return fs;
        

    }
}
