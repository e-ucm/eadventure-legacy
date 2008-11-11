package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;

public class TriggerLastSceneEffect extends TriggerSceneEffect{

    private static String getLastSceneId(){
        if (Game.getInstance( ).getLastScene( )!=null){
            return Game.getInstance( ).getLastScene( ).getNextSceneId( );
        }
        return null;
    }
    
    private static int getLastSceneX(){
        if (Game.getInstance( ).getLastScene( )!=null){
            return Game.getInstance( ).getLastScene( ).getX( );
        }
        return Integer.MIN_VALUE;
    }
    
    private static int getLastSceneY(){
        if (Game.getInstance( ).getLastScene( )!=null){
            return Game.getInstance( ).getLastScene( ).getY( );
        }
        return Integer.MIN_VALUE;
    }

    
    public TriggerLastSceneEffect( ) {
        super( null, Integer.MIN_VALUE, Integer.MIN_VALUE );
    }


    public void triggerEffect( ) {
        super.targetSceneId = getLastSceneId();
        super.x = getLastSceneX();
        super.y = getLastSceneY();
        super.triggerEffect( );
    }
    
}
