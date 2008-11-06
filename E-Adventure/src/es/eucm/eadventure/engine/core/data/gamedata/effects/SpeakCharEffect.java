package es.eucm.eadventure.engine.core.data.gamedata.effects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to speak a line of text.
 */
public class SpeakCharEffect implements Effect {

    /**
     * Id of the character who will talk
     */
    private String idTarget;

    /**
     * Text for the character to speak
     */
    private String line;

    /**
     * Creates a new SpeakCharEffect.
     * @param idTarget the id of the character who will speak
     * @param line the text to be spoken
     */
    public SpeakCharEffect( String idTarget, String line ) {
        this.idTarget = idTarget;
        this.line = line;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( idTarget );
        if( npc != null ) {
            npc.speak( line );
            Game.getInstance( ).setCharacterCurrentlyTalking( npc );
        }
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
     * @see es.eucm.eadventure.engine.core.data.gamedata.effects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && !Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).setCharacterCurrentlyTalking( null );
        
        return Game.getInstance( ).getCharacterCurrentlyTalking( ) != null;
    }

}
