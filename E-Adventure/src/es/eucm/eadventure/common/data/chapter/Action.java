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
package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.HasTargetId;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * An action that can be done during the game.
 */
public class Action implements Cloneable, Documented, HasTargetId {

    /**
     * An action of type examine.
     */
    public static final int EXAMINE = 0;

    /**
     * An action of type grab.
     */
    public static final int GRAB = 1;

    /**
     * An action of type give-to.
     */
    public static final int GIVE_TO = 2;

    /**
     * An action of type use-with.
     */
    public static final int USE_WITH = 3;

    /**
     * An action of type use
     */
    public static final int USE = 4;

    /**
     * A custom action
     */
    public static final int CUSTOM = 5;

    /**
     * A custom interaction action
     */
    public static final int CUSTOM_INTERACT = 6;

    /**
     * An action of the type talk-to
     */
    public static final int TALK_TO = 7;
    
    /**
     * An action of type drag to
     */
    public static final int DRAG_TO = 8;

    /**
     * Stores the action type
     */
    private int type;

    /**
     * Documentation of the action.
     */
    private String documentation;

    /**
     * Id of the target of the action (in give to and use with)
     */
    private String idTarget;

    /**
     * Conditions of the action
     */
    private Conditions conditions;

    /**
     * Effects of performing the action
     */
    private Effects effects;

    /**
     * Alternative effects, when the conditions aren't OK
     */
    private Effects notEffects;

    /**
     * Activate not effects
     */
    private boolean activatedNotEffects;

    /**
     * Indicates whether the character needs to go up to the object
     */
    private boolean needsGoTo;

    /**
     * Indicates the minimum distance the character should leave between the
     * object and himself
     */
    private int keepDistance;

    /**
     * Constructor.
     * 
     * @param type
     *            The type of the action
     */
    public Action( int type ) {

        this( type, null, new Conditions( ), new Effects( ), new Effects( ) );
        switch( type ) {
            case EXAMINE:
                needsGoTo = false;
                keepDistance = 0;
                break;
            case GRAB:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case GIVE_TO:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case USE_WITH:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case USE:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case TALK_TO:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case DRAG_TO:
                needsGoTo = false;
                keepDistance = 0;
                break;
            default:
                needsGoTo = false;
                keepDistance = 0;
                break;
        }
    }

    /**
     * Constructor.
     * 
     * @param type
     *            The type of the action
     * @param idTarget
     *            The target of the action
     */
    public Action( int type, String idTarget ) {

        this( type, idTarget, new Conditions( ), new Effects( ), new Effects( ) );
    }

    /**
     * Constructor
     * 
     * @param type
     *            The type of the action
     * @param conditions
     *            The conditions of the action (must not be null)
     * @param effects
     *            The effects of the action (must not be null)
     * @param notEffects
     *            The effects of the action when the conditions aren't OK (must
     *            not be null)
     */
    public Action( int type, Conditions conditions, Effects effects, Effects notEffects ) {

        this( type, null, conditions, effects, notEffects );
    }

    /**
     * Constructor
     * 
     * @param type
     *            The type of the action
     * @param idTarget
     *            The target of the action
     * @param conditions
     *            The conditions of the action (must not be null)
     * @param effects
     *            The effects of the action (must not be null)
     * @param notEffects
     *            The effects of the action when the conditions aren't OK (must
     *            not be null)
     */
    public Action( int type, String idTarget, Conditions conditions, Effects effects, Effects notEffects ) {

        this.type = type;
        this.idTarget = idTarget;
        this.conditions = conditions;
        this.effects = effects;
        this.notEffects = notEffects;
        documentation = null;
    }

    /**
     * Returns the type of the action.
     * 
     * @return the type of the action
     */
    public int getType( ) {

        return type;
    }

    /**
     * Returns the documentation of the action.
     * 
     * @return the documentation of the action
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * Returns the target of the action.
     * 
     * @return the target of the action
     */
    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Returns the conditions of the action.
     * 
     * @return the conditions of the action
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * Returns the effects of the action.
     * 
     * @return the effects of the action
     */
    public Effects getEffects( ) {

        return effects;
    }

    /**
     * Changes the documentation of this action.
     * 
     * @param documentation
     *            The new documentation
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    /**
     * Changes the id target of this action.
     * 
     * @param idTarget
     *            The new id target
     */
    public void setTargetId( String idTarget ) {

        this.idTarget = idTarget;
    }

    /**
     * Changes the conditions for this next scene
     * 
     * @param conditions
     *            the new conditions
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Changes the effects for this next scene
     * 
     * @param effects
     *            the new effects
     */
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    /**
     * @return the needsGoTo
     */
    public Boolean isNeedsGoTo( ) {

        return needsGoTo;
    }

    /**
     * @param needsGoTo
     *            the needsGoTo to set
     */
    public void setNeedsGoTo( Boolean needsGoTo ) {

        this.needsGoTo = needsGoTo;
    }

    /**
     * @return the keepDistance
     */
    public Integer getKeepDistance( ) {

        return keepDistance;
    }

    /**
     * @param keepDistance
     *            the keepDistance to set
     */
    public void setKeepDistance( Integer keepDistance ) {

        this.keepDistance = keepDistance;
    }

    /**
     * @return the notEffects
     */
    public Effects getNotEffects( ) {

        return notEffects;
    }

    /**
     * @param notEffects
     *            the notEffects to set
     */
    public void setNotEffects( Effects notEffects ) {

        this.notEffects = notEffects;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {
        Action a = (Action) super.clone( );
        a.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        a.documentation = ( documentation != null ? new String( documentation ) : null );
        a.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        a.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        a.keepDistance = keepDistance;
        a.needsGoTo = needsGoTo;
        a.type = type;
        a.notEffects = ( notEffects != null ? (Effects) notEffects.clone( ) : null );
        a.activatedNotEffects = activatedNotEffects;
        return a;
    }

    /**
     * @return the activateNotEffects
     */
    public boolean isActivatedNotEffects( ) {

        return activatedNotEffects;
    }

    /**
     * @param activateNotEffects
     *            the activateNotEffects to set
     */
    public void setActivatedNotEffects( boolean activateNotEffects ) {

        this.activatedNotEffects = activateNotEffects;
    }

}
