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
package es.eucm.eadventure.engine.core.control.animations.npc;

import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * An state for an idle npc
 */
public class NPCIdle extends NPCState {

    /**
     * Creates a new NPCIdle
     * 
     * @param npc
     *            the reference to the npc
     */
    public NPCIdle( FunctionalNPC npc ) {

        super( npc );
    }

    @Override
    public void update( long elapsedTime ) {

    }

    @Override
    public void initialize( ) {

        if( getCurrentDirection( ) == -1 )
            setCurrentDirection( SOUTH );
        npc.setSpeedX( 0.0f );
        npc.setSpeedY( 0.0f );
    }

    @Override
    public void loadResources( ) {

        Resources resources = npc.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        // added make the mirror when only is defined left animation
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ))
            animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
        else
            animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ), true, MultimediaManager.IMAGE_SCENE );
        
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) 
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ) )
            animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ), false, MultimediaManager.IMAGE_SCENE );
        else
            animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
        
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_SCENE );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_SCENE );

    }
}
