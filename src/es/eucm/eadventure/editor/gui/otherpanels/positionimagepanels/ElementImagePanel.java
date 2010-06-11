/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
