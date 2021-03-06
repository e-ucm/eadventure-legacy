/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
            gameLog.effectEvent( getCode(), "t="+( (MoveNPCEffect) effect ).getTargetId( ), "ix="+npc.getX( ), "iy="+npc.getY( ),
                    "dx="+( (MoveNPCEffect) effect ).getX( ), "dy="+( (MoveNPCEffect) effect ).getY( ));
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
