package es.eucm.eadventure.engine.core.data;

import es.eucm.eadventure.engine.core.gui.TextConstantsEngine;
/**
 * This class holds the texts of the game, such as standard player responses,
 * options text, and so on  
 */
public class GameText {
    
    // Text for loading screen
    public static boolean SHOW_COMMENTARIES = false;
    
    /**
     * Loading text for "Please wait"
     */
    public static final String TEXT_PLEASE_WAIT = TextConstantsEngine.getText( "GameText.PleaseWait" );
    
    /**
     * Loading text for "Loading data"
     */
    public static final String TEXT_LOADING_DATA = TextConstantsEngine.getText( "GameText.LoadingData" );
    
    /**
     * Loading text for "Loading XML"
     */
    public static final String TEXT_LOADING_XML = TextConstantsEngine.getText( "GameText.LoadingXML" );
    
    /**
     * Loading text for "Loading finished"
     */
    public static final String TEXT_LOADING_FINISHED = TextConstantsEngine.getText( "GameText.LoadingFinished" );
    
    //********************************************************************************//
   
    // Text for the actions
    
    /**
     * Action text for "Go"
     */
    public static final String TEXT_GO = TextConstantsEngine.getText( "GameText.Go" );
    
    /**
     * Action text for "Look"
     */
    public static final String TEXT_LOOK = TextConstantsEngine.getText( "GameText.Look" );
    
    /**
     * Action text for "Examine"
     */
    public static final String TEXT_EXAMINE = TextConstantsEngine.getText( "GameText.Examine" );
    
    /**
     * Action text for "Grab"
     */
    public static final String TEXT_GRAB = TextConstantsEngine.getText( "GameText.Examine" );
    
    /**
     * Action text for "Talk"
     */
    public static final String TEXT_TALK = TextConstantsEngine.getText( "GameText.Talk" );
    
    /**
     * Action text for "Give"
     */
    public static final String TEXT_GIVE = TextConstantsEngine.getText( "GameText.Give" );
    
    /**
     * Action text for "Use"
     */
    public static final String TEXT_USE = TextConstantsEngine.getText( "GameText.Use" );
    
    /**
     * Text for "at" (Look at)
     */
    public static final String TEXT_AT = TextConstantsEngine.getText( "GameText.At" );
    
    /**
     * Text for "to" (Go to, Give to, Talk to)
     */
    public static final String TEXT_TO = TextConstantsEngine.getText( "GameText.To" );
    
    /**
     * Text for "with" (Use with)
     */
    public static final String TEXT_WITH = TextConstantsEngine.getText( "GameText.With" );
    
    //********************************************************************************//
    
    // Text for the options
    
    /**
     * Text for the "Back to game" option
     */
    public static final String TEXT_BACK_TO_GAME = TextConstantsEngine.getText( "GameText.BackToGame" );
    
    /**
     * Text for the "Save/Load" option
     */
    public static final String TEXT_SAVE_LOAD = TextConstantsEngine.getText( "GameText.Save/Load" );
    
    /**
     * Text for the "Configuration" option
     */
    public static final String TEXT_CONFIGURATION = TextConstantsEngine.getText( "GameText.Configuration" );
    
    /**
     * Text for the "Generate report" option
     */
    public static final String TEXT_GENERATE_REPORT = TextConstantsEngine.getText( "GameText.GenerateReport" );
    
    /**
     * Text for the "Exit game" option
     */
    public static final String TEXT_EXIT_GAME = TextConstantsEngine.getText( "GameText.ExitGame" );
    
    /**
     * Text for the "Save" option
     */
    public static final String TEXT_SAVE = TextConstantsEngine.getText( "GameText.Save" );
    
    /**
     * Text for the "Load" option
     */
    public static final String TEXT_LOAD = TextConstantsEngine.getText( "GameText.Load" );
    
    /**
     * Text for the "Empty" label in the savegames
     */
    public static final String TEXT_EMPTY = TextConstantsEngine.getText( "GameText.Empty" );
    
    /**
     * Text for the "Music" section of the configuration
     */
    public static final String TEXT_MUSIC = TextConstantsEngine.getText( "GameText.Music" );
    
    /**
     * Text for the "FunctionalEffects" section of the configuration
     */
    public static final String TEXT_EFFECTS = TextConstantsEngine.getText( "GameText.Effects" );
    
    /**
     * Text for the "On" value of the configuration
     */
    public static final String TEXT_ON = TextConstantsEngine.getText( "GameText.On" );
    
    /**
     * Text for the "Off" value of the configuration
     */
    public static final String TEXT_OFF = TextConstantsEngine.getText( "GameText.Off" );
    
    /**
     * Text for the "Text speed" section of the configuration
     */
    public static final String TEXT_TEXT_SPEED = TextConstantsEngine.getText( "GameText.TextSpeed" );
    
    /**
     * Text for the "Slow" speed of the dialogues
     */
    public static final String TEXT_SLOW = TextConstantsEngine.getText( "GameText.Slow" );
    
    /**
     * Text for the "Normal" speed of the dialogues
     */
    public static final String TEXT_NORMAL = TextConstantsEngine.getText( "GameText.Normal" );
    
    /**
     * Text for the "Fast" speed of the dialogues
     */
    public static final String TEXT_FAST = TextConstantsEngine.getText( "GameText.Fast" );
    
    /**
     * Text for the "Back" option
     */
    public static final String TEXT_BACK = TextConstantsEngine.getText( "GameText.Back" );
    
    //********************************************************************************//
    
    // Text for the player to speak
    
    /**
     * Text to display when the character tries to speak with an item
     */
    private static final String[] TEXT_TALK_OBJECT = {
        TextConstantsEngine.getText( "GameText.TextTalkObject1" ),
        TextConstantsEngine.getText( "GameText.TextTalkObject2" )
    };
    
    /**
     * Text to display when the character can't talk
     */
    private static final String[] TEXT_TALK_CANNOT = {
        TextConstantsEngine.getText( "GameText.TextTalkCannot1" ),
        TextConstantsEngine.getText( "GameText.TextTalkCannot2" )
    };
    
    /**
     * Text to display when the character tries to give another character
     */
    private static final String[] TEXT_GIVE_NPC = {
        TextConstantsEngine.getText( "GameText.TextGiveNPC1" ),
        TextConstantsEngine.getText( "GameText.TextGiveNPC2" )
    };
    
    /**
     * Text to display when the character tries to give an item that's not in the inventory
     */
    private static final String[] TEXT_GIVE_OBJECT_NOT_INVENTORY = {
        TextConstantsEngine.getText( "GameText.TextGiveObjectNotInventory1" ),
        TextConstantsEngine.getText( "GameText.TextGiveObjectNotInventory2")
    };
    
    /**
     * Text to display when the character can't give an item
     */
    private static final String[] TEXT_GIVE_CANNOT = {
        TextConstantsEngine.getText( "GameText.TextGiveCannot1"),
        TextConstantsEngine.getText( "GameText.TextGiveCannot2")
    };
    
    /**
     * Text to display when the character tries to grab another character
     */
    private static final String[] TEXT_GRAB_NPC = {
        TextConstantsEngine.getText( "GameText.TextGrabNPC1"),
        TextConstantsEngine.getText( "GameText.TextGrabNPC2")
    };
    
    /**
     * Text to display when the character tries to grab an item which is already in the inventory
     */
    private static final String[] TEXT_GRAB_OBJECT_INVENTORY = {
        TextConstantsEngine.getText( "GameText.TextGrabObjectInventory1"),
        TextConstantsEngine.getText( "GameText.TextGrabObjectInventory2")
    };
    
    /**
     * Text to display when the character can't grab an item
     */
    private static final String[] TEXT_GRAB_CANNOT = {
        TextConstantsEngine.getText( "GameText.TextGiveCannot1"),
        TextConstantsEngine.getText( "GameText.TextGiveCannot2")
    };
    
    /**
     * Text to display when the character tries to use another character
     */
    private static final String[] TEXT_USE_NPC = {
        TextConstantsEngine.getText( "GameText.TextUseNPC1"),
        TextConstantsEngine.getText( "GameText.TextUseNPC2")
    };
    
    /**
     * Text to display when the character can't use an item
     */
    private static final String[] TEXT_USE_CANNOT = {
        TextConstantsEngine.getText( "GameText.TextUseCannot1"),
        TextConstantsEngine.getText( "GameText.TextUseCannot2")
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
        if (SHOW_COMMENTARIES)
            return TEXT_TALK_OBJECT[ (int)( TEXT_TALK_OBJECT.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character can't talk
     * @return Random string amongst the present
     */
    public static String getTextTalkCannot( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_TALK_CANNOT[ (int)( TEXT_TALK_CANNOT.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to give another character
     * @return Random string amongst the present
     */
    public static String getTextGiveNPC( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_GIVE_NPC[ (int)( TEXT_GIVE_NPC.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to give an item that's not in the inventory
     * @return Random string amongst the present
     */
    public static String getTextGiveObjectNotInventory( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_GIVE_OBJECT_NOT_INVENTORY[ (int)( TEXT_GIVE_OBJECT_NOT_INVENTORY.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character can't give an item
     * @return Random string amongst the present
     */
    public static String getTextGiveCannot( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_GIVE_CANNOT[ (int)( TEXT_GIVE_CANNOT.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to grab another character
     * @return Random string amongst the present
     */
    public static String getTextGrabNPC( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_GRAB_NPC[ (int)( TEXT_GRAB_NPC.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character tries to grab an item which is already in the inventory
     * @return Random string amongst the present
     */
    public static String getTextGrabObjectInventory( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_GRAB_OBJECT_INVENTORY[ (int)( TEXT_GRAB_OBJECT_INVENTORY.length * Math.random( ) ) ];
        else
            return null;    
    }
    
    /**
     * Returns a string used when the character can't grab an item
     * @return Random string amongst the present
     */
    public static String getTextGrabCannot( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_GRAB_CANNOT[ (int)( TEXT_GRAB_CANNOT.length * Math.random( ) ) ];
        else
            return null;
    }
    
    /**
     * Returns a string used when the character tries to use another character
     * @return Random string amongst the present
     */
    public static String getTextUseNPC( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_USE_NPC[ (int)( TEXT_USE_NPC.length * Math.random( ) ) ];
        else
            return null;
    }
    
    /**
     * Returns a string used when the character can't use an item
     * @return Random string amongst the present
     */
    public static String getTextUseCannot( ) {
        if (SHOW_COMMENTARIES)
            return TEXT_USE_CANNOT[ (int)( TEXT_USE_CANNOT.length * Math.random( ) ) ];
        else
            return null;
    }
}
