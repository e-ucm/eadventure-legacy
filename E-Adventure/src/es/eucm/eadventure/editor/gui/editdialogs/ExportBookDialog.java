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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookPagePreviewPanel;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * Export dialog for books.
 * 
 * @author Ángel S.
 * 
 */
public class ExportBookDialog extends ToolManagableDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Book panel preview
     */
    private BookPagePreviewPanel bookPreview;

    /**
     * Book pages
     */
    private List<BookPage> pages;

    private int numPag;

    /**
     * Constructor
     * 
     * @param pages
     *            Pages forming the book
     */
    public ExportBookDialog( BookDataControl dControl ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "Export.Book" ) );
        setLayout( new BorderLayout( ) );
        this.pages = dControl.getBookPagesList( ).getBookPages( );
        bookPreview = new BookPagePreviewPanel( dControl, true );
        bookPreview.setPreferredSize( new Dimension( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT ) );
        bookPreview.setDrawArrows( false );
        add( bookPreview, BorderLayout.CENTER );
        numPag = 0;

        JButton exportButton = new JButton( TC.get( "Export.Book" ) );
        exportButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {
                while( numPag < pages.size( ) && pages.get( numPag ).getType( ) != BookPage.TYPE_RESOURCE ) {
                    numPag++;
                }
                if( numPag >= pages.size( ) )
                    ExportBookDialog.this.setVisible( false );
                else {
                    bookPreview.setCurrentBookPage( pages.get( numPag++ ), true );
                }
            }

        } );
        add( exportButton, BorderLayout.SOUTH );

        pack( );
    }

}
