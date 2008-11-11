package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.editor.data.lomdata.LOMGeneral;


public class LOMGeneralDOMWriter extends LOMSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMGeneralDOMWriter( ) {}

	public static Node buildDOM( LOMGeneral general ) {
		Element generalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			generalElement = doc.createElement( "imsmd:general" );
			
			//Create the title node
			if (isStringSet(general.getTitle( ))){
				Element title = doc.createElement( "imsmd:title" );
				title.appendChild( buildLangStringNode(doc, general.getTitle( )));
				generalElement.appendChild( title );
			}
			
			//Create the language node
			if (isStringSet(general.getLanguage( ))){
				Element language = doc.createElement( "imsmd:language" );
				language.setTextContent(general.getLanguage( ));
				generalElement.appendChild( language );
			}
			
			//Create the description node
			if (isStringSet(general.getDescription( ))){
				Element description = doc.createElement( "imsmd:description" );
				description.appendChild( buildLangStringNode(doc, general.getDescription( )));
				generalElement.appendChild( description );
			}
			
			
			//Create the keyword node
			if (isStringSet(general.getKeyword( ))){
				Element keyword = doc.createElement( "imsmd:keyword" );
				keyword.appendChild( buildLangStringNode(doc, general.getKeyword( )));
				generalElement.appendChild( keyword );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return generalElement;
	}
}
