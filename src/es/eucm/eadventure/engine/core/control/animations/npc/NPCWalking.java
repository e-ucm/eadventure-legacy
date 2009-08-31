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
package es.eucm.eadventure.engine.core.control.animations.npc;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class NPCWalking extends NPCState {

    /**
     * Creates a new NPCIdle
     * 
     * @param npc
     *            the reference to the npc
     */
    public NPCWalking( FunctionalNPC npc ) {

        super( npc );
    }

    @Override
    public void update( long elapsedTime ) {

        if( ( npc.getSpeedX( ) > 0 && npc.getX( ) < npc.getDestX( ) ) || ( npc.getSpeedX( ) <= 0 && npc.getX( ) >= npc.getDestX( ) ) ) {
            npc.setX( npc.getX( ) + npc.getSpeedX( ) * elapsedTime / 1000 );
        }
        else {
            npc.setState( FunctionalNPC.IDLE );
            if( npc.getDirection( ) == -1 )
                npc.setDirection( AnimationState.SOUTH );
        }
    }

    @Override
    public void initialize( ) {

        if( npc.getX( ) < npc.getDestX( ) ) {
            setCurrentDirection( EAST );
            npc.setSpeedX( FunctionalNPC.DEFAULT_SPEED );
        }
        else {
            setCurrentDirection( WEST );
            npc.setSpeedX( -FunctionalNPC.DEFAULT_SPEED );
        }
    }

    @Override
    public void loadResources( ) {

        Resources resources = npc.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ), false, MultimediaManager.IMAGE_SCENE );
        else
            animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_SCENE );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_SCENE );
    }

}
