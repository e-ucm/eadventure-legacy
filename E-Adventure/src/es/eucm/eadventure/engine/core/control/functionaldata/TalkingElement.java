package es.eucm.eadventure.engine.core.control.functionaldata;

/**
 * Interface for all talking characters
 */
public interface TalkingElement {
    
    /**
     * Makes the character start speaking
     * @param text the text to be spoken
     */
    public void speak( String text );
    
    /**
     * Makes the character stops speaking
     */
    public void stopTalking( );

    /**
     * Returns whether the character is talking
     * @return true if the character is talking, false otherwise
     */
    public boolean isTalking( );
}
