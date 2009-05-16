package es.eucm.eadventure.editor.auxiliar.categoryfilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for JPG and PNG image files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class ImageFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept PNG and JPG files and folders
		String filename = file.toString( ).toLowerCase( );
		return filename.endsWith( ".png" ) || filename.endsWith( ".jpg" ) || 
				filename.endsWith( ".bmp" ) || filename.endsWith( ".wbmp") ||
				filename.endsWith( ".gif") || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Image files (*.png;*.jpg;*.bmp;*.wbmp;*.gif)";
	}
}
