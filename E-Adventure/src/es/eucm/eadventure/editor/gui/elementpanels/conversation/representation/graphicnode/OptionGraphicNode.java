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
package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;

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
            g.drawString( TextConstants.getText( "Effects.Title" ), (int) textPos.getX( ), (int) textPos.getY( ) );
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
