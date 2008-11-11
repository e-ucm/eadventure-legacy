package es.eucm.eadventure.editor.control.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.editor.control.auxiliar.FileFilter;

/**
 * Filter for MP3 audio files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class MP3FileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept WAV files and folders
		return file.toString( ).toLowerCase( ).endsWith( ".mp3" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "MP3 Files (*.mp3)";
	}
}
