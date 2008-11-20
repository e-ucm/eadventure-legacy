package es.eucm.eadventure.common.loader;

import java.io.InputStream;

/**
 * Constructs the InputStream of a file located in a parent structure, which is abstracted by this entity
 * Entities aiming to use Loader must implement this interface
 * @author Javier
 *
 */
public interface InputStreamCreator {

	/**
	 * Builds the inputStream Object of a filePath which is stored in "parent", where the
	 * implementation of parent is undefined
	 * @param filePath
	 * 				Path of the file
	 */
	public InputStream buildInputStream ( String filePath );
	
	/**
	 * If filePath is a directory in parentPath, the list of its children is given 
	 * @param filePath
	 * @return
	 */
	public String[] listNames ( String filePath );
	
}
