/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.core.control.animations.npc;

import java.util.Timer;
import java.util.TimerTask;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Options;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * A state for a talking npc
 */
public class NPCTalking extends NPCState {

    /**
     * The text the character will speak
     */
    private String[] text;

    private long audioId = -1;

    /**
     * Time spent in this state
     */
    private long totalTime;

    /**
     * The time the character will be talking
     */
    private int timeTalking;

    /**
     * This is an Voice object of FreeTTS, that is used to synthesize the sound
     * of a conversation line.
     */
    private Voice voice;

    /**
     * The speech must be launched in another thread
     */
    private TTask task;

    /**
     * Check if tts synthesizer is been used
     */
    private boolean ttsInUse;

    /**
     * Creates a new NPCTalking
     * 
     * @param npc
     *            the reference to the npc
     */
    public NPCTalking( FunctionalNPC npc ) {

        super( npc );
        ttsInUse = false;
    }

    /**
     * Set the text to be displayed. This is what the character is saying
     * 
     * @param text
     *            the text to be displayed
     */
    public void setText( String text ) {

        this.text = GUI.getInstance( ).splitText( text );

        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW )
            multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST )
            multiplier = 0.8f;

        timeTalking = (int) ( 300 * text.split( " " ).length * multiplier );
        if( timeTalking < (int) ( 1400 * multiplier ) )
            timeTalking = (int) ( 1400 * multiplier );
    }

    public void setAudio( String audioPath ) {

        if( audioPath == null ) {
            if( audioId != -1 ) {
                MultimediaManager.getInstance( ).stopPlayingInmediately( audioId );
                while( MultimediaManager.getInstance( ).isPlaying( audioId ) ) {
                    try {
                        Thread.sleep( 1 );
                    }
                    catch( InterruptedException e ) {
                    }
                }
                audioId = -1;
            }
        }
        else {

            if( audioId != -1 ) {
                MultimediaManager.getInstance( ).stopPlayingInmediately( audioId );
                while( MultimediaManager.getInstance( ).isPlaying( audioId ) ) {
                    try {
                        Thread.sleep( 1 );
                    }
                    catch( InterruptedException e ) {
                    }
                }
            }

            audioId = MultimediaManager.getInstance( ).loadSound( audioPath, false );

            MultimediaManager.getInstance( ).startPlaying( audioId );
            while( !MultimediaManager.getInstance( ).isPlaying( audioId ) ) {
                try {
                    Thread.sleep( 1 );
                }
                catch( InterruptedException e ) {
                }
            }
        }
    }

    public void setSpeakFreeTTS( String text, String voice ) {

        task = new TTask( voice, text );
        Timer timer = new Timer( );
        ttsInUse = true;
        timer.schedule( task, 0 );
    }

    public void stopTTSTalking( ) {

        if( task != null ) {
            task.cancel( );
            ttsInUse = false;
        }
    }

    @Override
    public void update( long elapsedTime ) {

        totalTime += elapsedTime;
        if( totalTime > timeTalking && ( audioId == -1 || !MultimediaManager.getInstance( ).isPlaying( audioId ) ) && ( !ttsInUse ) ) {
            npc.setState( FunctionalNPC.IDLE );
            stopTTSTalking( );
        }
    }

    @Override
    public void draw( int x, int y, float scale, int depth, FunctionalElement fe) {

        super.draw( x, y, scale, depth, fe );
        // If there is a line to speak, draw it
        if( text.length >= 1 && !text[0].equals( "" ) ) {
            if( npc.getShowsSpeechBubbles( ) )
                GUI.getInstance( ).addTextToDraw( text, x - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), y - Math.round( npc.getHeight( ) * scale ) - 15, npc.getTextFrontColor( ), npc.getTextBorderColor( ), npc.getBubbleBkgColor( ), npc.getBubbleBorderColor( ), true );
            else
                GUI.getInstance( ).addTextToDraw( text, x - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), y - Math.round( npc.getHeight( ) * scale ), npc.getTextFrontColor( ), npc.getTextBorderColor( ) );
        }
    }

    @Override
    public void initialize( ) {

        totalTime = 0;
        // Check the player mode
        if( Game.getInstance( ).getGameDescriptor( ).getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON || Game.getInstance( ).getFunctionalPlayer( ).getX( ) >= npc.getX( ) ) {
            setCurrentDirection( EAST );
        }
        else
            setCurrentDirection( WEST );
    }

    @Override
    public void loadResources( ) {

        Resources resources = npc.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        
     // added make the mirror when only is defined left animation
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
        else
            animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ), true, MultimediaManager.IMAGE_SCENE );

        if( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ), false, MultimediaManager.IMAGE_SCENE );
        else
            animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_SCENE );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_SCENE );
    }

    public class TTask extends TimerTask {

        private String text;

        private boolean deallocate;

        public TTask( String voiceText, String text ) {

            this.text = text;
            this.deallocate = false;
            VoiceManager voiceManager = VoiceManager.getInstance( );
            voice = voiceManager.getVoice( voiceText );
            voice.allocate( );
        }

        @Override
        public void run( ) {

            try {

                voice.speak( text );
                ttsInUse = false;

            }
            catch( IllegalStateException e ) {
                System.out.println( "TTS found one word which can not be processated." );
            }

        }

        @Override
        public boolean cancel( ) {

            if( !deallocate ) {
                voice.deallocate( );
                deallocate = true;
            }
            return true;

        }
    }
}
