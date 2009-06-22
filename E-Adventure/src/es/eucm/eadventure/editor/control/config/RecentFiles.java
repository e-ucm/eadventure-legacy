package es.eucm.eadventure.editor.control.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.auxiliar.File;

public class RecentFiles {

	public static final int OPENED_TODAY = 0;

	public static final int OPENED_YESTERDAY = 1;

	public static final int OPENED_THIS_WEEK = 2;

	public static final int OPENED_THIS_MONTH = 3;

	public static final int MAX_FILES = 10;

	private RecentFile[] recentFiles;

	private int nFiles;

	public RecentFiles( Properties properties ) {
		nFiles = 0;
		if( properties.containsKey( "RecentFiles" ) ) {
			try {
				nFiles = Integer.parseInt( properties.getProperty( "RecentFiles" ) );
			} catch (Exception e){
				// If any problem reading or parsing the number of recent files, do not use that field
			}
		}
		recentFiles = new RecentFile[MAX_FILES];
		int nCorrectFiles = nFiles;
		for( int i = 0; i < nFiles; i++ ) {
			String pathKey = "RecentFile." + i + ".FilePath";
			String path = properties.getProperty( pathKey );
			File file = new File( path );
			if( file.exists( ) ) {
				String dateKey = "RecentFile." + i + ".DateOpened";
				String date = properties.getProperty( dateKey );
				try {

					RecentFile recentFile = new RecentFile( path, date );
					recentFiles[i] = recentFile;
				} catch( ParseException e ) {
		        	//ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
					// If any errors, just discard the recent file
					recentFiles[i] = null;
					nCorrectFiles--;
				}
			} else {
				recentFiles[i] = null;
				nCorrectFiles--;
			}

		}

		RecentFile[] temp = new RecentFile[MAX_FILES];
		for( int i = 0, j = 0; i < nFiles; i++ ) {
			if( recentFiles[i] != null ) {
				temp[j] = recentFiles[i];
				j++;
			}
		}
		nFiles = nCorrectFiles;
		recentFiles = temp;

		orderFilesByDate( );
	}

	public void fillProperties( Properties properties ) {
		int i = 0;
		for( RecentFile file : recentFiles ) {
			if( i >= nFiles )
				break;
			String pathKey = "RecentFile." + i + ".FilePath";
			String path = file.getAbsolutePath( );
			properties.put( pathKey, path );
			String dateKey = "RecentFile." + i + ".DateOpened";
			String date = file.toString( );
			properties.put( dateKey, date );
			i++;
		}
		String nFilesKey = "RecentFiles";
		String nFiles = Integer.toString( this.nFiles );
		properties.put( nFilesKey, nFiles );

	}

	public String[][] getRecentFilesInfo( int r ) {
		ArrayList<RecentFile> toReturn = new ArrayList<RecentFile>( );
		//Date today = new Date();
		Calendar right = Calendar.getInstance( );
		right.add( Calendar.DAY_OF_YEAR, -r );
		right.set( Calendar.HOUR_OF_DAY, 0 );
		int n = 0;
		for( RecentFile file : this.recentFiles ) {
			if( n >= nFiles )
				break;
			Calendar actual = Calendar.getInstance( );
			actual.setTime( file.getDate( ) );
			if( actual.compareTo( right ) <= 0 ) {
				toReturn.add( file );
			}
			n++;
		}

		String[][] info = new String[toReturn.size( )][3];
		for( int i = 0; i < toReturn.size( ); i++ ) {
			info[i][0] = toReturn.get( i ).getAbsolutePath( );
			info[i][1] = DateFormat.getDateInstance( ).format( toReturn.get( i ).getDate( ) );
			info[i][2] = DateFormat.getTimeInstance( ).format( toReturn.get( i ).getDate( ) );
		}
		return info;
	}

	public String[][] getRecentFilesInfo( int l, int r ) {
		ArrayList<RecentFile> toReturn = new ArrayList<RecentFile>( );
		//Date today = new Date();
		Calendar left = Calendar.getInstance( );
		left.add( Calendar.DAY_OF_YEAR, -l );
		left.set( Calendar.HOUR_OF_DAY, 0 );
		Calendar right = Calendar.getInstance( );
		right.add( Calendar.DAY_OF_YEAR, -r );
		right.set( Calendar.HOUR_OF_DAY, 0 );
		int n = 0;
		for( RecentFile file : this.recentFiles ) {
			if( n >= nFiles )
				break;

			Calendar actual = Calendar.getInstance( );
			actual.setTime( file.getDate( ) );
			if( actual.compareTo( right ) <= 0 && actual.compareTo( left ) > 0 ) {
				toReturn.add( file );
			}
			n++;
		}

		String[][] info = new String[toReturn.size( )][3];
		for( int i = 0; i < toReturn.size( ); i++ ) {
			info[i][0] = toReturn.get( i ).getAbsolutePath( );
			info[i][1] = DateFormat.getDateInstance( ).format( toReturn.get( i ).getDate( ) );
			info[i][2] = DateFormat.getTimeInstance( ).format( toReturn.get( i ).getDate( ) );
		}
		return info;
	}

	public void orderFilesByDate( ) {
		for( int i = 0; i < nFiles; i++ ) {
			RecentFile minDate = recentFiles[i];
			int minPos = i;
			//Seek the min value
			for( int j = i + 1; j < nFiles; j++ ) {
				if( recentFiles[j].getDate( ).before( minDate.getDate( ) ) ) {
					minDate = recentFiles[j];
					minPos = j;
				}
			}
			//Swap min value, locating it in pos. i: i<->minPos
			recentFiles[minPos] = recentFiles[i];
			recentFiles[i] = minDate;
		}
	}

	public void fileLoaded( String path ) {
		//Browse the array to find occurrences of the file
		boolean inserted = false;
		for( int i = 0; i < nFiles; i++ ) {
			RecentFile file = recentFiles[i];

			//If the file exists in the array (previously opened), update its date
			if( file.getAbsolutePath( ).equals( path ) ) {
				file.setDate( new Date( ) );
				inserted = true;
			}
		}

		//If file was not found, insert it into the array, removing the "oldest" if there's no room enough
		if( !inserted ) {
			RecentFile newFile = new RecentFile( path );

			//If there is no room
			if( nFiles == recentFiles.length ) {
				//Remove the oldest. As it is ordered, it is the first one. Replace it by the new one
				recentFiles[0] = newFile;
			}
			//In case there is room enough, just insert it in the las position
			else {
				recentFiles[nFiles] = newFile;
				nFiles++;
			}
		}

		//Finally, order the array
		this.orderFilesByDate( );
	}

	public static void main( String[] args ) {

		Date today = DateFormat.getDateInstance( ).getCalendar( ).getTime( );
		System.out.println( today );

		long milis = today.getTime( );
		long min = milis - 0 * 86400000;
		long max = milis - 2 * 86400000;
		Date minD = new Date( min );
		Date maxD = new Date( max );
		System.out.println( minD );
		System.out.println( maxD );
	}

}
