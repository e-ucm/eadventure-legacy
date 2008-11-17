package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.common.data.chapterdata.elements.Player;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The idle animation for the player
 */
public class PCIdle extends PCState {

    /**
     * Creates a new PCIdle
     * @param player the reference to the player
     */
    public PCIdle( FunctionalPlayer player ) {
        super( player );
        setCurrentDirection( SOUTH );
    }

    @Override
    public void update( long elapsedTime ) {
        // Do nothing
    }

    @Override
    public void initialize( ) {
        player.setOptionalElement( null );
        player.setFinalElement( null );
        Game.getInstance( ).getActionManager( ).setActionSelected( ActionManager.ACTION_GOTO );
        player.setSpeedX( 0.0f );
        player.setSpeedY( 0.0f );
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
}
