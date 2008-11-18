package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class ItemDOMWriter {

	/**
	 * Private constructor.
	 */
	private ItemDOMWriter( ) {}

	public static Node buildDOM( Item item ) {
		Element itemElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			itemElement = doc.createElement( "object" );
			itemElement.setAttribute( "id", item.getId( ) );

			// Append the documentation (if avalaible)
			if( item.getDocumentation( ) != null ) {
				Node itemDocumentationNode = doc.createElement( "documentation" );
				itemDocumentationNode.appendChild( doc.createTextNode( item.getDocumentation( ) ) );
				itemElement.appendChild( itemDocumentationNode );
			}

			// Append the resources
			for( Resources resources : item.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_ITEM );
				doc.adoptNode( resourcesNode );
				itemElement.appendChild( resourcesNode );
			}

			// Create the description
			Node descriptionNode = doc.createElement( "description" );

			// Create and append the name, brief description and detailed description
			Node nameNode = doc.createElement( "name" );
			nameNode.appendChild( doc.createTextNode( item.getName( ) ) );
			descriptionNode.appendChild( nameNode );

			Node briefNode = doc.createElement( "brief" );
			briefNode.appendChild( doc.createTextNode( item.getDescription( ) ) );
			descriptionNode.appendChild( briefNode );

			Node detailedNode = doc.createElement( "detailed" );
			detailedNode.appendChild( doc.createTextNode( item.getDetailedDescription( ) ) );
			descriptionNode.appendChild( detailedNode );

			// Append the description
			itemElement.appendChild( descriptionNode );

			// Append the actions (if there is at least one)
			if( !item.getActions( ).isEmpty( ) ) {
				// Create the actions node
				Node actionsNode = doc.createElement( "actions" );

				// For every action
				for( Action action : item.getActions( ) ) {
					Element actionElement = null;

					// Create the element
					switch( action.getType( ) ) {
						case Action.EXAMINE:
							actionElement = doc.createElement( "examine" );
							break;
						case Action.GRAB:
							actionElement = doc.createElement( "grab" );
							break;
						case Action.USE:
							actionElement = doc.createElement( "use" );
							break;
						case Action.USE_WITH:
							actionElement = doc.createElement( "use-with" );
							actionElement.setAttribute( "idTarget", action.getIdTarget( ) );
							break;
						case Action.GIVE_TO:
							actionElement = doc.createElement( "give-to" );
							actionElement.setAttribute( "idTarget", action.getIdTarget( ) );
							break;
					}

					// Append the documentation (if avalaible)
					if( action.getDocumentation( ) != null ) {
						Node actionDocumentationNode = doc.createElement( "documentation" );
						actionDocumentationNode.appendChild( doc.createTextNode( action.getDocumentation( ) ) );
						actionElement.appendChild( actionDocumentationNode );
					}

					// Append the conditions (if avalaible)
					if( !action.getConditions( ).isEmpty( ) ) {
						Node conditionsNode = ConditionsDOMWriter.buildDOM( action.getConditions( ) );
						doc.adoptNode( conditionsNode );
						actionElement.appendChild( conditionsNode );
					}

					// Append the effects (if avalaible)
					if( !action.getEffects( ).isEmpty( ) ) {
						Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, action.getEffects( ) );
						doc.adoptNode( effectsNode );
						actionElement.appendChild( effectsNode );
					}

					// Append the action element
					actionsNode.appendChild( actionElement );
				}

				// Append the actions node
				itemElement.appendChild( actionsNode );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return itemElement;
	}
}
