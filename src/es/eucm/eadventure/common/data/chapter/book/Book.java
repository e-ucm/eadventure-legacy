package es.eucm.eadventure.common.data.chapter.book;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * This class holds a "bookscene" data
 */
public class Book implements Cloneable, Documented {

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
	 * Documentation of the book.
	 */
	private String documentation;

	/**
	 * Set of resources for the book
	 */
	private List<Resources> resources;

	/**
	 * Paragraphs of the book
	 */
	private List<BookParagraph> paragraphs;

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
	 * 
	 * @param id
	 *            the id of the book
	 */
	public Book( String id ) {
		this.id = id;
		this.type=TYPE_PARAGRAPHS;
		resources = new ArrayList<Resources>( );
		paragraphs = new ArrayList<BookParagraph>( );
		pages = new ArrayList<BookPage>();
	}

	/**
	 * Returns the book's id
	 * 
	 * @return the book's id
	 */
	public String getId( ) {
		return id;
	}

	/**
	 * Returns the documentation of the book.
	 * 
	 * @return the documentation of the book
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * Returns the book's list of resources
	 * 
	 * @return the book's list of resources
	 */
	public List<Resources> getResources( ) {
		return resources;
	}

	/**
	 * Adds some resources to the list of resources
	 * 
	 * @param resources
	 *            the resources to add
	 */
	public void addResources( Resources resources ) {
		this.resources.add( resources );
	}

	/**
	 * Returns the book's set of paragraphs
	 * 
	 * @return The book's set of paragraphs
	 */
	public List<BookParagraph> getParagraphs( ) {
		return paragraphs;
	}

    /**
     * Adds a page to the book with margin
     * @param page New page (url) to be added
     */

    public void addPage( String uri, int type, int margin, boolean scrollable ){
        pages.add( new BookPage(uri,type,margin,scrollable) );
    }

    /**
     * Adds a page to the book with margin
     * @param page New page (url) to be added
     */

    public void addPage( String uri, int type, int margin, int marginEnd, int marginTop, int marginBottom, boolean scrollable ){
        pages.add( new BookPage(uri,type,margin,marginEnd,marginTop,marginBottom,scrollable) );
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
	
	/**
	 * Sets the a new identifier for the book.
	 * 
	 * @param id
	 *            New identifier
	 */
	public void setId( String id ) {
		this.id = id;
	}

	/**
	 * Changes the documentation of this book.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Adds a paragraph to the book
	 * 
	 * @param paragraph
	 *            New paragraph to be added
	 */
	public void addParagraph( BookParagraph paragraph ) {
		paragraphs.add( paragraph );
	}

	public Object clone() throws CloneNotSupportedException {
		Book b = (Book) super.clone();
		b.documentation = (documentation != null ? new String(documentation) : null);
		b.id = (id != null ? new String(id) : null);
		if (pages != null) {
			b.pages = new ArrayList<BookPage>();
			for (BookPage bp : pages)
				b.pages.add((BookPage) bp.clone());
		}
		if (paragraphs != null) {
			b.paragraphs = new ArrayList<BookParagraph>();
			for (BookParagraph bp : paragraphs)
				b.paragraphs.add((BookParagraph) bp.clone());			
		}
		if (resources != null) {
			b.resources = new ArrayList<Resources>();
			for (Resources r : resources)
				b.resources.add((Resources) r.clone());
		}
		b.type = type;
		return b;
	}
}
