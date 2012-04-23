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
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class HighlightItemEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constant for the highlight dialog.
     */
    public static final int HIGHLIGHT = 0;

    /**
     * Constant for the de-highlight dialog.
     */
    public static final int DEHIGHLIGHT = 1;

    /**
     * Texts for the different titles of the dialogs.
     */
    private static final String[] dialogTitles = { "HighlightItemEffect.Title", "DeHighlightItemEffect.Title" };

    /**
     * Texts for the different descriptions of the dialogs.
     */
    private static final String[] dialogDescriptions = { "HighlightItemEffect.Description", "DeHighlightItemEffect.Description" };

    /**
     * Combo box with the items.
     */
    private JComboBox itemsComboBox;
    
    private JComboBox typesComboBox;
    
    private JCheckBox animatedCheckBox;

    /**
     * Constructor.
     * 
     * @param type
     *            Type of the dialog (HighlightItemEffectDialog.HIGHLIGHT or
     *            HighlightItemEffectDialog.DEHIGHLIGHT)
     * @param currentProperties
     *            Set of initial values
     */
    public HighlightItemEffectDialog( int type, HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( dialogTitles[type] ), false );

        // Take the list of items
        String[] itemsArray = controller.getIdentifierSummary( ).getItemIds( );

        // If there is at least one element
        if( itemsArray.length > 0 ) {
            // Create the main panel
            JPanel mainPanel = new JPanel( );
            mainPanel.setLayout( new GridBagLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );

            // Set the border of the panel with the description
            mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "HighlightItemEffect.ShortDescription" ) ) ) );

            
            // Create and add the list of flags
            c.insets = new Insets( 2, 4, 4, 4 );
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            c.gridy = 0;
            
            JTextPane informationTextPane = new JTextPane( );
            informationTextPane.setEditable( false );
            informationTextPane.setBackground( getBackground( ) );
            informationTextPane.setText(TC.get( dialogDescriptions[type] ));
            mainPanel.add( informationTextPane, c);
            
            c.gridy++;
            itemsComboBox = new JComboBox( controller.getIdentifierSummary( ).getItemIds( ) );
            mainPanel.add( itemsComboBox, c );

            c.gridy++;
            String[] types = { TC.get( "HighlightItemEffect.None" ),
                    TC.get( "HighlightItemEffect.Blue" ),
                    TC.get( "HighlightItemEffect.Red" ),
                    TC.get( "HighlightItemEffect.Green" ), 
                    TC.get( "HighlightItemEffect.Border" ) };
            typesComboBox = new JComboBox(types );
            mainPanel.add(typesComboBox, c);

            c.gridy++;
            animatedCheckBox = new JCheckBox( TC.get( "HighlightItemEffect.Animated" ));
            mainPanel.add( animatedCheckBox, c );
            
            // Add the panel to the center
            add( mainPanel, BorderLayout.CENTER );

            // Set the defualt values (if present)
            if( currentProperties != null ) {
                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
                    itemsComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
                if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_HIGHLIGHT_TYPE ) )
                    typesComboBox.setSelectedIndex( (Integer) currentProperties.get( EffectsController.EFFECT_PROPERTY_HIGHLIGHT_TYPE ) );
                if (currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_ANIMATED ))
                    animatedCheckBox.setSelected( (Boolean) currentProperties.get( EffectsController.EFFECT_PROPERTY_ANIMATED ));
            }
            

            // Set the dialog
            //setResizable( false );
            pack( );
            setSize(400, 300);
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
            setVisible( true );
        }

        // If the list had no elements, show an error message
        else
            controller.showErrorDialog( getTitle( ), TC.get( "HighlightItemEffect.ErrorNoItems" ) );
    }

    @Override
    protected void pressedOKButton( ) {
        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_TARGET, itemsComboBox.getSelectedItem( ).toString( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_ANIMATED, animatedCheckBox.isSelected( ) );
        properties.put( EffectsController.EFFECT_PROPERTY_HIGHLIGHT_TYPE, typesComboBox.getSelectedIndex( ));
    }

}
