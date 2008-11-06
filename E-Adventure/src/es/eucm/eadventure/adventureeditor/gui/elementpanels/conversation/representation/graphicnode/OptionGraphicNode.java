package es.eucm.eadventure.adventureeditor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;

import es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView;

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
	public void drawNode( Graphics g ) {
		// Paint the circle
		super.drawNode( g );

		// Write a O inside
		g.setColor( SystemColor.control );
		g.setFont( new Font( "Monospaced", Font.BOLD, 40 ) );
		g.drawString( "O", (int) position.getX( ) - 12, (int) position.getY( ) + 11 );
	}
}
