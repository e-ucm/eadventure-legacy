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
package es.eucm.eadventure.editor.control.controllers.timer;

import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionContextProperty;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionCustomMessage;
import es.eucm.eadventure.editor.control.controllers.ConditionsController.ConditionOwner;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeLongValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class TimerDataControl extends DataControl {

    /**
     * Contained timer structure.
     */
    private Timer timer;

    /**
     * Conditions controller (init).
     */
    private ConditionsController initConditionsController;

    /**
     * Conditions controller (end).
     */
    private ConditionsController endConditionsController;

    /**
     * Effects controller
     */
    private EffectsController effectsController;

    /**
     * Post effects controller.
     */
    private EffectsController postEffectsController;

    /**
     * Contructor.
     * 
     * @param timer
     *            Timer structure
     */
    public TimerDataControl( Timer timer ) {

        this.timer = timer;

        // Create subcontrollers
        HashMap<String, ConditionContextProperty> context1 = new HashMap<String, ConditionContextProperty>( );
        ConditionOwner owner = new ConditionOwner( Controller.TIMER, timer.getDisplayName( ) );
        context1.put( ConditionsController.CONDITION_OWNER, owner );
        ConditionCustomMessage cMessage1 = new ConditionCustomMessage( TC.get( "Conditions.Context.1A.44" ), TC.get( "Conditions.Context.2A.44" ) );
        context1.put( ConditionsController.CONDITION_CUSTOM_MESSAGE, cMessage1 );

        HashMap<String, ConditionContextProperty> context2 = new HashMap<String, ConditionContextProperty>( );
        context2.put( ConditionsController.CONDITION_OWNER, owner );
        ConditionCustomMessage cMessage2 = new ConditionCustomMessage( TC.get( "Conditions.Context.1B.44" ), TC.get( "Conditions.Context.2B.44" ) );
        context2.put( ConditionsController.CONDITION_CUSTOM_MESSAGE, cMessage2 );

        initConditionsController = new ConditionsController( timer.getInitCond( ), context1 );
        endConditionsController = new ConditionsController( timer.getEndCond( ), context2 );
        effectsController = new EffectsController( timer.getEffects( ) );
        postEffectsController = new EffectsController( timer.getPostEffects( ) );

    }

    /**
     * Returns the init conditions of the timer.
     * 
     * @return Init Conditions of the timer
     */
    public ConditionsController getInitConditions( ) {

        return initConditionsController;
    }

    /**
     * Returns the end conditions of the timer.
     * 
     * @return end Conditions of the timer
     */
    public ConditionsController getEndConditions( ) {

        return endConditionsController;
    }

    /**
     * Returns the effects of the next scene.
     * 
     * @return Effects of the next scene
     */
    public EffectsController getEffects( ) {

        return effectsController;
    }

    /**
     * Returns the post-effects of the next scene.
     * 
     * @return Post-effects of the next scene
     */
    public EffectsController getPostEffects( ) {

        return postEffectsController;
    }

    @Override
    public Object getContent( ) {

        return timer;
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

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Update the summary with conditions and both blocks of effects
        ConditionsController.updateVarFlagSummary( varFlagSummary, timer.getInitCond( ) );
        ConditionsController.updateVarFlagSummary( varFlagSummary, timer.getEndCond( ) );
        EffectsController.updateVarFlagSummary( varFlagSummary, timer.getEffects( ) );
        EffectsController.updateVarFlagSummary( varFlagSummary, timer.getPostEffects( ) );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Valid if the effects and the post effects are valid
        valid &= EffectsController.isValid( currentPath + " >> " + TC.get( "Element.Effects" ), incidences, timer.getEffects( ) );
        valid &= EffectsController.isValid( currentPath + " >> " + TC.get( "Element.PostEffects" ), incidences, timer.getPostEffects( ) );

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Add to the counter the values of the effects and posteffects
        count += EffectsController.countAssetReferences( assetPath, timer.getEffects( ) );
        count += EffectsController.countAssetReferences( assetPath, timer.getPostEffects( ) );

        return count;
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        EffectsController.deleteAssetReferences( assetPath, timer.getEffects( ) );
        EffectsController.deleteAssetReferences( assetPath, timer.getPostEffects( ) );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Add to the counter the values of the effects and posteffects
        count += EffectsController.countIdentifierReferences( id, timer.getEffects( ) );
        count += EffectsController.countIdentifierReferences( id, timer.getPostEffects( ) );
        count += initConditionsController.countIdentifierReferences( id );
        count += endConditionsController.countIdentifierReferences( id );
        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        EffectsController.replaceIdentifierReferences( oldId, newId, timer.getEffects( ) );
        EffectsController.replaceIdentifierReferences( oldId, newId, timer.getPostEffects( ) );
        initConditionsController.replaceIdentifierReferences( oldId, newId );
        endConditionsController.replaceIdentifierReferences( oldId, newId );

    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        EffectsController.deleteIdentifierReferences( id, timer.getEffects( ) );
        EffectsController.deleteIdentifierReferences( id, timer.getPostEffects( ) );
        initConditionsController.deleteIdentifierReferences( id );
        endConditionsController.deleteIdentifierReferences( id );
    }

    /**
     * Returns the time of the timer represented as hours:minutes:seconds. The
     * string returned will look like: HHh:MMm:SSs
     * 
     * @return The time as HHh:MMm:SSs
     */
    public String getTimeHhMmSs( ) {

        String time = "";
        long seconds = timer.getTime( );

        // Less than 60 seconds
        if( seconds < 60 && seconds >= 0 ) {
            time = Long.toString( seconds ) + "s";
        }

        // Between 1 minute and 60 minutes
        else if( seconds < 3600 && seconds >= 60 ) {
            long minutes = seconds / 60;
            long lastSeconds = seconds % 60;
            time = Long.toString( minutes ) + "m:" + Long.toString( lastSeconds ) + "s";
        }

        // One hour or more
        else if( seconds >= 3600 ) {
            long hours = seconds / 3600;
            long minutes = ( seconds % 3600 ) / 60;
            long lastSeconds = ( seconds % 3600 ) % 60;
            time = Long.toString( hours ) + "h:" + Long.toString( minutes ) + "m:" + Long.toString( lastSeconds ) + "s";
        }

        return time;
    }

    /**
     * Returns the time in total seconds
     * 
     * @return The time in seconds
     */
    public long getTime( ) {

        return timer.getTime( );
    }

    public String getDocumentation( ) {

        return timer.getDocumentation( );
    }

    public void setDocumentation( String newDoc ) {

        controller.addTool( new ChangeDocumentationTool( timer, newDoc ) );
    }

    public void setTime( long newTime ) {

        controller.addTool( new ChangeLongValueTool( timer, newTime, "getTime", "setTime" ) );
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        EffectsController.getAssetReferences( assetPaths, assetTypes, timer.getEffects( ) );
        EffectsController.getAssetReferences( assetPaths, assetTypes, timer.getPostEffects( ) );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getEndConditions( ), TC.get( "Search.EndConditions" ) );
        check( this.getInitConditions( ), TC.get( "Search.InitConditions" ) );
        for( int i = 0; i < this.getEffects( ).getEffectCount( ); i++ ) {
            check( this.getEffects( ).getEffectInfo( i ), TC.get( "Search.Effect" ) );
        }
        for( int i = 0; i < this.getPostEffects( ).getEffectCount( ); i++ ) {
            check( this.getPostEffects( ).getEffectInfo( i ), TC.get( "Search.PostEffect" ) );
        }
    }

    public boolean isUsesEndCondition( ) {

        return timer.isUsesEndCondition( );
    }

    public void setUsesEndCondition( boolean selected ) {

        controller.addTool( new ChangeBooleanValueTool( timer, selected, "isUsesEndCondition", "setUsesEndCondition" ) );
    }

    public boolean isMultipleStarts( ) {

        return timer.isMultipleStarts( );
    }

    public void setMultipleStarts( boolean multipleStarts ) {

        controller.addTool( new ChangeBooleanValueTool( timer, multipleStarts, "isMultipleStarts", "setMultipleStarts" ) );
    }

    public boolean isRunsInLoop( ) {

        return timer.isRunsInLoop( );
    }

    public void setRunsInLoop( boolean runsInLoop ) {

        controller.addTool( new ChangeBooleanValueTool( timer, runsInLoop, "isRunsInLoop", "setRunsInLoop" ) );
    }

    public boolean isShowTime( ) {

        return timer.isShowTime( );
    }

    public void setShowTime( boolean selected ) {

        controller.addTool( new ChangeBooleanValueTool( timer, selected, "isShowTime", "setShowTime" ) );
    }

    public boolean isCountDown( ) {

        return timer.isCountDown( );
    }

    public void setCountDown( boolean countDown ) {

        controller.addTool( new ChangeBooleanValueTool( timer, countDown, "isCountDown", "setCountDown" ) );
    }

    public boolean isShowWhenStopped( ) {

        return timer.isShowWhenStopped( );
    }

    public void setShowWhenStopped( boolean showWhenStopped ) {

        controller.addTool( new ChangeBooleanValueTool( timer, showWhenStopped, "isShowWhenStopped", "setShowWhenStopped" ) );
    }

    public String getDisplayName( ) {

        return timer.getDisplayName( );
    }

    public void setDisplayName( String displayName ) {

        controller.addTool( new ChangeStringValueTool( timer, displayName, "getDisplayName", "setDisplayName" ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }

}
