/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import java.util.ArrayList;

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
            scene.setAllowPlayerLayer( oldPlayerLayer.get( i ) != Scene.PLAYER_NO_ALLOWED );
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
                return true;
            }
            else if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON ) {
                adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
                // delete player reference
                chapterDataControlList.deletePlayerToAllScenesChapters( );
                return true;
            }
        }

        return false;

    }

}
