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
import java.awt.Image;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class ElementImagePanel extends PositionImagePanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Element to be showed in the given position.
     */
    private Image element;

    private Trajectory trajectory;

    /**
     * Constructor.
     * 
     * @param imagePath
     *            Image to show
     * @param elementPath
     *            Element to be drawn on top of the image
     */
    public ElementImagePanel( String imagePath, String elementPath ) {

        super( imagePath );

        this.trajectory = null;
        // Load the element image
        if( elementPath != null )
            element = AssetsController.getImage( elementPath );
        else
            element = null;
    }

    /**
     * Constructor.
     * 
     * @param imagePath
     *            Image to show
     * @param elementPath
     *            Element to be drawn on top of the image
     */
    public ElementImagePanel( String imagePath, String elementPath, Trajectory trajectory ) {

        this( imagePath, elementPath );
        this.trajectory = trajectory;
    }

    /**
     * Loads a new element with the given path.
     * 
     * @param elementPath
     *            Path of the new element
     */
    public void loadElement( String elementPath ) {

        // Load the element image
        if( elementPath != null )
            element = AssetsController.getImage( elementPath );
        else
            element = null;
    }
    
    public void removeElementImage() {
        element = null;
    }

    @Override
    public void paint( Graphics g ) {

        super.paint( g );

        // If the background is loaded, paint an image
        if( isImageLoaded( ) ) {

            if( trajectory != null ) {
                for( Side side : trajectory.getSides( ) ) {
                    Node node = trajectory.getNodeForId( side.getIDStart( ) );
                    int x1 = getAbsoluteX( node.getX( ) );
                    int y1 = getAbsoluteY( node.getY( ) );
                    node = trajectory.getNodeForId( side.getIDEnd( ) );
                    int x2 = getAbsoluteX( node.getX( ) );
                    int y2 = getAbsoluteY( node.getY( ) );
                    g.drawLine( x1, y1, x2, y2 );
                }
                for( Node node : trajectory.getNodes( ) ) {
                    int x = getAbsoluteX( node.getX( ) );
                    int y = getAbsoluteY( node.getY( ) );
                    g.fillOval( x - 5, y - 5, 10, 10 );
                }
            }

            // If the element is avalaible, paint it
            if( element != null )
                paintRelativeImage( g, element, selectedX, selectedY, scale, true );

            // If it is not avalaible, draw a circle
            else {
                int realX = getAbsoluteX( selectedX );
                int realY = getAbsoluteY( selectedY );

                g.setColor( Color.RED );
                g.fillOval( realX - 3, realY - 3, 8, 8 );
                g.setColor( Color.BLACK );
                g.drawOval( realX - 3, realY - 3, 7, 7 );
            }

        }
    }
}
