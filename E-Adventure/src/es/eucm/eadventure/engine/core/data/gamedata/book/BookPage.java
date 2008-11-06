package es.eucm.eadventure.engine.core.data.gamedata.book;

/**
 * Main class for the Page (HTML or RTF document) of a book
 * @author Javier Torrente
 *
 */
public class BookPage {

    /**
     * The page takes the document from a url, which can be on the internet
     */
    public static final int TYPE_URL=0;

    /**
     * The page takes the document as a resource, from the zip file
     */
    public static final int TYPE_RESOURCE=1;

    /**
     * The url/resource path
     */
    private String uri;
    
    /**
     * The type of the page: {@link #TYPE_URL} or {@link #TYPE_RESOURCE}
     */
    private int type;

    /**
     * Space to be left between the border of the background image and the book page
     */
    private int margin;
    
    /**
     * Determines whether the page must be in a scroll pane (Scroll bars shown)
     */
    private boolean scrollable;

    /**
     * @return the uri
     */
    public String getUri( ) {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri( String uri ) {
        this.uri = uri;
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

    public BookPage( String uri, int type, int margin, boolean scrollable ) {
        this.uri = uri;
        this.type = type;
        this.margin = margin;
        this.scrollable = scrollable;
    }
    
    /**
     * @param uri
     * @param type
     */
    public BookPage( String uri, int type, int margin ) {
        this(uri,type,margin,false);
    }
    
    public BookPage( String uri, int type) {
        this (uri,type,0);
    }
    /**
     * @return the margin
     */
    public int getMargin( ) {
        return margin;
    }

    /**
     * @param margin the margin to set
     */
    public void setMargin( int margin ) {
        this.margin = margin;
    }
    
    public boolean getScrollable(){
        return scrollable;
    }
    
    
}
