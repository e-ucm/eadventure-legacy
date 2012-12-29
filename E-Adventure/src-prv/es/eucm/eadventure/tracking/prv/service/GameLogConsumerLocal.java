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

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.tracking.prv.GameLogEntry;
import es.eucm.eadventure.tracking.prv.GameLogWriter;


public class GameLogConsumerLocal extends GameLogConsumer{

    private int seq;

    private String fixedPrefix;

    private String sessionId;
    
    private long initialFreq;
    
    public GameLogConsumerLocal( ServiceConstArgs args ) {
        super( args );
        seq=1;
        this.initialFreq = serviceConfig.getFrequency( );
        this.sessionId=getCurrentTimeAndDate();
        fixedPrefix="eadgamelog-"+Game.getInstance( ).getAdventureName( )+"-"+args.config.getStudentId( )+"-"+sessionId;
    }

    private String getCurrentTimeAndDate ( ){
        int year = Calendar.getInstance( ).get( Calendar.YEAR );
        int month = Calendar.getInstance( ).get( Calendar.MONTH );
        int day = Calendar.getInstance( ).get( Calendar.DAY_OF_MONTH );
        int hour = Calendar.getInstance( ).get( Calendar.HOUR_OF_DAY );
        int minute = Calendar.getInstance( ).get( Calendar.MINUTE );
        int second = Calendar.getInstance( ).get( Calendar.SECOND );
        String timeAndDate= year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second+"-" ;
        return timeAndDate;
    }
    
    @Override
    protected boolean consumerCode( List<GameLogEntry> newQ ) {
        GameLogWriter.writeToFile( startTime, newQ, getFile(false) );
        GameLogWriter.writeToFile( startTime, newQ, getFile(true) );
        return true;
    }

    private File getFile(boolean temp){
        String seqStr =""+seq;
        while (seqStr.length( )<6)seqStr="0"+seqStr;
        String prefix= fixedPrefix+seqStr+"-";
        String suffix= ".xml";
        File file;
        try {
            if (temp){
                file = File.createTempFile( prefix, suffix );
            } else {
                File parent = new File("tracking/");
                if (!parent.exists( )){
                    parent.mkdirs( );
                }
                file = new File("tracking/"+prefix+suffix);
            }
            seq++;
        }
        catch( IOException e ) {
            file = new File (prefix+suffix);
            e.printStackTrace();
        }
        return file;
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
        // Nothing needs initialization
    }
}
