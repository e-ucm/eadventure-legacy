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

import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to speak a line of text.
 */
public class FunctionalSpeakCharEffect extends FunctionalEffect {

    /**
     * Creates a new SpeakCharEffect.
     * 
     * @param idTarget
     *            the id of the character who will speak
     * @param line
     *            the text to be spoken
     */
    public FunctionalSpeakCharEffect( SpeakCharEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ( (SpeakCharEffect) effect ).getTargetId( ) );
        if( npc != null ) {
            String line = ((SpeakCharEffect) effect ).getLine( );
            gameLog.effectEvent( getCode(), "t="+( (SpeakCharEffect) effect ).getTargetId( ),
                    "l="+(line.length( )>5?line.substring( 0,5 ):line));
            String audioPath = ((SpeakCharEffect)effect).getAudioPath( );
            if( npc.isAlwaysSynthesizer( ) )
                npc.speakWithFreeTTS( ( (SpeakCharEffect) effect ).getLine( ), npc.getPlayerVoice( ), Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) );
            else if (audioPath!=null && !audioPath.equals( "" )) 
                npc.speak(( (SpeakCharEffect) effect ).getLine( ), audioPath, Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) );
            else    
                npc.speak( ( (SpeakCharEffect) effect ).getLine( ), Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) );
            Game.getInstance( ).setCharacterCurrentlyTalking( npc );
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
    
    @Override
    public boolean canSkip( ) {
        return true;
    }

    @Override
    public void skip( ) {
        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).getCharacterCurrentlyTalking( ).stopTalking( );
        Game.getInstance( ).setCharacterCurrentlyTalking( null );
    }


}
