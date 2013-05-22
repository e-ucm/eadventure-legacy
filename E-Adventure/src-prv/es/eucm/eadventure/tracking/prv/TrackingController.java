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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        } else {
            studentId = trackingConfigExt.getStudentId( );
        }
        trackingConfigExt.setStudentId( studentId );
        
        gameLog = new GameLog(trackingConfig.isEnabled( ), trackingConfigExt.effectVerbosityEnabled( ), startTimeStamp, trackingConfigExt.logLowLevelEventsSampleFreq( ), trackingConfigExt.getGameId( ), studentId, trackingConfigExt.getSpecialGameEntries( ));
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
