package es.eucm.eadventure.editor.control.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.editor.control.auxiliar.FileFilter;

/**
 * Filter for ZIP files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class EADAndFolderFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept XML files and folders
		return file.getAbsolutePath().toLowerCase( ).endsWith( ".ead" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Project folders and <e-Adventure> Files (*.ead)";
	}
}
