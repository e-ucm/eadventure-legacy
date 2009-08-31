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
package es.eucm.eadventure.editor.gui.editdialogs.customizeguidialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.displaydialogs.ImageDialog;

/**
 * 
 * @author Javier Torrente
 */
public class CursorsPanel extends JScrollPane implements Updateable {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private static final int MAX_SPACE = 0;

    /**
     * The list of cursors
     */
    private String[] cursorTypes;

    private AdventureDataControl adventureData;

    /**
     * The text fields with the cursor paths.
     */
    private JTextField[] cursorFields;

    /**
     * The buttons with the "View" option.
     */
    private JButton[] viewButtons;

    /**
     * Constructor.
     * 
     * @param dataControl
     *            Resources data control
     */
    public CursorsPanel( AdventureDataControl adventureData ) {

        super( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        JPanel mainPanel = new JPanel( );

        //super( );
        this.adventureData = adventureData;
        this.cursorTypes = DescriptorData.getCursorTypes( );

        // Load the image for the delete content button
        Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

        mainPanel.setLayout( new GridBagLayout( ) );
        //setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cursors.Title" ) ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 2, 4, 2, 4 );

        // Create and insert a text with information about this panel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.weighty = 0;
        //resourcesPanel.add( informationTextPane, c );
        JTextPane informationTextPane = new JTextPane( );
        informationTextPane.setEditable( false );
        informationTextPane.setBackground( mainPanel.getBackground( ) );
        informationTextPane.setText( TextConstants.getText( "Cursors.Information" ) );
        JPanel informationPanel = new JPanel( );
        informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
        informationPanel.setLayout( new BorderLayout( ) );
        informationPanel.add( informationTextPane, BorderLayout.CENTER );
        mainPanel.add( informationPanel, c );

        // Create the fields
        int assetCount = cursorTypes.length;
        cursorFields = new JTextField[ assetCount ];
        viewButtons = new JButton[ assetCount ];

        // For every asset type of the resources, create and add a subpanel
        for( int i = 0; i < assetCount; i++ ) {

            // Create the panel and set the border
            JPanel assetPanel = new JPanel( );
            assetPanel.setLayout( new GridBagLayout( ) );
            assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cursor." + cursorTypes[i] + ".Description" ) ) );
            GridBagConstraints c2 = new GridBagConstraints( );
            c2.insets = new Insets( 2, 2, 2, 2 );
            c2.fill = GridBagConstraints.NONE;
            c2.weightx = 0;
            c2.weighty = 0;

            // Create the delete content button
            JButton deleteContentButton = new JButton( deleteContentIcon );
            deleteContentButton.addActionListener( new DeleteContentButtonListener( i ) );
            deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
            deleteContentButton.setToolTipText( TextConstants.getText( "Cursors.DeleteCursor" ) );
            deleteContentButton.setEnabled( adventureData.isCursorTypeAllowed( i ) );
            assetPanel.add( deleteContentButton, c2 );

            // Create the text field and insert it
            cursorFields[i] = new JTextField( MAX_SPACE );
            if( adventureData.isCursorTypeAllowed( i ) ) {
                if( adventureData.getCursorPath( i ) != null )
                    cursorFields[i].setText( adventureData.getCursorPath( i ) );
            }
            else
                cursorFields[i].setText( TextConstants.getText( "Cursors.TypeNotAllowed" ) );

            cursorFields[i].setEditable( false );
            c2.gridx = 1;
            c2.fill = GridBagConstraints.HORIZONTAL;
            c2.weightx = 1;
            assetPanel.add( cursorFields[i], c2 );

            // Create the "Select" button and insert it
            JButton selectButton = new JButton( TextConstants.getText( "Cursors.Select" ) );
            selectButton.addActionListener( new ExamineButtonListener( i ) );
            selectButton.setEnabled( adventureData.isCursorTypeAllowed( i ) );
            c2.gridx = 2;
            c2.fill = GridBagConstraints.NONE;
            c2.weightx = 0;
            assetPanel.add( selectButton, c2 );

            // Create the "View" button and insert it
            viewButtons[i] = new JButton( TextConstants.getText( "Cursors.Preview" ) );
            viewButtons[i].setEnabled( adventureData.isCursorTypeAllowed( i ) && adventureData.getCursorPath( i ) != null );
            viewButtons[i].addActionListener( new ViewButtonListener( i ) );
            c2.gridx = 3;
            assetPanel.add( viewButtons[i], c2 );

            // Add the panel
            c.gridy++;
            //resourcesPanel.add( assetPanel, c );
            assetPanel.setToolTipText( TextConstants.getText( "Cursor." + cursorTypes[i] + ".Tip" ) );
            mainPanel.add( assetPanel, c );
        }

        // Add a filler at the end
        c.gridy++;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        //add( new JFiller( ), c );
        //add( new JFiller( ), c );

        // TODO Parche, arreglar
        //resourcesPanel.setPreferredSize( new Dimension( 0, assetCount * 80 ) );
        //setPreferredSize( new Dimension( 0, assetCount * 80 ) );
        //setMaximumSize( new Dimension( 0, assetCount * 80 ) );

        // Insert the panel into the scroll
        setViewportView( mainPanel );
    }

    /**
     * This class is the listener for the "Delete content" buttons on the
     * panels.
     */
    private class DeleteContentButtonListener implements ActionListener {

        /**
         * Index of the asset.
         */
        private int assetIndex;

        /**
         * Constructor.
         * 
         * @param assetIndex
         *            Index of the asset
         */
        public DeleteContentButtonListener( int assetIndex ) {

            this.assetIndex = assetIndex;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            if( adventureData.isCursorTypeAllowed( assetIndex ) ) {
                adventureData.deleteCursor( assetIndex );
                cursorFields[assetIndex].setText( null );
                viewButtons[assetIndex].setEnabled( false );
            }
        }
    }

    /**
     * This class is the listener for the "Examine" buttons on the panels.
     */
    private class ExamineButtonListener implements ActionListener {

        /**
         * Index of the asset.
         */
        private int assetIndex;

        /**
         * Constructor.
         * 
         * @param assetIndex
         *            Index of the asset
         */
        public ExamineButtonListener( int assetIndex ) {

            this.assetIndex = assetIndex;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            adventureData.editCursorPath( assetIndex );
            if( adventureData.isCursorTypeAllowed( assetIndex ) ) {
                cursorFields[assetIndex].setText( adventureData.getCursorPath( assetIndex ) );
                viewButtons[assetIndex].setEnabled( adventureData.getCursorPath( assetIndex ) != null );
            }
        }
    }

    /**
     * This class is the listener for the "View" buttons on the panels.
     */
    private class ViewButtonListener implements ActionListener {

        /**
         * Index of the asset.
         */
        private int assetIndex;

        /**
         * Constructor.
         * 
         * @param assetIndex
         *            Index of the asset
         */
        public ViewButtonListener( int assetIndex ) {

            this.assetIndex = assetIndex;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            String assetPath = adventureData.getCursorPath( assetIndex );

            new ImageDialog( assetPath );
        }
    }

    public boolean updateFields( ) {

        // For every cursor, update the cursorPath field
        int assetCount = cursorTypes.length;
        for( int i = 0; i < assetCount; i++ ) {
            if( adventureData.isCursorTypeAllowed( i ) ) {
                if( adventureData.getCursorPath( i ) != null ) {
                    cursorFields[i].setText( adventureData.getCursorPath( i ) );
                    viewButtons[i].setEnabled( true );
                }
                else {
                    cursorFields[i].setText( null );
                    viewButtons[i].setEnabled( false );
                }

            }
            else {
                cursorFields[i].setText( TextConstants.getText( "Cursors.TypeNotAllowed" ) );
                viewButtons[i].setEnabled( false );
            }
        }
        return true;
    }

}
