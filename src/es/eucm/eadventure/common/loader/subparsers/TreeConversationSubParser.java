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
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * Class to subparse tree conversations
 */
public class TreeConversationSubParser extends SubParser {

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
     * Normal state
     */
    private static final int STATE_NORMAL = 0;

    /**
     * Waiting for an option state
     */
    private static final int STATE_WAITING_OPTION = 1;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * State of the recognizer
     */
    private int state = STATE_NORMAL;

    /**
     * Stores the different conversations, in trees form
     */
    private Conversation conversation;

    /**
     * Stores the current node
     */
    private ConversationNode currentNode;

    /**
     * Stores the past optional nodes, for back tracking
     */
    private List<ConversationNode> pastOptionNodes;

    /**
     * Current effect (of the current node)
     */
    private Effects currentEffects;

    /**
     * The subparser for the effect
     */
    private SubParser effectSubParser;

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

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the readed data
     */
    public TreeConversationSubParser( Chapter chapter ) {

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

            // If it is a "conversation" we pick the name, so we can build the tree later
            if( qName.equals( "tree-conversation" ) ) {
                // Store the name
                String conversationName = "";
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "id" ) )
                        conversationName = attrs.getValue( i );

                // Create a dialogue node (which will be the root node) and add it to a new tree
                // The content of the tree will be built adding nodes directly to the root
                currentNode = new DialogueConversationNode( );
                conversation = new TreeConversation( conversationName, currentNode );
                pastOptionNodes = new ArrayList<ConversationNode>( );
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

            // If it is a point with a set of possible responses, create a new OptionNode
            else if( qName.equals( "response" ) ) {

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    //If there is a "random" attribute, store is the options will be random
                    if( attrs.getQName( i ).equals( "random" ) ) {
                        if( attrs.getValue( i ).equals( "yes" ) )
                            random = true;
                        else
                            random = false;
                    }
                }

                // Create a new OptionNode, and link it to the current node
                ConversationNode nuevoNodoOpcion = new OptionConversationNode( random );
                currentNode.addChild( nuevoNodoOpcion );

                // Change the actual node for the option node recently created
                currentNode = nuevoNodoOpcion;
            }

            // If we are about to read an option, change the state of the recognizer, so we can read the line of the
            // option
            else if( qName.equals( "option" ) ) {
                state = STATE_WAITING_OPTION;

            }

            // If it is an effect tag, create new effect, new subparser and switch state
            else if( qName.equals( "effect" ) ) {
                currentEffects = new Effects( );
                effectSubParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }

            // If there is a go back, link the current node (which will be a DialogueNode) with the last OptionNode
            // stored
            else if( qName.equals( "go-back" ) ) {
                currentNode.addChild( pastOptionNodes.get( pastOptionNodes.size( ) - 1 ) );
            }
        }

        // If an effect element is being subparsed, spread the call
        if( subParsing == SUBPARSING_EFFECT ) {
            effectSubParser.startElement( namespaceURI, sName, qName, attrs );
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
            // If the conversation ends, store it in the game data
            if( qName.equals( "tree-conversation" ) ) {
                chapter.addConversation( new GraphConversation( (TreeConversation) conversation ) );
            }

            // If the tag is a line said by the player, add it to the current node
            else if( qName.equals( "speak-player" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                ConversationLine line = new ConversationLine( ConversationLine.PLAYER, new String( currentString ).trim( ) );
                if( audioPath != null && !this.audioPath.equals( "" ) ) {
                    line.setAudioPath( audioPath );
                }
                if( synthesizerVoice != null )
                    line.setSynthesizerVoice( synthesizerVoice );

                currentNode.addLine( line );

                // If we were waiting an option, create a new DialogueNode
                if( state == STATE_WAITING_OPTION ) {
                    // Create a new DialogueNode, and link it to the current node (which will be a OptionNode)
                    ConversationNode newDialogueNode = new DialogueConversationNode( );
                    currentNode.addChild( newDialogueNode );

                    // Add the current node (OptionNode) in the list of past option nodes, and change the current node
                    pastOptionNodes.add( currentNode );
                    currentNode = newDialogueNode;

                    // Go back to the normal state
                    state = STATE_NORMAL;
                }

            }

            // If the tag is a line said by a non-player character, add it to the current node
            else if( qName.equals( "speak-char" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                ConversationLine line = new ConversationLine( characterName, new String( currentString ).trim( ) );
                if( audioPath != null && !this.audioPath.equals( "" ) ) {
                    line.setAudioPath( audioPath );
                }

                if( synthesizerVoice != null )
                    line.setSynthesizerVoice( synthesizerVoice );
                currentNode.addLine( line );

            }

            // If an "option" tag ends, go back to keep working on the last OptionNode
            else if( qName.equals( "option" ) ) {
                // Se the current node to the last OptionNode stored
                currentNode = pastOptionNodes.remove( pastOptionNodes.size( ) - 1 );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If an effect tag is being subparsed
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
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If an effect is being subparsed
        else if( subParsing == SUBPARSING_EFFECT )
            effectSubParser.characters( buf, offset, len );
    }
}
