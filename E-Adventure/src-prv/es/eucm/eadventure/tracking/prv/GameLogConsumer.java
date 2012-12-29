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


public abstract class GameLogConsumer extends Thread{
    public static final long DEFAULT_FREQ = 10000;
    public static final int DEFAULT_INCR_FACTOR = 2;
    public static final long DEFAULT_MAX_FREQ = DEFAULT_FREQ * DEFAULT_INCR_FACTOR * 5+1;
    
    private List<GameLogEntry> q;
    
    private boolean terminate;
    
    protected long startTime;
    
    protected long updateFreq;
    
    protected int incrFactor;
    
    public synchronized void setTerminate (boolean interrupt){
        this.terminate = interrupt;
    }
    
    public synchronized boolean terminate(){
        return terminate;
    }
    
    public GameLogConsumer(List<GameLogEntry> q, long startTime){
        this.q = q;
        setTerminate(false);
        reset();
        this.startTime = startTime;
        
    }
    
    @Override
    public void run(){
        while (!terminate()){
            try {
                consumeGameLog();
                Thread.sleep( updateFreq );
            }
            catch( InterruptedException e ) {
            }
        }
        List<GameLogEntry> newQ = copyQ();
        consumerClose(newQ);
    }

    private void consumeGameLog( ) {
        List<GameLogEntry> newQ = copyQ();
        if (newQ.size( )>0){
            System.out.println( "["+this.getClass( ).getName( )+" :"+((System.currentTimeMillis()-startTime)/1000)+"] "+ newQ.size() );
            if (consumerCode(newQ)){
                emptyQ( newQ );
                reset();
            } else {
                increment();
            }
        }else{
            System.out.println( "["+this.getClass( ).getName( )+" :"+((System.currentTimeMillis()-startTime)/1000)+"] Q is empty");
        }
            
    }
    
    private List<GameLogEntry> copyQ (){
        List<GameLogEntry> newQ = new ArrayList<GameLogEntry>();
        synchronized (q){
            for (GameLogEntry entry:q){
                newQ.add( entry );
            }
        }
        return newQ;
    }
    
    private void emptyQ (List<GameLogEntry> newQ){
        synchronized (q){
            for (int i=0; i<newQ.size( ); i++){
                for (int j=0; j<q.size( ); j++){
                    if (q.get( j ) == newQ.get( i )){
                        q.remove( j ); j--;
                    }
                }
            }
        }
    }
    
    protected abstract boolean consumerCode(List<GameLogEntry> newQ);

    protected abstract boolean consumerClose(List<GameLogEntry> newQ);
    
    protected void reset(){
        updateFreq=DEFAULT_FREQ;
        this.incrFactor = DEFAULT_INCR_FACTOR;
    }
    
    protected void increment(){
        updateFreq*=incrFactor;
        if (updateFreq>=DEFAULT_MAX_FREQ){
            setTerminate(true);
        }
    }
}
