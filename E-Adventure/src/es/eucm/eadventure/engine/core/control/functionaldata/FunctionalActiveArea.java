package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class FunctionalActiveArea extends FunctionalItem{

    private ActiveArea activeArea;
    
    private static Item buildItem ( ActiveArea activeArea ){
        Item item = new Item(activeArea.getId( ), activeArea.getName( ), activeArea.getDescription( ), activeArea.getDetailedDescription( ) );
        for (Action action:activeArea.getActions( )){
            item.addAction( action );
        }
        item.addResources( new Resources() );
        return item;
    }
    
    private static int calculateX(ActiveArea activeArea){
        return activeArea.getX( ) + activeArea.getWidth( )/2;
    }
    
    private static int calculateY(ActiveArea activeArea){
        return  activeArea.getY( )+activeArea.getHeight( );        
    }
    
    
    public FunctionalActiveArea ( ActiveArea activeArea ){
        super ( buildItem(activeArea), calculateX(activeArea), calculateY(activeArea) );
        
        this.activeArea = activeArea;
        
        // Create transparent image
        BufferedImage bImagenTransparente = new BufferedImage(
              activeArea.getWidth( ), activeArea.getHeight( ), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bImagenTransparente.createGraphics();

        // Make all pixels transparent
        Color transparent = new Color(0, 0, 0, 0);
        g2d.setColor(transparent);
        g2d.setComposite(AlphaComposite.Src);
        g2d.fill(new Rectangle2D.Float(0, 0, activeArea.getWidth( ), activeArea.getHeight( )));
        g2d.dispose();
        
        image = bImagenTransparente;
        
        

    }
    
    @Override
    public int getWidth( ) {
        return activeArea.getWidth( );
    }

    @Override
    public int getHeight( ) {
        return activeArea.getHeight( );
    }
    
    public ActiveArea getActiveArea() {
    	return activeArea;
    }
    
    @Override
    public boolean isPointInside( float x, float y ) {
        boolean isInside = false;
        
        int mousex = (int)( x - ( this.x - getWidth( ) / 2 ) );
        int mousey = (int)( y - ( this.y - getHeight( ) ) );
        
        isInside = ( ( mousex >= 0 ) && ( mousex < getWidth() ) && ( mousey >= 0 ) && ( mousey < getHeight() ) ) ;
        
        //System.out.println( "IS ACTIVE AREA INSIDE("+this.activeArea.getId( )+")="+isInside+" "+x+" , "+y );
        //System.out.println( "X="+this.x+" Y="+ this.y+" WIDTH="+this.getWidth( )+" HEIGHT="+this.getHeight( ));
        return isInside;
    }

    /**
     * Triggers the giving action associated with the item
     * @param npc The character receiver of the item
     * @return True if the item was given, false otherwise
     */
    public boolean giveTo( FunctionalNPC npc ) {
        boolean givenTo = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !givenTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GIVE_TO && action.getIdTarget( ).equals( npc.getElement( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                	FunctionalEffects.storeAllEffects(action.getEffects( ));
                    givenTo = true;
                }
            }
        }
        
        return givenTo;
    }

    /**
     * Triggers the grabbing action associated with the item
     * @return True if the item was grabbed, false otherwise
     */
    public boolean grab( ) {
        boolean grabbed = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !grabbed; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GRAB ) {
                if( new FunctionalConditions ( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                	FunctionalEffects.storeAllEffects(action.getEffects( ));
                    grabbed = true;
                } 
            }
        }
        
        return grabbed;
    }

    
}
