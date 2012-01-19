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

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;

/**
 * Graphic representation of a initial dialogue node (the first node of the
 * conversation)
 */
public class InitialGraphicNode extends GraphicNode {

    /**
     * Constructor
     * 
     * @param node
     *            Link to the conversational node
     * @param position
     *            Position of the node
     */
    public InitialGraphicNode( ConversationNodeView node, Point position ) {

        super( node, position );
    }

    @Override
    public void drawNode( float scale, Graphics g ) {

        Point position = getPosition( scale );

        int side = (int) ( NODE_DIAMETER * scale );

        g.setColor( Color.GREEN );
        int x = (int) ( position.x - NODE_RADIUS * scale );
        int y = (int) ( position.y - NODE_RADIUS * scale );
        g.fillOval( x, y, side, side );

        Point textPos = new Point( );
        textPos.setLocation( position.x - NODE_RADIUS * scale + 2 * scale, position.y + ( 7 * scale ) );
        g.setFont( new Font( "Monospaced", Font.PLAIN, (int) ( 15 * scale ) ) );
        if( node.hasEffects( ) ) {
            g.setColor( Color.BLACK );
            g.drawString( TC.get( "Effects.Title" ), (int) textPos.getX( ), (int) textPos.getY( ) );
            textPos.setLocation( textPos.getX( ), textPos.getY( ) - ( 15 * scale ) );
        }
        /*		if (node.getLineCount() > 0) {
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
        g.drawOval( x, y, side, side );

        if( selected ) {
            g.setColor( Color.RED );
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke( new BasicStroke( (int) ( 4 * scale ) ) );
            g.drawOval( x, y, side, side );
            g2.setStroke( new BasicStroke( 1 ) );
        }
        if( selectedChild ) {
            g.setColor( Color.BLUE );
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke( new BasicStroke( (int) ( 4 * scale ) ) );
            g.drawOval( x, y, side, side );
            g2.setStroke( new BasicStroke( 1 ) );
        }
    }

}
