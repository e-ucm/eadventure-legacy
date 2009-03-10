package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Graphics2D;

import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalPlayAnimationEffect;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * A game main loop while the effects of an action are being performed
 */
public class GameStateRunEffects extends GameState {
    
    /**
     * The current effect being executed
     */
    private FunctionalEffect currentExecutingEffect;
        
    /**
     * Constructor
     */
    public GameStateRunEffects(boolean fromConversation) {
        super();
        currentExecutingEffect = null;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    public void mainLoop( long elapsedTime, int fps ) {
        
        game.getActionManager( ).setElementOver( null );
        game.getActionManager( ).setExitCustomized( null, null );
        
        // Toggle the HUD off and set the default cursor
        GUI.getInstance( ).toggleHud( false );
        GUI.getInstance( ).setDefaultCursor( );
        
        if (game.getFunctionalScene()!=null)
        	game.getFunctionalScene( ).update( elapsedTime );
        GUI.getInstance( ).update( elapsedTime );

        Graphics2D g = GUI.getInstance( ).getGraphics( );
        g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );

        // Draw the scene
        if (game.getFunctionalScene()!=null)
        	game.getFunctionalScene( ).draw( );
        GUI.getInstance( ).drawScene( g , elapsedTime);
        GUI.getInstance( ).drawHUD( g );
        
        
       
        
        // Draw the FPS
        //g.setColor( Color.WHITE );
        //g.drawString( Integer.toString( fps ), 780, 14 );

        // If no effect is being executed, or it has stopped running
        if( currentExecutingEffect == null || !currentExecutingEffect.isStillRunning( ) ) {
            
            // Delete the current effect
            currentExecutingEffect = null;
            
            
            
            // If no more effects must be executed, switch the state
            if( game.isEmptyFIFOinStack() ) {
                //XXX MODIFIED
                //game.updateDataPendingFromFlags( false );
                System.gc( );
                GUI.getInstance().toggleHud( true );
                //XXX MODIFIED by angel; con esta modificacion sobra la variable fromConversation
               /* if (fromConversation)
                    game.setAndPopState( );
                else
                    game.setState( Game.STATE_PLAYING );*/
                
                // Look if there are some stored state, and change to correct one.               
                game.setAndPopState( );
                               
            } 
            
            boolean stop = false;
            // Execute effects while some of them is not instantaneous
            
            while( !stop && !game.isEmptyFIFOinStack() ) {
                FunctionalEffect currentEffect = game.getFirstElementOfTop();
                currentEffect.triggerEffect( );
                stop = !currentEffect.isInstantaneous( );
                if( stop )
                    currentExecutingEffect = currentEffect;
            }
        }
   
        
        // Special conditions for the play animation effect
        // FIXME Edu: ¿Mover esto de aqui?
        else if ( currentExecutingEffect != null && currentExecutingEffect.isStillRunning( )) {
            if(currentExecutingEffect instanceof FunctionalPlayAnimationEffect){
                ((FunctionalPlayAnimationEffect)currentExecutingEffect).draw( g );
                ((FunctionalPlayAnimationEffect)currentExecutingEffect).update( elapsedTime );
            }
        }
        
        GUI.getInstance( ).endDraw( );
        g.dispose( );
    }
}
