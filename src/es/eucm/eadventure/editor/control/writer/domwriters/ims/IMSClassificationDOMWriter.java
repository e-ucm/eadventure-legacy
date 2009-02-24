package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.ims.IMSClassification;
import es.eucm.eadventure.editor.data.ims.IMSRights;

public class IMSClassificationDOMWriter extends IMSSimpleDataWriter{
	
	/**
	 * Private constructor.
	 */
	private IMSClassificationDOMWriter( ) {}

	public static Node buildDOM( IMSClassification classification) {
		Element classificationElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			// Create the root
			classificationElement = doc.createElement("classification");
			
			// Create the purpose node
			classificationElement.appendChild( buildVocabularyNode(doc,"purpose",classification.getPurpose()));
				
			// Create the description node
			Element description = doc.createElement( "description" );
			description.appendChild( buildLangStringNode(doc,classification.getDescription()));
			classificationElement.appendChild( description );
			
			//Create the keyword node
			Element keyword = doc.createElement( "keyword" );
			keyword.appendChild( buildLangStringNode(doc, classification.getKeyword( )));
			classificationElement.appendChild( keyword );
			
			
		} catch( ParserConfigurationException e ) {
	    	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return classificationElement;
	}
}
