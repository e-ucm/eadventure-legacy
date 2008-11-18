package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Graphics2D;
import java.awt.Image;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This is a image that can be put in a book scene
 */
public class FunctionalBookImage extends FunctionalBookParagraph {
    
    /**
     * The image book
     */
    private BookParagraph bookImage;
    
    /**
     * The image of the image book
     */
    private Image image;

    /**
     * Creates a new FunctionalBookImage
     * @param image the image to be rendered
     */
    public FunctionalBookImage( BookParagraph image ) {
        //set the book image
        this.bookImage = image;
        //and loads the image
        this.image = MultimediaManager.getInstance( ).loadImageFromZip( bookImage.getContent( ), MultimediaManager.IMAGE_SCENE );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#canBeSplitted()
     */
    @Override
    public boolean canBeSplitted( ) {
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#draw(java.awt.Graphics2D, int, int)
     */
    @Override
    public void draw( Graphics2D g, int x, int y ) {
        //This book only draw a image
        g.drawImage( image, x, y+5, null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#getHeight()
     */
    @Override
    public int getHeight( ) {
        //The height of the book is the height of the image
        return (int)Math.ceil((image.getHeight( null )+5)/(double)FunctionalTextBook.LINE_HEIGHT)*FunctionalTextBook.LINE_HEIGHT;
    }

}
