package es.eucm.eadventure.engine.adaptation;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the adaptation data, which includes the flag activation and deactivation values,
 * along with the inital scene of the game 
 */
public class AdaptedState {

    /**
     * Id of the initial scene
     */
    private String initialScene;
    
    /**
     * List of flags to be activated
     */
    private List<String> activatedFlags;
    
    /**
     * List of flags to be deactivated
     */
    private List<String> deactivatedFlags;
    
    /**
     * Constructor
     */
    public AdaptedState( ) {
        initialScene = null;
        activatedFlags = new ArrayList<String>( );
        deactivatedFlags = new ArrayList<String>( );
    }
    
    /**
     * Returns the id of the initial scene
     * @return Id of the initial scene, null if none
     */
    public String getInitialScene( ) {
        return initialScene;
    }
    
    /**
     * Returns the list of the activated flags
     * @return List of the activated flags
     */
    public List<String> getActivatedFlags( ) {
        return activatedFlags;
    }

    /**
     * Returns the list of the deactivated flags
     * @return List of the deactivated flags
     */
    public List<String> getDeactivatedFlags( ) {
        return deactivatedFlags;
    }
    
    /**
     * Sets the initial scene id
     * @param initialScene Id of the initial scene
     */
    public void setInitialScene( String initialScene ) {
        this.initialScene = initialScene;
    }
    
    /**
     * Adds a new flag to be activated
     * @param flag Name of the flag
     */
    public void addActivatedFlag( String flag ) {
        activatedFlags.add( flag );
    }
    
    /**
     * Adds a new flag to be deactivated
     * @param flag Name of the flag
     */
    public void addDeactivatedFlag( String flag ) {
        deactivatedFlags.add( flag );
    }
    
}
