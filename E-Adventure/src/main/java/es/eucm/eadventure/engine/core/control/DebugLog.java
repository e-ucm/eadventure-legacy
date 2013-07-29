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
package es.eucm.eadventure.engine.core.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.eucm.eadventure.engine.core.gui.DebugLogPanel;

public class DebugLog {

    private static final String DATE_FORMAT_NOW = "HH:mm:ss";

    public static final int GENERAL = 0;

    public static final int USER = 1;

    public static final int PLAYER = 2;

    private static DebugLog instance;

    private List<String> text;

    private DebugLogPanel debugFrameLog;

    public DebugLog( ) {

        text = new ArrayList<String>( );
    }

    public static DebugLog getInstance( ) {

        if( instance == null )
            instance = new DebugLog( );
        return instance;
    }

    public static void player( String text ) {

        getInstance( ).logEntry( DebugLog.PLAYER, text );
    }

    public static void general( String text ) {

        getInstance( ).logEntry( DebugLog.GENERAL, text );
    }

    public static void user( String text ) {

        getInstance( ).logEntry( DebugLog.USER, text );
    }

    private void logEntry( int type, String line ) {

        text.add( line );
        if( debugFrameLog != null )
            debugFrameLog.addLine( type, now( ), line );
    }

    public String now( ) {

        SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT_NOW );
        return sdf.format( Calendar.getInstance( ).getTime( ) );
    }

    public void setDebugFrameLog( DebugLogPanel debugFrameLog ) {

        this.debugFrameLog = debugFrameLog;
    }

    public void clear( ) {

        text.clear( );
        debugFrameLog = null;
    }
}
