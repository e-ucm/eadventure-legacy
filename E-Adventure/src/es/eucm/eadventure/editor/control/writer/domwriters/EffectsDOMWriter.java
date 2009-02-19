package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
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

public class EffectsDOMWriter {

	/**
	 * Constant for the effect block.
	 */
	public static final String EFFECTS = "effect";

	/**
	 * Constant for the post effect block.
	 */
	public static final String POST_EFFECTS = "post-effect";
	
	/**
	 * Constant for the macro block.
	 */
	public static final String MACRO = "macro";


	/**
	 * Private constructor.
	 */
	private EffectsDOMWriter( ) {}

	public static Node buildDOM( String type, Effects effects ) {
		Node effectsNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			effectsNode = doc.createElement( type );
			appendEffects ( doc, effectsNode, effects );
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}
		return effectsNode;

	}
	
	public static Element buildDOM( Macro macro ) {
		Element effectsNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			effectsNode = doc.createElement( MACRO );
			effectsNode.setAttribute("id", macro.getId());
			Node documentationNode = doc.createElement("documentation");
			documentationNode.appendChild(doc.createTextNode(macro.getDocumentation()));
			effectsNode.appendChild(documentationNode);
			appendEffects ( doc, effectsNode, macro );
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}
		return effectsNode;

	}

	
	public static void appendEffects ( Document doc, Node effectsNode, Effects effects ){

		// Add every effect
		for( Effect effect : effects.getEffects( ) ) {
			
			Element effectElement = null;
			
			if ( effect.getType( ) != Effect.RANDOM_EFFECT)
				effectElement = buildEffectNode( effect, doc );
			else{
				RandomEffect randomEffect = (RandomEffect)effect;
				effectElement = doc.createElement( "random-effect" );
				effectElement.setAttribute( "probability", Integer.toString( randomEffect.getProbability( ) ) );
				
				Element posEfElement = null;
				Element negEfElement = null;
				
				if (randomEffect.getPositiveEffect( )!=null){
					posEfElement = buildEffectNode (randomEffect.getPositiveEffect( ), doc);
					effectElement.appendChild( posEfElement );
					if (randomEffect.getNegativeEffect( )!=null){
						negEfElement = buildEffectNode (randomEffect.getNegativeEffect( ), doc);
						effectElement.appendChild( negEfElement );
					}
				}

				
			}

			// Add the effect
			effectsNode.appendChild( effectElement );
		}

	}
	
	private static Element buildEffectNode (Effect effect, Document doc){
		Element effectElement = null;

		switch( effect.getType( ) ) {
			case Effect.ACTIVATE:
				ActivateEffect activateEffect = (ActivateEffect) effect;
				effectElement = doc.createElement( "activate" );
				effectElement.setAttribute( "flag", activateEffect.getTargetId( ) );
				break;
			case Effect.DEACTIVATE:
				DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
				effectElement = doc.createElement( "deactivate" );
				effectElement.setAttribute( "flag", deactivateEffect.getTargetId( ) );
				break;
			case Effect.SET_VALUE:
				SetValueEffect setValueEffect = (SetValueEffect) effect;
				effectElement = doc.createElement( "set-value" );
				effectElement.setAttribute("var", setValueEffect.getTargetId());
				effectElement.setAttribute("value", Integer.toString( setValueEffect.getValue() ) );
				break;
			case Effect.INCREMENT_VAR:
				IncrementVarEffect incrementVarEffect = (IncrementVarEffect) effect;
				effectElement = doc.createElement( "increment" );
				effectElement.setAttribute("var", incrementVarEffect.getTargetId());
				effectElement.setAttribute("value", Integer.toString( incrementVarEffect.getIncrement() ) );
				break;
			case Effect.DECREMENT_VAR:
				DecrementVarEffect decrementVarEffect = (DecrementVarEffect) effect;
				effectElement = doc.createElement( "decrement" );
				effectElement.setAttribute("var", decrementVarEffect.getTargetId());
				effectElement.setAttribute("value", Integer.toString( decrementVarEffect.getDecrement() ) );
				break;
			case Effect.MACRO_REF:
				MacroReferenceEffect macroRefEffect = (MacroReferenceEffect) effect;
				effectElement = doc.createElement( "macro-ref" );
				effectElement.setAttribute( "id", macroRefEffect.getTargetId( ) );
				break;
			case Effect.CONSUME_OBJECT:
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				effectElement = doc.createElement( "consume-object" );
				effectElement.setAttribute( "idTarget", consumeObjectEffect.getTargetId( ) );
				break;
			case Effect.GENERATE_OBJECT:
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				effectElement = doc.createElement( "generate-object" );
				effectElement.setAttribute( "idTarget", generateObjectEffect.getTargetId( ) );
				break;
			case Effect.CANCEL_ACTION:
				effectElement = doc.createElement( "cancel-action" );
				break;
			case Effect.SPEAK_PLAYER:
				SpeakPlayerEffect speakPlayerEffect = (SpeakPlayerEffect) effect;
				effectElement = doc.createElement( "speak-player" );
				effectElement.appendChild( doc.createTextNode( speakPlayerEffect.getLine( ) ) );
				break;
			case Effect.SPEAK_CHAR:
				SpeakCharEffect speakCharEffect = (SpeakCharEffect) effect;
				effectElement = doc.createElement( "speak-char" );
				effectElement.setAttribute( "idTarget", speakCharEffect.getTargetId( ) );
				effectElement.appendChild( doc.createTextNode( speakCharEffect.getLine( ) ) );
				break;
			case Effect.TRIGGER_BOOK:
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				effectElement = doc.createElement( "trigger-book" );
				effectElement.setAttribute( "idTarget", triggerBookEffect.getTargetId( ) );
				break;
			case Effect.PLAY_SOUND:
				PlaySoundEffect playSoundEffect = (PlaySoundEffect) effect;
				effectElement = doc.createElement( "play-sound" );
				if( !playSoundEffect.isBackground( ) )
					effectElement.setAttribute( "background", "no" );
				effectElement.setAttribute( "uri", playSoundEffect.getPath( ) );
				break;
			case Effect.PLAY_ANIMATION:
				PlayAnimationEffect playAnimationEffect = (PlayAnimationEffect) effect;
				effectElement = doc.createElement( "play-animation" );
				effectElement.setAttribute( "uri", playAnimationEffect.getPath( ) );
				effectElement.setAttribute( "x", String.valueOf( playAnimationEffect.getX( ) ) );
				effectElement.setAttribute( "y", String.valueOf( playAnimationEffect.getY( ) ) );
				break;
			case Effect.MOVE_PLAYER:
				MovePlayerEffect movePlayerEffect = (MovePlayerEffect) effect;
				effectElement = doc.createElement( "move-player" );
				effectElement.setAttribute( "x", String.valueOf( movePlayerEffect.getX( ) ) );
				effectElement.setAttribute( "y", String.valueOf( movePlayerEffect.getY( ) ) );
				break;
			case Effect.MOVE_NPC:
				MoveNPCEffect moveNPCEffect = (MoveNPCEffect) effect;
				effectElement = doc.createElement( "move-npc" );
				effectElement.setAttribute( "idTarget", moveNPCEffect.getTargetId( ) );
				effectElement.setAttribute( "x", String.valueOf( moveNPCEffect.getX( ) ) );
				effectElement.setAttribute( "y", String.valueOf( moveNPCEffect.getY( ) ) );
				break;
			case Effect.TRIGGER_CONVERSATION:
				TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
				effectElement = doc.createElement( "trigger-conversation" );
				effectElement.setAttribute( "idTarget", triggerConversationEffect.getTargetId( ) );
				break;
			case Effect.TRIGGER_CUTSCENE:
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				effectElement = doc.createElement( "trigger-cutscene" );
				effectElement.setAttribute( "idTarget", triggerCutsceneEffect.getTargetId( ) );
				break;
			case Effect.TRIGGER_LAST_SCENE:
				effectElement = doc.createElement( "trigger-last-scene" );
				break;
			case Effect.TRIGGER_SCENE:
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				effectElement = doc.createElement( "trigger-scene" );
				effectElement.setAttribute( "idTarget", triggerSceneEffect.getTargetId( ) );
				effectElement.setAttribute( "x", String.valueOf( triggerSceneEffect.getX( ) ) );
				effectElement.setAttribute( "y", String.valueOf( triggerSceneEffect.getY( ) ) );
				break;
				
		}
		return effectElement;
	}
}
