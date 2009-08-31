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
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;


public class IMSEducationalDOMWriter extends IMSSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private IMSEducationalDOMWriter( ) {}

	public static Node buildDOM( IMSEducational educational ) {
		Element educationalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			educationalElement = doc.createElement( "educational" );
			
			//Create the interactivity type node
			Node intType = buildVocabularyNode(doc, "interactivitytype", educational.getInteractivityType( ));
			educationalElement.appendChild( intType );

			//Create the learning resource type
			Node lrType = buildVocabularyNode(doc, "learningresourcetype", educational.getLearningResourceType( ));
			educationalElement.appendChild( lrType );

			
			//Create the interactivity level node
			Node intLevel = buildVocabularyNode(doc, "interactivitylevel", educational.getInteractivityLevel( ));
			educationalElement.appendChild( intLevel );
			
			//Create the semantic density node
			Node semanticDensity = buildVocabularyNode(doc, "semanticdensity", educational.getSemanticDensity( ));
			educationalElement.appendChild( semanticDensity );
			
			//Create the intended end user role node
			Node ieuRole = buildVocabularyNode(doc, "intendedenduserrole", educational.getIntendedEndUserRole( ));
			educationalElement.appendChild( ieuRole );
			
			//Create the context node
			Node context = buildVocabularyNode(doc, "context", educational.getContext( ));
			educationalElement.appendChild( context );
			
			//Create the typical age range node
			if (isStringSet(educational.getTypicalAgeRange( ))){
				Element taRange = doc.createElement( "typicalagerange" );
				taRange.appendChild( buildLangStringNode(doc, educational.getTypicalAgeRange( )) );
				educationalElement.appendChild( taRange );
			}

			//Create the difficulty node
			Node difficulty = buildVocabularyNode(doc, "difficulty", educational.getDifficulty( ));
			educationalElement.appendChild( difficulty );

			//Create the typical learning time node
			Node tlTime = doc.createElement( "typicallearningtime" );
			Node dateTime = doc.createElement( "datetime" );
			dateTime.setTextContent( educational.getTypicalLearningTime( ) );
			tlTime.appendChild( dateTime );
			educationalElement.appendChild( tlTime );
			
			//Create the description node
			if (isStringSet(educational.getDescription( ))){
				Element description = doc.createElement( "description" );
				description.appendChild( buildLangStringNode(doc, educational.getDescription( )));
				educationalElement.appendChild( description );
			}
			
			
			//Create the language node
			if (isStringSet(educational.getLanguage( ))){
				Element language = doc.createElement( "language" );
				language.setTextContent(educational.getLanguage( ));
				educationalElement.appendChild( language );
			}

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return educationalElement;
	}
}
