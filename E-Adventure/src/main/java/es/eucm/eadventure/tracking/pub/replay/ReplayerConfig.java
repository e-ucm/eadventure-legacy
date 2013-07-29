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

package es.eucm.eadventure.tracking.pub.replay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;


public class ReplayerConfig {

    private static final String CONTROLLER_CLASS="replayer-class";
    
    private Properties rConfig;
    
    public ReplayerConfig (){
        // Get file "replayer.config"
        InputStream source = null;
        source = ReplayerConfig.class.getResourceAsStream( "replayer.config" );
        if (source==null)
            source = ReplayerConfig.class.getResourceAsStream( "/replayer.config" );
        if (source==null)
            source = ReplayerConfig.class.getResourceAsStream( "./replayer.config" );
        if (source==null)
            source = ReplayerConfig.class.getResourceAsStream( "/tracking/replayer.config" );
        if (source==null)
            source = ReplayerConfig.class.getResourceAsStream( "/tracking/replayer/replayer.config" );
        if (source==null){
            try {
                source = new FileInputStream( "replayer.config" );
            }
            catch( FileNotFoundException e ) {
            }
        }

        if (source!=null){
            loadConfig(source);
        } else {
            setDefaultValues();
        }

    }
    
    
    private void loadConfig (InputStream source){
        if (rConfig==null)
            rConfig = new Properties( );
        
        try {
            rConfig.loadFromXML( source );
        }

        // If the file is bad formed
        catch( InvalidPropertiesFormatException e ) {
            System.out.println( "[REPLAYER] Can not load replayerconfig" );
            setDefaultValues();
        }

        // If the file was not found
        catch( FileNotFoundException e ) {
            System.out.println( "[REPLAYER] Can not load replayerconfig" );
            setDefaultValues();
        }

        // If there was a I/O exception
        catch( IOException e ) {
            System.out.println( "[REPLAYER] Can not load replayerconfig" );
            setDefaultValues();
        }
    }
    
    public String getReplayerClass(){
        return getStringParam( CONTROLLER_CLASS ).equals( "UNKNOWN" )?null:getStringParam( CONTROLLER_CLASS );
    }
    
    public void store(){
        try {
            rConfig.storeToXML( new FileOutputStream("replayer.config"), "Replayer config params "+Calendar.getInstance( ).getTime( ).toString( ), "UTF-8");
        }
        catch( FileNotFoundException e ) {
            System.out.println("[REPLAYER] Unable to store properties" );
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
    }
    
    public void setDefaultValues(){
        System.out.println("[GAMELOG] Setting default values" );
        if (rConfig==null)
            rConfig = new Properties( );
        else
            rConfig.clear( );
        rConfig.setProperty( CONTROLLER_CLASS, "es.eucm.eadventure.tracking.pub.replay.EmptyReplayer" );
    }
    
    private String getStringParam(String key){
        try{
            String value = rConfig.getProperty( key, "UNKNOWN" );
            return value;
        } catch (Exception e){
            return "UNKNOWN";
        } 
    }
}
