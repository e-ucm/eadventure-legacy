package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode.GraphicNode;

/**
 * This interface contains the methods to draw a conversation into a panel and calculate its size
 */
public abstract class GraphicRepresentation {

	/**
	 * Size of the body of the arrow.
	 */
	private static final int ARROW_SIZE = 15;

	protected float scale = 1.0f;

	/**
	 * Set of graphic nodes representating the conversation
	 */
	protected List<GraphicNode> graphicNodes;

	protected GraphicNode selectedNode;

	protected GraphicNode selectedChildNode;

	/**
	 * Returns the size of the painted conversation.
	 * 
	 * @return The dimension of the conversation
	 */
	public abstract Dimension getConversationSize( );

	/**
	 * Returns reference to the node in the given position.
	 * 
	 * @param point
	 *            Position of the possible node
	 * @return Reference to node if present, null otherwise
	 */
	public abstract ConversationNodeView getNodeInPosition( Point point );

	/**
	 * Returns the position of the given conversational node, null if the node is not present.
	 * 
	 * @param node
	 *            Node which position we want to know
	 * @return The point in which the given node is placed
	 */
	public abstract Point getPositionOfNode( ConversationNodeView node );

	/**
	 * Sets the size of the container component (usually a JPanel).
	 * 
	 * @param containerSize
	 *            New size for the container
	 */
	public abstract void setContainerSize( Dimension containerSize );

	/**
	 * Draw the conversation in the given Graphics.
	 * 
	 * @param g
	 *            Graphics to perform draw
	 */
	public abstract void drawConversation( Graphics g );

	/**
	 * Updates the graphic representation of the conversation, when a node has been added or removed, or when the
	 * container has been resized.
	 */
	public abstract void update( );

	/**
	 * Notifies the graphic representation that a mouse button has been pressed (not clicked, but pressed and held).
	 * 
	 * @param point
	 *            Point in which the mouse has been pressed
	 */
	public abstract void mousePressed( Point point );

	/**
	 * Notifies the graphic representation that the mouse has been dragged.
	 * 
	 * @param point
	 *            Point in which the mouse has been dragged
	 * @return True if changes have been made, to repaint the panel, false otherwise
	 */
	public abstract boolean mouseDragged( Point point );

	/**
	 * Notifies the graphic representation that the mouse button has been released.
	 * 
	 * @return True if changes have been made, to repaint and resize the panel, false otherwise
	 */
	public abstract boolean mouseReleased( );

	/**
	 * Draws a little arrow head.
	 * 
	 * @param g
	 *            Graphics to perform draw
	 * @param startPoint
	 *            Start point of the line
	 * @param endPoint
	 *            End point of the line
	 */
	protected void drawArrow( Graphics g, GraphicNode node, Point startPoint, Point endPoint ) {
		double dX, dY, angle = 0;

		dX = startPoint.getX( ) - endPoint.getX( );
		dY = startPoint.getY( ) - endPoint.getY( );

		if( dX == 0 && dY > 0 )
			angle = Math.PI / 2;
		if( dX == 0 && dY < 0 )
			angle = 3 * Math.PI / 2;
		if( dX > 0 )
			angle = Math.atan( dY / dX );
		if( dX < 0 )
			angle = Math.atan( dY / dX ) + Math.PI;

		int[] pointsX = new int[3];
		int[] pointsY = new int[3];

		// Obtain the first point of the arrow
		boolean found = false;
		int i = 0;
		while (!found) {
			pointsX[0] = (int) ( endPoint.getX( ) + Math.cos( angle ) * i * scale);
			pointsY[0] = (int) ( endPoint.getY( ) + Math.sin( angle ) * i * scale);
			if (!node.isInside(scale, new Point(pointsX[0], pointsY[0])))
				found = true;
			else
				i++;
		}
		// Obtain the second point of the arrow
		pointsX[1] = (int) ( pointsX[0] + Math.cos( angle + 0.4 ) * ARROW_SIZE * scale);
		pointsY[1] = (int) ( pointsY[0] + Math.sin( angle + 0.4 ) * ARROW_SIZE * scale);

		// Obtain the third point of the arrow
		pointsX[2] = (int) ( pointsX[0] + Math.cos( angle - 0.4 ) * ARROW_SIZE * scale);
		pointsY[2] = (int) ( pointsY[0] + Math.sin( angle - 0.4 ) * ARROW_SIZE * scale);

		g.fillPolygon( pointsX, pointsY, 3 );
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float value) {
		scale = value;
	}

	public void setSelectedNode(ConversationNodeView selectedNode) {
		if (this.selectedNode != null) 
			this.selectedNode.setSelected(false);
		for (GraphicNode node : graphicNodes) {
			if (node.getNode() == selectedNode)
				this.selectedNode = node;
		}
		if (this.selectedNode != null)
			this.selectedNode.setSelected(true);
	}
	
	public void setSelectedChildNode(ConversationNodeView selectedNode) {
		if (this.selectedChildNode != null) 
			this.selectedChildNode.setSelectedChild(false);
		for (GraphicNode node : graphicNodes) {
			if (node.getNode() == selectedNode)
				this.selectedChildNode = node;
		}
		if (this.selectedChildNode != null)
			this.selectedChildNode.setSelectedChild(true);
	}

}
