/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.loader.InputStreamCreator;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added - Support for .ead
 *          files. Therefore <e-Adventure> files are no longer .zip but .ead
 * @updated by Enrique López. Functionalities added (10/2008) - Multilanguage
 *          support. Two new classes added
 */
public class EAdventureDebug {

    /**
     * Launchs a new e-Adventure game
     * 
     * @param args
     *            Arguments
     */
    public static void debug( AdventureData data, InputStreamCreator isCreator ) {

        Loader.setAdventureData( data );
        ResourceHandler.setExternalMode( isCreator );
        GameText.reloadStrings( );
        Game.create( true, true );
        Game.getInstance( ).setAdventureName( data.getTitle( ) );
        Game.getInstance( ).run( );
        Game.delete( );
        Loader.setAdventureData( null );
    }

    public static void normalRun( AdventureData data, InputStreamCreator isCreator ) {

        Loader.setAdventureData( data );
        ResourceHandler.setExternalMode( isCreator );
        GameText.reloadStrings( );
        Game.create( true, false );
        Game.getInstance( ).setAdventureName( data.getTitle( ) );
        Game.getInstance( ).run( );
        Game.delete( );
        Loader.setAdventureData( null );
    }
}
