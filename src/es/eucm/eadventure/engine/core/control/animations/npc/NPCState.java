/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
     * @param npc the reference to the npc 
     */
    public NPCState( FunctionalNPC npc ) {
        this.npc = npc;
        loadResources( );
    }
    
    protected float getVelocityX() {
        return npc.getSpeedX();
    }
    
    protected float getVelocityY() {
        return npc.getSpeedY();
    }
    
    protected int getCurrentDirection() {
        return npc.getDirection();
    }
    
    protected void setCurrentDirection( int direction ) {
    	npc.setDirection( direction );
    }
}
