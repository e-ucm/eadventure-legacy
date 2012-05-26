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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * A custom action, defined by the user.
 * 
 * @author Eugenio Marchiori
 */
public class FunctionalCustom extends FunctionalAction {

    /**
     * The element onto with to perform the action
     */
    private FunctionalElement element;

    /**
     * The name of the action
     */
    private String actionName;

    /**
     * The total time of the action
     */
    private long totalTime;

    /**
     * True if the action has special animation
     */
    private boolean hasAnimation;

    /**
     * Default constructor, using the functional element object of the action
     * and the action name.
     * 
     * @param element
     *            The element onto which the action is performed
     * @param actionName
     *            The name of the action
     */
    public FunctionalCustom( FunctionalElement element, String actionName ) {

        super( null );
        this.element = element;
        this.actionName = actionName;
        originalAction = element.getFirstValidCustomAction( actionName );
        needsGoTo = originalAction.isNeedsGoTo( );
        keepDistance = originalAction.getKeepDistance( );
        this.hasAnimation = false;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;

        Resources resources = null;
        CustomAction customAction = (CustomAction) originalAction;
        for( int i = 0; i < customAction.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions( customAction.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                resources = customAction.getResources( ).get( i );

        Resources playerResources = functionalPlayer.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        Animation[] animation = new Animation[ 4 ];

        if( resources.getAssetPath( "actionAnimation" ) != null && !resources.getAssetPath( "actionAnimation" ).equals( "" ) ) {
            animation[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( "actionAnimation" ), true, MultimediaManager.IMAGE_PLAYER );
            animation[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( "actionAnimation" ), true, MultimediaManager.IMAGE_PLAYER );
            animation[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( "actionAnimation" ), false, MultimediaManager.IMAGE_PLAYER );
            animation[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( "actionAnimation" ), false, MultimediaManager.IMAGE_PLAYER );
        }
        else {
            if( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ) != null && !playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) 
                    && !playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ) )
                animation[AnimationState.EAST] = multimedia.loadAnimation( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            else
                animation[AnimationState.EAST] = multimedia.loadAnimation( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), true, MultimediaManager.IMAGE_PLAYER );
            
            if( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                    && !playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa") )
                animation[AnimationState.WEST] = multimedia.loadAnimation( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
            else
                animation[AnimationState.WEST] = multimedia.loadAnimation( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            animation[AnimationState.NORTH] = multimedia.loadAnimation( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            animation[AnimationState.SOUTH] = multimedia.loadAnimation( playerResources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        }

        if( resources.getAssetPath( "actionAnimationLeft" ) != null && !resources.getAssetPath( "actionAnimationLeft" ).equals( "" ) ) {
            animation[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( "actionAnimationLeft" ), true, MultimediaManager.IMAGE_PLAYER );
        }

        if( element.getX( ) > functionalPlayer.getX( ) ) {
            functionalPlayer.setDirection( AnimationState.EAST );
        }
        else {
            functionalPlayer.setDirection( AnimationState.WEST );
        }
        functionalPlayer.setAnimation( animation, -1 );
        hasAnimation = true;

        totalTime = 0;
        finished = false;

        DebugLog.player( "Started custom action: " + customAction.getName( ) + " " + customAction.getTargetId( ) );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        totalTime += elapsedTime;
        if( totalTime > 1000 ) {

            finished = true;
            if( hasAnimation )
                functionalPlayer.popAnimation( );

            if( element instanceof FunctionalItem ) {
                FunctionalItem item = (FunctionalItem) element;
                if( !item.custom( actionName ) ) {
                    if( functionalPlayer.isAlwaysSynthesizer( ) )
                        functionalPlayer.speakWithFreeTTS( GameText.getTextCustomCannot( ), functionalPlayer.getPlayerVoice( ) );
                    else
                        functionalPlayer.speak( GameText.getTextCustomCannot( ), Game.getInstance().getGameDescriptor( ).isKeepShowing( ) );
                }
            }
            else if( element instanceof FunctionalNPC ) {
                FunctionalNPC npc = (FunctionalNPC) element;
                if( !npc.custom( actionName ) ) {
                    if( functionalPlayer.isAlwaysSynthesizer( ) )
                        functionalPlayer.speakWithFreeTTS( GameText.getTextCustomCannot( ), functionalPlayer.getPlayerVoice( ) );
                    else
                        functionalPlayer.speak( GameText.getTextCustomCannot( ), Game.getInstance().getGameDescriptor( ).isKeepShowing( ) );
                }
            }
        }

    }

}
