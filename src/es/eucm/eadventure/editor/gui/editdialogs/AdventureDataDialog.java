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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

/**
 * Dialog to let the user change the adventure information (title and
 * description).
 * 
 * @author Bruno Torijano Bueno
 */
public class AdventureDataDialog extends ToolManagableDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Main controller (holds the methods for the access to the adventure data).
     */
    private Controller controller;

    /**
     * Text area for the description.
     */
    private JTextArea descriptionTextArea;

    /**
     * Text field for the name.
     */
    private JTextField titleTextField;

    private JCheckBox commentariesCheckBox;

    private DocumentListener documentListener;

    /**
     * Constructor.
     */
    public AdventureDataDialog( ) {

        this( Controller.getInstance( ).getAdventureTitle( ), Controller.getInstance( ).getAdventureDescription( ), Controller.getInstance( ).isPlayTransparent( ) );
    }

    public AdventureDataDialog( String adventureTitle, String adventureDescription, boolean isPlayerTransparent ) {

        // Set the values
        super( Controller.getInstance( ).peekWindow( ), TC.get( "Adventure.Title" ), false );//, Dialog.ModalityType.APPLICATION_MODAL );
        this.controller = Controller.getInstance( );

        // Panel with the options
        JPanel guiStylesPanel = new JPanel( );
        guiStylesPanel.setLayout( new GridBagLayout( ) );
        guiStylesPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.Title" ) ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );

        // Create text field for the title
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        JPanel titlePanel = new JPanel( );
        titlePanel.setLayout( new GridLayout( ) );
        titleTextField = new JTextField( adventureTitle );
        titleTextField.addActionListener( new TitleTextFieldChangeListener( ) );
        titleTextField.addFocusListener( new TitleTextFieldChangeListener( ) );
        titlePanel.add( titleTextField );
        titlePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.AdventureTitle" ) ) );
        guiStylesPanel.add( titlePanel, c );

        // Create the text area for the description
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.gridy = 1;
        JPanel descriptionPanel = new JPanel( );
        descriptionPanel.setLayout( new GridLayout( ) );
        descriptionTextArea = new JTextArea( adventureDescription, 4, 0 );
        documentListener = new DescriptionTextAreaChangesListener( );
        descriptionTextArea.getDocument( ).addDocumentListener( documentListener );
        descriptionTextArea.setLineWrap( true );
        descriptionTextArea.setWrapStyleWord( true );
        descriptionPanel.add( new JScrollPane( descriptionTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ) );
        descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.AdventureDescription" ) ) );
        guiStylesPanel.add( descriptionPanel, c );

        //Create the info panel for the mode of the player
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.3;
        c.gridy = 2;
        JPanel playerModePanel = new JPanel( );
        playerModePanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c1 = new GridBagConstraints( );
        c1.anchor = GridBagConstraints.LINE_END;
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.weightx = 0.5;
        c1.gridx = 0;
        c1.weighty = 0.35;
        c1.gridy = 0;
        descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.AdventureDescription" ) ) );

        JTextField playerMode = null;
        JTextArea playerModeDescription = new JTextArea( );
        playerModeDescription.setEditable( false );
        playerModeDescription.setWrapStyleWord( true );
        playerModeDescription.setBackground( playerModePanel.getBackground( ) );
        playerModeDescription.setBorder( BorderFactory.createEtchedBorder( ) );
        playerModeDescription.setLineWrap( true );
        if( isPlayerTransparent ) {
            playerMode = new JTextField( TC.get( "Adventure.ModePlayerTransparent.Name" ) );
            playerModeDescription.setText( TC.get( "Adventure.ModePlayerTransparent.Description" ) );
        }
        else {
            playerMode = new JTextField( TC.get( "Adventure.ModePlayerVisible.Name" ) );
            playerModeDescription.setText( TC.get( "Adventure.ModePlayerVisible.Description" ) );
        }
        playerMode.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.CurrentPlayerMode" ) ) );
        playerMode.setEditable( false );
        c1.anchor = GridBagConstraints.LINE_START;
        playerModePanel.add( playerMode, c1 );
        c1.gridx = 0;
        c1.gridwidth = 2;
        c1.gridy = 1;
        c1.weightx = 1;
        c1.weighty = 0.65;
        c1.anchor = GridBagConstraints.CENTER;
        playerModePanel.add( playerModeDescription, c1 );
        playerModePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.PlayerMode" ) ) );
        guiStylesPanel.add( playerModePanel, c );

        // Automatic-commentaries
        JPanel commentariesPanel = new JPanel( );
        commentariesPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "MenuAdventure.Commentaries" ) ) );
        commentariesCheckBox = new JCheckBox( TC.get( "MenuAdventure.CommentariesLabel" ) );
        if( controller.isCommentaries( ) ) {
            commentariesCheckBox.setSelected( true );
        }
        else {
            commentariesCheckBox.setSelected( false );
        }
        commentariesPanel.setLayout( new GridLayout( 1, 2 ) );
        commentariesPanel.add( commentariesCheckBox );
        commentariesCheckBox.addActionListener( new CheckBoxListener( ) );
        c.gridy = 3;
        guiStylesPanel.add( commentariesPanel, c );

        // Panel with the buttons
        JPanel buttonsPanel = new JPanel( );
        JButton btnExit = new JButton( TC.get( "GeneralText.Close" ) );
        btnExit.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                setVisible( false );
                dispose( );
            }
        } );
        buttonsPanel.add( btnExit );

        // Add the principal and the buttons panel
        add( guiStylesPanel, BorderLayout.CENTER );
        add( buttonsPanel, BorderLayout.SOUTH );

        // Set size and position and show the dialog
        setSize( new Dimension( 450, 500 ) );
        setMinimumSize( new Dimension( 450, 500 ) );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    /**
     * Listener for the title text field.
     */
    private class TitleTextFieldChangeListener extends FocusAdapter implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
         */
        @Override
        public void focusLost( FocusEvent e ) {

            controller.setAdventureTitle( titleTextField.getText( ) );
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            controller.setAdventureTitle( titleTextField.getText( ) );
        }
    }

    /**
     * Listener for the text area. It checks the value of the area and updates
     * the description.
     */
    private class DescriptionTextAreaChangesListener implements DocumentListener {

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
         */
        public void changedUpdate( DocumentEvent arg0 ) {

            // Do nothing
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
         */
        public void insertUpdate( DocumentEvent arg0 ) {

            // Set the new content
            controller.setAdventureDescription( descriptionTextArea.getText( ) );
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
         */
        public void removeUpdate( DocumentEvent arg0 ) {

            // Set the new content
            controller.setAdventureDescription( descriptionTextArea.getText( ) );
        }
    }

    private class CheckBoxListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            if( commentariesCheckBox.isSelected( ) ) {
                controller.setCommentaries( true );
            }
            else {
                controller.setCommentaries( false );
            }

        }

    }

    @Override
    public boolean updateFields( ) {

        descriptionTextArea.getDocument( ).removeDocumentListener( documentListener );
        descriptionTextArea.setText( Controller.getInstance( ).getAdventureDescription( ) );
        descriptionTextArea.getDocument( ).addDocumentListener( documentListener );
        titleTextField.setText( Controller.getInstance( ).getAdventureTitle( ) );
        if( controller.isCommentaries( ) ) {
            commentariesCheckBox.setSelected( true );
        }
        else {
            commentariesCheckBox.setSelected( false );
        }
        return true;
    }
}
