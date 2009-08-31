/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
