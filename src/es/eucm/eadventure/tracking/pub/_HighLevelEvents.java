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
package es.eucm.eadventure.tracking.pub;

/**
 * Convenient interface for keeping handy shortcuts to high level event identifiers.
 * @author Javier Torrente
 *
 */
public interface _HighLevelEvents {

    // Unary actions
    
    /**
     * Identifier for the next high level event (unary action): An interactive element (object or character) has been used (by clicking the "Use" button).
     */
    public static final String USE="use";
    
    /**
     * Identifier for the next high level event (unary action): An interactive element (object or character) has been grabbed (by clicking the "Grab" button).
     */    
    public static final String GRAB="grb";
    
    /**
     * Identifier for the next high level event (unary action): Player has talked to a character (by explicitly clicking on "Talk to" button).
     */
    public static final String TALK="tlk";
    
    /**
     * Identifier for the next high level event (unary action): An interactive element (object or character) has been examined (by clicking the "Examine" button).
     */
    public static final String EXAMINE="exa";
    
    /**
     * Identifier for the next high level event (unary action): An interactive element (object or character) has been looked at (by clicking on the element - works only if the game has not been configured to show action buttons with a left click).
     */
    public static final String LOOK="loo";
    
    /**
     * Identifier for the next high level event (unary action): an exit is activated (by clicking on it, only if it is reachable).
     */
    public static final String EXIT_CLICK="exc";
    
    /**
     * Identifier for the next high level event (unary action): A custom action is triggered on an interactive element (name of action is logged).
     */
    public static final String CUSTOM="cus";
    
    /**
     * Identifier for the next high level event (unary action): Player's avatar moves to a different location on the screen (player clicks on any point of the scene that does not contain an interactive element). Only applicable in third person games.
     */
    public static final String GO_TO="g2";

    // Binary actions (interactions)
    
    /**
     * Identifier for the next high level event (binary action started): A custom interaction was started (the first element is selected) but has not finished yet. Name of custom interaction is logged, along with the first element involved in the action.
     */
    public static final String START_CUSTOM_INTERACTION="sci";
    /**
     * Identifier for the next high level event (binary action completed): A custom interaction was completed (the first element was applied to a second element). Name of custom interaction is logged, along with the name of both elements involved in the action.
     */
    public static final String END_CUSTOM_INTERACTION="eci";

    /**
     * Identifier for the next high level event (binary action started): A use-with interaction was started (the first object is selected) but has not finished yet.
     */
    public static final String START_USE_WITH="suw";
    /**
     * Identifier for the next high level event (binary action completed): A use-with interaction was completed (the first element was applied to a second element). Name both elements involved in the action are logged.
     */
    public static final String END_USE_WITH="euw";
    
    /**
     * Identifier for the next high level event (binary action started): A give-to interaction was started (the first object is selected) but has not finished yet.
     */    
    public static final String START_GIVE_TO="sgt";
    /**
     * Identifier for the next high level event (binary action completed): A give-to interaction was completed (the first element was applied to a character). Name both elements involved in the action are logged.
     */
    public static final String END_GIVE_TO="egt";
    
    /**
     * Identifier for the next high level event (binary action started): A drag-to interaction was started (the first object is selected) but has not finished yet.
     */    
    public static final String START_DRAG_TO="sdt";
    /**
     * Identifier for the next high level event (binary action completed): A drag-to interaction was completed (the first element was dragged to a second element). Name both elements involved in the action are logged.
     */
    public static final String END_DRAG_TO="edt";
    
    // State related events
    
    /**
     * Identifier for the next high level event (new GameState entered): Book.
     */
    public static final String BOOK_ENTER="bin";
    /**
     * Identifier for the next high level event (GameState exited): Book.
     */
    public static final String BOOK_EXIT="bout";
    /**
     * Identifier for the next high level event (GameStateBook event): the previous page button is activated.
     */
    public static final String BOOK_BROWSE_PREVPAGE="bpr";
    /**
     * Identifier for the next high level event (GameStateBook event): the next page button is activated.
     */
    public static final String BOOK_BROWSE_NEXTPAGE="bnxt";
    
    /**
     * Identifier for the next high level event (new GameState entered): Slidescene.
     */
    public static final String SLIDESCENE_ENTER="sin";
    /**
     * Identifier for the next high level event (GameState exited): Slidescene.
     */
    public static final String SLIDESCENE_EXIT="sout";
    
    /**
     * Identifier for the next high level event (GameStateSlidescene event): The next slide is shown.
     */    
    public static final String SLIDESCENE_NEXT="snxt";
    
    /**
     * Identifier for the next high level event (new GameState entered): Videoscene.
     */
    public static final String VIDEOSCENE_ENTER="vin";

    /**
     * Identifier for the next high level event (GameState exited): Videoscene.
     */
    public static final String VIDEOSCENE_EXIT="vout";
    
    /**
     * Identifier for the next high level event (GameStateVideoscene event): Video is skipped.
     */
    public static final String VIDEOSCENE_SKIP="vnxt";

    /**
     * Identifier for the next high level event (new GameState entered): RunEffects.
     */
    public static final String RUNEFFECTS_ENTER="rin";
    /**
     * Identifier for the next high level event (GameState exited): RunEffects.
     */
    public static final String RUNEFFECTS_EXIT="rout";
    
    /**
     * Identifier for the next high level event (GameStateRunEffects event): Effect is skipped.
     */
    public static final String RUNEFFECTS_EFFECT_SKIPPED="rnxt";
    
    /**
     * Identifier for the next high level event (new GameState entered): Conversation. This kind of event occurs both with the player clicks on a "Talk-to" button but also when a trigger-conversation effect is triggered.
     */
    public static final String CONV_ENTER="cin";
    /**
     * Identifier for the next high level event (GameState exited): Conversation.
     */
    public static final String CONV_EXIT="cout";
    
    /**
     * Identifier for the next high level event (GameStateConversation event): Dialog line is skipped.
     */
    public static final String CONV_SKIP_LINE="cnxt";
    
    /**
     * Identifier for the next high level event (GameStateConversation event): A node is skipped.
     */
    public static final String CONV_SKIP_NODE="cnde";
    
    /**
     * Identifier for the next high level event (GameStateConversation event): Option is selected.
     */
    public static final String CONV_SELECT_OPTION="copt";
    
    /**
     * Identifier for the next high level event (new GameState entered): Menu.
     */
    public static final String MENU_OPEN="min";
    /**
     * Identifier for the next high level event (GameState exited): Menu.
     */
    public static final String MENU_CLOSE="mout";
    
    /**
     * Identifier for the next high level event (GameStateMenu event): A menu item or submenu is browsed/clicked.
     */
    public static final String MENU_BROWSE="mbws";

    // Other events
    
    /**
     * Identifier for the next high level event: The game is quited.
     */
    public static final String EXIT_GAME="exg";
    
    /**
     * Identifier for the next high level event: Action buttons are shown for an interactive element.
     */
    public static final String SHOW_ACTIONS="act";
    
    /**
     * Identifier for the next high level event: Current scene changes.
     */
    public static final String NEW_SCENE="scn";
    
    /**
     * Identifier for the next high level event: right scroll arrow is clicked (only in first person games).
     */
    public static final String OFFSET_ARROW_RIGHT="oar";
    /**
     * Identifier for the next high level event: left scroll arrow is clicked (only in first person games).
     */    
    public static final String OFFSET_ARROW_LEFT="oal";


    // Run effects
    /*
     * Each effect has a 3-letter code that is logged when it is triggered. 
     */
    /**
     * Constant for activate effect.
     */
    public static final String ACTIVATE = "act";

    /**
     * Constant for deactivate effect.
     */
    public static final String DEACTIVATE = "dct";

    /**
     * Constant for consume-object effect.
     */
    public static final String CONSUME_OBJECT = "cob";

    /**
     * Constant for generate-object effect.
     */
    public static final String GENERATE_OBJECT = "gob";

    /**
     * Constant for cancel-action effect.
     */
    public static final String CANCEL_ACTION = "can";

    /**
     * Constant for speak-player effect.
     */
    public static final String SPEAK_PLAYER = "spl";

    /**
     * Constant for speak-char effect.
     */
    public static final String SPEAK_CHAR = "sch";

    /**
     * Constant for trigger-book effect.
     */
    public static final String TRIGGER_BOOK = "tbk";

    /**
     * Constant for play-sound effect.
     */
    public static final String PLAY_SOUND = "psn";

    /**
     * Constant for play-animation effect.
     */
    public static final String PLAY_ANIMATION = "pan";

    /**
     * Constant for move-player effect.
     */
    public static final String MOVE_PLAYER = "mpl";

    /**
     * Constant for move-npc effect.
     */
    public static final String MOVE_NPC = "mch";

    /**
     * Constant for trigger-conversation effect.
     */
    public static final String TRIGGER_CONVERSATION ="tcn";

    /**
     * Constant for trigger-cutscene effect.
     */
    public static final String TRIGGER_CUTSCENE = "tcu";

    /**
     * Constant for trigger-scene effect.
     */
    public static final String TRIGGER_SCENE = "tsc";

    /**
     * Constant for trigger-last-scene effect.
     */
    public static final String TRIGGER_LAST_SCENE = "tls";

    /**
     * Constant for random-effect.
     */
    public static final String RANDOM_EFFECT = "ran";

    /**
     * Constant for set-value effect.
     */
    public static final String SET_VALUE = "set";

    /**
     * Constant for increment var effect.
     */
    public static final String INCREMENT_VAR = "inc";

    /**
     * Constant for decrement var effect.
     */
    public static final String DECREMENT_VAR = "dec";

    /**
     * Constant for macro-ref effect.
     */
    public static final String MACRO_REF = "mac";

    /**
     * Constant for wait-time effect
     */
    public static final String WAIT_TIME = "wai";

    /**
     * Constant for show-text effect
     */
    public static final String SHOW_TEXT = "txt";
    
    /**
     * Constant for highlight element effect
     */
    public static final String HIGHLIGHT_ITEM = "hgl";
    
    /**
     * Constant for move object effect
     */
    public static final String MOVE_OBJECT = "mob";
    
    
}
