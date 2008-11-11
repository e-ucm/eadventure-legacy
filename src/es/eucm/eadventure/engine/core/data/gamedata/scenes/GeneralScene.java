package es.eucm.eadventure.engine.core.data.gamedata.scenes;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;

/**
 * This class holds the data of a scene of any type in eAdventure
 */
public abstract class GeneralScene {

    /**
     * A regular eAdventure scene
     */
    public static final int SCENE = 0;

    /**
     * A scene of type "videoscene"
     */
    public static final int VIDEOSCENE = 1;

    /**
     * A scene of type "slidescene"
     */
    public static final int SLIDESCENE = 2;

    /**
     * Type of the scene
     */
    private int type;

    /**
     * Id of the scene
     */
    private String id;

    /**
     * Name of the scene
     */
    private String name;

    /**
     * Stores if the scene should be loaded at the begining
     */
    private boolean initialScene;
    
    /**
     * List of resources for the scene
     */
    private ArrayList<Resources> resources;

    /**
     * Creates a new GeneralScene
     * @param type the type of the scene
     * @param id the id of the scene
     */
    public GeneralScene( int type, String id ) {
        this.type = type;
        this.id = id;
        name = null;
        initialScene = false;
        resources = new ArrayList<Resources>( );
    }

    /**
     * Returns the type of the scene
     * @return the type of the scene
     */
    public int getType( ) {
        return type;
    }

    /**
     * Returns the id of the scene
     * @return the id of the scene
     */
    public String getId( ) {
        return id;
    }

    /**
     * Returns the name of the scene
     * @return the name of the scene
     */
    public String getName( ) {
        return name;
    }

    /**
     * Returns whether this is the initial scenene
     * @return true if this is the initial scnene, false otherwise
     */
    public boolean isInitialScene( ) {
        return initialScene;
    }
    
    /**
     * Returns the list of resources of this scene
     * @return the list of resources of this scene
     */
    public ArrayList<Resources> getResources( ) {
        return resources;
    }

    /**
     * Changes the name of this scene
     * @param name the new name
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Changes whether this is the initial scene
     * @param initialScene true if this is the initial scene, false otherwise
     */
    public void setInitialScene( boolean initialScene ) {
        this.initialScene = initialScene;
    }

    /**
     * Adds some resources to the list of resources
     * @param resources the resources to add
     */
    public void addResources( Resources resources ) {
        this.resources.add( resources );
    }
}
