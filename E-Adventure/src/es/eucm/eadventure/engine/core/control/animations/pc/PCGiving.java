package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The giving animation for the player
 */
public class PCGiving extends PCState {
    
    private long totalTime;

    /**
     * Creates a new PCGiving
     * @param player the reference to the player
     */
    public PCGiving( FunctionalPlayer player ) {
        super( player );
    }
    
    @Override
    public void initialize( ) {
        totalTime = 0;
    }

    @Override
    public void update( long elapsedTime ) {
        totalTime += elapsedTime;
        if( totalTime > 1000 ) {
            FunctionalItem item = (FunctionalItem) player.getOptionalElement( );
            FunctionalNPC npc = (FunctionalNPC) player.getFinalElement( );
    
            if( item.giveTo( npc ) )
                player.setState( FunctionalPlayer.IDLE );
            else
                player.speak( GameText.getTextGiveCannot( ) );
        }
    }

    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
    }
}
