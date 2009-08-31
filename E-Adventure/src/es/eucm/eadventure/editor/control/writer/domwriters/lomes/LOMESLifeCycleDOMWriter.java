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
package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleContribute;
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
			lifeCycleElement.setAttribute("vocElement", "lifeCycle");
			
			//Create the version node
			Element version = doc.createElement( "lomes:version" );
			version.setAttribute("vocElement", "version");
			version.appendChild( buildLangStringNode(doc, lifeCycle.getVersion( )));
			lifeCycleElement.appendChild( version );
			
			// Create the status node and add it 
			lifeCycleElement.appendChild(buildVocabularyNode(doc,"lomes:status",lifeCycle.getStatus()));
			
			// contribution node
			for (int i=0;i<lifeCycle.getContribute().getSize();i++){
			Element contribution = doc.createElement("lomes:contribute");
			//Add role
			contribution.appendChild(buildVocabularyNode(doc,"lomes:role",((LOMESLifeCycleContribute)lifeCycle.getContribute().get(i)).getRole()));
			//take all entities for that contribution
			ArrayList<String> ent = ((LOMESLifeCycleContribute)lifeCycle.getContribute().get(i)).getEntity();
			for (int j=0;j<ent.size();j++){
				Element entity = doc.createElement("lomes:entity");
				entity.setTextContent(ent.get(j));
				contribution.appendChild(entity);
			}
			//Add date
			Element date = doc.createElement("lomes:date");
			date.setAttribute("vocElement", "date");
			Element dateTime = doc.createElement("lomes:dateTime");
			dateTime.setAttribute("vocElement", "dateTime");
			dateTime.setTextContent(((LOMESLifeCycleContribute)lifeCycle.getContribute().get(i)).getDate().getDateTime());
			date.appendChild(dateTime);
			Element description = doc.createElement( "lomes:description" );
			description.appendChild( buildLangStringNode(doc, ((LOMESLifeCycleContribute)lifeCycle.getContribute().get(i)).getDate().getDescription()));
			date.appendChild(description);
			contribution.appendChild(date);
			
			lifeCycleElement.appendChild(contribution);
			}
			
			
			
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return lifeCycleElement;
	}
}
