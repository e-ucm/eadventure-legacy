package es.eucm.eadventure.adventureeditor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.adventureeditor.data.lomdata.LOMGeneral;
import es.eucm.eadventure.adventureeditor.data.lomdata.LOMTechnical;


public class LOMTechnicalDOMWriter extends LOMSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMTechnicalDOMWriter( ) {}

	public static Node buildDOM( LOMTechnical technical ) {
		Element technicalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			technicalElement = doc.createElement( "imsmd:technical" );
			
			//Create the requirement node
			Element requirement = doc.createElement( "imsmd:requirement" );
			//Create the orComposite node
			Element orComposite = doc.createElement( "imsmd:orcomposite" );
			
			//Create the minimum version node
			if (isStringSet(technical.getMinimumVersion( ))){
				Element minVer = doc.createElement( "imsmd:minimumversion" );
				minVer.setTextContent( technical.getMinimumVersion( ));
				orComposite.appendChild( minVer );
			}
			
			//Create the maximum version node
			if (isStringSet(technical.getMinimumVersion( ))){
				Element maxVer = doc.createElement( "imsmd:maximumversion" );
				maxVer.setTextContent( technical.getMaximumVersion( ));
				orComposite.appendChild( maxVer );
			}
			
			requirement.appendChild( orComposite );
			technicalElement.appendChild( requirement );
			
		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return technicalElement;
	}
}
