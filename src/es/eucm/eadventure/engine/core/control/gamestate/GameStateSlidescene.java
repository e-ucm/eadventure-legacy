package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.data.chapterdata.resources.Asset;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.ImageSet;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.common.data.chapterdata.NextScene;
import es.eucm.eadventure.common.data.chapterdata.book.Book;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;
import es.eucm.eadventure.common.data.chapterdata.scenes.Slidescene;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * A game main loop while a "slidescene" is being displayed
 */
public class GameStateSlidescene extends GameState {

    /**
     * Animation of the slidescene
     */
    private ImageSet slides;

    /**
     * Slidescene being played
     */
    private Slidescene slidescene;
    
    /**
     * Flag to control that we jump to the next chapter at most once
     */
    private boolean yetSkipped = false;

    /**
     * Creates a new GameStateSlidescene
     */
    public GameStateSlidescene( ) {
        super( );

        slidescene = (Slidescene) game.getGameData( ).getGeneralScene( game.getNextScene( ).getNextSceneId( ) );
        
        // Select the resources
        Resources resources = createResourcesBlock( );
        
        // Create the set of slides and start it
        slides = MultimediaManager.getInstance( ).loadSlides( resources.getAssetPath( Slidescene.RESOURCE_TYPE_SLIDES ), MultimediaManager.IMAGE_SCENE );
        slides.start( );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    public void mainLoop( long elapsedTime, int fps ) {
        // Paint the current slide
        Graphics2D g = GUI.getInstance( ).getGraphics( );
        g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        g.drawImage( slides.getImage( ), 0, 0, null );
        GUI.getInstance( ).endDraw( );
        g.dispose( );
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        
        // Display the next slide 
        boolean endSlides = slides.nextImage( );
        
        // If the slides have ended
        if( endSlides ) {

            // If it is a endscene, go to the next chapter
            if( !yetSkipped && slidescene.isEndScene( ) ) {
                yetSkipped = true;
                game.goToNextChapter( );
            }

            else {
                // Search for a next scene structure
                NextScene nextScene = null;
                for( NextScene currentNextScene : slidescene.getNextScenes( ) )
                    if( new FunctionalConditions(currentNextScene.getConditions( )).allConditionsOk( ) )
                        nextScene = currentNextScene;

                // If it had a next scene, jump to it
                if( nextScene != null ) {
                    game.setNextScene( nextScene );
                    game.setState( Game.STATE_NEXT_SCENE );
                }

                // If it had not a next scene, keep executing effects
                else
                    game.setState( Game.STATE_RUN_EFFECTS );
            }
        }
    }
    
    /**
     * Creates the current resource block to be used
     */
    private Resources createResourcesBlock( ) {
        
        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < slidescene.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions(slidescene.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
                newResources = slidescene.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
            newResources.addAsset( new Asset( Slidescene.RESOURCE_TYPE_SLIDES, ResourceHandler.DEFAULT_SLIDES ) );
        }
        return newResources;
    }
}
