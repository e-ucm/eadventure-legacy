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
