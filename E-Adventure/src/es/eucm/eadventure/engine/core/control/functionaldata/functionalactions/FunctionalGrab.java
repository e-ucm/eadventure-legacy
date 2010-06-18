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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The action to grab an element
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalGrab extends FunctionalAction {

    /**
     * The element to grab
     */
    private FunctionalElement element;

    /**
     * The total elapsed time of the action
     */
    private long totalTime;

    /**
     * True if the element can be grabed
     */
    private boolean canGrab = false;

    /**
     * Default constructor, with the original action and the element to grab
     * 
     * @param action
     *            The original action
     * @param element
     *            The element to grab
     */
    public FunctionalGrab( Action action, FunctionalElement element ) {

        super( action );
        this.element = element;
        this.type = ActionManager.ACTION_GRAB;
        originalAction = element.getFirstValidAction( Action.GRAB );
        if( originalAction != null ) {
            this.needsGoTo = originalAction.isNeedsGoTo( );
            this.keepDistance = originalAction.getKeepDistance( );
        }
        else {
            this.needsGoTo = true;
            this.keepDistance = 35;
        }
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

        // TODO always loads the default animation and walks with the defualt speed
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        if( functionalPlayer.getX( ) < element.getX( ) ) {
            functionalPlayer.setDirection( AnimationState.EAST );
        }
        else {
            functionalPlayer.setDirection( AnimationState.WEST );
        }
        Animation[] animations = new Animation[ 4 ];
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), true, MultimediaManager.IMAGE_PLAYER );
        
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        functionalPlayer.setAnimation( animations, -1 );
        finished = false;

        DebugLog.player( "Started grab: " + element.getElement( ).getId( ) );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        totalTime += elapsedTime;
        FunctionalItem item = (FunctionalItem) element;
        if( !finished && !canGrab ) {
            canGrab = item.grab( );
            if( !canGrab ) {
                finished = true;
                functionalPlayer.popAnimation( );
                if( functionalPlayer.isAlwaysSynthesizer( ) )
                    functionalPlayer.speakWithFreeTTS( GameText.getTextGrabCannot( ), functionalPlayer.getPlayerVoice( ) );
                else
                    functionalPlayer.speak( GameText.getTextGrabCannot( ), Game.getInstance().getGameDescriptor( ).isKeepShowing( ) );
            }
        }
        else if( !finished && totalTime > 1000 ) {
            finished = true;
            functionalPlayer.popAnimation( );
        }
    }

}
