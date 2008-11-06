package es.eucm.eadventure.engine.core.data.gamedata.conversation.util;

/**
 * This class stores a single conversation line, along with the name of the speaker character
 */
public class ConversationLine {

    /* Attributes */

    /**
     * String that holds the name of the character
     */
    private String name;

    /**
     * Sentence said by the character
     */
    private String text;
    
    /**
     * Sentence said by the character
     */
    private String audioPath;

    /* Methods */

    /**
     * @return the audioPath
     */
    public String getAudioPath( ) {
        return audioPath;
    }

    /**
     * @param audioPath the audioPath to set
     */
    public void setAudioPath( String audioPath ) {
        this.audioPath = audioPath;
    }

    public boolean hasValidAudio(){
        return audioPath!=null && !audioPath.equals( "" );
    }
    /**
     * Constructor
     * @param name Name of the character
     * @param text Sentence
     */
    public ConversationLine( String name, String text ) {
        this.name = name;
        this.text = text;
    }

    /**
     * Returns if the line belongs to the player
     * @return True if the line belongs to the player, false otherwise
     */
    public boolean isPlayerLine( ) {
        return name.equals( "Player" );
    }

    /**
     * Returns the text of the converstational line
     */
    public String getText( ) {
        return text;
    }

    /**
     * Returns the name of the character
     */
    public String getName( ) {
        return name;
    }
}
