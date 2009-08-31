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
 * An effect that plays a sound
 */
public class PlaySoundEffect extends AbstractEffect {

    /**
     * Whether the sound must be played in background
     */
    private boolean background;

    /**
     * The path to the sound file
     */
    private String path;

    /**
     * Creates a new PlaySoundEffect
     * 
     * @param background
     *            whether to play the sound in background
     * @param path
     *            path to the sound file
     */
    public PlaySoundEffect( boolean background, String path ) {

        super( );
        this.background = background;
        this.path = path;
    }

    @Override
    public int getType( ) {

        return PLAY_SOUND;
    }

    /**
     * Returns whether the sound must be played in background
     * 
     * @return True if the sound must be played in background, false otherwise
     */
    public boolean isBackground( ) {

        return background;
    }

    /**
     * Sets the value which tells if the sound must be played in background
     * 
     * @param background
     *            New value for background
     */
    public void setBackground( boolean background ) {

        this.background = background;
    }

    /**
     * Returns the path of the sound
     * 
     * @return Path of the sound
     */
    public String getPath( ) {

        return path;
    }

    /**
     * Sets the new path for the sound to be played
     * 
     * @param path
     *            New path of the sound
     */
    public void setPath( String path ) {

        this.path = path;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        PlaySoundEffect pse = (PlaySoundEffect) super.clone( );
        pse.background = background;
        pse.path = ( path != null ? new String( path ) : null );
        return pse;
    }
}
