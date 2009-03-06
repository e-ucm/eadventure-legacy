package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;


public class IMSGeneralDOMWriter extends IMSSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private IMSGeneralDOMWriter( ) {}

	public static Node buildDOM( IMSGeneral general ) {
		Element generalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			generalElement = doc.createElement( "general" );
			
			//Create the title node
			Element title = doc.createElement( "title" );
			title.appendChild( buildLangStringNode(doc, general.getTitle( )));
			generalElement.appendChild( title );
			
			//Create calatogentry node
			Element catalogentry = doc.createElement("catalogentry");
			// create and add catalog
			Element catalog = doc.createElement("catalog");
			catalog.setTextContent(general.getCatalog());
			catalogentry.appendChild(catalog);
			// add entry node
			Element entry = doc.createElement("entry");
			entry.appendChild( buildLangStringNode(doc, general.getEntry()));
			catalogentry.appendChild(entry);
			generalElement.appendChild(catalogentry);
			
			//Create the language node
			Element language = doc.createElement( "language" );
			if (isStringSet(general.getLanguage( ))){
				
				language.setTextContent(general.getLanguage( ));
				
			}else {
				language.setTextContent("");
			}
			generalElement.appendChild( language );
			
			//Create the description node
			Element description = doc.createElement( "description" );
			description.appendChild( buildLangStringNode(doc, general.getDescription( )));
			generalElement.appendChild( description );
			
			
			
			//Create the keyword node
			Element keyword = doc.createElement( "keyword" );
			keyword.appendChild( buildLangStringNode(doc, general.getKeyword( )));
			generalElement.appendChild( keyword );
			

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return generalElement;
	}
}
