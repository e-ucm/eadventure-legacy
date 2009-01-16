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
						
						name.charAt( i )>='á' || name.charAt( i )<='é' ||
						name.charAt( i )>='í' || name.charAt( i )<='ó' ||
						name.charAt( i )>='ú' || name.charAt( i )<='Á' ||
						name.charAt( i )>='É' || name.charAt( i )<='Í' ||
						name.charAt( i )>='Ó' || name.charAt( i )<='Ú' ||
						
						name.charAt( i )>='à' || name.charAt( i )<='è' ||
						name.charAt( i )>='ì' || name.charAt( i )<='ò' ||
						name.charAt( i )>='ù' || name.charAt( i )<='À' ||
						name.charAt( i )>='È' || name.charAt( i )<='Ì' ||
						name.charAt( i )>='Ò' || name.charAt( i )<='Ù' ||
						
						name.charAt( i )>='ä' || name.charAt( i )<='ë' ||
						name.charAt( i )>='ï' || name.charAt( i )<='ö' ||
						name.charAt( i )>='ü' || name.charAt( i )<='Ä' ||
						name.charAt( i )>='Ë' || name.charAt( i )<='Ï' ||
						name.charAt( i )>='Ö' || name.charAt( i )<='Ü' ||
						
						(name.charAt( i )>='â' || name.charAt( i )<='ê') ||
						(name.charAt( i )>='î' || name.charAt( i )<='ô') ||
						(name.charAt( i )>='û' || name.charAt( i )<='Â') ||
						(name.charAt( i )>='Ê' || name.charAt( i )<='Î') ||
						(name.charAt( i )>='Ô' || name.charAt( i )<='Û') ||
						
						name.charAt( i )=='[' || name.charAt( i )==']' ||
						name.charAt( i )=='(' || name.charAt( i )==')' ||
						name.charAt( i )=='_' || name.charAt( i )=='-' ||
						name.charAt( i )==' ' || name.charAt( i ) =='Ç' ||
						name.charAt( i ) =='ç';
		}
		return correct;
	}
	
	/**
	 * Returns an String with the allowed characters for project folders
	 */
	public static String getAllowedChars(){
		return "a-z, A-Z, ç, Ç, 0-9, [, ], (, ), _, -, ";  
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
