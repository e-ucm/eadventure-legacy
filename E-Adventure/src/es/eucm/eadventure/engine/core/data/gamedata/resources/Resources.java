package es.eucm.eadventure.engine.core.data.gamedata.resources;

import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;

/**
 * The list of resources of an element in the game under certain conditions.
 */
public class Resources {
    
    /**
     * Conditions of the resource
     */
    private Conditions conditions;
    
    /**
     * List of single assets stored in the resources structure
     */
    private ArrayList<Asset> assets;
    
    /**
     * Creates a new Resources.
     */
    public Resources( ) {
        conditions = new Conditions( );
        assets = new ArrayList<Asset>( );
    }
    
    /**
     * Returns whether exists an asset of the given type.
     * @param type the type of the asset
     * @return whether exists an asset of the given type
     */
    public boolean existAsset( String type ) {
        boolean existAsset = false;
        
        for( int i = 0; i < assets.size( ) && !existAsset; i++ )
            if( assets.get( i ).getType( ).equals( type ) )
                existAsset = true;
        
        return existAsset;
    }
    
    /**
     * Returns the asset of the given type.
     * @param type the type of the asset
     * @return the asset of the given type
     */
    public Asset getAsset( String type ) {
        Asset asset = null;
        
        for( int i = 0; i < assets.size( ) && asset == null; i++ )
            if( assets.get( i ).getType( ).equals( type ) )
                asset = assets.get( i );
        
        return asset;
    }
    
    /**
     * Returns the path of the asset of the given type.
     * @param type the type of the asset
     * @return the path of the asset of the given type
     */
    public String getAssetPath( String type ) {
        String assetPath = null;
        
        for( int i = 0; i < assets.size( ) && assetPath == null; i++ )
            if( assets.get( i ).getType( ).equals( type ) )
                assetPath = assets.get( i ).getPath( );
        
        return assetPath;
    }

    /**
     * Returns the conditions to the resources.
     * @return the conditions to the resources
     */
    public Conditions getConditions( ) {
        return conditions;
    }
    
    /**
     * Adds an asset to the resources.
     * @param asset the asset to be added
     */
    public void addAsset( Asset asset ) {
        assets.add( asset );
    }

    /**
     * Sets the conditions to the resources.
     * @param conditions the new conditions.
     */
    public void setConditions( Conditions conditions ) {
        this.conditions = conditions;
    }
}
