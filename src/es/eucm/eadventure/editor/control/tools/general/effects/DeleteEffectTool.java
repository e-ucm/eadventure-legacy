/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.RandomEffect;
import es.eucm.eadventure.common.data.chapter.effects.SetValueEffect;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for deleting an effect
 * 
 * @author Javier
 * 
 */
public class DeleteEffectTool extends Tool {

    protected Effects effects;

    protected AbstractEffect effectDeleted;

    protected int index;

    protected Controller controller;

    protected List<ConditionsController> conditions;

    protected ConditionsController condition;

    public DeleteEffectTool( Effects effects, int index, List<ConditionsController> conditions ) {

        this.effects = effects;
        this.index = index;
        this.conditions = conditions;
        controller = Controller.getInstance( );
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

        effectDeleted = effects.getEffects( ).remove( index );
        condition = conditions.remove( index );
        updateVarFlagSummary( effectDeleted );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        effects.getEffects( ).remove( index );
        conditions.remove( index );
        updateVarFlagSummary( effectDeleted );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        effects.getEffects( ).add( index, effectDeleted );
        conditions.add( index, condition );
        undoUpdateVarFlagSummary( effectDeleted );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    /**
     * Updates the varFlag summary (the references to flags and variables if the
     * effect given as argument has any)
     * 
     * @param effect
     */
    protected void updateVarFlagSummary( Effect effect ) {

        if( effect.getType( ) == Effect.ACTIVATE ) {
            ActivateEffect activateEffect = (ActivateEffect) effect;
            controller.getVarFlagSummary( ).deleteReference( activateEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.DEACTIVATE ) {
            DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
            controller.getVarFlagSummary( ).deleteReference( deactivateEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.SET_VALUE ) {
            SetValueEffect setValueEffect = (SetValueEffect) effect;
            controller.getVarFlagSummary( ).deleteReference( setValueEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.INCREMENT_VAR ) {
            IncrementVarEffect setValueEffect = (IncrementVarEffect) effect;
            controller.getVarFlagSummary( ).deleteReference( setValueEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.DECREMENT_VAR ) {
            DecrementVarEffect setValueEffect = (DecrementVarEffect) effect;
            controller.getVarFlagSummary( ).deleteReference( setValueEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.RANDOM_EFFECT ) {
            RandomEffect randomEffect = (RandomEffect) effect;
            if( randomEffect.getPositiveEffect( ) != null )
                updateVarFlagSummary( randomEffect.getPositiveEffect( ) );
            if( randomEffect.getNegativeEffect( ) != null )
                updateVarFlagSummary( randomEffect.getNegativeEffect( ) );
        }
    }

    /**
     * Undoes the actions performed in updateVarFlagSummary
     * 
     * @param effect
     */
    protected void undoUpdateVarFlagSummary( Effect effect ) {

        if( effect.getType( ) == Effect.ACTIVATE ) {
            ActivateEffect activateEffect = (ActivateEffect) effect;
            controller.getVarFlagSummary( ).addReference( activateEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.DEACTIVATE ) {
            DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
            controller.getVarFlagSummary( ).addReference( deactivateEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.SET_VALUE ) {
            SetValueEffect setValueEffect = (SetValueEffect) effect;
            controller.getVarFlagSummary( ).addReference( setValueEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.INCREMENT_VAR ) {
            IncrementVarEffect setValueEffect = (IncrementVarEffect) effect;
            controller.getVarFlagSummary( ).addReference( setValueEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.DECREMENT_VAR ) {
            DecrementVarEffect setValueEffect = (DecrementVarEffect) effect;
            controller.getVarFlagSummary( ).addReference( setValueEffect.getTargetId( ) );
        }

        else if( effect.getType( ) == Effect.RANDOM_EFFECT ) {
            RandomEffect randomEffect = (RandomEffect) effect;
            if( randomEffect.getPositiveEffect( ) != null )
                undoUpdateVarFlagSummary( randomEffect.getPositiveEffect( ) );
            if( randomEffect.getNegativeEffect( ) != null )
                undoUpdateVarFlagSummary( randomEffect.getNegativeEffect( ) );
        }
    }
}
