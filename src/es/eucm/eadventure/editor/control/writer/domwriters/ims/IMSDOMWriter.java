package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMDataControl;
import es.eucm.eadventure.editor.data.meta.lom.LOMGeneral;

public class IMSDOMWriter {

	
	public static Node buildIMSDOM( IMSDataControl dataControl ) {
		Element lomElement = null;
		
		

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			lomElement = doc.createElement( "lom" );
			//lomElement.setAttribute("xmlns", "http://www.imsglobal.org/xsd/imsmd_rootv1p2p1");
			// Create general node
			Node generalNode = IMSGeneralDOMWriter.buildDOM(dataControl.getGeneral( ).getData( ) );
			doc.adoptNode( generalNode );
			lomElement.appendChild( generalNode );
			
			// Create life cycle node
			Node lifeCycleNode =  IMSLifeCycleDOMWriter.buildDOM(dataControl.getLifeCycle( ).getData( ) );
			doc.adoptNode( lifeCycleNode );
			lomElement.appendChild( lifeCycleNode );
			
			// Create meta meta data node
			Node metametadataNode = IMSMetaMetaDataDOMWriter.buildDOM(dataControl.getMetametadata().getData( ) );
			doc.adoptNode( metametadataNode );
			lomElement.appendChild( metametadataNode );
			
			
			// Create technical node
			Node technicalNode = IMSTechnicalDOMWriter.buildDOM(dataControl.getTechnical( ).getData( ) );
			doc.adoptNode( technicalNode );
			lomElement.appendChild( technicalNode );
			
			// Create educational node
			Node educationalNode =IMSEducationalDOMWriter.buildDOM(dataControl.getEducational( ).getData( ) );
			doc.adoptNode( educationalNode );
			lomElement.appendChild( educationalNode );
			
			// Create rights node 
			Node rightsNode =IMSRightsDOMWriter.buildDOM(dataControl.getRights().getData() );
			doc.adoptNode( rightsNode );
			lomElement.appendChild( rightsNode );
			
			// Create classification node 
			Node classificationNode =IMSClassificationDOMWriter.buildDOM(dataControl.getClassification().getData() );
			doc.adoptNode( classificationNode );
			lomElement.appendChild( classificationNode );
			
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return lomElement;

	}
	
}
