package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * This is a block of text that can be put in a book scene 
 */
public class FunctionalBookText extends FunctionalBookParagraph {
    
    /**
     * The text book
     */
    private BookParagraph bookText;
    
    /**
     * The text of the book
     */
    private ArrayList<String> textLines;
    
    /**
     * Creates a new FunctionalBookText
     * @param text the text to be rendered
     */
    public FunctionalBookText( BookParagraph text ) {
        this.bookText = text;
        this.init();
    }
    
    private void init(){
        textLines = new ArrayList<String>( );

        //Get the text of the book
        String text = bookText.getContent( );
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
                Rectangle2D r = GUI.getInstance( ).getFrame( ).getFont( ).getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
                if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH ) {
                    //finish the line with the current word
                    line = line + word;
                    //add the line to the text of the bullet book
                    textLines.add( line );
                    //empy line and word
                    word = "";
                    line = "";
                } else {
                    textLines.add( line );
                    textLines.add( word.substring( 1 ) );
                    line = "";
                    word = "";
                }
            }
            //if its a white space
            else if( Character.isWhitespace( c ) ) {
                //get the width of the line and the word
                Rectangle2D r = GUI.getInstance( ).getFrame( ).getFont( ).getStringBounds( line + " " + word, new FontRenderContext( null, false, true ) );
                //if its width size don't go out of the line text width
                if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH ) {
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
        if( r.getWidth( ) < FunctionalTextBook.TEXT_WIDTH ) {
            line = line + word;
        } else {
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
        int x = xIni;
        int y = yIni;
        //for each line of the text book
        for( int i = 0; i < textLines.size( ); i++ ) {
            //draw the line string
            String line = textLines.get( i );
            g.drawString(line, x, y+FunctionalTextBook.LINE_HEIGHT);
            
            //add the line height to the Y coordinate for the next line
            y = y + FunctionalTextBook.LINE_HEIGHT;
            
            /*if( i == 0 + FunctionalBook.TEXT_LINES ) {
                x = FunctionalBook.TEXT_X_2;
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
        return textLines.size( )*FunctionalTextBook.LINE_HEIGHT;
    }

}
