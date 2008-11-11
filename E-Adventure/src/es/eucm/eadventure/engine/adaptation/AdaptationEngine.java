package es.eucm.eadventure.engine.adaptation;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.loader.Loader;

/**
 * This class manages the adaptation engine, responsible of initializing the flags in the game
 */
public class AdaptationEngine {
    
    public static final String INITIAL_STATE = "Initial";
    public static final String ADAPTATION_RULES = "Adaptation";
    
    
    /**
     * Adaptation data.
     */
    private AdaptedState initialAdaptedState;
    private List<AdaptationRule> externalAdaptationRules;
    private Set<String> externalPropertyNames;
    
    /**
     * Adaptation clock.
     */
    private AdaptationClock adaptationClock;
    
    /**
     * Loads the adaptation data.
     * @param adaptationPath Path of the file containing the adaptation data
     */
    @SuppressWarnings("unchecked")
    public void init( String adaptationPath ) {
        Map<String,Object> output = Loader.loadAdaptationData( adaptationPath );
        initialAdaptedState = (AdaptedState)output.get( INITIAL_STATE );
        externalAdaptationRules = (List<AdaptationRule>)output.get( ADAPTATION_RULES );
        
        if(initialAdaptedState!=null) {
            Game.getInstance().setAdaptedStateToExecute(initialAdaptedState);
        }
     
        //If we are an applet...
        if(Game.getInstance( ).isAppletMode( )) {
            
            Game.getInstance( ).getComm( ).setAdaptationEngine(this);
            
            //Create a Set with all the properties that should be requested from the server
            externalPropertyNames = new HashSet<String>();
            for(AdaptationRule rule : externalAdaptationRules) {
                for(String name : rule.getPropertyNames()) {
                    externalPropertyNames.add( name );
                }
            }
            
            //Request an initial state and set the clock to ask again later
            requestNewState( );
            adaptationClock = new AdaptationClock( this );
            adaptationClock.start( );
        }
    }
    
    /**
     * Requests a new state from the server.
     */
    void requestNewState( ) {
        Game.getInstance( ).getComm( ).getAdaptedState( externalPropertyNames );
    }
    

    
    /**
     * Receives a new uol state as and checks whether it triggers an adaptation
     * rule. If so, the new state is applied inmediately.
     * 
     * @param uolState A Map containing the state of the external properties in
     * the UoL
     */
    public synchronized void processExternalState(Map<String,String> uolState) {
        for (AdaptationRule r : externalAdaptationRules) {
            if ( r.evaluate( uolState )) {
                //System.out.println("Rule triggered");
                Game.getInstance( ).setAdaptedStateToExecute( r.getAdaptedState( ) );
                
                //The UoL states should be defined to be mutually exclusive
                return;
            }
        }
    }
    
    /**
     * Stops the adaptation clock.
     */
    public void stopAdaptationClock( ) {
        // If there is a clock, stop it
        if( adaptationClock != null )
            adaptationClock.stopClock( );
    }
    

    
}
