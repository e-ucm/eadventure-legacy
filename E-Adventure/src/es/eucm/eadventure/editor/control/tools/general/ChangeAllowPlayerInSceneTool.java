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
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.SceneLooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ChangeAllowPlayerInSceneTool extends Tool{

	private Controller controller;
	
	private boolean isAllow;
	
	private ScenePreviewEditionPanel scenePreviewEditionPanel;
	
	private SceneDataControl scene ;
	
	public ChangeAllowPlayerInSceneTool(boolean isAllow,ScenePreviewEditionPanel scenePreviewEditionPanel,SceneDataControl scene ){
		controller = Controller.getInstance();
		this.isAllow = isAllow;
		this.scenePreviewEditionPanel = scenePreviewEditionPanel;
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
		// Now, the player is always showed in scenePreviewEditionPanel
		//if (scenePreviewEditionPanel != null){
		  //  if (!Controller.getInstance().isPlayTransparent())
			//scenePreviewEditionPanel.addPlayer(scene, scene.getReferencesList().getPlayerImage());
		//scenePreviewEditionPanel.repaint();
		//}
		
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
