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

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that triggers a scene
 */
public class TriggerSceneEffect extends AbstractEffect implements HasTargetId {

    /**
     * Id of the cutscene to be played
     */
    private String targetSceneId;

    /**
     * X position of the player in the scene.
     */
    private int x;

    /**
     * Y position of the player in the scene.
     */
    private int y;

    /**
     * Creates a new TriggerSceneEffect
     * 
     * @param targetSceneId
     *            the id of the cutscene to be triggered
     * @param x
     *            X position of the player in the new scene
     * @param y
     *            Y position of the player in the new scene
     */
    public TriggerSceneEffect( String targetSceneId, int x, int y ) {

        super( );
        this.targetSceneId = targetSceneId;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType( ) {

        return TRIGGER_SCENE;
    }

    /**
     * Returns the targetSceneId
     * 
     * @return String containing the targetSceneId
     */
    public String getTargetId( ) {

        return targetSceneId;
    }

    /**
     * Sets the new targetSceneId
     * 
     * @param targetSceneId
     *            New targetSceneId
     */
    public void setTargetId( String targetSceneId ) {

        this.targetSceneId = targetSceneId;
    }

    /**
     * Returns the X destiny position of the player.
     * 
     * @return X destiny position
     */
    public int getX( ) {

        return x;
    }

    /**
     * Returns the Y destiny position of the player.
     * 
     * @return Y destiny position
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new insertion position for the player
     * 
     * @param x
     *            X coord of the position
     * @param y
     *            Y coord of the position
     */
    public void setPosition( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        TriggerSceneEffect tse = (TriggerSceneEffect) super.clone( );
        tse.targetSceneId = ( targetSceneId != null ? new String( targetSceneId ) : null );
        tse.x = x;
        tse.y = y;
        return tse;
    }

}
