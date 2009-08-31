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
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

/**
 * This class represents a dialog used to add and edit trigger scene effects. It
 * allows to select a scene and an insertion point to it.
 * 
 * @author Bruno Torijano Bueno
 */
public class TriggerSceneEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Combo box with the scenes.
     */
    private JComboBox scenesComboBox;

    /**
     * Panel to select and show the position.
     */
    private PositionPanel playerPositionPanel;

    /**
     * Constructor.
     * 
     * @param currentProperties
     *            Set of initial values
     */
    public TriggerSceneEffectDialog( HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TextConstants.getText( "TriggerSceneEffect.Title" ), true );

        // Take the list of characters
        String[] scenesArray = controller.getIdentifierSummary( ).getSceneIds( );

        // If there is one scene
        if( scenesArray.length > 0 ) {

            // Load the path to the image preview of the player
            String playerPath = controller.getPlayerImagePath( );

            // Create the main panel
            JPanel mainPanel = new JPanel( );
            mainPanel.setLayout( new GridBagLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );

            // Set the border of the panel with the description
            mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TriggerSceneEffect.Description" ) ) ) );

            // Create and add the list of scenes
            c.insets = new Insets( 2, 4, 4, 4 );
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            scenesComboBox = new JComboBox( scenesArray );
            mainPanel.add( scenesComboBox, c );

            // Create and add the panel
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 1;
            c.weightx = 1;
            c.weighty = 1;
            // Set the defualt values (if present)
            if( currentProperties != null ) {
                int x = 0;
                int y = 0;

                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
                    scenesComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );

                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_X ) )
                    x = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_X ) );

                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_Y ) )
                    y = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_Y ) );

                if( x > 5000 )
                    x = 5000;
                if( x < -2000 )
                    x = -2000;
                if( y > 5000 )
                    y = 5000;
                if( y < -2000 )
                    y = -2000;
                playerPositionPanel = new PositionPanel( new ElementImagePanel( null, playerPath ), x, y );
            }
            else {
                // Select the first element
                scenesComboBox.setSelectedIndex( 0 );
                playerPositionPanel = new PositionPanel( new ElementImagePanel( null, playerPath ), 400, 500 );
            }
            playerPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );

            if( !Controller.getInstance( ).isPlayTransparent( ) )
                mainPanel.add( playerPositionPanel, c );

            // Add the panel to the center
            add( mainPanel, BorderLayout.CENTER );

            scenesComboBox.addActionListener( new ScenesComboBoxActionListener( ) );

            // Set the dialog
            setResizable( false );
            setSize( 640, 480 );
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
            setVisible( true );
        }

        // If the list had no elements, show an error message
        else
            controller.showErrorDialog( getTitle( ), TextConstants.getText( "TriggerSceneEffect.ErrorNoScenes" ) );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_TARGET, scenesComboBox.getSelectedItem( ).toString( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_X, String.valueOf( playerPositionPanel.getPositionX( ) ) );
        properties.put( EffectsController.EFFECT_PROPERTY_Y, String.valueOf( playerPositionPanel.getPositionY( ) ) );
    }

    /**
     * Listener for the scenes combo box.
     */
    private class ScenesComboBoxActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            // Load the image of the selected scene
            playerPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );
        }
    }
}
