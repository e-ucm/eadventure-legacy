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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.SingleEffectController;

public class RandomEffectDialog extends EffectDialog {

    private SingleEffectController positiveEffectController;

    private SingleEffectController negativeEffectController;

    private JTextField positiveEffectDescription;

    private JTextField negativeEffectDescription;

    private JSpinner probabilitySpinner;

    public RandomEffectDialog( int probability, SingleEffectController positiveEffectController, SingleEffectController negativeEffectController ) {

        super( TC.get( "RandomEffect.Title" ), false );
        this.positiveEffectController = positiveEffectController;
        this.negativeEffectController = negativeEffectController;

        /*
         * Probability panel
         */
        SpinnerModel model = new SpinnerNumberModel( probability, //initial value
        1, //min
        100, //max
        1 ); //step

        probabilitySpinner = new JSpinner( model );

        JPanel probabilityPanel = new JPanel( );
        probabilityPanel.setLayout( new GridBagLayout( ) );
        probabilityPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "RandomEffect.Probability.Title" ) ) ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 4, 6, 4, 6 );
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        JLabel descriptionLabel = new JLabel( TC.get( "RandomEffect.Probability.Description" ) );
        probabilityPanel.add( descriptionLabel, c );

        c.gridx = 1;
        probabilityPanel.add( probabilitySpinner, c );

        /*
         * Main panel
         */
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.PAGE_AXIS ) );
        mainPanel.add( probabilityPanel );
        mainPanel.add( Box.createVerticalStrut( 2 ) );
        mainPanel.add( createSingleEffectPanel( true ) );
        mainPanel.add( createSingleEffectPanel( false ) );

        add( mainPanel, BorderLayout.CENTER );

        // Set the dialog
        //setResizable( false );
        pack( );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    private JPanel createSingleEffectPanel( boolean positive ) {

        // Load the image for the delete content button
        Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

        // Create the main panel and set the border
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 4, 4, 4, 4 );

        // Set the border of the panel with the description
        if( positive )
            mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "RandomEffect.Positive.Title" ) ) ) );
        else
            mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "RandomEffect.Negative.Title" ) ) ) );

        // Create the delete content button
        JButton deleteContentButton = new JButton( deleteContentIcon );
        deleteContentButton.addActionListener( new DeleteEffectListener( positive ) );
        deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
        deleteContentButton.setToolTipText( TC.get( "RandomEffect.DeleteEffect" ) );
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.weighty = 0;
        mainPanel.add( deleteContentButton, c );

        // Create the text field and insert it
        JTextField pathTextField = new JTextField( );
        pathTextField.setEditable( false );
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        mainPanel.add( pathTextField, c );

        // Create the "Edit" button and insert it
        JButton selectButton = new JButton( TC.get( "RandomEffect.EditEffect" ) );
        selectButton.addActionListener( new EditEffectListener( positive ) );
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        mainPanel.add( selectButton, c );

        if( positive ) {
            this.positiveEffectDescription = pathTextField;
            if( positiveEffectController.getEffectInfo( ) != null ) {
                pathTextField.setText( positiveEffectController.getEffectInfo( ) );
            }
        }
        else {
            this.negativeEffectDescription = pathTextField;
            if( negativeEffectController.getEffectInfo( ) != null ) {
                pathTextField.setText( negativeEffectController.getEffectInfo( ) );
            }

        }

        return mainPanel;

    }

    /**
     * 
     */
    private static final long serialVersionUID = -5737206942292209020L;

    @Override
    protected void pressedOKButton( ) {

        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_PROBABILITY, probabilitySpinner.getValue( ).toString( ) );
    }

    public void refresh( ) {

        if( positiveEffectController.getEffectInfo( ) != null ) {
            positiveEffectDescription.setText( positiveEffectController.getEffectInfo( ) );
        }
        else {
            positiveEffectDescription.setText( "" );
        }

        if( negativeEffectController.getEffectInfo( ) != null ) {
            negativeEffectDescription.setText( negativeEffectController.getEffectInfo( ) );
        }
        else {
            negativeEffectDescription.setText( "" );
        }

    }

    private class EditEffectListener implements ActionListener {

        private boolean positive = false;

        public EditEffectListener( boolean positive ) {

            this.positive = positive;
        }

        public void actionPerformed( ActionEvent e ) {

            if( positive && positiveEffectController.editEffect( ) || !positive && negativeEffectController.editEffect( ) ) {
                refresh( );
            }
        }

    }

    private class DeleteEffectListener implements ActionListener {

        private boolean positive = false;

        public DeleteEffectListener( boolean positive ) {

            this.positive = positive;
        }

        public void actionPerformed( ActionEvent e ) {

            if( positive ) {
                positiveEffectController.deleteEffect( );
            }
            else {
                negativeEffectController.deleteEffect( );
            }
            refresh( );
        }

    }

}
