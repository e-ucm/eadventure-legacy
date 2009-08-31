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
package es.eucm.eadventure.common.data.chapter.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of effects that can be triggered by an unique player's action during
 * the game.
 */
public class Effects implements Cloneable {

    /**
     * List of effects to be triggered
     */
    private List<AbstractEffect> effects;

    /**
     * Creates a new list of Effects.
     */
    public Effects( ) {

        effects = new ArrayList<AbstractEffect>( );
    }

    /**
     * Returns whether the effects block is empty or not.
     * 
     * @return True if the block has no effects, false otherwise
     */
    public boolean isEmpty( ) {

        return effects.isEmpty( );
    }

    /**
     * Clear the list of effects.
     */
    public void clear( ) {

        effects.clear( );
    }

    /**
     * Adds a new effect to the list.
     * 
     * @param effect
     *            the effect to be added
     */
    public void add( AbstractEffect effect ) {

        effects.add( effect );
    }

    /**
     * Returns the contained list of effects
     * 
     * @return List of effects
     */
    public List<AbstractEffect> getEffects( ) {

        return effects;
    }

    /**
     * Checks if there is any cancel action effect in the list
     */
    public boolean hasCancelAction( ) {

        boolean hasCancelAction = false;
        for( Effect effect : effects ) {
            if( effect.getType( ) == Effect.CANCEL_ACTION ) {
                hasCancelAction = true;
                break;
            }
        }
        return hasCancelAction;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Effects e = (Effects) super.clone( );
        if( effects != null ) {
            e.effects = new ArrayList<AbstractEffect>( );
            for( Effect ef : effects )
                e.effects.add( (AbstractEffect) ef.clone( ) );
        }
        return e;
    }

}
