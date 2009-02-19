package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to speak a line of text.
 */
public class FunctionalSpeakCharEffect extends FunctionalEffect {

    /**
     * Creates a new SpeakCharEffect.
     * @param idTarget the id of the character who will speak
     * @param line the text to be spoken
     */
    public FunctionalSpeakCharEffect( SpeakCharEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ((SpeakCharEffect)effect).getTargetId() );
        if( npc != null ) {
        	if (npc.isAlwaysSynthesizer())
        		npc.speakWithFreeTTS( ((SpeakCharEffect)effect).getLine(), npc.getPlayerVoice() );
        	else 
        		npc.speak( ((SpeakCharEffect)effect).getLine());
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && !Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).setCharacterCurrentlyTalking( null );
        
        return Game.getInstance( ).getCharacterCurrentlyTalking( ) != null;
    }

}
