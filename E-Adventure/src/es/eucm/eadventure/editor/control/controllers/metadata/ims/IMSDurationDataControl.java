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
package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

public class IMSDurationDataControl {

    public static final int YEARS = 0;

    public static final int MONTHS = 1;

    public static final int DAYS = 2;

    public static final int HOURS = 3;

    public static final int MINUTES = 4;

    public static final int SECONDS = 5;

    protected int years;

    protected int months;

    protected int days;

    protected int hours;

    protected int minutes;

    protected int seconds;

    public IMSDurationDataControl( ) {

        years = months = days = hours = minutes = seconds = 0;
    }

    public void parseDuration( String duration ) {

        if( duration.startsWith( "P" ) ) {
            duration = duration.substring( 1, duration.length( ) );
            if( duration.contains( "Y" ) ) {
                int posY = duration.indexOf( "Y" );
                years = Integer.parseInt( duration.substring( 0, posY ) );
                duration = duration.substring( posY + 1, duration.length( ) );
            }
            if( duration.contains( "M" ) && duration.indexOf( "M" ) < duration.indexOf( "T" ) ) {
                int posM = duration.indexOf( "M" );
                months = Integer.parseInt( duration.substring( 0, posM ) );
                duration = duration.substring( posM + 1, duration.length( ) );
            }
            if( duration.contains( "D" ) ) {
                int posD = duration.indexOf( "D" );
                days = Integer.parseInt( duration.substring( 0, posD ) );
                duration = duration.substring( posD + 1, duration.length( ) );
            }
            if( duration.contains( "T" ) ) {
                duration = duration.substring( 1 );
                if( duration.contains( "H" ) ) {
                    int posH = duration.indexOf( "H" );
                    hours = Integer.parseInt( duration.substring( 0, posH ) );
                    duration = duration.substring( posH + 1, duration.length( ) );
                }
                if( duration.contains( "M" ) ) {
                    int posM = duration.indexOf( "M" );
                    minutes = Integer.parseInt( duration.substring( 0, posM ) );
                    duration = duration.substring( posM + 1, duration.length( ) );
                }
                if( duration.contains( "S" ) ) {
                    int posS = duration.indexOf( "S" );
                    seconds = Integer.parseInt( duration.substring( 0, posS ) );
                }

            }

        }
    }

    @Override
    public String toString( ) {

        String value = "P";
        if( years > 0 ) {
            value += years + "Y";
        }
        if( months > 0 ) {
            value += months + "M";
        }
        if( days > 0 ) {
            value += days + "D";
        }
        if( hours > 0 || minutes > 0 || seconds > 0 ) {
            value += "T";
        }
        if( hours > 0 ) {
            value += hours + "H";
        }
        if( minutes > 0 ) {
            value += minutes + "M";
        }
        if( seconds > 0 ) {
            value += seconds + "S";
        }

        return value;
    }

    /** ****************************** GETTERS ****************************** */
    /**
     * @return the years
     */
    public String getYears( ) {

        if( years > 0 )
            return Integer.toString( years );
        else
            return "";
    }

    /**
     * @return the months
     */
    public String getMonths( ) {

        if( months > 0 )
            return Integer.toString( months );
        else
            return "";
    }

    /**
     * @return the days
     */
    public String getDays( ) {

        if( days > 0 )
            return Integer.toString( days );
        else
            return "";
    }

    /**
     * @return the hours
     */
    public String getHours( ) {

        if( hours > 0 )
            return Integer.toString( hours );
        else
            return "";
    }

    /**
     * @return the minutes
     */
    public String getMinutes( ) {

        if( minutes > 0 )
            return Integer.toString( minutes );
        else
            return "";
    }

    /**
     * @return the seconds
     */
    public String getSeconds( ) {

        if( seconds > 0 )
            return Integer.toString( seconds );
        else
            return "";
    }

    /** ****************************** SETTERS ****************************** */
    private String paramToString( int param ) {

        String paramString = null;
        switch( param ) {
            case YEARS:
                paramString = TC.get( "LOM.Duration.Years" );
                break;
            case MONTHS:
                paramString = TC.get( "LOM.Duration.Months" );
                break;
            case DAYS:
                paramString = TC.get( "LOM.Duration.Days" );
                break;
            case HOURS:
                paramString = TC.get( "LOM.Duration.Hours" );
                break;
            case MINUTES:
                paramString = TC.get( "LOM.Duration.Minutes" );
                break;
            case SECONDS:
                paramString = TC.get( "LOM.Duration.Seconds" );
                break;
        }
        return paramString;

    }

    protected boolean setParameter( int param, String value ) {

        boolean set = false;
        int intValue = -1;
        try {
            if( value == null || value.equals( "" ) || Integer.parseInt( value ) > 0 ) {
                if( value == null || value.equals( "" ) ) {
                    intValue = 0;
                }
                else {
                    intValue = Integer.parseInt( value );
                }
                switch( param ) {
                    case YEARS:
                        years = intValue;
                        break;
                    case MONTHS:
                        months = intValue;
                        break;
                    case DAYS:
                        days = intValue;
                        break;
                    case HOURS:
                        hours = intValue;
                        break;
                    case MINUTES:
                        minutes = intValue;
                        break;
                    case SECONDS:
                        seconds = intValue;
                        break;
                }

                set = true;
            }
        }
        catch( Exception e ) {
        }

        // Display error message
        if( !set ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "LOM.Duration.InvalidValue.Title" ), TC.get( "LOM.Duration.InvalidValue.Message", paramToString( param ) ) );
        }

        return set;
    }

    public boolean setYears( String y ) {

        return setParameter( YEARS, y );
    }

    public boolean setMonths( String m ) {

        return setParameter( MONTHS, m );
    }

    public boolean setDays( String d ) {

        return setParameter( DAYS, d );
    }

    public boolean setHours( String h ) {

        return setParameter( HOURS, h );
    }

    public boolean setMinutes( String m ) {

        return setParameter( MINUTES, m );
    }

    public boolean setSeconds( String s ) {

        return setParameter( SECONDS, s );
    }

}
