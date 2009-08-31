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

import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * An effect that makes the player to speak a line of text.
 */
public class FunctionalSpeakPlayerEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalSpeakPlayerEffect.
     * 
     * @param line
     *            the text to be spoken
     */
    public FunctionalSpeakPlayerEffect( SpeakPlayerEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        FunctionalConditions cond = new FunctionalConditions( effect.getConditions( ) );
        if( cond.allConditionsOk( ) ) {
            FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
            if( player.isAlwaysSynthesizer( ) )
                player.speakWithFreeTTS( ( (SpeakPlayerEffect) effect ).getLine( ), player.getPlayerVoice( ) );
            else
                player.speak( ( (SpeakPlayerEffect) effect ).getLine( ) );
            Game.getInstance( ).setCharacterCurrentlyTalking( player );
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

        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && !Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).setCharacterCurrentlyTalking( null );

        return Game.getInstance( ).getCharacterCurrentlyTalking( ) != null;
    }

}
