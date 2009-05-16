package es.eucm.eadventure.editor.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for PNG image files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class PNGFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept PNG files and folders
		return file.toString( ).toLowerCase( ).endsWith( ".png" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "PNG Files (*.png)";
	}
}
