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

import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionContextProperty;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionOwner;
import es.eucm.eadventure.editor.control.controllers.general.ExitLookDataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeNSDestinyPositionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeRectangleValueTool;
import es.eucm.eadventure.editor.control.tools.general.areaedition.AddNewPointTool;
import es.eucm.eadventure.editor.control.tools.general.areaedition.ChangeRectangularValueTool;
import es.eucm.eadventure.editor.control.tools.general.areaedition.DeletePointTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeIntegerValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ExitDataControl extends DataControl implements RectangleArea {

    /**
     * Scene controller that contains this element reference (used to extract
     * the id of the scene).
     */
    private SceneDataControl sceneDataControl;

    /**
     * Contained exit.
     */
    private Exit exit;

    /**
     * Conditions controller.
     */
    private ConditionsController conditionsController;

    private ExitLookDataControl exitLookDataControl;

    private InfluenceAreaDataControl influenceAreaDataControl;

    private EffectsController effectsController;

    private EffectsController postEffectsController;

    private EffectsController notEffectsController;

    /**
     * Constructor.
     * 
     * @param sceneDataControl
     *            Parent scene controller
     * @param exit
     *            Exit of the data control structure
     */
    public ExitDataControl( SceneDataControl sceneDataControl, Exit exit ) {

        this.sceneDataControl = sceneDataControl;
        this.exit = exit;

        this.influenceAreaDataControl = new InfluenceAreaDataControl( sceneDataControl, exit.getInfluenceArea( ), this );
        effectsController = new EffectsController( exit.getEffects( ) );
        postEffectsController = new EffectsController( exit.getPostEffects( ) );
        notEffectsController = new EffectsController( exit.getNotEffects( ) );
        conditionsController = new ConditionsController( new Conditions( ) );
        exitLookDataControl = new ExitLookDataControl( exit );
    }

    /**
     * Returns the id of the scene that contains this element reference.
     * 
     * @return Parent scene id
     */
    public String getParentSceneId( ) {

        return sceneDataControl.getId( );
    }

    /**
     * Returns the X coordinate of the upper left position of the exit.
     * 
     * @return X coordinate of the upper left point
     */
    public int getX( ) {

        return exit.getX( );
    }

    /**
     * Returns the Y coordinate of the upper left position of the exit.
     * 
     * @return Y coordinate of the upper left point
     */
    public int getY( ) {

        return exit.getY( );
    }

    /**
     * Returns the width of the exit.
     * 
     * @return Width of the exit
     */
    public int getWidth( ) {

        return exit.getWidth( );
    }

    /**
     * Returns the height of the exit.
     * 
     * @return Height of the exit
     */
    public int getHeight( ) {

        return exit.getHeight( );
    }

    /**
     * Returns the documentation of the exit.
     * 
     * @return Exit's documentation
     */
    public String getDocumentation( ) {

        return exit.getDocumentation( );
    }

    /**
     * Sets the new values for the exit.
     * 
     * @param x
     *            X coordinate of the upper left point
     * @param y
     *            Y coordinate of the upper left point
     * @param width
     *            Width of the exit area
     * @param height
     *            Height of the exit area
     */
    public void setExit( int x, int y, int width, int height ) {

        controller.addTool( new ChangeRectangleValueTool( exit, x, y, width, height ) );
    }

    /**
     * Sets the new documentation of the exit.
     * 
     * @param documentation
     *            Documentation of the exit
     */
    public void setDocumentation( String documentation ) {

        controller.addTool( new ChangeDocumentationTool( exit, documentation ) );
    }

    @Override
    public Object getContent( ) {

        return exit;
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

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;

        return elementAdded;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;

        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        ConditionsController.updateVarFlagSummary( varFlagSummary, exit.getConditions( ) );
        if( exit.getEffects( ) != null )
            EffectsController.updateVarFlagSummary( varFlagSummary, exit.getEffects( ) );
        if( exit.getPostEffects( ) != null )
            EffectsController.updateVarFlagSummary( varFlagSummary, exit.getPostEffects( ) );
        if( exit.getNotEffects( ) != null )
            EffectsController.updateVarFlagSummary( varFlagSummary, exit.getNotEffects( ) );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        return exitLookDataControl.countAssetReferences( assetPath );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        exitLookDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        if( id.equals( exit.getNextSceneId( ) ) )
            count = 1;
        count += EffectsController.countIdentifierReferences( id, exit.getEffects( ) );
        count += EffectsController.countIdentifierReferences( id, exit.getPostEffects( ) );
        count += EffectsController.countIdentifierReferences( id, exit.getNotEffects( ) );
        count += conditionsController.countIdentifierReferences( id );
        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        if( oldId.equals( exit.getNextSceneId( ) ) )
            exit.setNextSceneId( newId );
        EffectsController.replaceIdentifierReferences( oldId, newId, exit.getEffects( ) );
        EffectsController.replaceIdentifierReferences( oldId, newId, exit.getPostEffects( ) );
        EffectsController.replaceIdentifierReferences( oldId, newId, exit.getNotEffects( ) );
        conditionsController.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        EffectsController.deleteIdentifierReferences( id, exit.getEffects( ) );
        EffectsController.deleteIdentifierReferences( id, exit.getPostEffects( ) );
        EffectsController.deleteIdentifierReferences( id, exit.getNotEffects( ) );
        conditionsController.deleteIdentifierReferences( id );
        if( id.equals( exit.getNextSceneId( ) ) )
            exit.setNextSceneId( null );
    }

    /**
     * @return the exitLookDataControl
     */
    public ExitLookDataControl getExitLookDataControl( ) {

        return exitLookDataControl;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        exitLookDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getExitLookDataControl( ).getCustomizedText( ), TC.get( "Search.CustomizedText" ) );
    }

    public boolean isRectangular( ) {

        return exit.isRectangular( );
    }

    public List<Point> getPoints( ) {

        return exit.getPoints( );
    }

    public void addPoint( int x, int y ) {

        controller.addTool( new AddNewPointTool( exit, x, y ) );
    }

    public Point getLastPoint( ) {

        if( exit.getPoints( ).size( ) > 0 )
            return exit.getPoints( ).get( exit.getPoints( ).size( ) - 1 );
        return null;
    }

    public void deletePoint( Point point ) {

        controller.addTool( new DeletePointTool( exit, point ) );
    }

    public void setRectangular( boolean selected ) {

        controller.addTool( new ChangeRectangularValueTool( exit, selected ) );
    }

    public Rectangle getRectangle( ) {

        return (Rectangle) this.getContent( );
    }

    public InfluenceAreaDataControl getInfluenceArea( ) {

        return influenceAreaDataControl;
    }

    public SceneDataControl getSceneDataControl( ) {

        return sceneDataControl;
    }

    /**
     * Returns the conditions of the element reference.
     * 
     * @return Conditions of the element reference
     */
    public ConditionsController getConditions( ) {

        HashMap<String, ConditionContextProperty> context1 = new HashMap<String, ConditionContextProperty>( );
        ConditionOwner parent = new ConditionOwner( Controller.SCENE, sceneDataControl.getId( ) );
        ConditionOwner owner = new ConditionOwner( Controller.EXIT, exit.getNextSceneId( ), parent );
        context1.put( ConditionsController.CONDITION_OWNER, owner );

        conditionsController = new ConditionsController( exit.getConditions( ), context1 );

        return conditionsController;
    }

    public String getNextSceneId( ) {

        return exit.getNextSceneId( );
    }

    public void setNextSceneId( String value ) {

        Controller.getInstance( ).addTool( new ChangeStringValueTool( exit, value, "getNextSceneId", "setNextSceneId" ) );
    }

    public int getDestinyPositionX( ) {

        return exit.getDestinyX( );
    }

    public int getDestinyPositionY( ) {

        return exit.getDestinyY( );
    }

    /**
     * Sets the new destiny position of the next scene.
     * 
     * @param positionX
     *            X coordinate of the destiny position
     * @param positionY
     *            Y coordinate of the destiny position
     */
    public void setDestinyPosition( int positionX, int positionY ) {

        controller.addTool( new ChangeNSDestinyPositionTool( exit, positionX, positionY ) );
    }

    /**
     * Toggles the destiny position. If the next scene has a destiny position
     * deletes it, if it doesn't have one, set initial values for it.
     */
    public void toggleDestinyPosition( ) {

        if( exit.hasPlayerPosition( ) )
            controller.addTool( new ChangeNSDestinyPositionTool( exit, Integer.MIN_VALUE, Integer.MIN_VALUE ) );
        else
            controller.addTool( new ChangeNSDestinyPositionTool( exit, 0, 0 ) );
    }

    /**
     * Returns whether the next scene has a destiny position or not.
     * 
     * @return True if the next scene has a destiny position, false otherwise
     */
    public boolean hasDestinyPosition( ) {

        return exit.hasPlayerPosition( );
    }

    public int getTransitionType( ) {

        return exit.getTransitionType( );
    }

    public Number getTransitionTime( ) {

        return exit.getTransitionTime( );
    }

    public void setTransitionTime( int value ) {

        Controller.getInstance( ).addTool( new ChangeIntegerValueTool( exit, value, "getTransitionTime", "setTransitionTime" ) );
    }

    public void setTransitionType( int selectedIndex ) {

        Controller.getInstance( ).addTool( new ChangeIntegerValueTool( exit, selectedIndex, "getTransitionType", "setTransitionType" ) );
    }

    public boolean isHasNotEffects( ) {

        return exit.isHasNotEffects( );
    }

    public void setHasNotEffects( boolean selected ) {

        Controller.getInstance( ).addTool( new ChangeBooleanValueTool( exit, selected, "isHasNotEffects", "setHasNotEffects" ) );
    }

    public EffectsController getEffects( ) {

        return effectsController;
    }

    public EffectsController getPostEffects( ) {

        return postEffectsController;
    }

    public EffectsController getNotEffects( ) {

        return notEffectsController;
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }

}
