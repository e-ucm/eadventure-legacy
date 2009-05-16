package es.eucm.eadventure.editor.auxiliar.filefilters;

import java.io.File;

import javax.swing.JFileChooser;

import es.eucm.eadventure.common.auxiliar.FileFilter;

/**
 * Filter for ZIP files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class EADAndFolderFileFilter extends FileFilter {
	
	private JFileChooser fileChooser;
	
	public EADAndFolderFileFilter(JFileChooser startDialog) {
		this.fileChooser = startDialog;
	}

	@Override
	public boolean accept( File file ) {
		// Accept XML files and folders
		File[] files = fileChooser.getCurrentDirectory().listFiles();
		for (int i = 0; i < files.length; i++) {
			if (file.isDirectory() && (file.getAbsolutePath().toLowerCase() + ".eap").equals(files[i].getAbsolutePath().toLowerCase()))
				return false;
		}
		return file.getAbsolutePath().toLowerCase( ).endsWith( ".ead" ) || file.getAbsolutePath().toLowerCase( ).endsWith( ".eap" ) || file.isDirectory( );
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "<e-Adventure> Files (*.ead) and Projects (*.eap)";
	}
}
