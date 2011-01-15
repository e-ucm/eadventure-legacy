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

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This is a item of a enumeration of text that can be put in a book scene
 */
public class FunctionalBookBullet extends FunctionalBookParagraph {

    /**
     * The bullet book
     */
    private BookParagraph bookBullet;

    /**
     * The text of the book
     */
    private ArrayList<String> textLines;

    /**
     * Image of the bullet
     */
    private Image imgBullet;

    /**
     * Creates a new FunctionalBookBullet
     * 
     * @param bullet
     *            the bullet book to be rendered
     */
    public FunctionalBookBullet( BookParagraph bullet ) {

        this.bookBullet = bullet;
        this.init( );
        imgBullet = MultimediaManager.getInstance( ).loadImage( "gui/bullet.png", MultimediaManager.IMAGE_SCENE );
    }

    /**
     * Init the bullet book. Take the text of the book and reformat it to be
     * ready to be render with the correct format and size.
     */
    private void init( ) {

        textLines = new ArrayList<String>( );

        //Get the text of the book
        String text = bookBullet.getContent( );
        String word = "";
        String line = "";
        Font font = new Font( "Dialog", Font.PLAIN, 18 );
        //while there is still text to be process
        while( !text.equals( "" ) ) {
            //get the first char
            char c = text.charAt( 0 );
            //and the rest of the text (without that char)
            text = text.substring( 1 );
            //If the first char is a new line
            if( c == '\n' ) {
                // get the width of the line and the word
                Rectangle2D r = font.getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
                //if its width size don't go out of the line text width
                if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH_BULLET ) {
                    //finish the line with the current word
                    line = line + word;
                    //add the line to the text of the bullet book
                    textLines.add( line );
                    //empy line and word
                    word = "";
                    line = "";
                }
                else {
                    textLines.add( line );
                    textLines.add( word.substring( 1 ) );
                    word = "";
                    line = "";
                }
            }
            //if its a white space
            else if( Character.isWhitespace( c ) ) {
                //get the width of the line and the word
                Rectangle2D r = font.getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
                //if its width size don't go out of the line text width
                if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH_BULLET ) {
                    //add the word to the line
                    line = line + word;
                    word = " ";
                }
                //if it goes out
                else {
                    //and the line to the text of the bullet book
                    textLines.add( line );
                    //the line is now the word
                    line = word.substring( 1 ) + " ";
                    word = "";
                }
            }
            //else we add it to the current word
            else {
                Rectangle2D r = font.getStringBounds( line + word + c, new FontRenderContext( null, false, true ) );
                if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH_BULLET )
                    word = word + c;
                else {
                    if( line != "" )
                        textLines.add( line );
                    line = "";
                    Rectangle2D r2 = font.getStringBounds( word + c, new FontRenderContext( null, false, true ) );
                    if( r2.getWidth( ) < FunctionalTextBook.TEXT_WIDTH_BULLET )
                        word = word + c;
                    else {
                        textLines.add( word );
                        word = "" + c;
                    }
                }
            }
        }

        //All the text has been process except the last line and last word
        Rectangle2D r = font.getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
        if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH_BULLET ) {
            line = line + word;
        }
        else {
            textLines.add( line );
            line = word.substring( 1 );
        }
        textLines.add( line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#canBeSplitted()
     */
    @Override
    public boolean canBeSplitted( ) {

        return true;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#draw(java.awt.Graphics2D, int, int)
     */
    @Override
    public void draw( Graphics2D g, int xIni, int yIni ) {

        //X and Y coordinates
        int x = xIni + ( FunctionalTextBook.TEXT_WIDTH - FunctionalTextBook.TEXT_WIDTH_BULLET );
        int y = yIni;
        //for each line of the bullet book
        for( int i = 0; i < textLines.size( ); i++ ) {
            //if its the first line, we draw the bullet
            if( i == 0 ) {
                g.drawImage( imgBullet, xIni, y - 3, null );
            }
            //the the string
            String line = textLines.get( i );
            
            if ( FunctionalTextBook.PAGE_TEXT_HEIGHT - ( y % FunctionalTextBook.PAGE_TEXT_HEIGHT ) < FunctionalTextBook.LINE_HEIGHT ){
                y += ( FunctionalTextBook.PAGE_TEXT_HEIGHT - ( y % FunctionalTextBook.PAGE_TEXT_HEIGHT ) );
            }
            
            g.drawString( line, x, y + FunctionalTextBook.LINE_HEIGHT - 9 );
            
            //add the line height to the Y coordinate for the next line
            y = y + FunctionalTextBook.LINE_HEIGHT;
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#getHeight()
     */
    @Override
    public int getHeight( ) {

        return textLines.size( ) * FunctionalTextBook.LINE_HEIGHT;
    }

}
