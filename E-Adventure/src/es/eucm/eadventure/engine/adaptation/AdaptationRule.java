package es.eucm.eadventure.engine.adaptation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class AdaptationRule {


    private AdaptedState gameState;
    private Map<String,String> uolState;
    
    public AdaptationRule() {
        uolState = new HashMap<String,String>();
        gameState = new AdaptedState();
    }
    
    public void addProperty(String id, String value) {
        uolState.put( id, value );
    }
    
    public boolean evaluate(Map<String,String> currentState) {
        boolean activated = true;
        
        Iterator<String> keysIt = uolState.keySet().iterator( );
        while(activated && keysIt.hasNext( )) {
            String key = keysIt.next( );
            try {
                String propertyInUoL = currentState.get( key );
                String propertyInRule = uolState.get( key );
                //System.out.print("Comparing " + propertyInUoL + " with "+ propertyInRule);
                activated = currentState.get( key ).equals( uolState.get( key ) );
                if(activated){
                    //System.out.println(" TRUE");
                } else {
                    //System.out.println(" FALSE");
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("The external state does not reflect all relevant properties: Property " + key + " not found.");
            }
        }
        if(activated){
           // System.out.println("Rule returns with TRUE");
        } else {
           // System.out.println("Rule returns with FALSE");
        }
        return activated;
    }

    public void setInitialScene( String initialScene ) {
        gameState.setInitialScene( initialScene );
        
    }

    public void addActivatedFlag( String flag ) {
        gameState.addActivatedFlag( flag );
        
    }

    public void addDeactivatedFlag( String flag ) {
        gameState.addDeactivatedFlag( flag );
        
    }

    public Set<String> getPropertyNames( ) {
        return uolState.keySet( );
    }
    
    public AdaptedState getAdaptedState() {
        return gameState;
    }
    
}