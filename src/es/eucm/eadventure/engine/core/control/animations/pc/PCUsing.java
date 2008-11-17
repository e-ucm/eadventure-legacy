package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.common.data.chapterdata.elements.Player;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The using animation for the player
 */
public class PCUsing extends PCState {
    
    private long totalTime;

    /**
     * Creates a new PCUsing
     * @param player the reference to the player
     */
    public PCUsing( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        totalTime += elapsedTime;
        if( totalTime > 1000 ) {
            FunctionalItem item1 = (FunctionalItem) player.getOptionalElement( );
            FunctionalItem item2 = (FunctionalItem) player.getFinalElement( );
    
            if( item1.useWith( item2 ) | item2.useWith( item1 ) )
                player.setState( FunctionalPlayer.IDLE );
            else
                player.speak( GameText.getTextUseCannot( ) );
        }
    }

    @Override
    public void initialize( ) {
        totalTime = 0;
        //System.out.println( getCurrentDirection() );
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_DOWN ), true, MultimediaManager.IMAGE_PLAYER );
    }
}
