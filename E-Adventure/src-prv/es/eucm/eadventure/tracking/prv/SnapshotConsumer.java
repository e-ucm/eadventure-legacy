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

import java.io.File;
import java.util.List;

/**
 * Simple class that consumes the snapshots produced by SnapshotProducer. Periodically sends the snapshots to a backend server (using TrackingPoster).
 * @author Javier Torrente
 *
 */
public class SnapshotConsumer extends Thread{
    
    /**
     * Frequency, in millis, for consuming snapshotws. 
     */
    private long freq;
    
    /**
     * Shared queue of snapshots. TrackingController deals with synchronization
     */
    private List<File> q;
    
    private boolean terminate;
    
    private long startTime;

    private void sendSnapshot( ) {
        synchronized(q){
            while (q.size( )>0){
                //System.out.println( "[SN_CONSUMER:"+((System.currentTimeMillis()-startTime)/1000)+"] "+ q.get( 0 ).getAbsolutePath( ) );
                TrackingPoster.getInstance().sendSnapshot( q.remove( 0 ) );
            }//if (q.size( )==0)
                //System.out.println( "[SN_CONSUMER:"+((System.currentTimeMillis()-startTime)/1000)+"] Q is empty");
            
        }
    }
    
    private void sendAllPendingSnapshots(){
        synchronized(q){
            for (File f:q){
                TrackingPoster.getInstance().sendSnapshot( f );
                q.clear( );
            }
        }
    }
    
    public synchronized void setTerminate (boolean interrupt){
        this.terminate = interrupt;
    }
    
    public synchronized boolean terminate(){
        return terminate;
    }
    
    public SnapshotConsumer(List<File> q, long startTime, long sendFreq){
        this.q = q;
        setTerminate(false);
        this.startTime = startTime;
        this.freq = sendFreq;
    }
    
    @Override
    public void run(){
        while (!terminate()){
            try {
                sendSnapshot();
                Thread.sleep( freq );
            }
            catch( InterruptedException e ) {
            }
        }
        sendAllPendingSnapshots();
    }

}
