/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.core.control.functionaldata;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;

public class FunctionalBarrier {

    private Barrier barrier;

    public FunctionalBarrier( Barrier barrier ) {

        this.barrier = barrier;
    }

    public int[] checkPlayerAgainstBarrier( FunctionalPlayer player, int destX, int destY ) {

        int finalDestX = destX;
        int finalDestY = destY;

        if( !player.isTransparent( ) ) {
            if( new FunctionalConditions( barrier.getConditions( ) ).allConditionsOk( ) ) {
                int intersectionX = playerIntersectsBarrier( player, barrier, finalDestX, finalDestY );
                if( intersectionX != Integer.MIN_VALUE ) {
                    finalDestX = intersectionX;
                }
            }
        }

        int[] destinyPos = new int[] { finalDestX, finalDestY };
        return destinyPos;
    }

    public boolean isInside( float x, float y ) {

        if( new FunctionalConditions( barrier.getConditions( ) ).allConditionsOk( ) ) {
            float bx1 = barrier.getX( );
            float bx2 = barrier.getX( ) + barrier.getWidth( );
            float by1 = barrier.getY( );
            float by2 = barrier.getY( ) + barrier.getHeight( );

            if( bx1 > bx2 ) {
                float temp = bx1;
                bx1 = bx2;
                bx2 = temp;
            }
            if( by1 > by2 ) {
                float temp = by1;
                by1 = by2;
                by2 = temp;
            }

            if( x >= bx1 && x <= bx2 && y >= by1 && y <= by2 ) {
                return true;
            }
        }
        return false;
    }

    private int playerIntersectsBarrier( FunctionalPlayer player, Barrier barrier, int targetX, int targetY ) {
        // Player data
        float px = player.getX( );
        int w = player.getWidth( );

        // Barrier data
        int bx1 = barrier.getX( );
        int bx2 = barrier.getX( ) + barrier.getWidth( );

        // Direction vector
        float dx = targetX - px;

        int bx = Integer.MIN_VALUE;
        if( dx > 0 ) { // the player is to the right of the barrier
            bx = Math.min( bx1, bx2 );
            if (px <= bx && targetX >= bx - w / 2) { // the player has to cross the barrier
                return bx - w / 2;
            }
        } else if( dx < 0 ) { // the player is to the left of the barrier
            bx = Math.max( bx1, bx2 );
            if (px >= bx && targetX <= bx + w / 2) { // the player has to cross the barrier
                return bx + w /2;
            }
        }
        // the player does not cross the barrier.
        return Integer.MIN_VALUE;
    }

}
