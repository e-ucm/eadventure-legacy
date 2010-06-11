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
 * An effect that plays a sound
 */
public class PlayAnimationEffect extends AbstractEffect {

    /**
     * The path to the base animation file
     */
    private String path;

    /**
     * Upper-left coordinate of the animation in the X
     */
    private int x;

    /**
     * Upper-left coordinate of the animation in the Y
     */
    private int y;

    /**
     * Creates a new FunctionalPlayAnimationEffect.
     * 
     * @param path
     *            path to the animation file
     * @param x
     *            X coordinate for the animation to play
     * @param y
     *            Y coordinate for the animation to play
     */
    public PlayAnimationEffect( String path, int x, int y ) {

        super( );
        this.path = path;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType( ) {

        return PLAY_ANIMATION;
    }

    /**
     * Returns the path of the animation.
     * 
     * @return Path of the animation
     */
    public String getPath( ) {

        return path;
    }

    /**
     * Sets the new path for the animation.
     * 
     * @param path
     *            New path for the animation
     */
    public void setPath( String path ) {

        this.path = path;
    }

    /**
     * Returns the destiny x position for the animation.
     * 
     * @return Destiny x coord
     */
    public int getX( ) {

        return x;
    }

    /**
     * Returns the destiny y position for the animation.
     * 
     * @return Destiny y coord
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new destiny position for the animation.
     * 
     * @param x
     *            New destiny X coordinate
     * @param y
     *            New destiny Y coordinate
     */
    public void setDestiny( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        PlayAnimationEffect pae = (PlayAnimationEffect) super.clone( );
        pae.path = ( path != null ? new String( path ) : null );
        pae.x = x;
        pae.y = y;
        return pae;
    }
}
