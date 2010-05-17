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
package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeIntegerValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ActionDataControl extends DataControlWithResources {

    /**
     * Contained action structure.
     */
    private Action action;

    /**
     * Type of the action.
     */
    private int actionType;

    /**
     * Conditions controller.
     */
    private ConditionsController conditionsController;

    /**
     * Effects controller
     */
    private EffectsController effectsController;

    /**
     * Not-Effects controller
     */
    private EffectsController notEffectsController;

    /**
     * Contructor.
     * 
     * @param action
     *            Next scenes of the data control structure
     *            
     *            
     */
    public ActionDataControl (Action action, String name){
        
        this.action = action;

        this.resourcesList = new ArrayList<Resources>( );
        this.resourcesDataControlList = new ArrayList<ResourcesDataControl>( );

        actionType = Controller.ACTION_TALK_TO;
        
        // Create subcontrollers
        conditionsController = new ConditionsController( action.getConditions( ), actionType, name );
        effectsController = new EffectsController( action.getEffects( ) );
        notEffectsController = new EffectsController( action.getNotEffects( ) );
        
    }
    
    
    public ActionDataControl( Action action ) {

        this.action = action;

        this.resourcesList = new ArrayList<Resources>( );
        this.resourcesDataControlList = new ArrayList<ResourcesDataControl>( );

        String actionName = null;
        // Store the type of the action
        switch( action.getType( ) ) {
            case Action.EXAMINE:
                actionType = Controller.ACTION_EXAMINE;
                actionName = "";
                break;
            case Action.GRAB:
                actionType = Controller.ACTION_GRAB;
                actionName = "";
                break;
            case Action.USE:
                actionType = Controller.ACTION_USE;
                actionName = "";
                break;
            case Action.CUSTOM:
                actionType = Controller.ACTION_CUSTOM;
                CustomAction custom = (CustomAction) action;
                actionName = custom.getName( );
                break;
            case Action.USE_WITH:
                actionType = Controller.ACTION_USE_WITH;
                actionName = action.getTargetId( );
                break;
            case Action.GIVE_TO:
                actionType = Controller.ACTION_GIVE_TO;
                actionName = action.getTargetId( );
                break;
            case Action.CUSTOM_INTERACT:
                actionType = Controller.ACTION_CUSTOM_INTERACT;
                CustomAction custom2 = (CustomAction) action;
                actionName = custom2.getName( ) + " " + action.getTargetId( );
                break;
            case Action.TALK_TO:
                actionType = Controller.ACTION_TALK_TO;
                actionName = action.getTargetId( );
                break;
            case Action.DRAG_TO:
                actionType = Controller.ACTION_DRAG_TO;
                actionName = action.getTargetId( );
                break;
        }

        // Create subcontrollers
        conditionsController = new ConditionsController( action.getConditions( ), actionType, actionName );
        effectsController = new EffectsController( action.getEffects( ) );
        notEffectsController = new EffectsController( action.getNotEffects( ) );
    }

    /**
     * Returns the conditions of the action.
     * 
     * @return Conditions of the action
     */
    public ConditionsController getConditions( ) {

        return conditionsController;
    }

    /**
     * Returns the effects of the action.
     * 
     * @return Effects of the action
     */
    public EffectsController getEffects( ) {

        return effectsController;
    }

    /**
     * Returns the type of the contained effect.
     * 
     * @return Type of the contained effect
     */
    public int getType( ) {

        return actionType;
    }

    /**
     * Returns whether the action accepts id target or not.
     * 
     * @return True if the action accepts id target, false otherwise
     */
    public boolean hasIdTarget( ) {

        // The use-with and give-to actions accept id target
        return action.getType( ) == Action.USE_WITH || action.getType( ) == Action.GIVE_TO || action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.DRAG_TO;
    }

    /**
     * Returns the list of elements to select for the actions with targets.
     * 
     * @return List of elements, null if the action doesn't accept targets
     */
    public String[] getElementsList( ) {

        String[] elements = null;

        if( action.getType( ) == Action.USE_WITH )
            elements = controller.getIdentifierSummary( ).getItemAndActiveAreaIds( );
        else if( action.getType( ) == Action.GIVE_TO )
            elements = controller.getIdentifierSummary( ).getNPCIds( );
        else if( action.getType( ) == Action.DRAG_TO )
            elements = controller.getIdentifierSummary( ).getItemActiveAreaNPCIds();
        else if( action.getType( ) == Action.CUSTOM_INTERACT ) {
            String[] temp1 = controller.getIdentifierSummary( ).getNPCIds( );
            String[] temp2 = controller.getIdentifierSummary( ).getItemAndActiveAreaIds( );
            elements = new String[ temp1.length + temp2.length ];
            for( int i = 0; i < elements.length; i++ ) {
                if( i < temp1.length )
                    elements[i] = temp1[i];
                else
                    elements[i] = temp2[i - temp1.length];
            }
        }

        return elements;
    }

    /**
     * Returns the target id of the contained effect.
     * 
     * @return Target id of the contained effect
     */
    public String getIdTarget( ) {

        return action.getTargetId( );
    }

    /**
     * Returns the documentation of the action.
     * 
     * @return Action's documentation
     */
    public String getDocumentation( ) {

        return action.getDocumentation( );
    }

    /**
     * Sets the new documentation of the action.
     * 
     * @param documentation
     *            Documentation of the action
     */
    public void setDocumentation( String documentation ) {

        controller.addTool( new ChangeDocumentationTool( action, documentation ) );
    }

    /**
     * Sets the new id target of the action.
     * 
     * @param idTarget
     *            Id target of the action
     */
    public void setIdTarget( String idTarget ) {

        controller.addTool( new ChangeTargetIdTool( action, idTarget, true, false ) );
    }

    @Override
    public Object getContent( ) {

        return action;
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

        return false;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public String renameElement( String name ) {

        //TODO: IS this right?
        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Update the flag summary with the effects of the action
        EffectsController.updateVarFlagSummary( varFlagSummary, action.getEffects( ) );
        if( action.getNotEffects( ) != null )
            EffectsController.updateVarFlagSummary( varFlagSummary, action.getNotEffects( ) );
        ConditionsController.updateVarFlagSummary( varFlagSummary, action.getConditions( ) );
        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.updateVarFlagSummary( varFlagSummary );
        }
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;
        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
        // Iterate through the resources
        for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
            String resourcesPath = currentPath + " >> " + TC.getElement( Controller.RESOURCES ) + " #" + ( i + 1 );
            valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
        }
        }
        
        // Check the effects of the action
        valid &= EffectsController.isValid( currentPath + " >> " + TC.get( "Element.Effects" ), incidences, action.getEffects( ) );
        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;
        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
     // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countAssetReferences( assetPath );
        }
     // Return the asset references from the effects
        count += EffectsController.countAssetReferences( assetPath, action.getEffects( ) );;
        return count;
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {
        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );
        }
        EffectsController.deleteAssetReferences( assetPath, action.getEffects( ) );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // If the action references to the given identifier, increase the counter
        if( ( action.getType( ) == Action.GIVE_TO || action.getType( ) == Action.USE_WITH || action.getType( ) == Action.DRAG_TO || action.getType( ) == Action.CUSTOM_INTERACT ) && action.getTargetId( ).equals( id ) )
            count++;
        
        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.countIdentifierReferences( id );
        }

        // Add to the counter the references in the effects block
        count += EffectsController.countIdentifierReferences( id, action.getEffects( ) );

        count += conditionsController.countIdentifierReferences( id );
        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.replaceIdentifierReferences( oldId, newId );
        }
        // Only the "Give to" and "Use with" have item references
        if( ( action.getType( ) == Action.GIVE_TO || action.getType( ) == Action.USE_WITH || action.getType( ) == Action.DRAG_TO || action.getType( ) == Action.CUSTOM_INTERACT ) && action.getTargetId( ).equals( oldId ) )
            action.setTargetId( newId );

        EffectsController.replaceIdentifierReferences( oldId, newId, action.getEffects( ) );
        conditionsController.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {
        if (action.getType( ) == Action.CUSTOM_INTERACT || action.getType( ) == Action.CUSTOM){
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteIdentifierReferences( id );
        }

        EffectsController.deleteIdentifierReferences( id, action.getEffects( ) );
        conditionsController.deleteIdentifierReferences( id );
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
        
        
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );
        EffectsController.getAssetReferences( assetPaths, assetTypes, action.getEffects( ) );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    /**
     * @return the value of needsGoTo
     */
    public boolean getNeedsGoTo( ) {

        return action.isNeedsGoTo( );
    }

    /**
     * @param needsGoTo
     *            the needsGoTo to set
     */
    public void setNeedsGoTo( boolean needsGoTo ) {
        controller.addTool( new ChangeBooleanValueTool( action, needsGoTo, "isNeedsGoTo", "setNeedsGoTo" ) );
    }

    /**
     * @return the value of keepDistance
     */
    public int getKeepDistance( ) {

        return action.getKeepDistance( );
    }

    /**
     * @param keepDistance
     *            the keepDistance to set
     */
    public void setKeepDistance( int keepDistance ) {
        controller.addTool( new ChangeIntegerValueTool( action, keepDistance, "getKeepDistance", "setKeepDistance" ) );
    }

    /**
     * Change activated not effects
     * 
     * @param activated
     */
    public void setActivatedNotEffects( Boolean activated ) {

        action.setActivatedNotEffects( activated );
    }

    public Boolean isActivatedNotEffects( ) {

        return action.isActivatedNotEffects( );
    }

    /**
     * @return the notEffectsController
     */
    public EffectsController getNotEffectsController( ) {
        return notEffectsController;
    }

    @Override
    public void recursiveSearch( ) {
        check( this.getConditions( ), TC.get( "Search.Conditions" ) );
        check( this.getIdTarget( ), TC.get( "Search.IDTarget" ) );

        for( int i = 0; i < this.getEffects( ).getEffectCount( ); i++ ) {
            check( this.getEffects( ).getEffectInfo( i ), TC.get( "Search.Effect" ) );
        }
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }
}
