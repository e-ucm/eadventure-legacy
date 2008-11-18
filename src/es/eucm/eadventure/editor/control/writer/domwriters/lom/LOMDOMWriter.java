package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.editor.control.controllers.lom.LOMDataControl;
import es.eucm.eadventure.editor.data.lom.LOMGeneral;

public class LOMDOMWriter {

	public static Node buildManifestDOM ( LOMDataControl dataControl ){
		Element manifest = null;
		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			manifest = doc.createElement( "manifest" );
			manifest.setAttribute( "identifier", "imsaccmdv1p0_manifest" );
			manifest.setAttribute( "xmlns", "http://www.imsglobal.org/xsd/imscp_v1p1" );
			manifest.setAttribute( "xmlns:imsmd", "http://www.imsglobal.org/xsd/imsmd_v1p2" );
			manifest.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
			manifest.setAttribute( "xsi:schemaLocation", "http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd http://www.imsglobal.org/xsd/imsmd_v1p2 imsmd_v1p2p4.xsd" );
			manifest.setAttribute( "version", "IMS CP 1.1.3" );
			manifest.appendChild( buildLOMDOM(dataControl) );
			

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return manifest;
	}
	
	public static Node buildLOMDOM( LOMDataControl dataControl ) {
		Element lomElement = null;
		
		

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			lomElement = doc.createElement( "imsmd:lom" );
			Node generalNode = LOMGeneralDOMWriter.buildDOM(dataControl.getGeneral( ).getData( ) );
			doc.adoptNode( generalNode );
			lomElement.appendChild( generalNode );
			
			Node lifeCycleNode =  LOMLifeCycleDOMWriter.buildDOM(dataControl.getLifeCycle( ).getData( ) );
			doc.adoptNode( lifeCycleNode );
			lomElement.appendChild( lifeCycleNode );
			
			Node technicalNode = LOMTechnicalDOMWriter.buildDOM(dataControl.getTechnical( ).getData( ) );
			doc.adoptNode( technicalNode );
			lomElement.appendChild( technicalNode );
			
			Node educationalNode =LOMEducationalDOMWriter.buildDOM(dataControl.getEducational( ).getData( ) );
			doc.adoptNode( educationalNode );
			lomElement.appendChild( educationalNode );
			
		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return lomElement;

	}
}
