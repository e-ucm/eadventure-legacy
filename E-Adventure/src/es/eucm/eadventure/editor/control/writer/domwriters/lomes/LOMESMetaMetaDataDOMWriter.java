package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSMetaMetaData;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESMetaMetaData;

public class LOMESMetaMetaDataDOMWriter extends LOMESSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMESMetaMetaDataDOMWriter( ) {}

	public static Node buildDOM( LOMESMetaMetaData metametadata ) {
		Element metaMetaDataElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			// Create root node
			metaMetaDataElement = doc.createElement("lomes:metaMetadata");
			
			//Create identifier node
			Element identifier = doc.createElement( "lomes:identifier" );
			Element catalog =  doc.createElement( "lomes:catalog" );
			catalog.setTextContent(metametadata.getCatalog());
			identifier.appendChild(catalog);
			Element entry =  doc.createElement( "lomes:entry" );
			entry.setTextContent(metametadata.getEntry());
			identifier.appendChild(entry);
			metaMetaDataElement.appendChild(identifier);
			
			// contribution node
			Element contribution = doc.createElement("lomes:contribute");
			contribution.appendChild(buildVocabularyNode(doc,"lomes:role",metametadata.getRole()));
			Element entity = doc.createElement("lomes:entity");
			entity.setTextContent(metametadata.getEntity());
			contribution.appendChild(entity);
			
			metaMetaDataElement.appendChild(contribution);
			
			
			// metadata scheme
			Element metadatascheme = doc.createElement("lomes:metadataSchema");
			metadatascheme.setTextContent(metametadata.getMetadatascheme());
			metaMetaDataElement.appendChild(metadatascheme);
			
			//Create the language node
			Element language = doc.createElement( "lomes:language" );
			if (isStringSet(metametadata.getLanguage( ))){
				
				language.setTextContent(metametadata.getLanguage( ));
				
			}else {
				language.setTextContent("");
			}
			metaMetaDataElement.appendChild( language );
			
			
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return metaMetaDataElement;
	}
	
}
