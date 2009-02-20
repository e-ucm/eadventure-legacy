package es.eucm.eadventure.editor.control.tools.general.effects;

import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.RandomEffect;
import es.eucm.eadventure.common.data.chapter.effects.SetValueEffect;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for deleting an effect
 * @author Javier
 *
 */
public class DeleteEffectTool extends Tool{
	
	protected Effects effects;
	protected Effect effectDeleted;
	protected int index;
	protected Controller controller;
	
	
	public DeleteEffectTool (Effects effects, int index){
		this.effects = effects;
		this.index = index;
		controller = Controller.getInstance();
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		effectDeleted = effects.getEffects().remove(index);
		updateVarFlagSummary(effectDeleted);
		return true;
	}

	@Override
	public boolean redoTool() {
		effects.getEffects().remove(index);
		updateVarFlagSummary(effectDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		effects.getEffects().add(index, effectDeleted);
		undoUpdateVarFlagSummary(effectDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}
	
	/**
	 * Updates the varFlag summary (the references to flags and variables if the effect given as argument has any)
	 * @param effect
	 */
	protected void updateVarFlagSummary(Effect effect){
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
			RandomEffect randomEffect = (RandomEffect)effect;
			if (randomEffect.getPositiveEffect()!=null)
				updateVarFlagSummary(randomEffect.getPositiveEffect());
			if (randomEffect.getNegativeEffect()!=null)
				updateVarFlagSummary(randomEffect.getNegativeEffect());
		}
	}
	
	/**
	 * Undoes the actions performed in updateVarFlagSummary
	 * @param effect
	 */
	protected void undoUpdateVarFlagSummary(Effect effect){
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
			RandomEffect randomEffect = (RandomEffect)effect;
			if (randomEffect.getPositiveEffect()!=null)
				undoUpdateVarFlagSummary(randomEffect.getPositiveEffect());
			if (randomEffect.getNegativeEffect()!=null)
				undoUpdateVarFlagSummary(randomEffect.getNegativeEffect());
		}
	}
}
