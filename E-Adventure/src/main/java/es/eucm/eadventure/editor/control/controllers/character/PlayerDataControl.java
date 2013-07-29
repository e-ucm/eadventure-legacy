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
package es.eucm.eadventure.editor.control.controllers.character;

import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;

public class PlayerDataControl extends NPCDataControl {

    /**
     * Contained player data.
     */
    private Player player;

    /**
     * Constructor.
     * 
     * @param player
     *            Contained player data
     */
    public PlayerDataControl( Player player ) {

        super( player );
        this.player = player;
    }

    /**
     * Notify to all scenes that the player image has been changed
     */
    public void playerImageChange( ) {

        String preview = getPreviewImage( );
        if( preview != null ) {
            for( SceneDataControl scene : Controller.getInstance( ).getSelectedChapterDataControl( ).getScenesList( ).getScenes( ) ) {
                scene.imageChangeNotify( preview );
            }
        }
    }

    @Override
    public Object getContent( ) {

        return player;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return false;
    }

    @Override
    public boolean canBeMoved( ) {

        return false;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;

        if( type == Controller.RESOURCES && !Controller.getInstance( ).isPlayTransparent( ) ) {
            return super.addElement( type, id );
        }

        return elementAdded;
    }

    @Override
    public boolean buildResourcesTab( ) {

        return !Controller.getInstance( ).isPlayTransparent( );
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }


    @Override
    public boolean canBeDuplicated( ) {

        return false;

    }

}
