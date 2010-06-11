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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

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

                changePage( );
            }

        } );
        add( exportButton, BorderLayout.SOUTH );

        pack( );
    }

    private void changePage( ) {

        while( numPag < pages.size( ) && pages.get( numPag ).getType( ) != BookPage.TYPE_RESOURCE ) {
            numPag++;
        }
        if( numPag >= pages.size( ) )
            ExportBookDialog.this.setVisible( false );
        else {
            bookPreview.setCurrentBookPage( pages.get( numPag++ ), true );
        }
    }

    @Override
    public void setVisible( boolean visible ) {

        if( pages.size( ) > 0 ) {

            if( visible ) {
                changePage( );
                GenericOptionPaneDialog.showMessageDialog( Controller.getInstance( ).peekWindow( ), TC.get( "Export.Book" ), TC.get( "Export.Book.Info" ), JOptionPane.INFORMATION_MESSAGE );
            }

            super.setVisible( visible );
        }
        else {
            GenericOptionPaneDialog.showMessageDialog( Controller.getInstance( ).peekWindow( ), TC.get( "Export.Book" ), TC.get( "Export.Book.Error" ), JOptionPane.INFORMATION_MESSAGE );
            super.setVisible( false );
        }
    }

}
