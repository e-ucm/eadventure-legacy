/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers.animation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.animation.ChangeImageURITool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeSoundUriTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeTimeTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeWaitForClick;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class FrameDataControl extends DataControlWithResources {

    private Frame frame;

    public FrameDataControl( Frame frame ) {

        this.frame = frame;
        resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
        for( Resources resources : frame.getResources( ) ) {
            resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.ANIMATION ) );
        }
    }

    public long getTime( ) {

        return frame.getTime( );
    }

    public void setTime( long time ) {

        Controller.getInstance( ).addTool( new ChangeTimeTool( frame, time ) );
    }

    public void setImageURI( String uri ) {

        Controller.getInstance( ).addTool( new ChangeImageURITool( frame, uri ) );
    }

    public String getImageURI( ) {

        return frame.getUri( );
    }

    public boolean isWaitForClick( ) {

        return frame.isWaitforclick( );
    }

    public void setWaitForClick( boolean waitForClick ) {

        Controller.getInstance( ).addTool( new ChangeWaitForClick( frame, waitForClick ) );
    }

    public String getSoundUri( ) {

        return frame.getSoundUri( );
    }

    public void setSoundURI( String uri ) {

        Controller.getInstance( ).addTool( new ChangeSoundUriTool( frame, uri ) );
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
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
    public boolean canBeDuplicated( ) {

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
    public int countAssetReferences( String assetPath ) {

        return 0;
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return 0;
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

    }

    @Override
    public int[] getAddableElements( ) {

        return null;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

    }

    @Override
    public Object getContent( ) {

        return frame;
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return false;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public void recursiveSearch( ) {

    }

    @Override
    public String renameElement( String newName ) {

        return null;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

    }

    @Override
    public ResourcesDataControl getLastResources( ) {

        if( resourcesDataControlList.size( ) == 0 )
            return null;
        return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, resourcesDataControlList );
    }

}
