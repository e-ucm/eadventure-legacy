package es.eucm.eadventure.editor.control.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.editor.control.Controller;

public class ProjectConfigData {

	private static final String FILE_NAME = "project.xml";
	
	private static Properties properties;
	
	private static ArrayList<ProjectConfigDataConsumer> consumers
		= new ArrayList<ProjectConfigDataConsumer>();

	public static void init() {
		consumers = new ArrayList<ProjectConfigDataConsumer>();
		properties = new Properties();
		storeToXML();
	}
	
	public static void addConsumer(ProjectConfigDataConsumer consumer){
		consumers.add(consumer);
	}
	
	public static void loadFromXML (){
		properties = new Properties();
		try {
			File projectConfigFile = new File(Controller.getInstance().getProjectFolder(),FILE_NAME );
			if (projectConfigFile.exists()){
				properties.loadFromXML( new FileInputStream( Controller.getInstance().getProjectFolder()+"/"+FILE_NAME ) );
			} else {
				storeToXML();
			}
		} catch( InvalidPropertiesFormatException e ) {} catch( FileNotFoundException e ) {} catch( IOException e ) {}
		for ( ProjectConfigDataConsumer consumer: consumers ){
			consumer.updateData( );
		}
		LOMConfigData.loadData();
	}
	
	public static void storeToXML() {
		try {
		    	FileOutputStream file = new FileOutputStream( Controller.getInstance().getProjectFolder()+"/"+FILE_NAME );
			properties.storeToXML( file , "Project Configuration" );
			file.close();
			
		} catch( FileNotFoundException e ) {} catch( IOException e ) {}
	}
	
	public static String getProperty (String key){
		if (properties.containsKey( key )){
			return properties.getProperty( key );
		}
		else
			return null;
	}
	
	public static void setProperty( String key, String value ) {
		properties.setProperty( key, value );
	}

	public static boolean existsKey ( String key ){
		return properties.containsKey( key );
	}
	
	public static void registerConsumer( ProjectConfigDataConsumer newConsumer ){
		consumers.add( newConsumer );
	}
}
