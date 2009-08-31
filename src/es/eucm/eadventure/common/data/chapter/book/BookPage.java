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
