package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.gamedata.elements.Player;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The looking animation for the player
 */
public class PCLooking extends PCState {

    /**
     * Creates a new PCLooking
     * @param player the reference to the player
     */
    public PCLooking( FunctionalPlayer player ) {
        super( player );
    }
    
    @Override
    public void initialize( ) {
        if( player.getFinalElement( ).isInInventory( ) ) {
            player.setDirection( AnimationState.SOUTH );
        } else {
            if( player.getFinalElement( ).getX( ) < player.getX( ) )
                player.setDirection( AnimationState.WEST );
            else
                player.setDirection( AnimationState.EAST );
        }
    }

    @Override
    public void update( long elapsedTime ) {
        player.speak( player.getFinalElement( ).getElement( ).getDescription( ) );
    }

    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
}
