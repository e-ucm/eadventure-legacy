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
package es.eucm.eadventure.editor.gui.elementpanels.description;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.eucm.eadventure.common.data.HasDescriptionSound;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DescriptionController;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.SelectDescriptionSoundListener;


public class DescriptionPanel extends JPanel {

    
    
    /**
     * Max of characters to be shown in the label with the path of the audio path
     */
    private static final int PATH_MAX_LONG_TO_SHOW = 20;
    
    /**
     * Text field for the name.
     */
    private JTextField nameTextField;

    /**
     * Text field for the description.
     */
    private JTextField descriptionTextField;

    /**
     * Text field for the detailed description.
     */
    private JTextField detailedDescriptionTextField;
    
    /**
     * Button for deleting associated audio path. Name's sound 
     * goes in position 0, described's sound in 1 and detailed's sound in position 2
     */   
    private JButton[] soundDeleteButtons;

    private JPanel[] soundPanels;
    
    private JLabel[] soundLabels;
    
    /**
     * Listener for text
     */
    //used for update panels
    private DescriptionChangeListener descriptionChangeListener;
    
    private DetailedDescriptionChangeListener detailedDescriptionListener;
    
    private NameChangeListener nameChangeListener;
    
    
    private static final int SOUND_ELEMENTS = 3;
    
    private DescriptionController descriptionController;
    
    public DescriptionPanel(DescriptionController descriptionController){
        this.descriptionController = descriptionController;
        
        soundDeleteButtons = new JButton[SOUND_ELEMENTS];
        soundPanels =  new JPanel[SOUND_ELEMENTS];
        soundLabels = new JLabel[SOUND_ELEMENTS]; 
        
        setLayout( new GridBagLayout( ) );
        GridBagConstraints cDoc = new GridBagConstraints( );

        cDoc.insets = new Insets( 5, 5, 5, 5 );

        cDoc.fill = GridBagConstraints.HORIZONTAL;
        cDoc.weightx = 1;
        cDoc.weighty = 0.3;
        
        
     // gridbagconstraints for panel which contains textfield and button
        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        
        
        cDoc.gridy = 1;
        cDoc.weighty = 0;
        JPanel namePanel = new JPanel( );
        namePanel.setLayout( new GridBagLayout( ) );


        
        nameTextField = new JTextField( descriptionController.getName( ) );
        nameChangeListener = new NameChangeListener( nameTextField, descriptionController.getDescriptionData( )) ;
        nameTextField.getDocument( ).addDocumentListener(nameChangeListener );
        namePanel.add( nameTextField, c );
        namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.Name" ) ) );
        
        // add sound panel 
        String soundPath = ((HasDescriptionSound)descriptionController.getDescriptionData( )).getNameSoundPath( );
        
        JPanel soundpanel = createSoundPanel( descriptionController.getDescriptionData( ) , soundPath , HasDescriptionSound.NAME_PATH );
        c.gridwidth = 1;
        c.gridx = 2;
        c.ipadx = 10;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        namePanel.add( soundpanel, c );  
        
        add( namePanel, cDoc );
        
     // restart gridconstraints for new panel with textfield and soundpanel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;

        // Create the field for the brief description
        cDoc.gridy = 2;
        JPanel descriptionPanel = new JPanel( );
        descriptionPanel.setLayout( new GridBagLayout( ) );
        descriptionTextField = new JTextField( descriptionController.getBriefDescription( ) );
        descriptionChangeListener = new DescriptionChangeListener( descriptionTextField, descriptionController.getDescriptionData( ) ) ;
        descriptionTextField.getDocument( ).addDocumentListener(descriptionChangeListener );
        descriptionPanel.add( descriptionTextField, c );
        descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.Description" ) ) );
        
     // add sound panel
        soundPath = ((HasDescriptionSound)descriptionController.getDescriptionData( )).getDescriptionSoundPath( );
        soundpanel = createSoundPanel( descriptionController.getDescriptionData( ) , soundPath , HasDescriptionSound.DESCRIPTION_PATH );
        c.gridwidth = 1;
        c.gridx = 2;
        c.ipadx = 10;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        descriptionPanel.add( soundpanel, c );  
        
        add( descriptionPanel, cDoc );
        
     // restart gridconstraints for new panel with textfield and soundpanel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;

        // Create the field for the detailed description
        cDoc.gridy = 3;
        JPanel detailedDescriptionPanel = new JPanel( );
        detailedDescriptionPanel.setLayout( new GridBagLayout( ) );
        detailedDescriptionTextField = new JTextField( descriptionController.getDetailedDescription( ) );
        detailedDescriptionListener = new DetailedDescriptionChangeListener( detailedDescriptionTextField, descriptionController.getDescriptionData( ) ) ;
        detailedDescriptionTextField.getDocument( ).addDocumentListener( detailedDescriptionListener );
        detailedDescriptionPanel.add( detailedDescriptionTextField, c );
        detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.DetailedDescription" ) ) );
        
        //     add sound panel
        soundPath = ((HasDescriptionSound)descriptionController.getDescriptionData( )).getDetailedDescriptionSoundPath( );
        soundpanel = createSoundPanel( descriptionController.getDescriptionData( ) , soundPath , HasDescriptionSound.DETAILED_DESCRIPTION_PATH);
        c.gridwidth = 1;
        c.gridx = 2;
        c.ipadx = 10;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        detailedDescriptionPanel.add( soundpanel, c );
        
        add( detailedDescriptionPanel, cDoc );
        
    }
    
    
    private String cutLongAudioPath(String audioPath){
        
        String cutAudioPath = new String();
        
        if (audioPath!=null){
            if (audioPath.length( ) < PATH_MAX_LONG_TO_SHOW )
                return audioPath;
            else 
                return "..." +  audioPath.substring( audioPath.length( ) - PATH_MAX_LONG_TO_SHOW, audioPath.length( ));
        }
        
        return cutAudioPath;
    }
    
public JPanel createSoundPanel( HasDescriptionSound descriptionSound, String soundPath, int type){
        
        JPanel soundpanel = new JPanel( );        
        soundPanels[type] = soundpanel;
        soundpanel.setLayout( new GridBagLayout( )  );
        GridBagConstraints cPanel = new GridBagConstraints( );
        cPanel.gridx = 0;
        cPanel.gridy = 0;
        cPanel.ipadx = 10;
        cPanel.weightx = 0;
        
        JLabel label = new JLabel();
        label.setPreferredSize( new Dimension(1,1) );
        
     
        // prepare the label for the sound panel
        if (soundPath!=null && !soundPath.equals( "" )){
            ImageIcon icon = new ImageIcon( "img/icons/audio.png" );
            String[] temp = soundPath.split( "/" );
            label = new JLabel( cutLongAudioPath( temp[temp.length - 1]), icon, SwingConstants.LEFT );
        }
        else {
            ImageIcon icon = new ImageIcon( "img/icons/noAudio.png" );
            label = new JLabel( TC.get( "Conversations.NoAudio" ), icon, SwingConstants.LEFT );
        }   
        
        soundLabels[type] = label;
        
        label.setOpaque( false );
        soundpanel.add( label, cPanel );
        
        JPanel buttonPanel = new JPanel( );
        buttonPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints cButtons = new GridBagConstraints( );
        cButtons.gridx = 0;
        cButtons.gridy = 0;
        //cButtons.anchor = GridBagConstraints.NONE; 
        
        
        // prepare the buttons
        JButton selectButton = new JButton( TC.get( "Conversations.Select" ) );
        selectButton.setFocusable( false );
        selectButton.setEnabled( true );
        selectButton.addActionListener(new SelectDescriptionSoundListener(descriptionSound, type, false)  );
        selectButton.setOpaque( false );
        buttonPanel.add( selectButton, cButtons );

        cButtons.gridx = 1;
        JButton deleteButton = new JButton( new ImageIcon( "img/icons/deleteContent.png" ) );
        soundDeleteButtons[type] = deleteButton ;
        deleteButton.setToolTipText( TC.get( "Conversations.DeleteAudio" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setFocusable( false );
        deleteButton.setEnabled( soundPath != null &&  !soundPath.equals( "" ));
        deleteButton.addActionListener( new SelectDescriptionSoundListener(descriptionSound,type, true)  );
        deleteButton.setOpaque( false );
        buttonPanel.add( deleteButton, cButtons );
        buttonPanel.setOpaque( false );
        
        
        cPanel.gridx = 1;
        cPanel.anchor = GridBagConstraints.EAST;
        soundpanel.add( buttonPanel, cPanel );
        
        return soundpanel;
    }
    

       public boolean updateFields( ) {
    
           if( descriptionTextField != null ) {
               descriptionTextField.getDocument( ).removeDocumentListener( descriptionChangeListener );
               this.descriptionTextField.setText( descriptionController.getBriefDescription( ));
               descriptionTextField.getDocument( ).addDocumentListener( descriptionChangeListener );
           }
           if( detailedDescriptionTextField != null ) {
               detailedDescriptionTextField.getDocument( ).removeDocumentListener( detailedDescriptionListener );
               this.detailedDescriptionTextField.setText( descriptionController.getDetailedDescription( ) );
               detailedDescriptionTextField.getDocument( ).addDocumentListener( detailedDescriptionListener );
           }
           if( nameTextField != null ) {
               nameTextField.getDocument( ).removeDocumentListener( nameChangeListener );
               this.nameTextField.setText( descriptionController.getName( ) );
               nameTextField.getDocument( ).addDocumentListener( nameChangeListener );
           }
                
               for (int i=0;i< SOUND_ELEMENTS ; i++){ 
                  String soundName=null; 
                   switch (i){
                       case HasDescriptionSound.NAME_PATH: soundName = descriptionController.getNameSoundPath( );
                               break;
                       case HasDescriptionSound.DESCRIPTION_PATH: soundName = descriptionController.getDescriptionSoundPath( );
                               break;
                       case HasDescriptionSound.DETAILED_DESCRIPTION_PATH: soundName = descriptionController.getDetailedDescriptionSoundPath( );
                               break;
                   }  
                   
                   soundDeleteButtons[i].setEnabled( soundName != null && !soundName.equals( "" ));
                   
                   if (soundName != null && !soundName.equals( "" )){                
                       ImageIcon icon = new ImageIcon( "img/icons/audio.png" );
                       String[] temp = soundName.split( "/" );
                       soundLabels[i].setText( temp[temp.length - 1] );
                       soundLabels[i].setIcon( icon );
                   } else  {
                       ImageIcon icon = new ImageIcon( "img/icons/noAudio.png" );
                       soundLabels[i].setText( TC.get( "Conversations.NoAudio" ) );
                       soundLabels[i].setIcon( icon );
                   }
                   
                   soundPanels[i].updateUI( );
               }
           
           
           return true;


}



    
    
}
