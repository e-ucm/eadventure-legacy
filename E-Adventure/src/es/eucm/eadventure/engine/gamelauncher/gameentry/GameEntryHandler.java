package es.eucm.eadventure.engine.gamelauncher.gameentry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * SAX handler for the game entries 
 */
public class GameEntryHandler extends DefaultHandler {

    /* Attributes */

    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;
    
    /**
     * Stores if the title has been read
     */
    private boolean titleRead;
    
    /**
     * Stores if the description has been read
     */
    private boolean descriptionRead;
    
    /**
     * Information of the game
     */
    private GameEntry gameEntry;
    
    /* Methods */

    /**
     * Returns the game entry readed
     * @return The game entry
     */
    public GameEntry getGameEntry( ) {
        return gameEntry;
    }
    
    @Override
    public void startDocument( ) {
        gameEntry = new GameEntry( );
        currentString = new StringBuffer( );
        titleRead = false;
        descriptionRead = false;
    }
    
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attributes ) throws SAXException {
        // If it is an invalid tag, set the adventure as invalid
        if( qName.equals( "invalid" ) )
            gameEntry.setValid( false );
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {
        // Store the title
        if( qName.equals( "title" ) && !titleRead ) {
            gameEntry.setTitle( new String( currentString ).trim( ) );
            titleRead = true;
        }
        
        else if( qName.equals( "description" ) && !descriptionRead ) {
            gameEntry.setDescription( new String( currentString ).trim( ).replace( "\t", "" ) );
            descriptionRead = true;
        }
        
        // Reset the current string
        currentString = new StringBuffer( );
    }

    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {
        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
    }

    @Override
    public void error( SAXParseException exception ) throws SAXParseException {
        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }

    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {
        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );

        // Build and return a input stream with the file (usually the DTD)
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream( filename );
        } catch( FileNotFoundException e ) {
            e.printStackTrace( );
        }
        return new InputSource( inputStream );
    }
}
