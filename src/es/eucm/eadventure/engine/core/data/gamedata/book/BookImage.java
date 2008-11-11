package es.eucm.eadventure.engine.core.data.gamedata.book;

/**
 * Book paragraph, containing an image
 */
public class BookImage extends BookParagraph {

    /**
     * Path of the image
     */
    private String imagePath;
    
    /**
     * Constructor
     * @param imagePath Path of the image
     */
    public BookImage( String imagePath ) {
        super( BookParagraph.IMAGE );
        this.imagePath = imagePath;
    }

    /**
     * Returns the path of the image
     * @return Path of the image
     */
    public String getImagePath( ) {
        return imagePath;
    }
    
}
