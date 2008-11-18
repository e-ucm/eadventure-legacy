package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.StopEvent;

import de.schlichtherle.io.FileOutputStream;
import de.schlichtherle.io.FileWriter;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Videoscene;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * A game main loop while a "videoscene" is being displayed
 */
public class GameStateVideoscene extends GameState  implements ControllerListener {

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

    /**
     * Creates a new GameStateVideoscene
     */
    public GameStateVideoscene( ) {
        super( );
        videoscene = (Videoscene) game.getGameData( ).getGeneralScene( game.getNextScene( ).getNextSceneId( ) );

        stop = true;
        this.prefetched=false;
        
        try {
            
            Graphics2D g = GUI.getInstance( ).getGraphics( );
            g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            GUI.drawString( g, "Please wait", 400, 280 );
            GUI.drawString( g, "Loading ...", 400, 300 );
            GUI.getInstance( ).endDraw( );
            g.dispose( );
            
            Resources resources = createResourcesBlock( );
            
            // TODO Revisar, se ha eliminado el getResourceAsURL
            mediaPlayer = Manager.createRealizedPlayer( ResourceHandler.getInstance( ).getResourceAsMediaLocator( resources.getAssetPath( Videoscene.RESOURCE_TYPE_VIDEO ) ) );
            mediaPlayer.addControllerListener( this );
            this.blockingPrefetch( );
            //mediaPlayer.start( );
            Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
            video = mediaPlayer.getVisualComponent( );

            if( video != null ) {
                video.addMouseListener( Game.getInstance( ) );

                GUI.getInstance( ).getFrame( ).removeAll( );
                GUI.getInstance( ).showComponent( video );
                //GUI.getInstance( ).getFrame( ).add( video, BorderLayout.CENTER );
                GUI.getInstance( ).getFrame( ).createBufferStrategy( 1 );
                GUI.getInstance( ).getFrame( ).validate( );
                GUI.getInstance( ).getFrame( ).repaint( );

                stop = false;
                mediaPlayer.start( );
            }
        } catch( Exception e ) {
            
            e.printStackTrace( );
            loadNextScene( );
        }
        

    }

    private void loadNextScene( ) {
        if( video != null ) {
            mediaPlayer.stop( );

            //GUI.getInstance( ).getFrame( ).remove( video );
            
            GUI.getInstance( ).getFrame( ).createBufferStrategy( 2 );
            GUI.getInstance( ).getFrame( ).validate( );
            GUI.getInstance( ).restoreFrame( );

            mediaPlayer.deallocate( );
        }

        if( videoscene.isEndScene( ) )
            game.goToNextChapter( );

        else {
            NextScene nextScene = null;

            for( NextScene currentNextScene : videoscene.getNextScenes( ) )
                if( new FunctionalConditions( currentNextScene.getConditions( )).allConditionsOk( ) )
                    nextScene = currentNextScene;

            if( nextScene != null ) {
                game.setNextScene( nextScene );
                game.setState( Game.STATE_NEXT_SCENE );
            }

            else
                game.setState( Game.STATE_RUN_EFFECTS );
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    public void mainLoop( long elapsedTime, int fps ) {
        if( stop ){//|| !( mediaPlayer.getDuration( ).getNanoseconds( ) > mediaPlayer.getMediaTime( ).getNanoseconds( ) ) ) {
            loadNextScene( );
        }
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        stop = true;
    }
    
    public synchronized void blockingPrefetch( ) {
        if (mediaPlayer!=null){
            mediaPlayer.prefetch( );
            while( !prefetched ) {
                try {
                    wait( );
                } catch( InterruptedException e ) {
                    System.out.println( "Interrupted while waiting on realize...exiting." );
                    System.exit( 1 );
                }
            }
        }
    }
    
    public synchronized void controllerUpdate( ControllerEvent event ) {
        if( event instanceof RealizeCompleteEvent ) {
            //realized = true;
            notify( );
        } else if( event instanceof EndOfMediaEvent ) {
            //eomReached = true;
            loadNextScene();
        } else if( event instanceof StopEvent ) {
            //stoped = true;
            notify( );
        } else if( event instanceof PrefetchCompleteEvent ) {
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
            if( new FunctionalConditions(videoscene.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
                newResources = videoscene.getResources( ).get( i );

        // If no resource block is available, create a default, empty one 
        if (newResources == null){
            newResources = new Resources();
        }
        return newResources;
    }
}
