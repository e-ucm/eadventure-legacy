package es.eucm.eadventure.engine.core.data;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.Game;
/**
 * This class holds the texts of the game, such as standard player responses,
 * options text, and so on  
 */
public class GameText {
    
	public static boolean showCommentaries(){
		return Game.getInstance().getGameDescriptor().isCommentaries();
	}
	
    /**
     * Loading text for "Please wait"
     */
    public static final String TEXT_PLEASE_WAIT = TextConstants.getText( "GameText.PleaseWait" );
    
    /**
     * Loading text for "Loading data"
     */
    public static final String TEXT_LOADING_DATA = TextConstants.getText( "GameText.LoadingData" );
    
    /**
     * Loading text for "Loading XML"
     */
    public static final String TEXT_LOADING_XML = TextConstants.getText( "GameText.LoadingXML" );
    
    /**
     * Loading text for "Loading finished"
     */
    public static final String TEXT_LOADING_FINISHED = TextConstants.getText( "GameText.LoadingFinished" );
    
    //********************************************************************************//
   
    // Text for the actions
    
    /**
     * Action text for "Go"
     */
    public static final String TEXT_GO = TextConstants.getText( "GameText.Go" );
    
    /**
     * Action text for "Look"
     */
    public static final String TEXT_LOOK = TextConstants.getText( "GameText.Look" );
    
    /**
     * Action text for "Examine"
     */
    public static final String TEXT_EXAMINE = TextConstants.getText( "GameText.Examine" );
    
    /**
     * Action text for "Grab"
     */
    public static final String TEXT_GRAB = TextConstants.getText( "GameText.Examine" );
    
    /**
     * Action text for "Talk"
     */
    public static final String TEXT_TALK = TextConstants.getText( "GameText.Talk" );
    
    /**
     * Action text for "Give"
     */
    public static final String TEXT_GIVE = TextConstants.getText( "GameText.Give" );
    
    /**
     * Action text for "Use"
     */
    public static final String TEXT_USE = TextConstants.getText( "GameText.Use" );
    
    /**
     * Text for "at" (Look at)
     */
    public static final String TEXT_AT = TextConstants.getText( "GameText.At" );
    
    /**
     * Text for "to" (Go to, Give to, Talk to)
     */
    public static final String TEXT_TO = TextConstants.getText( "GameText.To" );
    
    /**
     * Text for "with" (Use with)
     */
    public static final String TEXT_WITH = TextConstants.getText( "GameText.With" );
    
    //********************************************************************************//
    
    // Text for the options
    
    /**
     * Text for the "Back to game" option
     */
    public static final String TEXT_BACK_TO_GAME = TextConstants.getText( "GameText.BackToGame" );
    
    /**
     * Text for the "Save/Load" option
     */
    public static final String TEXT_SAVE_LOAD = TextConstants.getText( "GameText.Save/Load" );
    
    /**
     * Text for the "Configuration" option
     */
    public static final String TEXT_CONFIGURATION = TextConstants.getText( "GameText.Configuration" );
    
    /**
     * Text for the "Generate report" option
     */
    public static final String TEXT_GENERATE_REPORT = TextConstants.getText( "GameText.GenerateReport" );
    
    /**
     * Text for the "Exit game" option
     */
    public static final String TEXT_EXIT_GAME = TextConstants.getText( "GameText.ExitGame" );
    
    /**
     * Text for the "Save" option
     */
    public static final String TEXT_SAVE = TextConstants.getText( "GameText.Save" );
    
    /**
     * Text for the "Load" option
     */
    public static final String TEXT_LOAD = TextConstants.getText( "GameText.Load" );
    
    /**
     * Text for the "Empty" label in the savegames
     */
    public static final String TEXT_EMPTY = TextConstants.getText( "GameText.Empty" );
    
    /**
     * Text for the "Music" section of the configuration
     */
    public static final String TEXT_MUSIC = TextConstants.getText( "GameText.Music" );
    
    /**
     * Text for the "FunctionalEffects" section of the configuration
     */
    public static final String TEXT_EFFECTS = TextConstants.getText( "GameText.Effects" );
    
    /**
     * Text for the "On" value of the configuration
     */
    public static final String TEXT_ON = TextConstants.getText( "GameText.On" );
    
    /**
     * Text for the "Off" value of the configuration
     */
    public static final String TEXT_OFF = TextConstants.getText( "GameText.Off" );
    
    /**
     * Text for the "Text speed" section of the configuration
     */
    public static final String TEXT_TEXT_SPEED = TextConstants.getText( "GameText.TextSpeed" );
    
    /**
     * Text for the "Slow" speed of the dialogues
     */
    public static final String TEXT_SLOW = TextConstants.getText( "GameText.Slow" );
    
    /**
     * Text for the "Normal" speed of the dialogues
     */
    public static final String TEXT_NORMAL = TextConstants.getText( "GameText.Normal" );
    
    /**
     * Text for the "Fast" speed of the dialogues
     */
    public static final String TEXT_FAST = TextConstants.getText( "GameText.Fast" );
    
    /**
     * Text for the "Back" option
     */
    public static final String TEXT_BACK = TextConstants.getText( "GameText.Back" );
    
    //********************************************************************************//
    
    // Text for the player to speak
    
    /**
     * Text to display when the character tries to speak with an item
     */
    private static final String[] TEXT_TALK_OBJECT = {
        TextConstants.getText( "GameText.TextTalkObject1" ),
        TextConstants.getText( "GameText.TextTalkObject2" )
    };
    
    /**
     * Text to display when the character can't talk
     */
    private static final String[] TEXT_TALK_CANNOT = {
        TextConstants.getText( "GameText.TextTalkCannot1" ),
        TextConstants.getText( "GameText.TextTalkCannot2" )
    };
    
    /**
     * Text to display when the character tries to give another character
     */
    private static final String[] TEXT_GIVE_NPC = {
        TextConstants.getText( "GameText.TextGiveNPC1" ),
        TextConstants.getText( "GameText.TextGiveNPC2" )
    };
    
    /**
     * Text to display when the character tries to give an item that's not in the inventory
     */
    private static final String[] TEXT_GIVE_OBJECT_NOT_INVENTORY = {
        TextConstants.getText( "GameText.TextGiveObjectNotInventory1" ),
        TextConstants.getText( "GameText.TextGiveObjectNotInventory2")
    };
    
    /**
     * Text to display when the character can't give an item
     */
    private static final String[] TEXT_GIVE_CANNOT = {
        TextConstants.getText( "GameText.TextGiveCannot1"),
        TextConstants.getText( "GameText.TextGiveCannot2")
    };
    
    /**
     * Text to display when the character tries to grab another character
     */
    private static final String[] TEXT_GRAB_NPC = {
        TextConstants.getText( "GameText.TextGrabNPC1"),
        TextConstants.getText( "GameText.TextGrabNPC2")
    };
    
    /**
     * Text to display when the character tries to grab an item which is already in the inventory
     */
    private static final String[] TEXT_GRAB_OBJECT_INVENTORY = {
        TextConstants.getText( "GameText.TextGrabObjectInventory1"),
        TextConstants.getText( "GameText.TextGrabObjectInventory2")
    };
    
    /**
     * Text to display when the character can't grab an item
     */
    private static final String[] TEXT_GRAB_CANNOT = {
        TextConstants.getText( "GameText.TextGiveCannot1"),
        TextConstants.getText( "GameText.TextGiveCannot2")
    };
    
    /**
     * Text to display when the character tries to use another character
     */
    private static final String[] TEXT_USE_NPC = {
        TextConstants.getText( "GameText.TextUseNPC1"),
        TextConstants.getText( "GameText.TextUseNPC2")
    };
    
    /**
     * Text to display when the character can't use an item
     */
    private static final String[] TEXT_USE_CANNOT = {
        TextConstants.getText( "GameText.TextUseCannot1"),
        TextConstants.getText( "GameText.TextUseCannot2")
    };
    
    //********************************************************************************//
    
    /**
     * Private constructor (Static class)
     */
    private GameText( ) { }
    
    /**
     * Returns a string used when the character tries to speak with an item
     * @return Random string amongst the present
     */
    public static String getTextTalkObject( ) {
        if (showCommentaries())
            return TEXT_TALK_OBJECT[ (int)( TEXT_TALK_OBJECT.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character can't talk
     * @return Random string amongst the present
     */
    public static String getTextTalkCannot( ) {
        if (showCommentaries())
            return TEXT_TALK_CANNOT[ (int)( TEXT_TALK_CANNOT.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to give another character
     * @return Random string amongst the present
     */
    public static String getTextGiveNPC( ) {
        if (showCommentaries())
            return TEXT_GIVE_NPC[ (int)( TEXT_GIVE_NPC.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to give an item that's not in the inventory
     * @return Random string amongst the present
     */
    public static String getTextGiveObjectNotInventory( ) {
        if (showCommentaries())
            return TEXT_GIVE_OBJECT_NOT_INVENTORY[ (int)( TEXT_GIVE_OBJECT_NOT_INVENTORY.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character can't give an item
     * @return Random string amongst the present
     */
    public static String getTextGiveCannot( ) {
        if (showCommentaries())
            return TEXT_GIVE_CANNOT[ (int)( TEXT_GIVE_CANNOT.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to grab another character
     * @return Random string amongst the present
     */
    public static String getTextGrabNPC( ) {
        if (showCommentaries())
            return TEXT_GRAB_NPC[ (int)( TEXT_GRAB_NPC.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to grab an item which is already in the inventory
     * @return Random string amongst the present
     */
    public static String getTextGrabObjectInventory( ) {
        if (showCommentaries())
            return TEXT_GRAB_OBJECT_INVENTORY[ (int)( TEXT_GRAB_OBJECT_INVENTORY.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character can't grab an item
     * @return Random string amongst the present
     */
    public static String getTextGrabCannot( ) {
        if (showCommentaries())
            return TEXT_GRAB_CANNOT[ (int)( TEXT_GRAB_CANNOT.length * Math.random( ) ) ];
        else
            return null;
    }
    
    /**
     * Returns a string used when the character tries to use another character
     * @return Random string amongst the present
     */
    public static String getTextUseNPC( ) {
        if (showCommentaries())
            return TEXT_USE_NPC[ (int)( TEXT_USE_NPC.length * Math.random( ) ) ];
        else
            return null;
    }
    
    /**
     * Returns a string used when the character can't use an item
     * @return Random string amongst the present
     */
    public static String getTextUseCannot( ) {
        if (showCommentaries())
            return TEXT_USE_CANNOT[ (int)( TEXT_USE_CANNOT.length * Math.random( ) ) ];
        else
            return null;
    }
}
