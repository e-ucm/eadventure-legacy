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
