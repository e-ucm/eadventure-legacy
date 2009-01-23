package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class AtrezzoDOMWriter {

	/**
	 * Private constructor.
	 */
	private AtrezzoDOMWriter( ) {}

	public static Node buildDOM( Atrezzo atrezzo ) {
		Element atrezzoElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			atrezzoElement = doc.createElement( "atrezzoobject" );
			atrezzoElement.setAttribute( "id", atrezzo.getId( ) );

			// Append the documentation (if avalaible)
			if( atrezzo.getDocumentation( ) != null ) {
				Node atrezzoDocumentationNode = doc.createElement( "documentation" );
				atrezzoDocumentationNode.appendChild( doc.createTextNode( atrezzo.getDocumentation( ) ) );
				atrezzoElement.appendChild( atrezzoDocumentationNode );
			}

			// Append the resources
			for( Resources resources : atrezzo.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_ITEM );
				doc.adoptNode( resourcesNode );
				atrezzoElement.appendChild( resourcesNode );
			}

			// Create the description
			Node descriptionNode = doc.createElement( "description" );

			// Create and append the name, brief description and detailed description
			Node nameNode = doc.createElement( "name" );
			nameNode.appendChild( doc.createTextNode( atrezzo.getName( ) ) );
			descriptionNode.appendChild( nameNode );

			Node briefNode = doc.createElement( "brief" );
			briefNode.appendChild( doc.createTextNode( atrezzo.getDescription( ) ) );
			descriptionNode.appendChild( briefNode );

			Node detailedNode = doc.createElement( "detailed" );
			detailedNode.appendChild( doc.createTextNode( atrezzo.getDetailedDescription( ) ) );
			descriptionNode.appendChild( detailedNode );

			// Append the description
			atrezzoElement.appendChild( descriptionNode );
			

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return atrezzoElement;
	}
	
}
