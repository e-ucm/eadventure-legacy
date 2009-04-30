package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
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

			if (cutscene.getNext() == Cutscene.NEWSCENE) {
				cutsceneElement.setAttribute( "idTarget", cutscene.getTargetId( ) );
	
				cutsceneElement.setAttribute( "destinyX", String.valueOf( cutscene.getPositionX( ) ) );
				cutsceneElement.setAttribute( "destinyY", String.valueOf( cutscene.getPositionY( ) ) );

				cutsceneElement.setAttribute( "transitionTime", String.valueOf( cutscene.getTransitionTime()));
				cutsceneElement.setAttribute( "transitionType", String.valueOf( cutscene.getTransitionType()));
			}
			
			if (cutscene.getNext() == Cutscene.GOBACK)
				cutsceneElement.setAttribute("next", "go-back");
			else if (cutscene.getNext() == Cutscene.ENDCHAPTER)
				cutsceneElement.setAttribute("next", "end-chapter");
			else if (cutscene.getNext() == Cutscene.NEWSCENE)
				cutsceneElement.setAttribute("next", "new-scene");
			
			if( !cutscene.getEffects( ).isEmpty( ) ) {
				Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, cutscene.getEffects( ) );
				doc.adoptNode( effectsNode );
				cutsceneElement.appendChild( effectsNode );
			}
			
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

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return cutsceneElement;
	}
}
