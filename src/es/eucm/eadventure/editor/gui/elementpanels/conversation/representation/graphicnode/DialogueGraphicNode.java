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
 * Graphic representation of a standard dialogue node
 */
public class DialogueGraphicNode extends GraphicNode {

    /**
     * Constructor
     * 
     * @param node
     *            Link to the conversational node
     * @param position
     *            Position of the node
     */
    public DialogueGraphicNode( ConversationNodeView node, Point position ) {

        super( node, position );
    }

    @Override
    public void drawNode( float scale, Graphics g ) {

        // Paint the circle
        //super.drawNode( scale, g );

        Point position = getPosition( scale );

        int side = (int) ( NODE_DIAMETER * scale );
        Polygon polygon = getPolygon( side, node.isTerminal( ) );
        polygon.translate( position.x - side / 2, position.y - side / 2 );

        g.setColor( Color.GREEN );
        g.fillPolygon( polygon );

        Point textPos = getTextPosition( position, scale, side, node.isTerminal( ) );
        g.setFont( new Font( "Monospaced", Font.PLAIN, (int) ( 15 * scale ) ) );
        if( node.hasEffects( ) ) {
            g.setColor( Color.BLACK );
            g.drawString( TC.get( "Effects.Title" ), (int) textPos.getX( ), (int) textPos.getY( ) );
            textPos.setLocation( textPos.getX( ), textPos.getY( ) - ( 15 * scale ) );
        }
        /*
        if (node.getLineCount() > 0) {
        	g.setColor( Color.BLUE );
        	String text = node.getLineText(0);
        	if (text == null)
        		text = "";
        	if (text.length() > 10)
        		text = text.substring(0, 10);
        	text += "...";
        	g.drawString( text, (int) textPos.getX( ), (int) textPos.getY( ));
        }
        */

        g.setColor( Color.GRAY );
        g.drawPolygon( polygon );

        if( selected ) {
            g.setColor( Color.RED );
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke( new BasicStroke( (int) ( 4 * scale ) ) );
            g2.drawPolygon( polygon );
            g2.setStroke( new BasicStroke( 1 ) );
        }
        if( selectedChild ) {
            g.setColor( Color.BLUE );
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke( new BasicStroke( (int) ( 4 * scale ) ) );
            g2.drawPolygon( polygon );
            g2.setStroke( new BasicStroke( 1 ) );
        }

    }

    @Override
    public boolean isInside( float scale, Point point ) {

        Point position = getPosition( scale );
        int side = (int) ( NODE_DIAMETER * scale );
        Polygon polygon = getPolygon( side, node.isTerminal( ) );
        polygon.translate( position.x - side / 2, position.y - side / 2 );
        return polygon.contains( point );
    }

    private Polygon getPolygon( int side, boolean terminal ) {

        Polygon polygon = new Polygon( );
        if( !terminal ) {
            int heigth = side - side / 6;
            polygon.addPoint( 0, 0 );
            polygon.addPoint( 0, heigth );
            polygon.addPoint( side / 8, heigth - side / 8 );
            polygon.addPoint( side / 4, heigth - side / 6 );
            polygon.addPoint( 3 * side / 8, heigth - side / 8 );
            polygon.addPoint( side / 2, heigth );
            polygon.addPoint( side - 3 * side / 8, heigth + side / 8 );
            polygon.addPoint( side - side / 4, heigth + side / 6 );
            polygon.addPoint( side - side / 8, heigth + side / 8 );
            polygon.addPoint( side, heigth );
            polygon.addPoint( side, 0 );
        }
        else {
            polygon.addPoint( side / 2, 0 );
            polygon.addPoint( 0, side / 2 );
            polygon.addPoint( 0, side );
            polygon.addPoint( side, side );
            polygon.addPoint( side, side / 2 );
        }
        return polygon;
    }

    private Point getTextPosition( Point position, float scale, int side, boolean terminal ) {

        Point point = new Point( );
        if( !terminal ) {
            point.setLocation( position.getX( ) + ( 1 * scale ) - side / 2, position.getY( ) + side / 2 - side / 3 - ( 1 * scale ) );
        }
        else {
            point.setLocation( position.getX( ) + ( 1 * scale ) - side / 2, position.getY( ) + side / 2 - ( 1 * scale ) );
        }
        return point;
    }
}
