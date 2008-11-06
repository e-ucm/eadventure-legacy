package es.eucm.eadventure.adventureeditor.control.auxiliar.categoryfilters;

import java.io.File;

import es.eucm.eadventure.adventureeditor.control.auxiliar.FileFilter;

/**
 * Filter for PNG and JPG animation files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class AnimationFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept PNG and JPG files and folders
		String filename = file.toString( ).toLowerCase( );
		return filename.endsWith( "_01.png" ) || filename.endsWith( "_01.jpg" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Animations (*_01.png;*_01.jpg)";
	}
}
