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

package es.eucm.eadventure.engine.gamelog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameLogController {

    public static final long DUMP_FREQ=15000;
    
    private SnapshotProducer snapshotMakerDaemon;
    private SnapshotConsumer snapshotSenderDaemon;
    private List<File> q;
    
    private GameLogConsumerHTTP glConsumerHTTP;
    private GameLogConsumerLocal glConsumerLocal;
    private GameLog gameLog;
    
    private long startTimeStamp;
    
    public GameLogController(boolean logging){
        startTimeStamp = System.currentTimeMillis();
        
        gameLog = new GameLog(logging, startTimeStamp);
        q = new ArrayList<File>();

        Random r = new Random();
        int randomId = r.nextInt( 10000 );

        
        snapshotMakerDaemon = new SnapshotProducer(q, startTimeStamp, randomId);
        snapshotSenderDaemon = new SnapshotConsumer (q, startTimeStamp);

        
        glConsumerHTTP = new GameLogConsumerHTTP(gameLog.getNewEntries( ), startTimeStamp);
        glConsumerLocal = new GameLogConsumerLocal(gameLog.getAllEntries( ), startTimeStamp, randomId);

    }
    
    public void start(){
        int tries =0;
        String baseURL=null;
        glConsumerLocal.start();
        snapshotMakerDaemon.start( );
        while ((baseURL=GameLogPoster.openSession())==null && tries<3){
            tries++;
        }
        if (baseURL!=null){
            glConsumerHTTP.start();
            snapshotSenderDaemon.start( );
        }
    }
    
    public void terminate(){
        if (snapshotMakerDaemon!=null && snapshotMakerDaemon.isAlive( ))
            snapshotMakerDaemon.setTerminate( true );
        if (snapshotSenderDaemon!=null && snapshotSenderDaemon.isAlive( ))
            snapshotSenderDaemon.setTerminate( true );
        if (glConsumerHTTP!=null && glConsumerHTTP.isAlive( ))
            glConsumerHTTP.setTerminate( true );
        if (glConsumerLocal!=null && glConsumerLocal.isAlive( ))
            glConsumerLocal.setTerminate( true );
    }
    
    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    
    public GameLog getGameLog( ) {
    
        return gameLog;
    }

}
