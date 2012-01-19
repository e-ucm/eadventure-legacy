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
package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.auxiliar.components.TalkTextField;
import es.eucm.eadventure.editor.gui.displaydialogs.AudioDialog;

public class SpeakCharacterEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Combo box for the characters.
     */
    private JComboBox charactersComboBox;

    /**
     * Combo box with the books.
     */
    private JTextArea textArea;
    
    /**
    * Text field containing the path.
    */
   private JTextField pathTextField;

   /**
    * Button to display the actual asset.
    */
   private JButton viewButton;
   
   /**
    * Controller of the effects.
    */
   private EffectsController effectsController;
   
   /**
    * The complete audioPath String
    */
   // we cut the full string when it is too large because of layout problems
   private String completeAudiopath;

    /**
     * Constructor.
     * 
     * @param currentProperties
     *            Set of initial values
     */
    public SpeakCharacterEffectDialog( EffectsController effectsController, HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( "SpeakCharacterEffect.Title" ), false );

        this.effectsController = effectsController;
        
        // Take the list of characters
        String[] charactersArray = controller.getIdentifierSummary( ).getNPCIds( );

        // If there is at least one
        if( charactersArray.length > 0 ) {
            // Create the main panel
            JPanel mainPanel = new JPanel( );
            mainPanel.setLayout( new GridBagLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );

            // Set the border of the panel with the description
            mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "SpeakCharacterEffect.Description" ) ) ) );

            // Create and add the list of characters
            c.insets = new Insets( 2, 4, 4, 4 );
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            c.gridwidth = 4;
            charactersComboBox = new JComboBox( charactersArray );
            mainPanel.add( charactersComboBox, c );

            // Create and add the list of flags
            c.fill = GridBagConstraints.BOTH;
            c.gridy = 1;
            c.weighty = 1;
            textArea = new JTextArea( );
            textArea.setWrapStyleWord( true );
            textArea.setLineWrap( true );

            mainPanel.add( new TalkTextField( textArea ), c );
            
         // create the components for audio
            // Create the delete content button
            // Load the image for the delete content button
               
               
               Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );
               JButton deleteContentButton = new JButton( deleteContentIcon );
               deleteContentButton.addActionListener( new DeleteContentButtonActionListener( ) );
               deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
               deleteContentButton.setToolTipText( TC.get( "Resources.DeleteAsset" ) );
               c.gridy=4;
               c.gridwidth = 1;
               c.gridheight = 1;
               c.fill = GridBagConstraints.NONE;
               c.weightx = 0;
               c.weighty = 0;
               mainPanel.add( deleteContentButton, c );

               // Create the text field and insert it
               pathTextField = new JTextField( );
               pathTextField.setEditable( false );
               c.gridx = 1;
               c.fill = GridBagConstraints.HORIZONTAL;
               c.weightx = 1;
               mainPanel.add( pathTextField, c );

               // Create the "Select" button and insert it
               JButton selectButton = new JButton( TC.get( "Resources.Select" ) );
               selectButton.addActionListener( new ExamineButtonActionListener( ) );
               c.gridx = 2;
               c.fill = GridBagConstraints.NONE;
               c.weightx = 0;
               mainPanel.add( selectButton, c );

               // Create the "View" button and insert it
               viewButton = new JButton( TC.get( "Resources.PlayAsset" ) );
               viewButton.setEnabled( false );
               viewButton.addActionListener( new ViewButtonActionListener( ) );
               c.gridx = 3;
               mainPanel.add( viewButton, c );

            // Add the panel to the center
            add( mainPanel, BorderLayout.CENTER );

            // Set the defualt values (if present)
            if( currentProperties != null ) {
                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TEXT ) )
                    textArea.setText( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
                    charactersComboBox.setSelectedItem(  currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
                
                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_PATH ) ) {
                    completeAudiopath =  (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_PATH );
                    
                    if (completeAudiopath!=null){
                    if (completeAudiopath.length( ) < 25 )
                        pathTextField.setText( completeAudiopath );
                    else  
                      pathTextField.setText( "..." +  completeAudiopath.substring( completeAudiopath.length( ) - 25, completeAudiopath.length( )) );
                    }
                    viewButton.setEnabled( completeAudiopath != null && !completeAudiopath.equals( "" ) );
                }
            }

            // Set the dialog
            setResizable( false );
            setSize( 400, 300 );
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
            setVisible( true );
        }

        // If the list had no elements, show an error message
        else
            controller.showErrorDialog( getTitle( ), TC.get( "SpeakCharacterEffect.ErrorNoCharacters" ) );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_TARGET, charactersComboBox.getSelectedItem( ).toString( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_TEXT, textArea.getText( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_PATH, completeAudiopath );
    }
    
    
    /**
     * Listener for the delete content button.
     */
    private class DeleteContentButtonActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Delete the current path and disable the view button
            pathTextField.setText( null );
            completeAudiopath = null;
            viewButton.setEnabled( false );
        }
    }

    /**
     * Listener for the examine button.
     */
    private class ExamineButtonActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Ask the user for an animation
            String newPath = effectsController.selectAsset( EffectsController.ASSET_SOUND );

            // If a new value was selected, set it and enable the view button
            if( newPath != null ) {
                completeAudiopath = newPath;
                if (completeAudiopath.length( ) < 25 )
                    pathTextField.setText( completeAudiopath );
                else {
                 
                  pathTextField.setText( "..." +  completeAudiopath.substring(completeAudiopath.length( ) - 25, completeAudiopath.length( )) );
                }
                
                viewButton.setEnabled( true );
            }
        }
    }

    /**
     * Listener for the view button.
     */
    private class ViewButtonActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            new AudioDialog( completeAudiopath );
        }
    }

}
