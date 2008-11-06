package es.eucm.eadventure.engine.core.data.gamedata.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.engine.core.data.gamedata.conversation.node.Node;

/**
 * Implements Tree and Graph conversations
 */
public class Conversation {

    /* Attributes */

    /**
     * Reference name of the conversation
     */
    private String conversationId;

    /**
     * Root of the conversation
     */
    private Node root;

    /* Methods */

    /**
     * Constructor
     * @param conversationName Reference name of the conversation
     * @param root Root node (start) of the conversation
     */
    public Conversation( String conversationName, Node root ) {
        this.conversationId = conversationName;
        this.root = root;
    }

    /**
     * Returns the name of the conversation
     * @return Conversation's name
     */
    public String getId( ) {
        return conversationId;
    }

    /**
     * Returns the initial node of the conversation, the one which starts the conversation
     * @return First node of the conversation
     */
    public Node getStartingNode( ) {
        return root;
    }
    
    public void resetEffects( ){
        
    }
    
    public List<Node> getAllNodes (){
        List<Node> nodes = new ArrayList<Node>();
        getAllNodes ( root, nodes );
        return nodes;
    }
    
    private void getAllNodes(Node firstNode, List<Node> nodes){
        for (int i=0; i<firstNode.getChildCount( ); i++){
            Node child = firstNode.getChild( i );
            // Check the child is not in the list yet
            boolean isInList = false; 
            for (Node aNode: nodes){
                if (aNode == child){
                    isInList = true;break;
                }
                    
            }
            if (!isInList){
                nodes.add( child );
                getAllNodes( child, nodes);
            }
        }
    }
}
