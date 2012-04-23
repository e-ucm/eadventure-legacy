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
        //setResizable( false );
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
