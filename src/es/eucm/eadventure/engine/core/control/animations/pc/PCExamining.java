package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.gamedata.elements.Player;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The examining animation for the player
 */
public class PCExamining extends PCState {
    
    /**
     * Creates a new PCExamining
     * @param player the reference to the player
     */
    public PCExamining( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
       FunctionalElement element = player.getFinalElement( );
        if( element.examine( ) ) {
            player.setDirection( getCurrentDirection() );
            player.setState( FunctionalPlayer.IDLE );
        } else
            player.speak( player.getFinalElement( ).getElement( ).getDetailedDescription( ) );
    }

    @Override
    public void initialize( ) {
        if( player.getFinalElement( ).isInInventory( ) ) {
            player.setDirection( AnimationState.SOUTH );
        } else {
            //player.setDirection( AnimationState.NORTH );
        }
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
}
