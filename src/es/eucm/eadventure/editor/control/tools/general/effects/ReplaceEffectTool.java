package es.eucm.eadventure.editor.control.tools.general.effects;

import java.util.HashMap;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
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
import es.eucm.eadventure.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.WaitTimeEffect;
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
	protected AbstractEffect effect;
	protected HashMap<Integer, Object> newProperties;
	protected AbstractEffect oldEffect;
	
	protected AbstractEffect pos;
	protected AbstractEffect neg;
	
	public ReplaceEffectTool (Effects effects, AbstractEffect effect, HashMap<Integer, Object> newProperties){
		this(effects,effect,newProperties,null,null);
	}
	
	public ReplaceEffectTool (Effects effects, AbstractEffect effect, HashMap<Integer, Object> newProperties, AbstractEffect pos, AbstractEffect neg){
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
				oldEffect = (AbstractEffect) effect.clone();
			} catch (CloneNotSupportedException e) {
				ReportDialog.GenerateErrorReport(e, true, "Error cloning effect "+effect.getType());
			}
			switch( effectType ) {
				case Effect.ACTIVATE:
					ActivateEffect activateEffect = (ActivateEffect) effect;
					activateEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.DEACTIVATE:
					DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
					deactivateEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.SET_VALUE:
					SetValueEffect setValueEffect = (SetValueEffect) effect;
					setValueEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					setValueEffect.setValue( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_VALUE ) ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.INCREMENT_VAR:
					IncrementVarEffect incrementVarEffect = (IncrementVarEffect) effect;
					incrementVarEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					incrementVarEffect.setIncrement( Integer.parseInt((String) newProperties.get( EffectsController.EFFECT_PROPERTY_VALUE ) ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.DECREMENT_VAR:
					DecrementVarEffect decrementVarEffect = (DecrementVarEffect) effect;
					decrementVarEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					decrementVarEffect.setDecrement( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_VALUE ) ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.MACRO_REF:
					MacroReferenceEffect macroEffect = (MacroReferenceEffect) effect;
					macroEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.CONSUME_OBJECT:
					ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
					consumeObjectEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.GENERATE_OBJECT:
					GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
					generateObjectEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.SPEAK_PLAYER:
					SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
					speakPlayerEffect.setLine( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
					break;
				case Effect.SPEAK_CHAR:
					SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
					speakCharEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					speakCharEffect.setLine( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TEXT ) );
					break;
				case Effect.TRIGGER_BOOK:
					TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
					triggerBookEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.PLAY_SOUND:
					PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
					playSoundEffect.setPath( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_PATH ) );
					playSoundEffect.setBackground( Boolean.parseBoolean( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_BACKGROUND ) ) );
					break;
				case Effect.PLAY_ANIMATION:
					PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
					playAnimationEffect.setPath( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_PATH ) );
					playAnimationEffect.setDestiny( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.MOVE_PLAYER:
					MovePlayerEffect movePlayerEffect = (MovePlayerEffect) effect;
					movePlayerEffect.setDestiny( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.MOVE_NPC:
					MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
					moveNPCEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					moveNPCEffect.setDestiny( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.TRIGGER_CONVERSATION:
					TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
					triggerConversationEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.TRIGGER_CUTSCENE:
					TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
					triggerCutsceneEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.TRIGGER_SCENE:
					TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
					triggerSceneEffect.setTargetId( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
					triggerSceneEffect.setPosition( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_X ) ), Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.RANDOM_EFFECT:
					RandomEffect randomEffect = (RandomEffect) effect;
					randomEffect.setProbability( Integer.parseInt( (String)newProperties.get( EffectsController.EFFECT_PROPERTY_PROBABILITY )  ) );
					randomEffect.setPositiveEffect( pos );
					randomEffect.setNegativeEffect( neg );
					break;
				case Effect.WAIT_TIME:
				    	WaitTimeEffect waitTimeEffect = (WaitTimeEffect)effect;
				    	waitTimeEffect.setTime(Integer.parseInt((String)newProperties.get( EffectsController.EFFECT_PROPERTY_TIME )  ) );
				    	break;
				case Effect.SHOW_TEXT:
				    	ShowTextEffect showTextEffect = (ShowTextEffect)effect;
				    	showTextEffect.setText((String)newProperties.get( EffectsController.EFFECT_PROPERTY_TEXT )  );
				    	showTextEffect.setTextPosition(Integer.parseInt((String)newProperties.get( EffectsController.EFFECT_PROPERTY_X )  ), Integer.parseInt((String)newProperties.get( EffectsController.EFFECT_PROPERTY_Y )  ));
				    	showTextEffect.setRgbFrontColor(Integer.parseInt((String)newProperties.get( EffectsController.EFFECT_PROPERTY_FRONT_COLOR )  ));
				    	showTextEffect.setRgbBorderColor(Integer.parseInt((String)newProperties.get( EffectsController.EFFECT_PROPERTY_BORDER_COLOR )));
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