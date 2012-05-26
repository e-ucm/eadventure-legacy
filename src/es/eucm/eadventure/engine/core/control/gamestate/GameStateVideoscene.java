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
package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.StopEvent;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.Videoscene;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.InputStreamDataSource;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.tracking.pub._HighLevelEvents;

/**
 * A game main loop while a "videoscene" is being displayed
 */
public class GameStateVideoscene extends GameState implements ControllerListener, _HighLevelEvents {

    /**
     * Videoscene being played
     */
    private Videoscene videoscene;

    /**
     * Multimedia player
     */
    private Player mediaPlayer;

    /**
     * Stores if the video has stopped
     */
    private boolean stop;

    /**
     * Component to display the video
     */
    private Component video;

    private boolean prefetched;

    private InputStreamDataSource ds;
    
    /**
     * Creates a new GameStateVideoscene
     */
    public GameStateVideoscene( ) {

        super( );
        videoscene = (Videoscene) game.getCurrentChapterData( ).getGeneralScene( game.getNextScene( ).getNextSceneId( ) );

        stop = true;
        this.prefetched = false;

        try {


            final Resources resources = createResourcesBlock( );

            Runtime.getRuntime( ).gc( );

            System.out.println("Free memory: " + Runtime.getRuntime( ).freeMemory( ));
            
            if (Runtime.getRuntime( ).freeMemory( ) < 20000000) {
                Graphics2D g = GUI.getInstance( ).getGraphics( );
                g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
                GUI.drawString( g, "Appologies from <e-Adventure>", 400, 280 );
                GUI.drawString( g, "Sorry, there is not enough memory to show the video", 400, 300 );
                GUI.getInstance( ).endDraw( );
                g.dispose( );
                try {
                    wait( 5000 );
                }
                catch( InterruptedException e ) {
                    System.out.println( "Interrupted while waiting on realize...exiting." );
                    System.exit( 1 );
                }
                throw new Exception("Not enough memory");
            }
            
            Graphics2D g = GUI.getInstance( ).getGraphics( );
            g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            GUI.drawString( g, "Please wait", 400, 280 );
            GUI.drawString( g, "Loading ...", 400, 300 );
            GUI.getInstance( ).endDraw( );
            g.dispose( );

            // TODO se ha cambiado el c—digo para utilizar el nuevo sistema que carga directamente los videos como InputStreams
            // mediaPlayer = Manager.createRealizedPlayer( ResourceHandler.getInstance( ).getResourceAsMediaLocator( resources.getAssetPath( Videoscene.RESOURCE_TYPE_VIDEO ) ) );
            ds = new InputStreamDataSource(resources.getAssetPath( Videoscene.RESOURCE_TYPE_VIDEO ) );
            ds.connect( );
            Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
            mediaPlayer = Manager.createRealizedPlayer( ds );

            mediaPlayer.addControllerListener( this );
            this.blockingPrefetch( );

            video = mediaPlayer.getVisualComponent( );

            /*
             * To keep original width/height rate 
             */
            int w=video.getPreferredSize( ).width;
            
            int h=video.getPreferredSize( ).height - 14;
            
            
            if( video != null ) {
                video.addMouseListener( Game.getInstance( ) );
                if ( w>0 && h>0 ){
                    GUI.getInstance( ).showComponent( video, w, h );
                } else { 
                    GUI.getInstance( ).showComponent( video );
                }

                stop = false;
                mediaPlayer.start( );
                video.requestFocus( );
            }
        }
        catch( Exception e ) {
            loadNextScene( );
        }
        catch( OutOfMemoryError e ) {
            loadNextScene( );
        }

        game.getGameLog( ).highLevelEvent( VIDEOSCENE_ENTER, videoscene.getId( ) );
        
    }

    private void loadNextScene( ) {

        if( video != null ) {
            mediaPlayer.stop( );
        }


        if (mediaPlayer != null) {
            mediaPlayer.close( );
            mediaPlayer.deallocate( );
            mediaPlayer = null;
        }

        if (ds != null) {
            ds.disconnect( );
            ds = null;
        }

        System.gc( );

        GUI.getInstance( ).getFrame( ).createBufferStrategy( 2 );
        GUI.getInstance( ).getFrame( ).validate( );
        GUI.getInstance( ).restoreFrame( );

        if( videoscene.getNext( ) == Cutscene.ENDCHAPTER )
            game.goToNextChapter( );
        else if( videoscene.getNext( ) == Cutscene.NEWSCENE ) {
            Exit exit = new Exit( videoscene.getTargetId( ) );
            exit.setDestinyX( videoscene.getPositionX( ) );
            exit.setDestinyY( videoscene.getPositionY( ) );
            exit.setPostEffects( videoscene.getEffects( ) );
            exit.setTransitionTime( videoscene.getTransitionTime( ) );
            exit.setTransitionType( videoscene.getTransitionType( ) );
            game.setNextScene( exit );
            game.setState( Game.STATE_NEXT_SCENE );
        }
        else {
            if( game.getFunctionalScene( ) == null ) {
                JOptionPane.showMessageDialog( null, TC.get( "DesignError.Message" ), TC.get( "DesignError.Title" ), JOptionPane.ERROR_MESSAGE );
                game.goToNextChapter( );
            }
            FunctionalEffects.storeAllEffects( new Effects( ) );
        }
        game.getGameLog( ).highLevelEvent( VIDEOSCENE_EXIT, videoscene.getId( ) );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {
        if( stop ) {//|| !( mediaPlayer.getDuration( ).getNanoseconds( ) > mediaPlayer.getMediaTime( ).getNanoseconds( ) ) ) {
            loadNextScene( );
        }
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        game.getGameLog( ).highLevelEvent( VIDEOSCENE_SKIP, videoscene.getId( ) );
        // Check if the video scene can be skipped before skip it
        if (videoscene.isCanSkip( ))
            stop = true;
    }
    
    @Override
    public void keyPressed( KeyEvent e ) {
        mouseClicked(null);
    }

    public synchronized void blockingPrefetch( ) {
        if( mediaPlayer != null ) {
            mediaPlayer.prefetch( );
            while( !prefetched ) {
                try {
                    wait( );
                }
                catch( InterruptedException e ) {
                    System.out.println( "Interrupted while waiting on realize...exiting." );
                    System.exit( 1 );
                }
            }
        }
    }

    public synchronized void controllerUpdate( ControllerEvent event ) {

        if( event instanceof RealizeCompleteEvent ) {
            System.out.println("RealizeCompleteEvent");
            //realized = true;
            notify( );
        }
        else if( event instanceof EndOfMediaEvent ) {
            System.out.println("EndOfMediaEvent");
            //eomReached = true;
            loadNextScene( );
        }
        else if( event instanceof StopEvent ) {
            System.out.println("StopEvent");
            //stoped = true;
            notify( );
        }
        else if( event instanceof PrefetchCompleteEvent ) {
            System.out.println("PrefetchCompleteEvent");
            prefetched = true;
            notify( );
        }
        //(else if (event instanceof )
    }

    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < videoscene.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( videoscene.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = videoscene.getResources( ).get( i );

        // If no resource block is available, create a default, empty one 
        if( newResources == null ) {
            newResources = new Resources( );
        }
        return newResources;
    }

}
