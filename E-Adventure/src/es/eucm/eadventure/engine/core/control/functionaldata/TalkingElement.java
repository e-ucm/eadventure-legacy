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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;

/**
 * Interface for all talking characters
 */
public interface TalkingElement {

    /**
     * Makes the character start speaking
     * 
     * @param text
     *            the text to be spoken
     * @param keepShowing
     *          keep showing the current line until user skip it
     */
    public void speak( String text, boolean keepShowing);

    public void speakWithFreeTTS( String text, String voice, boolean keepShowing );

    public void speak( String text, String audioPath,boolean keepShowing );

    /**
     * Check if all player conversation lines must be read by synthesizer
     * 
     * @return true, if all player conversation lines must be read by
     *         synthesizer
     */
    public boolean isAlwaysSynthesizer( );

    /**
     * Takes the player voice for synthesizer
     * 
     * @return A string representing associates voice
     */
    public String getPlayerVoice( );

    /**
     * Makes the character stops speaking
     */
    public void stopTalking( );

    /**
     * Returns whether the character is talking
     * 
     * @return true if the character is talking, false otherwise
     */
    public boolean isTalking( );

    public boolean getShowsSpeechBubbles( );

    public Color getBubbleBkgColor( );

    public Color getBubbleBorderColor( );
    
    public void setDirection( int direction );
    
    
}
