package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.awt.Graphics2D;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * An effect that plays a sound 
 */
public class PlayAnimationEffect implements Effect {


    /**
     * Upper-left coordinate of the animation in the X
     */
    private int x;
    
    /**
     * Upper-left coordinate of the animation in the Y
     */
    private int y;
    
    /**
     * The path to the base animation file
     */
    private String path;
    
    /**
     * The animation to be played
     */
    private Animation animation;

    /**
     * Creates a new PlaySoundEffect
     * @param background whether to play the sound in background
     * @param path path to the sound file
     */
    public PlayAnimationEffect( String path, int x, int y ) {
        this.path = path;
        this.x = x;
        this.y = y;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        animation = MultimediaManager.getInstance( ).loadAnimation( path, false, MultimediaManager.IMAGE_SCENE );
        animation.start( );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effect#isStillRunning()
     */
    public boolean isStillRunning(  ) {
        return animation.isPlayingForFirstTime( );
    }
    
    public void update( long elapsedTime ){
        animation.update( elapsedTime );
    }
    
    public void draw( Graphics2D g ) {
        GUI.getInstance( ).addElementToDraw( animation.getImage( ), Math.round( x - ( animation.getImage( ).getWidth( null ) / 2 ) ) - Game.getInstance().getFunctionalScene().getOffsetX(), Math.round( y - ( animation.getImage( ).getHeight( null ) / 2 ) ), Math.round( y ) );
    }

}
