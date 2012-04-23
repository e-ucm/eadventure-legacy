/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.displaydialogs.AudioDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.ImageDialog;

/**
 * 
 * @author Eugenio Marchiori
 */
public class ButtonsPanel extends JScrollPane implements Updateable {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private static final int MAX_SPACE = 0;

    /**
     * The list of buttons
     */
    private String[] buttonTypes;

    private String[] actionTypes;

    private AdventureDataControl adventureData;

    /**
     * The text fields with the cursor paths.
     */
    private JTextField[] buttonFields;

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
    public ButtonsPanel( AdventureDataControl adventureData ) {

        super( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        JPanel mainPanel = new JPanel( );

        //super( );
        this.adventureData = adventureData;

        this.actionTypes = DescriptorData.getActionTypes( );
        this.buttonTypes = DescriptorData.getButtonTypes( );

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
        informationTextPane.setText( TC.get( "Buttons.Information" ) );
        JPanel informationPanel = new JPanel( );
        informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "GeneralText.Information" ) ) );
        informationPanel.setLayout( new BorderLayout( ) );
        informationPanel.add( informationTextPane, BorderLayout.CENTER );
        mainPanel.add( informationPanel, c );

        // Create the fields
        int assetCount = buttonTypes.length * actionTypes.length;
        buttonFields = new JTextField[ assetCount ];
        viewButtons = new JButton[ assetCount ];

        for( int j = 0; j < actionTypes.length; j++ ) {
            // For every asset type of the resources, create and add a subpanel
            JPanel buttonPanel = new JPanel( );
            buttonPanel.setLayout( new GridBagLayout( ) );
            buttonPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Button." + actionTypes[j] + ".Description" ) ) );
            GridBagConstraints c3 = new GridBagConstraints( );
            c3.insets = new Insets( 2, 4, 2, 4 );
            c3.fill = GridBagConstraints.HORIZONTAL;
            c3.weightx = 1;
            c3.gridy = 0;
            c3.weighty = 0;

            for( int i = 0; i < buttonTypes.length; i++ ) {
                int assetIndex = j * buttonTypes.length + i;

                JPanel assetPanel = null;
                if (buttonTypes[i].equals( DescriptorData.SOUND_PATH )){
                    
                    assetPanel = this.getAssetPanel( assetIndex, i, j, TC.get( "Button." + actionTypes[j] + "." + buttonTypes[i] + ".Description" ), TC.get( "Button." + actionTypes[j] + "." + buttonTypes[i] + ".Tip" ), 
                            (adventureData.getButtonPath( actionTypes[j], buttonTypes[i] )!=null?adventureData.getButtonPath( actionTypes[j], buttonTypes[i] ):TC.get( "Conversations.NoAudio" )), 
                            deleteContentIcon, TC.get( "Conversations.DeleteAudio" ), 
                            TC.get( "Conversations.Select" ), 
                            new ImageIcon("img/buttons/play16x16.png"));
                    
                } else{ 
                    assetPanel = this.getAssetPanel( assetIndex, i, j, TC.get( "Button." + actionTypes[j] + "." + buttonTypes[i] + ".Description" ), TC.get( "Button." + actionTypes[j] + "." + buttonTypes[i] + ".Tip" ), 
                            adventureData.getButtonPath( actionTypes[j], buttonTypes[i] ), 
                            deleteContentIcon, TC.get( "Buttons.DeleteButton" ), 
                            TC.get( "Buttons.Select" ), 
                            TC.get( "Buttons.Preview" ));
                }
                
                buttonPanel.add( assetPanel, c3 );
                c3.gridy++;

            }
            
            
            c.gridy++;
            mainPanel.add( buttonPanel, c );
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

    public boolean updateFields( ) {

        // For every cursor, update the cursorPath field
        for( int j = 0; j < actionTypes.length; j++ ) {
            for( int i = 0; i < buttonTypes.length; i++ ) {
                int assetIndex = j * buttonTypes.length + i;
                if (buttonTypes[i].equals( DescriptorData.SOUND_PATH )){
                    if( adventureData.getButtonPath( actionTypes[j], buttonTypes[i] ) != null ) {
                        buttonFields[assetIndex].setText( adventureData.getButtonPath( actionTypes[j], buttonTypes[i] ) );
                        viewButtons[assetIndex].setEnabled( true );
                    }
                    else {
                        buttonFields[assetIndex].setText( TC.get( "Conversations.NoAudio" ) );
                        viewButtons[assetIndex].setEnabled( false );
                    }
                    
                } else {
                    if( adventureData.getButtonPath( actionTypes[j], buttonTypes[i] ) != null ) {
                        buttonFields[assetIndex].setText( adventureData.getButtonPath( actionTypes[j], buttonTypes[i] ) );
                        viewButtons[assetIndex].setEnabled( true );
                    }
                    else {
                        buttonFields[assetIndex].setText( null );
                        viewButtons[assetIndex].setEnabled( false );
                    }
                    
                }
                
            }
        }
        return true;
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

        private int action;

        private int type;

        /**
         * Constructor.
         * 
         * @param actionType
         *            Index of the asset
         */
        public DeleteContentButtonListener( int assetIndex, int action, int type ) {

            this.action = action;
            this.type = type;
            this.assetIndex = assetIndex;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            adventureData.deleteButton( actionTypes[action], buttonTypes[type] );
            if (buttonTypes[type].equals( DescriptorData.SOUND_PATH )){
                buttonFields[assetIndex].setText( TC.get( "Conversations.NoAudio" ) );    
            } else{
                buttonFields[assetIndex].setText( null );
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

        private int action;

        private int type;

        /**
         * Constructor.
         * 
         * @param actionType
         *            Index of the asset
         */
        public ExamineButtonListener( int assetIndex, int action, int type ) {

            this.assetIndex = assetIndex;
            this.action = action;
            this.type = type;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            adventureData.editButtonPath( actionTypes[action], buttonTypes[type] );
            if( adventureData.getButtonPath( actionTypes[action], buttonTypes[type] ) != null ) {
                buttonFields[assetIndex].setText( adventureData.getButtonPath( actionTypes[action], buttonTypes[type] ) );
            } else if (buttonTypes[type].equals( DescriptorData.SOUND_PATH )){
                buttonFields[assetIndex].setText( TC.get( "Conversations.NoAudio" ) );    
            } else {
                buttonFields[assetIndex].setText( null );    
            }
            viewButtons[assetIndex].setEnabled( adventureData.getButtonPath( actionTypes[action], buttonTypes[type] ) != null );
        }
    }

    /**
     * This class is the listener for the "View" buttons on the panels.
     */
    private class ViewButtonListener implements ActionListener {

        private int action;

        private int type;

        /**
         * Constructor.
         * 
         * @param actionType
         *            Index of the asset
         */
        public ViewButtonListener( int assetIndex, int action, int type ) {

            this.action = action;
            this.type = type;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            if (buttonTypes[type].equals( DescriptorData.SOUND_PATH )){
                String audioPath = adventureData.getButtonPath( actionTypes[action], DescriptorData.SOUND_PATH );
                if (audioPath!=null)
                    new AudioDialog( audioPath );
            } else {
                String assetPath = adventureData.getButtonPath( actionTypes[action], buttonTypes[type] );
                new ImageDialog( assetPath );
            }
        }
    }
    
    private JPanel getAssetPanel(int assetIndex, int i, int j, String title, String tooltip, String path, 
            Icon deleteContentIcon, String deleteToolTip, 
            String selectText, 
            Object viewContent){
        
        // Create the panel and set the border
        JPanel assetPanel = new JPanel( );
        assetPanel.setLayout( new GridBagLayout( ) );
        assetPanel.setBorder( BorderFactory.createTitledBorder( title ) ); //MODIFICADO
        GridBagConstraints c2 = new GridBagConstraints( );
        c2.insets = new Insets( 2, 2, 2, 2 );
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        c2.weighty = 0;

        // Create the delete content button
        JButton deleteContentButton = new JButton( deleteContentIcon ); //MODIFICADO
        deleteContentButton.addActionListener( new DeleteContentButtonListener( assetIndex, j, i ) );
        deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
        deleteContentButton.setToolTipText( deleteToolTip ); //MODIFICADO
        assetPanel.add( deleteContentButton, c2 );

        // Create the text field and insert it
        buttonFields[assetIndex] = new JTextField( MAX_SPACE );
        if( path != null ) //MODIFICADO
            buttonFields[assetIndex].setText( path ); //MODIFICADO

        buttonFields[assetIndex].setEditable( false );
        c2.gridx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        assetPanel.add( buttonFields[assetIndex], c2 );

        // Create the "Select" button and insert it
        JButton selectButton = new JButton( selectText ); //MODIFICADO
        selectButton.addActionListener( new ExamineButtonListener( assetIndex, j, i ) );
        c2.gridx = 2;
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        assetPanel.add( selectButton, c2 );

        // Create the "View" button and insert it
        if (viewContent instanceof String)
            viewButtons[assetIndex] = new JButton( (String)viewContent ); //MODIFICADO
        else if (viewContent instanceof ImageIcon)
            viewButtons[assetIndex] = new JButton( (ImageIcon)viewContent ); //MODIFICADO
        else
            viewButtons[assetIndex] = new JButton( "" ); //MODIFICADO
        viewButtons[assetIndex].setEnabled( adventureData.getButtonPath( actionTypes[j], buttonTypes[i] )!=null ); //MODIFICADO
        viewButtons[assetIndex].addActionListener( new ViewButtonListener( assetIndex, j, i ) );
        c2.gridx = 3;
        assetPanel.add( viewButtons[assetIndex], c2 );

        // Add the panel
        //resourcesPanel.add( assetPanel, c );
        assetPanel.setToolTipText( tooltip );//MODIFICADO
        return assetPanel;
    }
    
}
