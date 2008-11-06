package es.eucm.eadventure.adventureeditor.control.config;

import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}

	}
}
