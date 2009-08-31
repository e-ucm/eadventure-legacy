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
package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to walk to a given position.
 */
public class FunctionalMoveNPCEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalMoveNPCEffect.
     * 
     * @param idTarget
     *            the id of the character who will walk
     * @param x
     *            X final position for the NPC
     * @param y
     *            Y final position for the NPC
     */
    public FunctionalMoveNPCEffect( MoveNPCEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ( (MoveNPCEffect) effect ).getTargetId( ) );
        if( npc != null ) {
            npc.setDestiny( ( (MoveNPCEffect) effect ).getX( ), ( (MoveNPCEffect) effect ).getY( ) );
            npc.setState( FunctionalNPC.WALK );
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {

        boolean stillRunning = false;

        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ( (MoveNPCEffect) effect ).getTargetId( ) );
        if( npc != null ) {
            stillRunning = npc.isWalking( );
            // stillRunning = !(((MoveNPCEffect)effect).getX()==npc.getX()&&((MoveNPCEffect)effect).getY()==npc.getY());

        }
        return stillRunning;
    }

}
