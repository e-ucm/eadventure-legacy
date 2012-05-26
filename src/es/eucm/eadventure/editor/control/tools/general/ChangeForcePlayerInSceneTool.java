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
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeForcePlayerInSceneTool extends Tool {

    private Controller controller;

    private boolean isAllow;

    //private ScenePreviewEditionPanel scenePreviewEditionPanel;

    private SceneDataControl scene;

    public ChangeForcePlayerInSceneTool( boolean isAllow, /*ScenePreviewEditionPanel scenePreviewEditionPanel,*/ SceneDataControl scene ) {

        controller = Controller.getInstance( );
        this.isAllow = isAllow;
        //this.scenePreviewEditionPanel = scenePreviewEditionPanel;
        this.scene = scene;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        action( isAllow );
        return true;
    }

    private void action( boolean al ) {

        if (!al)
            scene.setPlayerLayer( Scene.PLAYER_WITHOUT_LAYER );
        else
            scene.setPlayerLayer( 0 );
            
        //if it is not allow that the player has layer, delete it in all references container
        if( al ) {
            scene.addPlayerInReferenceList( );
        }
        else {
            scene.deletePlayerInReferenceList( );
        }
        // Now, the player is always showed in scenePreviewEditionPanel
        //if (scenePreviewEditionPanel != null){
        //  if (!Controller.getInstance().isPlayTransparent())
        //scenePreviewEditionPanel.addPlayer(scene, scene.getReferencesList().getPlayerImage());
        //scenePreviewEditionPanel.repaint();
        //}

        controller.updatePanel( );
        /*looksPanel = new SceneLooksPanel(looksPanel.getSceneDataControl());
        tabPanel.remove(0);
        tabPanel.insertTab( TextConstants.getText( "Scene.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "Scene.LookPanelTip" ), 0 );
        */
    }

    @Override
    public boolean redoTool( ) {

        return doTool( );
    }

    @Override
    public boolean undoTool( ) {

        action( !isAllow );
        return true;
    }

}
