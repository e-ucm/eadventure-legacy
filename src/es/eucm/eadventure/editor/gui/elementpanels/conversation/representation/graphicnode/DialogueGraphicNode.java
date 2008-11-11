package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;

import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView;

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
	public void drawNode( Graphics g ) {
		// Paint the circle
		super.drawNode( g );

		// Set color and font
		g.setColor( SystemColor.control );
		g.setFont( new Font( "Monospaced", Font.BOLD, 40 ) );

		// If the node is not terminal, draw a "D"
		if( !node.isTerminal( ) )
			g.drawString( "D", (int) position.getX( ) - 10, (int) position.getY( ) + 11 );

		// If the node is terminal, draw a "T" (and the "FunctionalEffect" string if necessary)
		else {
			g.drawString( "T", (int) position.getX( ) - 12, (int) position.getY( ) + 11 );
		}
		
		// If the node has an effect, write it beside the circle
		if( node.hasEffects( ) ) {
			g.setColor( Color.BLACK );
			g.setFont( new Font( "Monospaced", Font.PLAIN, 10 ) );
			g.drawString( "FunctionalEffect", (int) position.getX( ) + NODE_RADIUS, (int) position.getY( ) + NODE_RADIUS );
		}

	}
}
