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
