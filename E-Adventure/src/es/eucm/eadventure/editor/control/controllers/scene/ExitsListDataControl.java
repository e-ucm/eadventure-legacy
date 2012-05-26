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
package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ExitsListDataControl extends DataControl {

    /**
     * Scene controller that contains this element reference.
     */
    private SceneDataControl sceneDataControl;

    /**
     * List of exits.
     */
    private List<Exit> exitsList;

    /**
     * List of exits controllers.
     */
    private List<ExitDataControl> exitsDataControlList;

    /**
     * Constructor.
     * 
     * @param sceneDataControl
     *            Link to the parent scene controller
     * @param exitsList
     *            List of exits
     */
    public ExitsListDataControl( SceneDataControl sceneDataControl, List<Exit> exitsList ) {

        this.sceneDataControl = sceneDataControl;
        this.exitsList = exitsList;

        // Create subcontrollers
        exitsDataControlList = new ArrayList<ExitDataControl>( );
        for( Exit exit : exitsList )
            exitsDataControlList.add( new ExitDataControl( sceneDataControl, exit ) );
    }

    /**
     * Returns the list of exits controllers.
     * 
     * @return List of exits controllers
     */
    public List<ExitDataControl> getExits( ) {

        return exitsDataControlList;
    }

    /**
     * Returns the last exit controller from the list.
     * 
     * @return Last exit controller
     */
    public ExitDataControl getLastExit( ) {

        return exitsDataControlList.get( exitsDataControlList.size( ) - 1 );
    }

    /**
     * Returns the id of the scene that contains this exits list.
     * 
     * @return Parent scene id
     */
    public String getParentSceneId( ) {

        return sceneDataControl.getId( );
    }

    @Override
    public Object getContent( ) {

        return exitsList;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.EXIT };
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new exit
        return type == Controller.EXIT;
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

        if( type == Controller.EXIT ) {
            String[] generalScenes = controller.getIdentifierSummary( ).getGeneralSceneIds( );

            if( generalScenes.length > 0 ) {
                String selectedScene = controller.showInputDialog( TC.get( "Operation.AddNextSceneTitle" ), TC.get( "Operation.AddNextSceneMessage" ), generalScenes );
                if( selectedScene != null ) {
                    Exit newExit = new Exit( true, 240, 240, 100, 100 );
                    newExit.setNextSceneId( selectedScene );
                    ExitDataControl newExitDataControl = new ExitDataControl( sceneDataControl, newExit );

                    exitsList.add( newExit );
                    exitsDataControlList.add( newExitDataControl );
                    elementAdded = true;
                }
            }

        }

        return elementAdded;
    }

    @Override
    public boolean duplicateElement( DataControl dataControl ) {

        if( !( dataControl instanceof ExitDataControl ) )
            return false;

        try {
            Exit newElement = (Exit) ( ( (Exit) ( dataControl.getContent( ) ) ).clone( ) );
            exitsList.add( newElement );
            exitsDataControlList.add( new ExitDataControl( sceneDataControl, newElement ) );
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone exit" );
            return false;
        }
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;

        if( exitsList.remove( dataControl.getContent( ) ) ) {
            exitsDataControlList.remove( dataControl );
            //controller.dataModified( );
            elementDeleted = true;
        }

        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = exitsList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            exitsList.add( elementIndex - 1, exitsList.remove( elementIndex ) );
            exitsDataControlList.add( elementIndex - 1, exitsDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = exitsList.indexOf( dataControl.getContent( ) );

        if( elementIndex < exitsList.size( ) - 1 ) {
            exitsList.add( elementIndex + 1, exitsList.remove( elementIndex ) );
            exitsDataControlList.add( elementIndex + 1, exitsDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Iterate through each exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            exitDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Iterate through the exits
        for( int i = 0; i < exitsDataControlList.size( ); i++ ) {
            String exitPath = currentPath + " >> " + TC.getElement( Controller.EXIT ) + " #" + ( i + 1 );
            valid &= exitsDataControlList.get( i ).isValid( exitPath, incidences );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through each exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            count += exitDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through each exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            exitDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through each exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            exitDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Iterate through each exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            count += exitDataControl.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Iterate through each exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            exitDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Spread the call to every exit
        for( ExitDataControl exitDataControl : exitsDataControlList )
            exitDataControl.deleteIdentifierReferences( id );

        int i = 0;
        while( i < exitsList.size( ) ) {
            if( exitsList.get( i ).getNextSceneId( ) == null ) {
                exitsList.remove( i );
                exitsDataControlList.remove( i );
            }
            else
                i++;
        }
    }

    @Override
    public boolean canBeDuplicated( ) {

        return false;
    }

    /**
     * Returns the data controllers of the item references of the scene that
     * contains this element reference.
     * 
     * @return List of item references (including the one being edited)
     */
    public List<ElementReferenceDataControl> getParentSceneItemReferences( ) {

        return sceneDataControl.getReferencesList( ).getItemReferences( );
    }

    /**
     * Returns the data controllers of the character references of the scene
     * that contains this element reference.
     * 
     * @return List of character references (including the one being edited)
     */
    public List<ElementReferenceDataControl> getParentSceneNPCReferences( ) {

        return sceneDataControl.getReferencesList( ).getNPCReferences( );
    }

    /**
     * Returns the data controllers of the atrezzo items references of the scene
     * that contains this element reference.
     * 
     * @return List of atrezzo references (including the one being edited)
     */
    public List<ElementReferenceDataControl> getParentSceneAtrezzoReferences( ) {

        return sceneDataControl.getReferencesList( ).getAtrezzoReferences( );
    }

    public List<BarrierDataControl> getParentSceneBarriers( ) {

        return sceneDataControl.getBarriersList( ).getBarriers( );
    }

    public List<ActiveAreaDataControl> getParentSceneActiveAreas( ) {

        return sceneDataControl.getActiveAreasList( ).getActiveAreas( );
    }

    @Override
    public void recursiveSearch( ) {

        for( DataControl dc : this.exitsDataControlList )
            dc.recursiveSearch( );
    }

    public SceneDataControl getSceneDataControl( ) {

        return sceneDataControl;
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, exitsDataControlList );
    }

    public List<Exit> getExitsList( ) {

        return this.exitsList;
    }

}
