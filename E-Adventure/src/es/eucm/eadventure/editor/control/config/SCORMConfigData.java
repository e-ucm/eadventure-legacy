package es.eucm.eadventure.editor.control.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.editor.control.Controller;

/**
 * Parts of the cmi model of Scorm 1.2 and 2004
 * 
 *
 */
public class SCORMConfigData {

	private static final String FILE_NAME_12 = "datamodel.xml";
	
	private static final String FILE_NAME_2004 = "datamodel2004.xml";
	
	private static Properties properties2004;
	
	private static Properties properties12;
	
	
	public static void init() {
		properties2004 = new Properties();
		properties12 = new Properties();
		loadFromXML();
		
	}
	
	public static void loadFromXML (){
		properties2004 = new Properties();
		try {
			File projectConfigFile = new File(/*Controller.getInstance().getProjectFolder(),*/FILE_NAME_12 );
			File projectConfigFile2 = new File(/*Controller.getInstance().getProjectFolder(),*/FILE_NAME_2004);
			if (projectConfigFile.exists()){
				properties12.loadFromXML( new FileInputStream( /*Controller.getInstance().getProjectFolder()+"/"+*/FILE_NAME_12 ) );
			}
			if (projectConfigFile2.exists()){
				properties2004.loadFromXML( new FileInputStream( /*Controller.getInstance().getProjectFolder()+"/"+*/FILE_NAME_2004 ) );
			}
			
		} catch( InvalidPropertiesFormatException e ) {} catch( FileNotFoundException e ) {} catch( IOException e ) {}
		
	}
	
	public static String getProperty2004 (String key){
		if (properties2004.containsKey( key )){
			return properties2004.getProperty( key );
		}
		else
			return null;
	}
	
	public static String getProperty12 (String key){
		if (properties12.containsKey( key )){
			return properties12.getProperty( key );
		}
		else
			return null;
	}
	
	public static ArrayList<String> getPartsOfModel2004(){
		Set<String> prop = properties2004.stringPropertyNames();
		
		ArrayList<String> elements = new ArrayList<String>();
		for (Iterator<String> it = prop.iterator();it.hasNext();){
			elements.add(it.next());
		}
		return elements;
	}
	
	public static ArrayList<String> getPartsOfModel12(){
		Set<String> prop = properties12.stringPropertyNames();
		
		ArrayList<String> elements = new ArrayList<String>();
		for (Iterator<String> it = prop.iterator();it.hasNext();){
			elements.add(it.next());
		}
		return elements;
	}
	
}
