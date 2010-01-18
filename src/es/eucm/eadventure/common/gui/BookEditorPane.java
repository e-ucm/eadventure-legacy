/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, availabe at http://e-adventure.e-ucm.es.
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
 */
package es.eucm.eadventure.common.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.engine.core.gui.GUI;

public class BookEditorPane extends JEditorPane {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final int REAL_WIDTH = 800;

    private final int REAL_HEIGHT = 600;

    private BookPage currentBookPage;

    private URL documentBase;

    public BookEditorPane( ) {

        this.setEditorKit( new BookHTMLEditorKit( ) );
        updateBounds( );
        setOpaque( false );
        setEditable( false );
    }

    public BookEditorPane( BookPage currentBookP ) {

        this( );
        this.currentBookPage = currentBookP;

    }

    public void setDocumentBase( URL documentBase ) {

        this.documentBase = documentBase;
    }

    public void updateBounds( ) {

        try {
            setBounds( currentBookPage.getMargin( ), currentBookPage.getMarginTop( ), GUI.WINDOW_WIDTH - currentBookPage.getMargin( ) - currentBookPage.getMarginEnd( ), GUI.WINDOW_HEIGHT - currentBookPage.getMarginTop( ) - currentBookPage.getMarginBottom( ) );
        }
        catch( NullPointerException e ) {
            setBounds( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );

        }
    }

    /**
     * 
     * @param g
     *            Graphics
     * @param x
     *            x-left-corner-position to draw in graphics
     * @param y
     *            x-left-corner-position to draw in graphics
     * @param width
     *            real width of the component
     * @param height
     *            real height of the component
     */
    public void paint( Graphics g, int x, int y, int width, int height ) {

        if( width != 0 && height != 0 ) {

            BufferedImage b = new BufferedImage( getWidth( ), getHeight( ), BufferedImage.TYPE_INT_ARGB );
            Graphics2D g2 = (Graphics2D) b.getGraphics( );
            super.paint( g2 );
            int widthScale = Math.round( ( width * getWidth( ) ) / REAL_WIDTH );
            int heightScale = ( height * getHeight( ) ) / REAL_HEIGHT;

            g.drawImage( b.getScaledInstance( widthScale, heightScale, Image.SCALE_SMOOTH ), x, y, null );
        }
    }

    @Override
    public void setText( String t ) {

        try {
            Document doc = getDocument( );
            doc.remove( 0, doc.getLength( ) );
            if( t == null || t.equals( "" ) ) {
                return;
            }
            Reader r = new StringReader( t );
            EditorKit kit = getEditorKit( );
            kit.read( r, doc, 0 );
        }
        catch( IOException ioe ) {
            UIManager.getLookAndFeel( ).provideErrorFeedback( this );
        }
        catch( BadLocationException ble ) {
            UIManager.getLookAndFeel( ).provideErrorFeedback( this );
        }
    }

    private class BookHTMLEditorKit extends HTMLEditorKit {

        private static final long serialVersionUID = 1L;

        private BookHTMLFactory factory = new BookHTMLFactory( );

        @Override
        public ViewFactory getViewFactory( ) {

            return factory;
        }

        @Override
        public void read( Reader in, Document doc, int pos ) throws IOException, BadLocationException {

            if( doc instanceof HTMLDocument ) {
                HTMLDocument hdoc = (HTMLDocument) doc;
                Parser p = getParser( );
                if( p == null ) {
                    throw new IOException( "Can't load parser" );
                }
                if( pos > doc.getLength( ) ) {
                    throw new BadLocationException( "Invalid location", pos );
                }

                ParserCallback receiver = hdoc.getReader( pos );
                //Boolean ignoreCharset = (Boolean)doc.getProperty("IgnoreCharsetDirective");
                Boolean ignoreCharset = true;
                p.parse( in, receiver, ( ignoreCharset == null ) ? false : ignoreCharset.booleanValue( ) );
                receiver.flush( );
            }
            else {
                super.read( in, doc, pos );
            }
        }

        private class BookHTMLFactory extends HTMLFactory {

            @Override
            public View create( Element elem ) {

                Object o = elem.getAttributes( ).getAttribute( StyleConstants.NameAttribute );
                if( o instanceof HTML.Tag ) {
                    HTML.Tag kind = (HTML.Tag) o;
                    if( kind == HTML.Tag.IMG )
                        return new MyImageView( elem );
                    else if( kind == HTML.Tag.LINK ) {
                        System.out.println( );
                    }
                    else if( kind == HTML.Tag.META ) {
                        return null;
                    }
                }
                return super.create( elem );
            }

            /**
             * View for HTML element. In this class, we redefine the method
             * getImageURL. Thus, we tell to HTMLFactory that right document
             * base to load the image correctly.
             * 
             * @author Ángel S.
             * 
             */
            private class MyImageView extends ImageView {

                public MyImageView( Element elem ) {

                    super( elem );
                }

                @Override
                public URL getImageURL( ) {

                    String src = (String) getElement( ).getAttributes( ).getAttribute( HTML.Attribute.SRC );
                    if( src == null ) {
                        return null;
                    }

                    URL reference = ( (HTMLDocument) getDocument( ) ).getBase( );

                    if( reference == null )
                        reference = documentBase;

                    try {
                        URL u = new URL( reference, src );
                        return u;
                    }
                    catch( MalformedURLException e ) {
                        return null;
                    }
                }

            }
        }

    }
}
