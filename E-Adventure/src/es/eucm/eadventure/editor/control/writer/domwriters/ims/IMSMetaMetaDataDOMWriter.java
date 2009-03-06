package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSMetaMetaData;

public class IMSMetaMetaDataDOMWriter extends IMSSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private IMSMetaMetaDataDOMWriter( ) {}

	public static Node buildDOM( IMSMetaMetaData metametadata ) {
		Element metaMetaDataElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			// Create root node
			metaMetaDataElement = doc.createElement("metametadata");
			
			Element metadatascheme = doc.createElement("metadatascheme");
			metadatascheme.setTextContent(metametadata.getMetadatascheme());
			metaMetaDataElement.appendChild(metadatascheme);
			
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return metaMetaDataElement;
	}
	
}
