package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapter.ConversationReference;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class CharacterDOMWriter {

	/**
	 * Private constructor.
	 */
	private CharacterDOMWriter( ) {}

	public static Node buildDOM( NPC character ) {
		Element characterElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			characterElement = doc.createElement( "character" );
			characterElement.setAttribute( "id", character.getId( ) );

			// Append the documentation (if avalaible)
			if( character.getDocumentation( ) != null ) {
				Node characterDocumentationNode = doc.createElement( "documentation" );
				characterDocumentationNode.appendChild( doc.createTextNode( character.getDocumentation( ) ) );
				characterElement.appendChild( characterDocumentationNode );
			}

			// Append the resources
			for( Resources resources : character.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CHARACTER );
				doc.adoptNode( resourcesNode );
				characterElement.appendChild( resourcesNode );
			}

			// Create the textcolor
			Node textColorNode = doc.createElement( "textcolor" );

			// Create and append the frontcolor
			Element frontColorElement = doc.createElement( "frontcolor" );
			frontColorElement.setAttribute( "color", character.getTextFrontColor( ) );
			textColorNode.appendChild( frontColorElement );

			// Create and append the bordercolor
			Element borderColoElement = doc.createElement( "bordercolor" );
			borderColoElement.setAttribute( "color", character.getTextBorderColor( ) );
			textColorNode.appendChild( borderColoElement );

			// Append the textcolor
			characterElement.appendChild( textColorNode );

			// Create the description
			Node descriptionNode = doc.createElement( "description" );

			// Create and append the name, brief description and detailed description
			Node nameNode = doc.createElement( "name" );
			nameNode.appendChild( doc.createTextNode( character.getName( ) ) );
			descriptionNode.appendChild( nameNode );

			Node briefNode = doc.createElement( "brief" );
			briefNode.appendChild( doc.createTextNode( character.getDescription( ) ) );
			descriptionNode.appendChild( briefNode );

			Node detailedNode = doc.createElement( "detailed" );
			detailedNode.appendChild( doc.createTextNode( character.getDetailedDescription( ) ) );
			descriptionNode.appendChild( detailedNode );

			// Append the description
			characterElement.appendChild( descriptionNode );

			// Add the conversation references (if there is at least one)
			if( !character.getConversationReferences( ).isEmpty( ) ) {
				Node conversationsNode = doc.createElement( "conversations" );

				// Append every single conversation reference
				for( ConversationReference conversationReference : character.getConversationReferences( ) ) {
					// Create the item reference element
					Element conversationReferenceElement = doc.createElement( "conversation-ref" );
					conversationReferenceElement.setAttribute( "idTarget", conversationReference.getIdTarget( ) );

					// Append the documentation (if avalaible)
					if( conversationReference.getDocumentation( ) != null ) {
						Node conversationDocumentationNode = doc.createElement( "documentation" );
						conversationDocumentationNode.appendChild( doc.createTextNode( conversationReference.getDocumentation( ) ) );
						conversationReferenceElement.appendChild( conversationDocumentationNode );
					}

					// Append the conditions (if avalaible)
					if( !conversationReference.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( conversationReference.getConditions( ) );
						doc.adoptNode( conditionsNode );
						conversationReferenceElement.appendChild( conditionsNode );
					}

					// Append the conversation reference
					conversationsNode.appendChild( conversationReferenceElement );
				}
				// Append the list of conversations
				characterElement.appendChild( conversationsNode );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return characterElement;
	}
}
