package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SwapPlayerModeTool extends Tool{

	private boolean showConfirmation;
	
	private Controller controller;
	
	private AdventureDataControl adventureData;
	
	private ChapterListDataControl chapterDataControlList;
	
	public SwapPlayerModeTool ( boolean showConfirmation, AdventureDataControl adventureData, ChapterListDataControl chapterDataControlList){
		this.showConfirmation = showConfirmation;
		this.adventureData = adventureData;
		controller = Controller.getInstance();
		this.chapterDataControlList = chapterDataControlList;
		setGlobal(true);
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
		boolean swap = true;
		if( showConfirmation )
			swap = controller.showStrictConfirmDialog( TextConstants.getText( "SwapPlayerMode.Title" ), TextConstants.getText( "SwapPlayerMode.Message" ) );

		if( swap ) {
			if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON ) {
				adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
				// adds player reference
				chapterDataControlList.addPlayerToAllScenesChapters();
				return true;
			} else if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON ) {
				adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
				// delete player reference
				chapterDataControlList.deletePlayerToAllScenesChapters();
				return true;
			}
		}
		
		return false;

	}

	@Override
	public boolean redoTool() {
		boolean done = doTool();
		controller.updatePanel();
		return done;
	}

	@Override
	public boolean undoTool() {
		showConfirmation = false;
		boolean done = doTool();
		controller.updatePanel();
		return done;
	}

}
