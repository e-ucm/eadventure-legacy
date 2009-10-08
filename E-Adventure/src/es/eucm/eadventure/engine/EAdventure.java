/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine;

import java.io.File;

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;
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
 * @updated by Enrique López. Functionalities added (10/2008) - Multilanguage
 *          support. Two new classes added
 */
public class EAdventure {

    public static String VERSION = "1.0b";

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
            PlugInManager.addPlugIn( "net.sourceforge.jffmpeg.VideoDecoder", video.getSupportedInputFormats( ), new Format[] { new VideoFormat( VideoFormat.MPEG ) }, PlugInManager.CODEC );
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
        /*if (args.length == 0) 
            gameLauncher.init( new File( "" ), "" );
        else {
        	if ( !args[0].equals("")) {
        		
        		File file = new File(args[0]);
        	 
        		if (file.exists( )){
        			gameLauncher.init( new File(args[0]),args[1] );
          
        		} else {
                
        			JOptionPane.showMessageDialog(null,
                        TextConstants.getText("ErrorMessage.Title"),
                        TextConstants.getText("ErrorMessage.Content"),
                        JOptionPane.ERROR_MESSAGE);
                
                gameLauncher.init(new File(""), "");
            }
        	}
        	else 
        		gameLauncher.init( new File( "" ), args[1] );
        	}*/
        new Thread( gameLauncher ).start( );

    }
}
