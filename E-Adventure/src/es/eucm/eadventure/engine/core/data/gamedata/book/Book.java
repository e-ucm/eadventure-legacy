package es.eucm.eadventure.engine.core.data.gamedata.book;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;

/**
 * This class holds a "bookscene" data
 */
public class Book {
    
    /**
     * The xml tag for the background image of the bookscene
     */
    public static final String RESOURCE_TYPE_BACKGROUND = "background";
    
    
    public static final int TYPE_PARAGRAPHS=0;
    
    public static final int TYPE_PAGES=1;

    /**
     * Id of the book
     */
    private String id;

    /**
     * Set of resources for the book
     */
    private ArrayList<Resources> resources;

    /**
     * Paragraphs of the book: Used in case type is {@value #TYPE_PARAGRAPHS} 
     */
    private ArrayList<BookParagraph> paragraphs;
    
    /**
     * Pages of the book: Used in case type is {@value #TYPE_PAGES} or {@value #TYPE_SCROLL_PAGE}.
     */   
    private ArrayList<BookPage> pages; 
    
    /**
     * {@link #TYPE_PAGES}, {@link #TYPE_PARAGRAPHS} 
     */
    private int type;

    /**
     * Creates a new Book
     * @param id the id of the book
     */
    public Book( String id ) {
        this.id = id;
        this.type=TYPE_PARAGRAPHS;
        resources = new ArrayList<Resources>( );
        paragraphs = new ArrayList<BookParagraph>();
        pages = new ArrayList<BookPage>();
    }

    /**
     * Returns the book's id
     * @return the book's id
     */
    public String getId( ) {
        return id;
    }

    /**
     * Returns the book's list of resources
     * @return the book's list of resources
     */
    public ArrayList<Resources> getResources( ) {
        return resources;
    }

    /**
     * Adds some resources to the list of resources
     * @param resources the resources to add
     */
    public void addResources( Resources resources ) {
        this.resources.add( resources );
    }
    
    /**
     * Returns the book's set of paragraphs
     * @return The book's set of paragraphs
     */
    public ArrayList<BookParagraph> getParagraphs( ) {
        return paragraphs;
    }
    
    /**
     * Adds a paragraph to the book
     * @param paragraph New paragraph to be added
     */
    public void addParagraph( BookParagraph paragraph ) {
        paragraphs.add( paragraph );
    }

    /**
     * Adds a page to the book with margin
     * @param page New page (url) to be added
     */

    public void addPage( String uri, int type, int margin, boolean scrollable ){
        pages.add( new BookPage(uri,type,margin,scrollable) );
    }
    
    /**
     * Adds a page to the book
     * @param page New page (url) to be added
     */

    public void addPage( String uri, int type ){
        pages.add( new BookPage(uri,type) );
    }


    /**
     * @return the type
     */
    public int getType( ) {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType( int type ) {
        this.type = type;
    }

    /**
     * @return the pageURLs
     */
    public ArrayList<BookPage> getPageURLs( ) {
        return pages;
    }
    
}
