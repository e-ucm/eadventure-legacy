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
package es.eucm.eadventure.editor.control.controllers.cutscene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.GeneralScene;
import es.eucm.eadventure.common.data.chapter.scenes.Slidescene;
import es.eucm.eadventure.common.data.chapter.scenes.Videoscene;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.editdialogs.CutsceneTypesDialog;

public class CutscenesListDataControl extends DataControl {

    /**
     * List of cutscenes.
     */
    private List<Cutscene> cutscenesList;

    /**
     * List of cutscene controllers.
     */
    private List<CutsceneDataControl> cutscenesDataControlList;

    /**
     * Constructor.
     * 
     * @param cutscenesList
     *            List of cutscenes
     */
    public CutscenesListDataControl( List<Cutscene> cutscenesList ) {

        this.cutscenesList = cutscenesList;

        // Create subcontrollers
        cutscenesDataControlList = new ArrayList<CutsceneDataControl>( );
        for( Cutscene cutscene : cutscenesList )
            cutscenesDataControlList.add( new CutsceneDataControl( cutscene ) );
    }

    /**
     * Returns the list of cutscene controllers.
     * 
     * @return Cutscene controllers
     */
    public List<CutsceneDataControl> getCutscenes( ) {

        return cutscenesDataControlList;
    }

    /**
     * Returns the last cutscene controller of the list.
     * 
     * @return Last cutscene controller
     */
    public CutsceneDataControl getLastCutscene( ) {

        return cutscenesDataControlList.get( cutscenesDataControlList.size( ) - 1 );
    }

    /**
     * Returns the info of the cutscenes contained in the list.
     * 
     * @return Array with the information of the cutscenes. It contains the
     *         identifier of each cutscene, and its type
     */
    public String[][] getCutscenesInfo( ) {

        String[][] cutscenesInfo = null;

        // Create the list for the cutscenes
        cutscenesInfo = new String[ cutscenesList.size( ) ][ 2 ];

        // Fill the array with the info
        for( int i = 0; i < cutscenesList.size( ); i++ ) {
            Cutscene cutscene = cutscenesList.get( i );
            cutscenesInfo[i][0] = cutscene.getId( );
            if( cutscene.getType( ) == GeneralScene.SLIDESCENE )
                cutscenesInfo[i][1] = TC.get( "CutscenesList.Slidescene" );
            else if( cutscene.getType( ) == GeneralScene.VIDEOSCENE )
                cutscenesInfo[i][1] = TC.get( "CutscenesList.Videoscene" );

        }

        return cutscenesInfo;
    }

    @Override
    public Object getContent( ) {

        return cutscenesList;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.CUTSCENE };
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new cutscenes
        return type == Controller.CUTSCENE;
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
    public boolean addElement( int type, String cutsceneId ) {

        boolean elementAdded = false;

        if( type == Controller.CUTSCENE ) {

            CutsceneTypesDialog cutscenesTypesDialog = new CutsceneTypesDialog( );
            type = cutscenesTypesDialog.getOptionSelected( );

            if( type == Controller.CUTSCENE_SLIDES ) {

                // Show a dialog asking for the cutscene id
                if( cutsceneId == null )
                    cutsceneId = controller.showInputDialog( TC.get( "Operation.AddCutsceneTitle" ), TC.get( "Operation.AddCutsceneMessage" ), TC.get( "Operation.AddCutsceneDefaultValue" ) );

                // If some value was typed and the identifier is valid
                if( cutsceneId != null && controller.isElementIdValid( cutsceneId ) ) {
                    Cutscene newCutscene = null;

                    // Create the new cutscene
                    if( type == Controller.CUTSCENE_SLIDES )
                        newCutscene = new Slidescene( cutsceneId );

                    // Add the new cutscene
                    cutscenesList.add( newCutscene );
                    cutscenesDataControlList.add( new CutsceneDataControl( newCutscene ) );
                    controller.getIdentifierSummary( ).addCutsceneId( cutsceneId );
                    //controller.dataModified( );
                    elementAdded = true;
                }
            }

            else if( type == Controller.CUTSCENE_VIDEO ) {

                // Show a dialog asking for the cutscene id
                if( cutsceneId == null )
                    cutsceneId = controller.showInputDialog( TC.get( "Operation.AddCutsceneTitle" ), TC.get( "Operation.AddCutsceneMessage" ), TC.get( "Operation.AddCutsceneDefaultValue" ) );

                // If some value was typed and the identifier is valid
                if( cutsceneId != null && controller.isElementIdValid( cutsceneId ) ) {
                    Cutscene newCutscene = null;

                    // Create the new cutscene
                    if( type == Controller.CUTSCENE_VIDEO )
                        newCutscene = new Videoscene( cutsceneId );

                    // Add the new cutscene
                    cutscenesList.add( newCutscene );
                    cutscenesDataControlList.add( new CutsceneDataControl( newCutscene ) );
                    controller.getIdentifierSummary( ).addCutsceneId( cutsceneId );
                    //controller.dataModified( );
                    elementAdded = true;
                }
            }
        }

        return elementAdded;
    }

    @Override
    public String getDefaultId( int type ) {

        return TC.get( "Operation.AddCutsceneDefaultValue" );
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;

        // Take the number of general scenes in the chapter
        int generalScenesCount = controller.getIdentifierSummary( ).getGeneralSceneIds( ).length;

        // If there are at least two scenes, this one can be deleted
        if( generalScenesCount > 1 ) {
            String cutsceneId = ( (CutsceneDataControl) dataControl ).getId( );
            String references = String.valueOf( controller.countIdentifierReferences( cutsceneId ) );

            // Ask for confirmation
            if( !askConfirmation || controller.showStrictConfirmDialog( TC.get( "Operation.DeleteElementTitle" ), TC.get( "Operation.DeleteElementWarning", new String[] { cutsceneId, references } ) ) ) {
                if( cutscenesList.remove( dataControl.getContent( ) ) ) {
                    cutscenesDataControlList.remove( dataControl );
                    controller.deleteIdentifierReferences( cutsceneId );
                    controller.getIdentifierSummary( ).deleteCutsceneId( cutsceneId );
                    //controller.dataModified( );
                    elementDeleted = true;
                }
            }
        }

        // If this is the last scene, it can't be deleted
        else
            controller.showErrorDialog( TC.get( "Operation.DeleteElementTitle" ), TC.get( "Operation.ErrorLastScene" ) );

        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = cutscenesList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            cutscenesList.add( elementIndex - 1, cutscenesList.remove( elementIndex ) );
            cutscenesDataControlList.add( elementIndex - 1, cutscenesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = cutscenesList.indexOf( dataControl.getContent( ) );

        if( elementIndex < cutscenesList.size( ) - 1 ) {
            cutscenesList.add( elementIndex + 1, cutscenesList.remove( elementIndex ) );
            cutscenesDataControlList.add( elementIndex + 1, cutscenesDataControlList.remove( elementIndex ) );
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

        // Iterate through each cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            cutsceneDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Update the current path
        currentPath += " >> " + TC.getElement( Controller.CUTSCENES_LIST );

        // Iterate through the cutscenes
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList ) {
            String cutscenePath = currentPath + " >> " + cutsceneDataControl.getId( );
            valid &= cutsceneDataControl.isValid( cutscenePath, incidences );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through each cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            count += cutsceneDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through each cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            cutsceneDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through each cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            cutsceneDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Iterate through each cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            count += cutsceneDataControl.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Iterate through each cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            cutsceneDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Spread the call to every cutscene
        for( CutsceneDataControl cutsceneDataControl : cutscenesDataControlList )
            cutsceneDataControl.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return false;
    }

    @Override
    public void recursiveSearch( ) {

        for( DataControl dc : this.cutscenesDataControlList )
            dc.recursiveSearch( );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, cutscenesDataControlList );
    }
    
    public List<CutsceneDataControl> getAllCutsceneDataControls(){
        return cutscenesDataControlList;
    }

}
