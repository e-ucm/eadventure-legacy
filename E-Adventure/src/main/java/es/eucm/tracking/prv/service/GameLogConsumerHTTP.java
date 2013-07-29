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

package es.eucm.eadventure.tracking.prv.service;

import java.util.List;

import es.eucm.eadventure.tracking.prv.GameLogEntry;


public class GameLogConsumerHTTP extends GameLogConsumer{

    private long initialFreq;
    
    public GameLogConsumerHTTP( ServiceConstArgs args ) {
        super( args );
        this.initialFreq = serviceConfig.getFrequency( );
    }

    @Override
    protected boolean consumerCode( List<GameLogEntry> newQ ) {
        return TrackingPoster.getInstance().sendChunk( newQ );
    }

    @Override
    protected boolean consumerClose( List<GameLogEntry> newQ ) {
        return consumerCode( newQ );
    }

    
    @Override
    protected void reset(){
        super.reset( );
        updateFreq=initialFreq;
    }

    @Override
    protected void consumerInit( ) {
        int tries = 0;
        if (TrackingPoster.getInstance( )==null){
            TrackingPoster.setInstance( serviceConfig.getUrl( ), null, serviceConfig.getPath( ) );
            String baseURL = null;
            while ((baseURL=TrackingPoster.getInstance().openSession())==null && tries<3){
                tries++;
            }
            // If TrackingPoster was not successfully set up, disable this service
            if (baseURL == null){
                System.err.println( "[GameLogConsumerHttp] A session could not be opened. Disabling service...");
                setTerminate(true);
            }
        } else {
            TrackingPoster.getInstance( ).setChunksPath( serviceConfig.getPath( ) );
        }
    }
}
