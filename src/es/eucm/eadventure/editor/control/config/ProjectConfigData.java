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

    private static ArrayList<ProjectConfigDataConsumer> consumers = new ArrayList<ProjectConfigDataConsumer>( );

    public static void init( ) {

        consumers = new ArrayList<ProjectConfigDataConsumer>( );
        properties = new Properties( );
        storeToXML( );
    }

    public static void addConsumer( ProjectConfigDataConsumer consumer ) {

        consumers.add( consumer );
    }

    public static void loadFromXML( ) {

        properties = new Properties( );
        try {
            File projectConfigFile = new File( Controller.getInstance( ).getProjectFolder( ), FILE_NAME );
            if( projectConfigFile.exists( ) ) {
                properties.loadFromXML( new FileInputStream( Controller.getInstance( ).getProjectFolder( ) + "/" + FILE_NAME ) );
            }
            else {
                storeToXML( );
            }
        }
        catch( InvalidPropertiesFormatException e ) {
        }
        catch( FileNotFoundException e ) {
        }
        catch( IOException e ) {
        }
        for( ProjectConfigDataConsumer consumer : consumers ) {
            consumer.updateData( );
        }
        LOMConfigData.loadData( );
    }

    public static void storeToXML( ) {

        try {
            FileOutputStream file = new FileOutputStream( Controller.getInstance( ).getProjectFolder( ) + "/" + FILE_NAME );
            properties.storeToXML( file, "Project Configuration" );
            file.close( );

        }
        catch( FileNotFoundException e ) {
        }
        catch( IOException e ) {
        }
    }

    public static String getProperty( String key ) {

        if( properties.containsKey( key ) ) {
            return properties.getProperty( key );
        }
        else
            return null;
    }

    public static void setProperty( String key, String value ) {

        properties.setProperty( key, value );
    }

    public static boolean existsKey( String key ) {

        return properties.containsKey( key );
    }

    public static void registerConsumer( ProjectConfigDataConsumer newConsumer ) {

        consumers.add( newConsumer );
    }
}
