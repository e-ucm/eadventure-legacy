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

package es.eucm.eadventure.tracking.prv;

import java.util.List;


public abstract class GameLogConsumer extends Thread{
    public static final long DEFAULT_FREQ = 10000;
    
    protected List<GameLogEntry> q;
    
    private boolean terminate;
    
    protected long startTime;
    
    protected long updateFreq;
    
    public synchronized void setTerminate (boolean interrupt){
        this.terminate = interrupt;
    }
    
    public synchronized boolean terminate(){
        return terminate;
    }
    
    public GameLogConsumer(List<GameLogEntry> q, long startTime){
        this.q = q;
        setTerminate(false);
        updateFreq=DEFAULT_FREQ;
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
        synchronized(q){
            consumerClose();
        }
    }

    private void consumeGameLog( ) {
        synchronized(q){
            if (q.size( )>0){
                System.out.println( "["+this.getClass( ).getName( )+" :"+((System.currentTimeMillis()-startTime)/1000)+"] "+ q.size() );
                consumerCode();
            }else
                System.out.println( "["+this.getClass( ).getName( )+" :"+((System.currentTimeMillis()-startTime)/1000)+"] Q is empty");
            
        }
    }
    
    protected abstract void consumerCode();

    protected abstract void consumerClose();
}
