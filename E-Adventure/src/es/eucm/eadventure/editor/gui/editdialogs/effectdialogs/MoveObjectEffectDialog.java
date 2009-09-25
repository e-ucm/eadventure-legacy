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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.NoEditableNumberSpinner;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

public class MoveObjectEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Combo box with the scenes.
     */
    private JComboBox scenesComboBox;
    
    private JComboBox itemsComboBox;
    
    private JCheckBox animatedCheckBox;

    /**
     * Panel to select and show the position.
     */
    private PositionPanel pointPositionPanel;
    
    private ElementImagePanel imagePanel;

    private NoEditableNumberSpinner translateSpeedSpinner;
    
    private NoEditableNumberSpinner scaleSpeedSpinner;
    
    /**
     * Constructor.
     * 
     * @param effectsController
     *            Controller of the effects
     * @param currentProperties
     *            Set of initial values
     */
    public MoveObjectEffectDialog( HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( "MoveObjectEffect.Title" ), true );

        // Create the set of values for the scenes
        List<String> scenesList = new ArrayList<String>( );
        scenesList.add( TC.get( "MoveObjectEffect.NoSceneSelected" ) );
        String[] scenesArray = controller.getIdentifierSummary( ).getSceneIds( );
        for( String scene : scenesArray )
            scenesList.add( scene );
        scenesArray = scenesList.toArray( new String[] {} );
        
        List<String> itemList = new ArrayList<String>();
        itemList.add( TC.get( "MoveObjectEffect.NoItemSelected" ) );
        String[] itemArray = controller.getIdentifierSummary( ).getItemIds( );
        for (String item : itemArray )
            itemList.add( item );
        itemArray = itemList.toArray( new String[] {} );

        // Create the main panel
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 2, 4, 4, 4 );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        // Create and add the list of scenes
        c.gridy = 1;
        scenesComboBox = new JComboBox( scenesArray );
        scenesComboBox.addActionListener( new ScenesComboBoxActionListener( ) );
        mainPanel.add( scenesComboBox, c );

        c.gridy++;
        itemsComboBox = new JComboBox( itemArray );
        itemsComboBox.addActionListener( new ItemsComboBoxActionListener() );
        mainPanel.add( itemsComboBox, c);
        
        // Create and add the panel
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy++;
        c.weightx = 1;
        c.weighty = 1;
        
        JPanel animatedPanel = new JPanel();
        
        animatedCheckBox = new JCheckBox(TC.get( "MoveObjectEffect.Animated" ));
        animatedPanel.add( animatedCheckBox );
        animatedPanel.add(new JLabel(TC.get( "MoveObjectEffect.TranslateSpeed" )));
        translateSpeedSpinner = new NoEditableNumberSpinner(20, 10, 100, 10);
        animatedPanel.add(translateSpeedSpinner);
        animatedPanel.add(new JLabel(TC.get( "MoveObjectEffect.ScaleSpeed" )));
        scaleSpeedSpinner = new NoEditableNumberSpinner(20, 10, 100, 10);
        animatedPanel.add(scaleSpeedSpinner);
        
        // Set the defualt values (if present)
        if( currentProperties != null ) {
            int x = 0;
            int y = 0;
            float scale = 1.0f;
            String targetId = "";
            boolean animated = true;
                        
            if (currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ))
                targetId = (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET );

            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_X ) )
                x = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_X ) );

            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_Y ) )
                y = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_Y ) );

            if (currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_SCALE ))
                scale = (Float) currentProperties.get( EffectsController.EFFECT_PROPERTY_SCALE );
            
            if (currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_ANIMATED ))
                animated = (Boolean) currentProperties.get( EffectsController.EFFECT_PROPERTY_ANIMATED );
            
            if (currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_SCALE_SPEED ))
                scaleSpeedSpinner.setValue( currentProperties.get( EffectsController.EFFECT_PROPERTY_SCALE_SPEED));
                
            if (currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TRANSLATION_SPEED ))
                translateSpeedSpinner.setValue( currentProperties.get( EffectsController.EFFECT_PROPERTY_TRANSLATION_SPEED));
                   
            if( x > 5000 )
                x = 5000;
            if( x < -2000 )
                x = -2000;
            if( y > 5000 )
                y = 5000;
            if( y < -2000 )
                y = -2000;
    
            if (targetId.equals( "" ))
                imagePanel = new ElementImagePanel(null, null);
            else {
                imagePanel = new ElementImagePanel(null, controller.getElementImagePath( targetId ));
                itemsComboBox.setSelectedItem( targetId );
            }
            pointPositionPanel = new PositionPanel( imagePanel, x, y, scale );
            animatedCheckBox.setSelected( animated );
        }
        else {
            imagePanel = new ElementImagePanel(null, null);
            pointPositionPanel = new PositionPanel( imagePanel, 400, 500, 1.0f );
        }
        //pointPositionPanel = new PositionPanel( new PointImagePanel( ) );
        mainPanel.add( pointPositionPanel, c );

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy++;
        c.weighty = 0;
        

        mainPanel.add(animatedPanel, c);
        
        // Add the panel to the center
        add( mainPanel, BorderLayout.CENTER );

        // Set the dialog
        setResizable( false );
        setSize( 640, 480 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_X, String.valueOf( pointPositionPanel.getPositionX( ) ) );
        properties.put( EffectsController.EFFECT_PROPERTY_Y, String.valueOf( pointPositionPanel.getPositionY( ) ) );
        properties.put( EffectsController.EFFECT_PROPERTY_SCALE, pointPositionPanel.getScale() );
        properties.put( EffectsController.EFFECT_PROPERTY_TARGET, itemsComboBox.getSelectedItem( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_ANIMATED, animatedCheckBox.isSelected( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_TRANSLATION_SPEED, translateSpeedSpinner.getValue( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_SCALE_SPEED, scaleSpeedSpinner.getValue( ) );
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

            int selectedScene = scenesComboBox.getSelectedIndex( );

            // If the first option were selected, remove the image
            if( selectedScene == 0 )
                pointPositionPanel.removeImage( );

            // If other option were selected, load the image
            else
                pointPositionPanel.loadImage( controller.getSceneImagePath( scenesComboBox.getSelectedItem( ).toString( ) ) );
        }
    }

    /**
     * Listener for the scenes combo box.
     */
    private class ItemsComboBoxActionListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            int selectedItem = itemsComboBox.getSelectedIndex( );

            if (selectedItem == 0)
                imagePanel.removeElementImage();
            else
                imagePanel.loadElement(  controller.getElementImagePath( (String) itemsComboBox.getSelectedItem( ) ));
        }
    }

}