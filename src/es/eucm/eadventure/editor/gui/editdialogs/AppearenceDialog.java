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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;

/**
 * This class is the editing dialog for the conditions. Here the user can add
 * conditions to the events of the script, using the flags defined in the Flags
 * dialog.
 * 
 * @author Javier Torrente
 */
public class AppearenceDialog extends ToolManagableDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    private CustomActionLooksPanel conditionsPanel;

    /**
     * Constructor.
     * 
     * @param actionDataControl
     *            Controller for the conditions
     */
    public AppearenceDialog( ActionDataControl actionDataControl ) {

        // Call to the JDialog constructor
        super( Controller.getInstance( ).peekWindow( ), TC.get( "CustomAction.PersonalizationTitle" ), false );

        // Create the main panel and add it
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        conditionsPanel = new CustomActionLooksPanel( actionDataControl );
        add( conditionsPanel, c );

        // Set the size, position and properties of the dialog
        //setResizable( false );
        setSize( 600, 400 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    //@Override
    @Override
    public boolean updateFields( ) {

        //this.removeAll();
        return conditionsPanel.updateFields( );
    }

    private class CustomActionLooksPanel extends LooksPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public CustomActionLooksPanel( DataControlWithResources control ) {

            super( control );
            // TODO Parche, arreglar
            lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

        }

        @Override
        protected void createPreview( ) {

        }

        @Override
        public void updatePreview( ) {

            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

        @Override
        public void updateResources( ) {

            super.updateResources( );
            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

    }

}
