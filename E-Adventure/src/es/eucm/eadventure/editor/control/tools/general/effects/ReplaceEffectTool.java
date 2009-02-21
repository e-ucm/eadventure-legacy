package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.HashMap;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadventure.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadventure.common.data.chapter.effects.MovePlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadventure.common.data.chapter.effects.RandomEffect;
import es.eucm.eadventure.common.data.chapter.effects.SetValueEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding an effect
 * @author Javier
 *
 */
public class ReplaceEffectTool extends Tool{

	protected Effects effects;
	protected Effect effect;
	protected HashMap<Integer, String> newProperties;
	protected Effect oldEffect;
	
	protected Effect pos;
	protected Effect neg;
	
	public ReplaceEffectTool (Effects effects, Effect effect, HashMap<Integer, String> newProperties){
		this(effects,effect,newProperties,null,null);
	}
	
	public ReplaceEffectTool (Effects effects, Effect effect, HashMap<Integer, String> newProperties, Effect pos, Effect neg){
		this.effects = effects;
		this.effect = effect;
		this.pos = pos;
		this.neg = neg;
		this.newProperties = newProperties;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return (oldEffect!=null);
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		boolean effectEdited = false;

		int effectType = effect.getType( );

		// If a change has been made
		if( newProperties != null ) {
			effectEdited = true;
			try {
				oldEffect = (Effect) effect.clone();
			} catch (CloneNotSupportedException e) {
				ReportDialog.GenerateErrorReport(e, true, "Error cloning effect "+effect.getType());
			}
			switch( effectType ) {
				case Effect.ACTIVATE:
					ActivateEffect activateEffect = (ActivateEffect) effect;
					activateEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.DEACTIVATE:
					DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
					deactivateEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.SET_VALUE:
					SetValueEffect setValueEffect = (SetValueEffect) effect;
					setValueEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					setValueEffect.setValue( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_VALUE ) ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.INCREMENT_VAR:
					IncrementVarEffect incrementVarEffect = (IncrementVarEffect) effect;
					incrementVarEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					incrementVarEffect.setIncrement( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_VALUE ) ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.DECREMENT_VAR:
					DecrementVarEffect decrementVarEffect = (DecrementVarEffect) effect;
					decrementVarEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					decrementVarEffect.setDecrement( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_VALUE ) ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.MACRO_REF:
					MacroReferenceEffect macroEffect = (MacroReferenceEffect) effect;
					macroEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.CONSUME_OBJECT:
					ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
					consumeObjectEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.GENERATE_OBJECT:
					GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
					generateObjectEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.SPEAK_PLAYER:
					SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
					speakPlayerEffect.setLine( newProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
					break;
				case Effect.SPEAK_CHAR:
					SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
					speakCharEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					speakCharEffect.setLine( newProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
					break;
				case Effect.TRIGGER_BOOK:
					TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
					triggerBookEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.PLAY_SOUND:
					PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
					playSoundEffect.setPath( newProperties.get( EffectsController.EFFECT_PROPERTY_PATH ) );
					playSoundEffect.setBackground( Boolean.parseBoolean( newProperties.get( EffectsController.EFFECT_PROPERTY_BACKGROUND ) ) );
					break;
				case Effect.PLAY_ANIMATION:
					PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
					playAnimationEffect.setPath( newProperties.get( EffectsController.EFFECT_PROPERTY_PATH ) );
					playAnimationEffect.setDestiny( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.MOVE_PLAYER:
					MovePlayerEffect movePlayerEffect = (MovePlayerEffect) effect;
					movePlayerEffect.setDestiny( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.MOVE_NPC:
					MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
					moveNPCEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					moveNPCEffect.setDestiny( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.TRIGGER_CONVERSATION:
					TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
					triggerConversationEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.TRIGGER_CUTSCENE:
					TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
					triggerCutsceneEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.TRIGGER_SCENE:
					TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
					triggerSceneEffect.setTargetId( newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					triggerSceneEffect.setPosition( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.RANDOM_EFFECT:
					RandomEffect randomEffect = (RandomEffect) effect;
					randomEffect.setProbability( Integer.parseInt( newProperties.get( EffectsController.EFFECT_PROPERTY_PROBABILITY )  ) );
					randomEffect.setPositiveEffect( pos );
					randomEffect.setNegativeEffect( neg );
					break;
			}
			effectEdited = true;
		}

		return effectEdited;
	}

	@Override
	public boolean redoTool() {
		int index = effects.getEffects().indexOf(oldEffect);
		effects.getEffects().remove(oldEffect);
		effects.getEffects().add(index, effect);
		Controller.getInstance( ).updateFlagSummary( );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		int index = effects.getEffects().indexOf(effect);
		effects.getEffects().remove(effect);
		effects.getEffects().add(index, oldEffect);
		Controller.getInstance( ).updateFlagSummary( );
		Controller.getInstance().updatePanel();
		return true;
	}

}
