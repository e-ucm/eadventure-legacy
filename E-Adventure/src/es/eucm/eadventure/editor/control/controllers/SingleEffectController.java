package es.eucm.eadventure.editor.control.controllers;

import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.CancelActionEffect;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadventure.common.data.chapter.effects.MovePlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerLastSceneEffect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.auxiliar.categoryfilters.AnimationFileFilter;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.EffectDialog;

/**
 * This class is the controller of the effects blocks. It manages the insertion and modification of the effects lists.
 * 
 * @author Bruno Torijano Bueno
 */
public class SingleEffectController extends EffectsController{

	private static Effects createEffectsStructure ( Effect effect ){
		Effects effects = new Effects();
		if (effect!=null)
			effects.add( effect );
		return effects;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param effects
	 *            Contained block of effects
	 */
	public SingleEffectController( Effect effect ) {
		super( createEffectsStructure(effect) );
	}

	public SingleEffectController( ) {
		super ( new Effects() );
	}

	/**
	 * Returns the info of the effect in the given position.
	 * 
	 * @param index
	 *            Position of the effect
	 * @return Information about the effect
	 */
	public String getEffectInfo( ) {
		if ( getEffectCount()>0 )
			return getEffectInfo (0);
		return null;
	}

	/**
	 * Adds a new condition to the block.
	 * 
	 * @return True if an effect was added, false otherwise
	 */
	public boolean addEffect( ) {
		
		effects.clear( );
		
		boolean effectAdded = false;

		// Create a list with the names of the effects (in the same order as the next)
		final String[] effectNames = { TextConstants.getText( "FunctionalEffect.Activate" ), TextConstants.getText( "FunctionalEffect.Deactivate" ), TextConstants.getText( "FunctionalEffect.ConsumeObject" ), TextConstants.getText( "FunctionalEffect.GenerateObject" ), TextConstants.getText( "FunctionalEffect.SpeakPlayer" ), TextConstants.getText( "FunctionalEffect.SpeakCharacter" ), TextConstants.getText( "FunctionalEffect.TriggerBook" ), TextConstants.getText( "FunctionalEffect.PlaySound" ), TextConstants.getText( "FunctionalEffect.PlayAnimation" ), TextConstants.getText( "FunctionalEffect.MovePlayer" ), TextConstants.getText( "FunctionalEffect.MoveCharacter" ), TextConstants.getText( "FunctionalEffect.TriggerConversation" ), TextConstants.getText( "FunctionalEffect.TriggerCutscene" ), TextConstants.getText( "FunctionalEffect.TriggerScene" ), TextConstants.getText( "FunctionalEffect.TriggerLastScene" ) };

		// Create a list with the types of the effects (in the same order as the previous)
		final int[] effectTypes = { Effect.ACTIVATE, Effect.DEACTIVATE, Effect.CONSUME_OBJECT, Effect.GENERATE_OBJECT, Effect.SPEAK_PLAYER, Effect.SPEAK_CHAR, Effect.TRIGGER_BOOK, Effect.PLAY_SOUND, Effect.PLAY_ANIMATION, Effect.MOVE_PLAYER, Effect.MOVE_NPC, Effect.TRIGGER_CONVERSATION, Effect.TRIGGER_CUTSCENE, Effect.TRIGGER_SCENE, Effect.TRIGGER_LAST_SCENE };

		// Show a dialog to select the type of the effect
		String selectedValue = controller.showInputDialog( TextConstants.getText( "FunctionalEffects.OperationAddEffect" ), TextConstants.getText( "FunctionalEffects.SelectEffectType" ), effectNames );

		// If some effect was selected
		if( selectedValue != null ) {
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
		}

		return effectAdded;
	}

	/**
	 * Deletes the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect
	 */
	public void deleteEffect( ) {
		if ( getEffectCount( )>0 )
			deleteEffect (0);
	}

	/**
	 * Edits the effect in the given position.
	 * 
	 * @param index
	 *            Index of the effect
	 * @return True if the effect was moved, false otherwise
	 */
	public boolean editEffect( ) {
		if ( getEffectCount( )>0 )
			return editEffect ( 0 );
		else
			return addEffect ( );
	}

	public Effect getEffect (){
		if ( getEffectCount( )>0 )
			return effects.getEffects( ).get( 0 );
		else
			return null;
	}

}