package es.eucm.eadventure.engine.loader.subparsers;

import java.util.Vector;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapterdata.effects.Effects;
import es.eucm.eadventure.engine.core.data.gamedata.GameData;
import es.eucm.eadventure.common.data.chapterdata.conversation.Conversation;
import es.eucm.eadventure.common.data.chapterdata.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapterdata.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.OptionConversationNode;

/**
 * Class to subparse graph conversations
 */
public class GraphConversationSubParser extends SubParser {
    
    /* Attributes */
    
    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;
    
    /**
     * Constant for subparsing effect tag
     */
    private static final int SUBPARSING_EFFECT = 1;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Name of the conversation
     */
    private String conversationName;

    /**
     * Stores the current node
     */
    private ConversationNode currentNode;

    /**
     * Set of nodes for the graph
     */
    private Vector<ConversationNode> graphNodes;

    /**
     * Stores the current set of links (of the current node)
     */
    private Vector<Integer> currentLinks;

    /**
     * Bidimensional vector for storing the links between nodes (the first dimension holds the nodes, the second one the links)
     */
    private Vector<Vector<Integer>> nodeLinks;

    /**
     * Current effect (of the current node)
     */
    private Effects currentEffects;
    
    /**
     * Subparser for the effect
     */
    private SubParser effectSubParser;

    /**
     * Name of the last non-player character readed, "NPC" is no name were found
     */
    private String characterName;
    
    /**
     * Path of the audio track for a conversation line
     */
    private String audioPath;

    /* Methods */

    /**
     * Constructor
     * @param gameData Game data to store the readed data
     */
    public GraphConversationSubParser( GameData gameData ) {
        super( gameData );
    }

    /*
     *  (non-Javadoc)
     * @see conversationaleditor.xmlparser.ConversationParser#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {
        
        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If it is a "graph-conversation" we pick the name, so we can build the conversation later
            if( qName.equals( "graph-conversation" ) ) {
                // Store the name
                conversationName = "";
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "id" ) )
                        conversationName = attrs.getValue( i );
                
                graphNodes = new Vector<ConversationNode>( );
                nodeLinks = new Vector<Vector<Integer>>( );
            }
    
            // If it is a node, create a new node
            else if( qName.equals( "dialogue-node" ) || qName.equals( "option-node" ) ) {
                // Create the node depending of the tag
                if( qName.equals( "dialogue-node" ) )
                    currentNode = new DialogueConversationNode( );
    
                if( qName.equals( "option-node" ) )
                    currentNode = new OptionConversationNode( );
    
                // Create a new vector for the links of the current node
                currentLinks = new Vector<Integer>( );
            }
    
         // If it is a non-player character line, store the character name and audio path (if present)
            else if( qName.equals( "speak-char" ) ) {
                // Set default name to "NPC"
                characterName = "NPC";
                audioPath="";

                for (int i=0 ; i<attrs.getLength( ); i++){
                    // If there is a "idTarget" attribute, store it
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        characterName = attrs.getValue( i );
                
                    // If there is a "uri" attribute, store it as audio path
                    if( attrs.getQName( i ).equals( "uri" ) )
                        audioPath = attrs.getValue( i );
                }
            }
            
            // If it is a player character line, store the audio path (if present)
            else if( qName.equals( "speak-player" ) ) {
                audioPath="";

                for (int i=0 ; i<attrs.getLength( ); i++){
                
                    // If there is a "uri" attribute, store it as audio path
                    if( attrs.getQName( i ).equals( "uri" ) )
                        audioPath = attrs.getValue( i );
                }
            }

    
            // If it is a node to a child, store the number of the child node
            else if( qName.equals( "child" ) ) {
                // Look for the index of the link, and add it
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "nodeindex" ) ) {
                        // Get the child node index, and store it
                        Integer childIndex = new Integer( attrs.getValue( i ) );
                        currentLinks.add( childIndex );
                    }
                }
            }
    
            // If it is an effect tag
            else if( qName.equals( "effect" ) ) {
                // Create the new effects, and the subparser
                currentEffects = new Effects( );
                effectSubParser = new EffectSubParser( currentEffects, gameData );
                subParsing = SUBPARSING_EFFECT;
            }
        }

        // If we are subparsing an effect, spread the call
        if( subParsing == SUBPARSING_EFFECT ) {
            effectSubParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     *  (non-Javadoc)
     * @see conversationaleditor.xmlparser.ConversationParser#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If the tag ending is the conversation, create the graph conversation, with the first node of the list
            if( qName.equals( "graph-conversation" ) ) {
                setNodeLinks( );
                gameData.addConversation( new GraphConversation( conversationName, graphNodes.get( 0 ) ) );
            }
    
            // If a node is closed
            else if( qName.equals( "dialogue-node" ) || qName.equals( "option-node" ) ) {
                // Add the current node to the node list, and the set of children references into the node links
                graphNodes.add( currentNode );
                nodeLinks.add( currentLinks );
            }
    
            // If the tag is a line said by the player, add it to the current node
            else if( qName.equals( "speak-player" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                ConversationLine line =new ConversationLine( "Player", new String( currentString ).trim( ) );
                if (audioPath!=null && !this.audioPath.equals( "" )){
                    line.setAudioPath( audioPath );
                }
                
                currentNode.addLine( line );
            }

            // If the tag is a line said by a non-player character, add it to the current node
            else if( qName.equals( "speak-char" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                ConversationLine line =new ConversationLine( characterName, new String( currentString ).trim( ) );
                if (audioPath!=null && !this.audioPath.equals( "" )){
                    line.setAudioPath( audioPath );
                }
                currentNode.addLine( line );
            }
            
            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If we are subparsing an effect
        else if( subParsing == SUBPARSING_EFFECT ) {
            // Spread the call
            effectSubParser.endElement( namespaceURI, sName, qName );
            
            // If the effect is being closed, insert the effect into the current node
            if( qName.equals( "effect" ) ) {
                currentNode.setEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) {
        // If no element is being subparsing
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );
        
        // If an effect is being subparsed, spread the call
        else if( subParsing == SUBPARSING_EFFECT )
            effectSubParser.characters( buf, offset, len );
    }

    /**
     * Set the links between the conversational nodes, taking the indexes of their children, stored in nodeLinks
     */
    private void setNodeLinks( ) {
        // The size of graphNodes and nodeLinks should be the same
        for( int i = 0; i < graphNodes.size( ); i++ ) {
            // Extract the node and its links
            ConversationNode node = graphNodes.get( i );
            Vector<Integer> links = nodeLinks.get( i );

            // For each reference, insert the referenced node into the father node
            for( int j = 0; j < links.size( ); j++ )
                node.addChild( graphNodes.get( links.get( j ) ) );
        }
    }
}