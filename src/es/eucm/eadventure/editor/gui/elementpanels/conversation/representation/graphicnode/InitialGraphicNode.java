package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;

/**
 * Graphic representation of a initial dialogue node (the first node of the conversation)
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
	public void drawNode( Graphics g ) {
		// Paint the circle
		super.drawNode( g );

		// Write a I inside the node
		g.setColor( SystemColor.control );
		g.setFont( new Font( "Monospaced", Font.BOLD, 40 ) );
		g.drawString( "I", (int) position.getX( ) - 12, (int) position.getY( ) + 11 );

		// If the node has an effect, write it beside the circle
		if( node.hasEffects( ) ) {
			g.setColor( Color.BLACK );
			g.setFont( new Font( "Monospaced", Font.PLAIN, 10 ) );
			g.drawString( TextConstants.getText("Effects.Title"), (int) position.getX( ) + NODE_RADIUS, (int) position.getY( ) + NODE_RADIUS );
		}
	}

}
