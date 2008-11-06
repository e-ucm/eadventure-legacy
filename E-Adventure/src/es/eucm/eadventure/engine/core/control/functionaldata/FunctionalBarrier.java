package es.eucm.eadventure.engine.core.control.functionaldata;

import es.eucm.eadventure.engine.core.data.gamedata.elements.Barrier;

public class FunctionalBarrier{
    private Barrier barrier;
    
    public FunctionalBarrier ( Barrier barrier ){
        this.barrier = barrier;
    }
    
    public int[] checkPlayerAgainstBarrier ( FunctionalPlayer player, int destX, int destY){
        int finalDestX = destX;
        int finalDestY = destY;
        
        if (!player.isTransparent( )){
            if (barrier.getConditions( ).allConditionsOk( )){
                float intersectionX= playerIntersectsBarrier (player, barrier, finalDestX, finalDestY);
                // Intersection
                if (intersectionX!=Integer.MIN_VALUE){
                    //System.out.println("INTERSECTION X:"+intersectionX );
                    if (intersectionX<destX)
                        finalDestX = (int)(intersectionX-player.getWidth( )/2.0);
                    else if (intersectionX>destX)
                        finalDestX = (int)(intersectionX+player.getWidth( )/2.0);
                }
            }
        }
     
        int[] destinyPos = new int[]{finalDestX, finalDestY};
        return destinyPos;
    }

    private static final float SEC_GAP = 5;
    
    private float playerIntersectsBarrier (FunctionalPlayer player, Barrier barrier, int targetX, int targetY){
        float returnValue = Integer.MIN_VALUE;
        float secGap = SEC_GAP;
        
        // Player data
        float px = player.getX( );
        float py = player.getY( );
        float w = player.getWidth( );
        float h = player.getHeight( );
        
        //secGap = (float)(w/2.0);
        
        // Barrier data
        float bx1 = barrier.getX( );
        float bx2 = barrier.getX( ) + barrier.getWidth( );
        float byh = barrier.getY( );
        float byl = barrier.getY( ) + barrier.getHeight( );
        
        // Direction vector
        float dx = targetX - px;
        float dy = 0;
        
        // Determine closer side of the barrier
        float bx = Integer.MIN_VALUE;
        if (dx>0){
            bx = Math.min( bx1, bx2 );
        } else if (dx<0){
            bx = Math.max( bx1, bx2 );
        }
        //Up corner:
        float ucx1 = 0;
        float ucx2 = 0;
        if (dx>=0){
            ucx1 = (float)(px + w/2.0);
            ucx2 = (float)(px - w/2.0);
        }else{
            ucx1 = (float)(px - w/2.0);
            ucx2 = (float)(px + w/2.0);
        }
        float ucy = py+h;
        float dcy = py;
        
        // Test up corner:
        boolean intersectsUp = false;
        boolean intersectsDown = false;
        float tx = (bx-ucx1)/dx;
        if (tx>=0 && tx<=1){
            // Check y
            if (ucy>=byh && ucy<=byl){
                intersectsUp = true;
                if (dx>=0)
                    returnValue=(float)(bx-secGap);
                else
                    returnValue=(float)(bx+secGap);
            } else {
                if (dcy>=byh && dcy<=byl){
                    intersectsDown = true;
                    if (dx>=0)
                        returnValue=(float)(bx-secGap);
                    else
                        returnValue=(float)(bx+secGap);                }
            }
        }
        
        if (!intersectsUp && !intersectsDown){
            tx = (bx-ucx2)/dx;
            if (tx>=0 && tx<=1){
                // Check y
                if (ucy>=byh && ucy<=byl){
                    intersectsUp = true;
                    if (dx>=0)
                        returnValue=(float)(bx-secGap);
                    else
                        returnValue=(float)(bx+secGap);                
                } else {
                    if (dcy>=byh && dcy<=byl)
                        intersectsDown = true;
                    if (dx>=0)
                        returnValue=(float)(bx-secGap);
                    else
                        returnValue=(float)(bx+secGap);

                }
            }
        }
        return returnValue;
    }

}
