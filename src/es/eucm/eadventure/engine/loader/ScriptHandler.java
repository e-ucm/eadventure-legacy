package es.eucm.eadventure.engine.loader;

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.chapterdata.Chapter;
import es.eucm.eadventure.engine.loader.subparsers.BookSubParser;
import es.eucm.eadventure.engine.loader.subparsers.CharacterSubParser;
import es.eucm.eadventure.engine.loader.subparsers.GraphConversationSubParser;
import es.eucm.eadventure.engine.loader.subparsers.ObjectSubParser;
import es.eucm.eadventure.engine.loader.subparsers.PlayerSubParser;
import es.eucm.eadventure.engine.loader.subparsers.SceneSubParser;
import es.eucm.eadventure.engine.loader.subparsers.SlidesceneSubParser;
import es.eucm.eadventure.engine.loader.subparsers.SubParser;
import es.eucm.eadventure.engine.loader.subparsers.TimerSubParser;
import es.eucm.eadventure.engine.loader.subparsers.TreeConversationSubParser;
import es.eucm.eadventure.engine.loader.subparsers.VideosceneSubParser;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class is the handler to parse the e-Adventure XML file
 */
public class ScriptHandler extends DefaultHandler {
    
    /* Attributes */
    
    /**
     * Constant for subparsing nothing
     */
    private static final int NONE = 0;

    /**
     * Constant for subparsing scene tag
     */
    private static final int SCENE = 1;

    /**
     * Constant for subparsing videoscene tag
     */
    private static final int VIDEOSCENE = 2;

    /**
     * Constant for subparsing slidescene tag
     */
    private static final int SLIDESCENE = 3;

    /**
     * Constant for subparsing book tag
     */
    private static final int BOOK = 4;

    /**
     * Constant for subparsing object tag
     */
    private static final int OBJECT = 5;

    /**
     * Constant for subparsing player tag
     */
    private static final int PLAYER = 6;

    /**
     * Constant for subparsing character tag
     */
    private static final int CHARACTER = 7;

    /**
     * Constant for subparsing conversation tag
     */
    private static final int CONVERSATION = 8;
    
    /**
     * Constant for subparsing timer tag
     */
    private static final int TIMER = 9;
    
    /**
     * Stores the current element being parsed
     */
    private int subParsing = NONE;

    /**
     * Current subparser being used
     */
    private SubParser subParser;

    /**
     * Game data
     */
    private Chapter gameData;

    /* Methods */

    /**
     * Default constructor
     */
    public ScriptHandler( ) {
        gameData = new Chapter( );
    }

    /**
     * Returns the game data read
     * @return The game data from the XML script
     */
    public Chapter getGameData( ) {
        return gameData;
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        // If no element is being subparsed, check if we must subparse something
        if( subParsing == NONE ) {
            
            // Subparse scene
            if( qName.equals( "scene" ) ) {
                subParser = new SceneSubParser( gameData );
                subParsing = SCENE;
            }

            // Subparse videoscene
            else if( qName.equals( "videoscene" ) ) {
                subParser = new VideosceneSubParser( gameData );
                subParsing = VIDEOSCENE;
            }

            // Subparse slidescene
            else if( qName.equals( "slidescene" ) ) {
                subParser = new SlidesceneSubParser( gameData );
                subParsing = SLIDESCENE;
            }

            // Subparse book
            else if( qName.equals( "book" ) ) {
                subParser = new BookSubParser ( gameData );
                subParsing = BOOK;
            }

            // Subparse object
            else if( qName.equals( "object" ) ) {
                subParser = new ObjectSubParser( gameData );
                subParsing = OBJECT;
            }

            // Subparse player
            else if( qName.equals( "player" ) ) {
                subParser = new PlayerSubParser( gameData );
                subParsing = PLAYER;
            }

            // Subparse character
            else if( qName.equals( "character" ) ) {
                subParser = new CharacterSubParser( gameData );
                subParsing = CHARACTER;
            }

            // Subparse conversacion (tree conversation)
            else if( qName.equals( "tree-conversation" ) ) {
                subParser = new TreeConversationSubParser( gameData );
                subParsing = CONVERSATION;
            }

            // Subparse conversation (graph conversation)
            else if( qName.equals( "graph-conversation" ) ) {
                subParser = new GraphConversationSubParser( gameData );
                subParsing = CONVERSATION;
            }
            
            // Subparse timer
            else if( qName.equals( "timer" ) ) {
                subParser = new TimerSubParser( gameData );
                subParsing = TIMER;
            }
        }

        // If an element is being subparsed, spread the call
        if( subParsing != NONE ){
        	try{
        		subParser.startElement( namespaceURI, sName, qName, attrs );
        	} catch (NullPointerException e){
        		System.out.println(e.getMessage());
        	}
        }
    }

    /*	
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

        // If an element is being subparsed
        if( subParsing != NONE ) {
            
            // Spread the end element call
            subParser.endElement( namespaceURI, sName, qName );

            // If the element is not being subparsed anymore, return to normal state
            if( qName.equals( "scene" ) && subParsing == SCENE ||
                qName.equals( "videoscene" ) && subParsing == VIDEOSCENE ||
                qName.equals( "slidescene" ) && subParsing == SLIDESCENE ||
                qName.equals( "book" ) && subParsing == BOOK ||
                qName.equals( "object" ) && subParsing == OBJECT ||
                qName.equals( "player" ) && subParsing == PLAYER ||
                qName.equals( "character" ) && subParsing == CHARACTER ||
                qName.equals( "tree-conversation" ) && subParsing == CONVERSATION ||
                qName.equals( "graph-conversation" ) && subParsing == CONVERSATION  || 
                qName.equals( "timer" ) && subParsing == TIMER ) {
                subParsing = NONE;
            }
            
        }
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) throws SAXException {
        // If the SAX handler is reading an element, just spread the call to the parser
        if( subParsing != NONE ) {
            subParser.characters( buf, offset, len );
        }
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    public void error( SAXParseException exception ) throws SAXParseException {
        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }
    
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity( String publicId, String systemId ) {
        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );
        
        // Build and return a input stream with the file (usually the DTD)
        InputStream inputStream = ResourceHandler.getInstance( ).getResourceAsStream( filename );        
        return new InputSource( inputStream );
    }
}
