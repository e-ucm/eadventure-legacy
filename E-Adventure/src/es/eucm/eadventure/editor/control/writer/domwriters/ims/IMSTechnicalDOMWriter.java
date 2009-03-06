package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;


public class IMSTechnicalDOMWriter extends IMSSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private IMSTechnicalDOMWriter( ) {}

	public static Node buildDOM( IMSTechnical technical ) {
		Element technicalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			technicalElement = doc.createElement( "technical" );
			
			// Create the format node
			Element format = doc.createElement("format");
			format.setTextContent(technical.getFormat());
			technicalElement.appendChild(format);
			
			//Create the location node
			Element location = doc.createElement("location");
			String loc = technical.getLocation();
			if (loc.startsWith("0")){
				location.setAttribute("type", "URI");
			} else if (loc.startsWith("1")){
				location.setAttribute("type", "TEXT");
			}
			location.setTextContent(loc.substring(2));
			technicalElement.appendChild(location);
			
			
			
			//Create the requirement node
			Element requirement = doc.createElement( "requirement" );
			
			//Create the minimum version node
			if (isStringSet(technical.getMinimumVersion( ))){
				Element minVer = doc.createElement( "minimumversion" );
				minVer.setTextContent( technical.getMinimumVersion( ));
				requirement.appendChild( minVer );
			}
			
			//Create the maximum version node
			if (isStringSet(technical.getMinimumVersion( ))){
				Element maxVer = doc.createElement( "maximumversion" );
				maxVer.setTextContent( technical.getMaximumVersion( ));
				requirement.appendChild( maxVer );
			}
			
			technicalElement.appendChild( requirement );
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return technicalElement;
	}
}
