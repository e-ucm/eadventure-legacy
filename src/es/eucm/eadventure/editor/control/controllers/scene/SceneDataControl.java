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

import es.eucm.eadventure.common.auxiliar.AssetsImageDimensions;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeForcePlayerInSceneTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeNSDestinyPositionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangePositionTool;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
import es.eucm.eadventure.editor.control.tools.scene.ChangePlayerScaleTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class SceneDataControl extends DataControlWithResources {

    /**
     * Contained scene data.
     */
    private Scene scene;

    /**
     * Exits list controller.
     */
    private ExitsListDataControl exitsListDataControl;

    /**
     * Element references list controller.
     */
    private ReferencesListDataControl referencesListDataControl;

    /**
     * Active areas list controller
     */
    private ActiveAreasListDataControl activeAreasListDataControl;

    /**
     * Barriers list controller
     */
    private BarriersListDataControl barriersListDataControl;

    private TrajectoryDataControl trajectoryDataControl;

    /**
     * Constructor.
     * 
     * @param scene
     *            Contained scene data
     */
    public SceneDataControl( Scene scene, String playerImagePath ) {

        this.scene = scene;
        this.resourcesList = scene.getResources( );

        selectedResources = 0;

        // Add a new resource if the list is empty
        if( resourcesList.size( ) == 0 )
            resourcesList.add( new Resources( ) );

        // Create the subcontrollers
        resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
        for( Resources resources : resourcesList )
            resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.SCENE ) );

        exitsListDataControl = new ExitsListDataControl( this, scene.getExits( ) );
        referencesListDataControl = new ReferencesListDataControl( playerImagePath, this, scene.getItemReferences( ), scene.getAtrezzoReferences( ), scene.getCharacterReferences( ) );
        activeAreasListDataControl = new ActiveAreasListDataControl( this, scene.getActiveAreas( ) );
        barriersListDataControl = new BarriersListDataControl( this, scene.getBarriers( ) );
        trajectoryDataControl = new TrajectoryDataControl( this, scene.getTrajectory( ) );
    }

    /**
     * Returns the exits list controller.
     * 
     * @return Exits list controller
     */
    public ExitsListDataControl getExitsList( ) {

        return exitsListDataControl;
    }

    /**
     * Returns the active areas list controller.
     * 
     * @return Exits list controller
     */
    public ActiveAreasListDataControl getActiveAreasList( ) {

        return this.activeAreasListDataControl;
    }

    /**
     * Returns the barriers list controller.
     * 
     * @return Barriers list controller
     */
    public BarriersListDataControl getBarriersList( ) {

        return this.barriersListDataControl;
    }

    /**
     * Returns the item references list controller.
     * 
     * @return Item references list controller
     */
    public ReferencesListDataControl getReferencesList( ) {

        return referencesListDataControl;
    }

    /**
     * Returns the path to the selected preview image.
     * 
     * @return Path to the image, null if not present
     */
    public String getPreviewBackground( ) {

        return resourcesDataControlList.get( selectedResources ).getAssetPath( "background" );
    }

    /**
     * Returns the id of the scene.
     * 
     * @return Scene's id
     */
    public String getId( ) {

        return scene.getId( );
    }

    /**
     * Returns the documentation of the scene.
     * 
     * @return Scene's documentation
     */
    public String getDocumentation( ) {

        return scene.getDocumentation( );
    }

    /**
     * Returns the name of the scene.
     * 
     * @return Scene's name
     */
    public String getName( ) {

        return scene.getName( );
    }

    /**
     * Returns whether the scene has a default initial position or not.
     * 
     * @return True if the scene has an initial position, false otherwise
     */
    public boolean hasDefaultInitialPosition( ) {

        return scene.hasDefaultPosition( );
    }

    /**
     * Returns the X coordinate of the default initial position
     * 
     * @return X coordinate of the initial position
     */
    public int getDefaultInitialPositionX( ) {

        return scene.getPositionX( );
    }

    /**
     * Returns the Y coordinate of the default initial position
     * 
     * @return Y coordinate of the initial position
     */
    public int getDefaultInitialPositionY( ) {

        return scene.getPositionY( );
    }

    /**
     * Sets the new name of the scene.
     * 
     * @param name
     *            Name of the scene
     */
    public void setName( String name ) {

        ChangeNameTool tool = new ChangeNameTool( scene, name );
        controller.addTool( tool );
    }

    public void imageChangeNotify( String imagePath ) {

        this.referencesListDataControl.changeImagePlayerPath( imagePath );
    }

    /**
     * Sets the new documentation of the scene.
     * 
     * @param documentation
     *            Documentation of the scene
     */
    public void setDocumentation( String documentation ) {

        ChangeDocumentationTool tool = new ChangeDocumentationTool( scene, documentation );
        controller.addTool( tool );
    }

    /**
     * Toggles the default initial position. If the scene has an initial
     * position deletes it, if it doesn't have one, set initial values for it.
     */
    public void toggleDefaultInitialPosition( ) {

        if( scene.hasDefaultPosition( ) )
            controller.addTool( new ChangeNSDestinyPositionTool( scene, Integer.MIN_VALUE, Integer.MIN_VALUE ) );
        else
            controller.addTool( new ChangeNSDestinyPositionTool( scene, AssetsImageDimensions.BACKGROUND_MAX_WIDTH/2 , AssetsImageDimensions.BACKGROUND_MAX_HEIGHT/2 ) );
    }

    /**
     * Sets the new default initial position of the scene.
     * 
     * @param positionX
     *            X coordinate of the initial position
     * @param positionY
     *            Y coordinate of the initial position
     */
    public void setDefaultInitialPosition( int positionX, int positionY ) {

        controller.addTool( new ChangePositionTool( scene, positionX, positionY ) );
    }

    @Override
    public Object getContent( ) {

        return scene;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { /*Controller.RESOURCES*/};
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new resources
        //return type == Controller.RESOURCES;
        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return true;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;

        if( type == Controller.RESOURCES ) {
            elementAdded = Controller.getInstance( ).addTool( new AddResourcesBlockTool( resourcesList, resourcesDataControlList, Controller.SCENE, this ) );
        }

        return elementAdded;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex < resourcesList.size( ) - 1 ) {
            resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        boolean elementRenamed = false;
        String oldSceneId = scene.getId( );
        String references = String.valueOf( controller.countIdentifierReferences( oldSceneId ) );

        // Ask for confirmation
        if( name != null || controller.showStrictConfirmDialog( TC.get( "Operation.RenameSceneTitle" ), TC.get( "Operation.RenameElementWarning", new String[] { oldSceneId, references } ) ) ) {

            // Show a dialog asking for the new scene id
            String newSceneId = name;
            if( name == null )
                newSceneId = controller.showInputDialog( TC.get( "Operation.RenameSceneTitle" ), TC.get( "Operation.RenameSceneMessage" ), oldSceneId );

            // If some value was typed and the identifiers are different
            if( newSceneId != null && !newSceneId.equals( oldSceneId ) && controller.isElementIdValid( newSceneId ) ) {
                scene.setId( newSceneId );
                controller.replaceIdentifierReferences( oldSceneId, newSceneId );
                controller.getIdentifierSummary( ).deleteSceneId( oldSceneId );
                controller.getIdentifierSummary( ).addSceneId( newSceneId );
                //controller.dataModified( );
                elementRenamed = true;
            }
        }

        if( elementRenamed )
            return oldSceneId;
        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Update the flag summary with the exits, item and character references
        exitsListDataControl.updateVarFlagSummary( varFlagSummary );
        referencesListDataControl.updateVarFlagSummary( varFlagSummary );
        activeAreasListDataControl.updateVarFlagSummary( varFlagSummary );
        barriersListDataControl.updateVarFlagSummary( varFlagSummary );
        trajectoryDataControl.updateVarFlagSummary( varFlagSummary );
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.updateVarFlagSummary( varFlagSummary );

    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Iterate through the resources
        for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
            String resourcesPath = currentPath + " >> " + TC.getElement( Controller.RESOURCES ) + " #" + ( i + 1 );
            valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
        }

        // Spread the call to the exits
        valid &= exitsListDataControl.isValid( currentPath, incidences );
        valid &= trajectoryDataControl.isValid( currentPath, incidences );

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countAssetReferences( assetPath );

        // Increase the counter with the references in the exits
        count += exitsListDataControl.countAssetReferences( assetPath );
        count += activeAreasListDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Do nothing
        exitsListDataControl.getAssetReferences( assetPaths, assetTypes );

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );

        // Delete the references in the exits
        exitsListDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Increase the counter for the exits, items and characters
        count += exitsListDataControl.countIdentifierReferences( id );
        count += referencesListDataControl.countIdentifierReferences( id );
        count += activeAreasListDataControl.countIdentifierReferences( id );
        count += barriersListDataControl.countIdentifierReferences( id );
        count += trajectoryDataControl.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        exitsListDataControl.replaceIdentifierReferences( oldId, newId );
        referencesListDataControl.replaceIdentifierReferences( oldId, newId );
        activeAreasListDataControl.replaceIdentifierReferences( oldId, newId );
        barriersListDataControl.replaceIdentifierReferences( oldId, newId );
        trajectoryDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        exitsListDataControl.deleteIdentifierReferences( id );
        referencesListDataControl.deleteIdentifierReferences( id );
        activeAreasListDataControl.deleteIdentifierReferences( id );
        barriersListDataControl.deleteIdentifierReferences( id );
        trajectoryDataControl.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    public TrajectoryDataControl getTrajectory( ) {

        return trajectoryDataControl;
    }

    public void setTrajectoryDataControl( TrajectoryDataControl trajectoryDataControl ) {

        this.trajectoryDataControl = trajectoryDataControl;
    }

    public void setTrajectory( Trajectory trajectory ) {

        scene.setTrajectory( trajectory );
    }

    public void setPlayerLayer( int layer ) {

        scene.setPlayerLayer( layer );
    }

    public int getPlayerLayer( ) {

        return scene.getPlayerLayer( );
    }

 /*   public void setAllowPlayerLayer( boolean allow ) {

        scene.setAllowPlayerLayer( allow );
    }*/
    
    public boolean isForcedPlayerLayer( ){
        
        return scene.isForcedPlayerLayer( );
    
    }

    public boolean isAllowPlayer( ) {

        return scene.isAllowPlayerLayer( );
    }

    public void deletePlayerInReferenceList( ) {

        referencesListDataControl.deletePlayer( );
    }

    public void addPlayerInReferenceList( ) {

        referencesListDataControl.addPlayer( );

    }

    public void changeAllowPlayerLayer( boolean isAllowPlayerLayer /*, ScenePreviewEditionPanel scenePreviewEditionPanel^*/ ) {

        Controller.getInstance( ).addTool( new ChangeForcePlayerInSceneTool( isAllowPlayerLayer, /*scenePreviewEditionPanel,*/ this ) );
    }

    // this method is only used in SwapPlayerModeTool
    public void insertPlayer( ) {

        deletePlayerInReferenceList( );
        referencesListDataControl.restorePlayer( );
    }

    // this method is only used in SwapPlayerModeTool

    public void setPlayerScale( float scale ) {

        //scene.setPlayerScale(scale);
        Controller.getInstance( ).addTool( new ChangePlayerScaleTool( scene, scale ) );
    }

    public float getPlayerScale( ) {

        return scene.getPlayerScale( );
    }

    @Override
    public void recursiveSearch( ) {

        this.getActiveAreasList( ).recursiveSearch( );
        this.getBarriersList( ).recursiveSearch( );
        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getName( ), TC.get( "Search.Name" ) );
        check( this.getId( ), "ID" );
        this.getExitsList( ).recursiveSearch( );
        this.getReferencesList( ).recursiveSearch( );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, resourcesDataControlList );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, exitsListDataControl );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, referencesListDataControl );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, activeAreasListDataControl );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, barriersListDataControl );
        if( path != null )
            return path;
        return getPathFromChild( dataControl, trajectoryDataControl );
    }

}
