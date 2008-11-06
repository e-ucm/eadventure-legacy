package es.eucm.eadventure.engine.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.engine.core.data.gamedata.ConversationReference;
import es.eucm.eadventure.engine.core.data.gamedata.GameData;
import es.eucm.eadventure.engine.core.data.gamedata.conditions.Conditions;
import es.eucm.eadventure.engine.core.data.gamedata.elements.NPC;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Asset;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;

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
    private SubParser conditionSubParser;

    /* Methods */

    /**
     * Constructor
     * @param gameData Game data to store the readed data
     */
    public CharacterSubParser( GameData gameData ) {
        super( gameData );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
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
            }
            
            // If it is a resources tag, create the new resources, and switch the element being parsed
            else if( qName.equals( "resources" ) ) {
                currentResources = new Resources( );
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
                
                currentResources.addAsset( new Asset( type, path ) );
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
                conditionSubParser = new ConditionSubParser( currentConditions, gameData );
                subParsing = SUBPARSING_CONDITION;
            }
        }
        
        // If a condition is being subparsed, spread the call
        if( subParsing == SUBPARSING_CONDITION ) {
            conditionSubParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String namespaceURI, String sName, String qName ) {
        
        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            
            // If it is a character tag, store the character in the game data
            if( qName.equals( "character" ) ) {
                gameData.addCharacter( npc );
            }
            
            // If it is a resources tag, add the resources in the character
            else if( qName.equals( "resources" ) ) {
                npc.addResources( currentResources );
                reading = READING_NONE;
            }
            
            // If it is a name tag, store the name
            else if( qName.equals( "name" ) ) {
                npc.setName( currentString.toString( ).trim( ) );
            }
    
            // If it is a brief tag, store the brief description
            else if( qName.equals( "brief" ) ) {
                npc.setDescription( currentString.toString( ).trim( ) );
            }
    
            // If it is a detailed tag, store the detailed description
            else if( qName.equals( "detailed" ) ) {
                npc.setDetailedDescription( currentString.toString( ).trim( ) );
            }
    
            // If it is a conversation reference tag, add the reference to the character
            else if( qName.equals( "conversation-ref" ) ) {
                npc.addConversationReference( conversationReference );
                reading = READING_NONE;
            }
            
            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            
            // Spread the end element call
            conditionSubParser.endElement( namespaceURI, sName, qName );
            
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
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) {
        // If no element is being subparsed, read the characters
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );
        
        // If a condition is being subparsed, spread the call
        else if( subParsing == SUBPARSING_CONDITION )
            conditionSubParser.characters( buf, offset, len );
    }
}
