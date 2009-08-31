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
