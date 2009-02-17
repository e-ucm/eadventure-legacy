package es.eucm.eadventure.common.data.chapter.resources;

import java.util.HashMap;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * The list of resources of an element in the game under certain conditions.
 */
public class Resources implements Cloneable {

	/**
	 * Conditions of the resource
	 */
	private Conditions conditions;

	/**
	 * List of single assets stored in the resources structure
	 */
	private HashMap<String, String> assets;

	/**
	 * Creates a new Resources.
	 */
	public Resources( ) {
		assets = new HashMap<String, String>( );
		conditions = new Conditions( );
	}

	/**
	 * Inserts a new asset in the resources.
	 * 
	 * @param type
	 *            Identifier of the asset
	 * @param path
	 *            Path of the asset
	 */
	public void addAsset( String type, String path ) {
		// Remove the asset (if it was present), and insert the new one
		deleteAsset( type );
		assets.put( type, path );
	}
	
    /**
     * Adds an asset to the resources.
     * @param asset the asset to be added
     */
    public void addAsset( Asset asset ) {
    	addAsset ( asset.getType(), asset.getPath() );
    }

	/**
	 * Deletes the given asset from the resources block.
	 * 
	 * @param type
	 *            Identifier of the asset
	 */
	public void deleteAsset( String type ) {
		assets.remove( type );
	}

	/**
	 * Returns the number of assets contained.
	 * 
	 * @return Number of assets
	 */
	public int getAssetCount( ) {
		return assets.size( );
	}

	/**
	 * Returns an array with all the types of the resources.
	 * 
	 * @return Array with the resources types
	 */
	public String[] getAssetTypes( ) {
		return assets.keySet( ).toArray( new String[] {} );
	}

	/**
	 * Returns an array with all the values of the resources.
	 * 
	 * @return Array with the resources values
	 */
	public String[] getAssetValues( ) {
		return assets.values( ).toArray( new String[] {} );
	}

	/**
	 * Returns the path associated with the given type.
	 * 
	 * @param type
	 *            Identifier of the asset
	 * @return Path to the asset if present, null otherwise
	 */
	public String getAssetPath( String type ) {
		return assets.get( type );
	}

	/**
	 * Returns the conditions to the resources.
	 * 
	 * @return the conditions to the resources
	 */
	public Conditions getConditions( ) {
		return conditions;
	}

	/**
	 * Sets the conditions to the resources.
	 * 
	 * @param conditions
	 *            the new conditions.
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}
	
    /**
     * Returns whether exists an asset of the given type.
     * @param type the type of the asset
     * @return whether exists an asset of the given type
     */
    public boolean existAsset( String type ) {
        boolean existAsset = false;
        
        existAsset = assets.containsKey(type);
        
        return existAsset;
    }
    
	public Object clone() throws CloneNotSupportedException {
		Resources r = (Resources) super.clone();
		if (assets != null) {
			r.assets = new HashMap<String, String>();
			for (String s : assets.keySet()) {
				String s2 = (assets.get(s) != null ? new String(assets.get(s)) : null);
				r.assets.put(new String(s), s2);
			}
		}
		r.conditions = (conditions != null ? (Conditions) conditions.clone() : null);
		return r;
	}
	

}
