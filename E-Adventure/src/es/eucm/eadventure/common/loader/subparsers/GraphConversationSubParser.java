/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.common.loader.subparsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

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
     * Constant for subparsing conditions
     */
    private static final int SUBPARSING_CONDITION = 2;

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
    private List<ConversationNode> graphNodes;

    /**
     * Stores the current set of links (of the current node)
     */
    private List<Integer> currentLinks;

    /**
     * Bidimensional vector for storing the links between nodes (the first
     * dimension holds the nodes, the second one the links)
     */
    private List<List<Integer>> nodeLinks;

    /**
     * Current effect (of the current node)
     */
    private Effects currentEffects;

    /**
     * Subparser for the effect
     */
    private SubParser subParser;

    /**
     * Name of the last non-player character read, "NPC" is no name were found
     */
    private String characterName;

    /**
     * Path of the audio track for a conversation line
     */
    private String audioPath;

    /**
     * Check if the options in option node may be random
     */
    private boolean random;

    /**
     * Check if a conversation line must be synthesize
     */
    private Boolean synthesizerVoice;

    /**
     * Stores the current conditions being read
     */
    private Conditions currentConditions;

    /**
     * Store the current conversation line
     */
    private ConversationLine conversationLine;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public GraphConversationSubParser( Chapter chapter ) {

        super( chapter );
    }

    /*
     * (non-Javadoc)
     * 
     * @see conversationaleditor.xmlparser.ConversationParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
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

                graphNodes = new ArrayList<ConversationNode>( );
                nodeLinks = new ArrayList<List<Integer>>( );
            }

            // If it is a node, create a new node
            else if( qName.equals( "dialogue-node" ) || qName.equals( "option-node" ) ) {
                // Create the node depending of the tag
                if( qName.equals( "dialogue-node" ) )
                    currentNode = new DialogueConversationNode( );

                if( qName.equals( "option-node" ) ) {
                    for( int i = 0; i < attrs.getLength( ); i++ ) {
                        //If there is a "random" attribute, store is the options will be random
                        if( attrs.getQName( i ).equals( "random" ) ) {
                            if( attrs.getValue( i ).equals( "yes" ) )
                                random = true;
                            else
                                random = false;
                        }
                    }

                    currentNode = new OptionConversationNode( random );
                }
                // Create a new vector for the links of the current node
                currentLinks = new ArrayList<Integer>( );
            }

            // If it is a non-player character line, store the character name and audio path (if present)
            else if( qName.equals( "speak-char" ) ) {
                // Set default name to "NPC"
                characterName = "NPC";
                audioPath = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    // If there is a "idTarget" attribute, store it
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        characterName = attrs.getValue( i );

                    // If there is a "uri" attribute, store it as audio path
                    if( attrs.getQName( i ).equals( "uri" ) )
                        audioPath = attrs.getValue( i );
                    // If there is a "synthesize" attribute, store its value
                    if( attrs.getQName( i ).equals( "synthesize" ) ) {
                        String response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            synthesizerVoice = true;
                        else
                            synthesizerVoice = false;
                    }
                }
            }

            // If it is a player character line, store the audio path (if present)
            else if( qName.equals( "speak-player" ) ) {
                audioPath = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {

                    // If there is a "uri" attribute, store it as audio path
                    if( attrs.getQName( i ).equals( "uri" ) )
                        audioPath = attrs.getValue( i );
                    // If there is a "synthesize" attribute, store its value
                    if( attrs.getQName( i ).equals( "synthesize" ) ) {
                        String response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            synthesizerVoice = true;
                        else
                            synthesizerVoice = false;
                    }
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
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }// If it is a condition tag, create new conditions and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }
        }

        // If we are subparsing an effect, spread the call
        if( subParsing == SUBPARSING_EFFECT || subParsing == SUBPARSING_CONDITION ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see conversationaleditor.xmlparser.ConversationParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If the tag ending is the conversation, create the graph conversation, with the first node of the list
            if( qName.equals( "graph-conversation" ) ) {
                setNodeLinks( );
                chapter.addConversation( new GraphConversation( conversationName, graphNodes.get( 0 ) ) );
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
                conversationLine = new ConversationLine( ConversationLine.PLAYER, new String( currentString ).trim( ) );
                if( audioPath != null && !this.audioPath.equals( "" ) ) {
                    conversationLine.setAudioPath( audioPath );
                }
                if( synthesizerVoice != null )
                    conversationLine.setSynthesizerVoice( synthesizerVoice );

                currentNode.addLine( conversationLine );
            }

            // If the tag is a line said by a non-player character, add it to the current node
            else if( qName.equals( "speak-char" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                conversationLine = new ConversationLine( characterName, new String( currentString ).trim( ) );
                if( audioPath != null && !this.audioPath.equals( "" ) ) {
                    conversationLine.setAudioPath( audioPath );
                }
                if( synthesizerVoice != null )
                    conversationLine.setSynthesizerVoice( synthesizerVoice );
                currentNode.addLine( conversationLine );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If we are subparsing an effect
        else if( subParsing == SUBPARSING_EFFECT || subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the effect is being closed, insert the effect into the current node
            if( qName.equals( "effect" ) && subParsing == SUBPARSING_EFFECT ) {
                currentNode.setEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }
            // If the effect is being closed, insert the effect into the current node
            else if( qName.equals( "condition" ) && subParsing == SUBPARSING_CONDITION ) {
                conversationLine.setConditions( currentConditions );
                subParsing = SUBPARSING_NONE;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // If no element is being subparsing
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If an effect is being subparsed, spread the call
        else if( subParsing == SUBPARSING_EFFECT || subParsing == SUBPARSING_CONDITION )
            subParser.characters( buf, offset, len );
    }

    /**
     * Set the links between the conversational nodes, taking the indexes of
     * their children, stored in nodeLinks
     */
    private void setNodeLinks( ) {

        // The size of graphNodes and nodeLinks should be the same
        for( int i = 0; i < graphNodes.size( ); i++ ) {
            // Extract the node and its links
            ConversationNode node = graphNodes.get( i );
            List<Integer> links = nodeLinks.get( i );

            // For each reference, insert the referenced node into the father node
            for( int j = 0; j < links.size( ); j++ )
                node.addChild( graphNodes.get( links.get( j ) ) );
        }
    }
}