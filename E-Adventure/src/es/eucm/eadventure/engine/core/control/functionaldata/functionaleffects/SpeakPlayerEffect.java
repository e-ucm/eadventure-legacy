package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * An effect that makes the player to speak a line of text.
 */
public class SpeakPlayerEffect implements Effect {

    /**
     * Text for the player to speak
     */
    private String line;

    /**
     * Creates a new SpeakPlayerEffect.
     * @param line the text to be spoken
     */
    public SpeakPlayerEffect( String line ) {
        this.line = line;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        player.speak( line );
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && !Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).setCharacterCurrentlyTalking( null );
        
        return Game.getInstance( ).getCharacterCurrentlyTalking( ) != null;
    }

}
