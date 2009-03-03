package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;

/**
 * Interface for all talking characters
 */
public interface TalkingElement {
    
    /**
     * Makes the character start speaking
     * @param text the text to be spoken
     */
    public void speak( String text );
    
    public void speakWithFreeTTS(String text, String voice);
    
    public void speak ( String text, String audioPath );
    
    /**
     * Check if all player conversation lines must be read by synthesizer
     * @return
     * 		true, if all player conversation lines must be read by synthesizer
     */
    public boolean isAlwaysSynthesizer();
    
    /**
     * Takes the player voice for synthesizer
     * 
     * @return
     * 		A string representing associates voice
     */
    public String getPlayerVoice();

    
    /**
     * Makes the character stops speaking
     */
    public void stopTalking( );

    /**
     * Returns whether the character is talking
     * @return true if the character is talking, false otherwise
     */
    public boolean isTalking( );
    
    public boolean getShowsSpeechBubbles();
    
    public Color getBubbleBkgColor();
    
    public Color getBubbleBorderColor();
}
