package es.eucm.eadventure.common.data.chapterdata;

import es.eucm.eadventure.common.data.chapterdata.ExitLook;
import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;
import es.eucm.eadventure.common.data.chapterdata.effects.Effects;

/**
 * This class holds the data of a next scene in eAdventure
 */
public class NextScene {

	/**
	 * Id of the target scene
	 */
	private String nextSceneId;

	/**
	 * X position in which the player should appear in the new scene
	 */
	private int destinyX;

	/**
	 * Y position in which the player should appear in the new scene
	 */
	private int destinyY;

	/**
	 * Conditions of the next scene
	 */
	private Conditions conditions;

	/**
	 * FunctionalEffects triggered before exiting the current scene
	 */
	private Effects effects;

	/**
	 * Post effects triggered after exiting the current scene
	 */
	private Effects postEffects;
	
	/**
     * A list of assets. Required to store, when desired, customized cursors for the exits
     */
    private ExitLook look;
	
	/**
	 * Creates a new NextScene
	 * 
	 * @param nextSceneId
	 *            the id of the next scene
	 */
	public NextScene( String nextSceneId ) {
		this.nextSceneId = nextSceneId;

		destinyX = Integer.MIN_VALUE;
		destinyY = Integer.MIN_VALUE;
		conditions = new Conditions( );
		effects = new Effects( );
		postEffects = new Effects( );
	}

	/**
	 * Creates a new nextScene
	 * 
	 * @param nextScene
	 *            the id of the next scene
	 * @param positionX
	 *            the horizontal position of the player when he/she gets into this scene
	 * @param positionY
	 *            the vertical position of the player when he/she gets into this scene
	 */
	public NextScene( String nextScene, int positionX, int positionY ) {
		this.nextSceneId = nextScene;

		this.destinyX = positionX;
		this.destinyY = positionY;
		conditions = new Conditions( );
		effects = new Effects( );
		postEffects = new Effects( );
	}

	/**
	 * Returns the id of the next scene
	 * 
	 * @return the id of the next scene
	 */
	public String getNextSceneId( ) {
		return nextSceneId;
	}

	/**
	 * Returns whether this scene has been assigned a position for a player that just came in
	 * 
	 * @return true if this scene has a position assigned, false otherwise
	 */
	public boolean hasPlayerPosition( ) {
		return ( destinyX != Integer.MIN_VALUE ) && ( destinyY != Integer.MIN_VALUE );
	}

	/**
	 * Returns the horizontal position of the player when he/she gets into this scene
	 * 
	 * @return the horizontal position of the player when he/she gets into this scene
	 */
	public int getDestinyX( ) {
		return destinyX;
	}

	/**
	 * Returns the vertical position of the player when he/she gets into this scene
	 * 
	 * @return the verticalal position of the player when he/she gets into this scene
	 */
	public int getDestinyY( ) {
		return destinyY;
	}

	/**
	 * Returns the conditions for this next scene
	 * 
	 * @return the conditions for this next scene
	 */
	public Conditions getConditions( ) {
		return conditions;
	}

	/**
	 * Returns the effects for this next scene
	 * 
	 * @return the effects for this next scene
	 */
	public Effects getEffects( ) {
		return effects;
	}

	/**
	 * Returns the post-effects for this next scene
	 * 
	 * @return the post-effects for this next scene
	 */
	public Effects getPostEffects( ) {
		return postEffects;
	}

	/**
	 * Sets a new next scene id.
	 * 
	 * @param nextSceneId
	 *            New next scene id
	 */
	public void setNextSceneId( String nextSceneId ) {
		this.nextSceneId = nextSceneId;
	}

	/**
	 * Sets the new destiny position for the next scene.
	 * 
	 * @param positionX
	 *            X coordinate of the destiny position
	 * @param positionY
	 *            Y coordinate of the destiny position
	 */
	public void setDestinyPosition( int positionX, int positionY ) {
		this.destinyX = positionX;
		this.destinyY = positionY;
	}

	/**
	 * Changes the conditions for this next scene
	 * 
	 * @param conditions
	 *            The new conditions
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}

	/**
	 * Changes the effects for this next scene
	 * 
	 * @param effects
	 *            The new effects
	 */
	public void setEffects( Effects effects ) {
		this.effects = effects;
	}

	/**
	 * Changes the post-effects for this next scene
	 * 
	 * @param postEffects
	 *            The new post-effects
	 */
	public void setPostEffects( Effects postEffects ) {
		this.postEffects = postEffects;
	}
	
    
    public ExitLook getExitLook(){
        return this.look;
    }
    
    public void setExitLook(ExitLook exitLook){
        look= exitLook ;
    }

}
