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
package es.eucm.eadventure.common.data.chapter.book;


/**
 * Main class for the Page (HTML or RTF document) of a book
 * 
 * @author Javier Torrente
 * 
 */
public class BookPage implements Cloneable {

    /**
     * The page takes the document from a url, which can be on the internet
     */
    public static final int TYPE_URL = 0;

    /**
     * The page takes the document as a resource, from the zip file
     */
    public static final int TYPE_RESOURCE = 1;

    /**
     * The page takes the document as an image from the zip file
     */
    public static final int TYPE_IMAGE = 2;

    /**
     * String to add at the beggining of all images names to avoid problems of
     * overwriting
     */
    private static final String IMAGE_FILE_STARTER = "eAdventure_styled_text_image";

    /**
     * The url/resource path
     */
    private String uri;

    /**
     * The type of the page: {@link #TYPE_URL} or {@link #TYPE_RESOURCE}
     */
    private int type;

    /**
     * Space to be left between the border of the background image and the book
     * page
     */
    private int margin;

    private int marginEnd;

    private int marginTop;

    private int marginBottom;

    /**
     * Determines whether the page must be in a scroll pane (Scroll bars shown)
     */
    private boolean scrollable;

    /**
     * Determines whether the page have changed and it hasn't been exported to
     * its image
     */
    private boolean changed = false;

    /**
     * 
     * @param changed
     *            set the changed
     */
    public void setChanged( boolean changed ) {

        this.changed = changed;
    }

    /**
     * @return if the page has changed since last time was saved
     */
    public boolean isChanged( ) {

        return changed;
    }

    /**
     * 
     * @param withPath Determines if name must have relative path 
     * @return the name for the image representing this bookPage
     */
    public String getImageName( ) {

        try {
            if( uri != null ) {
                String fileName = "";
                /*if ( withPath ){
                    fileName += AssetsController.getCategoryFolder( AssetsController.CATEGORY_IMAGE ) + "/";
                }*/
                fileName += IMAGE_FILE_STARTER + uri.substring( uri.lastIndexOf( '/' ) + 1, uri.lastIndexOf( '.' ) );
                
                /*if ( withPath ){
                    fileName += ".png";
                }*/
                return fileName;
            }
        }
        catch( Exception e ) {

        }
        return null;
    }

    /**
     * @return the uri
     */
    public String getUri( ) {

        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri( String uri ) {

        this.uri = uri;
        setChanged( true );
    }

    /**
     * @return the type
     */
    public int getType( ) {

        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( int type ) {

        this.type = type;
        setChanged( true );
    }

    public BookPage( String uri, int type, int margin, boolean scrollable ) {

        this.uri = uri;
        this.type = type;
        this.margin = margin;
        this.scrollable = scrollable;
    }

    public BookPage( String uri, int type, int margin, int marginEnd, int marginTop, int marginBottom, boolean scrollable ) {

        this.uri = uri;
        this.type = type;
        this.margin = margin;
        this.marginEnd = marginEnd;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        this.scrollable = scrollable;
    }

    /**
     * @param uri
     * @param type
     */
    public BookPage( String uri, int type, int margin ) {

        this( uri, type, margin, false );
    }

    public BookPage( String uri, int type ) {

        this( uri, type, 0 );
    }

    /**
     * @return the margin
     */
    public int getMargin( ) {

        return margin;
    }

    /**
     * @param margin
     *            the margin to set
     */
    public void setMargin( int margin ) {

        this.margin = margin;
        setChanged( true );
    }

    /**
     * @return the margin
     */
    public int getMarginStart( ) {

        return margin;
    }

    /**
     * @param margin
     *            the margin to set
     */
    public void setMarginStart( int margin ) {

        this.margin = margin;
        setChanged( true );
    }

    /**
     * @return the marginEnd
     */
    public int getMarginEnd( ) {

        return marginEnd;
    }

    /**
     * @param marginEnd
     *            the marginEnd to set
     */
    public void setMarginEnd( int marginEnd ) {

        this.marginEnd = marginEnd;
        setChanged( true );
    }

    /**
     * @return the marginTop
     */
    public int getMarginTop( ) {

        return marginTop;
    }

    /**
     * @param marginTop
     *            the marginTop to set
     */
    public void setMarginTop( int marginTop ) {

        this.marginTop = marginTop;
        setChanged( true );
    }

    /**
     * @return the marginBottom
     */
    public int getMarginBottom( ) {

        return marginBottom;
    }

    /**
     * @param marginBottom
     *            the marginBottom to set
     */
    public void setMarginBottom( int marginBottom ) {

        this.marginBottom = marginBottom;
        setChanged( true );
    }

    public boolean getScrollable( ) {

        return scrollable;
    }

    public void setScrollable( boolean scrollable ) {

        this.scrollable = scrollable;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        BookPage bp = (BookPage) super.clone( );
        bp.margin = margin;
        bp.marginBottom = marginBottom;
        bp.marginEnd = marginEnd;
        bp.marginTop = marginTop;
        bp.scrollable = scrollable;
        bp.type = type;
        bp.uri = ( uri != null ? new String( uri ) : null );
        return bp;
    }
}
