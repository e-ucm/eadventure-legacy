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
package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that makes the player to speak a line of text.
 */
public class SpeakPlayerEffect extends AbstractEffect {

    /**
     * Text for the player to speak
     */
    private String line;

    /**
     * Creates a new SpeakPlayerEffect.
     * 
     * @param line
     *            the text to be spoken
     */
    public SpeakPlayerEffect( String line ) {

        super( );
        this.line = line;
    }

    @Override
    public int getType( ) {

        return SPEAK_PLAYER;
    }

    /**
     * Returns the line that the player will speak
     * 
     * @return The line of the player
     */
    public String getLine( ) {

        return line;
    }

    /**
     * Sets the line that the player will speak
     * 
     * @param line
     *            New line
     */
    public void setLine( String line ) {

        this.line = line;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        SpeakPlayerEffect spe = (SpeakPlayerEffect) super.clone( );
        spe.line = ( line != null ? new String( line ) : null );
        return spe;
    }
}
