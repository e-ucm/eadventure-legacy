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

import java.io.File;

import javax.media.Codec;
import javax.media.PlugInManager;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.config.ConfigData;
import es.eucm.eadventure.engine.gamelauncher.GameLauncher;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added - Support for .ead
 *          files. Therefore <e-Adventure> files are no longer .zip but .ead
 * @updated by Enrique L�pez. Functionalities added (10/2008) - Multilanguage
 *          support. Two new classes added
 */
public class EAdventure {

    public static String VERSION = "1.5";

    public static String languageFile = ReleaseFolders.LANGUAGE_UNKNOWN;

    /**
     * Sets the current language of the editor. Accepted values are
     * {@value #LANGUAGE_ENGLISH} & {@value #LANGUAGE_ENGLISH}. This method
     * automatically updates the about, language strings, and loading image
     * parameters.
     * 
     * The method will reload the main window if reloadData is true
     * 
     * @param language
     */
    public static void setLanguage( String language ) {

        if( true ) {
            ConfigData.setLanguangeFile( ReleaseFolders.getLanguageFilePath( language ), ReleaseFolders.getAboutFilePath( language ) );
            languageFile = language;
            TC.loadStrings( ReleaseFolders.getLanguageFilePath4Engine( languageFile ) );
        }
    }

    /**
     * Launchs a new e-Adventure game
     * 
     * @param args
     *            Arguments
     */
    public static void main( String[] args ) {

        // Load the configuration
        ConfigData.loadFromXML( ReleaseFolders.configFileEngineRelativePath( ) );

        if( args.length >= 2 ) {
            setLanguage( ReleaseFolders.getLanguageFromPath( args[1] ) );
        }
        else
            setLanguage( ReleaseFolders.getLanguageFromPath( ConfigData.getLanguangeFile( ) ) );

        try {
            Codec video = (Codec) Class.forName( "net.sourceforge.jffmpeg.VideoDecoder" ).newInstance( );
            PlugInManager.addPlugIn( "net.sourceforge.jffmpeg.VideoDecoder", video.getSupportedInputFormats( ),  video.getSupportedOutputFormats( null ), PlugInManager.CODEC );
            PlugInManager.commit( );
        }
        catch( Exception e ) {
        }

        // Set the Look&Feel
        try {
            javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName( ) );
        }
        catch( Exception e ) {
            e.printStackTrace( );
        }

        GameLauncher gameLauncher = new GameLauncher( );

        File file = new File( "" );
        if( args.length >= 1 ) {
            file = new File( args[0] );
            if( !args[0].equals( "" ) && !file.exists( ) ) {
                JOptionPane.showMessageDialog( null, TC.get( "ErrorMessage.Title" ), TC.get( "ErrorMessage.Content" ), JOptionPane.ERROR_MESSAGE );
                file = new File( "" );
            }
        }

        gameLauncher.init( file );
        new Thread( gameLauncher ).start( );
    }
}
