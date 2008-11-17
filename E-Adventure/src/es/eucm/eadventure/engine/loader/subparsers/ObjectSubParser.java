package es.eucm.eadventure.engine.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapterdata.Action;
import es.eucm.eadventure.common.data.chapterdata.Chapter;
import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;
import es.eucm.eadventure.common.data.chapterdata.effects.Effects;
import es.eucm.eadventure.common.data.chapterdata.resources.Asset;
import es.eucm.eadventure.common.data.chapterdata.elements.Item;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;

/**
 * Class to subparse objetcs
 */
public class ObjectSubParser extends SubParser {
    
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
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;
    
    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;
    
    /**
     * Constant for subparsing effect tag
     */
    private static final int SUBPARSING_EFFECT = 2;
    
    /**
     * Store the current element being parsed
     */
    private int reading = READING_NONE;
    
    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Object being parsed
     */
    private Item object;
    
    /**
     * Current resources being parsed
     */
    private Resources currentResources;

    /**
     * Current conditions being parsed
     */
    private Conditions currentConditions;
    
    /**
     * Current effects being parsed
     */
    private Effects currentEffects;
    
    /**
     * Subparser for effects and conditions
     */
    private SubParser subParser;

    /**
     * Stores an idTarget
     */
    private String currentIdTarget;

    /* Methods */

    /**
     * Constructor
     * @param gameData Game data to store the readed data
     */
    public ObjectSubParser( Chapter gameData ) {
        super( gameData );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {
        
        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If it is a object tag, create the new object (with its id)
            if( qName.equals( "object" ) ) {
                String objectId = "";
    
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "id" ) )
                        objectId = attrs.getValue( i );
    
                object = new Item( objectId );
            }
            
            // If it is a resources tag, create the new resources and switch the state
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
    
            // If it is an examine, use or grab tag, create new conditions and effects
            else if( qName.equals( "examine" ) || qName.equals( "grab" ) || qName.equals( "use" ) ) {
                currentConditions = new Conditions( );
                currentEffects = new Effects( );
            }
    
            // If it is an use-with or give-to tag, create new conditions and effects, and store the idTarget
            else if( qName.equals( "use-with" ) || qName.equals( "give-to" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "idTarget" ) )
                        currentIdTarget = attrs.getValue( i );
    
                currentConditions = new Conditions( );
                currentEffects = new Effects( );
            }
            
            // If it is a condition tag, create new conditions and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, gameData );
                subParsing = SUBPARSING_CONDITION;
            }
            
            // If it is a effect tag, create new effects and switch the state
            else if( qName.equals( "effect" ) ) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, gameData );
                subParsing = SUBPARSING_EFFECT;
            }
        }
        
        // If it is reading an effect or a condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String namespaceURI, String sName, String qName ) {
        
        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            
            // If it is an object tag, store the object in the game data
            if( qName.equals( "object" ) ) {
                gameData.addItem( object );
            }
            
            // If it is a resources tag, add it to the object
            else if( qName.equals( "resources" ) ) {
                object.addResources( currentResources );
                reading = READING_NONE;
            }
            
            // If it is a name tag, store the name in the object
            else if( qName.equals( "name" ) ) {
                object.setName( currentString.toString( ).trim( ) );
            }
    
            // If it is a brief tag, store the brief description in the object
            else if( qName.equals( "brief" ) ) {
                object.setDescription( currentString.toString( ).trim( ) );
            }
    
            // If it is a detailed tag, store the detailed description in the object
            else if( qName.equals( "detailed" ) ) {
                object.setDetailedDescription( currentString.toString( ).trim( ) );
            }
    
            // If it is an examine tag, store the new action in the object
            else if( qName.equals( "examine" ) ) {
                object.addAction( new Action( Action.EXAMINE, currentConditions, currentEffects ) );
            }
    
            // If it is a grab tag, store the new action in the object
            else if( qName.equals( "grab" ) ) {
                object.addAction( new Action( Action.GRAB, currentConditions, currentEffects ) );
            }
            
            // If it is an use-with tag, store the new action in the object
            else if( qName.equals( "use" ) ) {
                object.addAction( new Action( Action.USE, currentConditions, currentEffects ) );
            }
    
           // If it is an use-with tag, store the new action in the object
            else if( qName.equals( "use-with" ) ) {
                object.addAction( new Action( Action.USE_WITH, currentIdTarget, currentConditions, currentEffects ) );
            }
    
            // If it is a give-to tag, store the new action in the object
            else if( qName.equals( "give-to" ) ) {
                object.addAction( new Action( Action.GIVE_TO, currentIdTarget, currentConditions, currentEffects ) );
            }
            
            // Reset the current string
            currentString = new StringBuffer( );
        }
        
        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );
            
            // If the condition tag is being closed
            if ( qName.equals( "condition" ) ) {
                // Store the conditions in the resources
                if( reading == READING_RESOURCES )
                    currentResources.setConditions( currentConditions );
                
                // Switch state
                subParsing = SUBPARSING_NONE;
            }
        }
        
        // If an effect is being subparsed
        else if( subParsing == SUBPARSING_EFFECT ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );
            
            // If the effect tag is being closed, switch the state
            if ( qName.equals( "effect" ) ) {
                subParsing = SUBPARSING_NONE;
            }
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) {
        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );
        
        // If it is reading an effect or a condition, spread the call
        else
            subParser.characters( buf, offset, len );
    }
}
