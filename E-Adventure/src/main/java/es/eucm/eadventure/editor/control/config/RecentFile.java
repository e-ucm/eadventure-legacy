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
package es.eucm.eadventure.editor.control.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import es.eucm.eadventure.common.auxiliar.ReportDialog;

public class RecentFile {

    private String absolutePath;

    private Date date;

    /**
     * @param absolutePath
     * @param date
     */
    public RecentFile( String absolutePath, Date date ) {

        this.absolutePath = absolutePath;
        this.date = date;
    }

    public RecentFile( String absolutePath ) {

        this.absolutePath = absolutePath;
        date = new Date( );
    }

    public RecentFile( String absolutePath, String date ) throws ParseException {

        this.absolutePath = absolutePath;
        this.date = DateFormat.getDateInstance( ).parse( date );
    }

    /**
     * @return the absolutePath
     */
    public String getAbsolutePath( ) {

        return absolutePath;
    }

    /**
     * @param absolutePath
     *            the absolutePath to set
     */
    public void setAbsolutePath( String absolutePath ) {

        this.absolutePath = absolutePath;
    }

    /**
     * @return the date
     */
    public Date getDate( ) {

        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate( Date date ) {

        this.date = date;
    }

    @Override
    public String toString( ) {

        DateFormat format = DateFormat.getDateInstance( );
        return format.format( date );
    }

    public static void main( String[] args ) {

        Date hoy = new Date( );

        //System.out.println( hoy.getTime( ) );

        DateFormat format = DateFormat.getDateTimeInstance( );
        String string = format.format( hoy );
        System.out.println( string );
        try {
            Date newDate = format.parse( string );
            System.out.println( newDate );
        }
        catch( ParseException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

    }
}
