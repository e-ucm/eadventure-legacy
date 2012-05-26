/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine;

import java.util.Timer;
import java.util.TimerTask;

import javax.media.Codec;
import javax.media.PlugInManager;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added - Support for .ead
 *          files. Therefore <e-Adventure> files are no longer .zip but .ead
 */
public class EAdventureStandalone {

    private static long startTime;

    public static int getElapsedTime( ) {

        return (int) ( ( ( System.currentTimeMillis( ) - startTime ) ) / 1000.0d );
    }

    public static void printElapsedTime( String message ) {

        //System.out.println( "["+getElapsedTime()+"] "+message );
    }

    /**
     * Launchs a new e-Adventure game
     * 
     * @param args
     *            Arguments
     */
    public static void main( String[] args ) {
        TC.loadStrings( EAdventureApplet.class.getResourceAsStream( "/i18n/engine/en_EN.xml" ) );
        startTime = System.currentTimeMillis( );
        printElapsedTime( "Starting" );

        /*Timer t = new Timer();
        t.schedule( new TimerTask() {
            @Override
            public void run( ) {
                System.out.println( Runtime.getRuntime( ).freeMemory( ) + " " + Runtime.getRuntime( ).totalMemory( ));
            }
            
        }, 0, 50 );*/

        try {
            Codec video = (Codec) Class.forName( "net.sourceforge.jffmpeg.VideoDecoder" ).newInstance( );
            PlugInManager.addPlugIn( "net.sourceforge.jffmpeg.VideoDecoder", video.getSupportedInputFormats( ), video.getSupportedOutputFormats( null ), PlugInManager.CODEC );
            PlugInManager.commit( );
        }
        catch( Exception e ) {
        }
        printElapsedTime( "Zip library stuff completed" );

        // Set the Look&Feel
        try {
            javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName( ) );
        }
        catch( Exception e ) {
            e.printStackTrace( );
        }
        printElapsedTime( "Look And Feel completed" );
        String adventureName = "integration";

        ResourceHandler.setRestrictedMode( true, false );
        printElapsedTime( "Restricted mode completed" );
        ResourceHandler.getInstance( ).setZipFile( adventureName + ".zip" );
        printElapsedTime( "Zip file set" );
        Game.create( );
        printElapsedTime( "Game created" );
        Game.getInstance( ).setAdventureName( adventureName );
        printElapsedTime( "Adventure name set. Starting execution of the game" );
        Game.getInstance( ).run( );
        Game.delete( );
        ResourceHandler.getInstance( ).closeZipFile( );
        ResourceHandler.delete( );
        System.exit( 0 );

    }
}
