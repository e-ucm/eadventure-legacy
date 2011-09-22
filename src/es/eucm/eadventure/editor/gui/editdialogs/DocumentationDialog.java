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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.HasDescriptionSound;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.SelectDescriptionSoundListener;

/**
 * This class is the editing dialog for the effects. Here the user can add
 * effects to the events of the script.
 * 
 * @author Bruno Torijano Bueno
 */
public class DocumentationDialog extends ToolManagableDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private JTextField nameTextField;

    private NameChangeListener nameChangeListener;

    /**
     * Text field for the description.
     */
    private JTextField descriptionTextField;

    /**
     * Text field for the detailed description.
     */
    private JTextField detailedDescriptionTextField;

    private DataControl dataControl;

    private DescriptionChangeListener descriptionChangeListener;

    private DocumentListener detailedDescriptionListener;
    
    /**
     * Button for deleting associated audio path. Name's sound 
     * goes in position 0, described's sound in 1 and detailed's sound in position 2
     */   
    private JButton[] soundDeleteButtons;

    private JPanel[] soundPanels;
    
    private JLabel[] soundLabels;
    
    private static final int SOUND_ELEMENTS = 3;
    
    /**
     * Constructor.
     * 
     * @param effectsController
     *            Controller for the conditions
     */
    public DocumentationDialog( DataControl dataControl ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "ActiveAreasList.Documentation" ), false );//, Dialog.ModalityType.APPLICATION_MODAL );
        this.dataControl = dataControl;
        
        soundDeleteButtons = new JButton[SOUND_ELEMENTS];
        soundPanels =  new JPanel[SOUND_ELEMENTS];
        soundLabels = new JLabel[SOUND_ELEMENTS];
        
        setLayout( new GridBagLayout( ) );
        GridBagConstraints cDoc = new GridBagConstraints( );

        cDoc.fill = GridBagConstraints.HORIZONTAL;
        cDoc.weightx = 1;
        cDoc.weighty = 0;
        cDoc.gridx = 0;
        cDoc.gridy = 0;

        if( dataControl.getContent( ) instanceof Named ) {
            JPanel descriptionPanel = new JPanel( );
            descriptionPanel.setLayout( new GridBagLayout() );
            GridBagConstraints c = new GridBagConstraints( );

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.0;
            
            nameTextField = new JTextField( ( (Named) dataControl.getContent( ) ).getName( ) );
            nameChangeListener = new NameChangeListener( nameTextField, (Named) dataControl.getContent( ) );
            nameTextField.getDocument( ).addDocumentListener( nameChangeListener );
            descriptionPanel.add( nameTextField, c );
            descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.Name" ) ) );

            // if is Named has description sound, create the panel for it
            if( dataControl.getContent( ) instanceof HasDescriptionSound ) {
                
                String nameSoundPath = ((HasDescriptionSound)dataControl.getContent( )).getNameSoundPath( );
                JPanel soundpanel = createSoundPanel( (HasDescriptionSound)dataControl.getContent( ) , nameSoundPath , HasDescriptionSound.NAME_PATH );
                
                c.gridwidth = 1;
                c.gridx = 2;
                c.ipadx = 10;
                c.weightx = 0;
                c.fill = GridBagConstraints.NONE;
                descriptionPanel.add( soundpanel, c );
            }
           
            add( descriptionPanel, cDoc );
            cDoc.gridy++;
        }

        if( dataControl.getContent( ) instanceof Described ) {
            JPanel descriptionPanel = new JPanel( );
            descriptionPanel.setLayout(  new GridBagLayout() );
            GridBagConstraints c = new GridBagConstraints( );

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.ipadx = 0;
            c.weightx = 1.0;
            
            descriptionTextField = new JTextField( ( (Described) dataControl.getContent( ) ).getDescription( ) );
            descriptionChangeListener = new DescriptionChangeListener( descriptionTextField, (Described) dataControl.getContent( ) );
            descriptionTextField.getDocument( ).addDocumentListener( descriptionChangeListener );
            descriptionPanel.add( descriptionTextField, c );
            descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.Description" ) ) );
        
            // if Described also has description sound, create the panel for it
            if( dataControl.getContent( ) instanceof HasDescriptionSound ) {
                
                String nameSoundPath = ((HasDescriptionSound)dataControl.getContent( )).getDescriptionSoundPath( );
                JPanel soundpanel = createSoundPanel( (HasDescriptionSound)dataControl.getContent( ) , nameSoundPath , HasDescriptionSound.DESCRIPTION_PATH );
                c.gridwidth = 1;
                c.gridx = 2;
                c.ipadx = 10;
                c.weightx = 0;
                
                descriptionPanel.add( soundpanel, c );
            }
            
            
            add( descriptionPanel, cDoc );
            cDoc.gridy++;
        }

        if( dataControl.getContent( ) instanceof Detailed ) {
            JPanel detailedDescriptionPanel = new JPanel( );
            detailedDescriptionPanel.setLayout(  new GridBagLayout() );
            GridBagConstraints c = new GridBagConstraints( );

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            c.ipadx = 0;
            c.weightx = 1.0;
            
            detailedDescriptionTextField = new JTextField( ( (Detailed) dataControl.getContent( ) ).getDetailedDescription( ) );
            detailedDescriptionListener = new DetailedDescriptionChangeListener( detailedDescriptionTextField, (Detailed) dataControl.getContent( ) );
            detailedDescriptionTextField.getDocument( ).addDocumentListener( detailedDescriptionListener );
            detailedDescriptionPanel.add( detailedDescriptionTextField, c );
            detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Item.DetailedDescription" ) ) );
         
            // if Described also has description sound, create the panel for it
            if( dataControl.getContent( ) instanceof HasDescriptionSound ) {
                
                String nameSoundPath = ((HasDescriptionSound)dataControl.getContent( )).getDetailedDescriptionSoundPath( );
                JPanel soundpanel = createSoundPanel( (HasDescriptionSound)dataControl.getContent( ) , nameSoundPath , HasDescriptionSound.DETAILED_DESCRIPTION_PATH);
                c.gridwidth = 1;
                c.gridx = 2;
                c.ipadx = 10;
                c.weightx = 0;
                detailedDescriptionPanel.add( soundpanel, c );
            }
            
            
            add( detailedDescriptionPanel, cDoc );
        }

        setResizable( false );
        setSize( 600, 200 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
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
            label = new JLabel( temp[temp.length - 1], icon, SwingConstants.LEFT );
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
        deleteButton.setEnabled( soundPath != null &&  !soundPath.equals( "" ) );
        deleteButton.addActionListener( new SelectDescriptionSoundListener(descriptionSound,type, true)  );
        deleteButton.setOpaque( false );
        buttonPanel.add( deleteButton, cButtons );
        buttonPanel.setOpaque( false );
        
        
        cPanel.gridx = 1;
        cPanel.anchor = GridBagConstraints.EAST;
        soundpanel.add( buttonPanel, cPanel );
        
        return soundpanel;
    }

    @Override
    public boolean updateFields( ) {

        if( descriptionTextField != null ) {
            descriptionTextField.getDocument( ).removeDocumentListener( descriptionChangeListener );
            this.descriptionTextField.setText( ( (Described) dataControl.getContent( ) ).getDescription( ) );
            descriptionTextField.getDocument( ).addDocumentListener( descriptionChangeListener );
        }
        if( detailedDescriptionTextField != null ) {
            detailedDescriptionTextField.getDocument( ).removeDocumentListener( detailedDescriptionListener );
            this.detailedDescriptionTextField.setText( ( (Detailed) dataControl.getContent( ) ).getDetailedDescription( ) );
            detailedDescriptionTextField.getDocument( ).addDocumentListener( detailedDescriptionListener );
        }
        if( nameTextField != null ) {
            nameTextField.getDocument( ).removeDocumentListener( nameChangeListener );
            this.nameTextField.setText( ( (Named) dataControl.getContent( ) ).getName( ) );
            nameTextField.getDocument( ).addDocumentListener( nameChangeListener );
        }
             
        // update the UI for associated sounds if exists
        if (dataControl.getContent( ) instanceof HasDescriptionSound){
            
            for (int i=0;i< SOUND_ELEMENTS ; i++){ 
               String soundName=null; 
                switch (i){
                    case HasDescriptionSound.NAME_PATH: soundName = ((HasDescriptionSound)dataControl.getContent( )).getNameSoundPath( );
                            break;
                    case HasDescriptionSound.DESCRIPTION_PATH: soundName = ((HasDescriptionSound)dataControl.getContent( )).getDescriptionSoundPath( );
                            break;
                    case HasDescriptionSound.DETAILED_DESCRIPTION_PATH: soundName = ((HasDescriptionSound)dataControl.getContent( )).getDetailedDescriptionSoundPath( );
                            break;
                }  
                
                soundDeleteButtons[i].setEnabled( soundName != null );
                
                if (soundName != null){                
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
        
        }
      
        return true;
    }
     
    
}
