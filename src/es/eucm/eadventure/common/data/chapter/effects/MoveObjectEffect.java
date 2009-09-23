/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fern‡ndez-Manj—n, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;


public class MoveObjectEffect extends AbstractEffect implements HasTargetId {
    
    private String idTarget;
    
    private int x;
    
    private int y;
    
    private float scale;
    
    private int translateSpeed;
    
    private int scaleSpeed;
    
    private boolean animated;
        
    public MoveObjectEffect(String idTarget, int x, int y, float scale, boolean animated, int translateSpeed, int scaleSpeed) {
        super();
        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.translateSpeed = translateSpeed;
        this.scaleSpeed = scaleSpeed;
        this.animated = animated;
    }
    
    @Override
    public int getType( ) {
        return MOVE_OBJECT;
    }
    
    public String getTargetId( ) {
        return idTarget;
    }

    public void setTargetId( String id ) {
        idTarget = id;
    }
        
    public int getX( ) {
        return x;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public int getY( ) {
        return y;
    }

    public void setY( int y ) {
        this.y = y;
    }

    public float getScale( ) {
        return scale;
    }

    public void setScale( float scale ) {
        this.scale = scale;
    }

    public int getTranslateSpeed( ) {
        return translateSpeed;
    }
    
    public void setTranslateSpeed( int translateSpeed ) {
        this.translateSpeed = translateSpeed;
    }

    public int getScaleSpeed( ) {
        return scaleSpeed;
    }
    
    public void setScaleSpeed( int scaleSpeed ) {
        this.scaleSpeed = scaleSpeed;
    }

    public boolean isAnimated( ) {
        return animated;
    }

    public void setAnimated( boolean animated ) {    
        this.animated = animated;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {
        MoveObjectEffect coe = (MoveObjectEffect) super.clone( );
        coe.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        coe.scale = scale;
        coe.x = x;
        coe.y = y;
        coe.animated = animated;
        coe.translateSpeed = translateSpeed;
        coe.scaleSpeed = scaleSpeed;
        return coe;
    }

}
