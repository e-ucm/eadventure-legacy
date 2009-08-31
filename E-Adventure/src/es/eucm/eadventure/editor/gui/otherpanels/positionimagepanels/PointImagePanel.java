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

import java.awt.Color;
import java.awt.Graphics;

public class PointImagePanel extends PositionImagePanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public PointImagePanel( ) {

        this( null );

        selectedX = 0;
        selectedY = 0;
    }

    /**
     * Constructor.
     * 
     * @param imagePath
     *            Image to show
     */
    public PointImagePanel( String imagePath ) {

        super( imagePath );

        selectedX = 0;
        selectedY = 0;
    }

    @Override
    public void paint( Graphics g ) {

        super.paint( g );

        // If the image is loaded, draw the point
        if( isImageLoaded( ) ) {
            int realX = getAbsoluteX( selectedX );
            int realY = getAbsoluteY( selectedY );

            g.setColor( Color.RED );
            g.fillOval( realX - 3, realY - 3, 8, 8 );
            g.setColor( Color.BLACK );
            g.drawOval( realX - 3, realY - 3, 7, 7 );
        }
    }
}
