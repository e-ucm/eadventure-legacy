/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
