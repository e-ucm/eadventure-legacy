package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.awt.Graphics2D;

import es.eucm.eadventure.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * An effect that plays a sound 
 */
public class FunctionalPlayAnimationEffect extends FunctionalEffect {

    private Animation animation;

    /**
     * Creates a new PlaySoundEffect
     * @param background whether to play the sound in background
     * @param path path to the sound file
     */
    public FunctionalPlayAnimationEffect( PlayAnimationEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        animation = MultimediaManager.getInstance( ).loadAnimation( ((PlayAnimationEffect)effect).getPath(), false, MultimediaManager.IMAGE_SCENE );
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning(  ) {
        return animation.isPlayingForFirstTime( );
    }
    
    public void update( long elapsedTime ){
        animation.update( elapsedTime );
    }
    
    public void draw( Graphics2D g ) {
        GUI.getInstance( ).addElementToDraw( animation.getImage( ), Math.round( ((PlayAnimationEffect)effect).getX() - ( animation.getImage( ).getWidth( null ) / 2 ) ) - Game.getInstance().getFunctionalScene().getOffsetX(), Math.round( ((PlayAnimationEffect)effect).getY() - ( animation.getImage( ).getHeight( null ) / 2 ) ), Math.round( ((PlayAnimationEffect)effect).getY() ) );
    }

}
