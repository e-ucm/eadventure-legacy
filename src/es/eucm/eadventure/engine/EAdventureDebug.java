package es.eucm.eadventure.engine;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.Loader;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added
 * - Support for .ead files. Therefore <e-Adventure> files are no longer .zip but .ead
 *  @updated by Enrique López. Functionalities added (10/2008)
 * - Multilanguage support. Two new classes added
 */
public class EAdventureDebug {
	
    /**
     * Launchs a new e-Adventure game
     * @param args Arguments
     */
    public static void debug( AdventureData data, InputStreamCreator isCreator ) {
        
        Loader.setAdventureData (data);
        ResourceHandler.setExternalMode( isCreator );
        GameText.reloadStrings();
        Game.create( );
        Game.getInstance( ).setAdventureName( data.getTitle() );
        Game.getInstance( ).run( );
        Game.delete( );
        Loader.setAdventureData (null);
    }
}
