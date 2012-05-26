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
package es.eucm.eadventure.engine.core.control.animations.npc;

import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * A state for a non player character (npc)
 */
public abstract class NPCState extends AnimationState {

    /**
     * The npc that owns this state
     */
    protected FunctionalNPC npc;

    /**
     * Creates a new NPCState
     * 
     * @param npc
     *            the reference to the npc
     */
    public NPCState( FunctionalNPC npc ) {

        this.npc = npc;
        loadResources( );
    }

    @Override
    protected float getVelocityX( ) {

        return npc.getSpeedX( );
    }

    @Override
    protected float getVelocityY( ) {

        return npc.getSpeedY( );
    }

    @Override
    protected int getCurrentDirection( ) {

        return npc.getDirection( );
    }

    @Override
    protected void setCurrentDirection( int direction ) {

        npc.setDirection( direction );
    }
}
