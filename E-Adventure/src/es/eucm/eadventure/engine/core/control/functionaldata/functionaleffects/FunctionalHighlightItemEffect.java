/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.HighlightItemEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights.FunctionalHighlightBlue;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights.FunctionalHighlightBorder;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights.FunctionalHighlightGreen;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights.FunctionalHighlightRed;

/**
 * An effect that highlights an item
 */
public class FunctionalHighlightItemEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalHighlightItemEffect.
     * 
     * @param the
     *            HighlightItemEffect
     */
    public FunctionalHighlightItemEffect( HighlightItemEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {
        HighlightItemEffect temp = (HighlightItemEffect) effect;
        for (FunctionalItem item : Game.getInstance( ).getFunctionalScene( ).getItems( )) {
            if (item.getItem( ).getId( ).equals( temp.getTargetId( ) )) {
                if (temp.getHighlightType( ) == HighlightItemEffect.NO_HIGHLIGHT)
                    item.setHighlight( null );
                else if (temp.getHighlightType( ) == HighlightItemEffect.HIGHLIGHT_BLUE)
                    item.setHighlight( new FunctionalHighlightBlue(temp.isHighlightAnimated( )) );
                else if (temp.getHighlightType( ) == HighlightItemEffect.HIGHLIGHT_RED)
                    item.setHighlight( new FunctionalHighlightRed(temp.isHighlightAnimated( )) );
                else if (temp.getHighlightType( ) == HighlightItemEffect.HIGHLIGHT_GREEN)
                    item.setHighlight( new FunctionalHighlightGreen(temp.isHighlightAnimated( )) );
                else if (temp.getHighlightType( ) == HighlightItemEffect.HIGHLIGHT_BORDER)
                    item.setHighlight( new FunctionalHighlightBorder(temp.isHighlightAnimated( )) );
                    
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {

        return true;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {

        return false;
    }
}
