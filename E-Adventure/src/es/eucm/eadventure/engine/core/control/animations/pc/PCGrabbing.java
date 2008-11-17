package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.common.data.chapterdata.elements.Player;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The grabbing animation for the player
 */
public class PCGrabbing extends PCState {
    
    private long totalTime;

    /**
     * Creates a new PCGrabbing
     * @param player the reference to the player
     */
    public PCGrabbing( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        totalTime += elapsedTime;
        if( totalTime > 1000 ) {
            FunctionalItem item = (FunctionalItem) player.getFinalElement( );
            
            if( item.grab( ) )
                player.setState( FunctionalPlayer.IDLE );
            else
                player.speak( GameText.getTextGrabCannot( ) );
        }
    }

    @Override
    public void initialize( ) {
        totalTime = 0;
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
    }
}
