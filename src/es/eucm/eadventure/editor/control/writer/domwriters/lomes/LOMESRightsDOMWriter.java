package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;

public class LOMESRightsDOMWriter extends LOMESSimpleDataWriter{
	
	/**
	 * Private constructor.
	 */
	private LOMESRightsDOMWriter( ) {}

	public static Node buildDOM( LOMESRights rights ) {
		Element rightsElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			// Create the root
			rightsElement = doc.createElement("lomes:rights");
			
			// Create cost node
			rightsElement.appendChild( buildVocabularyNode(doc,"cost",rights.getCost() ));
			
			// Create copyright and other restrictions
			rightsElement.appendChild(buildVocabularyNode(doc,"copyrightAndOtherRestrictions" ,rights.getCopyrightandotherrestrictions()));

			// Create description node
			Element description = doc.createElement( "lomes:description" );
			description.appendChild( buildLangStringNode(doc, rights.getDescription( )));
			rightsElement.appendChild( description );
			
			
			// create access node
			Element access = doc.createElement("lomes:access");
			
			// Create access type and access description
			access.appendChild(buildVocabularyNode(doc,"accessType" ,rights.getAccessType()));
			Element accessDescription = doc.createElement( "lomes:description" );
			accessDescription.appendChild( buildLangStringNode(doc, rights.getAccessDescription()));
			access.appendChild( accessDescription );
			rightsElement.appendChild( access );

			
	} catch( ParserConfigurationException e ) {
    	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
	}

	return rightsElement;
}

}
