package es.eucm.eadventure.editor.auxiliar.categoryfilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for MP3 and MIDI audio files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class AudioFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept MP3 and MIDI files and folders
		String filename = file.toString( ).toLowerCase( );
		return filename.endsWith( ".mp3" ) || filename.endsWith( ".mid" ) || filename.endsWith( ".midi" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Audio files (*.mp3;*.mid;*.midi)";
	}
}
