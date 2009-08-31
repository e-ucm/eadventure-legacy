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

import es.eucm.eadventure.common.data.chapter.book.Book;

/**
 * This class manages the eGame "bookscenes".
 */

public abstract class FunctionalBook {

    /**
     * X position of the upper left corner of the next page button
     */
    public static final int NEXT_PAGE_X = 685;

    /**
     * Y position of the upper left corner of the next page button
     */
    public static final int NEXT_PAGE_Y = 475;

    /**
     * X position of the upper left corner of the previous page button
     */
    public static final int PREVIOUS_PAGE_X = 45;

    /**
     * Y position of the upper left corner of the previous page button
     */
    public static final int PREVIOUS_PAGE_Y = 475;

    /**
     * Width of the change page button
     */
    public static final int CHANGE_PAGE_WIDTH = 80;

    /**
     * Height of the change page button
     */
    public static final int CHANGE_PAGE_HEIGHT = 80;

    /**
     * Book with the information
     */
    protected Book book;

    /**
     * Current page.
     */
    protected int currentPage;

    /**
     * Number of pages.
     */
    protected int numPages;

    /**
     * Returns whether the mouse pointer is in the "next page" button
     * 
     * @param x
     *            the horizontal position of the mouse pointer
     * @param y
     *            the vertical position of the mouse pointer
     * @return true if the mouse is in the "next page" button, false otherwise
     */
    public boolean isInNextPage( int x, int y ) {

        return ( NEXT_PAGE_X < x ) && ( x < NEXT_PAGE_X + CHANGE_PAGE_WIDTH ) && ( NEXT_PAGE_Y < y ) && ( y < NEXT_PAGE_Y + CHANGE_PAGE_HEIGHT );
    }

    /**
     * Returns wheter the mouse pointer is in the "previous page" button
     * 
     * @param x
     *            the horizontal position of the mouse pointer
     * @param y
     *            the vertical position of the mouse pointer
     * @return true if the mouse is in the "previous page" button, false
     *         otherwise
     */
    public boolean isInPreviousPage( int x, int y ) {

        return ( PREVIOUS_PAGE_X < x ) && ( x < PREVIOUS_PAGE_X + CHANGE_PAGE_WIDTH ) && ( PREVIOUS_PAGE_Y < y ) && ( y < PREVIOUS_PAGE_Y + CHANGE_PAGE_HEIGHT );
    }

    /**
     * Returns the book's data (text and images)
     * 
     * @return the book's data
     */
    public Book getBook( ) {

        return book;
    }

    /**
     * Returns whether the book is in its last page
     * 
     * @return true if the book is in its last page, false otherwise
     */
    public abstract boolean isInLastPage( );

    /**
     * Changes the current page to the next one
     */
    public abstract void nextPage( );

    /**
     * Changes the current page to the previous one
     */
    public abstract void previousPage( );
}
