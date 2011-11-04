/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.loader.subparsers;


import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.ConversationReference;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * Class to subparse characters
 */
public class CharacterSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading resources tag
     */
    private static final int READING_RESOURCES = 1;

    /**
     * Constant for reading conversation reference tag
     */
    private static final int READING_CONVERSATION_REFERENCE = 2;

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Constant for subparsing the actions tag
     */
    private static final int SUBPARSING_ACTIONS = 2;
    
    /**
     * Constant for subparsing description tag.
     */
    private static final int SUBPARSING_DESCRIPTION = 3;

    /**
     * Stores the current element being parsed
     */
    private int reading = READING_NONE;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * The character being read
     */
    private NPC npc;

    /**
     * Current resources being read
     */
    private Resources currentResources;

    /**
     * Current conversation reference being read
     */
    private ConversationReference conversationReference;

    /**
     * Current conditions being read
     */
    private Conditions currentConditions;

    /**
     * Subparser for the conditions
     */
    private SubParser subParser;
    
    
    private List<Description> descriptions;
    
    private Description description;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public CharacterSubParser( Chapter chapter ) {

        super( chapter );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a character tag, store the id of the character
            if( qName.equals( "character" ) ) {
                String characterId = "";

                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "id" ) )
                        characterId = attrs.getValue( i );

                npc = new NPC( characterId );
                
                descriptions = new ArrayList<Description>();
                npc.setDescriptions( descriptions );
            }

            // If it is a resources tag, create the new resources, and switch the element being parsed
            else if( qName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getQName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }

                reading = READING_RESOURCES;
            }

            // If it is an asset tag, read it and add it to the current resources
            else if( qName.equals( "asset" ) ) {
                String type = "";
                String path = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "type" ) )
                        type = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "uri" ) )
                        path = attrs.getValue( i );
                }

                // If the asset is not an special one
                //if( !AssetsController.isAssetSpecial( path ) )
                currentResources.addAsset( type, path );
            }

            // If it is a frontcolor or bordercolor tag, pick the color
            else if( qName.equals( "frontcolor" ) || qName.equals( "bordercolor" ) ) {
                String color = "";

                // Pick the color
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "color" ) )
                        color = attrs.getValue( i );

                // Set the color in the npc
                if( qName.equals( "frontcolor" ) )
                    npc.setTextFrontColor( color );
                if( qName.equals( "bordercolor" ) )
                    npc.setTextBorderColor( color );
            }

            else if( qName.equals( "textcolor" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "showsSpeechBubble" ) )
                        npc.setShowsSpeechBubbles( attrs.getValue( i ).equals( "yes" ) );
                    if( attrs.getQName( i ).equals( "bubbleBkgColor" ) )
                        npc.setBubbleBkgColor( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "bubbleBorderColor" ) )
                        npc.setBubbleBorderColor( attrs.getValue( i ) );
                }
            }

            // If it is a conversation reference tag, store the destination id, and switch the element being parsed
            else if( qName.equals( "conversation-ref" ) ) {
                String idTarget = "";

                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        idTarget = attrs.getValue( i );

                conversationReference = new ConversationReference( idTarget );
                reading = READING_CONVERSATION_REFERENCE;
            }

            // If it is a condition tag, create a new subparser
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }
            // If it is a voice tag, take the voice and the always synthesizer option
            else if( qName.equals( "voice" ) ) {
                String voice = new String( "" );
                String response;
                boolean alwaysSynthesizer = false;

                // Pick the voice and synthesizer option
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "name" ) )
                        voice = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "synthesizeAlways" ) ) {
                        response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            alwaysSynthesizer = true;
                    }

                }
                npc.setAlwaysSynthesizer( alwaysSynthesizer );
                npc.setVoice( voice );
            }

            else if( qName.equals( "actions" ) ) {
                subParser = new ActionsSubParser( chapter, npc );
                subParsing = SUBPARSING_ACTIONS;
            }
         
            // If it is a description tag, create the new description (with its id)
            else if( qName.equals( "description" ) ) {
                description = new Description();
                subParser = new DescriptionsSubParser(description, chapter);
                subParsing = SUBPARSING_DESCRIPTION; 
            }   
         
        }

        // If a condition or action is being subparsed, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a character tag, store the character in the game data
            if( qName.equals( "character" ) ) {
                chapter.addCharacter( npc );
            }

            // If it is a documentation tag, hold the documentation in the character
            else if( qName.equals( "documentation" ) ) {
                if( reading == READING_NONE )
                    npc.setDocumentation( currentString.toString( ).trim( ) );
                else if( reading == READING_CONVERSATION_REFERENCE )
                    conversationReference.setDocumentation( currentString.toString( ).trim( ) );
            }
            
            // If it is a resources tag, add the resources in the character
            else if( qName.equals( "resources" ) ) {
                npc.addResources( currentResources );
                reading = READING_NONE;
            }

            // If it is a conversation reference tag, add the reference to the character
            else if( qName.equals( "conversation-ref" ) ) {

                //npc.addConversationReference( conversationReference );
                Action action = new Action( Action.TALK_TO );
                action.setConditions( conversationReference.getConditions( ) );
                action.setDocumentation( conversationReference.getDocumentation( ) );
                TriggerConversationEffect effect = new TriggerConversationEffect( conversationReference.getTargetId( ) );
                action.getEffects( ).add( effect );
                npc.addAction( action );
                reading = READING_NONE;
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {

            // Spread the end element call
            subParser.endElement( namespaceURI, sName, qName );

            // If the condition is being closed
            if( qName.equals( "condition" ) ) {
                // Add the condition to the resources
                if( reading == READING_RESOURCES )
                    currentResources.setConditions( currentConditions );

                // Add the condition to the conversation reference
                if( reading == READING_CONVERSATION_REFERENCE )
                    conversationReference.setConditions( currentConditions );

                // Stop subparsing
                subParsing = SUBPARSING_NONE;
            }
        }
        else if( subParsing == SUBPARSING_ACTIONS ) {
            subParser.endElement( namespaceURI, sName, qName );
            if( qName.equals( "actions" ) ) {
                subParsing = SUBPARSING_NONE;
            }
        }
        
        // If it is a description tag, create the new description (with its id)
        else if( subParsing == SUBPARSING_DESCRIPTION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );
            if( qName.equals( "description" ) ) {
                this.descriptions.add( description );
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

        // If no element is being subparsed, read the characters
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If there are some kind of subparsing, spread the call
        else 
            subParser.characters( buf, offset, len );
    }
}
