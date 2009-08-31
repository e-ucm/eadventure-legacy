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
package es.eucm.eadventure.editor.control.controllers.metadata.lomes.time;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class LOMESTimeDataControl extends LOMESDurationDataControl {

    public static final int MILISECONDS = 6;

    public static final int TIMEZONE = 7;

    protected int milisec;

    protected String timeZone;

    public LOMESTimeDataControl( ) {

        super( );
        milisec = 0;
        timeZone = new String( );
    }

    @Override
    public String toString( ) {

        String value = new String( );
        if( years > 0 ) {
            value += years + "-";
        }
        if( months > 0 && months < 13 ) {
            if( months < 10 )
                value += "0" + months + "-";
            else
                value += months + "-";

        }
        if( days > 0 && days < 32 ) {
            if( days < 10 )
                value += "0" + days;
            else
                value += days;
        }
        if( hours > 0 && minutes > 0 && seconds > 0 ) {
            value += "T";

            if( hours > 0 && hours < 24 ) {
                value += hours;
            }
            if( minutes > 0 && minutes < 60 ) {
                value += ":" + minutes;
            }
            if( seconds > 0 && seconds < 60 ) {
                value += ":" + seconds;
            }
            if( milisec > 0 ) {
                value += "." + milisec;
            }

            if( timeZone != "" ) {
                value += timeZone;
            }
        }

        return value;

    }

    @Override
    public void parseDuration( String duration ) {

        if( duration.contains( "-" ) ) {
            int posY = duration.indexOf( "-" );
            years = Integer.parseInt( duration.substring( 0, posY ) );
            duration = duration.substring( posY + 1, duration.length( ) );
        }
        if( duration.contains( "-" ) ) {
            int posM = duration.indexOf( "-" );
            months = Integer.parseInt( duration.substring( 0, posM ) );
            duration = duration.substring( posM + 1, duration.length( ) );
        }
        if( duration.contains( "T" ) ) {
            int posD = duration.indexOf( "T" );
            days = Integer.parseInt( duration.substring( 0, posD ) );
            duration = duration.substring( posD + 1, duration.length( ) );

            if( duration.contains( ":" ) ) {
                int posH = duration.indexOf( ":" );
                hours = Integer.parseInt( duration.substring( 0, posH ) );
                duration = duration.substring( posH + 1, duration.length( ) );
            }
            if( duration.contains( ":" ) ) {
                int posM = duration.indexOf( ":" );
                minutes = Integer.parseInt( duration.substring( 0, posM ) );
                duration = duration.substring( posM + 1, duration.length( ) );
            }
            if( duration.contains( "." ) ) {
                int posS = duration.indexOf( "." );
                seconds = Integer.parseInt( duration.substring( 0, posS ) );
                duration = duration.substring( posS + 1, duration.length( ) );
            }
            else {
                seconds = Integer.parseInt( duration );
                duration = "";
            }

            int posMS = -1;
            if( duration.contains( "Z" ) ) {
                posMS = duration.indexOf( "Z" );
            }
            if( duration.contains( "+" ) ) {
                posMS = duration.indexOf( "+" );
            }
            if( duration.contains( "-" ) ) {
                posMS = duration.indexOf( "-" );
            }

            if( posMS != -1 ) {
                milisec = Integer.parseInt( duration.substring( 0, posMS ) );
                duration = duration.substring( posMS, duration.length( ) );
                timeZone = duration;
            }
            else if( !duration.equals( "" ) ) {
                milisec = Integer.parseInt( duration );
            }
        }
        else {
            days = Integer.parseInt( duration );
            duration = "";
        }

    }

    @Override
    protected String paramToString( int param ) {

        String paramString = super.paramToString( param );
        if( paramString == null ) {
            switch( param ) {
                case MILISECONDS:
                    paramString = TextConstants.getText( "LOMES.Date.Miliseconds" );
                    break;
                case TIMEZONE:
                    paramString = TextConstants.getText( "LOMES.Date.TimeZone" );
                    break;
            }
        }
        return paramString;

    }

    @Override
    protected boolean setParameter( int param, String value ) {

        super.setParameter( param, value );
        boolean set = false;
        try {

            int intValue = -1;
            if( !( param == TIMEZONE ) ) {
                if( value == null || value.equals( "" ) || Integer.parseInt( value ) > 0 ) {
                    if( value == null || value.equals( "" ) ) {
                        intValue = 0;
                    }
                    else {
                        intValue = Integer.parseInt( value );
                    }
                    milisec = intValue;
                    set = true;
                }
            }
            else {
                timeZone = value;
                set = true;
            }

        }
        catch( Exception e ) {
        }

        // Display error message
        if( !set ) {
            Controller.getInstance( ).showErrorDialog( TextConstants.getText( "LOM.Duration.InvalidValue.Title" ), TextConstants.getText( "LOM.Duration.InvalidValue.Message", paramToString( param ) ) );
        }

        return set;

    }

    public boolean setMiliSeconds( String s ) {

        return setParameter( MILISECONDS, s );
    }

    public boolean setTimeZone( String s ) {

        return setParameter( TIMEZONE, s );
    }

    /**
     * @return the milisec
     */
    public String getMilisec( ) {

        if( milisec > 0 )
            return Integer.toString( milisec );
        else
            return "";
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone( ) {

        return timeZone;
    }

}
