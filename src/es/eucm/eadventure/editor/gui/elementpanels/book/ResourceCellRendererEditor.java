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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.HTMLEditDialog;

public class ResourceCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private BookPage value;

    private JTextField textField;

    private JButton createButton;

    private JButton editButton;

    private JButton selectButton;

    private JLabel validPage;

    private BookPagesListDataControl control;

    private BookPagesPanel parentPanel;

    private boolean oldValid;

    public ResourceCellRendererEditor( BookPagesListDataControl control, BookPagesPanel parentPanel ) {

        this.control = control;
        this.parentPanel = parentPanel;
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (BookPage) value2;
        return createComponent( isSelected, table.getSelectionBackground( ) );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (BookPage) value2;
        if( table.getSelectedRow( ) == row ) {
            return createComponent( isSelected, table.getSelectionBackground( ) );
        }
        validPage = new JLabel( );
        validateContentSource( );
        return new JLabel( value.getUri( ), validPage.getIcon( ), SwingConstants.LEFT );
    }

    private Component createComponent( boolean isSelected, Color color ) {

        JPanel temp = new JPanel( );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 2, 2, 0, color ) );

        temp.setLayout( new GridBagLayout( ) );

        GridBagConstraints c2 = new GridBagConstraints( );
        c2.insets = new Insets( 2, 2, 0, 0 );
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        c2.weighty = 0;

        // Create the delete content button
        validPage = new JLabel( );
        validateContentSource( );
        //		new JLabel(validateContentSource());
        validPage.setPreferredSize( new Dimension( 16, 16 ) );
        temp.add( validPage, c2 );

        // Create the text field and insert it
        c2.gridx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        if( value == null ) {
            textField = new JTextField( "" );
        }
        else
            textField = new JTextField( value.getUri( ) );
        textField.setEditable( value != null && value.getType( ) == BookPage.TYPE_URL );
        //urlListener = new URLChangeListener() ;
        textField.getDocument( ).addDocumentListener( new URLChangeListener( ) );
        temp.add( textField, c2 );

        // Create the "Select" button and insert it
        c2.gridx = 0;
        c2.gridy = 1;
        c2.gridwidth = 2;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 2;
        JPanel panel = new JPanel( );

        selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
        selectButton.addActionListener( new ExamineButtonListener( ) );
        selectButton.setEnabled( value != null && !textField.isEditable( ) );
        panel.add( selectButton );

        createButton = new JButton( TextConstants.getText( "Resources.Create" ) );
        createButton.addActionListener( new CreateButtonListener( ) );
        createButton.setEnabled( value != null && !textField.isEditable( ) && value.getType( ) != BookPage.TYPE_IMAGE );
        panel.add( createButton );

        editButton = new JButton( TextConstants.getText( "Resources.Edit" ) );
        editButton.addActionListener( new EditButtonListener( ) );
        editButton.setEnabled( value != null && !textField.isEditable( ) && textField.getText( ).length( ) > 0 && value.getType( ) != BookPage.TYPE_IMAGE );
        panel.add( editButton );

        temp.add( panel, c2 );

        return temp;
    }

    private class URLChangeListener implements DocumentListener {

        private Thread updater;

        private boolean stop = false;

        private boolean changed = false;

        private long lastUpdate = -1;

        private synchronized void setChanged( boolean changed ) {

            this.changed = changed;
        }

        private synchronized boolean hasChanged( ) {

            return changed;
        }

        private synchronized long getLastUpdate( ) {

            return lastUpdate;
        }

        private synchronized void setLastUpdate( long lastUpdate ) {

            this.lastUpdate = lastUpdate;
        }

        private synchronized boolean isStop( ) {

            return stop;
        }

        public synchronized void stop( ) {

            stop = true;
        }

        public URLChangeListener( ) {

            updater = new Thread( ) {

                @Override
                public void run( ) {

                    while( !isStop( ) ) {
                        if( getLastUpdate( ) != -1 && System.currentTimeMillis( ) - getLastUpdate( ) > 1000 && hasChanged( ) ) {
                            if( control.getSelectedPage( ).getType( ) == BookPage.TYPE_URL )
                                if( control.editURL( textField.getText( ) ) ) {
                                    validateContentSource( );
                                    setChanged( false );
                                }
                        }
                        try {
                            Thread.sleep( 5 );
                        }
                        catch( InterruptedException e ) {
                        }
                    }
                }
            };
            updater.start( );
        }

        public void changedUpdate( DocumentEvent e ) {

            //Do nothing
        }

        public void insertUpdate( DocumentEvent e ) {

            setLastUpdate( System.currentTimeMillis( ) );
            setChanged( true );
        }

        public void removeUpdate( DocumentEvent e ) {

            insertUpdate( e );
        }
    }

    /**
     * Listener for the examine button.
     */
    private class ExamineButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            if( control.getSelectedPage( ).getType( ) == BookPage.TYPE_RESOURCE || control.getSelectedPage( ).getType( ) == BookPage.TYPE_IMAGE ) {
                if( control.getSelectedPage( ).getType( ) == BookPage.TYPE_RESOURCE && control.editStyledTextAssetPath( ) ) {
                    textField.setText( control.getSelectedPage( ).getUri( ) );
                    editButton.setEnabled( true );
                    validateContentSource( );
                }
                else if( control.getSelectedPage( ).getType( ) == BookPage.TYPE_IMAGE && control.editImageAssetPath( ) ) {
                    textField.setText( control.getSelectedPage( ).getUri( ) );
                    editButton.setEnabled( true );
                    validateContentSource( );
                }
            }
        }
    }

    /**
     * Listener for the create/edit button.
     */
    private class CreateButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            String filename = null;

            filename = AssetsController.TempFileGenerator.generateTempFileAbsolutePath( "html" );
            File file = new File( filename );
            try {
                file.createNewFile( );
                AssetsController.addSingleAsset( AssetsConstants.CATEGORY_STYLED_TEXT, file.getAbsolutePath( ) );
                String uri = "assets/styledtext/" + file.getName( );
                control.getSelectedPage( ).setUri( uri );
                textField.setText( control.getSelectedPage( ).getUri( ) );
                editButton.setEnabled( true );
                parentPanel.updatePreview( );
            }
            catch( IOException exc ) {
                ReportDialog.GenerateErrorReport( exc, true, "UNKNOWERROR" );
            }
        }
    }

    /**
     * Listener for the create/edit button.
     */
    private class EditButtonListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            String filename = null;
            if( !( control.getSelectedPage( ).getUri( ) == null ) && !( control.getSelectedPage( ).getUri( ).compareTo( "" ) == 0 ) ) {
                filename = Controller.getInstance( ).getProjectFolder( ) + "/" + control.getSelectedPage( ).getUri( );
            }

            HTMLEditDialog bepg = new HTMLEditDialog( filename, null );

            File temp = new File( bepg.getHtmlEditController( ).getFilename( ) );

            String uri = "assets/styledtext/" + temp.getName( );
            control.getSelectedPage( ).setUri( uri );
            textField.setText( control.getSelectedPage( ).getUri( ) );
        }
    }

    private void validateContentSource( ) {

        ImageIcon notValid = new ImageIcon( "img/icons/deleteContent.png" );
        ImageIcon valid = new ImageIcon( "img/icons/okIcon.png" );
        if( value == null || !control.isValidPage( value ) ) {
            validPage.setIcon( notValid );
            if( value == null || value.getType( ) == BookPage.TYPE_URL )
                validPage.setToolTipText( TextConstants.getText( "BookPage.NotValidURL" ) );
            else if( value == null || value.getType( ) == BookPage.TYPE_IMAGE )
                validPage.setToolTipText( TextConstants.getText( "BookPage.NotValidImage" ) );
            else
                validPage.setToolTipText( TextConstants.getText( "BookPage.NotValidResource" ) );
            oldValid = false;
        }
        else {
            validPage.setIcon( valid );
            validPage.setToolTipText( TextConstants.getText( "BookPage.Valid" ) );
            if( !oldValid )
                parentPanel.updatePreview( );
            oldValid = true;
        }
    }
}
