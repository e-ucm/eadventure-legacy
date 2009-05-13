package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Component;
import java.awt.Graphics2D;
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
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
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
        videoscene = (Videoscene) game.getCurrentChapterData( ).getGeneralScene( game.getNextScene( ).getNextSceneId() );

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

                //GUI.getInstance( ).getFrame( ).removeAll( );
                GUI.getInstance( ).showComponent( video );
                ////GUI.getInstance( ).getFrame( ).add( video, BorderLayout.CENTER );
                //GUI.getInstance( ).getFrame( ).createBufferStrategy( 1 );
                //GUI.getInstance( ).getFrame( ).validate( );
                //GUI.getInstance( ).getFrame( ).repaint( );

                stop = false;
                mediaPlayer.start( );
            }
        } catch( Exception e ) {
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
            mediaPlayer.close();
            mediaPlayer = null;
            System.gc();
        }

        if( videoscene.getNext() == Cutscene.ENDCHAPTER )
            game.goToNextChapter( );
        else if (videoscene.getNext() == Cutscene.NEWSCENE ){
            	Exit exit = new Exit(videoscene.getTargetId());
            	exit.setDestinyX(videoscene.getPositionX());
            	exit.setDestinyY(videoscene.getPositionY());
            	exit.setPostEffects(videoscene.getEffects());
            	exit.setTransitionTime(videoscene.getTransitionTime());
            	exit.setTransitionType(videoscene.getTransitionType());
                game.setNextScene( exit );
                game.setState( Game.STATE_NEXT_SCENE );
        }
         else {
             if (game.getFunctionalScene() == null) {
          	   JOptionPane.showMessageDialog(null, TextConstants.getText("DesignError.Message"), TextConstants.getText("DesignError.Title"), JOptionPane.ERROR_MESSAGE);
          	   game.goToNextChapter();
             }
             FunctionalEffects.storeAllEffects(new Effects());
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
