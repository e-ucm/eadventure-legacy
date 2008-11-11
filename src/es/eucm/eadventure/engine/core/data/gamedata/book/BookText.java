package es.eucm.eadventure.engine.core.data.gamedata.book;

/**
 * Book paragraph, containing plain text
 */
public class BookText extends BookParagraph {
    
    /**
     * Text of the paragraph
     */
    private String text;
    
    /**
     * Constructor
     * @param text Text of the paragraph
     */
    public BookText( String text ) {
        super( BookParagraph.TEXT );
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
