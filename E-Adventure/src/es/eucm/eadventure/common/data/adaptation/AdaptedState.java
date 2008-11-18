package es.eucm.eadventure.common.data.adaptation;

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
    
    public static final String ACTIVATE = "activate";
    
    public static final String DEACTIVATE = "deactivate";
    
    /**
     * List of all flags
     */
    private List<String> allFlags;
    
    /**
     * List of deactivate/activate
     */
    private List<String> actions;
    
    /**
     * Constructor
     */
    public AdaptedState( ) {
        initialScene = null;
        allFlags = new ArrayList<String>( );
        actions = new ArrayList<String>( );
    }
    
    /**
     * Returns the id of the initial scene
     * @return Id of the initial scene, null if none
     */
    public String getInitialScene( ) {
        return initialScene;
    }
    
    /**
     * Returns the list of the deactivated flags
     * @return List of the deactivated flags
     */
    public List<String> getFlags( ) {
        return allFlags;
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
        allFlags.add( flag );
        actions.add( ACTIVATE );
    }
    
    /**
     * Adds a new flag to be deactivated
     * @param flag Name of the flag
     */
    public void addDeactivatedFlag( String flag ) {
        allFlags.add( flag );
        actions.add( DEACTIVATE );
    }
    
    public void removeFlag( int row ){
    	allFlags.remove( row );
    	actions.remove( row );
    }
    
    public void changeFlag (int row, String flag){
    	int nFlags = actions.size( );
    	allFlags.remove( row );
		if (row<nFlags-1)
			allFlags.add( row, flag );
		else
			allFlags.add( flag );

    }
    
    public void changeAction (int row){
    	int nFlags = actions.size( );
    	if (actions.get( row ).equals( ACTIVATE )){
    		actions.remove( row );
    		
    		if (row<nFlags-1)
    			actions.add( row, DEACTIVATE );
    		else
    			actions.add( DEACTIVATE );
    	}
    	
    	else if (actions.get( row ).equals( DEACTIVATE )){
    		actions.remove( row );
    		
    		if (row<nFlags-1)
    			actions.add( row, ACTIVATE );
    		else
    			actions.add( ACTIVATE );
    	}

    }
    
    public String getAction (int i){
    	return actions.get( i );
    }
    
    public String getFlag (int i){
    	return allFlags.get( i );
    }
    
    public boolean isEmpty(){
    	return allFlags.size( )==0 && (initialScene==null || initialScene.equals( "" ));
    }

    /**
     * Returns the list of the activated flags
     * @return List of the activated flags
     */
    public List<String> getActivatedFlags( ) {
    	List<String> activatedFlags = new ArrayList<String>();
    	for ( int i=0; i<actions.size(); i++){
    		if (actions.get(i).equals(ACTIVATE)){
    			activatedFlags.add(allFlags.get(i));
    		}
    	}
        return activatedFlags;
    }

    /**
     * Returns the list of the deactivated flags
     * @return List of the deactivated flags
     */
    public List<String> getDeactivatedFlags( ) {
       	List<String> deactivatedFlags = new ArrayList<String>();
    	for ( int i=0; i<actions.size(); i++){
    		if (actions.get(i).equals(DEACTIVATE)){
    			deactivatedFlags.add(allFlags.get(i));
    		}
    	}    	
        return deactivatedFlags;
    }
    
}
