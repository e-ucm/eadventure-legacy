package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The walking animation to the player
 */
public class PCWalking extends PCState {

    /**
     * Creates a new PCWalking
     * @param player the reference to the player
     */
    public PCWalking( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
       if( ( player.getSpeedX( ) > 0 && player.getX( ) < player.getDestX( ) ) || ( player.getSpeedX( ) <= 0 && player.getX( ) >= player.getDestX( ) ) )
            player.setX( player.getX( ) + player.getSpeedX( )*elapsedTime/1000 );
        else
            player.setState( FunctionalPlayer.IDLE );
    }

    @Override
    public void initialize( ) {
        if( player.getX( ) < player.getDestX( ) ) {
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
