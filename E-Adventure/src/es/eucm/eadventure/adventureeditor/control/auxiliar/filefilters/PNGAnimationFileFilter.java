package es.eucm.eadventure.adventureeditor.control.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.adventureeditor.control.auxiliar.FileFilter;

/**
 * Filter for PNG animation files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class PNGAnimationFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept PNG files and folders
		return file.toString( ).toLowerCase( ).endsWith( "_01.png" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "PNG Animations (*_01.png)";
	}
}
