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
package es.eucm.eadventure.engine.core.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class stores the options of the game
 */
public class Options {

    /**
     * Number of speeds for the text speed parameter
     */
    public static final int TEXT_NUM_SPEEDS = 3;

    /**
     * Slow speed for text display
     */
    public static final int TEXT_SLOW = 0;

    /**
     * Normal speed for text display
     */
    public static final int TEXT_NORMAL = 1;

    /**
     * Fast speed for text display
     */
    public static final int TEXT_FAST = 2;

    /**
     * Texts for the options of the text speed.
     */
    public static final String[] TEXT_SPEED_PRINT_VALUES = { GameText.TEXT_SLOW, GameText.TEXT_NORMAL, GameText.TEXT_FAST };

    /**
     * Options of the game stored in a Properties structure
     */
    private Properties options;

    /**
     * Constructor, sets the default options
     */
    public Options( ) {

        options = new Properties( );

        // Set the default values
        options.setProperty( "Music", "On" );
        options.setProperty( "FunctionalEffects", "On" );
        options.setProperty( "TextSpeed", "1" );
    }

    /**
     * Loads the options from a file, if possible
     * 
     * @param optionsPath
     *            Options file path
     * @param optionsFile
     *            Options filename
     */
    public void loadOptions( String optionsPath, String optionsFile ) {

        InputStream is = ResourceHandler.getInstance( ).getResourceAsStream( optionsPath + optionsFile + "-opt.xml" );
        if( is != null ) {
            try {
                options.loadFromXML( is );
                is.close( );
            }
            catch( IOException e ) {
                ReportDialog.GenerateErrorReport( e, Game.getInstance( ).isFromEditor( ), "UNKNOWERROR" );
            }
        }
    }

    /**
     * Saves the options from a file, if possible
     * 
     * @param optionsPath
     *            Options file path
     * @param optionsFile
     *            Options filename
     */
    public void saveOptions( String optionsPath, String optionsFile ) {

        OutputStream os = ResourceHandler.getInstance( ).getOutputStream( optionsPath + optionsFile + "-opt.xml" );
        if( os != null ) {
            try {
                options.storeToXML( os, "eAdventure game options", "ISO-8859-1" );
            }
            catch( IOException e ) {
                ReportDialog.GenerateErrorReport( e, Game.getInstance( ).isFromEditor( ), "UNKNOWERROR" );
            }
        }
    }

    /**
     * Returns the state of the music
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isMusicActive( ) {

        return options.getProperty( "Music" ).equals( "On" );
    }

    /**
     * Returns the state of the effects
     * 
     * @return True if the effects are active, false otherwise
     */
    public boolean isEffectsActive( ) {

        return options.getProperty( "FunctionalEffects" ).equals( "On" );
    }

    /**
     * Returns the speed of the text
     * 
     * @return TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public int getTextSpeed( ) {

        return Integer.parseInt( options.getProperty( "TextSpeed" ) );
    }

    /**
     * Sets the state of the music
     * 
     * @param active
     *            True if the music must be activated, false if deactivated
     */
    public void setMusicActive( boolean active ) {

        options.setProperty( "Music", ( active ? "On" : "Off" ) );
    }

    /**
     * Sets the state of the effects
     * 
     * @param active
     *            True if the effects must be activated, false if deactivated
     */
    public void setEffectsActive( boolean active ) {

        options.setProperty( "FunctionalEffects", ( active ? "On" : "Off" ) );
    }

    /**
     * Sets the speed of the text
     * 
     * @param textSpeed
     *            TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public void setTextSpeed( int textSpeed ) {

        options.setProperty( "TextSpeed", String.valueOf( textSpeed ) );
    }
}
