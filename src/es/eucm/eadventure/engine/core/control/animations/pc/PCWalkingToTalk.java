package es.eucm.eadventure.engine.core.control.animations.pc;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.common.data.chapter.ConversationReference;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The walking to talk animation for the player
 */
public class PCWalkingToTalk extends PCState {

    /**
     * Creates a new PCWalkingToTalk
     * @param player the reference to the player
     */
    public PCWalkingToTalk( FunctionalPlayer player ) {
        super( player );
    }

    @Override
    public void update( long elapsedTime ) {
        if( ( player.getSpeedX( ) > 0 && player.getX( ) < player.getDestX( ) - player.getWidth()/**1.2f*/ ) || ( player.getSpeedX( ) <= 0 && player.getX( ) >= player.getDestX( ) + player.getWidth()/**1.2f*/ ) ) {
            player.setX( player.getX( ) + player.getSpeedX( )*elapsedTime/1000 );
        }else {
            FunctionalNPC npc = (FunctionalNPC) player.getFinalElement( );
            List<ConversationReference> conversationReferences = npc.getNPC( ).getConversationReferences( );
            boolean triggeredConversation = false;
            
            for( int i = 0; i < conversationReferences.size( ) && !triggeredConversation; i++ ) {
                if( new FunctionalConditions( conversationReferences.get( i ).getConditions( ) ).allConditionsOk( ) ) {
                    Game.getInstance( ).setCurrentNPC( npc );
                    Game.getInstance( ).setConversation( conversationReferences.get( i ).getIdTarget( ) );
                    Game.getInstance( ).setState( Game.STATE_CONVERSATION );
                    triggeredConversation = true;
                }
            }
            
            if( triggeredConversation )
                player.setState( FunctionalPlayer.IDLE );
            else
                player.speak( GameText.getTextTalkCannot( ) );
        }
    }

    @Override
    public void initialize( ) {
        FunctionalNPC npc = (FunctionalNPC) player.getFinalElement( );
        List<ConversationReference> conversationReferences = npc.getNPC( ).getConversationReferences( );     
        boolean anyConversation = false;
        for( int i = 0; i < conversationReferences.size( ) && !anyConversation; i++ )
            if( new FunctionalConditions( conversationReferences.get( i ).getConditions( ) ).allConditionsOk( ) )
                anyConversation = true;
        
        if( anyConversation ) {
        
            if( player.getX( ) < player.getFinalElement( ).getX( ) ) {
                setCurrentDirection( EAST );
                player.setSpeedX( FunctionalPlayer.DEFAULT_SPEED );
            } else {
                setCurrentDirection( WEST );
                player.setSpeedX( -FunctionalPlayer.DEFAULT_SPEED );
            }
        
        } else {
            player.speak( GameText.getTextTalkCannot( ) );
        }
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
}
