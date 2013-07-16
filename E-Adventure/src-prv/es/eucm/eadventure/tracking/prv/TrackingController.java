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

package es.eucm.eadventure.tracking.prv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import es.eucm.eadventure.tracking.prv.form.TrackingContactForm;
import es.eucm.eadventure.tracking.prv.form.TrackingContactFormScreen;
import es.eucm.eadventure.tracking.prv.service.GameLogConsumer;
import es.eucm.eadventure.tracking.prv.service.ServiceConstArgs;
import es.eucm.eadventure.tracking.prv.studentcode.NameInputScreen;
import es.eucm.eadventure.tracking.pub._GameLog;
import es.eucm.eadventure.tracking.pub._TrackingController;
import es.eucm.eadventure.tracking.pub.config.Service;
import es.eucm.eadventure.tracking.pub.config.TrackingConfig;


public class TrackingController implements _TrackingController{

    public static final long DUMP_FREQ=15000;
    
    private GameLog gameLog;
    
    private long startTimeStamp;
    
    private TrackingConfigExtended trackingConfigExt;
    
    private List<GameLogConsumer> services;
    
    public TrackingController(TrackingConfig trackingConfig){
        startTimeStamp = System.currentTimeMillis();
        this.trackingConfigExt = new TrackingConfigExtended(trackingConfig);
        services = new ArrayList<GameLogConsumer>();
        
        // Get the Student id. 
        String studentId = "SID";
        boolean releaseMode=false;
        if (trackingConfigExt.getStudentId( ).equals( "random" )){
            Random r = new Random();
            studentId = Integer.toString( r.nextInt( 10000 ) );
        } else if (trackingConfigExt.getStudentId( ).equals( "ask" )){ 
           String c= NameInputScreen.getCode( trackingConfigExt.getWelcomeMessage( ) );
           if (c==null) {
               System.err.println( "Error. Not valid code. "+c );
               System.exit( 0 );
           }else
               studentId =  c ;
        } else if (trackingConfigExt.getStudentId( ).equals( "release" )){ 
            releaseMode=true;
            studentId = getStoredCode( );
            if (studentId==null){
                System.out.println("Student id was not stored..." );
                studentId = makeRobustRandomStudent("anonymous", 8);
            } else {
                System.out.println("Student id found! "+studentId );
            }
        }
        
        else {
            studentId = trackingConfigExt.getStudentId( );
        }
        trackingConfigExt.setStudentId( studentId );
        
        gameLog = new GameLog(trackingConfig.isEnabled( ), trackingConfigExt.effectVerbosityEnabled( ), startTimeStamp, trackingConfigExt.logLowLevelEventsSampleFreq( ), trackingConfigExt.getGameId( ), studentId, trackingConfigExt.getSpecialGameEntries( ));
        
        if (releaseMode){
            if (getStoredCode()==null){
                TrackingContactForm form = TrackingContactFormScreen.showForm( );
                if (form.getTrackingEnabled( )==TrackingContactForm.TrackMe.Si){
                    trackingConfigExt.getTrackingConfig( ).setEnabled( true ); 
                    this.storeProperties( studentId, true );
                } else {
                    trackingConfigExt.getTrackingConfig( ).setEnabled( false );
                    this.storeProperties( studentId, false );
                }
                gameLog.effectEvent( "user-form", "t=name", "v="+form.getName( ));
                gameLog.effectEvent( "user-form", "t=surname", "v="+form.getSurname( ));
                gameLog.effectEvent( "user-form", "t=email", "v="+form.getEmail( ));
                gameLog.effectEvent( "user-form", "t=gender", "v="+form.getGender( ));
                gameLog.effectEvent( "user-form", "t=age", "v="+form.getAge( ));
                gameLog.effectEvent( "user-form", "t=occupation", "v="+form.getOccupation( ));
                gameLog.effectEvent( "user-form", "t=intendeduse", "v="+form.getIntendedUse( ));
            } else {
                System.out.println( "Enabled tracking found! "+this.getStoredEnabledTracking( ) );
                trackingConfigExt.getTrackingConfig( ).setEnabled( this.getStoredEnabledTracking( ) );
            }
            
        }
        
        if (trackingConfigExt.getTrackingConfig( ).isEnabled( )) {        
    
            // Inject default services
            addDefaultServices(trackingConfig);
            
            for (Service service: trackingConfig.getService( )){
                if (service.isEnabled( ) && service.getClazz( )!=null &&!service.getClazz().equals( "" )){
                    try {
                        ServiceConstArgs args = new ServiceConstArgs(gameLog.getEntries( ), startTimeStamp, service, trackingConfigExt);
                        GameLogConsumer newConsumer = (GameLogConsumer) Class.forName( service.getClazz( ) ).getConstructor( ServiceConstArgs.class ).newInstance( args );
                        services.add( newConsumer );
                    }
                    catch( Exception e ) {
                        System.err.println( "[TrackingController] Service \""+service.getName( )+"\" could not be initialized. Failed to load class "+service.getClazz( ));
                    }            
                }
            }
    
            
        }
    }
    
    private static String makeRobustRandomStudent( String string, int size ) {
        String suffix ="";
        Random r = new Random();
        for (int i=0; i<size; i++){
            String d="";
            int digit = r.nextInt(62);
            if (digit>=0 && digit<26){
                char c = (char) (digit + 'a');
                d=""+c;
            } else if (digit>=26 && digit<52){
                char c = (char) (digit-26 + 'A');
                d=""+c;
            } else {
                char c = (char) (digit-52 + '0');
                d=""+c;
            }
            suffix+=d;
        }
        return string+suffix;
    }

    public static void main (String[]args){
        for (int i=0;i<100;i++)
        System.out.println( makeRobustRandomStudent("anonymous_", 6));
    }
    
    private String getStoredCode(){
        String userHome = System.getProperty( "user.home" );
        String storedCode = tryRetrieveStoredId(new File (userHome+"/.damaboba/config.txt"));
        if (storedCode==null){
            storedCode = tryRetrieveStoredId(new File ("/.damaboba/config.txt"));
        }
        return storedCode;
    }
    
    private boolean getStoredEnabledTracking(){
        String userHome = System.getProperty( "user.home" );
        String storedCode = tryRetrieveStoredEnabledTracking(new File (userHome+"/.damaboba/config.txt"));
        if (storedCode==null){
            storedCode = tryRetrieveStoredEnabledTracking(new File ("/.damaboba/config.txt"));
        }
        return storedCode!=null && Boolean.parseBoolean( storedCode );
    }
    
    private void storeProperties(String id, boolean enabled){
        String userHome = System.getProperty( "user.home" );
        new File (userHome+"/.damaboba/").mkdir( );
        boolean done = tryStoreProperties(new File (userHome+"/.damaboba/config.txt"), id, enabled);
        if (!done){
            new File (userHome+"\\.damaboba\\").mkdir( );
            done = tryStoreProperties(new File (userHome+"\\.damaboba\\config.txt"), id, enabled);
        }
        if (!done){
            new File ("/.damaboba/").mkdir( );
            done = tryStoreProperties(new File ("/.damaboba/config.txt"), id, enabled);
        }
    }
    
    private String tryRetrieveStoredId(File file){
        BufferedReader br = null;
        try {
            Properties properties = new Properties();
            
             br= new BufferedReader(new FileReader(file));
             properties.load( br );
             return properties.getProperty( "id" );
        }
        catch( IOException e ) {
            e.printStackTrace();
        } finally {
            if (br!=null)
                try {
                    br.close();
                }
                catch( IOException e ) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    
    private String tryRetrieveStoredEnabledTracking(File file){
        BufferedReader br = null;
        try {
            Properties properties = new Properties();
            
             br= new BufferedReader(new FileReader(file));
             properties.load( br );
             return properties.getProperty( "enable-tracking" );
        }
        catch( IOException e ) {
            e.printStackTrace();
        } finally {
            if (br!=null)
                try {
                    br.close();
                }
                catch( IOException e ) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    
    private boolean tryStoreProperties(File file, String id, boolean enableTracking){
        boolean stored = false;
        Properties properties=new Properties();
        properties.put( "id", id );
        properties.put( "enable-tracking", ""+enableTracking );
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            properties.store( out, "Dama Boba properties" );
            stored=true;
            out.close();
            System.out.println( "Properties: "+id+", "+enableTracking+" stored at: "+file.getAbsolutePath( ));
        } catch (Exception e) {stored=false;}
        return stored;
    }
    
    public void start(){
        if (!this.trackingConfigExt.getTrackingConfig( ).isEnabled( ))
            return;
        
        for (GameLogConsumer service: this.services){
            service.start( );
        }
        
    }
    
    public void terminate(){
        
        if (!this.trackingConfigExt.getTrackingConfig( ).isEnabled( ))
            return;
        
        for (GameLogConsumer service: this.services){
            if (service.isAlive( )){
                service.setTerminate( true );
            }
        }
        
    }
    
    
    public _GameLog getGameLog( ) {
    
        return gameLog;
    }

    /**
     * Adds default services. That is, services that must be always added and which are not defined through the XML config file.
     * @param config
     */
    private void addDefaultServices(TrackingConfig config){
        Service newService = new Service();
        newService.setClazz("es.eucm.eadventure.tracking.prv.service.StudentIDInjector");
        newService.setEnabled( true );
        newService.setFrequency( 5L );
        newService.setName( "studentid-injector" );
        config.getService( ).add( newService );
    }
}
