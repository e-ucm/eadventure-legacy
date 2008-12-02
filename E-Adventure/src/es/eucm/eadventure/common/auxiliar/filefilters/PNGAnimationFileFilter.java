package es.eucm.eadventure.common.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for PNG animation files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class PNGAnimationFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept PNG files and folders
		String filename = file.toString( ).toLowerCase( );
		return filename.endsWith( "_01.png" ) || filename.endsWith( ".eaa") || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "PNG Animations (*_01.png;*.eaa)";
	}
}
