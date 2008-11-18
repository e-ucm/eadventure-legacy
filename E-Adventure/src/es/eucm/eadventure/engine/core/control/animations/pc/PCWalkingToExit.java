package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalNextSceneEffect;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The walking to exit animation for the player
 */
public class PCWalkingToExit extends PCState {

    /**
     * Creates a new PCWalkingToExit
     * @param player the reference to the player
     */
    public PCWalkingToExit( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        if( ( player.getSpeedX( ) > 0 && player.getX( ) < player.getDestX( ) ) || ( player.getSpeedX( ) <= 0 && player.getX( ) >= player.getDestX( ) ) ) {
            player.setX( player.getX( ) + player.getSpeedX( )*elapsedTime/1000 );
        } else {
            player.setState( FunctionalPlayer.IDLE );
            Exit exit = player.getTargetExit( );
            if( exit != null ) {
                NextScene nextScene = null;

                // Pick the FIRST valid next-scene structure
                for( int i = 0; i < exit.getNextScenes( ).size( ) && nextScene == null; i++ )
                    if( new FunctionalConditions ( exit.getNextScenes( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                        nextScene = exit.getNextScenes( ).get( i );

                if( nextScene != null ) {
                	FunctionalEffects.storeAllEffects(nextScene.getEffects( ));
                    Game.getInstance().enqueueEffect( new FunctionalNextSceneEffect( nextScene ) );
                }
            }
        }
    }

    @Override
    public void initialize( ) {
        
        if( player.getX( ) < (player.getTargetExit( ).getX0( ) + player.getTargetExit( ).getX1()) / 2 ) {
            setCurrentDirection( EAST );
            player.setSpeedX( FunctionalPlayer.DEFAULT_SPEED );
        } else {
            setCurrentDirection( WEST );
            player.setSpeedX( -FunctionalPlayer.DEFAULT_SPEED );
        }
            
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
}
