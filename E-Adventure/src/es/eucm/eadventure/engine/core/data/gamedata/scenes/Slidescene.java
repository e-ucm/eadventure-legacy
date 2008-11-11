package es.eucm.eadventure.engine.core.data.gamedata.scenes;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.NextScene;

/**
 * This class holds the data of a slidescene in eAdventure
 */
public class Slidescene extends GeneralScene {

    /**
     * The tag for the slides
     */
    public static final String RESOURCE_TYPE_SLIDES = "slides";

    /**
     * List of nextscenes of the slidescene
     */
    private ArrayList<NextScene> nextScenes;

    /**
     * Stores if the slidescene should end the game
     */
    private boolean endScene;

    /**
     * Creates a new Slidescene
     * @param id the id of the slidescene
     */
    public Slidescene( String id ) {
        super( GeneralScene.SLIDESCENE, id );

        nextScenes = new ArrayList<NextScene>( );
        endScene = false;
    }

    /**
     * Returns the list of next scenes
     * @return the list of next scenes
     */
    public ArrayList<NextScene> getNextScenes( ) {
        return nextScenes;
    }

    /**
     * Adds a next scene to the list of next scenes
     * @param nextScene the next scene to add
     */
    public void addNextScene( NextScene nextScene ) {
        nextScenes.add( nextScene );
    }

    /**
     * Returns whether the displayed slide is the last in the slidescene 
     * @return true if the displayed slide is the last, false otherwise
     */
    public boolean isEndScene( ) {
        return endScene;
    }

    /**
     * Chages whether the displayed slide is the last in the slidescene
     * @param endScene true if the displayed slide is the last, false otherwise
     */
    public void setEndScene( boolean endScene ) {
        this.endScene = endScene;
    }
}
