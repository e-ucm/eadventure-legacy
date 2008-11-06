package es.eucm.eadventure.adventureeditor.control.auxiliar.categoryfilters;

import java.io.File;

import es.eucm.eadventure.adventureeditor.control.auxiliar.FileFilter;

public class FormattedTextFileFilter extends FileFilter{

	@Override
	public boolean accept( File file ) {
		String fileName = file.toString( ).toLowerCase( );
		return (fileName.endsWith( "rtf" ) || fileName.endsWith( "htm" ) || fileName.endsWith( "html" ) || file.isDirectory( ));
	}

	@Override
	public String getDescription( ) {
		return "RTF and HTML files (*.rtf, *.html, *.htm)";
	}

}
