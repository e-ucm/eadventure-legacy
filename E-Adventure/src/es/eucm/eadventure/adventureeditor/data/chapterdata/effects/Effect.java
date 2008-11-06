package es.eucm.eadventure.adventureeditor.data.chapterdata.effects;

/**
 * This interface defines any individual effect that can be triggered by a player's action during the game.
 */
public interface Effect {

	/**
	 * Constant for activate effect.
	 */
	public static final int ACTIVATE = 0;

	/**
	 * Constant for deactivate effect.
	 */
	public static final int DEACTIVATE = 1;

	/**
	 * Constant for consume-object effect.
	 */
	public static final int CONSUME_OBJECT = 2;

	/**
	 * Constant for generate-object effect.
	 */
	public static final int GENERATE_OBJECT = 3;

	/**
	 * Constant for cancel-action effect.
	 */
	public static final int CANCEL_ACTION = 4;

	/**
	 * Constant for speak-player effect.
	 */
	public static final int SPEAK_PLAYER = 5;

	/**
	 * Constant for speak-char effect.
	 */
	public static final int SPEAK_CHAR = 6;

	/**
	 * Constant for trigger-book effect.
	 */
	public static final int TRIGGER_BOOK = 7;

	/**
	 * Constant for play-sound effect.
	 */
	public static final int PLAY_SOUND = 8;

	/**
	 * Constant for play-animation effect.
	 */
	public static final int PLAY_ANIMATION = 9;

	/**
	 * Constant for move-player effect.
	 */
	public static final int MOVE_PLAYER = 10;

	/**
	 * Constant for move-npc effect.
	 */
	public static final int MOVE_NPC = 11;

	/**
	 * Constant for trigger-conversation effect.
	 */
	public static final int TRIGGER_CONVERSATION = 12;

	/**
	 * Constant for trigger-cutscene effect.
	 */
	public static final int TRIGGER_CUTSCENE = 13;

	/**
	 * Constant for trigger-scene effect.
	 */
	public static final int TRIGGER_SCENE = 14;
	
	/**
	 * Constant for trigger-last-scene effect.
	 */
	public static final int TRIGGER_LAST_SCENE = 15;
	
	/**
	 * Constant for random-effect.
	 */
	public static final int RANDOM_EFFECT = 16;


	/**
	 * Returns the type of the effect.
	 * 
	 * @return Type of the effect
	 */
	public int getType( );
}
