package es.eucm.eadventure.engine.core.control.gamestate;

import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalScene;
import es.eucm.eadventure.engine.core.data.gamedata.NextScene;
import es.eucm.eadventure.engine.core.data.gamedata.scenes.GeneralScene;
import es.eucm.eadventure.engine.core.data.gamedata.scenes.Scene;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * A game main loop while a next scene is being processed
 */
public class GameStateNextScene extends GameState {

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    public void mainLoop( long elapsedTime, int fps ) {
        
        // Flush the image pool and the garbage colector
        MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_SCENE );
        System.gc( );
        
        // Pick the next scene, and the scene related to it
        NextScene nextScene = game.getNextScene( );
        GeneralScene generalScene = game.getGameData( ).getGeneralScene( nextScene.getNextSceneId( ) );

        // Depending on the type of the scene
        switch( generalScene.getType( ) ) {
            case GeneralScene.SCENE:
                // If it is a scene
                Scene scene = (Scene) generalScene;
                
                // Set the loading state
                game.setState( Game.STATE_LOADING );
                
                // Create a background music identifier to not replay the music from the start
                long backgroundMusicId = -1;
                
                // If there is a funcional scene
                if( game.getFunctionalScene( ) != null ) {
                   // Take the old and the new music path
                    String oldMusicPath = null;
                    for( int i = 0; i < game.getFunctionalScene( ).getScene( ).getResources( ).size( ) && oldMusicPath == null; i++ )
                        if( game.getFunctionalScene( ).getScene( ).getResources( ).get( i ).getConditions( ).allConditionsOk( ) )
                            oldMusicPath = game.getFunctionalScene( ).getScene( ).getResources( ).get( i ).getAssetPath( Scene.RESOURCE_TYPE_MUSIC );
                    String newMusicPath = null;
                    for( int i = 0; i < scene.getResources( ).size( ) && newMusicPath == null; i++ )
                        if( scene.getResources( ).get( i ).getConditions( ).allConditionsOk( ) )
                            newMusicPath = scene.getResources( ).get( i ).getAssetPath( Scene.RESOURCE_TYPE_MUSIC );
                    
                    // If the music paths are the same, take the music identifier
                    if( oldMusicPath != null && newMusicPath != null && oldMusicPath.equals( newMusicPath ) )
                        backgroundMusicId = game.getFunctionalScene( ).getBackgroundMusicId( );
                    else
                        game.getFunctionalScene( ).stopBackgroundMusic( );
                }

                // Create the new functional scene
                game.setFunctionalScene( new FunctionalScene( scene, game.getFunctionalPlayer( ), backgroundMusicId ) );
                
                // Set the player position
                if( nextScene.hasPlayerPosition( ) ) {
                    game.getFunctionalPlayer( ).setX( nextScene.getX( ) );
                    game.getFunctionalPlayer( ).setY( nextScene.getY( ) );
                }
                
                // If no next scene position was defined, use the scene default
                else if( scene.hasDefaultPosition( ) ) {
                    game.getFunctionalPlayer( ).setX( scene.getDefaultX( ) );
                    game.getFunctionalPlayer( ).setY( scene.getDefaultY( ) );
                }
                
                // If no position was defined at all, place the player in the middle
                else {
                    game.getFunctionalPlayer( ).setX( GUI.getInstance( ).getGameAreaWidth( ) / 2 );
                    game.getFunctionalPlayer( ).setY( GUI.getInstance( ).getGameAreaHeight( ) / 2 );
                }

                // Set the state of the player and the action manager
                game.getFunctionalPlayer( ).setState( FunctionalPlayer.IDLE );
                game.getActionManager( ).setActionSelected( ActionManager.ACTION_GOTO );
                
                // Play the post effects only if we arrive to a playable scene
                nextScene.getPostEffects( ).storeAllEffects( );

                // Switch to run effects node
                game.setState( Game.STATE_RUN_EFFECTS );
                break;
                
            case GeneralScene.SLIDESCENE:
                // Stop the music
                if( game.getFunctionalScene( ) != null )
                    game.getFunctionalScene( ).stopBackgroundMusic( );
                
                // If it is a slidescene, load the slidescene
                game.setState( Game.STATE_SLIDE_SCENE );
                break;
                
            case GeneralScene.VIDEOSCENE:
                // Stop the music
                if( game.getFunctionalScene( ) != null )
                    game.getFunctionalScene( ).stopBackgroundMusic( );
                
                // If it is a videoscene, load the videoscene
                game.setState( Game.STATE_VIDEO_SCENE );
                break;
        }
    }
}
