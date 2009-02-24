package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.ims.IMSEducational;
import es.eucm.eadventure.editor.data.ims.IMSRights;

public class IMSRightsDOMWriter extends IMSSimpleDataWriter{
	
	/**
	 * Private constructor.
	 */
	private IMSRightsDOMWriter( ) {}

	public static Node buildDOM( IMSRights rights ) {
		Element rightsElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			// Create the root
			rightsElement = doc.createElement("rights");
			
			// Create cost node
			rightsElement.appendChild( buildVocabularyNode(doc,"cost",rights.getCost() ));
			
			// Create copyright and other restrictions
			rightsElement.appendChild(buildVocabularyNode(doc,"copyrightandotherrestrictions" ,rights.getCopyrightandotherrestrictions()));

	} catch( ParserConfigurationException e ) {
    	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
	}

	return rightsElement;
}

}
