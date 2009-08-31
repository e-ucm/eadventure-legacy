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
 * An effect that makes the player to move to the given position.
 */
public class MovePlayerEffect extends AbstractEffect {

    /**
     * The destination of the player
     */
    private int x;

    private int y;

    /**
     * Creates a new MovePlayerEffect.
     * 
     * @param x
     *            X final position for the player
     * @param y
     *            Y final position for the player
     */
    public MovePlayerEffect( int x, int y ) {

        super( );
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType( ) {

        return MOVE_PLAYER;
    }

    /**
     * Returns the destiny x position.
     * 
     * @return Destiny x coord
     */
    public int getX( ) {

        return x;
    }

    /**
     * Returns the destiny y position.
     * 
     * @return Destiny y coord
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new destiny position
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

        MovePlayerEffect mpe = (MovePlayerEffect) super.clone( );
        mpe.x = x;
        mpe.y = y;
        return mpe;
    }
}
