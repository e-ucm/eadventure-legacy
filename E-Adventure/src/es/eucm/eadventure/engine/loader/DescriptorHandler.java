package es.eucm.eadventure.engine.loader;

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.adventuredata.AdventureData;
import es.eucm.eadventure.common.data.chapterdata.Chapter;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * XML handler of the descriptor files 
 */
public class DescriptorHandler extends DefaultHandler {
    
    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;
    
    /**
     * Constant for reading a chapter
     */
    private static final int READING_CHAPTER = 1;
    
    
    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;
    
    /**
     * Stores the game descriptor being read
     */
    private AdventureData gameDescriptor;
    
    /**
     * Stores the element which is being read
     */
    private int reading = READING_NONE;
    
    /**
     * Chapter being currently read
     */
    private Chapter currentChapter;
    
    /**
     * Constructor
     */
    public DescriptorHandler( ) {
        currentString = new StringBuffer( );
        gameDescriptor = new AdventureData( );
    }
    
    /**
     * Returns the game descriptor read
     * @return Game descriptor
     */
    public AdventureData getGameDescriptor( ) {
        return gameDescriptor;
    }
    
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {
        
        
        // If the element is the GUI configuration, store the values
        if( qName.equals( "gui" ) ) {
            int guiType = AdventureData.GUI_TRADITIONAL;
            boolean guiCustomized = false;
            
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                // Type of the GUI
                if( attrs.getQName( i ).equals( "type" ) ) {
                    if( attrs.getValue( i ).equals( "traditional" ) )
                        guiType = AdventureData.GUI_TRADITIONAL;
                    else if( attrs.getValue( i ).equals( "contextual" ) )
                        guiType = AdventureData.GUI_CONTEXTUAL;
                }
                
                // Customized GUI
                else if( attrs.getQName( i ).equals( "customized" ) ) {
                    guiCustomized = attrs.getValue( i ).equals( "yes" );
                }
            }
            
            // Set the values
            gameDescriptor.setGUI( guiType, guiCustomized );
        }
        
        //Cursor
        if (qName.equals( "cursor" )){
            String type="";String uri="";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if (attrs.getQName( i ).equals( "type" )){
                    type=attrs.getValue( i );
                }else if (attrs.getQName( i ).equals( "uri" )){
                    uri=attrs.getValue( i );
                }
            }
            gameDescriptor.addCursor( type, uri );
        }
        
        if (qName.endsWith("automatic-commentaries")) {
            gameDescriptor.setCommentaries(true);
            GameText.SHOW_COMMENTARIES = true;
        }
        
        //If the element is the player mode, store value
        if (qName.equals( "mode" )){
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if (attrs.getQName( i ).equals( "playerTransparent" )){
                    if (attrs.getValue( i ).equals( "yes" )){
                        gameDescriptor.setPlayerMode(AdventureData.MODE_PLAYER_1STPERSON);
                    }
                    else if(attrs.getValue( i ).equals( "no" )){
                        gameDescriptor.setPlayerMode(AdventureData.MODE_PLAYER_3RDPERSON);
                    }
                }
            }
        }
        
        // If it is a chapter, create it and store the path
        else if( qName.equals( "chapter" ) ) {
            currentChapter =  new Chapter( );
            
            // Store the path of the chapter
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getQName( i ).equals( "path" ) )
                    currentChapter.setPath( attrs.getValue( i ) );
            
            // Change the state
            reading = READING_CHAPTER;
        }
        
        // If it is an adaptation file, store the path
        else if( qName.equals( "adaptation-configuration" ) ) {
            // Store the path of the adaptation file
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getQName( i ).equals( "path" ) )
                    currentChapter.setAdaptationPath( attrs.getValue( i ) );
        }
        
        // If it is an assessment file, store the path
        else if( qName.equals( "assessment-configuration" ) ) {
            // Store the path of the assessment file
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getQName( i ).equals( "path" ) )
                    currentChapter.setAssessmentPath( attrs.getValue( i ) );
        }
    }
    
    /*  
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

        // Stores the title
        if( qName.equals( "title" ) ) {
            if( reading == READING_NONE )
                gameDescriptor.setTitle( currentString.toString( ).trim( ) );
            else if( reading == READING_CHAPTER )
                currentChapter.setTitle( currentString.toString( ).trim( ) );
        }
        
        // Stores the description
        else if( qName.equals( "description" ) ) {
            if( reading == READING_NONE )
                gameDescriptor.setDescription( currentString.toString( ).trim( ) );
            else if( reading == READING_CHAPTER )
                currentChapter.setDescription( currentString.toString( ).trim( ) );
        }
        
        // Change the state if ends reading a chapter
        else if( qName.equals( "chapter" ) ) {
            // Add the new chapter and change the state
            gameDescriptor.addChapter( currentChapter );
            reading = READING_NONE;
        }
            
        // Reset the current string
        currentString = new StringBuffer( );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters( char[] buf, int offset, int len ) throws SAXException {
        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
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
