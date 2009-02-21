package es.eucm.eadventure.common.data.adaptation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * Stores the adaptation data, which includes the flag activation and deactivation values,
 * along with the inital scene of the game 
 */
public class AdaptedState implements Cloneable, HasTargetId {

    /**
     * Id of the initial scene
     */
    private String initialScene;
    
    public static final String ACTIVATE = "activate";
    
    public static final String DEACTIVATE = "deactivate";
    
    /**
     * List of all flags and vars (in order)
     */
    private List<String> allFlagsVars;
    
    /**
     * List of deactivate/activate for flags (and value for vars)
     */
    private List<String> actionsValues;
    
    /**
     * Constructor
     */
    public AdaptedState( ) {
        initialScene = null;
        allFlagsVars = new ArrayList<String>( );
        actionsValues = new ArrayList<String>( );
    }
    
    /**
     * Returns the id of the initial scene
     * @return Id of the initial scene, null if none
     */
    public String getTargetId( ) {
        return initialScene;
    }
    
    /**
     * Returns the list of flags and vars
     * @return List of the deactivated flags
     */
    public List<String> getFlagsVars( ) {
        return allFlagsVars;
    }
    
    /**
     * Sets the initial scene id
     * @param initialScene Id of the initial scene
     */
    public void setTargetId( String initialScene ) {
        this.initialScene = initialScene;
    }
    
    /**
     * Adds a new flag to be activated
     * @param flag Name of the flag
     */
    public void addActivatedFlag( String flag ) {
        allFlagsVars.add( flag );
        actionsValues.add( ACTIVATE );
    }
    
    /**
     * Adds a new flag to be deactivated
     * @param flag Name of the flag
     */
    public void addDeactivatedFlag( String flag ) {
        allFlagsVars.add( flag );
        actionsValues.add( DEACTIVATE );
    }

    /**
     * Adds a new var
     * @param var
     * @param value
     */
    public void addVarValue ( String var, int value ){
    	allFlagsVars.add( var );
    	actionsValues.add( Integer.toString(value) );
    }
    
    public void removeFlagVar( int row ){
    	allFlagsVars.remove( row );
    	actionsValues.remove( row );
    }
    
    public void changeFlag (int row, String flag){
    	int nFlags = actionsValues.size( );
    	allFlagsVars.remove( row );
		if (row<nFlags-1)
			allFlagsVars.add( row, flag );
		else
			allFlagsVars.add( flag );

    }
    
    public void changeAction (int row){
    	int nFlags = actionsValues.size( );
    	if (actionsValues.get( row ).equals( ACTIVATE )){
    		actionsValues.remove( row );
    		
    		if (row<nFlags-1)
    			actionsValues.add( row, DEACTIVATE );
    		else
    			actionsValues.add( DEACTIVATE );
    	}
    	
    	else if (actionsValues.get( row ).equals( DEACTIVATE )){
    		actionsValues.remove( row );
    		
    		if (row<nFlags-1)
    			actionsValues.add( row, ACTIVATE );
    		else
    			actionsValues.add( ACTIVATE );
    	}

    }
    
    public void changeValue ( int row, int newValue ){
    	if ( row>=0 && row<=actionsValues.size() ){
    		actionsValues.remove( row );
    		actionsValues.add( row, Integer.toString(newValue) );
    	}
    }
    
    public String getAction (int i){
    	return actionsValues.get( i );
    }
    
    public int getValue (int i){
    	return Integer.parseInt(actionsValues.get(i));
    }
    
    public String getFlagVar (int i){
    	return allFlagsVars.get( i );
    }
    
    public boolean isEmpty(){
    	return allFlagsVars.size( )==0 && (initialScene==null || initialScene.equals( "" ));
    }

    /**
     * Returns the list of the activated flags
     * @return List of the activated flags
     */
    public List<String> getActivatedFlags( ) {
    	List<String> activatedFlags = new ArrayList<String>();
    	for ( int i=0; i<actionsValues.size(); i++){
    		if (actionsValues.get(i).equals(ACTIVATE)){
    			activatedFlags.add(allFlagsVars.get(i));
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
    	for ( int i=0; i<actionsValues.size(); i++){
    		if (actionsValues.get(i).equals(DEACTIVATE)){
    			deactivatedFlags.add(allFlagsVars.get(i));
    		}
    	}    	
        return deactivatedFlags;
    }
    
    /**
     * Fills the argumented structures with the names of the vars and the values they must be set with
     * @param vars
     * @param values
     */
    public void getVarsValues ( List<String> vars, List<Integer> values){
    	for ( int i=0; i<actionsValues.size(); i++){
    		if ( !actionsValues.get(i).equals(ACTIVATE) && !actionsValues.get(i).equals(DEACTIVATE) ){
    			vars.add(allFlagsVars.get(i));
    			values.add(new Integer(actionsValues.get(i)));
    		}
    	}
    }
    
    /**
     *	Joins two Adapted States. The new resulting adapted state has a merge of active/inactive flags of both states, 
     * 	and the initial scene will be set as the initial scene of the parameter state. With the vars, its do the same action.
     *  The new flags/vars will be add at the end of the data structure;
     * 
     * 	@param AdaptedState mergeState
     * 					The state which will be merged with the current object
     * 
     */
    public void merge(AdaptedState mergeState){
    	
    	if (mergeState.initialScene != null)
    		this.initialScene = mergeState.initialScene;
    	if (this.allFlagsVars.size()==0){
    		this.allFlagsVars = mergeState.allFlagsVars;
    		this.actionsValues = mergeState.actionsValues;
    	}
    	else {
    	for (int i=0; i<mergeState.allFlagsVars.size();i++){
    		this.allFlagsVars.add(mergeState.allFlagsVars.get(i));
    		this.actionsValues.add(mergeState.allFlagsVars.get(i));
    	}
    	}
    }

	public Object clone() throws CloneNotSupportedException {
		AdaptedState as = (AdaptedState) super.clone();
		as.actionsValues = new ArrayList<String>();
		for (String s : actionsValues)
			as.actionsValues.add((s != null ? new String(s) : null));
		as.allFlagsVars = new ArrayList<String>();
		for (String s : allFlagsVars)
			as.allFlagsVars.add((s != null ? new String(s) : null));
		as.initialScene = (initialScene != null ? new String(initialScene) : null);
		return as; 
	}
} 
