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
