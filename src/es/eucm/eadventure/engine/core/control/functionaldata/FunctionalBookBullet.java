/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.engine.core.gui.GUI;
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
        //while there is still text to be process
        while( !text.equals( "" ) ) {
            //get the first char
            char c = text.charAt( 0 );
            //and the rest of the text (without that char)
            text = text.substring( 1 );
            //If the first char is a new line
            if( c == '\n' ) {
                // get the width of the line and the word
                Rectangle2D r = GUI.getInstance( ).getFrame( ).getFont( ).getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
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
                Rectangle2D r = GUI.getInstance( ).getFrame( ).getFont( ).getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
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
                word = word + c;
            }
        }

        //All the text has been process except the last line and last word
        Rectangle2D r = GUI.getInstance( ).getFrame( ).getFont( ).getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
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
                g.drawImage( imgBullet, xIni, y + 4, null );
            }
            //the the string
            String line = textLines.get( i );
            g.drawString( line, x, y + FunctionalTextBook.LINE_HEIGHT );

            //add the line height to the Y coordinate for the next line
            y = y + FunctionalTextBook.LINE_HEIGHT;

            /*if( i == 0 + FunctionalBook.TEXT_LINES ) {
                x = FunctionalBook.TEXT_X_2+(FunctionalBook.TEXT_WIDTH-FunctionalBook.TEXT_WIDTH_BULLET);
                y = FunctionalBook.TEXT_Y;
            }*/
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
