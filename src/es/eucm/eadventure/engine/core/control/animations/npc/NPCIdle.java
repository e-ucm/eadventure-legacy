package es.eucm.eadventure.engine.core.control.animations.npc;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * An state for an idle npc
 */
public class NPCIdle extends NPCState {

    /**
     * Creates a new NPCIdle
     * @param npc the reference to the npc
     */
    public NPCIdle( FunctionalNPC npc ) {
        super( npc );
    }

    @Override
    public void update( long elapsedTime ) {
    }

    @Override
    public void initialize( ) {
        if (getCurrentDirection() == -1)
        	setCurrentDirection( SOUTH );
        npc.setSpeedX( 0.0f );
        npc.setSpeedY( 0.0f );
    }
    
    @Override
    public void loadResources() {
        Resources resources = npc.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
        if (resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals(AssetsController.ASSET_EMPTY_ANIMATION))
        	animations[WEST] = multimedia.loadAnimation( resources.getAssetPath(NPC.RESOURCE_TYPE_STAND_LEFT), false, MultimediaManager.IMAGE_SCENE);
        else
        	animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_SCENE );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_SCENE );

    }
}
