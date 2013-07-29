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
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.adventure.CustomArrow;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.displaydialogs.AudioDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.ImageDialog;

/**
 * 
 * @author Javier Torrente
 */
public class InventoryPanel extends JScrollPane implements Updateable {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private static final int MAX_SPACE = 0;

    /**
     * The list of images
     */
    private String[] arrowTypes;

    private AdventureDataControl adventureData;

    /**
     * The text fields with the cursor paths.
     */
    private JTextField[] arrowFields;

    /**
     * The buttons with the "View" option.
     */
    private JButton[] viewButtons;

    /**
     * Option for "inventoryPosition" attribute in "gui" element (descriptor.xml)
     */
    private JComboBox whereInventory;

    /**
     * Constructor.
     * 
     * @param dataControl
     *            Resources data control
     */
    public InventoryPanel( AdventureDataControl adventureData ) {

        super( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        JPanel mainPanel = new JPanel( );

        this.adventureData = adventureData;

        this.arrowTypes = DescriptorData.getArrowTypes( );

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
        informationTextPane.setText( TC.get( "Inventory.Information" ) );
        JPanel informationPanel = new JPanel( );
        informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "GeneralText.Information" ) ) );
        informationPanel.setLayout( new BorderLayout( ) );
        informationPanel.add( informationTextPane, BorderLayout.CENTER );
        mainPanel.add( informationPanel, c );

        // Create the fields
        int assetCount = arrowTypes.length;
        arrowFields = new JTextField[ assetCount ];
        viewButtons = new JButton[ assetCount ];

        JPanel buttonPanel = new JPanel( );
        buttonPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c3 = new GridBagConstraints( );
        c3.insets = new Insets( 2, 4, 2, 4 );
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.weightx = 1;
        c3.gridy = 0;
        c3.weighty = 0;

        for( int i = 0; i < arrowTypes.length; i++ ) {
            
            JPanel assetPanel = null;
            if (arrowTypes[i].startsWith( DescriptorData.SOUND_PATH )){
                CustomArrow arw= null;
                
                for (CustomArrow arrow:adventureData.getArrows( )){
                    if ((( arrow.getType( ).equals( DescriptorData.NORMAL_ARROW_LEFT ) || arrow.getType( ).equals( DescriptorData.HIGHLIGHTED_ARROW_LEFT )  ) && arrowTypes[i].equals( DescriptorData.SOUND_PATH_ARROW_LEFT )) ||
                        (( arrow.getType( ).equals( DescriptorData.NORMAL_ARROW_RIGHT ) || arrow.getType( ).equals( DescriptorData.HIGHLIGHTED_ARROW_RIGHT )  ) && arrowTypes[i].equals( DescriptorData.SOUND_PATH_ARROW_RIGHT ))){
                        arw=arrow;break;
                    }
                }
                
                assetPanel = this.getAssetPanel( i, TC.get( "Inventory." + arrowTypes[i] + ".Description" ), TC.get( "Inventory." + arrowTypes[i] + ".Tip" ), 
                        (adventureData.getArrowPath( arrowTypes[i] )!=null?adventureData.getArrowPath( arrowTypes[i] ):TC.get( "Conversations.NoAudio" )), 
                        deleteContentIcon, TC.get( "Conversations.DeleteAudio" ), 
                        TC.get( "Inventory.Select" ),  
                        new ImageIcon("img/buttons/play16x16.png"));
                
            } else{ 
                assetPanel = this.getAssetPanel( i, TC.get( "Inventory." + arrowTypes[i] + ".Description" ), TC.get( "Inventory." + arrowTypes[i] + ".Tip" ), 
                        adventureData.getArrowPath( arrowTypes[i] ), 
                        deleteContentIcon, TC.get( "Buttons.DeleteButton" ), 
                        TC.get( "Inventory.Select" ), 
                        TC.get( "Inventory.Preview" ));
            }

            
            // Create the panel and set the border
            /*JPanel assetPanel = new JPanel( );
            assetPanel.setLayout( new GridBagLayout( ) );
            assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Inventory." + arrowTypes[i] + ".Description" ) ) );
            GridBagConstraints c2 = new GridBagConstraints( );
            c2.insets = new Insets( 2, 2, 2, 2 );
            c2.fill = GridBagConstraints.NONE;
            c2.weightx = 0;
            c2.weighty = 0;
        
            // Create the delete content button
            JButton deleteContentButton = new JButton( deleteContentIcon );
            deleteContentButton.addActionListener( new DeleteContentButtonListener( i ) );
            deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
            deleteContentButton.setToolTipText( TC.get( "Buttons.DeleteButton" ) );
            assetPanel.add( deleteContentButton, c2 );
        
            // Create the text field and insert it
            arrowFields[i] = new JTextField( MAX_SPACE );
            if( adventureData.getArrowPath( arrowTypes[i] ) != null )
                arrowFields[i].setText( adventureData.getArrowPath( arrowTypes[i] ) );
        
            arrowFields[i].setEditable( false );
            c2.gridx = 1;
            c2.fill = GridBagConstraints.HORIZONTAL;
            c2.weightx = 1;
            assetPanel.add( arrowFields[i], c2 );
        
            // Create the "Select" button and insert it
            JButton selectButton = new JButton( TC.get( "Inventory.Select" ) );
            selectButton.addActionListener( new ExamineButtonListener( i ) );
            c2.gridx = 2;
            c2.fill = GridBagConstraints.NONE;
            c2.weightx = 0;
            assetPanel.add( selectButton, c2 );
        
            // Create the "View" button and insert it
            viewButtons[i] = new JButton( TC.get( "Inventory.Preview" ) );
            viewButtons[i].setEnabled( adventureData.getArrowPath( arrowTypes[i] ) != null );
            viewButtons[i].addActionListener( new ViewButtonListener( i ) );
            c2.gridx = 3;
            assetPanel.add( viewButtons[i], c2 );
        
            // Add the panel
            //resourcesPanel.add( assetPanel, c );
            assetPanel.setToolTipText( TC.get( "Inventory." + arrowTypes[i] + ".Tip" ) );*/
            
            buttonPanel.add( assetPanel, c3 );
            c3.gridy++;

        }
        c.gridy++;
        mainPanel.add( buttonPanel, c );

        JPanel inventoryPosition = new JPanel( );
        inventoryPosition.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Inventory.Position" ) ) );
        inventoryPosition.setLayout( new BorderLayout( ) );

        String[] options = new String[ 6 ];
        options[0] = TC.get( "Inventory.NoInventory" );
        options[1] = TC.get( "Inventory.TopAndBottom" );
        options[2] = TC.get( "Inventory.OnlyTop" );
        options[3] = TC.get( "Inventory.OnlyBottom" );
        options[4] = TC.get( "Inventory.FixedTop" );
        options[5] = TC.get( "Inventory.FixedBottom" );        
        whereInventory = new JComboBox( options );
        whereInventory.setSelectedIndex( adventureData.getInventoryPosition( ) );
        whereInventory.addActionListener( new ComboBoxActionListener( ) );
        inventoryPosition.add( whereInventory, BorderLayout.CENTER );

        c.gridy++;
        mainPanel.add( inventoryPosition, c );

        // Add a filler at the end
        c.gridy++;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

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

            adventureData.deleteArrow( arrowTypes[assetIndex] );
            if (arrowTypes[assetIndex].equals( DescriptorData.SOUND_PATH_ARROW_LEFT ) ||
                    arrowTypes[assetIndex].equals( DescriptorData.SOUND_PATH_ARROW_RIGHT )){
                arrowFields[assetIndex].setText( TC.get( "Conversations.NoAudio" ) );
            } else {
                arrowFields[assetIndex].setText( null );
            }
            viewButtons[assetIndex].setEnabled( false );
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

            adventureData.editArrowPath( arrowTypes[assetIndex] );
            if( adventureData.getArrowPath( arrowTypes[assetIndex] ) != null ) {
                arrowFields[assetIndex].setText( adventureData.getArrowPath( arrowTypes[assetIndex] ) );
            } else if (arrowTypes[assetIndex].equals( DescriptorData.SOUND_PATH_ARROW_LEFT ) ||
                    arrowTypes[assetIndex].equals( DescriptorData.SOUND_PATH_ARROW_RIGHT )){
                arrowFields[assetIndex].setText( TC.get( "Conversations.NoAudio" ) );
            } else {
                arrowFields[assetIndex].setText( null );
            }
            viewButtons[assetIndex].setEnabled( adventureData.getArrowPath( arrowTypes[assetIndex] ) != null );
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
            if (arrowTypes[assetIndex].equals( DescriptorData.SOUND_PATH_ARROW_LEFT ) ||
                    arrowTypes[assetIndex].equals( DescriptorData.SOUND_PATH_ARROW_RIGHT )){
                String audioPath = adventureData.getArrowPath( arrowTypes[assetIndex] );
                if (audioPath!=null)
                    new AudioDialog( audioPath );
            } else {
                String assetPath = adventureData.getArrowPath( arrowTypes[assetIndex] );
                new ImageDialog( assetPath );
            }
        }
    }

    private class ComboBoxActionListener implements ActionListener {

        public void actionPerformed( ActionEvent e ) {

            adventureData.setInventoryPosition( whereInventory.getSelectedIndex( ) );
        }
    }

    public boolean updateFields( ) {

        // For every cursor, update the cursorPath field
        int assetCount = arrowTypes.length;
        for( int i = 0; i < assetCount; i++ ) {
            
            if (arrowTypes[i].startsWith( DescriptorData.SOUND_PATH )){
                if( adventureData.getArrowPath( arrowTypes[i] ) != null ) {
                    arrowFields[i].setText( adventureData.getArrowPath( arrowTypes[i] ) );
                    viewButtons[i].setEnabled( true );
                }
                else {
                    arrowFields[i].setText( TC.get( "Conversations.NoAudio" ) );
                    viewButtons[i].setEnabled( false );
                }
                
            } else {
                if( adventureData.getArrowPath( arrowTypes[i] ) != null ) {
                    arrowFields[i].setText( adventureData.getArrowPath( arrowTypes[i] ) );
                    viewButtons[i].setEnabled( true );
                }
                else {
                    arrowFields[i].setText( null );
                    viewButtons[i].setEnabled( false );
                }                
            }

        }
        whereInventory.setSelectedIndex( adventureData.getInventoryPosition( ) );
        return true;
    }
    private JPanel getAssetPanel(int i, String title, String tooltip, String path, 
            Icon deleteContentIcon, String deleteToolTip, 
            String selectText, 
            Object viewContent ){

        // Create the panel and set the border
        JPanel assetPanel = new JPanel( );
        assetPanel.setLayout( new GridBagLayout( ) );
        assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title) );
        GridBagConstraints c2 = new GridBagConstraints( );
        c2.insets = new Insets( 2, 2, 2, 2 );
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        c2.weighty = 0;
    
        // Create the delete content button
        JButton deleteContentButton = new JButton( deleteContentIcon );
        deleteContentButton.addActionListener( new DeleteContentButtonListener( i ) );
        deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
        deleteContentButton.setToolTipText( deleteToolTip );
        assetPanel.add( deleteContentButton, c2 );
    
        // Create the text field and insert it
        arrowFields[i] = new JTextField( MAX_SPACE );
        if( path != null )
            arrowFields[i].setText( path );
    
        arrowFields[i].setEditable( false );
        c2.gridx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        assetPanel.add( arrowFields[i], c2 );
    
        // Create the "Select" button and insert it
        JButton selectButton = new JButton( selectText );
        selectButton.addActionListener( new ExamineButtonListener( i ) );
        c2.gridx = 2;
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        assetPanel.add( selectButton, c2 );
    
        // Create the "View" button and insert it
        if (viewContent instanceof String)
            viewButtons[i] = new JButton( (String)viewContent );
        else if (viewContent instanceof ImageIcon)
            viewButtons[i] = new JButton( (ImageIcon)viewContent );
        else
            viewButtons[i] = new JButton( "" );
        
        viewButtons[i].setEnabled( adventureData.getArrowPath( arrowTypes[i] ) != null );
        viewButtons[i].addActionListener( new ViewButtonListener( i ) );
        c2.gridx = 3;
        assetPanel.add( viewButtons[i], c2 );
    
        // Add the panel
        //resourcesPanel.add( assetPanel, c );
        assetPanel.setToolTipText( tooltip );
        
        return assetPanel;
    }
    
    
}
