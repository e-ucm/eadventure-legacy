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
package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.Image;

import es.eucm.eadventure.editor.control.controllers.DataControl;

/**
 * Abstract class for all the ImageElements that can be shown and edited in the
 * ScenePreviewEditionPanel.
 * 
 * @author Eugenio Marchiori
 */
public abstract class ImageElement implements Comparable<Object> {

    /**
     * The asociated image that is shown in the panel
     */
    protected Image image;

    /**
     * Returns the scale of the reference or component
     * 
     * @return the scale
     */
    public abstract float getScale( );

    /**
     * Returns the DataControl asociated with the ImageElement in case it exits
     * 
     * @return the DataControl
     */
    public abstract DataControl getDataControl( );

    public abstract DataControl getReferencedDataControl( );

    /**
     * Get the image of the element
     * 
     * @return the image of the element
     */
    public Image getImage( ) {

        return image;
    }

    /**
     * Get the position along the x-axis
     * 
     * @return the position along the x-axis
     */
    public abstract int getX( );

    /**
     * Get the position along the y-axis
     * 
     * @return the position along the y-axis
     */
    public abstract int getY( );

    /**
     * Recreate the image, in case a change was made to the controller
     */
    public abstract void recreateImage( );

    /**
     * Get the layer of the reference
     * 
     * @return the layer
     */
    public abstract int getLayer( );

    /**
     * Change the position of the element
     * 
     * @param x
     *            The new x-axis value
     * @param y
     *            The new y-axis value
     */
    public abstract void changePosition( int x, int y );

    /**
     * Change the scale of the element
     * 
     * @param scale
     *            the new scale of the element
     */
    public abstract void setScale( float scale );

    public abstract void changeSize( int width, int height );

    public abstract boolean canResize( );

    public abstract boolean canRescale( );

    public abstract int getWidth( );

    public abstract int getHeight( );

    public int compareTo( Object arg0 ) {

        if( arg0 == null || !( arg0 instanceof ImageElement ) ) {
            return 1;
        }
        ImageElement temp = (ImageElement) arg0;

        int tempLayer = temp.getLayer( );
        int thisLayer = this.getLayer( );

        return thisLayer - tempLayer;
    }

    public abstract boolean transparentPoint( int x, int y );

    public boolean isVisible( ) {

        return true;
    }

}
