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
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

/**
 * This class represents a dialog used to add and edit the time in
 * WaitTimeEffect.
 * 
 */
public class WaitTimeEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The spinner to store the time (in seconds) to wait without do nothing
     */
    private JSpinner time;

    /**
     * Constructor.
     * 
     * @param currentProperties
     *            Set of initial values
     */
    public WaitTimeEffectDialog( HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( "WaitTimeEffect.Title" ), true );

        // Create the main panel
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Set the border of the panel with the description
        mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "WaitTimeEffect.Border" ) ) ) );

        JLabel label = new JLabel( TC.get( "WaitTimeEffect.Label" ) );

        // Set the defualt values (if present)
        if( currentProperties != null ) {
            int timeValue = 0;
            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TIME ) )
                timeValue = Integer.parseInt( (String) currentProperties.get( EffectsController.EFFECT_PROPERTY_TIME ) );
            time = new JSpinner( new SpinnerNumberModel( timeValue, 0, Integer.MAX_VALUE, 1 ) );
        }
        else {
            time = new JSpinner( new SpinnerNumberModel( 0, 0, Integer.MAX_VALUE, 1 ) );
        }
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add( label, c );
        c.gridy++;
        mainPanel.add( time, c );

        add( mainPanel, BorderLayout.CENTER );
        // Set the dialog
        setResizable( false );
        setSize( 250, 150 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );
        properties.put( EffectsController.EFFECT_PROPERTY_TIME, Integer.toString( (Integer) time.getModel( ).getValue( ) ) );

    }

}
