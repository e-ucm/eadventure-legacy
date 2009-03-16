package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMOrComposite;
import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;


public class LOMESTechnicalDOMWriter extends LOMESSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMESTechnicalDOMWriter( ) {}

	public static Node buildDOM( LOMESTechnical technical ) {
		Element technicalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			technicalElement = doc.createElement( "lomes:technical" );
			
			// Create the format node
			/*Element format = doc.createElement("format");
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
			*/
			
			
			//Create the requirement node
			Element requirement = doc.createElement( "lomes:requirement" );
			
			for (int i=0;i<technical.getRequirement().getSize();i++){
			
			Element orComposite = doc.createElement("lomes:orComposite");
			
			orComposite.appendChild(buildVocabularyNode(doc,"type",((LOMOrComposite)technical.getRequirement().get(i)).getType()));
			orComposite.appendChild(buildVocabularyNode(doc,"name",((LOMOrComposite)technical.getRequirement().get(i)).getName()));
			
			
			
			//Create the maximum version node
			if (isStringSet(((LOMOrComposite)technical.getRequirement().get(i)).getMaximumVersion())){
				Element maxVer = doc.createElement( "lomes:maximumversion" );
				maxVer.setTextContent( ((LOMOrComposite)technical.getRequirement().get(i)).getMaximumVersion());
				orComposite.appendChild( maxVer );
			}
			
			//Create the minimum version node
			if (isStringSet(((LOMOrComposite)technical.getRequirement().get(i)).getMinimumVersion())){
				Element minVer = doc.createElement( "lomes:maximumversion" );
				minVer.setTextContent(((LOMOrComposite)technical.getRequirement().get(i)).getMinimumVersion());
				orComposite.appendChild( minVer );
			}
			
			requirement.appendChild(orComposite);
			}
			technicalElement.appendChild( requirement );
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return technicalElement;
	}
}
