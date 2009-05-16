package es.eucm.eadventure.editor.auxiliar.filefilters;

import java.io.File;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for XML files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
public class XMLFileFilter extends FileFilter {

	@Override
	public boolean accept( File file ) {
		// Accept XML files and folders
		return file.toString( ).toLowerCase( ).endsWith( ".xml" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "XML Files (*.xml)";
	}
}
