package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.CancelActionEffect;
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
import es.eucm.eadventure.common.data.chapter.effects.TriggerLastSceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.WaitTimeEffect;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectResourceTool;
import es.eucm.eadventure.editor.control.tools.general.effects.AddEffectTool;
import es.eucm.eadventure.editor.control.tools.general.effects.DeleteEffectTool;
import es.eucm.eadventure.editor.control.tools.general.effects.MoveEffectInTableTool;
import es.eucm.eadventure.editor.control.tools.general.effects.ReplaceEffectTool;
import es.eucm.eadventure.editor.control.tools.generic.MoveObjectTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.editdialogs.SelectEffectsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.EffectDialog;

/**
 * This class is the controller of the effects blocks. It manages the insertion and modification of the effects lists.
 * 
 * @author Javier Torrente
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
	 * Constant for effect property. Refers to "Value" flag.
	 */
	public static final int EFFECT_PROPERTY_VALUE = 7;
	
	/**
	 * Constant for effect property. Refers to time value for WaitTimeEffect.
	 */
	public static final int EFFECT_PROPERTY_TIME= 8;
	
	/**
	 * Constant for effect property. Refers to text front color .
	 */
	public static final int EFFECT_PROPERTY_FRONT_COLOR= 9;
	
	/**
	 * Constant for effect property. Refers to text border color.
	 */
	public static final int EFFECT_PROPERTY_BORDER_COLOR= 10;
	
	/**
	 * Constant for effect property. Refers to type (ACTIVATE | DEACTIVATE | MOVE-NPC...).
	 */
	public static final int EFFECT_PROPERTY_TYPE= 11;
	
	/**
	 * Constant for effect property. Refers to first effect (RandomEffect).
	 */
	public static final int EFFECT_PROPERTY_FIRST_EFFECT= 12;
	
	/**
	 * Constant for effect property. Refers to second effect (RandomEffect).
	 */
	public static final int EFFECT_PROPERTY_SECOND_EFFECT= 13;
	

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
	
	protected List<ConditionsController> conditionsList; 
	
	protected boolean waitingForEffectSelection = false;

	/**
	 * Constructor.
	 * 
	 * @param effects
	 *            Contained block of effects
	 */
	public EffectsController( Effects effects ) {
		this.effects = effects;
		controller = Controller.getInstance( );
		conditionsList=new ArrayList<ConditionsController>();
		// create the list of effects controllers
		for (AbstractEffect effect:effects.getEffects()){
			conditionsList.add(new ConditionsController(effect.getConditions(), Controller.EFFECT, getEffectInfo( effect)));
		    }
	}
	
	/**
	 * Return the conditions controller in the given position.
	 * @param index
	 * @return
	 */
	public ConditionsController getConditionController(int index){
	    return conditionsList.get(index);
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
			AbstractEffect effect = effects.getEffects( ).get( index );
			return getEffectInfo (effect);
		} else
			return null;
	}
	
	/**
	 * Returns the icon of the effect in the given position.
	 * 
	 * @param index
	 *            Position of the effect
	 * @return Icon of the effect
	 */
	public Icon getEffectIcon( int index ) {
		Icon icon = null;
		if (index>=0 && index<effects.getEffects().size()){
			AbstractEffect effect = effects.getEffects().get( index );
			switch( effect.getType( ) ) {
			case Effect.ACTIVATE:
				icon = new ImageIcon("img/icons/effects/16x16/activate.png");
				break;
			case Effect.DEACTIVATE:
				icon = new ImageIcon("img/icons/effects/16x16/deactivate.png");
				break;
			case Effect.SET_VALUE:
				icon = new ImageIcon("img/icons/effects/16x16/set-value.png");				
				break;				
			case Effect.INCREMENT_VAR:
				icon = new ImageIcon("img/icons/effects/16x16/increment.png");
				break;
			case Effect.DECREMENT_VAR:
				icon = new ImageIcon("img/icons/effects/16x16/decrement.png");
				break;
			case Effect.MACRO_REF:
				icon = new ImageIcon("img/icons/effects/16x16/macro.png");
				break;				
			case Effect.CONSUME_OBJECT:
				icon = new ImageIcon("img/icons/effects/16x16/consume-object.png");
				break;
			case Effect.GENERATE_OBJECT:
				icon = new ImageIcon("img/icons/effects/16x16/generate-object.png");
				break;
			case Effect.CANCEL_ACTION:
				icon = new ImageIcon("img/icons/effects/16x16/cancel-action.png");
				break;
			case Effect.SPEAK_PLAYER:
				icon = new ImageIcon("img/icons/effects/16x16/speak-player.png");
				break;
			case Effect.SPEAK_CHAR:
				icon = new ImageIcon("img/icons/effects/16x16/speak-npc.png");
				break;
			case Effect.TRIGGER_BOOK:
				icon = new ImageIcon("img/icons/effects/16x16/trigger-book.png");
				break;
			case Effect.PLAY_SOUND:
				icon = new ImageIcon("img/icons/effects/16x16/play-sound.png");
				break;
			case Effect.PLAY_ANIMATION:
				icon = new ImageIcon("img/icons/effects/16x16/play-animation.png");
				break;
			case Effect.MOVE_PLAYER:
				icon = new ImageIcon("img/icons/effects/16x16/move-player.png");
				break;
			case Effect.MOVE_NPC:
				icon = new ImageIcon("img/icons/effects/16x16/move-npc.png");
				break;
			case Effect.TRIGGER_CONVERSATION:
				icon = new ImageIcon("img/icons/effects/16x16/trigger-conversation.png");
				break;
			case Effect.TRIGGER_CUTSCENE:
				icon = new ImageIcon("img/icons/effects/16x16/trigger-cutscene.png");
				break;
			case Effect.TRIGGER_SCENE:
				icon = new ImageIcon("img/icons/effects/16x16/trigger-scene.png");
				break;
			case Effect.TRIGGER_LAST_SCENE:
				icon = new ImageIcon("img/icons/effects/16x16/trigger-last-scene.png");
				break;
			case Effect.RANDOM_EFFECT:
				icon = new ImageIcon("img/icons/effects/16x16/random-effect.png");
				break;
			case Effect.WAIT_TIME:
				icon = new ImageIcon("img/icons/effects/16x16/wait.png");
			    break;
			case Effect.SHOW_TEXT:
				icon = new ImageIcon("img/icons/effects/16x16/show-text.png");
			    break;
			}
		
		}
		return icon;
	}
	
	protected static String getEffectInfo( AbstractEffect effect ) {
		String effectInfo = null;

		switch( effect.getType( ) ) {
			case Effect.ACTIVATE:
				ActivateEffect activateEffect = (ActivateEffect) effect;
				effectInfo = TextConstants.getText( "Effect.ActivateInfo", activateEffect.getTargetId( ) );
				break;
			case Effect.DEACTIVATE:
				DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
				effectInfo = TextConstants.getText( "Effect.DeactivateInfo", deactivateEffect.getTargetId( ) );
				break;
			case Effect.SET_VALUE:
				SetValueEffect setValueEffect = (SetValueEffect) effect;
				effectInfo = TextConstants.getText( "Effect.SetValueInfo", new String[]{setValueEffect.getTargetId( ), Integer.toString( setValueEffect.getValue() )} );
				break;				
			case Effect.INCREMENT_VAR:
				IncrementVarEffect incrementEffect = (IncrementVarEffect) effect;
				effectInfo = TextConstants.getText( "Effect.IncrementVarInfo", new String[]{incrementEffect.getTargetId( ), Integer.toString( incrementEffect.getIncrement() )} );
				break;
			case Effect.DECREMENT_VAR:
				DecrementVarEffect decrementEffect = (DecrementVarEffect) effect;
				effectInfo = TextConstants.getText( "Effect.DecrementVarInfo", new String[]{decrementEffect.getTargetId( ), Integer.toString( decrementEffect.getDecrement() )} );
				break;
			case Effect.MACRO_REF:
				MacroReferenceEffect macroReferenceEffect = (MacroReferenceEffect) effect;
				effectInfo = TextConstants.getText( "Effect.MacroRefInfo", macroReferenceEffect.getTargetId( ) );
				break;				
			case Effect.CONSUME_OBJECT:
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				effectInfo = TextConstants.getText( "Effect.ConsumeObjectInfo", consumeObjectEffect.getTargetId( ) );
				break;
			case Effect.GENERATE_OBJECT:
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				effectInfo = TextConstants.getText( "Effect.GenerateObjectInfo", generateObjectEffect.getTargetId( ) );
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
				effectInfo = TextConstants.getText( "Effect.SpeakCharacterInfo", new String[] { speakCharEffect.getTargetId( ), speakCharEffect.getLine( ) } );
				break;
			case Effect.TRIGGER_BOOK:
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerBookInfo", triggerBookEffect.getTargetId( ) );
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
				effectInfo = TextConstants.getText( "Effect.MoveCharacterInfo", new String[] { moveNPCEffect.getTargetId( ), String.valueOf( moveNPCEffect.getX( ) ), String.valueOf( moveNPCEffect.getY( ) ) } );
				break;
			case Effect.TRIGGER_CONVERSATION:
				TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerConversationInfo", triggerConversationEffect.getTargetId( ) );
				break;
			case Effect.TRIGGER_CUTSCENE:
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerCutsceneInfo", triggerCutsceneEffect.getTargetId( ) );
				break;
			case Effect.TRIGGER_SCENE:
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				effectInfo = TextConstants.getText( "Effect.TriggerSceneInfo", triggerSceneEffect.getTargetId( ) );
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
			case Effect.WAIT_TIME:
			    	WaitTimeEffect waitTimeEffect = (WaitTimeEffect)effect;
			    	effectInfo = TextConstants.getText("Effect.WaitTimeInfo", Integer.toString(waitTimeEffect.getTime()));
			    	break;
			case Effect.SHOW_TEXT:
			    	ShowTextEffect showTextInfo = (ShowTextEffect)effect;
			    	effectInfo = TextConstants.getText("Effect.ShowTextInfo",new String[] {showTextInfo.getText(),Integer.toString(showTextInfo.getX()),Integer.toString(showTextInfo.getY())});
			    	break;
		}

		return effectInfo;
	}
	
	public List<AbstractEffect> getEffects() {
		return effects.getEffects();
	}

	/**
	 * Starts Adding a new condition to the block.
	 * 
	 * @return True if an effect was added, false otherwise
	 */
	public boolean addEffect( ) {
		boolean effectAdded = false;

		// Create a list with the names of the effects (in the same order as the next)
		/*final String[] effectNames = { TextConstants.getText( "Effect.Activate" ), TextConstants.getText( "Effect.Deactivate" ),
				TextConstants.getText( "Effect.SetValue" ), TextConstants.getText( "Effect.IncrementVar" ), TextConstants.getText( "Effect.DecrementVar" ),
				TextConstants.getText( "Effect.MacroReference" ),
				TextConstants.getText( "Effect.ConsumeObject" ), TextConstants.getText( "Effect.GenerateObject" ), 
				TextConstants.getText( "Effect.CancelAction" ), TextConstants.getText( "Effect.SpeakPlayer" ), 
				TextConstants.getText( "Effect.SpeakCharacter" ), TextConstants.getText( "Effect.TriggerBook" ), 
				TextConstants.getText( "Effect.PlaySound" ), TextConstants.getText( "Effect.PlayAnimation" ), 
				TextConstants.getText( "Effect.MovePlayer" ), TextConstants.getText( "Effect.MoveCharacter" ), 
				TextConstants.getText( "Effect.TriggerConversation" ), TextConstants.getText( "Effect.TriggerCutscene" ), 
				TextConstants.getText( "Effect.TriggerScene" ), TextConstants.getText( "Effect.TriggerLastScene" ) , 
				TextConstants.getText( "Effect.RandomEffect" ),TextConstants.getText( "Effect.ShowText" ),
				TextConstants.getText( "Effect.WaitTime" )};

		// Create a list with the types of the effects (in the same order as the previous)
		final int[] effectTypes = { Effect.ACTIVATE, Effect.DEACTIVATE, Effect.SET_VALUE, Effect.INCREMENT_VAR, Effect.DECREMENT_VAR, 
				Effect.MACRO_REF, Effect.CONSUME_OBJECT, Effect.GENERATE_OBJECT, Effect.CANCEL_ACTION, Effect.SPEAK_PLAYER, 
				Effect.SPEAK_CHAR, Effect.TRIGGER_BOOK, Effect.PLAY_SOUND, Effect.PLAY_ANIMATION, Effect.MOVE_PLAYER, Effect.MOVE_NPC, 
				Effect.TRIGGER_CONVERSATION, Effect.TRIGGER_CUTSCENE, Effect.TRIGGER_SCENE, Effect.TRIGGER_LAST_SCENE, Effect.RANDOM_EFFECT,
				Effect.SHOW_TEXT,Effect.WAIT_TIME};*/

		// Show a dialog to select the type of the effect
		//String selectedValue = controller.showInputDialog( TextConstants.getText( "Effects.OperationAddEffect" ), TextConstants.getText( "Effects.SelectEffectType" ), effectNames );
		//String selectedValue = SelectEffectsDialog.getSelectedEffect();
		HashMap<Integer, Object> effectProperties = SelectEffectsDialog.getNewEffectProperties(this);
		// If some effect was selected
		/*if( selectedValue != null && 
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
			}*/
			

		
			if( effectProperties != null ) {
				int selectedType = 0;
				if (effectProperties.containsKey(EFFECT_PROPERTY_TYPE)){
					selectedType = Integer.parseInt((String)effectProperties.get(EFFECT_PROPERTY_TYPE));
				}
					
				AbstractEffect newEffect = null;

				// Take all the values from the set
				String target = (String)effectProperties.get( EFFECT_PROPERTY_TARGET );
				String path = (String)effectProperties.get( EFFECT_PROPERTY_PATH );
				String text = (String)effectProperties.get( EFFECT_PROPERTY_TEXT );
				String value = (String)effectProperties.get( EFFECT_PROPERTY_VALUE );

				int x = 0;
				if( effectProperties.containsKey( EFFECT_PROPERTY_X ) )
					x = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_X ) );

				int y = 0;
				if( effectProperties.containsKey( EFFECT_PROPERTY_Y ) )
					y = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_Y ) );

				boolean background = false;
				if( effectProperties.containsKey( EFFECT_PROPERTY_BACKGROUND ) )
					background = Boolean.parseBoolean( (String)effectProperties.get( EFFECT_PROPERTY_BACKGROUND ) );
				
				int time=0;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_TIME  ) )
					time = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_TIME ) );

				int frontColor=0;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_FRONT_COLOR  ) )
				    frontColor = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_FRONT_COLOR ) );

				int borderColor=0;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_BORDER_COLOR  ) )
				    borderColor = Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_BORDER_COLOR ) );
				
				AbstractEffect firstEffect  = null;
				AbstractEffect secondEffect  = null;
				if ( effectProperties.containsKey( EFFECT_PROPERTY_FIRST_EFFECT  ) )
					firstEffect = (AbstractEffect)effectProperties.get(EFFECT_PROPERTY_FIRST_EFFECT );
				if ( effectProperties.containsKey( EFFECT_PROPERTY_SECOND_EFFECT  ) )
					secondEffect = (AbstractEffect)effectProperties.get(EFFECT_PROPERTY_SECOND_EFFECT );
					

				switch( selectedType ) {
					case Effect.ACTIVATE:
						newEffect = new ActivateEffect( target );
						controller.getVarFlagSummary( ).addFlagReference( target );
						break;
					case Effect.DEACTIVATE:
						newEffect = new DeactivateEffect( target );
						controller.getVarFlagSummary( ).addFlagReference( target );
						break;
					case Effect.SET_VALUE:
						newEffect = new SetValueEffect( target, Integer.parseInt( value ) );
						controller.getVarFlagSummary( ).addVarReference( target );
						break;
					case Effect.INCREMENT_VAR:
						newEffect = new IncrementVarEffect( target, Integer.parseInt( value ) );
						controller.getVarFlagSummary( ).addVarReference( target );
						break;
					case Effect.DECREMENT_VAR:
						newEffect = new DecrementVarEffect( target, Integer.parseInt( value ) );
						controller.getVarFlagSummary( ).addVarReference( target );
						break;
					case Effect.MACRO_REF:
						newEffect = new MacroReferenceEffect( target );
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
					case Effect.WAIT_TIME:
					    newEffect = new WaitTimeEffect(time);
					    break;
					case Effect.SHOW_TEXT:
					    newEffect = new ShowTextEffect(text,x,y,frontColor,borderColor);
					    break;
					case Effect.RANDOM_EFFECT:
						RandomEffect randomEffect = new RandomEffect(50);
						if (effectProperties.containsKey( EffectsController.EFFECT_PROPERTY_PROBABILITY )){
							randomEffect.setProbability( Integer.parseInt( (String)effectProperties.get( EFFECT_PROPERTY_PROBABILITY ) ) );
						}
						if (firstEffect!=null)
							randomEffect.setPositiveEffect( firstEffect );
						
						if (secondEffect!=null)
							randomEffect.setNegativeEffect( secondEffect );
						newEffect = randomEffect;
						break;
				}
				effectAdded = controller.addTool(new AddEffectTool(effects, newEffect,conditionsList));
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
		controller.addTool(new DeleteEffectTool (effects, index,conditionsList));
	}

	/**
	 * Moves up the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect to move
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean moveUpEffect( int index ) {
		return controller.addTool(new MoveEffectInTableTool(effects, index, MoveObjectTool.MODE_UP,conditionsList));
		/*boolean effectMoved = false;

		if( index > 0 ) {
			effects.getEffects( ).add( index - 1, effects.getEffects( ).remove( index ) );
			controller.dataModified( );
			effectMoved = true;
		}*/

		//return effectMoved;
	}

	/**
	 * Moves down the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect to move
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean moveDownEffect( int index ) {
		return controller.addTool(new MoveEffectInTableTool(effects, index, MoveObjectTool.MODE_DOWN,conditionsList));
		/*boolean effectMoved = false;

		if( index < effects.getEffects( ).size( ) - 1 ) {
			effects.getEffects( ).add( index + 1, effects.getEffects( ).remove( index ) );
			controller.dataModified( );
			effectMoved = true;
		}

		return effectMoved;*/
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
		AbstractEffect effect = effects.getEffects( ).get( index );
		int effectType = effect.getType( );

		// Create the hashmap to store the current values
		HashMap<Integer, Object> currentValues = new HashMap<Integer, Object>( );

		switch( effectType ) {
			case Effect.ACTIVATE:
				ActivateEffect activateEffect = (ActivateEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, activateEffect.getTargetId( ) );
				break;
			case Effect.DEACTIVATE:
				DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, deactivateEffect.getTargetId( ) );
				break;
			case Effect.SET_VALUE:
				SetValueEffect setValueEffect = (SetValueEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, setValueEffect.getTargetId( ) );
				currentValues.put( EFFECT_PROPERTY_VALUE, Integer.toString( setValueEffect.getValue() ) );
				break;
			case Effect.INCREMENT_VAR:
				IncrementVarEffect incrementVarEffect = (IncrementVarEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, incrementVarEffect.getTargetId( ) );
				currentValues.put( EFFECT_PROPERTY_VALUE, Integer.toString( incrementVarEffect.getIncrement() ) );
				break;
			case Effect.DECREMENT_VAR:
				DecrementVarEffect decrementVarEffect = (DecrementVarEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, decrementVarEffect.getTargetId( ) );
				currentValues.put( EFFECT_PROPERTY_VALUE, Integer.toString( decrementVarEffect.getDecrement() ) );
				break;
			case Effect.MACRO_REF:
				MacroReferenceEffect macroRefEffect = (MacroReferenceEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, macroRefEffect.getTargetId() );
				break;
			case Effect.CONSUME_OBJECT:
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, consumeObjectEffect.getTargetId( ) );
				break;
			case Effect.GENERATE_OBJECT:
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, generateObjectEffect.getTargetId( ) );
				break;
			case Effect.SPEAK_PLAYER:
				SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TEXT, speakPlayerEffect.getLine( ) );
				break;
			case Effect.SPEAK_CHAR:
				SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, speakCharEffect.getTargetId( ) );
				currentValues.put( EFFECT_PROPERTY_TEXT, speakCharEffect.getLine( ) );
				break;
			case Effect.TRIGGER_BOOK:
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerBookEffect.getTargetId( ) );
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
				currentValues.put( EFFECT_PROPERTY_TARGET, moveNPCEffect.getTargetId( ) );
				currentValues.put( EFFECT_PROPERTY_X, String.valueOf( moveNPCEffect.getX( ) ) );
				currentValues.put( EFFECT_PROPERTY_Y, String.valueOf( moveNPCEffect.getY( ) ) );
				break;
			case Effect.TRIGGER_CONVERSATION:
				TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerConversationEffect.getTargetId( ) );
				break;
			case Effect.TRIGGER_CUTSCENE:
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerCutsceneEffect.getTargetId( ) );
				break;
			case Effect.TRIGGER_SCENE:
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				currentValues.put( EFFECT_PROPERTY_TARGET, triggerSceneEffect.getTargetId( ) );
				currentValues.put( EFFECT_PROPERTY_X, String.valueOf( triggerSceneEffect.getX( ) ) );
				currentValues.put( EFFECT_PROPERTY_Y, String.valueOf( triggerSceneEffect.getY( ) ) );
				break;
			case Effect.WAIT_TIME:
			    	WaitTimeEffect waitTimeEffect = (WaitTimeEffect)effect;
			    	currentValues.put(EFFECT_PROPERTY_TIME, Integer.toString(waitTimeEffect.getTime()));
			    	break;
			case Effect.SHOW_TEXT:
			    	ShowTextEffect showTextEffect = (ShowTextEffect)effect;
			    	currentValues.put(EFFECT_PROPERTY_TEXT, showTextEffect.getText());
			    	currentValues.put(EFFECT_PROPERTY_X, Integer.toString(showTextEffect.getX()));
			    	currentValues.put(EFFECT_PROPERTY_Y, Integer.toString(showTextEffect.getY()));
			    	currentValues.put(EFFECT_PROPERTY_FRONT_COLOR, Integer.toString(showTextEffect.getRgbFrontColor()));
			    	currentValues.put(EFFECT_PROPERTY_BORDER_COLOR, Integer.toString(showTextEffect.getRgbBorderColor()));
			    	break;
		}

		// Show the editing dialog
		HashMap<Integer, Object> newProperties = null; 
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
			
			if( effectType!= Effect.RANDOM_EFFECT ) {
				effectEdited = controller.addTool(new ReplaceEffectTool(effects, effect, newProperties));
			} else {
				effectEdited = controller.addTool(new ReplaceEffectTool(effects, effect, newProperties,
						pos.getEffect( ),neg.getEffect( )));
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
		/*String selectedAsset = null;
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
		}*/
		int assetCategory = -1;
		int assetFilter = AssetsController.FILTER_NONE;
		if( assetType == ASSET_ANIMATION ){
			assetCategory = AssetsController.CATEGORY_ANIMATION;
			assetFilter = AssetsController.FILTER_PNG;
			
		}else if( assetType == ASSET_SOUND ){
			assetCategory = AssetsController.CATEGORY_AUDIO;
		}
		
		String assetPath = SelectResourceTool.selectAssetPathUsingChooser ( assetCategory, assetFilter );
		
		return assetPath;
	}

	/**
	 * Updates the given flag summary, adding the flag references contained in the given effects.
	 * 
	 * @param varFlagSummary
	 *            Flag summary to update
	 * @param effects
	 *            Set of effects to search in
	 */
	public static void updateVarFlagSummary( VarFlagSummary varFlagSummary, Effects effects ) {
		// Search every effect
		for( Effect effect : effects.getEffects( ) ) {

			updateVarFlagSummary ( varFlagSummary, effect );
			
			if( effect.getType() == Effect.RANDOM_EFFECT ) {
				RandomEffect randomEffect = (RandomEffect) effect;
				if (randomEffect.getNegativeEffect( )!=null){
					updateVarFlagSummary ( varFlagSummary, randomEffect.getNegativeEffect() );
				}
				
				if (randomEffect.getPositiveEffect( )!=null){
					updateVarFlagSummary ( varFlagSummary, randomEffect.getPositiveEffect() );
				}

			}

		}
	}
	
	/**
	 * Udaptes a flag summary according to a single Effect
	 * @param varFlagSummary
	 * @param effect
	 */
	private static void updateVarFlagSummary ( VarFlagSummary varFlagSummary, Effect effect ){
		if ( effect.getType() == Effect.ACTIVATE ){
			ActivateEffect activateEffect = (ActivateEffect)effect;
			varFlagSummary.addFlagReference( activateEffect.getTargetId( ) );
		} else if ( effect.getType() == Effect.DEACTIVATE ){
			DeactivateEffect deactivateEffect = (DeactivateEffect)effect;
			varFlagSummary.addFlagReference( deactivateEffect.getTargetId( ) );
		} else if ( effect.getType() == Effect.SET_VALUE ){
			SetValueEffect setValueEffect = (SetValueEffect)effect;
			varFlagSummary.addVarReference( setValueEffect.getTargetId() );
		} else if ( effect.getType() == Effect.INCREMENT_VAR ){
			IncrementVarEffect incrementEffect = (IncrementVarEffect)effect;
			varFlagSummary.addVarReference( incrementEffect.getTargetId() );
		} else if ( effect.getType() == Effect.DECREMENT_VAR ){
			DecrementVarEffect decrementEffect = (DecrementVarEffect)effect;
			varFlagSummary.addVarReference( decrementEffect.getTargetId() );
		}
		
		// UPdate conditions
		ConditionsController.updateVarFlagSummary(varFlagSummary, ((AbstractEffect)effect).getConditions());
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
					//Controller.getInstance( ).dataModified( );
				}
			} else if( type == Effect.PLAY_SOUND ) {
				// If the asset is the same, delete it
				PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
				if( playSoundEffect.getPath( ).equals( assetPath ) ) {
					playSoundEffect.setPath( "" );
					//Controller.getInstance( ).dataModified( );
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
			if( ( type == Effect.CONSUME_OBJECT && ( (ConsumeObjectEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.GENERATE_OBJECT && ( (GenerateObjectEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.SPEAK_CHAR && ( (SpeakCharEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.TRIGGER_BOOK && ( (TriggerBookEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.MOVE_NPC && ( (MoveNPCEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.TRIGGER_CONVERSATION && ( (TriggerConversationEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.TRIGGER_SCENE && ( (TriggerSceneEffect) effect ).getTargetId( ).equals( id ) ) || ( type == Effect.TRIGGER_CUTSCENE && ( (TriggerCutsceneEffect) effect ).getTargetId( ).equals( id ) ) )
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
				if( consumeObjectEffect.getTargetId( ).equals( oldId ) )
					consumeObjectEffect.setTargetId( newId );
			} else if( type == Effect.GENERATE_OBJECT ) {
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				if( generateObjectEffect.getTargetId( ).equals( oldId ) )
					generateObjectEffect.setTargetId( newId );
			} else if( type == Effect.SPEAK_CHAR ) {
				SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
				if( speakCharEffect.getTargetId( ).equals( oldId ) )
					speakCharEffect.setTargetId( newId );
			} else if( type == Effect.TRIGGER_BOOK ) {
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				if( triggerBookEffect.getTargetId( ).equals( oldId ) )
					triggerBookEffect.setTargetId( newId );
			} else if( type == Effect.MOVE_NPC ) {
				MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
				if( moveNPCEffect.getTargetId( ).equals( oldId ) )
					moveNPCEffect.setTargetId( newId );
			} else if( type == Effect.TRIGGER_CONVERSATION ) {
				TriggerConversationEffect trigerConversationEffect = (TriggerConversationEffect) effect;
				if( trigerConversationEffect.getTargetId( ).equals( oldId ) )
					trigerConversationEffect.setTargetId( newId );
			} else if( type == Effect.TRIGGER_SCENE ) {
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				if( triggerSceneEffect.getTargetId( ).equals( oldId ) )
					triggerSceneEffect.setTargetId( newId );
			} else if( type == Effect.TRIGGER_CUTSCENE ) {
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				if( triggerCutsceneEffect.getTargetId( ).equals( oldId ) )
					triggerCutsceneEffect.setTargetId( newId );
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
			Effect effect = effects.getEffects( ).get( i );
			int type = effect.getType( );
			boolean deleteEffect = false;
			// If random effect
			if ( type == Effect.RANDOM_EFFECT){
				RandomEffect randomEffect = (RandomEffect) effect;
				
				if (randomEffect.getPositiveEffect( )!=null){
					if (deleteSingleEffect(id, randomEffect.getPositiveEffect())){
						randomEffect.setPositiveEffect(null);
						deleteEffect = true;
					}
				}
				if (randomEffect.getNegativeEffect( )!=null){
					if (deleteSingleEffect(id, randomEffect.getNegativeEffect())){
						randomEffect.setNegativeEffect(null);
						
					}
				}

			} else {
				deleteEffect = deleteSingleEffect ( id, effect );
			}


			// Delete the effect, or increase the counter
			if( deleteEffect )
				effects.getEffects( ).remove( i );
			else
				i++;
		}
	}
	
	private static boolean deleteSingleEffect (String id, Effect effect ){
		boolean deleteEffect = false;
		int type = effect.getType( );

		// Check if the effect must be deleted
		if( type == Effect.CONSUME_OBJECT )
			deleteEffect = ( (ConsumeObjectEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.GENERATE_OBJECT )
			deleteEffect = ( (GenerateObjectEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.SPEAK_CHAR )
			deleteEffect = ( (SpeakCharEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.TRIGGER_BOOK )
			deleteEffect = ( (TriggerBookEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.MOVE_NPC )
			deleteEffect = ( (MoveNPCEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.TRIGGER_CONVERSATION )
			deleteEffect = ( (TriggerConversationEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.TRIGGER_SCENE )
			deleteEffect = ( (TriggerSceneEffect) effect ).getTargetId( ).equals( id );
		else if( type == Effect.TRIGGER_CUTSCENE )
			deleteEffect = ( (TriggerCutsceneEffect) effect ).getTargetId( ).equals( id );
		return deleteEffect;
	}
}