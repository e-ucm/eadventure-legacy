/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.general.ExitLookDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class ExitLookPanel extends JPanel {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    private JCheckBox customizeText;

    private JCheckBox customizeCursor;

    private ImagePanel cursorPreviewPanel;

    private JTextField customizedText;

    private JTextField customizedCursorPath;

    private JButton editCursor;

    private JButton deleteCursor;

    private ExitLookDataControl dataControl;

    public ExitLookPanel( ExitLookDataControl dControl ) {

        this.dataControl = dControl;
        /*----------------------INFO TEXT AREA------------------------*/
        JTextPane helpText = new JTextPane( );
        helpText.setEditable( false );
        helpText.setBackground( getBackground( ) );
        helpText.setText( TC.get( "ExitLook.HelpText" ) );
        JPanel informationPanel = new JPanel( );
        informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "GeneralText.Information" ) ) );
        informationPanel.setLayout( new BorderLayout( ) );
        informationPanel.add( helpText, BorderLayout.CENTER );

        /*---------------- TEXT PANEL --------------------------*/
        JPanel textPanel = new JPanel( );
        textPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.2;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        //CheckBox
        this.customizeText = new JCheckBox( TC.get( "ExitLook.CustomizeText" ) );
        customizeText.setSelected( dataControl.isTextCustomized( ) );
        customizeText.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                customizedText.setEditable( customizeText.isSelected( ) );
                if( !customizeText.isSelected( ) ) {
                    dataControl.setExitText( null );
                    customizedText.setText( "" );
                }
                else {
                    dataControl.setExitText( customizedText.getText( ) );
                }
            }

        } );
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.25;
        c.fill = GridBagConstraints.HORIZONTAL;
        textPanel.add( customizeText, c );

        //Edit text field
        if( dataControl.isTextCustomized( ) ) {
            customizedText = new JTextField( dataControl.getCustomizedText( ) );
            customizedText.setEditable( true );
        }
        else {
            customizedText = new JTextField( "" );
            customizedText.setEditable( false );
        }
        customizedText.setBorder( BorderFactory.createEtchedBorder( ) );

        customizedText.addFocusListener( new FocusListener( ) {

            public void focusGained( FocusEvent e ) {

            }

            public void focusLost( FocusEvent e ) {

                if( customizedText.isEditable( ) ) {
                    dataControl.setExitText( customizedText.getText( ) );
                }
            }

        } );
        c.gridx = 1;
        c.gridwidth = 3;
        c.weightx = 0.75;
        textPanel.add( customizedText, c );
        textPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "ExitLook.TextPanelTitle" ) ) );

        /*---------------- Cursor PANEL --------------------------*/
        JPanel cursorPanel = new JPanel( );
        cursorPanel.setLayout( new GridBagLayout( ) );

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.2;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        this.customizeCursor = new JCheckBox( TC.get( "ExitLook.CustomizeCursor" ) );
        customizeCursor.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                if( !customizeCursor.isSelected( ) ) {
                    dataControl.invalidCursor( );
                    cursorPreviewPanel.removeImage( );
                    editCursor.setEnabled( false );
                    deleteCursor.setEnabled( false );
                    customizedCursorPath.setText( "" );
                }
                else {
                    editCursor.setEnabled( true );
                    if( dataControl.isCursorCustomized( ) ) {
                        cursorPreviewPanel.loadImage( dataControl.getCustomizedCursor( ) );
                        deleteCursor.setEnabled( true );
                        customizedCursorPath.setText( dataControl.getCustomizedCursor( ) );
                    }
                    else {
                        deleteCursor.setEnabled( false );
                    }
                }
            }

        } );

        this.editCursor = new JButton( TC.get( "Resources.Select" ) );
        editCursor.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                dataControl.editCursorPath( );
                if( dataControl.isCursorCustomized( ) && !dataControl.getCustomizedCursor( ).equals( customizedCursorPath.getText( ) ) ) {
                    customizedCursorPath.setText( dataControl.getCustomizedCursor( ) );
                    cursorPreviewPanel.loadImage( dataControl.getCustomizedCursor( ) );
                    deleteCursor.setEnabled( true );
                }
            }

        } );

        Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );
        this.deleteCursor = new JButton( deleteContentIcon );
        deleteCursor.setPreferredSize( new Dimension( 20, 20 ) );
        deleteCursor.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                dataControl.invalidCursor( );
                cursorPreviewPanel.removeImage( );
                deleteCursor.setEnabled( false );
                customizedCursorPath.setText( "" );

            }

        } );

        if( dataControl.isCursorCustomized( ) ) {
            this.cursorPreviewPanel = new ImagePanel( dataControl.getCustomizedCursor( ) );
            this.customizedCursorPath = new JTextField( dataControl.getCustomizedCursor( ) );
            customizeCursor.setSelected( true );
            editCursor.setEnabled( true );
            deleteCursor.setEnabled( true );
        }
        else {
            this.cursorPreviewPanel = new ImagePanel( );
            this.customizedCursorPath = new JTextField( "" );
            customizeCursor.setSelected( false );
            editCursor.setEnabled( false );
            deleteCursor.setEnabled( false );
        }
        customizedCursorPath.setEditable( false );

        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.weighty = 1;
        cursorPreviewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Cursor.PreviewTitle" ) ) );
        cursorPanel.add( cursorPreviewPanel, c );
        c.gridx = 2;
        c.weightx = 0;
        cursorPanel.add( customizeCursor, c );

        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 0;
        c.gridwidth = 1;
        c.weighty = 0;
        cursorPanel.add( deleteCursor, c );
        c.gridx = 1;
        c.weightx = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        cursorPanel.add( customizedCursorPath, c );
        c.gridx = 3;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        cursorPanel.add( editCursor, c );

        cursorPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "ExitLook.CursorPanelTitle" ) ) );

        // ADD BOTH PANELS
        this.setLayout( new GridBagLayout( ) );
        c = new GridBagConstraints( );
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.5;
        this.add( informationPanel, c );
        c.anchor = GridBagConstraints.NORTH;
        c.gridy = 1;
        c.weighty = 0;
        this.add( textPanel, c );
        c.gridy = 2;
        c.weighty = 0.4;
        this.add( cursorPanel, c );
        c.gridy = 3;
        c.weighty = 0.3;
        this.add( new JFiller( ), c );
    }

}
