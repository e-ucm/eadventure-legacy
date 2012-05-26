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
package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.gui.displaydialogs.EditImageDialog;

public class FrameConfigPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private FrameDataControl frame;

    private JList list;

    private JSpinner frameTimeSpinner;

    private JTextField imageUriTextField;

    private JTextField soundUriTextField;

    private AnimationEditDialog aed;

    private JCheckBox checkbox;

    public FrameConfigPanel( FrameDataControl frameDataControl, JList list, AnimationEditDialog aed ) {

        this.frame = frameDataControl;
        this.list = list;
        this.aed = aed;
        this.setLayout( new GridBagLayout( ) );

        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        JPanel temp = new JPanel( );
        temp.add( new JLabel( TC.get( "Animation.Duration" ) + ": " ) );
        SpinnerModel sm = new SpinnerNumberModel( frameDataControl.getTime( ), 0, 10000, 100 );
        frameTimeSpinner = new JSpinner( sm );
        frameTimeSpinner.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                modifyFrame( );
            }
        } );
        temp.add( frameTimeSpinner );

        if( aed.getAnimationDataControl( ).isSlides( ) ) {
            checkbox = new JCheckBox( TC.get( "Animation.WaitForClick" ) );
            if( frameDataControl.isWaitForClick( ) ) {
                checkbox.setSelected( true );
                frameTimeSpinner.setEnabled( false );
            }
            else
                checkbox.setSelected( false );
            checkbox.addChangeListener( new ChangeListener( ) {

                public void stateChanged( ChangeEvent arg0 ) {

                    changeWaitForClick( );
                }

            } );
            temp.add( checkbox );
        }

        this.add( temp, c );

        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        c.gridx = 0;
        c.weightx = 1;
        JPanel imageAssetPanel = createImageAssetPanel( frameDataControl );

        this.add( imageAssetPanel, c );

        c.gridy++;
        this.add( createSoundAssetPanel( frameDataControl ), c );
    }

    private JPanel createImageAssetPanel( final FrameDataControl frameDataControl ) {

        JPanel imageAssetPanel = new JPanel( );
        imageAssetPanel.setLayout( new GridBagLayout( ) );
        imageAssetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Animation.Image" ) ) );
        GridBagConstraints c2 = new GridBagConstraints( );
        c2.insets = new Insets( 2, 2, 2, 2 );
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        c2.weighty = 0;

        // Create the text field and insert it
        imageUriTextField = new JTextField( 30 );
        imageUriTextField.setText( frameDataControl.getImageURI( ) );
        imageUriTextField.setEditable( false );
        c2.gridx = 0;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        imageAssetPanel.add( imageUriTextField, c2 );

        // Create the "Select" button and insert it
        final JButton imageViewButton = new JButton( TC.get( "Resources.Edit" ) );
        JButton imageSelectButton = new JButton( TC.get( "Resources.Select" ) );
        imageSelectButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                selectImage( );
                if (frameDataControl.getImageURI( ) != null
                        && !frameDataControl.getImageURI( ).equals( "" ))
                    imageViewButton.setEnabled( true );
                else
                    imageViewButton.setEnabled( false );
            }
        } );
        c2.gridx = 1;
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        imageAssetPanel.add( imageSelectButton, c2 );

        imageViewButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                new EditImageDialog( frameDataControl );
            }
        } );
        if (frameDataControl.getImageURI( ) != null
                && !frameDataControl.getImageURI( ).equals( "" ))
            imageViewButton.setEnabled( true );
        else
            imageViewButton.setEnabled( false );

        c2.gridx = 2;
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        imageAssetPanel.add( imageViewButton, c2 );

        return imageAssetPanel;
    }

    private JPanel createSoundAssetPanel( FrameDataControl frameDataControl ) {

        Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

        JPanel soundAssetPanel = new JPanel( );
        soundAssetPanel.setLayout( new GridBagLayout( ) );
        soundAssetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Animation.Sound" ) ) );
        GridBagConstraints c2 = new GridBagConstraints( );
        c2.insets = new Insets( 2, 2, 2, 2 );
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        c2.weighty = 0;

        JButton deleteContentButton = new JButton( deleteContentIcon );
        deleteContentButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                deleteSound( );
            }
        } );
        deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
        deleteContentButton.setToolTipText( TC.get( "Resources.DeleteAsset" ) );
        soundAssetPanel.add( deleteContentButton, c2 );

        // Create the text field and insert it
        soundUriTextField = new JTextField( 30 );
        soundUriTextField.setText( frameDataControl.getSoundUri( ) );
        soundUriTextField.setEditable( false );
        c2.gridx = 1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1;
        soundAssetPanel.add( soundUriTextField, c2 );

        // Create the "Select" button and insert it
        JButton soundSelectButton = new JButton( TC.get( "Resources.Select" ) );
        soundSelectButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                selectSound( );
            }
        } );
        c2.gridx = 2;
        c2.fill = GridBagConstraints.NONE;
        c2.weightx = 0;
        soundAssetPanel.add( soundSelectButton, c2 );

        return soundAssetPanel;
    }

    protected void changeWaitForClick( ) {

        frame.setWaitForClick( checkbox.isSelected( ) );
        if( checkbox.isSelected( ) )
            frameTimeSpinner.setEnabled( false );
        else
            frameTimeSpinner.setEnabled( true );
    }

    protected void selectImage( ) {

        String temp = aed.getAnimationDataControl( ).getImagePath( aed );
        if( temp != null ) {
            frame.setImageURI( temp );
            list.updateUI( );
            imageUriTextField.setText( temp );
            list.updateUI( );
        }
    }

    protected void selectSound( ) {

        String temp = aed.getAnimationDataControl( ).getSoundPath( aed );
        if( temp != null ) {
            frame.setSoundURI( temp );
            list.updateUI( );
            soundUriTextField.setText( temp );
            list.updateUI( );
        }
    }

    protected void deleteSound( ) {

        frame.setSoundURI( "" );
        list.updateUI( );
        soundUriTextField.setText( "" );
        list.updateUI( );
    }

    protected void modifyFrame( ) {

        frame.setTime( ( (Double) frameTimeSpinner.getModel( ).getValue( ) ).longValue( ) );
        list.updateUI( );
    }
}
