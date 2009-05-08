package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencesTable;


/**
 * That tool edit the changes in player layer because of the fact of player's movements in ScenePanel table
 *
 */
public class MovePlayerLayerInTableTool extends Tool{

	
	private ElementReferencesTable table;
	
	private ReferencesListDataControl referencesListDataControl;
	
	private boolean moveUp;
	
	private ElementContainer element;
	
	private int selectedRow;
	
	public MovePlayerLayerInTableTool(ReferencesListDataControl rldc, ElementReferencesTable table2, boolean isMoveUp){
		this.referencesListDataControl = rldc;
		this.table = table2 ;
		this.moveUp = isMoveUp;
		selectedRow = table.getSelectedRow( );
		element = referencesListDataControl.getAllReferencesDataControl().get( selectedRow );
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		action(moveUp);
		return true;
	}
	
	private void action(boolean up){
		// do moveDown 
		if (!up&&referencesListDataControl.moveElementDown( element.getErdc() )){
			table.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
			table.updateUI( );
		}
		//do moveUp
		if (up&&referencesListDataControl.moveElementUp( element.getErdc() )){
				table.getSelectionModel( ).setSelectionInterval( selectedRow-1, selectedRow-1 );
				table.updateUI( );
		}
		moveUp = !moveUp;
	}
	

	@Override
	public boolean redoTool() {
		action(moveUp);
		return true;
	}

	@Override
	public boolean undoTool() {
		action(moveUp);
		return true;
	}

}
