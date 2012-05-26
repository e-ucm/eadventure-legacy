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
package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
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
            condition = new ConditionsController( effectToAdd.getConditions( ), Controller.EFFECT, EffectsController.getEffectInfo( effectToAdd ) );
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
