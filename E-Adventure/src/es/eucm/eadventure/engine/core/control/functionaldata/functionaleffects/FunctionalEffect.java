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
package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.HighlightItemEffect;
import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadventure.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadventure.common.data.chapter.effects.MoveObjectEffect;
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
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.tracking.pub._GameLog;
import es.eucm.eadventure.tracking.pub._HighLevelEvents;

/**
 * This abstract class defines how a certain effect must be triggered in the
 * game engine.
 */
public abstract class FunctionalEffect implements _HighLevelEvents{

    /**
     * The effect to be ruled
     */
    protected AbstractEffect effect;
    
    /**
     * The gamelog for tracking when effects are triggered
     */
    protected _GameLog gameLog;

    /**
     * Constructor
     */
    public FunctionalEffect( AbstractEffect effect ) {

        this.effect = effect;
        this.gameLog = Game.getInstance( ).getGameLog( );
    }

    /**
     * triggers the effect
     */
    public abstract void triggerEffect( );

    /**
     * Returns whether the effect is instantaneous.
     * 
     * @return whether the effect is instantaneous
     */
    public abstract boolean isInstantaneous( );

    /**
     * Returns whether the effect is still running
     * 
     * @return true if the effect is still running, false otherwise
     */
    public abstract boolean isStillRunning( );

    /**
     * Returns true if all conditions associated to this effect are OK
     * 
     * @return
     */
    public boolean isAllConditionsOK( ) {

        if( effect != null )
            return new FunctionalConditions( effect.getConditions( ) ).allConditionsOk( );
        else
            return true;
    }

    /**
     * Static factory constructor for FunctionalEffects. Creates a new
     * FunctionalEffect according to the type of the Effect provided as argument
     */
    public static FunctionalEffect buildFunctionalEffect( AbstractEffect effect ) {

        FunctionalEffect fe = null;
        switch( effect.getType( ) ) {
            case Effect.ACTIVATE:
                fe = new FunctionalActivateEffect( (ActivateEffect) effect );
                break;
            case Effect.DEACTIVATE:
                fe = new FunctionalDeactivateEffect( (DeactivateEffect) effect );
                break;
            case Effect.SET_VALUE:
                fe = new FunctionalSetValueEffect( (SetValueEffect) effect );
                break;
            case Effect.INCREMENT_VAR:
                fe = new FunctionalIncrementVarEffect( (IncrementVarEffect) effect );
                break;
            case Effect.DECREMENT_VAR:
                fe = new FunctionalDecrementVarEffect( (DecrementVarEffect) effect );
                break;
            case Effect.MACRO_REF:
                fe = new FunctionalMacroReferenceEffect( (MacroReferenceEffect) effect );
                break;
            case Effect.CANCEL_ACTION:
                break;
            case Effect.CONSUME_OBJECT:
                fe = new FunctionalConsumeObjectEffect( (ConsumeObjectEffect) effect );
                break;
            case Effect.GENERATE_OBJECT:
                fe = new FunctionalGenerateObjectEffect( (GenerateObjectEffect) effect );
                break;
            case Effect.MOVE_NPC:
                fe = new FunctionalMoveNPCEffect( (MoveNPCEffect) effect );
                break;
            case Effect.MOVE_PLAYER:
                fe = new FunctionalMovePlayerEffect( (MovePlayerEffect) effect );
                break;
            case Effect.PLAY_ANIMATION:
                fe = new FunctionalPlayAnimationEffect( (PlayAnimationEffect) effect );
                break;
            case Effect.PLAY_SOUND:
                fe = new FunctionalPlaySoundEffect( (PlaySoundEffect) effect );
                break;
            case Effect.RANDOM_EFFECT:
                fe = new FunctionalRandomEffect( (RandomEffect) effect );
                break;
            case Effect.SPEAK_CHAR:
                fe = new FunctionalSpeakCharEffect( (SpeakCharEffect) effect );
                break;
            case Effect.SPEAK_PLAYER:
                fe = new FunctionalSpeakPlayerEffect( (SpeakPlayerEffect) effect );
                break;
            case Effect.TRIGGER_BOOK:
                fe = new FunctionalTriggerBookEffect( (TriggerBookEffect) effect );
                break;
            case Effect.TRIGGER_CONVERSATION:
                fe = new FunctionalTriggerConversationEffect( (TriggerConversationEffect) effect );
                break;
            case Effect.TRIGGER_CUTSCENE:
                fe = new FunctionalTriggerCutsceneEffect( (TriggerCutsceneEffect) effect );
                break;
            case Effect.TRIGGER_LAST_SCENE:
                fe = new FunctionalTriggerLastSceneEffect( );
                break;
            case Effect.TRIGGER_SCENE:
                fe = new FunctionalTriggerSceneEffect( (TriggerSceneEffect) effect );
                break;
            case Effect.SHOW_TEXT:
                fe = new FunctionalShowTextEffect( (ShowTextEffect) effect );
                break;
            case Effect.WAIT_TIME:
                fe = new FunctionalWaitTimeEffect( (WaitTimeEffect) effect );
                break;
            case Effect.HIGHLIGHT_ITEM:
                fe = new FunctionalHighlightItemEffect( (HighlightItemEffect) effect );
                break;
            case Effect.MOVE_OBJECT:
                fe = new FunctionalMoveObjectEffect( (MoveObjectEffect) effect);
                break;
        }
        return fe;
    }

    public boolean canSkip( ) {
        return false;
    }

    public void skip( ) {
    }
    public FunctionalEffect getTriggerEffect(){
        return null;
        }

    /**
     * Returns the three letter code used for logging events with the tracking system
     * @return
     */
    public String getCode(){
        String str="unk";
        if (effect!=null){
            switch( effect.getType( ) ) {
                case Effect.ACTIVATE:
                    str= ACTIVATE;
                    break;
                case Effect.DEACTIVATE:
                    str= DEACTIVATE;
                    break;
                case Effect.SET_VALUE:
                    str= SET_VALUE;
                    break;
                case Effect.INCREMENT_VAR:
                    str = INCREMENT_VAR;
                    break;
                case Effect.DECREMENT_VAR:
                    str = DECREMENT_VAR;
                    break;
                case Effect.MACRO_REF:
                    str = MACRO_REF;
                    break;
                case Effect.CANCEL_ACTION:
                    str = CANCEL_ACTION;
                    break;
                case Effect.CONSUME_OBJECT:
                    str = CONSUME_OBJECT;
                    break;
                case Effect.GENERATE_OBJECT:
                    str = GENERATE_OBJECT;
                    break;
                case Effect.MOVE_NPC:
                    str = MOVE_NPC;
                    break;
                case Effect.MOVE_PLAYER:
                    str = MOVE_PLAYER;
                    break;
                case Effect.PLAY_ANIMATION:
                    str = PLAY_ANIMATION;
                    break;
                case Effect.PLAY_SOUND:
                    str = PLAY_SOUND;
                    break;
                case Effect.RANDOM_EFFECT:
                    str = RANDOM_EFFECT;
                    break;
                case Effect.SPEAK_CHAR:
                    str = SPEAK_CHAR;
                    break;
                case Effect.SPEAK_PLAYER:
                    str = SPEAK_PLAYER;
                    break;
                case Effect.TRIGGER_BOOK:
                    str = TRIGGER_BOOK;
                    break;
                case Effect.TRIGGER_CONVERSATION:
                    str = TRIGGER_CONVERSATION;
                    break;
                case Effect.TRIGGER_CUTSCENE:
                    str = TRIGGER_CUTSCENE;
                    break;
                case Effect.TRIGGER_LAST_SCENE:
                    str = TRIGGER_LAST_SCENE;
                    break;
                case Effect.TRIGGER_SCENE:
                    str = TRIGGER_SCENE;
                    break;
                case Effect.SHOW_TEXT:
                    str = SHOW_TEXT;
                    break;
                case Effect.WAIT_TIME:
                    str = WAIT_TIME;
                    break;
                case Effect.HIGHLIGHT_ITEM:
                    str = HIGHLIGHT_ITEM;
                    break;
                case Effect.MOVE_OBJECT:
                    str = MOVE_OBJECT;
                    break;
            }
        }
        return str;
    }
}
