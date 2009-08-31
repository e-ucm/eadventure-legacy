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
package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.HasTargetId;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * This class holds the data for a element reference in eAdventure
 */
public class ElementReference implements Cloneable, Documented, HasTargetId {

    /**
     * Id of the element referenced
     */
    private String idTarget;

    /**
     * X position of the referenced element
     */
    private int x;

    /**
     * Y position of the referenced element
     */
    private int y;

    /**
     * Scale of the referenced element
     */
    private float scale;

    /**
     * The order in which will be drown this element
     */
    private int layer;

    /**
     * Documentation of the element reference.
     */
    private String documentation;

    /**
     * Conditions for the element to be placed
     */
    private Conditions conditions;

    /**
     * The influenceArea of the object, used with trajectories
     */
    private InfluenceArea influenceArea;

    /**
     * Creates a new ElementReference
     * 
     * @param idTarget
     *            the id of the element that is referenced
     * @param x
     *            the horizontal position of the element
     * @param y
     *            the vertical position of the element
     */
    public ElementReference( String idTarget, int x, int y ) {

        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
        this.scale = 1;
        this.layer = -1;
        documentation = null;
        conditions = new Conditions( );
        influenceArea = new InfluenceArea( );
    }

    /**
     * Creates a new ElementReference
     * 
     * @param idTarget
     *            the id of the element that is referenced
     * @param x
     *            the horizontal position of the element
     * @param y
     *            the vertical position of the element
     * @param layer
     *            the position where this element reference will be paint
     */
    public ElementReference( String idTarget, int x, int y, int layer ) {

        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
        this.scale = 1;
        this.layer = layer;
        documentation = null;
        conditions = new Conditions( );
        influenceArea = new InfluenceArea( );
    }

    /**
     * Returns the id of the element that is referenced
     * 
     * @return the id of the element that is referenced
     */
    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Returns the horizontal position of the element
     * 
     * @return the horizontal position of the element
     */
    public int getX( ) {

        return x;
    }

    /**
     * Returns the vertical position of the element
     * 
     * @return the vertical position of the element
     */
    public int getY( ) {

        return y;
    }

    /**
     * Returns the documentation of the element.
     * 
     * @return the documentation of the element
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * Returns the conditions for this element
     * 
     * @return the conditions for this element
     */
    public Conditions getConditions( ) {

        return this.conditions;
    }

    /**
     * Returns the scale for this element
     * 
     * @return the scale for this element
     */
    public float getScale( ) {

        return this.scale;
    }

    /**
     * Set the scale of the element
     * 
     * @param scale
     *            the scale of the element
     */
    public void setScale( float scale ) {

        this.scale = scale;
    }

    /**
     * Sets the new id of the referenced element.
     * 
     * @param idTarget
     *            Id of the new referenced element
     */
    public void setTargetId( String idTarget ) {

        this.idTarget = idTarget;
    }

    /**
     * Sets the new position for the element reference.
     * 
     * @param x
     *            X coordinate of the element reference
     * @param y
     *            Y coordinate of the element reference
     */
    public void setPosition( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    /**
     * Changes the documentation of this element reference.
     * 
     * @param documentation
     *            The new documentation
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    /**
     * Changes the conditions for this element
     * 
     * @param conditions
     *            the new conditions
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Get the layer for this element
     * 
     * @return layer
     */
    public int getLayer( ) {

        return layer;
    }

    /**
     * Changes the layer for this element
     * 
     * @param layer
     *            The new layer
     */
    public void setLayer( int layer ) {

        this.layer = layer;
    }

    /**
     * @return the influenceArea
     */
    public InfluenceArea getInfluenceArea( ) {

        return influenceArea;
    }

    /**
     * @param influenceArea
     *            the influenceArea to set
     */
    public void setInfluenceArea( InfluenceArea influenceArea ) {

        this.influenceArea = influenceArea;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ElementReference er = (ElementReference) super.clone( );
        er.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        er.documentation = ( documentation != null ? new String( documentation ) : null );
        er.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        er.influenceArea = ( influenceArea != null ? (InfluenceArea) influenceArea.clone( ) : null );
        er.layer = layer;
        er.scale = scale;
        er.x = x;
        er.y = y;
        return er;
    }
}
