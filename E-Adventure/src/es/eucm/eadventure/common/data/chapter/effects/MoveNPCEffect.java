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

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that makes a character to walk to a given position.
 */
public class MoveNPCEffect extends AbstractEffect implements HasTargetId {

    /**
     * Id of the npc who will walk
     */
    private String idTarget;

    /**
     * The destination of the npc
     */
    private int x;

    private int y;

    /**
     * Creates a new FunctionalMoveNPCEffect.
     * 
     * @param idTarget
     *            the id of the character who will walk
     * @param x
     *            X final position for the NPC
     * @param y
     *            Y final position for the NPC
     */
    public MoveNPCEffect( String idTarget, int x, int y ) {

        super( );
        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType( ) {

        return MOVE_NPC;
    }

    /**
     * Returns the id target.
     * 
     * @return Id target
     */
    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Sets the new id target.
     * 
     * @param idTarget
     *            New id target
     */
    public void setTargetId( String idTarget ) {

        this.idTarget = idTarget;
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

        MoveNPCEffect npe = (MoveNPCEffect) super.clone( );
        npe.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        npe.x = x;
        npe.y = y;
        return npe;
    }
}
