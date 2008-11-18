package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * An effect that plays an animation 
 */
public class FunctionalPlaySoundEffect extends FunctionalEffect {
  
    /**
     * The sound to be played
     */
    private long soundID;

    /**
     * Creates a new PlaySoundEffect
     * @param background whether to play the sound in background
     * @param path path to the sound file
     */
    public FunctionalPlaySoundEffect( PlaySoundEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        if( Game.getInstance().getOptions().isEffectsActive() ){
            soundID = MultimediaManager.getInstance().loadSound( ((PlaySoundEffect)effect).getPath(), false );
            MultimediaManager.getInstance().startPlaying( soundID );
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return ((PlaySoundEffect)effect).isBackground();
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return !((PlaySoundEffect)effect).isBackground() && MultimediaManager.getInstance().isPlaying( soundID );
    }
}