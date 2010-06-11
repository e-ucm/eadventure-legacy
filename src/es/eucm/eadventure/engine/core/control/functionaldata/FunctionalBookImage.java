/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
     * 
     * @param image
     *            the image to be rendered
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
        g.drawImage( image, x, y + 5, null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#getHeight()
     */
    @Override
    public int getHeight( ) {

        //The height of the book is the height of the image
        return (int) Math.ceil( ( image.getHeight( null ) + 5 ) / (double) FunctionalTextBook.LINE_HEIGHT ) * FunctionalTextBook.LINE_HEIGHT;
    }

}
