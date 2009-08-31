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
package es.eucm.eadventure.common.data.chapter.scenes;

import es.eucm.eadventure.common.data.HasTargetId;
import es.eucm.eadventure.common.data.Positioned;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

public abstract class Cutscene extends GeneralScene implements HasTargetId, Positioned {

    /**
     * The tag for the video
     */
    public static final String RESOURCE_TYPE_VIDEO = "video";

    /**
     * The tag for the slides
     */
    public static final String RESOURCE_TYPE_SLIDES = "slides";

    /**
     * The tag for the background music
     */
    public static final String RESOURCE_TYPE_MUSIC = "bgmusic";

    public static final int GOBACK = 0;

    public static final int ENDCHAPTER = 1;

    public static final int NEWSCENE = 2;

    private int next;

    private int transitionTime;

    private int transitionType;

    private int destinyX;

    private int destinyY;

    private String idTarget;

    private Effects effects;

    /**
     * Creates a new cutscene
     * 
     * @param type
     *            The type of the scene
     * @param id
     *            The id of the scene
     */
    protected Cutscene( int type, String id ) {

        super( type, id );

        effects = new Effects( );
        destinyX = Integer.MIN_VALUE;
        destinyY = Integer.MIN_VALUE;
        transitionType = NextScene.NO_TRANSITION;
        transitionTime = 0;
        next = GOBACK;
    }

    /**
     * Adds a next scene to the list of next scenes
     * 
     * @param nextScene
     *            the next scene to add
     */
    public void addNextScene( NextScene nextScene ) {

        next = NEWSCENE;
        idTarget = nextScene.getTargetId( );
        transitionTime = nextScene.getTransitionTime( );
        transitionType = nextScene.getTransitionType( );
        destinyX = nextScene.getPositionX( );
        destinyY = nextScene.getPositionY( );
        for( AbstractEffect effect : nextScene.getEffects( ).getEffects( ) ) {
            effects.add( effect );
        }
        for( AbstractEffect effect : nextScene.getPostEffects( ).getEffects( ) ) {
            effects.add( effect );
        }
    }

    /**
     * Returns whether the cutscene ends the chapter.
     * 
     * @return True if the cutscene ends the chapter, false otherwise
     */
    public boolean isEndScene( ) {

        return ( next == ENDCHAPTER );
    }

    /**
     * Changes whether the cutscene ends the chapter.
     * 
     * @param endScene
     *            True if the cutscene ends the chapter, false otherwise
     */
    public void setEndScene( boolean endScene ) {

        if( endScene )
            this.next = ENDCHAPTER;
        else
            this.next = GOBACK;
    }

    /**
     * Returns whether this scene has been assigned a position for a player that
     * just came in
     * 
     * @return true if this scene has a position assigned, false otherwise
     */
    public boolean hasPlayerPosition( ) {

        return ( destinyX != Integer.MIN_VALUE ) && ( destinyY != Integer.MIN_VALUE );
    }

    /**
     * Returns the horizontal position of the player when he/she gets into this
     * scene
     * 
     * @return the horizontal position of the player when he/she gets into this
     *         scene
     */
    public int getPositionX( ) {

        return destinyX;
    }

    /**
     * Returns the vertical position of the player when he/she gets into this
     * scene
     * 
     * @return the verticalal position of the player when he/she gets into this
     *         scene
     */
    public int getPositionY( ) {

        return destinyY;
    }

    /**
     * Returns the effects for this next scene
     * 
     * @return the effects for this next scene
     */
    public Effects getEffects( ) {

        return effects;
    }

    /**
     * Sets a new next scene id.
     * 
     * @param nextSceneId
     *            New next scene id
     */
    public void setTargetId( String nextSceneId ) {

        this.idTarget = nextSceneId;
    }

    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Sets the new destiny position for the next scene.
     * 
     * @param positionX
     *            X coordinate of the destiny position
     * @param positionY
     *            Y coordinate of the destiny position
     */
    public void setDestinyPosition( int positionX, int positionY ) {

        setPositionX( positionX );
        setPositionY( positionY );
    }

    /**
     * Sets the new destiny position X for the next scene.
     * 
     * @param positionX
     *            X coordinate of the destiny position
     */
    public void setPositionX( int positionX ) {

        this.destinyX = positionX;
    }

    /**
     * Sets the new destiny position Y for the next scene.
     * 
     * @param positionY
     *            Y coordinate of the destiny position
     */
    public void setPositionY( int positionY ) {

        this.destinyY = positionY;
    }

    /**
     * Changes the effects for this next scene
     * 
     * @param effects
     *            The new effects
     */
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    public Integer getTransitionType( ) {

        return transitionType;
    }

    public Integer getTransitionTime( ) {

        return transitionTime;
    }

    public void setTransitionType( Integer transitionType ) {

        this.transitionType = transitionType;
    }

    public void setTransitionTime( Integer transitionTime ) {

        this.transitionTime = transitionTime;
    }

    public void setNext( Integer next ) {

        this.next = next;
    }

    public Integer getNext( ) {

        return next;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Cutscene c = (Cutscene) super.clone( );
        c.next = next;
        c.destinyX = destinyX;
        c.destinyY = destinyY;
        c.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        c.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        return c;
    }
}
