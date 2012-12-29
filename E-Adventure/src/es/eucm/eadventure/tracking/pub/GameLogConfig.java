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

package es.eucm.eadventure.tracking.pub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;


public class GameLogConfig {

    private static final String TRACKING="tracking"; // Global
    
    private static final String CONTROLLER_CLASS="controller-class"; //Global
    
    private static final String GAME_ID="game-id"; // Impl
    
    private static final String FILE_ID="file-id"; // Impl 
    
    private static final String LOG="interaction-log";
    private static final String LOWLEVEL_EVENTS_SAMPLE_FREQ="interaction-log-ll-sample-freq";
    private static final String EFFECT_VERBOSITY="effect-verbosity";
    
    private static final String REMOTE="remote-service";
    private static final String LOG_SEND_FREQ="interaction-log-send-freq";
    private static final String LOG_SEND_URL="service-path-log";
    private static final String KEY="service-key";
    private static final String URL="service-url";
    
    private static final String LOCAL="local-dump";
    private static final String LOG_DUMP_FREQ="interaction-log-dump-freq";
    
    private static final String SNAPSHOTS="snapshots-capture";
    
    private static final String SNAPSHOTS_SAMPLE_FREQ="snapshots-sample-freq";
    private static final String SNAPSHOTS_SEND_FREQ="snapshots-send-freq";
    private static final String SNAPSHOT_SEND_URL="service-path-snapshot";
    
    private Properties glConfig;
    
    public GameLogConfig ( String trackingConfigFile ){
        // Get file "tracking.config"
        String trackingConfig ="tracking.config";
        if (trackingConfigFile!=null){
            trackingConfig=trackingConfigFile;
        }
        InputStream source = null;
        source = GameLogConfig.class.getResourceAsStream( trackingConfig );
        if (source==null)
            source = GameLogConfig.class.getResourceAsStream( "/"+trackingConfig );
        if (source==null)
            source = GameLogConfig.class.getResourceAsStream( "./"+trackingConfig );
        if (source==null)
            source = GameLogConfig.class.getResourceAsStream( "/tracking/"+trackingConfig );
        if (source==null){
            try {
                source = new FileInputStream( trackingConfig );
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
        if (glConfig==null)
            glConfig = new Properties( );
        
        try {
            glConfig.loadFromXML( source );
        }

        // If the file is bad formed
        catch( InvalidPropertiesFormatException e ) {
            System.out.println( "[GAMELOG] Can not load trackingconfig" );
            setDefaultValues();
        }

        // If the file was not found
        catch( FileNotFoundException e ) {
            System.out.println( "[GAMELOG] Can not load trackingconfig" );
            setDefaultValues();
        }

        // If there was a I/O exception
        catch( IOException e ) {
            System.out.println( "[GAMELOG] Can not load trackingconfig" );
            setDefaultValues();
        }
    }
    
    public boolean effectVerbosityEnabled(){
        return getBooleanParam( EFFECT_VERBOSITY );
    }
    
    public boolean trackingEnabled(){
        return getBooleanParam( TRACKING );
    }
    
    public boolean remoteEnabled(){
        return getBooleanParam( REMOTE );
    }
    
    public boolean localEnabled(){
        return getBooleanParam( LOCAL );
    }
    
    public boolean snapshotsEnabled(){
        return getBooleanParam( SNAPSHOTS );
    }
    
    public boolean logEnabled(){
        return getBooleanParam( LOG );
    }

    public long snapshotsSampleFreq(){
        return getLongParam( SNAPSHOTS_SAMPLE_FREQ );
        
    }

    public long snapshotsSendFreq(){
        return getLongParam( SNAPSHOTS_SEND_FREQ );
    }

    public long logDumpFreq(){
        return getLongParam( LOG_DUMP_FREQ );
    }
    
    public long logSendFreq(){
        return getLongParam( LOG_SEND_FREQ );
    }
    
    public long logLowLevelEventsSampleFreq(){
        return getLongParam( LOWLEVEL_EVENTS_SAMPLE_FREQ );
    }
    
    public String serviceKey(){
        return getStringParam( KEY );
    }
    
    public String serviceURL(){
        return getStringParam( URL );
    }
    
    public String getControllerClass(){
        return getStringParam( CONTROLLER_CLASS ).equals( "UNKNOWN" )?null:getStringParam( CONTROLLER_CLASS );
    }
    
    public String getFileId(){
        return getStringParam( FILE_ID ).equals( "UNKNOWN" )?"random":getStringParam( FILE_ID );
    }
    
    public String getGameId(){
        return getStringParam( GAME_ID ).equals( "UNKNOWN" )?"game_id":getStringParam( GAME_ID );
    }
    
    public String getLogSendUrl(){
        return getStringParam( LOG_SEND_URL ).equals( "UNKNOWN" )?"chunks":getStringParam( LOG_SEND_URL );
    }
    
    public String getSnapshotSendUrl(){
        return getStringParam( SNAPSHOT_SEND_URL ).equals( "UNKNOWN" )?"snapshots":getStringParam( SNAPSHOT_SEND_URL );
    }
    
    public void store(){
        try {
            glConfig.storeToXML( new FileOutputStream("tracking.config"), "Game Log config params "+Calendar.getInstance( ).getTime( ).toString( ), "UTF-8");
        }
        catch( FileNotFoundException e ) {
            System.out.println("[GAMELOG] Unable to store properties" );
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
    }
    
    public void setDefaultValues(){
        System.out.println("[GAMELOG] Setting default values" );
        if (glConfig==null)
            glConfig = new Properties( );
        else
            glConfig.clear( );
        glConfig.setProperty( TRACKING, "disabled" );
        glConfig.setProperty( LOG_DUMP_FREQ, "10000" );
        glConfig.setProperty( LOG_SEND_FREQ, "10000" );
        glConfig.setProperty( SNAPSHOTS_SAMPLE_FREQ, "10000" );
        glConfig.setProperty( SNAPSHOTS_SEND_FREQ, "10" );
        glConfig.setProperty( LOWLEVEL_EVENTS_SAMPLE_FREQ, "1000" );
        glConfig.setProperty( EFFECT_VERBOSITY, "disabled" );
        glConfig.setProperty( FILE_ID, "random" );
        glConfig.setProperty( GAME_ID, "game_id" );
        glConfig.setProperty( LOG_SEND_URL, "chunks" );
        glConfig.setProperty( SNAPSHOT_SEND_URL, "snapshots" );
    }
    
    private boolean getBooleanParam(String key){
        try{
            String value = glConfig.getProperty( key, "disabled" );
            return value.equals( "enabled" ) || value.equals( "ENABLED" ) || value.equals( "true" ) || value.equals( "TRUE" ) || value.equals( "on" ) || value.equals( "ON" );
        } catch (Exception e){
            return false;
        }
    }
    
    private long getLongParam(String key){
        try{
            String value = glConfig.getProperty( key, "10000" );
            return Long.parseLong( value );
        } catch (Exception e){
            return 0;
        }
    }

    private String getStringParam(String key){
        try{
            String value = glConfig.getProperty( key, "UNKNOWN" );
            return value;
        } catch (Exception e){
            return "UNKNOWN";
        } 
    }
}
