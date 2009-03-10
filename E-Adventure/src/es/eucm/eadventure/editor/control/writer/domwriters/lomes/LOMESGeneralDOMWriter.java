package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
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
			
			//Create identifier node for each identifier
			for (int i=0;i<general.getNIdentifier();i++){
				generalElement.appendChild(createOneIdentifier(doc,general.getIdentifier(i).getCatalog(),general.getIdentifier(i).getEntry()));
			}
	
			
			//generalElement.appendChild(identifier);
			
			//Create the title node
			Element title = doc.createElement( "lomes:title" );
			title.appendChild( buildLangStringNode(doc, general.getTitle( )));
			generalElement.appendChild( title );
			
			
			//Create the language node
			for (int i=0;i<general.getNLanguage();i++){
			Element language = doc.createElement( "lomes:language" );
			if (isStringSet(general.getLanguage( ))){
				
				language.setTextContent(general.getLanguage(i));
				
			}else {
				language.setTextContent("");
			}
			generalElement.appendChild( language );
			}
			
			//Create the description node
			for (int i=0;i<general.getNDescription();i++){
			Element description = doc.createElement( "lomes:description" );
			description.appendChild( buildLangStringNode(doc, general.getDescription(i)));
			generalElement.appendChild( description );
			}
			
			
			
			//Create the keyword node
			for (int i=0;i<general.getNKeyword();i++){
			Element keyword = doc.createElement( "lomes:keyword" );
			keyword.appendChild( buildLangStringNode(doc, general.getKeyword(i )));
			generalElement.appendChild( keyword );
			}
			// Create aggregation level node
			generalElement.appendChild(buildVocabularyNode(doc,"lomes:aggregationLevel",general.getAggregationLevel()));
			

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return generalElement;
	}
	
	private static Element createOneIdentifier(Document doc, String cat, String ent){
		
		Element identifier = doc.createElement( "lomes:identifier" );
		Element catalog =  doc.createElement( "lomes:catalog" );
		catalog.setTextContent(cat);
		identifier.appendChild(catalog);
		Element entry =  doc.createElement( "lomes:entry" );
		entry.setTextContent(ent);
		identifier.appendChild(entry);
		return identifier;
	}
}
