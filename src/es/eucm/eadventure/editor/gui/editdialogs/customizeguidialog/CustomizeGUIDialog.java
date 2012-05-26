/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.editdialogs.customizeguidialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ToolManagableDialog;

public class CustomizeGUIDialog extends ToolManagableDialog {

    private static final long serialVersionUID = 1L;

    private AdventureDataControl dataControl;

    private CursorsPanel cursorsPanel;

    private ButtonsPanel buttonsPanel;

    private InventoryPanel inventoryPanel;

    public CustomizeGUIDialog( AdventureDataControl dControl ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "CustomizeGUI.Title" ), false );//, Dialog.ModalityType.APPLICATION_MODAL );
        this.dataControl = dControl;
        //Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane( );

        //Create the cursors panel
        cursorsPanel = new CursorsPanel( dataControl );
        buttonsPanel = new ButtonsPanel( dataControl );
        inventoryPanel = null;

        tabbedPane.insertTab( TC.get( "Cursors.Title" ), null, cursorsPanel, TC.get( "Cursors.Tip" ), 0 );
        tabbedPane.insertTab( TC.get( "Buttons.Title" ), null, buttonsPanel, TC.get( "Buttons.Tip" ), 1 );
        if( dataControl.getGUIType( ) == DescriptorData.GUI_CONTEXTUAL ) {
            inventoryPanel = new InventoryPanel( dataControl );
            tabbedPane.insertTab( TC.get( "Inventory.Title" ), null, inventoryPanel, TC.get( "Inventory.Tip" ), 2 );
        }

        this.getContentPane( ).setLayout( new BorderLayout( ) );
        this.getContentPane( ).add( tabbedPane, BorderLayout.CENTER );
        Dimension screen = Toolkit.getDefaultToolkit( ).getScreenSize( );
        this.setLocation( (int) screen.getWidth( ) / 2 - 350, (int) screen.getHeight( ) / 2 - 250 );
        this.setSize( 700, 500 );
        this.setVisible( true );
    }

    @Override
    public boolean updateFields( ) {

        //Update cursorsPanel and buttonsPanel
        return cursorsPanel.updateFields( ) && buttonsPanel.updateFields( ) && ( inventoryPanel == null ? true : inventoryPanel.updateFields( ) );
    }
}
