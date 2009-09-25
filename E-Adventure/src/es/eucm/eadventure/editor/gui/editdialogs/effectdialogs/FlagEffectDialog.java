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
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.VarFlagsController;

public class FlagEffectDialog extends EffectDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constant for the activate dialog.
     */
    public static final int ACTIVATE = 0;

    /**
     * Constant for the deactivate dialog.
     */
    public static final int DEACTIVATE = 1;

    /**
     * Texts for the different titles of the dialogs.
     */
    private static final String[] dialogTitles = { "ActivateEffect.Title", "DeactivateEffect.Title" };

    /**
     * Texts for the different descriptions of the dialogs.
     */
    private static final String[] dialogDescriptions = { "ActivateEffect.Description", "DeactivateEffect.Description" };

    /**
     * Combo box with the flags.
     */
    private JComboBox flagsComboBox;

    /**
     * Constructor.
     * 
     * @param type
     *            Type of the dialog (FlagEffectDialog.ACTIVATE or
     *            FlagEffectDialog.DEACTIVATE)
     * @param currentProperties
     *            Set of initial values
     */
    public FlagEffectDialog( int type, HashMap<Integer, Object> currentProperties ) {

        // Call the super method
        super( TC.get( dialogTitles[type] ), false );

        // Take the array of flags
        String[] flagsArray = controller.getVarFlagSummary( ).getFlags( );

        // If it is not empty
        //if( flagsArray.length > 0 ) {
        // Create the main panel
        JPanel mainPanel = new JPanel( );
        mainPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Set the border of the panel with the description
        mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( dialogDescriptions[type] ) ) ) );

        // Create and add the list of flags
        c.insets = new Insets( 2, 4, 4, 4 );
        c.weightx = 1;
        flagsComboBox = new JComboBox( flagsArray );
        flagsComboBox.setEditable( true );
        mainPanel.add( flagsComboBox, c );

        // Add the panel to the center
        add( mainPanel, BorderLayout.CENTER );

        // Set the defualt values (if present)
        if( currentProperties != null ) {
            if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
                flagsComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
        }

        // Set the dialog
        setResizable( false );
        pack( );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
        //}

        // If the list had no elements, show an error message
        //else
        //	controller.showErrorDialog( getTitle( ), TextConstants.getText( "ActivateDeactivateEffect.ErrorNoFlags" ) );
    }

    @Override
    protected void pressedOKButton( ) {

        // Create a set of properties, and put the selected value
        properties = new HashMap<Integer, Object>( );

        VarFlagsController varFlagsController = new VarFlagsController( Controller.getInstance( ).getVarFlagSummary( ) );
        String flag = null;
        if( flagsComboBox.getSelectedItem( ) != null )
            flag = flagsComboBox.getSelectedItem( ).toString( );

        if( varFlagsController.existsFlag( flag ) ) {
            properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flag );
        }
        else {
            String flagAdded = varFlagsController.addShortCutFlagVar( true, flag );
            if( flagAdded != null ) {
                properties.put( EffectsController.EFFECT_PROPERTY_TARGET, flagAdded );
            }
            else
                properties = null;
        }

    }

}
