package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSLifeCycle;


public class IMSLifeCycleDOMWriter extends IMSSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private IMSLifeCycleDOMWriter( ) {}

	public static Node buildDOM( IMSLifeCycle lifeCycle ) {
		Element lifeCycleElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			lifeCycleElement = doc.createElement( "lifecycle" );
			
			//Create the version node
			Element version = doc.createElement( "version" );
			version.appendChild( buildLangStringNode(doc, lifeCycle.getVersion( )));
			lifeCycleElement.appendChild( version );
			
			// Create the status node and add it 
			lifeCycleElement.appendChild(buildVocabularyNode(doc,"status",lifeCycle.getStatus()));
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return lifeCycleElement;
	}
}
