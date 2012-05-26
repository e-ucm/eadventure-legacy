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

package es.eucm.eadventure.engine.core.gui;

import java.util.Timer;
import java.util.TimerTask;

import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * Class used to handle audio descriptions for ead1.4. It's necessary since we need to control each audio is played only once when the mouse hovers the element or button. This idea of remembering the last audio played has to bee shared among ContextualHUD, exits, etc.
 * @author Javier Torrente
 *
 */
public class GUIAudioDescriptionsHandler {

    private String lastAudioPlayed="";
    
    private long lastAudioReplay=0;
    
    private static final long REPEAT_MIN_MS=3000;
    
    /**
     * Checks if the sound path given as an argument has to be played. If so, it is automatically played.
     * Checks performed:
     *     Sound path is valid
     *     Sound path is different from the last audio description played
     * @param description
     */
    public void checkAndPlay( String soundPath ){
        if (lastAudioPlayed==null || (soundPath!= null && !soundPath.equals( "" ) && !soundPath.equals( " " ) &&
                !soundPath.equals( lastAudioPlayed ))/*&& this.playName*/){
            //Game.getInstance( ).getFunctionalPlayer( ).stopTalking( );
            /*if (Game.getInstance( ).getFunctionalPlayer( ).isTalking( ))
                Game.getInstance( ).getFunctionalPlayer( ).stopTalking( );
            
            Game.getInstance( ).getFunctionalPlayer( ).speak( "", description.getNameSoundPath( ) );*/
            Timer timer = new Timer ();
            timer.schedule( new ScheduledSoundPlay(soundPath), 1 );
            lastAudioPlayed=soundPath;
            //this.playName = false;
        }
        
    }
    
    /**
     * Resets the system. This method has to be invoked when the mouse exits an element
     */
    public void reset(){
        this.lastAudioPlayed = null;
    }
    
    public void repeat(){
        if (this.lastAudioPlayed!=null){
            long currentTime = System.currentTimeMillis( );
            long gap = currentTime - this.lastAudioReplay;
            if (gap>=REPEAT_MIN_MS){
                Timer timer = new Timer ();
                timer.schedule( new ScheduledSoundPlay(lastAudioPlayed), 1 );
                this.lastAudioReplay = currentTime;
            }
        }
    }
    
    private class   ScheduledSoundPlay  extends TimerTask {
        private String path;

        public ScheduledSoundPlay( String path ) {

            super( );
            this.path = path;
        }

        @Override
        public void run( ) {
            MultimediaManager.getInstance( ).stopAllSounds( );
            MultimediaManager.getInstance( ).startPlaying( MultimediaManager.getInstance( ).loadSound( path, false ) );
        }
        
    };

    
    
    
}
