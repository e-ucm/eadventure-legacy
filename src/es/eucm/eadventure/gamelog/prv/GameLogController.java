/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.gamelog.prv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.eucm.eadventure.gamelog.pub.GameLogConfig;
import es.eucm.eadventure.gamelog.pub._GameLog;
import es.eucm.eadventure.gamelog.pub._GameLogController;


public class GameLogController implements _GameLogController{

    public static final long DUMP_FREQ=15000;
    
    private SnapshotProducer snapshotMakerDaemon;
    private SnapshotConsumer snapshotSenderDaemon;
    private List<File> q;
    
    private GameLogConsumerHTTP glConsumerHTTP;
    private GameLogConsumerLocal glConsumerLocal;
    private GameLog gameLog;
    
    private long startTimeStamp;
    private boolean logging;
    
    private boolean localEnabled;
    private boolean remoteEnabled;
    
    private boolean snapshotsEnabled;
    private boolean logEnabled;
    
    private GameLogConfig glConfig;
    
    public GameLogController(GameLogConfig glConfig){
        startTimeStamp = System.currentTimeMillis();
        this.glConfig = glConfig;
        this.localEnabled = glConfig.localEnabled( );
        this.remoteEnabled = glConfig.remoteEnabled( );
        this.snapshotsEnabled = glConfig.snapshotsEnabled( );
        this.logEnabled = glConfig.logEnabled( );
        if (!glConfig.trackingEnabled( )) this.logging = false;
        else
            this.logging = localEnabled || remoteEnabled;
        
        gameLog = new GameLog(logging, startTimeStamp, glConfig.logLowLevelEventsSampleFreq( ));
        if (logging) {        
            q = new ArrayList<File>();
    
            Random r = new Random();
            int randomId = r.nextInt( 10000 );
    
            if (snapshotsEnabled){
                snapshotMakerDaemon = new SnapshotProducer(q, startTimeStamp, randomId, glConfig.snapshotsSampleFreq( ));
                if (remoteEnabled)
                    snapshotSenderDaemon = new SnapshotConsumer (q, startTimeStamp, glConfig.snapshotsSendFreq( ));
            }
            
            if (logEnabled){
                if (remoteEnabled)
                    glConsumerHTTP = new GameLogConsumerHTTP(gameLog.getNewEntries( ), startTimeStamp, glConfig.logSendFreq( ));
                if (localEnabled)
                    glConsumerLocal = new GameLogConsumerLocal(gameLog.getAllEntries( ), startTimeStamp, randomId, glConfig.logDumpFreq( ));
            }
        }
    }
    
    public void start(){
        if (!logging) return;
        int tries =0;
        String baseURL=null;
        if (logEnabled && localEnabled)
            glConsumerLocal.start();
        if (snapshotsEnabled)
            snapshotMakerDaemon.start( );
        if (remoteEnabled){
            GameLogPoster.setInstance( glConfig.serviceURL( ), glConfig.serviceKey( ) );
            while ((baseURL=GameLogPoster.getInstance().openSession())==null && tries<3){
                tries++;
            }
        }
        if (remoteEnabled && baseURL!=null){
            if (logEnabled)
                glConsumerHTTP.start();
            if (snapshotsEnabled)
                snapshotSenderDaemon.start( );
        }
    }
    
    public void terminate(){
        if (!logging) return;
        if (snapshotsEnabled && snapshotMakerDaemon!=null && snapshotMakerDaemon.isAlive( ))
            snapshotMakerDaemon.setTerminate( true );
        if (snapshotsEnabled && remoteEnabled && snapshotSenderDaemon!=null && snapshotSenderDaemon.isAlive( ))
            snapshotSenderDaemon.setTerminate( true );
        if (logEnabled && remoteEnabled && glConsumerHTTP!=null && glConsumerHTTP.isAlive( ))
            glConsumerHTTP.setTerminate( true );
        if (logEnabled && localEnabled && glConsumerLocal!=null && glConsumerLocal.isAlive( ))
            glConsumerLocal.setTerminate( true );
    }
    
    
    public _GameLog getGameLog( ) {
    
        return gameLog;
    }

}
