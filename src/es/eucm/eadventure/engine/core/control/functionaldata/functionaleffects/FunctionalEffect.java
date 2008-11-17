package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.Effect;
import es.eucm.eadventure.common.data.chapterdata.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.MoveNPCEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.MovePlayerEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.PlaySoundEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.RandomEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerBookEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerCutsceneEffect;

/**
 * This abstract class defines how a certain effect must be triggered
 * in the game engine.
 */
public abstract class FunctionalEffect {

	/**
	 * The effect to be ruled
	 */
	protected Effect effect;
	
	/**
	 * Constructor
	 */
	public FunctionalEffect ( Effect effect ){
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
     * Static factory constructor for FunctionalEffects. Creates a new 
     * FunctionalEffect according to the type of the Effect provided as 
     * argument
     */
    public static FunctionalEffect buildFunctionalEffect ( Effect effect ){
    	FunctionalEffect fe = null;
    	switch (effect.getType()){
    		case Effect.ACTIVATE:
    			fe = new FunctionalActivateEffect ( (ActivateEffect)effect );
    			break;
    		case Effect.DEACTIVATE:
    			fe = new FunctionalDeactivateEffect ( (DeactivateEffect)effect );
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
    			break;
    	}
    	return fe;
    }

}
