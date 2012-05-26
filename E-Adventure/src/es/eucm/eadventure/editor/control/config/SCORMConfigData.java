/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import es.eucm.eadventure.common.auxiliar.File;

/**
 * Parts of the cmi model of Scorm 1.2 and 2004
 * 
 * 
 */
public class SCORMConfigData {

    private static final String FILE_NAME_12 = "datamodel.xml";

    private static final String FILE_NAME_2004 = "datamodel2004.xml";
    
    public static final String[] ArrayNames = {"cmi.objectives", "cmi.interactions", "cmi.comments_from_lms","cmi.comments_from_learner"};
    
    /**
     * Constant for especial case: an array inside array
     */
    public static final String[] ArrayInArray = {"cmi.interactions.n.objetives.m.id","cmi.interactions.n.correct_responses.m.pattern"};
    
    /**
     * Constants with the index in array of attribute`s name
     */
    public static final int SCORM_OBJECTIVES= 0;
    
    public static final int SCORM_INTERACTIONS = 1;
    
    /**
     * Constants to identify read/write elements.
     */
    public static final int READ = 0;
    
    public static final int WRITE = 1;
    
    
    public static final int SCORM_V12 = 0;
    
    public static final int SCORM_2004 = 1;

    private static Properties properties2004;

    private static Properties properties12;

    public static void init( ) {

        properties2004 = new Properties( );
        properties12 = new Properties( );
        loadFromXML( );

    }

    public static void loadFromXML( ) {

        properties2004 = new Properties( );
        try {
            File projectConfigFile = new File( /*Controller.getInstance().getProjectFolder(),*/FILE_NAME_12 );
            File projectConfigFile2 = new File( /*Controller.getInstance().getProjectFolder(),*/FILE_NAME_2004 );
            if( projectConfigFile.exists( ) ) {
                properties12.loadFromXML( new FileInputStream( /*Controller.getInstance().getProjectFolder()+"/"+*/FILE_NAME_12 ) );
            }
            if( projectConfigFile2.exists( ) ) {
                properties2004.loadFromXML( new FileInputStream( /*Controller.getInstance().getProjectFolder()+"/"+*/FILE_NAME_2004 ) );
            }

        }
        catch( InvalidPropertiesFormatException e ) {
        }
        catch( FileNotFoundException e ) {
        }
        catch( IOException e ) {
        }

    }
    
    public static boolean isArrayInsideArray( String att ){
	for (int i=0; i<ArrayInArray.length; i++){
	    if (att.equals(ArrayInArray[i]))
		return true;
	}
	return false;
    }

    
    public static String getProperty2004( String key ) {

        if( properties2004.containsKey( key ) ) {
            return properties2004.getProperty( key );
        }
        else
            return null;
    }
    
    /**
     * For array attributes, there are one entry in xml file for identify it, and a set of attributes which are
     * the fields of that attribute. This method filter that fields for hide them in the list of accessible attributes.
     * 
     * @param att
     * @return
     */
    public static boolean isArrayAttributeExtension(String att){
	
	for (int i=0; i<ArrayNames.length; i++){
	    if (att.startsWith(ArrayNames[i]+"."))
		return true;
	}
	return false;
	
    }
    
    /**
     * Check if the parameter is one of the SCORM array data model attributes
     * 
     * @param att
     * 		The attribute to check.
     * @return
     */
    public static boolean isArrayAttribute( String att ){
	for (int i=0; i<ArrayNames.length; i++){
	    if (att.equals(ArrayNames[i]))
		return true;
	}
	return false;
    }

    public static String getProperty12( String key ) {

        if( properties12.containsKey( key ) ) {
            return properties12.getProperty( key );
        }
        else
            return null;
    }
    
    public static boolean isPartOfTheModel12( String key ){
        return properties12.containsKey( key ); 
    }
    
    public static boolean isPartOfTheModel2004( String key ){
        return properties2004.containsKey( key ); 
    }
    
    public static ArrayList<String> getPartsOfModel2004( int readWrite ) {

        Set<String> prop = properties2004.stringPropertyNames( );

        ArrayList<String> elements = new ArrayList<String>( );
        for( Iterator<String> it = prop.iterator( ); it.hasNext( ); ) {
            String next = it.next( );
            // Only is added if not is an attribute extension and if can be read/write
            if ( !isArrayAttributeExtension( next ) && canBeAdded( readWrite, next, SCORM_2004 ) )
        	elements.add( next );
        }
        return elements;
    }

    public static ArrayList<String> getPartsOfModel12( int readWrite ) {

        Set<String> prop = properties12.stringPropertyNames( );

        ArrayList<String> elements = new ArrayList<String>( );
        for( Iterator<String> it = prop.iterator( ); it.hasNext( ); ) {
            String next = it.next( );
            
            // Only is added if not is an attribute extension and if can be read/write
            if ( !isArrayAttributeExtension( next ) && canBeAdded( readWrite, next, SCORM_V12 ) )
        	elements.add( next );
        }
        return elements;
    }
    
    private static boolean canBeAdded(int readWrite, String attribute, int type){
	//Select the index in the XML entry to identify if that attribute can be Read/Write
        int index = 0;
        if ( readWrite==READ )
    	index = 1;
        else if (readWrite==WRITE )
    	index=3;
        if ( type == SCORM_V12)
            return SCORMConfigData.getProperty12( attribute ).charAt( index ) == '1' ;

	else if ( type == SCORM_2004)
	    return SCORMConfigData.getProperty2004( attribute ).charAt( index ) == '1' ;
	else 
	    return false;

        
    }
    
    /**
     *
     * Extract all elements which has "attribute" prefix.
     *
     * @param attribute
     * 		The prefix to look for.
     * @param type
     * 		The type of SCORM.
     * @param readWrite
     * 		Identifies if the attributes must to be read or write
     * @return
     * 		All the fields for the "Attribute".
     */
    public static ArrayList<String> getAttribute( String attribute, int type, int readWrite ){
	
	Set<String> prop = null;
	if ( type == SCORM_V12)
	    prop = properties12.stringPropertyNames( );

	else if ( type == SCORM_2004)
	    prop = properties2004.stringPropertyNames( );

	
        ArrayList<String> elements = new ArrayList<String>( );
        for( Iterator<String> it = prop.iterator( ); it.hasNext( ); ) {
            String next = it.next();
            if ( next.startsWith(attribute)&&!isArrayAttribute(next)&&canBeAdded(readWrite, next, type ))
        	elements.add( next );
        }
        return elements;
    }
    


}
