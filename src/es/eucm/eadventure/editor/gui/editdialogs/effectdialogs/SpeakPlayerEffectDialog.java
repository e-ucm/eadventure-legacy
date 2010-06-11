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
package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class SpeakPlayerEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Combo box with the books.
     */
    private JTextArea textArea;

    /**
     * Constructor.
     * 
     * @param currentProperties
     *            Set of initial values
     */
    public SpeakPlayerEffectDialog( HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( "SpeakPlayerEffect.Title" ), false );

        // Create the main panel
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Set the border of the panel with the description
        mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "SpeakPlayerEffect.Description" ) ) ) );

        // Create and add the list of flags
        c.insets = new Insets( 2, 4, 4, 4 );
        c.fill = GridBagConstraints.BOTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        textArea = new JTextArea( );
        textArea.setWrapStyleWord( true );
        textArea.setLineWrap( true );
        mainPanel.add( new JScrollPane( textArea ), c );

        // Add the panel to the center
        add( mainPanel, BorderLayout.CENTER );

        // Set the defualt values (if present)
        if( currentProperties != null ) {
            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TEXT ) )
                textArea.setText( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
        }

        // Set the dialog
        setResizable( false );
        setSize( 400, 140 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_TEXT, textArea.getText( ) );
    }

}
