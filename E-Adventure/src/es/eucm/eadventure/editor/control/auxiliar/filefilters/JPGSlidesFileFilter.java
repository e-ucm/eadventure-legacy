package es.eucm.eadventure.editor.control.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.editor.control.auxiliar.FileFilter;

/**
 * Filter for JPG set of slide files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class JPGSlidesFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept JPG files and folders
		return file.toString( ).toLowerCase( ).endsWith( "_01.jpg" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "JPG Set of slides (*_01.jpg)";
	}
}
