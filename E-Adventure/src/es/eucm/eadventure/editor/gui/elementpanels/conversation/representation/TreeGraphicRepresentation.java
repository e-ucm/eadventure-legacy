package es.eucm.eadventure.editor.gui.elementpanels.conversation.representation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.controllers.conversation.TreeConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode.DialogueGraphicNode;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode.GoBackGraphicNode;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode.GraphicNode;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.representation.graphicnode.OptionGraphicNode;

/**
 * This class implements the methods to draw a tree conversation into a panel
 */
public class TreeGraphicRepresentation extends GraphicRepresentation {

	/* Attributes */

	/**
	 * Tree conversation data control.
	 */
	private TreeConversationDataControl treeConversationDataControl;

	/**
	 * Size of the real screen in which we are drawing
	 */
	private Dimension containerSize;

	/**
	 * Set of graphic nodes representating the conversation
	 */
	private List<GraphicNode> graphicNodes;

	/**
	 * Size of the represented conversation
	 */
	private Dimension conversationSize;

	/* Methods */

	/**
	 * Constructor.
	 * 
	 * @param treeConversationDataControl
	 *            Conversation to be painted
	 * @param containerSize
	 *            Size of the container that holds the representation
	 */
	public TreeGraphicRepresentation( TreeConversationDataControl treeConversationDataControl, Dimension containerSize ) {
		this.treeConversationDataControl = treeConversationDataControl;
		this.containerSize = containerSize;
		graphicNodes = new ArrayList<GraphicNode>( );
		update( );
	}

	@Override
	public Dimension getConversationSize( ) {
		return conversationSize;
	}

	@Override
	public ConversationNodeView getNodeInPosition( Point point ) {
		ConversationNodeView node = null;

		// For each graphic node, and while the clicked node has not been found
		for( int i = 0; i < graphicNodes.size( ) && node == null; i++ )
			// If the current graphic node has been clicked, store the node
			if( graphicNodes.get( i ).isInside( point ) )
				node = graphicNodes.get( i ).getNode( );

		return node;
	}

	@Override
	public Point getPositionOfNode( ConversationNodeView node ) {
		Point point = null;

		// For each graphic node, and while the selected nde has not been found
		for( int i = 0; i < graphicNodes.size( ) && point == null; i++ )
			// If the current graphic node holds the node we are searching, store the position of the node
			if( graphicNodes.get( i ).getNode( ) == node )
				point = graphicNodes.get( i ).getPosition( );

		return point;
	}

	@Override
	public void setContainerSize( Dimension containerSize ) {
		// Set the new container size, and rebuild the graphic representation
		this.containerSize = containerSize;
		update( );
	}

	@Override
	public void drawConversation( Graphics g ) {
		g.setColor( Color.BLACK );

		// For each graphic node
		for( int i = 0; i < graphicNodes.size( ); i++ ) {
			GraphicNode currentNode = graphicNodes.get( i );

			// For each child, draw a line from the current node to the child node
			for( int j = 0; j < currentNode.getChildCount( ); j++ ) {
				Point childPosition = currentNode.getChildPosition( j );
				g.drawLine( (int) currentNode.getPosition( ).getX( ), (int) currentNode.getPosition( ).getY( ), (int) childPosition.getX( ), (int) childPosition.getY( ) );
				drawArrow( g, currentNode.getPosition( ), childPosition );
			}
		}

		// For each graphic node
		for( int i = 0; i < graphicNodes.size( ); i++ ) {
			GraphicNode currentNode = graphicNodes.get( i );

			// Paint the node
			currentNode.drawNode( g );
		}
	}

	@Override
	public void update( ) {
		// Remove all graphic nodes from the vector
		graphicNodes.clear( );

		// Get the root node
		ConversationNodeView rootNode = treeConversationDataControl.getRootNode( );

		// Calculate the size of the conversation
		conversationSize = new Dimension( GraphicNode.NODE_DIAMETER * 2 * getMaxWidth( rootNode ), GraphicNode.NODE_DIAMETER * 2 * getMaxDepth( rootNode ) );

		// Set the initial position of the first node
		int initialX = GraphicNode.NODE_DIAMETER * getMaxWidth( rootNode );
		int initialY = GraphicNode.NODE_DIAMETER;

		// If the container width is bigger than the conversation width, position the node on the center of the screen
		if( containerSize.getWidth( ) > conversationSize.getWidth( ) )
			initialX = (int) containerSize.getWidth( ) / 2;

		// If the container height is bigger than the conversation height, move down the starting node, so the tree is
		// centered
		if( containerSize.getHeight( ) > conversationSize.getHeight( ) )
			initialY += ( (int) containerSize.getHeight( ) - (int) conversationSize.getHeight( ) ) / 2;

		// Create the entire graphic representation, with the starting node and position
		createGraphicNode( rootNode, initialX, initialY );
	}

	/**
	 * Recursive function that creates the graphic node of the given node, and the graphic nodes of the children
	 * 
	 * @param node
	 *            Node for graphic node construction
	 * @param X
	 *            X position of the current graphic node
	 * @param Y
	 *            Y position of the current graphic node
	 * @return Reference to the created graphic node
	 */
	private GraphicNode createGraphicNode( ConversationNodeView node, int X, int Y ) {
		GraphicNode graphicNode = null;

		// If the node is a dialogue node
		if( node.getType( ) == ConversationNodeView.DIALOGUE ) {

			// If it is a go-back node, draw a "GB" node
			if( TreeConversationDataControl.thereIsGoBackTag( node ) )
				graphicNode = new GoBackGraphicNode( node, new Point( X, Y ) );
			// If it is a regular dialogue node, draw a "D" or "T" node
			else
				graphicNode = new DialogueGraphicNode( node, new Point( X, Y ) );

			// Add the created graphic node to the vector of graphic nodes
			graphicNodes.add( graphicNode );

			// If node has a valid child
			if( !node.isTerminal( ) && !TreeConversationDataControl.thereIsGoBackTag( node ) ) {
				// Add to the graphic node the graphic node of its only child, in a lower position (Y +
				// GraphicNode.NODE_DIAMETER * 2)
				graphicNode.addChild( createGraphicNode( node.getChildView( 0 ), X, Y + GraphicNode.NODE_DIAMETER * 2 ) );
			}

			// If the node has a go back tag, link it with its father node
			if( TreeConversationDataControl.thereIsGoBackTag( node ) ) {
				GraphicNode fatherGraphicNode = null;

				// Search for the father
				for( int i = 0; i < graphicNodes.size( ) && fatherGraphicNode == null; i++ )
					// If the node is an option node, store the father graphic node
					if( node.getChildView( 0 ) == graphicNodes.get( i ).getNode( ) )
						fatherGraphicNode = graphicNodes.get( i );

				// Add the link
				graphicNode.addChild( fatherGraphicNode );
			}
		}

		// If node is a option node
		if( node.getType( ) == ConversationNodeView.OPTION ) {
			// Draw a "O" node
			graphicNode = new OptionGraphicNode( node, new Point( X, Y ) );

			// Add the created graphic node to the vector of graphic nodes
			graphicNodes.add( graphicNode );

			// Set the initial X position (for the first child)
			int initialX = X - ( getMaxWidth( node ) - 1 ) * GraphicNode.NODE_DIAMETER;

			// For each child of the given node
			for( int i = 0; i < node.getChildCount( ); i++ ) {
				// Set the current X position of the node
				int currentX = initialX + ( getMaxWidth( node.getChildView( i ) ) - 1 ) * GraphicNode.NODE_DIAMETER;

				// Add to the graphic node the graphic node of the current child, in the given position
				graphicNode.addChild( createGraphicNode( node.getChildView( i ), currentX, Y + GraphicNode.NODE_DIAMETER * 2 ) );

				// Update the X position
				currentX += ( getMaxWidth( node.getChildView( i ) ) - 1 ) * GraphicNode.NODE_DIAMETER;
				initialX = currentX + GraphicNode.NODE_DIAMETER * 2;
			}
		}

		// Return the reference of the created graphic node
		return graphicNode;
	}

	/**
	 * Recursive function that returns the max width of leaf nodes linked with the given node
	 * 
	 * @param node
	 *            Node for width calculation
	 * @return Max width of the children of the given node
	 */
	private int getMaxWidth( ConversationNodeView node ) {
		// Important, this is a Tree conversation, so each DialogueNode must be linked with a OptionNode, and vice versa

		// Set the node width to 0
		int nodeWidth = 0;

		// If the node is a DialogueNode
		if( node.getType( ) == ConversationNodeView.DIALOGUE ) {
			// Set width to 1, for it is its depth, if the node is terminal, or has a "go-back" tag
			nodeWidth = 1;

			// If the node has a valid child, the node width equals the child's width
			if( !node.isTerminal( ) && !TreeConversationDataControl.thereIsGoBackTag( node ) )
				nodeWidth = getMaxWidth( node.getChildView( 0 ) );
		}

		// If it is a OptionNode
		else {
			// For each child node, add its width to the actual node width
			for( int i = 0; i < node.getChildCount( ); i++ )
				nodeWidth += getMaxWidth( node.getChildView( i ) );

			// If the node has no children, set width to 1
			if( nodeWidth == 0 )
				nodeWidth = 1;
		}

		return nodeWidth;
	}

	/**
	 * Returns the max depth of the given node
	 * 
	 * @param node
	 *            Node for depth calculation
	 * @return Mas depth of the given node
	 */
	private int getMaxDepth( ConversationNodeView node ) {
		// Set the node depth to 0
		int nodeDepth = 0;

		// If the node is a DialogueNode
		if( node.getType( ) == ConversationNodeView.DIALOGUE ) {
			// If the node isn't terminal, and there is no "go-back" tag, the depth equals the child's depth
			if( !node.isTerminal( ) && !TreeConversationDataControl.thereIsGoBackTag( node ) )
				nodeDepth = getMaxDepth( node.getChildView( 0 ) );
		}

		// If it is a OptionNode
		else {
			// Get the max depth of all children
			for( int i = 0; i < node.getChildCount( ); i++ )
				nodeDepth = Math.max( nodeDepth, getMaxDepth( node.getChildView( i ) ) );
		}

		// Add the depth of the CURRENT node (previous operations were recursive)
		nodeDepth++;

		return nodeDepth;
	}

	@Override
	public void mousePressed( Point point ) {
	// This graphic representation doesn't allow node reallocation
	}

	@Override
	public boolean mouseDragged( Point point ) {
		// This graphic representation doesn't allow node reallocation
		return false;
	}

	@Override
	public boolean mouseReleased( ) {
		// This graphic representation doesn't allow node reallocation
		return false;
	}
}
