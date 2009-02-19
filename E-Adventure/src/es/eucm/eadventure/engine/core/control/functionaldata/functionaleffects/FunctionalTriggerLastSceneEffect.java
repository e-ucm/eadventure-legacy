package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * Special case of FunctionalTriggerSceneEffect. Triggers the last scene rendered
 * on the screen
 * @author Javier
 *
 */
public class FunctionalTriggerLastSceneEffect extends FunctionalTriggerSceneEffect{

    private static String getLastSceneId(){
        if (Game.getInstance( ).getLastScene( )!=null){
            return Game.getInstance( ).getLastScene( ).getTargetId( );
        }
        return null;
    }
    
    private static int getLastSceneX(){
        if (Game.getInstance( ).getLastScene( )!=null){
            return Game.getInstance( ).getLastScene( ).getPositionX( );
        }
        return Integer.MIN_VALUE;
    }
    
    private static int getLastSceneY(){
        if (Game.getInstance( ).getLastScene( )!=null){
            return Game.getInstance( ).getLastScene( ).getPositionY( );
        }
        return Integer.MIN_VALUE;
    }

    
    public FunctionalTriggerLastSceneEffect( ) {
        super( new TriggerSceneEffect (null, Integer.MIN_VALUE, Integer.MIN_VALUE) );
    }


    public void triggerEffect( ) {
    	effect = new TriggerSceneEffect(getLastSceneId(), getLastSceneX(),getLastSceneY());
        super.triggerEffect( );
    }
    
}
