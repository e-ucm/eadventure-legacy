package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class stores the state of the flags in the game
 */
public class FlagSummary implements Serializable {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map storing the flags (indexed by a String)
     */
    private Map<String,Flag> flags;
    
    /**
     * Default constructor
     * @param flagNames Arraylist containing the name of the flags on the game
     */
    public FlagSummary( List<String> flagNames ) {
        flags = new HashMap<String,Flag>( );
        
        for( String name : flagNames ) {
            Flag flag = new Flag(name,false,false);
            flags.put( name,flag );
        }
    }
    
    /**
     * Deactivates a flag
     * @param flagName Name of the flag to be deactivated
     */
    public void deactivateFlag( String flagName ) {
        Flag flag = flags.get( flagName );
        flag.deactivate();
    }

    /**
     * Activates a flag
     * @param flagName Name of the flag to be activated
     */
    public void activateFlag( String flagName ) {
        Flag flag = flags.get( flagName );
        flag.activate();
    }

    /**
     * Returns if a flag is active
     * @param flagName Name of the flag
     * @return True if the flag is active, false otherwise
     */
    public boolean isActiveFlag( String flagName ) {
        boolean activeFlag = false;
        Flag flag = flags.get( flagName );
        if (flag != null) {
            activeFlag = flag.isActive( );
        }
        return activeFlag;
    }

    /**
     * Method maintained to avoid breaking savegames
     */
    public ArrayList<String> getActiveFlags( ) {
        ArrayList<String> activeFlags = new ArrayList<String>();
        Set<String> keys = flags.keySet( );
        for( String key : keys ) {
            Flag flag = flags.get( key );
            if(flag.isActive( )) {
                activeFlags.add( key );
            }
        }
        return activeFlags;
    }
    /**
     * Method maintained to avoid breaking savegames
     */
    public ArrayList<String> getInactiveFlags( ) {
        ArrayList<String> inactiveFlags = new ArrayList<String>();
        Set<String> keys = flags.keySet( );
        for( String key : keys ) {
            Flag flag = flags.get( key );
            if(!flag.isActive( )) {
                inactiveFlags.add( key );
            }
        }
        return inactiveFlags;
    }

    /**
     * Private class, contains a single flag 
     */
    private class Flag {
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
         * @param name Name of the flag
         * @param active State of the flag
         * @param external Tells if the flag is external
         */
        Flag(String name, boolean active, boolean external) {
            this.name = name;
            this.active = active;
            this.external = external;
        }
        
        /**
         * Returns if the flag is active
         * @return True if the flag is active, false otherwise
         */
        public boolean isActive( ) {
            return active;
        }

        /**
         * Activates the flag
         */
        void activate() {
            active = true;
        }

        /**
         * Deactivates the flag 
         */
        void deactivate() {
            active = false;
        }
        
        /**
         * Returns if the flag is external
         * @return True if the flag is external, false otherwise
         */
        boolean isExternal() {
            return external;
        }
    }    
}
