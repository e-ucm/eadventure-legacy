/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
