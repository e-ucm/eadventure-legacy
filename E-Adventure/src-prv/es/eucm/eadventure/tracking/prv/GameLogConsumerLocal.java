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
import java.io.IOException;
import java.util.List;

import es.eucm.eadventure.engine.core.control.Game;


public class GameLogConsumerLocal extends GameLogConsumer{

    private int randomId;
    private int seq;

    private String fixedPrefix;
    public GameLogConsumerLocal( List<GameLogEntry> q, long timestamp, int randomId, long freq ) {
        super( q, timestamp );
        this.updateFreq = 20000;
        this.randomId = randomId;
        seq=1;
        this.updateFreq = freq;
        fixedPrefix="eadgamelog-"+Game.getInstance( ).getAdventureName( )+"-"+randomId+"-";
    }

    @Override
    protected void consumerCode( ) {
        GameLogWriter.writeToFile( startTime, this.q, getFile() );
    }

    private File getFile(){
        String seqStr =""+seq;
        while (seqStr.length( )<4)seqStr="0"+seqStr;
        String prefix= fixedPrefix+seqStr+"-";
        String suffix= ".xml";
        File file;
        try {
            file = File.createTempFile( prefix, suffix );
            seq++;
        }
        catch( IOException e ) {
            file = new File (prefix+suffix);
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void consumerClose( ) {
        consumerCode();
    }
}
