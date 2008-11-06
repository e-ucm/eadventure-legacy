package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.core.data.gamedata.elements.Player;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The using animation for the player
 */
public class PCUsingSingle extends PCState {
    
    private long totalTime;

    /**
     * Creates a new PCUsingSingle
     * @param player the reference to the player
     */
    public PCUsingSingle( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        totalTime += elapsedTime;
        if( totalTime > 1000 ) {
            FunctionalItem item = (FunctionalItem) player.getFinalElement( );
    
            if( item.use( ) )
                player.setState( FunctionalPlayer.IDLE );
            else
                player.speak( GameText.getTextUseCannot( ) );
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
