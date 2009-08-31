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
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.BookPagePreviewPanel;

public class StyledBookDialog extends JDialog {

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

    private BookPagePreviewPanel previewPanel;

    private BookDataControl dataControl;

    private List<BookPage> pages;

    private Image background;

    private int currentPage;

    private int numPages;

    public StyledBookDialog( BookDataControl book ) {

        super( );
        if( book.getType( ) == Book.TYPE_PAGES ) {
            this.dataControl = book;
            currentPage = 0;
            getContentPane( ).setLayout( new BorderLayout( ) );
            this.updatePreview( );

            setResizable( false );
            setSize( new Dimension( 800, 600 ) );

            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - 800 ) / 2, ( screenSize.height - 600 ) / 2 );
            setEnabled( true );
            setVisible( true );
            setFocusable( true );
            requestFocus( );

        }
    }

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

    public boolean isInLastPage( ) {

        return currentPage == numPages - 1;
    }

    public void nextPage( ) {

        if( currentPage < numPages - 1 ) {
            currentPage++;
            previewPanel = new BookPagePreviewPanel( this, pages.get( currentPage ), background );
            getContentPane( ).removeAll( );
            getContentPane( ).add( previewPanel, BorderLayout.CENTER );
            previewPanel.updateUI( );
        }
    }

    public void previousPage( ) {

        if( currentPage > 0 ) {
            currentPage--;
            previewPanel = new BookPagePreviewPanel( this, pages.get( currentPage ), background );
            getContentPane( ).removeAll( );
            getContentPane( ).add( previewPanel, BorderLayout.CENTER );
            previewPanel.updateUI( );
        }
    }

    public void mouseClicked( int x, int y ) {

        if( isInPreviousPage( x, y ) )
            previousPage( );

        else if( isInNextPage( x, y ) ) {
            nextPage( );
        }

    }

    public void updatePreview( ) {

        pages = dataControl.getBookPagesList( ).getBookPages( );
        numPages = pages.size( );
        this.setTitle( "Preview of the book: " + dataControl.getId( ) );

        //Get the background image
        String backgroundPath = dataControl.getPreviewImage( );
        if( backgroundPath != null && backgroundPath.length( ) > 0 )
            background = AssetsController.getImage( backgroundPath );
        else
            background = null;

        if( currentPage < 0 || currentPage >= numPages )
            currentPage = 0;

        if( numPages > 0 ) {
            previewPanel = new BookPagePreviewPanel( this, pages.get( currentPage ), background );
            getContentPane( ).add( previewPanel, BorderLayout.CENTER );
        }
        else {
            getContentPane( ).add( new JPanel( ), BorderLayout.CENTER );
        }
    }
}
