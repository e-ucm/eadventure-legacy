package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.SceneLooksPanel;

public class ChangeAllowPlayerInSceneTool extends Tool{

	private Controller controller;
	
	private boolean isAllow;
	
	private SceneLooksPanel looksPanel;
	
	private SceneDataControl scene ;
	
	public ChangeAllowPlayerInSceneTool(boolean isAllow,SceneLooksPanel looksPanel,SceneDataControl scene ){
		controller = Controller.getInstance();
		this.isAllow = isAllow;
		this.looksPanel = looksPanel;
		this.scene = scene;
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
		action(isAllow);
		return true;
	}

	private void action(boolean al){
		scene.setAllowPlayerLayer(al);
		//if it is not allow that the player has layer, delete it in all references container
		if (al){
			scene.addPlayerInReferenceList();
		}
		else{	
			scene.deletePlayerInReferenceList();
		}
		if (looksPanel != null)
			looksPanel.addPlayer();
		
		controller.updatePanel();
		/*looksPanel = new SceneLooksPanel(looksPanel.getSceneDataControl());
		tabPanel.remove(0);
		tabPanel.insertTab( TextConstants.getText( "Scene.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "Scene.LookPanelTip" ), 0 );
		*/
	}
	
	@Override
	public boolean redoTool() {
		
		return doTool();
	}

	@Override
	public boolean undoTool() {
		action(!isAllow);
		return true;
	}

}
