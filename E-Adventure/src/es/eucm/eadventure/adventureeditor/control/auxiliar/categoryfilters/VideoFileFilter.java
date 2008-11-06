package es.eucm.eadventure.adventureeditor.control.auxiliar.categoryfilters;

import java.io.File;

import es.eucm.eadventure.adventureeditor.control.auxiliar.FileFilter;

public class VideoFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept MPG and AVI files and folders
		String filename = file.toString( ).toLowerCase( );
		return filename.endsWith( ".mov" ) || filename.endsWith( ".mpg" ) || filename.endsWith( ".avi" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Video files (*.mov;*.mpg;*.avi)";
	}

}
