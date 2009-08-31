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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * The action to look at an element
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalLook extends FunctionalAction {

    /**
     * The element to look at
     */
    private FunctionalElement element;

    /**
     * Default constructor, with the element to look at
     * 
     * @param element
     *            The element to look at
     */
    public FunctionalLook( FunctionalElement element ) {

        super( null );
        type = ActionManager.ACTION_LOOK;
        this.element = element;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;
        if( element.isInInventory( ) ) {
            functionalPlayer.setDirection( AnimationState.SOUTH );
        }
        else {
            if( element.getX( ) < functionalPlayer.getX( ) )
                functionalPlayer.setDirection( AnimationState.WEST );
            else
                functionalPlayer.setDirection( AnimationState.EAST );
        }
        finished = true;
        if( functionalPlayer.isAlwaysSynthesizer( ) )
            functionalPlayer.speakWithFreeTTS( element.getElement( ).getDescription( ), functionalPlayer.getPlayerVoice( ) );
        else
            functionalPlayer.speak( element.getElement( ).getDescription( ) );

        DebugLog.player( "Look: " + element.getElement( ).getId( ) + " desc: " + element.getElement( ).getDescription( ) );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

    }

}
