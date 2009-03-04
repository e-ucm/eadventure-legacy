package es.eucm.eadventure.common.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * This class holds all the methods to load and handle the text strings to the rest of the application.
 * 
 * @author Bruno Torijano Bueno
 */
public class TextConstants {

	/**
	 * Properties set containing the strings.
	 */
	private static Properties guiStrings;

    /**
     * Loads the strings of the application from the given XML properties file.
     * 
     * @param languageFile
     *            Name of the file containing the text
     */
    public static void loadStrings( String languageFile ) {
        try {
            loadStrings ( new FileInputStream( languageFile ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
    }

	/**
	 * Loads the strings of the application from the given set of XML properties files.
	 * 
	 * @param languageFiles
	 *            List with the names of the file containing the text
	 */
	public static void loadStrings( String[] languageFiles ) {
		if (languageFiles!=null && languageFiles.length>0){
			loadStrings (languageFiles[0]);
			for (int i=1; i<languageFiles.length; i++)
				appendStrings (languageFiles[i]);
		}else{
			guiStrings = new Properties();
		}
	}

    
	/**
	 * Loads the strings of the application from the given set of XML properties files.
	 * 
	 * @param languageFiles
	 *            List with the names of the file containing the text
	 */
	public static void loadStrings( InputStream[] languageFiles ) {
		if (languageFiles!=null && languageFiles.length>0){
			loadStrings (languageFiles[0]);
			for (int i=1; i<languageFiles.length; i++)
				appendStrings (languageFiles[i]);
		}else{
			guiStrings = new Properties();
		}
	}

    
	/**
	 * Loads the strings of the application from the given XML properties file.
	 * 
	 * @param languageFile
	 *            Name of the file containing the text
	 */
	public static void loadStrings( InputStream languageFile ) {
		try {
			guiStrings = new Properties( );
			guiStrings.loadFromXML( languageFile );
		}

		// If the file is bad formed
		catch( InvalidPropertiesFormatException e ) {
			JOptionPane.showMessageDialog( null, "The language file is bad-formed, please try to use another language file.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
		}

		// If the file was not found
		catch( FileNotFoundException e ) {
			JOptionPane.showMessageDialog( null, "The language file was not found, please verify that the \"config.xml\" file contains a reference to a valid language file.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
		}

		// If there was a I/O exception
		catch( IOException e ) {
			JOptionPane.showMessageDialog( null, "There has been an error loading the language file, please check for problem accessing the files.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
		}
	}

	/**
	 * Loads the language file specified as argument but does not clear the properties structure, appending
	 * both contents. The results are equivalent to:
	 * properties = properties.append(propertiesFromNewFile)
	 */
	public static void appendStrings ( String languageFile ){
        try {
            appendStrings ( new FileInputStream( languageFile ) );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
	}

	
	/**
	 * Loads the language file specified as argument but does not clear the properties structure, appending
	 * both contents. The results are equivalent to:
	 * properties = properties.append(propertiesFromNewFile)
	 */
	public static void appendStrings ( InputStream languageFile ){
		try {
			Properties newStrings = new Properties( );
			newStrings.loadFromXML( languageFile );
			for ( Object key: newStrings.keySet() ){
				guiStrings.put( key, newStrings.get( key ) );
			}
		}

		// If the file is bad formed
		catch( InvalidPropertiesFormatException e ) {
			JOptionPane.showMessageDialog( null, "The language file is bad-formed, please try to use another language file.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
		}

		// If the file was not found
		catch( FileNotFoundException e ) {
			JOptionPane.showMessageDialog( null, "The language file was not found, please verify that the \"config.xml\" file contains a reference to a valid language file.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
		}

		// If there was a I/O exception
		catch( IOException e ) {
			JOptionPane.showMessageDialog( null, "There has been an error loading the language file, please check for problem accessing the files.\nThe default language (English) will be loaded.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	/**
	 * Returns the element name for the given element identifier.
	 * 
	 * @param element
	 *            Element identifier
	 * @return Element identifier, "Error" if the element was not found
	 */
	public static String getElementName( int element ) {
		return getText( "Element.Name" + element );
	}

	/**
	 * Returns the string associated with the given identifier.
	 * 
	 * @param identifier
	 *            Identifier to search for the string
	 * @return String retrieved from the text base, "Error" if the text was not found
	 */
	public static String getText( String identifier ) {
		String text = null;
		
		if( guiStrings != null && guiStrings.containsKey( identifier ) )
			text = guiStrings.getProperty( identifier );
		else {
			text = "Error";
			System.err.println( "Identifier \"" + identifier + "\" not found" );
		}

		return text;
	}

	/**
	 * Returns the string associated with the given identifier. This method also replaces occurrences in the original
	 * string with words passed through the call. Every placeholder for the strings have all the form "#n", where <i>n</i>
	 * is the index of the string to replace the placeholder in the given array. This method takes only one string to
	 * replace.
	 * 
	 * @param identifier
	 *            Identifier to search for the string
	 * @param parameter
	 *            String to replace the placeholder in the returned string
	 * @return String retrieved from the text base, "Error" if the text was not found
	 */
	public static String getText( String identifier, String parameter ) {
		String text = null;

		if( guiStrings.containsKey( identifier ) ) {
			text = guiStrings.getProperty( identifier );
			text = text.replace( "{#0}", parameter );
		} else {
			text = "Error";
			System.err.println( "Identifier \"" + identifier + "\" not found" );
		}

		return text;
	}

	/**
	 * Returns the string associated with the given identifier. This method also replaces occurrences in the original
	 * string with words passed through the call. Every placeholder for the strings have all the form "#n", where <i>n</i>
	 * is the index of the string to replace the placeholder in the given array. This method takes an array of strings
	 * to replace.
	 * 
	 * @param identifier
	 *            Identifier to search for the string
	 * @param parameters
	 *            Array of strings to replace the placeholders in the returned string
	 * @return String retrieved from the text base, "Error" if the text was not found
	 */
	public static String getText( String identifier, String[] parameters ) {
		String text = null;

		if( guiStrings.containsKey( identifier ) ) {
			text = guiStrings.getProperty( identifier );
			for( int i = 0; i < parameters.length; i++ )
				text = text.replace( "{#" + i + "}", parameters[i] );
		} else {
			text = "Error";
			System.err.println( "Identifier \"" + identifier + "\" not found" );
		}

		return text;
	}
	
	/**
	 * Returns the default name of the edition tool given its class
	 * @param c The class of the tool
	 * @return The the text if any, "Error" if not found
	 */
	@SuppressWarnings("unchecked")
	public static String getEditionToolName ( Class c ){
		return getText ("Edition.UndoRedo.Name."+c.getSimpleName());
	}
}
