package es.eucm.eadventure.adventureeditor.gui.elementpanels.conversation.representation.graphicnode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView;

/**
 * This class is the graphic representation of a given conversational node. Basically, it holds a link to the node, its
 * position in the canvas, and links to other GraphicNodes, so it can draw lines attaching nodes
 */
public class GraphicNode {

	/**
	 * Radius of the nodes.
	 */
	public static final int NODE_RADIUS = 25;

	/**
	 * Diameter for the nodes.
	 */
	public static final int NODE_DIAMETER = 50;

	/**
	 * Radius to paint the border on the nodes.
	 */
	public static final int NODE_SELECTED_RADIUS = 30;

	/**
	 * Diameter to paint the border on the nodes.
	 */
	public static final int NODE_SELECTED_DIAMETER = 60;

	/**
	 * Link to the conversational attached node
	 */
	protected ConversationNodeView node;

	/**
	 * Position of the node in the canvas (usually a JPanel)
	 */
	protected Point position;

	/**
	 * Link to other GraphicNodes, to draw connections
	 */
	private List<GraphicNode> children;

	/**
	 * Constructor
	 * 
	 * @param node
	 *            Link to the conversational node
	 * @param position
	 *            Position of the node
	 */
	public GraphicNode( ConversationNodeView node, Point position ) {
		this.node = node;
		this.position = position;
		children = new ArrayList<GraphicNode>( );
	}

	/**
	 * Adds a new child to the node
	 * 
	 * @param graphicNode
	 *            New child
	 */
	public void addChild( GraphicNode graphicNode ) {
		children.add( graphicNode );
	}

	/**
	 * Removes all the children of the graphic node
	 */
	public void removeAllChildren( ) {
		while( !children.isEmpty( ) )
			children.remove( 0 );
	}

	/**
	 * Returns the conversational node linked to the GraphicNode.
	 * 
	 * @return The contained node
	 */
	public ConversationNodeView getNode( ) {
		return node;
	}

	/**
	 * Returns the number of GraphicNode children.
	 * 
	 * @return The children count of the node
	 */
	public int getChildCount( ) {
		return children.size( );
	}

	/**
	 * Returns the position of the selected GraphicNode child.
	 * 
	 * @param index
	 *            Index of child
	 * @return The position of the given child node
	 */
	public Point getChildPosition( int index ) {
		return children.get( index ).getPosition( );
	}

	/**
	 * Returns the position of the node in the canvas.
	 * 
	 * @return The position of the node
	 */
	public Point getPosition( ) {
		return position;
	}

	/**
	 * Moves the node in the given increment.
	 * 
	 * @param increment
	 *            Increment of position
	 */
	public void moveNode( Point increment ) {
		position.x += increment.x;
		position.y += increment.y;
	}

	/**
	 * Returns if the given point is inside the node (for selecting nodes with mouse clicks).
	 * 
	 * @param point
	 *            Point we want to know if it is inside the node
	 * @return True if the point is near enough of the node, false otherwise
	 */
	public boolean isInside( Point point ) {
		// True if the distance is less than the radius of the node
		double difX = point.getX( ) - position.getX( );
		double difY = point.getY( ) - position.getY( );

		return Math.sqrt( difX * difX + difY * difY ) <= NODE_RADIUS;
	}

	/**
	 * Draws the current node in the stored position.
	 * 
	 * @param g
	 *            Graphics for drawing
	 */
	public void drawNode( Graphics g ) {
		// Draws a black circle
		g.setColor( Color.BLACK );
		g.fillOval( (int) position.getX( ) - NODE_RADIUS, (int) position.getY( ) - NODE_RADIUS, NODE_DIAMETER, NODE_DIAMETER );
	}
}
