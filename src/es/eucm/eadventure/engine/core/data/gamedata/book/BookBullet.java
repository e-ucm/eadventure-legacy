package es.eucm.eadventure.engine.core.data.gamedata.book;

/**
 * Book paragraph, containing a bullet
 */
public class BookBullet extends BookParagraph {
    
    /**
     * Text of the paragraph
     */
    private String text;
    
    /**
     * Constructor
     * @param text Text of the paragraph
     */
    public BookBullet( String text ) {
        super( BookParagraph.BULLET );
        this.text = text;
    }
    
    /**
     * Returns the text of the paragraph
     * @return Text of the paragraph
     */
    public String getText( ) {
        return text;
    }
}
