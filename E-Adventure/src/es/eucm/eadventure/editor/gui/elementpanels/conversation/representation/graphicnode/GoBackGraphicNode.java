package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;

/**
 * Graphic representation of a dialogue node with a go-back tag
 */
public class GoBackGraphicNode extends GraphicNode {

	/**
	 * Constructor
	 * 
	 * @param node
	 *            Link to the conversational node
	 * @param position
	 *            Position of the node
	 */
	public GoBackGraphicNode( ConversationNodeView node, Point position ) {
		super( node, position );
	}

	@Override
	public void drawNode( Graphics g ) {
		// Paint the circle
		super.drawNode( g );

		// Write a D inside the
		g.setColor( SystemColor.control );
		g.setFont( new Font( "Monospaced", Font.BOLD, 34 ) );
		g.drawString( "GB", (int) position.getX( ) - 20, (int) position.getY( ) + 10 );
	}

}
