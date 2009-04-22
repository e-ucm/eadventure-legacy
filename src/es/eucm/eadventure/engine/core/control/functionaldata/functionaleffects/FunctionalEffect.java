package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
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
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;

/**
 * This abstract class defines how a certain effect must be triggered
 * in the game engine.
 */
public abstract class FunctionalEffect {

	/**
	 * The effect to be ruled
	 */
	protected AbstractEffect effect;
	
	/**
	 * Constructor
	 */
	public FunctionalEffect ( AbstractEffect effect ){
		this.effect = effect;
	}
	
    /**
     * triggers the effect
     */
    public abstract void triggerEffect( );

    /**
     * Returns whether the effect is instantaneous.
     * @return whether the effect is instantaneous
     */
    public abstract boolean isInstantaneous( );
    
    /**
     * Returns whether the effect is still running
     * @return true if the effect is still running, false otherwise
     */
    public abstract boolean isStillRunning();
    
    /**
     * Returns true if all conditions associated to this effect are OK
     * @return
     */
    public boolean isAllConditionsOK(){
	return new FunctionalConditions(effect.getConditions()).allConditionsOk();
    }
    
    /**
     * Static factory constructor for FunctionalEffects. Creates a new 
     * FunctionalEffect according to the type of the Effect provided as 
     * argument
     */
    public static FunctionalEffect buildFunctionalEffect ( AbstractEffect effect ){
    	FunctionalEffect fe = null;
    	switch (effect.getType()){
    		case Effect.ACTIVATE:
    			fe = new FunctionalActivateEffect ( (ActivateEffect)effect );
    			break;
    		case Effect.DEACTIVATE:
    			fe = new FunctionalDeactivateEffect ( (DeactivateEffect)effect );
    			break;
    		case Effect.SET_VALUE:
    			fe = new FunctionalSetValueEffect ( (SetValueEffect)effect );
    			break;
    		case Effect.INCREMENT_VAR:
    			fe = new FunctionalIncrementVarEffect ( (IncrementVarEffect)effect );
    			break;
    		case Effect.DECREMENT_VAR:
    			fe = new FunctionalDecrementVarEffect ( (DecrementVarEffect)effect );
    			break;
    		case Effect.MACRO_REF:
    			fe = new FunctionalMacroReferenceEffect ( (MacroReferenceEffect)effect );
    			break;
    		case Effect.CANCEL_ACTION:
    			break;
    		case Effect.CONSUME_OBJECT:
    			fe = new FunctionalConsumeObjectEffect ( (ConsumeObjectEffect)effect );
    			break;
    		case Effect.GENERATE_OBJECT:
    			fe = new FunctionalGenerateObjectEffect ( (GenerateObjectEffect)effect );
    			break;
    		case Effect.MOVE_NPC:
    			fe = new FunctionalMoveNPCEffect ( (MoveNPCEffect)effect );
    			break;
    		case Effect.MOVE_PLAYER:
    			fe = new FunctionalMovePlayerEffect ( (MovePlayerEffect)effect );
    			break;
    		case Effect.PLAY_ANIMATION:
    			fe = new FunctionalPlayAnimationEffect ( (PlayAnimationEffect)effect );
    			break;
    		case Effect.PLAY_SOUND:
    			fe = new FunctionalPlaySoundEffect ( (PlaySoundEffect)effect );
    			break;
    		case Effect.RANDOM_EFFECT:
    			fe = new FunctionalRandomEffect ( (RandomEffect)effect );
    			break;
    		case Effect.SPEAK_CHAR:
    			fe = new FunctionalSpeakCharEffect ( (SpeakCharEffect)effect );
    			break;
    		case Effect.SPEAK_PLAYER:
    			fe = new FunctionalSpeakPlayerEffect ( (SpeakPlayerEffect)effect );
    			break;
    		case Effect.TRIGGER_BOOK:
    			fe = new FunctionalTriggerBookEffect ( (TriggerBookEffect)effect );
    			break;
    		case Effect.TRIGGER_CONVERSATION:
    			fe = new FunctionalTriggerConversationEffect ( (TriggerConversationEffect)effect );
    			break;
    		case Effect.TRIGGER_CUTSCENE:
    			fe = new FunctionalTriggerCutsceneEffect ( (TriggerCutsceneEffect)effect );
    			break;
    		case Effect.TRIGGER_LAST_SCENE:
    			fe = new FunctionalTriggerLastSceneEffect ( );
    			break;
    		case Effect.TRIGGER_SCENE:
    			fe = new FunctionalTriggerSceneEffect ( (TriggerSceneEffect)effect );
    			break;
    		case Effect.SHOW_TEXT:
			fe = new FunctionalShowTextEffect( (ShowTextEffect)effect );
			break;
    		case Effect.WAIT_TIME:
			fe = new FunctionalWaitTimeEffect( (WaitTimeEffect)effect );
			break;
    	}
    	return fe;
    }

}
