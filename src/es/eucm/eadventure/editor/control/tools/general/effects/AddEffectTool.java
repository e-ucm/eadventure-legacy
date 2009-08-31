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
package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding an effect
 * 
 * @author Javier
 * 
 */
public class AddEffectTool extends Tool {

    protected Effects effects;

    protected AbstractEffect effectToAdd;

    protected List<ConditionsController> conditions;

    protected ConditionsController condition;

    public AddEffectTool( Effects effects, AbstractEffect effectToAdd, List<ConditionsController> conditions ) {

        this.effects = effects;
        this.effectToAdd = effectToAdd;
        this.conditions = conditions;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        effects.add( effectToAdd );
        if( conditions != null ) {
            condition = new ConditionsController( effectToAdd.getConditions( ) );
            conditions.add( condition );
        }

        return true;
    }

    @Override
    public boolean redoTool( ) {

        boolean done = doTool( );
        if( done ) {
            Controller.getInstance( ).updateVarFlagSummary( );
            Controller.getInstance( ).updatePanel( );
        }
        return done;
    }

    @Override
    public boolean undoTool( ) {

        effects.getEffects( ).remove( effectToAdd );
        if( conditions != null ) {
            conditions.remove( condition );
        }
        Controller.getInstance( ).updateVarFlagSummary( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
