package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * An effect that makes the player to speak a line of text.
 */
public class FunctionalSpeakPlayerEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalSpeakPlayerEffect.
     * @param line the text to be spoken
     */
    public FunctionalSpeakPlayerEffect( SpeakPlayerEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        if (player.isAlwaysSynthesizer())
        	player.speakWithFreeTTS( ((SpeakPlayerEffect)effect).getLine(), player.getPlayerVoice() );
        else
        	player.speak( ((SpeakPlayerEffect)effect).getLine() );
        Game.getInstance( ).setCharacterCurrentlyTalking( player );
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
    public boolean isStillRunning( ) {
        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && !Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).setCharacterCurrentlyTalking( null );
        
        return Game.getInstance( ).getCharacterCurrentlyTalking( ) != null;
    }

}
