package es.eucm.eadventure.common.data.chapter.scenes;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.NextScene;

public abstract class Cutscene extends GeneralScene {

	/**
	 * List of nextscenes of the slidescene
	 */
	private List<NextScene> nextScenes;

	/**
	 * Stores if the slidescene should end the game
	 */
	private boolean endScene;

	/**
	 * Creates a new cutscene
	 * 
	 * @param type
	 *            The type of the scene
	 * @param id
	 *            The id of the scene
	 */
	protected Cutscene( int type, String id ) {
		super( type, id );

		nextScenes = new ArrayList<NextScene>( );
		endScene = false;
	}

	/**
	 * Returns the list of next scenes
	 * 
	 * @return the list of next scenes
	 */
	public List<NextScene> getNextScenes( ) {
		return nextScenes;
	}

	/**
	 * Adds a next scene to the list of next scenes
	 * 
	 * @param nextScene
	 *            the next scene to add
	 */
	public void addNextScene( NextScene nextScene ) {
		nextScenes.add( nextScene );
	}

	/**
	 * Returns whether the cutscene ends the chapter.
	 * 
	 * @return True if the cutscene ends the chapter, false otherwise
	 */
	public boolean isEndScene( ) {
		return endScene;
	}

	/**
	 * Changes whether the cutscene ends the chapter.
	 * 
	 * @param endScene
	 *            True if the cutscene ends the chapter, false otherwise
	 */
	public void setEndScene( boolean endScene ) {
		this.endScene = endScene;
	}
}
