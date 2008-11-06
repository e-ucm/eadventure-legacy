package es.eucm.eadventure.engine.core.data.gamedata.scenes;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.NextScene;

/**
 * This class holds the data of a videoscene in eAdventure
 */
public class Videoscene extends GeneralScene {

    /**
     * The tag for the video
     */
    public static final String RESOURCE_TYPE_VIDEO = "video";
    
    /**
     * List of nextscenes of the slidescene
     */
    private ArrayList<NextScene> nextScenes;

    /**
     * Stores if the slidescene should end the game
     */
    private boolean endScene;

    /**
     * Creates a new Videoscene
     * @param id the id of the videoscene
     */
    public Videoscene( String id ) {
        super( GeneralScene.VIDEOSCENE, id );

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
     * Returns whether the videoscene has ended
     * @return true if the videoscene has ended, false otherwise
     */
    public boolean isEndScene( ) {
        return endScene;
    }

    /**
     * Changes whether the videoscene has ended
     * @param endScene true if the videoscene has ended, false otherwise
     */
    public void setEndScene( boolean endScene ) {
        this.endScene = endScene;
    }
}
