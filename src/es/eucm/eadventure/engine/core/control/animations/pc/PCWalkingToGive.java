package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The walking to give animation for the player
 */
public class PCWalkingToGive extends PCState {

    /**
     * Creates a new PCWalkingToGive
     * @param player the reference to the player
     */
    public PCWalkingToGive( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        if( ( player.getSpeedX( ) > 0 && player.getX( ) < player.getDestX( ) - player.getWidth() ) || ( player.getSpeedX( ) <= 0 && player.getX( ) >= player.getDestX( ) + player.getWidth() ) )
            player.setX( player.getX( ) + player.getSpeedX( )*elapsedTime/1000 );
        else
            player.setState( FunctionalPlayer.GIVE );
    }

    @Override
    public void initialize( ) {
        FunctionalElement element = player.getOptionalElement( );
        if( element.isInInventory( ) ) {
            if( player.getX( ) < player.getFinalElement( ).getX( ) ) {
                setCurrentDirection( EAST );
                player.setSpeedX( FunctionalPlayer.DEFAULT_SPEED );
            } else {
                setCurrentDirection( WEST );
                player.setSpeedX( -FunctionalPlayer.DEFAULT_SPEED );
            }
        } else {
            player.setState( FunctionalPlayer.IDLE );
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
