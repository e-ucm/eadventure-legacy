package es.eucm.eadventure.adventureeditor.control.controllers;

import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.auxiliar.categoryfilters.AnimationFileFilter;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.ActivateEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.CancelActionEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.ConsumeObjectEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.DeactivateEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.Effect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.Effects;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.GenerateObjectEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.MoveNPCEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.MovePlayerEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.PlayAnimationEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.PlaySoundEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.RandomEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.SpeakCharEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.SpeakPlayerEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.TriggerBookEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.TriggerConversationEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.TriggerLastSceneEffect;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.TriggerSceneEffect;
import es.eucm.eadventure.adventureeditor.data.supportdata.FlagSummary;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.assetchooser.AssetChooser;
import es.eucm.eadventure.adventureeditor.gui.editdialogs.effectdialogs.EffectDialog;

/**
 * This class is the controller of the effects blocks. It manages the insertion and modification of the effects lists.
 * 
 * @author Bruno Torijano Bueno
 */
public class EffectsController {

	/**
	 * Constant for effect property. Refers to target elements.
	 */
	public static final int EFFECT_PROPERTY_TARGET = 0;

	/**
	 * Constant for effect property. Refers to an asset path.
	 */
	public static final int EFFECT_PROPERTY_PATH = 1;

	/**
	 * Constant for effect property. Refers to text content.
	 */
	public static final int EFFECT_PROPERTY_TEXT = 2;

	/**
	 * Constant for effect property. Refers to X coordinate.
	 */
	public static final int EFFECT_PROPERTY_X = 3;

	/**
	 * Constant for effect property. Refers to Y coordinate.
	 */
	public static final int EFFECT_PROPERTY_Y = 4;

	/**
	 * Constant for effect property. Refers to "Play in background" flag.
	 */
	public static final int EFFECT_PROPERTY_BACKGROUND = 5;
	
	/**
	 * Constant for effect property. Refers to "Play in background" flag.
	 */
	public static final int EFFECT_PROPERTY_PROBABILITY = 6;

	/**
	 * Constant to filter the selection of an asset. Used for animations.
	 */
	public static final int ASSET_ANIMATION = 0;

	/**
	 * Constant to filter the selection of an asset. Used for sounds.
	 */
	public static final int ASSET_SOUND = 1;

	/**
	 * Link to the main controller.
	 */
	protected Controller controller;

	/**
	 * Contained block of effects.
	 */
	protected Effects effects;

	/**
	 * Constructor.
	 * 
	 * @param effects
	 *            Contained block of effects
	 */
	public EffectsController( Effects effects ) {
		this.effects = effects;
		controller = Controller.getInstance( );
	}

	/**
	 * Returns the number of effects contained.
	 * 
	 * @return Number of effects
	 */
	public int getEffectCount( ) {
		return effects.getEffects( ).size( );
	}

	/**
	 * Returns the info of the effect in the given position.
	 * 
	 * @param index
	 *            Position of the effect
	 * @return Information about the effect
	 */
	public String getEffectInfo( int index ) {
		if ( getEffectCount()>0 ){
			Effect effect = effects.getEffects( ).get( index );
			return getEffectInfo (effect);
		} else
			return null;
	}
	protected static String getEffectInfo( Effect effect ) {
		String effectInfo = null;

		switch( effect.getType( ) ) {
			case Effect.ACTIVATE:
				ActivateEffect activateEffect = (ActivateEffect) effect;
				effectInfo = TextConstants.getText( "Effect.ActivateInfo", activateEffect.getIdFlag( ) );
				break;
			case Effect.DEACTIVATE:
				DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
				effectInfo = TextConstants.getText( "Effect.DeactivateInfo", deactivateEffect.getIdFlag( ) );
				break;
			case Effect.CONSUME_OBJECT:
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				effectInfo = TextConstants.getText( "Effect.ConsumeObjectInfo", consumeObjectEffect.getIdTarget( ) );
				break;
			case Effect.GENERATE_OBJECT:
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				effectInfo = TextConstants.getText( "Effect.GenerateObjectInfo", generateObjectEffect.getIdTarget( ) );
				break;
			case Effect.CANCEL_ACTION:
				effectInfo = TextConstants.getText( "Effect.CancelActionInfo" );
				break;
			case Effect.SPEAK_PLAYER:
				SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
				effectInfo = TextConstants.getText( "Effect.SpeakPlayerInfo", speakPlayerEffect.getLine( ) );
				break;
			case Effect.SPEAK_CHAR:
				SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
				effectInfo = TextConstants.getText( "Effect.SpeakCharacterInfo", new String[] { speakCharEffect.getIdTarget( ), speakCharEffect.getLine( ) } );
				break;
			case Effect.TRIGGER_BOOK:
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerBookInfo", triggerBookEffect.getTargetBookId( ) );
				break;
			case Effect.PLAY_SOUND:
				PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
				effectInfo = TextConstants.getText( "Effect.PlaySoundInfo", playSoundEffect.getPath( ) );
				break;
			case Effect.PLAY_ANIMATION:
				PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
				effectInfo = TextConstants.getText( "Effect.PlayAnimationInfo", playAnimationEffect.getPath( ) );
				break;
			case Effect.MOVE_PLAYER:
				MovePlayerEffect movePlayerEffect = (MovePlayerEffect) effect;
				effectInfo = TextConstants.getText( "Effect.MovePlayerInfo", new String[] { String.valueOf( movePlayerEffect.getX( ) ), String.valueOf( movePlayerEffect.getY( ) ) } );
				break;
			case Effect.MOVE_NPC:
				MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
				effectInfo = TextConstants.getText( "Effect.MoveCharacterInfo", new String[] { moveNPCEffect.getIdTarget( ), String.valueOf( moveNPCEffect.getX( ) ), String.valueOf( moveNPCEffect.getY( ) ) } );
				break;
			case Effect.TRIGGER_CONVERSATION:
				TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerConversationInfo", triggerConversationEffect.getTargetConversationId( ) );
				break;
			case Effect.TRIGGER_CUTSCENE:
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerCutsceneInfo", triggerCutsceneEffect.getTargetCutsceneId( ) );
				break;
			case Effect.TRIGGER_SCENE:
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerSceneInfo", triggerSceneEffect.getTargetSceneId( ) );
				break;
			case Effect.TRIGGER_LAST_SCENE:
				effectInfo = TextConstants.getText( "Effect.TriggerLastSceneInfo");
				break;
			case Effect.RANDOM_EFFECT:
				RandomEffect randomEffect = (RandomEffect)effect;
				String posInfo = ""; String negInfo = ""; 
				if (randomEffect.getPositiveEffect( )!=null)
				posInfo = getEffectInfo (randomEffect.getPositiveEffect( ));
				negInfo = getEffectInfo (randomEffect.getNegativeEffect( ));
				effectInfo = TextConstants.getText( "Effect.RandomInfo", new String[]{Integer.toString( randomEffect.getProbability( ) ), Integer.toString( 100-randomEffect.getProbability( ) ), posInfo, negInfo});
				break;
		}

		return effectInfo;
	}

	/**
	 * Adds a new condition to the block.
	 * 
	 * @return True if an effect was added, false otherwise
	 */
	public boolean addEffect( ) {
		boolean effectAdded = false;

		// Create a list with the names of the effects (in the same order as the next)
		final String[] effectNames = { TextConstants.getText( "Effect.Activate" ), TextConstants.getText( "Effect.Deactivate" ), TextConstants.getText( "Effect.ConsumeObject" ), TextConstants.getText( "Effect.GenerateObject" ), TextConstants.getText( "Effect.CancelAction" ), TextConstants.getText( "Effect.SpeakPlayer" ), TextConstants.getText( "Effect.SpeakCharacter" ), TextConstants.getText( "Effect.TriggerBook" ), TextConstants.getText( "Effect.PlaySound" ), TextConstants.getText( "Effect.PlayAnimation" ), TextConstants.getText( "Effect.MovePlayer" ), TextConstants.getText( "Effect.MoveCharacter" ), TextConstants.getText( "Effect.TriggerConversation" ), TextConstants.getText( "Effect.TriggerCutscene" ), TextConstants.getText( "Effect.TriggerScene" ), TextConstants.getText( "Effect.TriggerLastScene" ) , TextConstants.getText( "Effect.RandomEffect" )};

		// Create a list with the types of the effects (in the same order as the previous)
		final int[] effectTypes = { Effect.ACTIVATE, Effect.DEACTIVATE, Effect.CONSUME_OBJECT, Effect.GENERATE_OBJECT, Effect.CANCEL_ACTION, Effect.SPEAK_PLAYER, Effect.SPEAK_CHAR, Effect.TRIGGER_BOOK, Effect.PLAY_SOUND, Effect.PLAY_ANIMATION, Effect.MOVE_PLAYER, Effect.MOVE_NPC, Effect.TRIGGER_CONVERSATION, Effect.TRIGGER_CUTSCENE, Effect.TRIGGER_SCENE, Effect.TRIGGER_LAST_SCENE, Effect.RANDOM_EFFECT };

		// Show a dialog to select the type of the effect
		String selectedValue = controller.showInputDialog( TextConstants.getText( "Effects.OperationAddEffect" ), TextConstants.getText( "Effects.SelectEffectType" ), effectNames );

		// If some effect was selected
		if( selectedValue != null && 
				!selectedValue.equals( TextConstants.getText( "Effect.RandomEffect" ) )) {
			// Store the type of the effect selected
			int selectedType = 0;
			for( int i = 0; i < effectNames.length; i++ )
				if( effectNames[i].equals( selectedValue ) )
					selectedType = effectTypes[i];

			HashMap<Integer, String> effectProperties = null;
			if (selectedType==Effect.MOVE_PLAYER && Controller.getInstance( ).isPlayTransparent( )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.EffectMovePlayerNotAllowed.Title" ), TextConstants.getText( "Error.EffectMovePlayerNotAllowed.Message" ) );
			}else{
				effectProperties = EffectDialog.showAddEffectDialog( this, selectedType );	
			}
			

			if( effectProperties != null ) {
				Effect newEffect = null;

				// Take all the values from the set
				String target = effectProperties.get( EFFECT_PROPERTY_TARGET );
				String path = effectProperties.get( EFFECT_PROPERTY_PATH );
				String text = effectProperties.get( EFFECT_PROPERTY_TEXT );

				int x = 0;
				if( effectProperties.containsKey( EFFECT_PROPERTY_X ) )
					x = Integer.parseInt( effectProperties.get( EFFECT_PROPERTY_X ) );

				int y = 0;
				if( effectProperties.containsKey( EFFECT_PROPERTY_Y ) )
					y = Integer.parseInt( effectProperties.get( EFFECT_PROPERTY_Y ) );

				boolean background = false;
				if( effectProperties.containsKey( EFFECT_PROPERTY_BACKGROUND ) )
					background = Boolean.parseBoolean( effectProperties.get( EFFECT_PROPERTY_BACKGROUND ) );

				switch( selectedType ) {
					case Effect.ACTIVATE:
						newEffect = new ActivateEffect( target );
						controller.getFlagSummary( ).addReference( target );
						break;
					case Effect.DEACTIVATE:
						newEffect = new DeactivateEffect( target );
						controller.getFlagSummary( ).addReference( target );
						break;
					case Effect.CONSUME_OBJECT:
						newEffect = new ConsumeObjectEffect( target );
						break;
					case Effect.GENERATE_OBJECT:
						newEffect = new GenerateObjectEffect( target );
						break;
					case Effect.CANCEL_ACTION:
						newEffect = new CancelActionEffect( );
						break;
					case Effect.TRIGGER_LAST_SCENE:
						newEffect = new TriggerLastSceneEffect( );
						break;
					case Effect.SPEAK_PLAYER:
						newEffect = new SpeakPlayerEffect( text );
						break;
					case Effect.SPEAK_CHAR:
						newEffect = new SpeakCharEffect( target, text );
						break;
					case Effect.TRIGGER_BOOK:
						newEffect = new TriggerBookEffect( target );
						break;
					case Effect.PLAY_SOUND:
						newEffect = new PlaySoundEffect( background, path );
						break;
					case Effect.PLAY_ANIMATION:
						newEffect = new PlayAnimationEffect( path, x, y );
						break;
					case Effect.MOVE_PLAYER:
						newEffect = new MovePlayerEffect( x, y );
						break;
					case Effect.MOVE_NPC:
						newEffect = new MoveNPCEffect( target, x, y );
						break;
					case Effect.TRIGGER_CONVERSATION:
						newEffect = new TriggerConversationEffect( target );
						break;
					case Effect.TRIGGER_CUTSCENE:
						newEffect = new TriggerCutsceneEffect( target );
						break;
					case Effect.TRIGGER_SCENE:
						newEffect = new TriggerSceneEffect( target, x, y );
						break;
				}

				effects.add( newEffect );
				controller.dataModified( );
				effectAdded = true;
			}
		} else if (selectedValue != null){
			RandomEffect randomEffect = new RandomEffect(50);
			SingleEffectController posController = new SingleEffectController();
			SingleEffectController negController = new SingleEffectController();
			HashMap<Integer, String> effectProperties = EffectDialog.showEditRandomEffectDialog( 50, posController, negController );
			if (effectProperties.containsKey( EffectsController.EFFECT_PROPERTY_PROBABILITY )){
				randomEffect.setProbability( Integer.parseInt( effectProperties.get( EFFECT_PROPERTY_PROBABILITY ) ) );
			}
			if (posController.getEffect( )!=null)
				randomEffect.setPositiveEffect( posController.getEffect( ) );
			
			if (negController.getEffect( )!=null)
				randomEffect.setNegativeEffect( negController.getEffect( ) );
			
			effects.add( randomEffect );
			controller.dataModified( );
			effectAdded = true;
		}

		return effectAdded;
	}

	/**
	 * Deletes the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect
	 */
	public void deleteEffect( int index ) {
		Effect deletedEffect = effects.getEffects( ).remove( index );
		controller.dataModified( );

		if( deletedEffect.getType( ) == Effect.ACTIVATE ) {
			ActivateEffect activateEffect = (ActivateEffect) deletedEffect;
			controller.getFlagSummary( ).deleteReference( activateEffect.getIdFlag( ) );
		}

		else if( deletedEffect.getType( ) == Effect.DEACTIVATE ) {
			DeactivateEffect deactivateEffect = (DeactivateEffect) deletedEffect;
			controller.getFlagSummary( ).deleteReference( deactivateEffect.getIdFlag( ) );
		}
		
		else if( deletedEffect.getType( ) == Effect.RANDOM_EFFECT ) {
			RandomEffect randomEffect = (RandomEffect) deletedEffect;
			if (randomEffect.getNegativeEffect( )!=null){
				if ( randomEffect.getNegativeEffect( ).getType( ) == Effect.ACTIVATE ){
					ActivateEffect activateEffect = (ActivateEffect) randomEffect.getNegativeEffect( );
					controller.getFlagSummary( ).deleteReference( activateEffect.getIdFlag( ) );
				} else if ( randomEffect.getNegativeEffect( ).getType( ) == Effect.DEACTIVATE ){
					DeactivateEffect deactivateEffect = (DeactivateEffect) randomEffect.getNegativeEffect( );
					controller.getFlagSummary( ).deleteReference( deactivateEffect.getIdFlag( ) );
				}
			}
			
			if (randomEffect.getPositiveEffect( )!=null){
				if ( randomEffect.getPositiveEffect( ).getType( ) == Effect.ACTIVATE ){
					ActivateEffect activateEffect = (ActivateEffect) randomEffect.getPositiveEffect( );
					controller.getFlagSummary( ).deleteReference( activateEffect.getIdFlag( ) );
				} else if ( randomEffect.getPositiveEffect( ).getType( ) == Effect.DEACTIVATE ){
					DeactivateEffect deactivateEffect = (DeactivateEffect) randomEffect.getPositiveEffect( );
					controller.getFlagSummary( ).deleteReference( deactivateEffect.getIdFlag( ) );
				}
			}

		}
	}

	/**
	 * Moves up the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect to move
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean moveUpEffect( int index ) {
		boolean effectMoved = false;

		if( index > 0 ) {
			effects.getEffects( ).add( index - 1, effects.getEffects( ).remove( index ) );
			controller.dataModified( );
			effectMoved = true;
		}

		return effectMoved;
	}

	/**
	 * Moves down the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect to move
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean moveDownEffect( int index ) {
		boolean effectMoved = false;

		if( index < effects.getEffects( ).size( ) - 1 ) {
			effects.getEffects( ).add( index + 1, effects.getEffects( ).remove( index ) );
			controller.dataModified( );
			effectMoved = true;
		}

		return effectMoved;
	}

	/**
	 * Edits the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean editEffect( int index ) {
		boolean effectEdited = false;

		// Take the effect and its type
		Effect effect = effects.getEffects( ).get( index );
		int effectType = effect.getType( );

		// Create the hashmap to store the current values
		HashMap<Integer, String> currentValues = new HashMap<Integer, String>( );

		switch( effectType ) {
			case Effect.ACTIVATE:
				ActivateEffect activateEffect = (ActivateEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, activateEffect.getIdFlag( ) );
				break;
			case Effect.DEACTIVATE:
				DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, deactivateEffect.getIdFlag( ) );
				break;
			case Effect.CONSUME_OBJECT:
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, consumeObjectEffect.getIdTarget( ) );
				break;
			case Effect.GENERATE_OBJECT:
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, generateObjectEffect.getIdTarget( ) );
				break;
			case Effect.SPEAK_PLAYER:
				SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TEXT, speakPlayerEffect.getLine( ) );
				break;
			case Effect.SPEAK_CHAR:
				SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, speakCharEffect.getIdTarget( ) );
				currentValues.put( EFFECT_PROPERTY_TEXT, speakCharEffect.getLine( ) );
				break;
			case Effect.TRIGGER_BOOK:
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerBookEffect.getTargetBookId( ) );
				break;
			case Effect.PLAY_SOUND:
				PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
				currentValues.put( EFFECT_PROPERTY_PATH, playSoundEffect.getPath( ) );
				currentValues.put( EFFECT_PROPERTY_BACKGROUND, String.valueOf( playSoundEffect.isBackground( ) ) );
				break;
			case Effect.PLAY_ANIMATION:
				PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
				currentValues.put( EFFECT_PROPERTY_PATH, playAnimationEffect.getPath( ) );
				currentValues.put( EFFECT_PROPERTY_X, String.valueOf( playAnimationEffect.getX( ) ) );
				currentValues.put( EFFECT_PROPERTY_Y, String.valueOf( playAnimationEffect.getY( ) ) );
				break;
			case Effect.MOVE_PLAYER:
				MovePlayerEffect movePlayerEffect = (MovePlayerEffect) effect;
				currentValues.put( EFFECT_PROPERTY_X, String.valueOf( movePlayerEffect.getX( ) ) );
				currentValues.put( EFFECT_PROPERTY_Y, String.valueOf( movePlayerEffect.getY( ) ) );
				break;
			case Effect.MOVE_NPC:
				MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, moveNPCEffect.getIdTarget( ) );
				currentValues.put( EFFECT_PROPERTY_X, String.valueOf( moveNPCEffect.getX( ) ) );
				currentValues.put( EFFECT_PROPERTY_Y, String.valueOf( moveNPCEffect.getY( ) ) );
				break;
			case Effect.TRIGGER_CONVERSATION:
				TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerConversationEffect.getTargetConversationId( ) );
				break;
			case Effect.TRIGGER_CUTSCENE:
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerCutsceneEffect.getTargetCutsceneId( ) );
				break;
			case Effect.TRIGGER_SCENE:
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerSceneEffect.getTargetSceneId( ) );
				currentValues.put( EFFECT_PROPERTY_X, String.valueOf( triggerSceneEffect.getX( ) ) );
				currentValues.put( EFFECT_PROPERTY_Y, String.valueOf( triggerSceneEffect.getY( ) ) );
				break;
		}

		// Show the editing dialog
		HashMap<Integer, String> newProperties = null; 
		SingleEffectController pos = null;
		SingleEffectController neg = null;
		
		if ( effectType!=Effect.RANDOM_EFFECT)
			newProperties = EffectDialog.showEditEffectDialog( this, effectType, currentValues );
		else {
			RandomEffect randomEffect = (RandomEffect)effect;
			pos = new SingleEffectController ( randomEffect.getPositiveEffect( ) );
			neg = new SingleEffectController ( randomEffect.getNegativeEffect( ) );
			
			newProperties = EffectDialog.showEditRandomEffectDialog( randomEffect.getProbability( ), 
					pos,neg );
		}

		// If a change has been made
		if( newProperties != null ) {

			controller.dataModified( );
			effectEdited = true;

			switch( effectType ) {
				case Effect.ACTIVATE:
					ActivateEffect activateEffect = (ActivateEffect) effect;
					activateEffect.setIdFlag( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.DEACTIVATE:
					DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
					deactivateEffect.setIdFlag( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					Controller.getInstance( ).updateFlagSummary( );
					break;
				case Effect.CONSUME_OBJECT:
					ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
					consumeObjectEffect.setIdTarget( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.GENERATE_OBJECT:
					GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
					generateObjectEffect.setIdTarget( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.SPEAK_PLAYER:
					SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
					speakPlayerEffect.setLine( newProperties.get( EFFECT_PROPERTY_TEXT ) );
					break;
				case Effect.SPEAK_CHAR:
					SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
					speakCharEffect.setIdTarget( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					speakCharEffect.setLine( newProperties.get( EFFECT_PROPERTY_TEXT ) );
					break;
				case Effect.TRIGGER_BOOK:
					TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
					triggerBookEffect.setTargetBookId( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.PLAY_SOUND:
					PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
					playSoundEffect.setPath( newProperties.get( EFFECT_PROPERTY_PATH ) );
					playSoundEffect.setBackground( Boolean.parseBoolean( newProperties.get( EFFECT_PROPERTY_BACKGROUND ) ) );
					break;
				case Effect.PLAY_ANIMATION:
					PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
					playAnimationEffect.setPath( newProperties.get( EFFECT_PROPERTY_PATH ) );
					playAnimationEffect.setDestiny( Integer.parseInt( newProperties.get( EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.MOVE_PLAYER:
					MovePlayerEffect movePlayerEffect = (MovePlayerEffect) effect;
					movePlayerEffect.setDestiny( Integer.parseInt( newProperties.get( EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.MOVE_NPC:
					MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
					moveNPCEffect.setIdTarget( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					moveNPCEffect.setDestiny( Integer.parseInt( newProperties.get( EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.TRIGGER_CONVERSATION:
					TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
					triggerConversationEffect.setTargetConversationId( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.TRIGGER_CUTSCENE:
					TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
					triggerCutsceneEffect.setTargetCutsceneId( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					break;
				case Effect.TRIGGER_SCENE:
					TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
					triggerSceneEffect.setTargetSceneId( newProperties.get( EFFECT_PROPERTY_TARGET ) );
					triggerSceneEffect.setPosition( Integer.parseInt( newProperties.get( EFFECT_PROPERTY_X ) ), Integer.parseInt( newProperties.get( EFFECT_PROPERTY_Y ) ) );
					break;
				case Effect.RANDOM_EFFECT:
					RandomEffect randomEffect = (RandomEffect) effect;
					randomEffect.setProbability( Integer.parseInt( newProperties.get( EFFECT_PROPERTY_PROBABILITY )  ) );
					randomEffect.setPositiveEffect( pos.getEffect( ) );
					randomEffect.setNegativeEffect( neg.getEffect( ) );
					break;
			}
		}

		return effectEdited;
	}

	/**
	 * This method allows the user to select an asset to include it as an animation or a sound to be played in the
	 * effects block.
	 * 
	 * @param assetType
	 *            Type of the asset
	 * @return The path of the asset if it was selected, or null if no asset was selected
	 */
	public String selectAsset( int assetType ) {
		String selectedAsset = null;
		String assetPath = null;
		
		// Show a dialog to select a file, with the fitting file filter
		int assetCategory = -1;
		if( assetType == ASSET_ANIMATION )
			assetCategory = AssetsController.CATEGORY_ANIMATION;
		else if( assetType == ASSET_SOUND )
			assetCategory = AssetsController.CATEGORY_AUDIO;

		AssetChooser chooser = AssetsController.getAssetChooser( assetCategory, AssetsController.FILTER_NONE );
		int option = chooser.showAssetChooser( controller.peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( assetCategory, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Get the list of assets from the ZIP file
			String[] assetFilenames = AssetsController.getAssetFilenames( assetCategory );
			String[] assetPaths = AssetsController.getAssetsList( assetCategory );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data in the resources block (removing the suffix if necessary)
			if( assetCategory == AssetsController.CATEGORY_ANIMATION ){
				assetPath = AssetsController.removeSuffix( assetPaths[assetIndex] ) ;
			}
			else if( assetType == ASSET_SOUND )
				assetPath = assetPaths[assetIndex];
			controller.dataModified( );
		}
		return assetPath;
	}

	/**
	 * Updates the given flag summary, adding the flag references contained in the given effects.
	 * 
	 * @param flagSummary
	 *            Flag summary to update
	 * @param effects
	 *            Set of effects to search in
	 */
	public static void updateFlagSummary( FlagSummary flagSummary, Effects effects ) {
		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			// If the effect is an activate or deactivate effect, add the reference
			if( type == Effect.ACTIVATE )
				flagSummary.addReference( ( (ActivateEffect) effect ).getIdFlag( ) );
			else if( type == Effect.DEACTIVATE )
				flagSummary.addReference( ( (DeactivateEffect) effect ).getIdFlag( ) );
			
			else if( type == Effect.RANDOM_EFFECT ) {
				RandomEffect randomEffect = (RandomEffect) effect;
				if (randomEffect.getNegativeEffect( )!=null){
					if ( randomEffect.getNegativeEffect( ).getType( ) == Effect.ACTIVATE ){
						ActivateEffect activateEffect = (ActivateEffect) randomEffect.getNegativeEffect( );
						flagSummary.addReference( activateEffect.getIdFlag( ) );
					} else if ( randomEffect.getNegativeEffect( ).getType( ) == Effect.DEACTIVATE ){
						DeactivateEffect deactivateEffect = (DeactivateEffect) randomEffect.getNegativeEffect( );
						flagSummary.addReference( deactivateEffect.getIdFlag( ) );
					}
				}
				
				if (randomEffect.getPositiveEffect( )!=null){
					if ( randomEffect.getPositiveEffect( ).getType( ) == Effect.ACTIVATE ){
						ActivateEffect activateEffect = (ActivateEffect) randomEffect.getPositiveEffect( );
						flagSummary.addReference( activateEffect.getIdFlag( ) );
					} else if ( randomEffect.getPositiveEffect( ).getType( ) == Effect.DEACTIVATE ){
						DeactivateEffect deactivateEffect = (DeactivateEffect) randomEffect.getPositiveEffect( );
						flagSummary.addReference( deactivateEffect.getIdFlag( ) );
					}
				}

			}

		}
	}

	/**
	 * Returns if the effects block is valid or not.
	 * 
	 * @param currentPath
	 *            String with the path to the given element (including the element)
	 * @param incidences
	 *            List to store the incidences in the elements. Null if no incidences track must be stored
	 * @param effects
	 *            Block of effects
	 * @return True if the data structure pending from the element is valid, false otherwise
	 */
	public static boolean isValid( String currentPath, List<String> incidences, Effects effects ) {
		boolean valid = true;

		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			// If the effect is an animation without asset, set as invalid
			if( type == Effect.PLAY_ANIMATION && ( (PlayAnimationEffect) effect ).getPath( ).length( ) == 0 ) {
				valid = false;

				// Store the incidence
				if( incidences != null )
					incidences.add( currentPath + " >> " + TextConstants.getText( "Operation.AdventureConsistencyErrorPlayAnimation" ) );
			}

			// If the effect is a sound without asset, set as invalid
			else if( type == Effect.PLAY_SOUND && ( (PlaySoundEffect) effect ).getPath( ).length( ) == 0 ) {
				valid = false;

				// Store the incidence
				if( incidences != null )
					incidences.add( currentPath + " >> " + TextConstants.getText( "Operation.AdventureConsistencyErrorPlaySound" ) );
			}
			
			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				EffectsController.isValid( currentPath+ " >> " + TextConstants.getText( "Effect.RandomEffect" ), incidences, e );
				
			}
		}

		return valid;
	}

	/**
	 * Returns the count of references to the given asset path in the block of effects.
	 * 
	 * @param assetPath
	 *            Path to the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 * @param effects
	 *            Block of effects
	 * @return Number of references to the asset path in the block of effects
	 */
	public static int countAssetReferences( String assetPath, Effects effects ) {
		int count = 0;

		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			// If the asset appears in an animation or sound, add it
			if( ( type == Effect.PLAY_ANIMATION && ( (PlayAnimationEffect) effect ).getPath( ).equals( assetPath ) ) || ( type == Effect.PLAY_SOUND && ( (PlaySoundEffect) effect ).getPath( ).equals( assetPath ) ) )
				count++;
			
			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				count+=EffectsController.countAssetReferences( assetPath, e );
				
			}

		}

		return count;
	}
	
	/**
	 * Produces a list with all the referenced assets in the data control. This method works recursively.
	 * 
	 * @param assetPaths
	 * 				The list with all the asset references. The list will only contain each asset path once, even if it is referenced more than once.
	 * @param assetTypes
	 * 				The types of the assets contained in assetPaths.
	 */
	public static void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes, Effects effects ) {

		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			int assetType = -1;
			String assetPath = null;
			// If the asset appears in an animation or sound, add it
			if( type == Effect.PLAY_ANIMATION ) {
				PlayAnimationEffect animationEffect = (PlayAnimationEffect)effect;
				assetPath = animationEffect.getPath( );
				assetType = AssetsController.CATEGORY_ANIMATION;
				//( (PlayAnimationEffect) effect ).getPath( ).equals( assetPath ) ) || ( type == Effect.PLAY_SOUND && ( (PlaySoundEffect) effect ).getPath( ).equals( assetPath ) ) )
			} else if ( type == Effect.PLAY_SOUND ){
				PlaySoundEffect soundEffect = (PlaySoundEffect)effect;
				assetPath = soundEffect.getPath( );
				assetType = AssetsController.CATEGORY_AUDIO;
			} 			
			
			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				
				EffectsController.getAssetReferences( assetPaths, assetTypes, e );
			}

			
			if (assetPath!=null){
				// Search the list. If the asset is not in it, add it
				boolean add = true;
				for (String asset: assetPaths){
					if (asset.equals( assetPath )){
						add = false;
						break;
					}
				}
				if (add){
					int last = assetPaths.size( );
					assetPaths.add( last, assetPath );
					assetTypes.add( last, assetType );
				}
			}
		}
	}


	/**
	 * Deletes all the references to the asset path in the given block of effects.
	 * 
	 * @param assetPath
	 *            Path to the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 * @param effects
	 *            Block of effects
	 */
	public static void deleteAssetReferences( String assetPath, Effects effects ) {
		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			// If the effect is a play animation or play sound effect
			if( type == Effect.PLAY_ANIMATION ) {
				// If the asset is the same, delete it
				PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
				if( playAnimationEffect.getPath( ).equals( assetPath ) ) {
					playAnimationEffect.setPath( "" );
					Controller.getInstance( ).dataModified( );
				}
			} else if( type == Effect.PLAY_SOUND ) {
				// If the asset is the same, delete it
				PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
				if( playSoundEffect.getPath( ).equals( assetPath ) ) {
					playSoundEffect.setPath( "" );
					Controller.getInstance( ).dataModified( );
				}
			}
			
			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				EffectsController.deleteAssetReferences( assetPath, e );
				
			}

		}
	}

	/**
	 * Returns the count of references to the given identifier in the block of effects.
	 * 
	 * @param id
	 *            Identifier to search
	 * @param effects
	 *            Block of effects
	 * @return Number of references to the identifier in the block of effects
	 */
	public static int countIdentifierReferences( String id, Effects effects ) {
		int count = 0;

		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			// If the identifier appears in some effect with references, increase the counter
			if( ( type == Effect.CONSUME_OBJECT && ( (ConsumeObjectEffect) effect ).getIdTarget( ).equals( id ) ) || ( type == Effect.GENERATE_OBJECT && ( (GenerateObjectEffect) effect ).getIdTarget( ).equals( id ) ) || ( type == Effect.SPEAK_CHAR && ( (SpeakCharEffect) effect ).getIdTarget( ).equals( id ) ) || ( type == Effect.TRIGGER_BOOK && ( (TriggerBookEffect) effect ).getTargetBookId( ).equals( id ) ) || ( type == Effect.MOVE_NPC && ( (MoveNPCEffect) effect ).getIdTarget( ).equals( id ) ) || ( type == Effect.TRIGGER_CONVERSATION && ( (TriggerConversationEffect) effect ).getTargetConversationId( ).equals( id ) ) || ( type == Effect.TRIGGER_SCENE && ( (TriggerSceneEffect) effect ).getTargetSceneId( ).equals( id ) ) || ( type == Effect.TRIGGER_CUTSCENE && ( (TriggerCutsceneEffect) effect ).getTargetCutsceneId( ).equals( id ) ) )
				count++;
			
			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				EffectsController.countIdentifierReferences( id, e );
				
			}

		}

		return count;
	}

	/**
	 * Replaces all the references to the given identifier to references to a new one in the block of effects.
	 * 
	 * @param oldId
	 *            Identifier which references must be replaced
	 * @param newId
	 *            New identifier to replace the old references
	 * @param effects
	 *            Block of effects
	 */
	public static void replaceIdentifierReferences( String oldId, String newId, Effects effects ) {

		// For each effect
		for( Effect effect : effects.getEffects( ) ) {
			int type = effect.getType( );

			// Check the type and the identifier reference, if the identifier matches replace it with the new one
			if( type == Effect.CONSUME_OBJECT ) {
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				if( consumeObjectEffect.getIdTarget( ).equals( oldId ) )
					consumeObjectEffect.setIdTarget( newId );
			} else if( type == Effect.GENERATE_OBJECT ) {
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				if( generateObjectEffect.getIdTarget( ).equals( oldId ) )
					generateObjectEffect.setIdTarget( newId );
			} else if( type == Effect.SPEAK_CHAR ) {
				SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
				if( speakCharEffect.getIdTarget( ).equals( oldId ) )
					speakCharEffect.setIdTarget( newId );
			} else if( type == Effect.TRIGGER_BOOK ) {
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				if( triggerBookEffect.getTargetBookId( ).equals( oldId ) )
					triggerBookEffect.setTargetBookId( newId );
			} else if( type == Effect.MOVE_NPC ) {
				MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
				if( moveNPCEffect.getIdTarget( ).equals( oldId ) )
					moveNPCEffect.setIdTarget( newId );
			} else if( type == Effect.TRIGGER_CONVERSATION ) {
				TriggerConversationEffect trigerConversationEffect = (TriggerConversationEffect) effect;
				if( trigerConversationEffect.getTargetConversationId( ).equals( oldId ) )
					trigerConversationEffect.setTargetConversationId( newId );
			} else if( type == Effect.TRIGGER_SCENE ) {
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				if( triggerSceneEffect.getTargetSceneId( ).equals( oldId ) )
					triggerSceneEffect.setTargetSceneId( newId );
			} else if( type == Effect.TRIGGER_CUTSCENE ) {
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				if( triggerCutsceneEffect.getTargetCutsceneId( ).equals( oldId ) )
					triggerCutsceneEffect.setTargetCutsceneId( newId );
			} 			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				EffectsController.replaceIdentifierReferences( oldId, newId, e );
				
			}

		}
	}

	/**
	 * Deletes all the references to the given identifier in the block of effects. All the effects in the block that
	 * reference to the given identifier will be deleted.
	 * 
	 * @param id
	 *            Identifier which references must be deleted
	 * @param effects
	 *            Block of effects
	 */
	public static void deleteIdentifierReferences( String id, Effects effects ) {
		int i = 0;

		// For earch effect
		while( i < effects.getEffects( ).size( ) ) {
			// Get the effect and the type
			boolean deleteEffect = false;
			Effect effect = effects.getEffects( ).get( i );
			int type = effect.getType( );

			// Check if the effect must be deleted
			if( type == Effect.CONSUME_OBJECT )
				deleteEffect = ( (ConsumeObjectEffect) effect ).getIdTarget( ).equals( id );
			else if( type == Effect.GENERATE_OBJECT )
				deleteEffect = ( (GenerateObjectEffect) effect ).getIdTarget( ).equals( id );
			else if( type == Effect.SPEAK_CHAR )
				deleteEffect = ( (SpeakCharEffect) effect ).getIdTarget( ).equals( id );
			else if( type == Effect.TRIGGER_BOOK )
				deleteEffect = ( (TriggerBookEffect) effect ).getTargetBookId( ).equals( id );
			else if( type == Effect.MOVE_NPC )
				deleteEffect = ( (MoveNPCEffect) effect ).getIdTarget( ).equals( id );
			else if( type == Effect.TRIGGER_CONVERSATION )
				deleteEffect = ( (TriggerConversationEffect) effect ).getTargetConversationId( ).equals( id );
			else if( type == Effect.TRIGGER_SCENE )
				deleteEffect = ( (TriggerSceneEffect) effect ).getTargetSceneId( ).equals( id );
			else if( type == Effect.TRIGGER_CUTSCENE )
				deleteEffect = ( (TriggerCutsceneEffect) effect ).getTargetCutsceneId( ).equals( id );
			// If random effect
			else if ( type == Effect.RANDOM_EFFECT){

				RandomEffect randomEffect = (RandomEffect) effect;
				Effects e = new Effects(); 				
				if (randomEffect.getPositiveEffect( )!=null)
					e.add( randomEffect.getPositiveEffect( ) );
				if (randomEffect.getNegativeEffect( )!=null)
					e.add( randomEffect.getNegativeEffect( ) );
				EffectsController.deleteIdentifierReferences( id, e );
			}


			// Delete the effect, or increase the counter
			if( deleteEffect )
				effects.getEffects( ).remove( i );
			else
				i++;
		}
	}
}