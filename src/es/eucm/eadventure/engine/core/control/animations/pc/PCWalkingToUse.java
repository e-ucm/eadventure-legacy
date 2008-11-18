package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The walking to use animation for the player
 */
public class PCWalkingToUse extends PCState {

    /**
     * Creates a new PCWalkingToUse
     * @param player the reference to the player
     */
    public PCWalkingToUse( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        if( ( player.getSpeedX( ) > 0 && player.getX( ) < player.getDestX( ) ) || ( player.getSpeedX( ) <= 0 && player.getX( ) >= player.getDestX( ) ) )
            player.setX( player.getX( ) + player.getSpeedX( )*elapsedTime/1000 );
        else
            player.setState( FunctionalPlayer.USE );
    }

    @Override
    public void initialize( ) {

        FunctionalElement firstElement = player.getOptionalElement( );
        FunctionalElement secondElement = player.getFinalElement( );

        // If both items are in the inventory, don't move
        if( firstElement.isInInventory( ) && secondElement.isInInventory( ) )
            player.setState( FunctionalPlayer.USE );

        // If one of them is in the inventory, move to the other one
        else if( firstElement.isInInventory( ) || secondElement.isInInventory( ) ) {
            if( firstElement.isInInventory( ) ) {
                player.setOptionalElement( firstElement );
                player.setFinalElement( secondElement );
            }

            else if( secondElement.isInInventory( ) ) {
                player.setOptionalElement( secondElement );
                player.setFinalElement( firstElement );
            }

            if( player.getX( ) < player.getFinalElement( ).getX( ) ) {
                setCurrentDirection( EAST );
                player.setSpeedX( FunctionalPlayer.DEFAULT_SPEED );
            } else {
                setCurrentDirection( WEST );
                player.setSpeedX( -FunctionalPlayer.DEFAULT_SPEED );
            }

        } 
        
        // If none of the items are in the inventory, cancel the action
        else
            player.speak( GameText.getTextUseCannot( ) );
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
