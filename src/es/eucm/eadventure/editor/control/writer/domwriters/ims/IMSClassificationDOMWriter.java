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
package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSClassification;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;

public class IMSClassificationDOMWriter extends IMSSimpleDataWriter{
	
	/**
	 * Private constructor.
	 */
	private IMSClassificationDOMWriter( ) {}

	public static Node buildDOM( IMSClassification classification) {
		Element classificationElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );
			
			// Create the root
			classificationElement = doc.createElement("classification");
			
			// Create the purpose node
			classificationElement.appendChild( buildVocabularyNode(doc,"purpose",classification.getPurpose()));
				
			// Create the description node
			Element description = doc.createElement( "description" );
			description.appendChild( buildLangStringNode(doc,classification.getDescription()));
			classificationElement.appendChild( description );
			
			//Create the keyword node
			Element keyword = doc.createElement( "keyword" );
			keyword.appendChild( buildLangStringNode(doc, classification.getKeyword( )));
			classificationElement.appendChild( keyword );
			
			
		} catch( ParserConfigurationException e ) {
	    	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return classificationElement;
	}
}
