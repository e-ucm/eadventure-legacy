package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESGeneral;


public class LOMESGeneralDOMWriter extends LOMESSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMESGeneralDOMWriter( ) {}

	public static Node buildDOM( LOMESGeneral general ) {
		Element generalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			generalElement = doc.createElement( "lomes:general" );
			
			//Create identifier node
			Element identifier = doc.createElement( "lomes:identifier" );
			Element catalog =  doc.createElement( "lomes:catalog" );
			catalog.setTextContent(general.getCatalog());
			identifier.appendChild(catalog);
			Element entry =  doc.createElement( "lomes:entry" );
			entry.setTextContent(general.getEntry());
			identifier.appendChild(entry);
			
			//Create the title node
			Element title = doc.createElement( "lomes:title" );
			title.appendChild( buildLangStringNode(doc, general.getTitle( )));
			generalElement.appendChild( title );
			
			
			//Create the language node
			Element language = doc.createElement( "lomes:language" );
			if (isStringSet(general.getLanguage( ))){
				
				language.setTextContent(general.getLanguage( ));
				
			}else {
				language.setTextContent("");
			}
			generalElement.appendChild( language );
			
			//Create the description node
			Element description = doc.createElement( "lomes:description" );
			description.appendChild( buildLangStringNode(doc, general.getDescription( )));
			generalElement.appendChild( description );
			
			
			
			//Create the keyword node
			Element keyword = doc.createElement( "lomes:keyword" );
			keyword.appendChild( buildLangStringNode(doc, general.getKeyword( )));
			generalElement.appendChild( keyword );
			

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return generalElement;
	}
}
