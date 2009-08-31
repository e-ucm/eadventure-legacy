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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;


public class LOMESEducationalDOMWriter extends LOMESSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMESEducationalDOMWriter( ) {}

	public static Node buildDOM( LOMESEducational educational ) {
		Element educationalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			educationalElement = doc.createElement( "lomes:educational" );
			educationalElement.setAttribute("vocElement", "educational");
			
			//Create the interactivity type node
			Node intType = buildVocabularyNode(doc, "lomes:interactivityType", educational.getInteractivityType( ));
			educationalElement.appendChild( intType );

			//Create the learning resource type
			for (int i=0;i<educational.getLearningResourceType().size();i++){
			    Node lrType = buildVocabularyNode(doc, "lomes:learningResourceType", educational.getLearningResourceType( ).get(i));
			    educationalElement.appendChild( lrType );
			}

			
			//Create the interactivity level node
			Node intLevel = buildVocabularyNode(doc, "lomes:interactivityLevel", educational.getInteractivityLevel( ));
			educationalElement.appendChild( intLevel );
			
			//Create the semantic density node
			Node semanticDensity = buildVocabularyNode(doc, "lomes:semanticDensity", educational.getSemanticDensity( ));
			educationalElement.appendChild( semanticDensity );
			
			//Create the intended end user role node
			for (int i =0;i<educational.getIntendedEndUserRole().size();i++){
			    Node ieuRole = buildVocabularyNode(doc, "lomes:intendedEndUserRole", educational.getIntendedEndUserRole( ).get(i));
			    educationalElement.appendChild( ieuRole );
			}
			
			//Create the context node
			for (int i=0;i<educational.getContext().size();i++){
			    Node context = buildVocabularyNode(doc, "lomes:context", educational.getContext( ).get(i));
			    educationalElement.appendChild( context );
			}
			
			//Create the typical age range node
			for (int i =0;i<educational.getTypicalAgeRange().size();i++){
			if (isStringSet(educational.getTypicalAgeRange( ).get(i))){
				Element taRange = doc.createElement( "lomes:typicalAgeRange" );
				taRange.appendChild( buildLangStringNode(doc, educational.getTypicalAgeRange( ).get(i)) );
				educationalElement.appendChild( taRange );
			}
			}

			//Create the difficulty node
			Node difficulty = buildVocabularyNode(doc, "lomes:difficulty", educational.getDifficulty( ));
			educationalElement.appendChild( difficulty );

			//Create the typical learning time node
			Node tlTime = doc.createElement( "lomes:typicalLearningTime" );
			Node dateTime = doc.createElement( "lomes:duration" );
			dateTime.setTextContent( educational.getTypicalLearningTime( ) );
			tlTime.appendChild( dateTime );	
			Node tdescription = doc.createElement("lomes:description");
			tdescription.appendChild(buildLangStringNode(doc,new LangString("Total game duration")));
			tlTime.appendChild(tdescription);
			educationalElement.appendChild( tlTime );
			
			//Create the description node
			for (int i =0;i<educational.getDescription().size();i++){
			if (isStringSet(educational.getDescription( ).get(i))){
				Element description = doc.createElement( "lomes:description" );
				description.appendChild( buildLangStringNode(doc, educational.getDescription( ).get(i)));
				educationalElement.appendChild( description );
			}
			}
			
			
			//Create the language node
			if (isStringSet(educational.getLanguage( ))){
				Element language = doc.createElement( "lomes:language" );
				language.setTextContent(educational.getLanguage( ));
				educationalElement.appendChild( language );
			}
			
			//create cognitive process node
			for (int i =0;i<educational.getCognitiveProcess().size();i++){
			Node cognitive = buildVocabularyNode(doc, "lomes:cognitiveProcess", educational.getCognitiveProcess().get(i));
			educationalElement.appendChild( cognitive );
			}


		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return educationalElement;
	}
}
