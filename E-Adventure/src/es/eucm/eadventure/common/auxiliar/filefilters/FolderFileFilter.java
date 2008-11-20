package es.eucm.eadventure.common.auxiliar.filefilters;

import java.io.File;
import java.util.ArrayList;

import es.eucm.eadventure.common.auxiliar.FileFilter;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.common.loader.incidences.Incidence;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * Filter for ZIP files (and folders).
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class FolderFileFilter extends FileFilter {

	private boolean checkName;
	
	private boolean checkDescriptor;
	
	public FolderFileFilter ( boolean checkName, boolean checkDescriptor ){
		this.checkName = checkName;
		this.checkDescriptor = checkDescriptor;
	}
	
	/**
	 * Accepted characters: Letters, numbers and [,],(,),_
	 * @param name
	 * @return
	 */
	public static boolean checkCharacters ( String name ){
		boolean correct = true;
		for (int i=0; i<name.length( ); i++){
			correct &=  (name.charAt( i )>='A' && name.charAt( i )<='Z') ||
						(name.charAt( i )>='a' && name.charAt( i )<='z') ||
						(name.charAt( i )>='0' && name.charAt( i )<='9') ||
						name.charAt( i )=='[' || name.charAt( i )==']' ||
						name.charAt( i )=='(' || name.charAt( i )==')' ||
						name.charAt( i )=='_';
		}
		return correct;
	}
	
	/**
	 * Returns an String with the allowed characters for project folders
	 */
	public static String getAllowedChars(){
		return "a-z, A-Z, 0-9, [, ], (, ), _";  
	}
	
	@Override
	public boolean accept( File file ) {
		// Accept folders
		boolean accepted = file.isDirectory( );
		if (accepted && checkName)
			accepted &= checkCharacters( file.getName( ) );
		if (accepted && checkDescriptor){
			boolean containsDescriptor = false;
			boolean descriptorValid = false;
			for (String child: file.list( )){
				if (child.equals( "descriptor.xml" )){
					containsDescriptor = true;
					break;
				}
			}
			if (containsDescriptor){
				DescriptorData descriptor = Loader.loadDescriptorData( AssetsController.getInputStreamCreator(file.getAbsolutePath( )) );
				descriptorValid = descriptor!=null;
			}
			accepted &= containsDescriptor && descriptorValid;
		}
		return accepted;
	}

	@Override
	public String getDescription( ) {
		// Description of the filter
		return "Folders";
	}
}
