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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
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
    
    private JCheckBox waitUserInteraction;

    //V1.4    
    private JCheckBox keyboardNavigationEnabled;
    
    private JTextField versionNumber;

    private JComboBox actionOnMouseClick;

    private JComboBox perspective;

    private JComboBox ignoreTargets;

    private JTextField playerMode;

    private JTextArea playerModeDescription;

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
        JPanel adventureDataPanel = new JPanel( );
        adventureDataPanel.setLayout( new GridBagLayout( ) );
        adventureDataPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Adventure.Title" ) ) );
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
        adventureDataPanel.add( titlePanel, c );

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
        adventureDataPanel.add( descriptionPanel, c );

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

        playerMode = null;
        playerModeDescription = new JTextArea( );
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
        adventureDataPanel.add( playerModePanel, c );

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
        adventureDataPanel.add( commentariesPanel, c );
        
      
        waitUserInteraction = new JCheckBox( TC.get( "Conversation.WaitUserInteraction" ), controller.isKeepShowing( ) );
        waitUserInteraction.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                controller.setKeepShowing( waitUserInteraction.isSelected( ) );
            }
        } );
        waitUserInteraction.setSelected( controller.isKeepShowing( ) );
        
        JPanel waitUserInteractionPanel = new JPanel(new BorderLayout());
        waitUserInteractionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "MenuAdventure.KeepShowing" ) ) );
        waitUserInteractionPanel.add( waitUserInteraction, BorderLayout.WEST );
        
        c.gridy = 4;
        adventureDataPanel.add(waitUserInteractionPanel, c);
        
        
        JPanel panel = new JPanel();
        panel.setLayout( new GridLayout(0,1) );
        panel.add( new JLabel( TC.get( "DefaultClickAction.Explanation" )) );
        String[] values = { TC.get( "DefaultClickAction.ShowDetails" ),
                TC.get( "DefaultClickAction.ShowActions" )};
        actionOnMouseClick = new JComboBox(values);
        switch (controller.getDefaultCursorAction()) {
            case SHOW_DETAILS:
                actionOnMouseClick.setSelectedIndex( 0 );
                break;
            case SHOW_ACTIONS:
                actionOnMouseClick.setSelectedIndex( 1 );
                break;
        }
        actionOnMouseClick.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent arg0 ) {
                controller.setDefaultCursorAction((actionOnMouseClick.getSelectedIndex( ) == 0 ?
                        DescriptorData.DefaultClickAction.SHOW_DETAILS :
                            DescriptorData.DefaultClickAction.SHOW_ACTIONS));
            }
            
        });
        panel.add( actionOnMouseClick );
        
        c.gridy = 5;
        adventureDataPanel.add( panel, c);
        

        JPanel panel2 = new JPanel();
        panel2.setLayout( new GridLayout(0,1) );
        panel2.add( new JLabel( TC.get( "Perspective.Explanation" )) );
        String[] values2 = { TC.get( "Perspective.Regular" ),
                TC.get( "Perspective.Isometric" )};
        perspective = new JComboBox(values2);
        switch (controller.getPerspective( )) {
            case REGULAR:
                perspective.setSelectedIndex( 0 );
                break;
            case ISOMETRIC:
                perspective.setSelectedIndex( 1 );
                break;
        }
        perspective.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent arg0 ) {
                controller.setPerspective((perspective.getSelectedIndex( ) == 0 ?
                        DescriptorData.Perspective.REGULAR :
                            DescriptorData.Perspective.ISOMETRIC));
            }
            
        });
        panel2.add( perspective );
        
        c.gridy++;
        adventureDataPanel.add( panel2, c);

        
        panel2 = new JPanel();
        panel2.setLayout( new GridLayout(0,1) );
        panel2.add( new JLabel( TC.get( "DragBehaviour.Explanation" )) );
        String[] values3 = { TC.get( "DragBehaviour.IgnoreNonTrargets" ),
                TC.get( "DragBehaviour.ConsiderNonTargets" )};
        ignoreTargets = new JComboBox(values3);
        switch (controller.getDragBehaviour( )) {
            case IGNORE_NON_TARGETS:
                ignoreTargets.setSelectedIndex( 0 );
                break;
            case CONSIDER_NON_TARGETS:
                ignoreTargets.setSelectedIndex( 1 );
                break;
        }
        ignoreTargets.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent arg0 ) {
                controller.setDragBehaviour((ignoreTargets.getSelectedIndex( ) == 0 ?
                        DescriptorData.DragBehaviour.IGNORE_NON_TARGETS :
                            DescriptorData.DragBehaviour.CONSIDER_NON_TARGETS));
            }
            
        });
        panel2.add( ignoreTargets );
        
        c.gridy++;
        adventureDataPanel.add( panel2, c);

        keyboardNavigationEnabled = new JCheckBox( TC.get( "MenuAdventure.KeyboardNavigationEnabled.Checkbox" ), controller.isKeyboardNavigationEnabled( ) );
        keyboardNavigationEnabled.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                controller.setKeyboardNavigation( keyboardNavigationEnabled.isSelected( ) );
            }
        } );
        keyboardNavigationEnabled.setSelected( controller.isKeyboardNavigationEnabled( ) );
        
        JPanel keyboardNavigationPanel = new JPanel(new BorderLayout());
        keyboardNavigationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "MenuAdventure.KeyboardNavigationEnabled" ) ) );
        keyboardNavigationPanel.add( keyboardNavigationEnabled, BorderLayout.WEST );
        
        c.gridy++;
        adventureDataPanel.add(keyboardNavigationPanel, c);

        JPanel versionNumberPanel = new JPanel();
        versionNumberPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "VersionNumber" ) ) );
        versionNumber = new JTextField( controller.getVersionNumber( ) );
        versionNumber.setEditable( false );
        versionNumber.setOpaque( false );
        versionNumberPanel.add( versionNumber );
        c.gridy++;
        adventureDataPanel.add(versionNumberPanel, c);
        
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
        add( adventureDataPanel, BorderLayout.CENTER );
        add( buttonsPanel, BorderLayout.SOUTH );

        // Set size and position and show the dialog
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        int w = Math.min( 600, screenSize.width-50 );
        int h = Math.min( 900, screenSize.height-50 );
        setSize( new Dimension( w, h ) );
        setMinimumSize( new Dimension( w, h ) );
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

        if (titleTextField!=null){
            titleTextField.setText( controller.getAdventureTitle( ) );
        }
        if( controller.isPlayTransparent( ) ) {
            playerMode.setText( TC.get( "Adventure.ModePlayerTransparent.Name" ) );
            playerModeDescription.setText( TC.get( "Adventure.ModePlayerTransparent.Description" ) );
        }
        else {
            playerMode.setText( TC.get( "Adventure.ModePlayerVisible.Name" ) );
            playerModeDescription.setText( TC.get( "Adventure.ModePlayerVisible.Description" ) );
        }
        if( controller.isCommentaries( ) ) {
            commentariesCheckBox.setSelected( true );
        }
        else {
            commentariesCheckBox.setSelected( false );
        }
      
        waitUserInteraction.setSelected( controller.isKeepShowing( ) );
        
        switch (controller.getDefaultCursorAction()) {
            case SHOW_DETAILS:
                actionOnMouseClick.setSelectedIndex( 0 );
                break;
            case SHOW_ACTIONS:
                actionOnMouseClick.setSelectedIndex( 1 );
                break;
        }

        switch (controller.getPerspective( )) {
            case REGULAR:
                perspective.setSelectedIndex( 0 );
                break;
            case ISOMETRIC:
                perspective.setSelectedIndex( 1 );
                break;
        }

        switch (controller.getDragBehaviour( )) {
            case IGNORE_NON_TARGETS:
                ignoreTargets.setSelectedIndex( 0 );
                break;
            case CONSIDER_NON_TARGETS:
                ignoreTargets.setSelectedIndex( 1 );
                break;
        }

        keyboardNavigationEnabled.setSelected( controller.isKeyboardNavigationEnabled( ) );
        
        versionNumber.setText( controller.getVersionNumber( ) );
        
        descriptionTextArea.getDocument( ).removeDocumentListener( documentListener );
        descriptionTextArea.setText( Controller.getInstance( ).getAdventureDescription( ) );
        descriptionTextArea.getDocument( ).addDocumentListener( documentListener );
        return true;
    }
}
