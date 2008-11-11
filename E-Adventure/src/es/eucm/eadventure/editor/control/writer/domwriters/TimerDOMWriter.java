package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapterdata.Timer;

public class TimerDOMWriter {

	/**
	 * Private constructor.
	 */
	private TimerDOMWriter( ) {}

	public static Node buildDOM( Timer timer ) {
		Element timerElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			timerElement = doc.createElement( "timer" );

			// Set the time attribute
			timerElement.setAttribute( "time", Long.toString( timer.getTime( ) ) );

			// Append the documentation (if avalaible)
			if( timer.getDocumentation( ) != null ) {
				Node timerDocumentationNode = doc.createElement( "documentation" );
				timerDocumentationNode.appendChild( doc.createTextNode( timer.getDocumentation( ) ) );
				timerElement.appendChild( timerDocumentationNode );
			}

			// Append the init conditions (if avalaible)
			if( !timer.getInitCond( ).isEmpty( ) ) {
				Node conditionsNode = ConditionsDOMWriter.buildDOM( ConditionsDOMWriter.INIT_CONDITIONS, timer.getInitCond( ) );
				doc.adoptNode( conditionsNode );
				timerElement.appendChild( conditionsNode );
			}

			// Append the end-conditions (if avalaible)
			if( !timer.getEndCond( ).isEmpty( ) ) {
				Node conditionsNode = ConditionsDOMWriter.buildDOM( ConditionsDOMWriter.END_CONDITIONS, timer.getEndCond( ) );
				doc.adoptNode( conditionsNode );
				timerElement.appendChild( conditionsNode );
			}

			// Append the effects (if avalaible)
			if( !timer.getEffects( ).isEmpty( ) ) {
				Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, timer.getEffects( ) );
				doc.adoptNode( effectsNode );
				timerElement.appendChild( effectsNode );
			}

			// Append the post-effects (if avalaible)
			if( !timer.getPostEffects( ).isEmpty( ) ) {
				Node postEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.POST_EFFECTS, timer.getPostEffects( ) );
				doc.adoptNode( postEffectsNode );
				timerElement.appendChild( postEffectsNode );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return timerElement;
	}
}
