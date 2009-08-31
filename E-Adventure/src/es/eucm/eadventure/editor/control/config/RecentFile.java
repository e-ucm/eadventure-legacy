/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.config;

import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
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
	 * @param absolutePath the absolutePath to set
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
	 * @param date the date to set
	 */
	public void setDate( Date date ) {
		this.date = date;
	}

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
		} catch( ParseException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

	}
}
