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

import java.util.ArrayList;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SwapPlayerModeTool extends Tool {

    private boolean showConfirmation;

    private Controller controller;

    private AdventureDataControl adventureData;

    private ChapterListDataControl chapterDataControlList;

    private ArrayList<Integer> oldPlayerLayer;

    public SwapPlayerModeTool( boolean showConfirmation, AdventureDataControl adventureData, ChapterListDataControl chapterDataControlList ) {

        this.showConfirmation = showConfirmation;
        this.adventureData = adventureData;
        controller = Controller.getInstance( );
        this.chapterDataControlList = chapterDataControlList;
        oldPlayerLayer = new ArrayList<Integer>( );
        //Take the player layer in each scene
        for( SceneDataControl scene : chapterDataControlList.getSelectedChapterDataControl( ).getScenesList( ).getScenes( ) ) {
            oldPlayerLayer.add( scene.getPlayerLayer( ) );
        }

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

        boolean done = action( );
        return done;
    }

    @Override
    public boolean redoTool( ) {

        boolean done = action( );
        controller.reloadData( );
        return done;
    }

    @Override
    public boolean undoTool( ) {

        showConfirmation = false;
        boolean done = action( );
        // Restore the old values
        for( int i = 0; i < oldPlayerLayer.size( ); i++ ) {
            Integer layer = oldPlayerLayer.get( i );
            Scene scene = chapterDataControlList.getSelectedChapterData( ).getScenes( ).get( i );
            // set the previous player layer in each scene
            scene.setPlayerLayer( layer );
            // put the player in the correct position in referencesList container
            if( layer != Scene.PLAYER_NO_ALLOWED && layer != Scene.PLAYER_WITHOUT_LAYER ) {
                chapterDataControlList.getSelectedChapterDataControl( ).getScenesList( ).getScenes( ).get( i ).insertPlayer( );
            }
            // set if the player is not allowed in the scene
            //scene.setAllowPlayerLayer( oldPlayerLayer.get( i ) != Scene.PLAYER_NO_ALLOWED );
        }
        controller.reloadData( );

        return done;
    }

    private boolean action( ) {

        boolean swap = true;
        if( showConfirmation )
            swap = controller.showStrictConfirmDialog( TC.get( "SwapPlayerMode.Title" ), TC.get( "SwapPlayerMode.Message" ) );

        if( swap ) {
            if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON ) {
                adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
                // adds player reference
                chapterDataControlList.addPlayerToAllScenesChapters( );
                //adds "standright" and "standright" resources of the player as necessary
                controller.changePlayerNecessaryResources(true);
                return true;
            }
            else if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON ) {
                adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
                // delete player reference
                chapterDataControlList.deletePlayerToAllScenesChapters( );
                //remove "standright" and "standright" resources of the player as necessary
                controller.changePlayerNecessaryResources(false);
                return true;
            }
        }

        return false;

    }

}
