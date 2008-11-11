package es.eucm.eadventure.engine.core.data.gamedata.book;

/**
 * Main class of the paragraphs of one book
 */
public abstract class BookParagraph {
    
    /**
     * Type for title paragraph
     */
    public static final int TITLE = 0;
    
    /**
     * Type for bullet paragraph
     */
    public static final int BULLET = 1;
    
    /**
     * Type for image paragraph
     */
    public static final int IMAGE = 2;
    
    /**
     * Type for text paragraph
     */
    public static final int TEXT = 3;
    
    /**
     * Type of the paragraph
     */
    private int type;
    
    /**
     * Constructor
     * @param type The type of the paragraph
     */
    public BookParagraph( int type ) {
        this.type = type;
    }
    
    /**
     * Returns the type of the paragraph
     * @return Paragraph's type
     */
    public int getType() {
        return type;
    }
    
}
