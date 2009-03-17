package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleContribute;
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
			metaMetaDataElement.setAttribute("vocElement", "metaMetadata");
			
			//Create identifier node
			//Create identifier node for each identifier
			for (int i=0;i<metametadata.getNIdentifier();i++){
				metaMetaDataElement.appendChild(LOMESDOMWriter.createOneIdentifier(doc,metametadata.getIdentifier(i).getCatalog(),metametadata.getIdentifier(i).getEntry()));
			}
		
			
			// contribution node
			for (int i=0;i<metametadata.getContribute().getSize();i++){
				Element contribution = doc.createElement("lomes:contribute");
				//Add role
				contribution.appendChild(buildVocabularyNode(doc,"lomes:role",((LOMESLifeCycleContribute)metametadata.getContribute().get(i)).getRole()));
				//take all entities for that contribution
				ArrayList<String> ent = ((LOMESLifeCycleContribute)metametadata.getContribute().get(i)).getEntity();
				for (int j=0;j<ent.size();j++){
					Element entity = doc.createElement("lomes:entity");
					entity.setTextContent(ent.get(j));
					contribution.appendChild(entity);
				}
				//Add date
				Element date = doc.createElement("lomes:date");
				date.setAttribute("vocElement", "date");
				Element dateTime = doc.createElement("lomes:dateTime");
				dateTime.setAttribute("vocElement", "dateTime");
				dateTime.setTextContent(((LOMESLifeCycleContribute)metametadata.getContribute().get(i)).getDate().getDateTime());
				date.appendChild(dateTime);
				Element description = doc.createElement( "lomes:description" );
				description.appendChild( buildLangStringNode(doc, ((LOMESLifeCycleContribute)metametadata.getContribute().get(i)).getDate().getDescription()));
				date.appendChild(description);
				contribution.appendChild(date);
				
				metaMetaDataElement.appendChild(contribution);
				}
				
			
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
