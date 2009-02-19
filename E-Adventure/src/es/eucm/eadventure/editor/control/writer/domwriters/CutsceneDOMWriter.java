package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;

public class CutsceneDOMWriter {

	/**
	 * Private constructor.
	 */
	private CutsceneDOMWriter( ) {}

	public static Node buildDOM( Cutscene cutscene, boolean initialScene ) {
		Element cutsceneElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			if( cutscene.getType( ) == Cutscene.SLIDESCENE )
				cutsceneElement = doc.createElement( "slidescene" );
			else if( cutscene.getType( ) == Cutscene.VIDEOSCENE )
				cutsceneElement = doc.createElement( "videoscene" );

			// Set the attributes
			cutsceneElement.setAttribute( "id", cutscene.getId( ) );
			if( initialScene )
				cutsceneElement.setAttribute( "start", "yes" );
			else
				cutsceneElement.setAttribute( "start", "no" );

			// Append the documentation (if avalaible)
			if( cutscene.getDocumentation( ) != null ) {
				Node cutsceneDocumentationNode = doc.createElement( "documentation" );
				cutsceneDocumentationNode.appendChild( doc.createTextNode( cutscene.getDocumentation( ) ) );
				cutsceneElement.appendChild( cutsceneDocumentationNode );
			}

			// Append the resources
			for( Resources resources : cutscene.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CUTSCENE );
				doc.adoptNode( resourcesNode );
				cutsceneElement.appendChild( resourcesNode );
			}

			// Append the name
			Node nameNode = doc.createElement( "name" );
			nameNode.appendChild( doc.createTextNode( cutscene.getName( ) ) );
			cutsceneElement.appendChild( nameNode );

			// If it is and end cutscene, insert the special tag
			if( cutscene.isEndScene( ) ) {
				cutsceneElement.appendChild( doc.createElement( "end-game" ) );
			}

			// If it doesn't finish the game, write the next scene elements
			else {
				// Append the next-scene structures
				for( NextScene nextScene : cutscene.getNextScenes( ) ) {
					// Create the next-scene element
					Element nextSceneElement = doc.createElement( "next-scene" );
					nextSceneElement.setAttribute( "idTarget", nextScene.getTargetId( ) );

					// Append the destination position (if avalaible)
					if( nextScene.hasPlayerPosition( ) ) {
						nextSceneElement.setAttribute( "x", String.valueOf( nextScene.getPositionX( ) ) );
						nextSceneElement.setAttribute( "y", String.valueOf( nextScene.getPositionY( ) ) );
					}

					// Append the conditions (if avalaible)
					if( !nextScene.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( nextScene.getConditions( ) );
						doc.adoptNode( conditionsNode );
						nextSceneElement.appendChild( conditionsNode );
					}

					// Append the effects (if avalaible)
					if( !nextScene.getEffects( ).isEmpty( ) ) {
						Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, nextScene.getEffects( ) );
						doc.adoptNode( effectsNode );
						nextSceneElement.appendChild( effectsNode );
					}

					// Append the post-effects (if avalaible)
					if( !nextScene.getPostEffects( ).isEmpty( ) ) {
						Node postEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.POST_EFFECTS, nextScene.getPostEffects( ) );
						doc.adoptNode( postEffectsNode );
						nextSceneElement.appendChild( postEffectsNode );
					}

					// Append the next scene
					cutsceneElement.appendChild( nextSceneElement );
				}
			}

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return cutsceneElement;
	}
}
