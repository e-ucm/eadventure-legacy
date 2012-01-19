/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels;

import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public abstract class PositionImagePanel extends ImagePanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * X coord of the center of the element.
     */
    protected int selectedX;

    /**
     * Y coord of the bottom of the element.
     */
    protected int selectedY;
    
    protected float scale;

    /**
     * Constructor.
     * 
     * @param imagePath
     *            Image to show
     */
    public PositionImagePanel( String imagePath ) {

        super( imagePath );

        selectedX = 0;
        selectedY = 0;
        scale = 1.0f;
    }

    /**
     * Sets the new point in the position panel.
     * 
     * @param x
     *            X coordinate of the new position
     * @param y
     *            Y coordinate of the new position
     */
    public void setSelectedPoint( int x, int y ) {

        selectedX = x;
        selectedY = y;
    }

    /**
     * Returns the selected X coordinate.
     * 
     * @return Selected X coordinate
     */
    public int getSelectedtX( ) {

        return selectedX;
    }

    /**
     * Returns the selected Y coordinate.
     * 
     * @return Selected Y coordinate
     */
    public int getSelectedtY( ) {

        return selectedY;
    }

    public void setScale( float scale ) {
        this.scale = scale;
    }

}
