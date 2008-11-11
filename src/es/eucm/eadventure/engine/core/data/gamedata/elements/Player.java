package es.eucm.eadventure.engine.core.data.gamedata.elements;

/**
 * This class holds the data for the player in eAdventure
 */
public class Player extends Element {

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
     * The front color of the text of the player
     */
    private String textFrontColor;
    
    /**
     * The border color of the text of the player
     */
    private String textBorderColor;

    /**
     * Creates a new player
     */
    public Player( ) {
        super( "Player" );
        
        // Default colors are white for the front color, and black for the border color
        textFrontColor = "#FFFFFF";
        textBorderColor = "#000000";
    }

    /**
     * Creates a new player
     * @param name the name of the player
     * @param description the brief description of the player
     * @param detailedDescription the detailed description of the player
     */
    public Player( String name, String description, String detailedDescription ) {
        super( "Player", name, description, detailedDescription );
    }
    
    /**
     * Returns the front color of the player's text
     * @return String with the color, in format "#RRGGBB"
     */
    public String getTextFrontColor( ) {
        return textFrontColor;
    }
    
    /**
     * Returns the boder color of the player's text
     * @return String with the color, in format "#RRGGBB"
     */
    public String getTextBorderColor( ) {
        return textBorderColor;
    }
    
    /**
     * Sets the front color of the player's text
     * @param textFrontColor String with the color, in format "#RRGGBB"
     */
    public void setTextFrontColor( String textFrontColor ) {
        this.textFrontColor = textFrontColor;
    }
    
    /**
     * Sets the border color of the player's text
     * @param textBorderColor String with the color, in format "#RRGGBB"
     */
    public void setTextBorderColor( String textBorderColor ) {
        this.textBorderColor = textBorderColor;
    }

    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString( ) {
        return super.toString( );
    }
}