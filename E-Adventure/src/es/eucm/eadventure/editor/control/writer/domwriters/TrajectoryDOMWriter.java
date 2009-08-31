/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
