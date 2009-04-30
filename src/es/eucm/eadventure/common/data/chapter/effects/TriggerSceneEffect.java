package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that triggers a scene
 */
public class TriggerSceneEffect extends AbstractEffect implements HasTargetId {

	/**
	 * Id of the cutscene to be played
	 */
	private String targetSceneId;

	/**
	 * X position of the player in the scene.
	 */
	private int x;

	/**
	 * Y position of the player in the scene.
	 */
	private int y;

	/**
	 * Creates a new TriggerSceneEffect
	 * 
	 * @param targetSceneId
	 *            the id of the cutscene to be triggered
	 * @param x
	 *            X position of the player in the new scene
	 * @param y
	 *            Y position of the player in the new scene
	 */
	public TriggerSceneEffect( String targetSceneId, int x, int y ) {
	    	super();
		this.targetSceneId = targetSceneId;
		this.x = x;
		this.y = y;
	}

	public int getType( ) {
		return TRIGGER_SCENE;
	}

	/**
	 * Returns the targetSceneId
	 * 
	 * @return String containing the targetSceneId
	 */
	public String getTargetId( ) {
		return targetSceneId;
	}

	/**
	 * Sets the new targetSceneId
	 * 
	 * @param targetSceneId
	 *            New targetSceneId
	 */
	public void setTargetId( String targetSceneId ) {
		this.targetSceneId = targetSceneId;
	}

	/**
	 * Returns the X destiny position of the player.
	 * 
	 * @return X destiny position
	 */
	public int getX( ) {
		return x;
	}

	/**
	 * Returns the Y destiny position of the player.
	 * 
	 * @return Y destiny position
	 */
	public int getY( ) {
		return y;
	}

	/**
	 * Sets the new insertion position for the player
	 * 
	 * @param x
	 *            X coord of the position
	 * @param y
	 *            Y coord of the position
	 */
	public void setPosition( int x, int y ) {
		this.x = x;
		this.y = y;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TriggerSceneEffect tse = (TriggerSceneEffect) super.clone();
		tse.targetSceneId = (targetSceneId != null ? new String(targetSceneId) : null);
		tse.x = x;
		tse.y = y;
		return tse;
	}

}