package es.eucm.eadventure.editor.control.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.editor.control.auxiliar.FileFilter;

/**
 * Filter for JPG image files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class JPGFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept JPG and JPEG files and folders
		return file.toString( ).toLowerCase( ).endsWith( ".jpg" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "JPG Files (*.jpg)";
	}
}
