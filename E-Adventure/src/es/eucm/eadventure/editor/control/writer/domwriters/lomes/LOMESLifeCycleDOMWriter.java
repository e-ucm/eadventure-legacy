package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSLifeCycle;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESLifeCycle;


public class LOMESLifeCycleDOMWriter extends LOMESSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMESLifeCycleDOMWriter( ) {}

	public static Node buildDOM( LOMESLifeCycle lifeCycle ) {
		Element lifeCycleElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			lifeCycleElement = doc.createElement( "lomes:lifeCycle" );
			
			//Create the version node
			Element version = doc.createElement( "lomes:version" );
			version.appendChild( buildLangStringNode(doc, lifeCycle.getVersion( )));
			lifeCycleElement.appendChild( version );
			
			// Create the status node and add it 
			lifeCycleElement.appendChild(buildVocabularyNode(doc,"status",lifeCycle.getStatus()));
			
			// contribution node
			Element contribution = doc.createElement("lomes:contribute");
			contribution.appendChild(buildVocabularyNode(doc,"role",lifeCycle.getRole()));
			Element entity = doc.createElement("lomes:entity");
			entity.setTextContent(lifeCycle.getEntity());
			contribution.appendChild(entity);
			
			
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return lifeCycleElement;
	}
}
