package es.eucm.eadventure.engine.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.engine.core.data.gamedata.GameData;
import es.eucm.eadventure.engine.core.data.gamedata.book.Book;
import es.eucm.eadventure.engine.core.data.gamedata.book.BookBullet;
import es.eucm.eadventure.engine.core.data.gamedata.book.BookImage;
import es.eucm.eadventure.engine.core.data.gamedata.book.BookPage;
import es.eucm.eadventure.engine.core.data.gamedata.book.BookText;
import es.eucm.eadventure.engine.core.data.gamedata.book.BookTitle;
import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Asset;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;

/**
 * Class to subparse books
 */
public class BookSubParser extends SubParser {
    
    /* Attributes */

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * The book being read
     */
    private Book book;

    /**
     * Current resources being read
     */
    private Resources currentResources;

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
    public BookSubParser( GameData gameData ) {
        super( gameData );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            
            // If it is a book tag, store the id of the book
            if( qName.equals( "book" ) ) {
                String bookId = "";

                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getQName( i ).equals( "id" ) )
                        bookId = attrs.getValue( i );

                book = new Book( bookId );
            }

            // If it is a resources tag, create the new resources
            else if( qName.equals( "resources" ) ) {
                currentResources = new Resources( );
            }

            // If it is a condition tag, create a new subparser
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                conditionSubParser = new ConditionSubParser( currentConditions, gameData );
                subParsing = SUBPARSING_CONDITION;
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

            else if ( qName.equals( "text" )){
                book.setType( Book.TYPE_PARAGRAPHS );
            }
            
            else if ( qName.equals( "pages" )){
                book.setType( Book.TYPE_PAGES );
            }
           
          
            else if (qName.equals( "page" )){
                String uri = "";
                int type = BookPage.TYPE_URL;
                int margin =0;
                boolean scrollable =false;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "uri" ) )
                        uri = attrs.getValue( i );
                    
                    if( attrs.getQName( i ).equals( "type" ) )
                        if ( attrs.getValue( i ).equals( "resource" ))
                            type = BookPage.TYPE_RESOURCE;
                    
                    if( attrs.getQName( i ).equals( "scrollable" ) )
                        if ( attrs.getValue( i ).equals( "yes" ))
                            scrollable = true;
                    
                    if( attrs.getQName( i ).equals( "margin" ) )
                        try{
                            margin = Integer.parseInt( attrs.getValue( i ) );
                        }catch(Exception e){ }
                        
                }
                book.addPage( uri, type, margin, scrollable );
                
            }

            // If it is a title or bullet tag, store the previous text in the book
            else if( qName.equals( "title" ) || qName.equals( "bullet" ) ) {
                // Add the new text paragraph
                if( currentString != null && currentString.toString( ).trim( ).replace( "\t", "" ).replace( "\n", "" ).length( )>0 )
                    book.addParagraph( new BookText( currentString.toString( ).trim( ).replace( "\t", "" ) ) );
                currentString = new StringBuffer();
            }
            
            // If it is an image tag, store the image in the book
            else if( qName.equals( "img" ) ) {
                
                // Add the new text paragraph
                if( currentString != null )
                    book.addParagraph( new BookText( currentString.toString( ).trim( ).replace( "\t", "" ) ) );                
                
                String path = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "src" ) )
                        path = attrs.getValue( i );
                }
                
                // Add the new image paragraph
                book.addParagraph( new BookImage( path ) );
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
            
            // If it is a book tag, add the book to the game data
            if( qName.equals( "book" ) ) {
                gameData.addBook( book );
            }

            // If it is a resources tag, add the resources to the book
            else if( qName.equals( "resources" ) ) {
                book.addResources( currentResources );
            }

            // If it is a text tag, add the text to the book
            else if( qName.equals( "text" ) ) {
                // Add the new text paragraph
                if( currentString != null )
                    book.addParagraph( new BookText( currentString.toString( ).trim( ).replace( "\t", "" ) ) );
            }
            
            // If it is a title tag, add the text to the book
            else if( qName.equals( "title" ) ) {
                // Add the new title paragraph
                if( currentString != null )
                    book.addParagraph( new BookTitle( currentString.toString( ).trim( ).replace( "\t", "" ) ) );
            }
            
            // If it is a bullet tag, add the text to the book
            else if( qName.equals( "bullet" ) ) {
                // Add the new bullet paragraph
                if( currentString != null )
                    book.addParagraph( new BookBullet( currentString.toString( ).trim( ).replace( "\t", "" ) ) );
            }
            
            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            
            // Spread the end element call
            conditionSubParser.endElement( namespaceURI, sName, qName );

            // If the condition is being closed, add the conditions to the resources
            if( qName.equals( "condition" ) ) {
                currentResources.setConditions( currentConditions );
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
