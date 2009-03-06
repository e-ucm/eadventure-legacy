package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import java.util.ArrayList;
import java.util.List;

public class SwapPlayerModeTool extends Tool{

	private boolean showConfirmation;
	
	private Controller controller;
	
	private AdventureDataControl adventureData;
	
	private ChapterListDataControl chapterDataControlList;
	
	private ArrayList<Integer> oldPlayerLayer;
	
	public SwapPlayerModeTool ( boolean showConfirmation, AdventureDataControl adventureData, ChapterListDataControl chapterDataControlList){
		this.showConfirmation = showConfirmation;
		this.adventureData = adventureData;
		controller = Controller.getInstance();
		this.chapterDataControlList = chapterDataControlList;
		setGlobal(true);
		oldPlayerLayer = new ArrayList<Integer>();
		//Take the player layer in each scene
		for (SceneDataControl scene:chapterDataControlList.getSelectedChapterDataControl().getScenesList().getScenes()){
			oldPlayerLayer.add(scene.getPlayerLayer());
		}
	
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
		boolean done = action();
		controller.updatePanel();
		return done;
	}
	
	@Override
	public boolean redoTool() {
		boolean done = action();
		controller.updatePanel();
		return done;
	}

	@Override
	public boolean undoTool() {
		showConfirmation = false;		
		boolean done = action();
		// Restore the old values
		for (int i=0;i<oldPlayerLayer.size();i++){
			Integer layer = oldPlayerLayer.get(i);
			Scene scene = chapterDataControlList.getSelectedChapterData().getScenes().get(i);
			// set the previous player layer in each scene
			scene.setPlayerLayer(layer);
			// put the player in the correct position in referencesList container
			if (layer!=Scene.PLAYER_NO_ALLOWED&&layer!=Scene.PLAYER_WITHOUT_LAYER){
			chapterDataControlList.getSelectedChapterDataControl().getScenesList().getScenes().get(i).insertPlayer();
			}
			// set if the player is not allowed in the scene
			scene.setAllowPlayerLayer(oldPlayerLayer.get(i)!=Scene.PLAYER_NO_ALLOWED);
		}
		controller.updatePanel();
		return done;
	}
	
	private boolean action(){
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

}
