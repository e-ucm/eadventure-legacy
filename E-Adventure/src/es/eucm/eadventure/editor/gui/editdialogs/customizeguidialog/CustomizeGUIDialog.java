/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.editdialogs.customizeguidialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ToolManagableDialog;

public class CustomizeGUIDialog extends ToolManagableDialog{

	private static final long serialVersionUID = 1L;

	private AdventureDataControl dataControl;
	
	private CursorsPanel cursorsPanel;
	
	private ButtonsPanel buttonsPanel;
	
	private InventoryPanel inventoryPanel;
	
	public CustomizeGUIDialog (AdventureDataControl dControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "CustomizeGUI.Title" ), false );//, Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl=dControl;
		//Create the tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//Create the cursors panel
		cursorsPanel= new CursorsPanel (dataControl);
		buttonsPanel = new ButtonsPanel (dataControl);
		inventoryPanel = null;
		
		tabbedPane.insertTab( TextConstants.getText( "Cursors.Title" ), null, cursorsPanel, TextConstants.getText( "Cursors.Tip" ), 0 );
		tabbedPane.insertTab( TextConstants.getText( "Buttons.Title" ), null, buttonsPanel, TextConstants.getText( "Buttons.Tip" ), 1 );
		if (dataControl.getGUIType() == AdventureData.GUI_CONTEXTUAL) {
			inventoryPanel = new InventoryPanel (dataControl);
			tabbedPane.insertTab( TextConstants.getText( "Inventory.Title" ), null, inventoryPanel, TextConstants.getText( "Inventory.Tip" ), 2);
		}
		
		this.getContentPane( ).setLayout( new BorderLayout() );
		this.getContentPane( ).add( tabbedPane, BorderLayout.CENTER );
		Dimension screen = Toolkit.getDefaultToolkit( ).getScreenSize( );
		this.setLocation( (int)screen.getWidth( )/2 -350, (int)screen.getHeight( )/2 -250 );
		this.setSize( 700,500 );
		this.setVisible( true );
	}
	
	@Override
	public boolean updateFields() {
		//Update cursorsPanel and buttonsPanel
		return cursorsPanel.updateFields() && buttonsPanel.updateFields() && (inventoryPanel==null?true:inventoryPanel.updateFields());
	}
}
