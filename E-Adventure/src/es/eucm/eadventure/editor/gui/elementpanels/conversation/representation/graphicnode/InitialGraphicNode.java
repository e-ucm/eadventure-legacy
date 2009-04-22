package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

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
	public void drawNode( float scale, Graphics g ) {
		Point position = getPosition(scale);

		int side = (int) (NODE_DIAMETER * scale);
		
		g.setColor(Color.GREEN);
		int x = (int) (position.x - NODE_RADIUS * scale);
		int y = (int) (position.y - NODE_RADIUS * scale);
		g.fillOval(x, y, side, side);

		Point textPos = new Point();
		textPos.setLocation(position.x - NODE_RADIUS * scale, position.y + (10 * scale));
		g.setFont( new Font( "Monospaced", Font.PLAIN, (int) (10 * scale) ) );
		if (node.hasEffects()) {
			g.setColor(Color.BLACK);
			g.drawString( TextConstants.getText("Effects.Title"), (int) textPos.getX( ), (int) textPos.getY( ));
			textPos.setLocation(textPos.getX(), textPos.getY() - (10 * scale));
		}
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
		
		g.setColor(Color.GRAY);
		g.drawOval(x, y, side, side);
		
		if (selected) {
			g.setColor(Color.RED);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke((int) (4 * scale)));
			g.drawOval(x, y, side, side);
			g2.setStroke(new BasicStroke(1));
		}
		if (selectedChild) {
			g.setColor(Color.BLUE);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke((int) (4 * scale)));
			g.drawOval(x, y, side, side);
			g2.setStroke(new BasicStroke(1));
		}
	}

}
