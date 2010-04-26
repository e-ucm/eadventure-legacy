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
package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The action to give an element to an NPC
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalGive extends FunctionalAction {

    /**
     * The element to give
     */
    private FunctionalElement element;

    /**
     * The other element of the action (an NPC in this case)
     */
    private FunctionalElement anotherElement;

    /**
     * The total elapsed time of the action
     */
    private long totalTime;

    /**
     * True if the element can be given to the other element of the action
     */
    private boolean canGive = false;

    /**
     * Default constructor, with the original action and the element to be given
     * 
     * @param action
     *            The original action
     * @param element
     *            The element to be given
     */
    public FunctionalGive( Action action, FunctionalElement element ) {

        super( action );
        this.element = element;
        this.type = ActionManager.ACTION_GIVE;
        originalAction = element.getFirstValidAction( Action.GIVE_TO );
        requiersAnotherElement = true;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

        requiersAnotherElement = false;
        if( originalAction != null ) {
            needsGoTo = originalAction.isNeedsGoTo( );
            keepDistance = originalAction.getKeepDistance( );
        }
        else {
            needsGoTo = true;
            keepDistance = 35;
        }
        anotherElement = element;
    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;

        Resources resources = functionalPlayer.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        if( anotherElement.getX( ) > functionalPlayer.getX( ) ) {
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
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        functionalPlayer.setAnimation( animations, -1 );

    }

    @Override
    public void stop( ) {

        if( functionalPlayer != null && !finished ) {
            functionalPlayer.popAnimation( );
        }
        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        if( anotherElement != null ) {
            totalTime += elapsedTime;
            if( element instanceof FunctionalItem && anotherElement instanceof FunctionalNPC ) {
                FunctionalItem item = (FunctionalItem) element;
                FunctionalNPC npc = (FunctionalNPC) anotherElement;

                if( !finished && !canGive ) {
                    canGive = item.giveTo( npc );
                    if( !canGive ) {
                        DebugLog.player( "Can't give: " + item.getItem( ).getId( ) + " to " + npc.getNPC( ).getId( ) );
                        if( functionalPlayer.isAlwaysSynthesizer( ) )
                            functionalPlayer.speakWithFreeTTS( GameText.getTextGiveCannot( ), functionalPlayer.getPlayerVoice( ) );
                        else
                            functionalPlayer.speak( GameText.getTextGiveCannot( ) );
                        functionalPlayer.popAnimation( );
                        finished = true;
                    }
                }
                else if( !finished && totalTime > 1000 ) {
                    DebugLog.player( "Gave: " + item.getItem( ).getId( ) + " to " + npc.getNPC( ).getId( ) );
                    finished = true;
                    functionalPlayer.popAnimation( );
                }
            }
            else {
                DebugLog.player( "Invalid give..." );
                finished = true;
            }
        }

    }

    @Override
    public FunctionalElement getAnotherElement( ) {

        return anotherElement;
    }

}
