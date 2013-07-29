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
package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;

/**
 * Graphic representation of a standard option node
 */
public class OptionGraphicNode extends GraphicNode {

    /**
     * Constructor
     * 
     * @param node
     *            Link to the conversational node
     * @param position
     *            Position of the node
     */
    public OptionGraphicNode( ConversationNodeView node, Point position ) {

        super( node, position );
    }

    @Override
    public void drawNode( float scale, Graphics g ) {

        // Paint the circle

        Point position = getPosition( scale );

        int side = (int) ( NODE_DIAMETER * scale );
        Polygon polygon = getPolygon( side );
        polygon.translate( position.x - side / 2, position.y - side / 2 );

        g.setColor( Color.GREEN );
        g.fillPolygon( polygon );
        g.setColor( Color.GRAY );
        g.drawPolygon( polygon );

        Point textPos = getTextPosition( position, scale, side );
        g.setFont( new Font( "Monospaced", Font.PLAIN, (int) ( 15 * scale ) ) );
        if( node.hasEffects( ) ) {
            g.setColor( Color.BLACK );
            g.drawString( TC.get( "Effects.Title" ), (int) textPos.getX( ), (int) textPos.getY( ) );
            textPos.setLocation( textPos.getX( ), textPos.getY( ) - ( 15 * scale ) );
        }

        if( selected ) {
            g.setColor( Color.RED );
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke( new BasicStroke( (int) ( 4 * scale ) ) );
            g2.drawPolygon( polygon );
            g2.setStroke( new BasicStroke( 1 ) );
        }
    }

    private Point getTextPosition( Point position, float scale, int side ) {

        Point point = new Point( );
        point.setLocation( position.getX( ), position.getY( ) + side / 2 + 5 * scale );
        return point;
    }

    @Override
    public boolean isInside( float scale, Point point ) {

        Point position = getPosition( scale );
        int side = (int) ( NODE_DIAMETER * scale );
        Polygon polygon = getPolygon( side );
        polygon.translate( position.x - side / 2, position.y - side / 2 );
        return polygon.contains( point );
    }

    private Polygon getPolygon( int side ) {

        Polygon polygon = new Polygon( );
        polygon.addPoint( -side / 4, side / 2 );
        polygon.addPoint( side / 2, side );
        polygon.addPoint( side + side / 4, side / 2 );
        polygon.addPoint( side / 2, 0 );
        return polygon;
    }

}
