package es.eucm.eadventure.editor.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for ZIP files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class JARFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept XML files and folders
		return file.getAbsolutePath().toLowerCase( ).endsWith( ".jar" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Java ARchive files (*.jar)";
	}
}
