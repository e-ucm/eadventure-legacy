/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
