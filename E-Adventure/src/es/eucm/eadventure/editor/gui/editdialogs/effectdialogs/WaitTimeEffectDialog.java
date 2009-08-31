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
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.gui.TextConstants;
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
        super( TextConstants.getText( "WaitTimeEffect.Title" ), true );

        // Create the main panel
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Set the border of the panel with the description
        mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "WaitTimeEffect.Border" ) ) ) );

        JLabel label = new JLabel( TextConstants.getText( "WaitTimeEffect.Label" ) );

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
