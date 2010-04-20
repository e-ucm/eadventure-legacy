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
 */
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.displaydialogs.StyledBookDialog;
import es.eucm.eadventure.editor.gui.editdialogs.ExportBookDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookPagePreviewPanel;

public class BookPagesPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BookDataControl dataControl;

    private JPanel pagesPanel;

    private PagesTable pagesTable;

    private JPanel previewPanelContainer;

    private JButton deleteButton;

    private JButton moveUpButton;

    private JButton moveDownButton;

    private JSplitPane infoAndPreview;

    private BookPagePreviewPanel bookPanel;

    public BookPagesPanel( BookDataControl dControl ) {

        this.dataControl = dControl;

        createPagesPanel( );

        previewPanelContainer = new JPanel( );
        previewPanelContainer.setLayout( new BorderLayout( ) );
        bookPanel = new BookPagePreviewPanel( dataControl, false, pagesTable );

        JPanel bottomPanel = new JPanel( );

        JButton previewButton = new JButton( TC.get( "Book.Preview" ) );
        previewButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                StyledBookDialog dialog = new StyledBookDialog( dataControl );
                dialog.setVisible( true );
            }
        } );

        JButton exportButton = new JButton( TC.get( "Export.Book" ) );
        exportButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                ExportBookDialog dialog = new ExportBookDialog( dataControl );
                dialog.setVisible( true );
            }
        } );

        bottomPanel.add( previewButton );
        bottomPanel.add( exportButton );

        previewPanelContainer.add( bottomPanel, BorderLayout.SOUTH );
        updateSelectedPage( );

        previewPanelContainer.setMinimumSize( new Dimension( 100, 150 ) );
        pagesPanel.setMinimumSize( new Dimension( 0, 150 ) );

        infoAndPreview = new JSplitPane( JSplitPane.VERTICAL_SPLIT, pagesPanel, previewPanelContainer );
        infoAndPreview.setOneTouchExpandable( true );
        infoAndPreview.setDividerLocation( 150 );
        infoAndPreview.setResizeWeight( 0 );
        infoAndPreview.setDividerSize( 10 );
        infoAndPreview.setContinuousLayout( true );

        setLayout( new BorderLayout( ) );
        add( infoAndPreview, BorderLayout.CENTER );

        if( dataControl.getBookPagesList( ).getSelectedPage( ) != null ) {
            int index = dataControl.getBookPagesList( ).getBookPages( ).indexOf( dataControl.getBookPagesList( ).getSelectedPage( ) );
            pagesTable.changeSelection( index, 0, false, false );
        }
    }

    public void updatePreview( ) {

        BookPage currentPage = dataControl.getBookPagesList( ).getSelectedPage( );

        if( currentPage != null ) {
            previewPanelContainer.updateUI( );
        }

    }

    private void updateSelectedPage( ) {

        int selectedPage = pagesTable.getSelectedRow( );
        dataControl.getBookPagesList( ).changeCurrentPage( selectedPage );

        // No valid row is selected
        if( selectedPage < 0 || selectedPage >= dataControl.getBookPagesList( ).getBookPages( ).size( ) ) {
            //Disable delete button
            deleteButton.setEnabled( false );
            //Disable moveUp and moveDown buttons
            moveUpButton.setEnabled( false );
            moveDownButton.setEnabled( false );
        }

        //When a page has been selected
        else {
            // Enable delete button
            deleteButton.setEnabled( true );
            //Enable moveUp and moveDown buttons when there is more than one element
            moveUpButton.setEnabled( dataControl.getBookPagesList( ).getBookPages( ).size( ) > 1 && selectedPage > 0 );
            moveDownButton.setEnabled( dataControl.getBookPagesList( ).getBookPages( ).size( ) > 1 && selectedPage < pagesTable.getRowCount( ) - 1 );

            GridBagConstraints c = new GridBagConstraints( );
            c.fill = GridBagConstraints.BOTH;
            c.ipadx = 800;
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 0.9;
            bookPanel.setCurrentBookPage( selectedPage );
            previewPanelContainer.add( bookPanel, BorderLayout.CENTER );
        }

        previewPanelContainer.updateUI( );
        previewPanelContainer.repaint( );
    }

    private void createPagesPanel( ) {

        // Create the main panel
        pagesPanel = new JPanel( );
        pagesPanel.setLayout( new BorderLayout( ) );

        // Create the table (CENTER)
        pagesTable = new PagesTable( dataControl.getBookPagesList( ), this );
        pagesTable.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mousePressed( MouseEvent e ) {

                // By default the JTable only selects the nodes with the left click of the mouse
                // With this code, we spread a new call everytime the right mouse button is pressed
                if( e.getButton( ) == MouseEvent.BUTTON3 ) {
                    // Create new event (with the left mouse button)
                    MouseEvent newEvent = new MouseEvent( e.getComponent( ), e.getID( ), e.getWhen( ), InputEvent.BUTTON1_MASK, e.getX( ), e.getY( ), e.getClickCount( ), e.isPopupTrigger( ) );

                    // Take the listeners and make the calls
                    for( MouseListener mouseListener : e.getComponent( ).getMouseListeners( ) )
                        mouseListener.mousePressed( newEvent );

                }
            }

            @Override
            public void mouseClicked( MouseEvent evt ) {

                if( evt.getButton( ) == MouseEvent.BUTTON3 ) {
                    JPopupMenu menu = getCompletePopupMenu( );
                    menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
                }
            }
        } );
        pagesTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent e ) {

                if( !e.getValueIsAdjusting( ) ) {
                    setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
                    updateSelectedPage( );
                    SwingUtilities.invokeLater( new Runnable( ) {

                        public void run( ) {

                            setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) );
                        }
                    } );
                }
            }
        } );

        pagesPanel.add( new TableScrollPane( pagesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );

        //Create the buttons panel (SOUTH)
        JPanel buttonsPanel = new JPanel( );
        JButton newButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        newButton.setContentAreaFilled( false );
        newButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        newButton.setBorder( BorderFactory.createEmptyBorder( ) );
        newButton.setToolTipText( TC.get( "BookPages.AddPage" ) );
        newButton.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseClicked( MouseEvent evt ) {

                addPage( );
            }
        } );
        deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteButton.setToolTipText( TC.get( "BookPages.DeletePage" ) );
        deleteButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                deletePage( );
            }
        } );
        moveUpButton = new JButton( new ImageIcon( "img/icons/moveNodeUp.png" ) );
        moveUpButton.setContentAreaFilled( false );
        moveUpButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveUpButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveUpButton.setToolTipText( TC.get( "BookPages.MovePageUp" ) );
        moveUpButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                movePageUp( );
            }
        } );
        moveDownButton = new JButton( new ImageIcon( "img/icons/moveNodeDown.png" ) );
        moveDownButton.setContentAreaFilled( false );
        moveDownButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveDownButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveDownButton.setToolTipText( TC.get( "BookPages.MovePageDown" ) );
        moveDownButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                movePageDown( );
            }
        } );

        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( newButton, c );
        c.gridy = 1;
        buttonsPanel.add( moveUpButton, c );
        c.gridy = 2;
        buttonsPanel.add( moveDownButton, c );
        c.gridy = 4;
        buttonsPanel.add( deleteButton, c );
        c.gridy = 3;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add( new JFiller( ), c );

        pagesPanel.add( buttonsPanel, BorderLayout.EAST );
    }

    /**
     * Returns a popup menu with all the operations.
     * 
     * @return Popup menu with all operations
     */
    public JPopupMenu getCompletePopupMenu( ) {

        //Create the menu
        JPopupMenu completePopupMenu = new JPopupMenu( );

        //Add page item
        JMenuItem addPageIt = new JMenuItem( TC.get( "TreeNode.AddElement" + Controller.BOOK_PAGE ) );
        addPageIt.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                addPage( );
            }
        } );
        completePopupMenu.add( addPageIt );

        // Separator
        completePopupMenu.addSeparator( );

        // Create and add the delete item
        JMenuItem deleteMenuItem = new JMenuItem( TC.get( "TreeNode.DeleteElement" ) );
        deleteMenuItem.setEnabled( deleteButton.isEnabled( ) );
        deleteMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                deletePage( );
            }
        } );
        completePopupMenu.add( deleteMenuItem );

        // Separator
        completePopupMenu.addSeparator( );

        // Create and add the move up and down item
        JMenuItem moveUpMenuItem = new JMenuItem( TC.get( "TreeNode.MoveElementUp" ) );
        JMenuItem moveDownMenuItem = new JMenuItem( TC.get( "TreeNode.MoveElementDown" ) );
        moveUpMenuItem.setEnabled( moveUpButton.isEnabled( ) );
        moveDownMenuItem.setEnabled( moveDownButton.isEnabled( ) );
        moveUpMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                movePageUp( );
            }
        } );
        moveDownMenuItem.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                movePageDown( );
            }
        } );
        completePopupMenu.add( moveUpMenuItem );
        completePopupMenu.add( moveDownMenuItem );

        return completePopupMenu;
    }

    private void addPage( ) {

        if( dataControl.getBookPagesList( ).addPage( ) != null ) {
            int selectedRow = pagesTable.getSelectedRow( );
            if( selectedRow != -1 ) {
                selectedRow++;
            }
            else {
                selectedRow = dataControl.getBookPagesList( ).getBookPages( ).size( ) - 1;
            }
            pagesTable.clearSelection( );
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            pagesTable.changeSelection( selectedRow, 0, false, false );
        }

    }

    private void deletePage( ) {

        BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
        BookPage bookPage = pagesDataControl.getSelectedPage( );
        int i = pagesDataControl.getIndexSelectedPage( );
        if( bookPage != null && pagesDataControl.deletePage( bookPage ) ) {
            pagesTable.clearSelection( );
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            int selectedRow = i - 1 >= 0 ? i - 1 : 0;
            pagesTable.changeSelection( selectedRow, 0, false, false );
        }
    }

    private void movePageUp( ) {

        int selectedRow = pagesTable.getSelectedRow( );
        BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
        BookPage bookPage = pagesDataControl.getSelectedPage( );
        if( bookPage != null && pagesDataControl.movePageUp( bookPage ) ) {
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            pagesTable.changeSelection( selectedRow - 1, 0, false, false );
        }
    }

    private void movePageDown( ) {

        int selectedRow = pagesTable.getSelectedRow( );
        BookPagesListDataControl pagesDataControl = dataControl.getBookPagesList( );
        BookPage bookPage = pagesDataControl.getSelectedPage( );
        if( bookPage != null && pagesDataControl.movePageDown( bookPage ) ) {
            ( (AbstractTableModel) pagesTable.getModel( ) ).fireTableDataChanged( );
            pagesTable.changeSelection( selectedRow + 1, 0, false, false );
        }
    }

    public BookDataControl getDataControl( ) {

        return dataControl;
    }
}
