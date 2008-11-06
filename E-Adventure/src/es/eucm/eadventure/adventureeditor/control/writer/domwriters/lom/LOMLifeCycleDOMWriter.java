package es.eucm.eadventure.adventureeditor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.adventureeditor.data.lomdata.LOMGeneral;
import es.eucm.eadventure.adventureeditor.data.lomdata.LOMLifeCycle;


public class LOMLifeCycleDOMWriter extends LOMSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMLifeCycleDOMWriter( ) {}

	public static Node buildDOM( LOMLifeCycle lifeCycle ) {
		Element lifeCycleElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			lifeCycleElement = doc.createElement( "imsmd:lifecycle" );
			
			//Create the version node
			if (isStringSet(lifeCycle.getVersion( ))){
				Element version = doc.createElement( "imsmd:version" );
				version.appendChild( buildLangStringNode(doc, lifeCycle.getVersion( )));
				lifeCycleElement.appendChild( version );
			}
			
		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return lifeCycleElement;
	}
}
