package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Trajectory;

public class TrajectoryDOMWriter {

	/**
	 * Private constructor.
	 */
	private TrajectoryDOMWriter( ) {}

	public static Node buildDOM( Trajectory trajectory ) {
		Element itemElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			itemElement = doc.createElement( "trajectory" );

			for (es.eucm.eadventure.common.data.chapter.Trajectory.Node node: trajectory.getNodes()) {
				Element nodeElement = doc.createElement( "node");
				nodeElement.setAttribute("id", node.getID());
				nodeElement.setAttribute("x", String.valueOf(node.getX()));
				nodeElement.setAttribute("y", String.valueOf(node.getY()));
				nodeElement.setAttribute("scale", String.valueOf(node.getScale()));
				itemElement.appendChild(nodeElement);
			}
			
			if (trajectory.getInitial() != null) {
				Element initialNodeElement = doc.createElement("initialnode");
				initialNodeElement.setAttribute("id", trajectory.getInitial().getID());
				itemElement.appendChild(initialNodeElement);
			}
			
			for (es.eucm.eadventure.common.data.chapter.Trajectory.Side side : trajectory.getSides()) {
				Element sideElement = doc.createElement( "side" );
				sideElement.setAttribute("idStart", side.getIDStart());
				sideElement.setAttribute("idEnd", side.getIDEnd());
				itemElement.appendChild(sideElement);
			}
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return itemElement;
	}
}
