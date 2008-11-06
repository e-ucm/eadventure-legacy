package es.eucm.eadventure.adventureeditor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.ActivateEffect;
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
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.TriggerSceneEffect;

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

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return effectsNode;
	}
	
	private static Element buildEffectNode (Effect effect, Document doc){
		Element effectElement = null;

		switch( effect.getType( ) ) {
			case Effect.ACTIVATE:
				ActivateEffect activateEffect = (ActivateEffect) effect;
				effectElement = doc.createElement( "activate" );
				effectElement.setAttribute( "flag", activateEffect.getIdFlag( ) );
				break;
			case Effect.DEACTIVATE:
				DeactivateEffect deactivateEffect = (DeactivateEffect) effect;
				effectElement = doc.createElement( "deactivate" );
				effectElement.setAttribute( "flag", deactivateEffect.getIdFlag( ) );
				break;
			case Effect.CONSUME_OBJECT:
				ConsumeObjectEffect consumeObjectEffect = (ConsumeObjectEffect) effect;
				effectElement = doc.createElement( "consume-object" );
				effectElement.setAttribute( "idTarget", consumeObjectEffect.getIdTarget( ) );
				break;
			case Effect.GENERATE_OBJECT:
				GenerateObjectEffect generateObjectEffect = (GenerateObjectEffect) effect;
				effectElement = doc.createElement( "generate-object" );
				effectElement.setAttribute( "idTarget", generateObjectEffect.getIdTarget( ) );
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
				effectElement.setAttribute( "idTarget", speakCharEffect.getIdTarget( ) );
				effectElement.appendChild( doc.createTextNode( speakCharEffect.getLine( ) ) );
				break;
			case Effect.TRIGGER_BOOK:
				TriggerBookEffect triggerBookEffect = (TriggerBookEffect) effect;
				effectElement = doc.createElement( "trigger-book" );
				effectElement.setAttribute( "idTarget", triggerBookEffect.getTargetBookId( ) );
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
				effectElement.setAttribute( "idTarget", moveNPCEffect.getIdTarget( ) );
				effectElement.setAttribute( "x", String.valueOf( moveNPCEffect.getX( ) ) );
				effectElement.setAttribute( "y", String.valueOf( moveNPCEffect.getY( ) ) );
				break;
			case Effect.TRIGGER_CONVERSATION:
				TriggerConversationEffect triggerConversationEffect = (TriggerConversationEffect) effect;
				effectElement = doc.createElement( "trigger-conversation" );
				effectElement.setAttribute( "idTarget", triggerConversationEffect.getTargetConversationId( ) );
				break;
			case Effect.TRIGGER_CUTSCENE:
				TriggerCutsceneEffect triggerCutsceneEffect = (TriggerCutsceneEffect) effect;
				effectElement = doc.createElement( "trigger-cutscene" );
				effectElement.setAttribute( "idTarget", triggerCutsceneEffect.getTargetCutsceneId( ) );
				break;
			case Effect.TRIGGER_LAST_SCENE:
				effectElement = doc.createElement( "trigger-last-scene" );
				break;
			case Effect.TRIGGER_SCENE:
				TriggerSceneEffect triggerSceneEffect = (TriggerSceneEffect) effect;
				effectElement = doc.createElement( "trigger-scene" );
				effectElement.setAttribute( "idTarget", triggerSceneEffect.getTargetSceneId( ) );
				effectElement.setAttribute( "x", String.valueOf( triggerSceneEffect.getX( ) ) );
				effectElement.setAttribute( "y", String.valueOf( triggerSceneEffect.getY( ) ) );
				break;
				
		}
		return effectElement;
	}
}
