package es.eucm.eadventure.engine.core.control.animations.npc;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class NPCWalking extends NPCState{

    /**
     * Creates a new NPCIdle
     * @param npc the reference to the npc
     */
    public NPCWalking( FunctionalNPC npc ) {
        super( npc );
    }

    @Override
    public void update( long elapsedTime ) {
        if( ( npc.getSpeedX( ) > 0 && npc.getX( ) < npc.getDestX( ) ) || ( npc.getSpeedX( ) <= 0 && npc.getX( ) >= npc.getDestX( ) ) ) {
            npc.setX( npc.getX( ) + npc.getSpeedX( )*elapsedTime/1000 );
         } else {
             npc.setState( FunctionalNPC.IDLE );
             if (npc.getDirection() == -1)
            	 npc.setDirection( AnimationState.SOUTH );
         }
    }

    @Override
    public void initialize( ) {
        if( npc.getX( ) < npc.getDestX( ) ) {
            setCurrentDirection( EAST );
            npc.setSpeedX( FunctionalNPC.DEFAULT_SPEED );
        } else {
            setCurrentDirection( WEST );
            npc.setSpeedX( -FunctionalNPC.DEFAULT_SPEED );
        }
    }
    
    @Override
    public void loadResources() {
        Resources resources = npc.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
        if (resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ) != null && resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT) != AssetsController.ASSET_EMPTY_ANIMATION)
        	animations[WEST ] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT), false, MultimediaManager.IMAGE_SCENE);
        else
        	animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_SCENE );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_SCENE );
    }
    
}
