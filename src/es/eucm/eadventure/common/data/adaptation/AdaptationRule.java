package es.eucm.eadventure.common.data.adaptation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.eucm.eadventure.common.data.Described;


/**
 * 
 */
public class AdaptationRule implements Cloneable, Described, ContainsAdaptedState {

	//ID
	private String id;
	
	// GameState
    private AdaptedState gameState;
    
    /**
     * List of properties to be set
     */
    private List<UOLProperty> uolState;    
    //Description
    private String description;
    
    public AdaptationRule() {
    	uolState = new ArrayList<UOLProperty>( );
        gameState = new AdaptedState();
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public void addUOLProperty( UOLProperty property ) {
        uolState.add( property );
    }
    
    /**
     * Adds a new UOL property
     * 
     * @param id
     * @param value
     * @param op Operation of comparison between the value of var id in LMS and value
     */
    public void addUOLProperty( String id, String value, String op ) {
        addUOLProperty ( new UOLProperty(id,value,op));
    }
    
    public List<UOLProperty> getUOLProperties( ) {
        return uolState;
    }

    public void setInitialScene( String initialScene ) {
        gameState.setTargetId( initialScene );
        
    }

    public void addActivatedFlag( String flag ) {
        gameState.addActivatedFlag( flag );
        
    }

    public void addDeactivatedFlag( String flag ) {
        gameState.addDeactivatedFlag( flag );
        
    }
    
    public void addVarValue ( String var,String value ){
    	gameState.addVarValue(var, value);
    }

    
    public AdaptedState getAdaptedState() {
        return gameState;
    }

	/**
	 * @return the description
	 */
	public String getDescription( ) {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	public String getId( ) {
		return id;
	}

	public void setId( String generateId ) {
		this.id=generateId;
		
	}
	
    public Set<String> getPropertyNames( ) {
    	Set<String> names = new HashSet<String>();
    	for (UOLProperty property: uolState){
    		names.add( property.getId() );
    	}
        return names;
    }
    
    public String getPropertyValue ( String key ){
    	for (UOLProperty property: uolState){
    		if (property.getId().equals(key)){
    			return property.getValue();
    		}
    	}
    	return null;
    	
    }
    
	public Object clone() throws CloneNotSupportedException {
		AdaptationRule ar = (AdaptationRule) super.clone();
		ar.description = (description != null ? new String(description) : null);
		ar.gameState = (AdaptedState) gameState.clone();
		ar.id = (id != null ? new String(id) : null);
		ar.uolState = new ArrayList<UOLProperty>();
		for (UOLProperty uolp : uolState) {
			ar.uolState.add((UOLProperty) uolp.clone());
		}
		return ar;
	}

	public void setAdaptedState(AdaptedState state) {
		this.gameState = state;
	}
}