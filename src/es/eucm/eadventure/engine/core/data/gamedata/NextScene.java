package es.eucm.eadventure.engine.core.data.gamedata;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effects;
import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;

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
    private int x;

    /**
     * Y position in which the player should appear in the new scene
     */
    private int y;

    /**
     * Conditions of the next scene
     */
    private Conditions conditions;

    /**
     * Effects triggered before exiting the current scene
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
     * @param nextSceneId the id of the next scene
     */
    public NextScene( String nextSceneId ) {
        this.nextSceneId = nextSceneId;

        x = Integer.MIN_VALUE;
        y = Integer.MIN_VALUE;
        conditions = new Conditions( );
        effects = new Effects( );
        postEffects = new Effects( );
    }

    /**
     * Creates a new nextScene
     * @param nextScene the id of the next scene
     * @param positionX the horizontal position of the player when he/she gets into this scene
     * @param positionY the vertical position of the player when he/she gets into this scene
     */
    public NextScene( String nextScene, int positionX, int positionY ) {
        this.nextSceneId = nextScene;

        this.x = positionX;
        this.y = positionY;
        conditions = new Conditions( );
        effects = new Effects( );
        postEffects = new Effects( );
    }

    /**
     * Returns the id of the next scene
     * @return the id of the next scene
     */
    public String getNextSceneId( ) {
        return nextSceneId;
    }

    /**
     * Returns whether this scene has been assigned a position for a player that just came in
     * @return true if this scene has a position assigned, false otherwise
     */
    public boolean hasPlayerPosition( ) {
        return ( x != Integer.MIN_VALUE ) && ( y != Integer.MIN_VALUE );
    }

    /**
     * Returns the horizontal position of the player when he/she gets into this scene
     * @return the horizontal position of the player when he/she gets into this scene
     */
    public int getX( ) {
        return x;
    }

    /**
     * Returns the vertical position of the player when he/she gets into this scene
     * @return the verticalal position of the player when he/she gets into this scene
     */
    public int getY( ) {
        return y;
    }

    /**
     * Returns the conditions for this next scene
     * @return the conditions for this next scene
     */
    public Conditions getConditions( ) {
        return conditions;
    }

    /**
     * Returns the effects for this next scene
     * @return the effects for this next scene
     */
    public Effects getEffects( ) {
        return effects;
    }
    
    /**
     * Returns the post-effects for this next scene
     * @return the post-effects for this next scene
     */
    public Effects getPostEffects( ) {
        return postEffects;
    }

    /**
     * Changes the conditions for this next scene
     * @param conditions the new conditions
     */
    public void setConditions( Conditions conditions ) {
        this.conditions = conditions;
    }

    /**
     * Changes the effects for this next scene
     * @param effects the new effects
     */
    public void setEffects( Effects effects ) {
        this.effects = effects;
    }
    
    /**
     * Changes the post-effects for this next scene
     * @param effects the new post-effects
     */
    public void setPostEffects( Effects postEffects ) {
        this.postEffects = postEffects;
    }
    
    /**
     * @return the exitText
     */
    public String getExitText( ) {
        String exitText=null;
            if (new FunctionalConditions(getConditions( )).allConditionsOk( ) && look!=null){
                exitText=look.getExitText( );
            }
        return exitText;
    }

    
    /**
     * Returns the cursor of the first resources block which all conditions are met
     * @return the cursor. Null is returned if no look had its conditions satisfied, 
     * or if no one was set
     */
    public String getCursorPath(){
        String cursorPath=null;
            if (new FunctionalConditions(getConditions( )).allConditionsOk( ) && look!=null){
                cursorPath=look.getCursorPath( );
            }
        return cursorPath;    
   }
    
    public ExitLook getExitLook(){
        return this.look;
    }
    
    public void setExitLook(ExitLook exitLook){
        look= exitLook ;
    }

}
