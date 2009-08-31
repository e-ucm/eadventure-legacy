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

}
