package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class PlayerDOMWriter {

	/**
	 * Private constructor.
	 */
	private PlayerDOMWriter( ) {}

	public static Node buildDOM( Player player ) {
		Node playerNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			playerNode = doc.createElement( "player" );

			// Append the documentation (if avalaible)
			if( player.getDocumentation( ) != null ) {
				Node playerDocumentationNode = doc.createElement( "documentation" );
				playerDocumentationNode.appendChild( doc.createTextNode( player.getDocumentation( ) ) );
				playerNode.appendChild( playerDocumentationNode );
			}

			// Append the resources
			for( Resources resources : player.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CHARACTER );
				doc.adoptNode( resourcesNode );
				playerNode.appendChild( resourcesNode );
			}

			// Create the textcolor
			Node textColorNode = doc.createElement( "textcolor" );

			// Create and append the frontcolor
			Element frontColorElement = doc.createElement( "frontcolor" );
			frontColorElement.setAttribute( "color", player.getTextFrontColor( ) );
			textColorNode.appendChild( frontColorElement );

			// Create and append the bordercolor
			Element borderColoElement = doc.createElement( "bordercolor" );
			borderColoElement.setAttribute( "color", player.getTextBorderColor( ) );
			textColorNode.appendChild( borderColoElement );

			// Append the textcolor
			playerNode.appendChild( textColorNode );

			// Create the description
			Node descriptionNode = doc.createElement( "description" );

			// Create and append the name, brief description and detailed description
			Node nameNode = doc.createElement( "name" );
			nameNode.appendChild( doc.createTextNode( player.getName( ) ) );
			descriptionNode.appendChild( nameNode );

			Node briefNode = doc.createElement( "brief" );
			briefNode.appendChild( doc.createTextNode( player.getDescription( ) ) );
			descriptionNode.appendChild( briefNode );

			Node detailedNode = doc.createElement( "detailed" );
			detailedNode.appendChild( doc.createTextNode( player.getDetailedDescription( ) ) );
			descriptionNode.appendChild( detailedNode );

			// Append the description
			playerNode.appendChild( descriptionNode );

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return playerNode;
	}
}
