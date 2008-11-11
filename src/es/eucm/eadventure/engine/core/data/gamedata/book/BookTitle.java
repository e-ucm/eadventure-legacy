package es.eucm.eadventure.engine.core.data.gamedata.book;

/**
 * Book paragraph, containing a title
 */
public class BookTitle extends BookParagraph {
    
    /**
     * Text of the paragraph
     */
    private String text;
    
    /**
     * Constructor
     * @param text Text of the paragraph
     */
    public BookTitle( String text ) {
        super( BookParagraph.TITLE );
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
