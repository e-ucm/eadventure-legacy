package es.eucm.eadventure.common.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for JPG set of slide files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class JPGSlidesFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept JPG files and folders
		String filename = file.toString( ).toLowerCase( );
		return filename.endsWith( "_01.jpg" ) || filename.endsWith(".eaa") || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "JPG Set of slides (*_01.jpg;*.eaa)";
	}
}
