package es.eucm.eadventure.engine.core.data.gamedata.elements;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.ConversationReference;

/**
 * This class holds the data of a non playing character (npc) in eAdventure
 */
public class NPC extends Element {

    /**
     * The tag for the standup animation
     */
    public static final String RESOURCE_TYPE_STAND_UP = "standup";
    
    /**
     * The tag for the standdown animation
     */
    public static final String RESOURCE_TYPE_STAND_DOWN = "standdown";
    
    /**
     * The tag for the standright animation
     */
    public static final String RESOURCE_TYPE_STAND_RIGHT = "standright";
    
    /**
     * The tag for the speakup animation
     */
    public static final String RESOURCE_TYPE_SPEAK_UP = "speakup";
    
    /**
     * The tag for the speakdown animation
     */
    public static final String RESOURCE_TYPE_SPEAK_DOWN = "speakdown";
    
    /**
     * The tag for the speakright animation
     */
    public static final String RESOURCE_TYPE_SPEAK_RIGHT = "speakright";
    
    /**
     * The tag for the useright animation
     */
    public static final String RESOURCE_TYPE_USE_RIGHT = "useright";
    
    /**
     * The tag for the walkup animation
     */
    public static final String RESOURCE_TYPE_WALK_UP = "walkup";
    
    /**
     * The tag for the walkdown animation
     */
    public static final String RESOURCE_TYPE_WALK_DOWN = "walkdown";
    
    /**
     * The tag for the walkright animation
     */
    public static final String RESOURCE_TYPE_WALK_RIGHT = "walkright";
    
    /**
     * The front color of the text of the character
     */
    private String textFrontColor;
    
    /**
     * The border color of the text of the character
     */
    private String textBorderColor;
    
    /**
     * List of conversation references of the player
     */
    private ArrayList<ConversationReference> conversationReferences;


    /**
     * Creates a new NPC
     * @param id the id of the npc
     */
    public NPC( String id ) {
        super( id );
        conversationReferences = new ArrayList<ConversationReference>( );
        
        // Default colors are white for the front color, and black for the border color
        textFrontColor = "#FFFFFF";
        textBorderColor = "#000000";
    }

    /**
     * Creates a new NPC
     * @param id the id of the npc
     * @param name the name of the npc
     * @param description the brief description of the npc
     * @param detailedDescription the detailed description of the npc
     */
    public NPC( String id, String name, String description, String detailedDescription ) {
        super( id, name, description, detailedDescription );
    }
    
    /**
     * Returns the front color of the character's text
     * @return String with the color, in format "#RRGGBB"
     */
    public String getTextFrontColor( ) {
        return textFrontColor;
    }
    
    /**
     * Returns the boder color of the character's text
     * @return String with the color, in format "#RRGGBB"
     */
    public String getTextBorderColor( ) {
        return textBorderColor;
    }

    /**
     * Returns the list of conversation references related to this npc
     * @return the list of conversation references related to this npc
     */
    public ArrayList<ConversationReference> getConversationReferences( ) {
        return conversationReferences;
    }
    
    /**
     * Sets the front color of the character's text
     * @param textFrontColor String with the color, in format "#RRGGBB"
     */
    public void setTextFrontColor( String textFrontColor ) {
        this.textFrontColor = textFrontColor;
    }
    
    /**
     * Sets the border color of the character's text
     * @param textBorderColor String with the color, in format "#RRGGBB"
     */
    public void setTextBorderColor( String textBorderColor ) {
        this.textBorderColor = textBorderColor;
    }

    /**
     * Adds a conversation reference to the list of conversation references
     * @param conversationReference the conversation reference to add
     */
    public void addConversationReference( ConversationReference conversationReference ) {
        conversationReferences.add( conversationReference );
    }

    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString( ) {
        StringBuffer sb = new StringBuffer( 40 );

        sb.append( "\n" );
        sb.append( super.toString( ) );
        for( ConversationReference conversationReference : conversationReferences )
            sb.append( conversationReference.toString( ) );

        return sb.toString( );
    }
}
