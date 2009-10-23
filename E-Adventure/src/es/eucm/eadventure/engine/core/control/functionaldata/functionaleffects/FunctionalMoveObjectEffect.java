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

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.MoveObjectEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;

/**
 * An effect that consumes an object in the inventory
 */
public class FunctionalMoveObjectEffect extends FunctionalEffect {

    private FunctionalItem object;
    
    private MoveObjectEffect moveObjectEffect;
    
    private long time;
    
    private int initialX;
    
    private int initialY;
    
    private float scale;
    
    private boolean finished;
        
    /**
     * Creates a new FunctionalConsumeObjectEffect.
     * 
     * @param the
     *            ConsumeObjectEffect
     */
    public FunctionalMoveObjectEffect( MoveObjectEffect effect ) {
        super( effect );
        moveObjectEffect = effect;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {
        List<FunctionalItem> items = Game.getInstance( ).getFunctionalScene( ).getItems( );
        for (FunctionalItem item : items) {
            if (item.getItem( ).getId( ).equals( moveObjectEffect.getTargetId( )))
                object = item;
        }
        if (object != null) {
            if (moveObjectEffect.isAnimated( )) {
                time = System.currentTimeMillis( );
                initialX = (int) object.getX( );
                initialY = (int) object.getY( );
                scale = object.getScale( );
                finished = false;
            } else {
                object.setX( moveObjectEffect.getX( ) );
                object.setY( moveObjectEffect.getY( ) );
                object.setScale( moveObjectEffect.getScale( ) );
                finished = true;
            }
        } else
            finished = true;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {
        return !moveObjectEffect.isAnimated( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {
        return !finished;
    }
    
    public void update( long elapsedTime ) {
        float temp = (float) (System.currentTimeMillis( ) - time) * moveObjectEffect.getTranslateSpeed( ) / 60000;
        int newX = initialX + (int) ( (moveObjectEffect.getX( ) - initialX) * temp );
        int newY = initialY + (int) ( (moveObjectEffect.getY( ) - initialY) * temp );
        temp = (float) (System.currentTimeMillis( ) - time) * moveObjectEffect.getScaleSpeed( ) / 60000;
        float newScale = scale + ((moveObjectEffect.getScale( ) - scale) * temp);
        if ((initialX < moveObjectEffect.getX( ) && newX > moveObjectEffect.getX( )) ||
                (initialX > moveObjectEffect.getX( ) && newX < moveObjectEffect.getX()) ||
                (initialY < moveObjectEffect.getY( ) && newY > moveObjectEffect.getY( )) ||
                (initialY > moveObjectEffect.getY( ) && newY < moveObjectEffect.getY()) ||
                 (initialX==moveObjectEffect.getX( ) && initialY == moveObjectEffect.getY( ) )) {
            newX = moveObjectEffect.getX( );
            newY = moveObjectEffect.getY( );
            newScale = moveObjectEffect.getScale( );
            finished = true;
        }
        object.setX( newX );
        object.setY( newY );
        object.setScale( newScale );
    }

}
