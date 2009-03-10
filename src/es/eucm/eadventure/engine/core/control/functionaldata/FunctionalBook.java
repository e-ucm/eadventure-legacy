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
     * @param x the horizontal position of the mouse pointer
     * @param y the vertical position of the mouse pointer
     * @return true if the mouse is in the "next page" button, false otherwise
     */
    public boolean isInNextPage( int x, int y ) {
        return ( NEXT_PAGE_X < x ) && ( x < NEXT_PAGE_X + CHANGE_PAGE_WIDTH ) && ( NEXT_PAGE_Y < y ) && ( y < NEXT_PAGE_Y + CHANGE_PAGE_HEIGHT );
    }
    
    /**
     * Returns wheter the mouse pointer is in the "previous page" button
     * @param x the horizontal position of the mouse pointer
     * @param y the vertical position of the mouse pointer
     * @return true if the mouse is in the "previous page" button, false otherwise
     */
    public boolean isInPreviousPage( int x, int y ) {
        return ( PREVIOUS_PAGE_X < x ) && ( x < PREVIOUS_PAGE_X + CHANGE_PAGE_WIDTH ) && ( PREVIOUS_PAGE_Y < y ) && ( y < PREVIOUS_PAGE_Y + CHANGE_PAGE_HEIGHT );
    }

    /**
     * Returns the book's data (text and images)
     * @return the book's data
     */
    public Book getBook( ) {
        return book;
    }
    
    
    /**
     * Returns whether the book is in its last page
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
