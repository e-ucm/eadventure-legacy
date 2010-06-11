/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers.character;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

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
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.countIdentifierReferences( id );
        return count;

    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.replaceIdentifierReferences( oldId, newId );

    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteIdentifierReferences( id );

    }

    @Override
    public boolean canBeDuplicated( ) {

        return false;

    }

    @Override
    public void recursiveSearch( ) {

        super.recursiveSearch( );
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );

    }

}
