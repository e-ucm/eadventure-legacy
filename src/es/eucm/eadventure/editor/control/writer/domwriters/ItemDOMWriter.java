package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
				Node actionsNode = ActionsDOMWriter.buildDOM(item.getActions());
				doc.adoptNode(actionsNode);
				// Append the actions node
				itemElement.appendChild( actionsNode );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return itemElement;
	}
}
