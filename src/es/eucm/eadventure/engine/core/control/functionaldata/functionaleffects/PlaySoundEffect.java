package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * An effect that plays an animation 
 */
public class PlaySoundEffect implements Effect {
  
    /**
     * Whether the sound must be played in background
     */
    private boolean background;
    
    /**
     * The path to the sound file
     */
    private String path;
    
    /**
     * The sound to be played
     */
    private long soundID;

    /**
     * Creates a new PlaySoundEffect
     * @param background whether to play the sound in background
     * @param path path to the sound file
     */
    public PlaySoundEffect( boolean background, String path ) {
        this.background = background;
        this.path = path;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        if( Game.getInstance().getOptions().isEffectsActive() ){
            soundID = MultimediaManager.getInstance().loadSound( path, false );
            MultimediaManager.getInstance().startPlaying( soundID );
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return background;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return !background && MultimediaManager.getInstance().isPlaying( soundID );
    }
}