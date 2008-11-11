package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.gamedata.elements.Player;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The walking to use animation for the player
 */
public class PCWalkingToUseSingle extends PCState {

    /**
     * Creates a new PCWalkingToUseSingle
     * @param player the reference to the player
     */
    public PCWalkingToUseSingle( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        if( ( player.getSpeedX( ) > 0 && player.getX( ) < player.getDestX( ) ) || ( player.getSpeedX( ) <= 0 && player.getX( ) >= player.getDestX( ) ) )
            player.setX( player.getX( ) + player.getSpeedX( )*elapsedTime/1000 );
        else
            player.setState( FunctionalPlayer.USE_SINGLE );
    }

    @Override
    public void initialize( ) {
        
        // Get the item which is going to be used
        FunctionalElement item = player.getFinalElement( );
        
        // If the item is in the inventory, move to the next state
        if( item.isInInventory( ) ) {
            player.setState( FunctionalPlayer.USE_SINGLE );
        }
        
        // If the item is not in the inventory, move the player
        else {
            if( player.getX( ) < item.getX( ) ) {
                setCurrentDirection( EAST );
                player.setSpeedX( FunctionalPlayer.DEFAULT_SPEED );
            } else {
                setCurrentDirection( WEST );
                player.setSpeedX( -FunctionalPlayer.DEFAULT_SPEED );
            }
        }
    }
    
    @Override
    public void loadResources( ) {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
}
