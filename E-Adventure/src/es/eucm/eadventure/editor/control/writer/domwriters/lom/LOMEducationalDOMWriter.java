/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.lom.LOMEducational;

public class LOMEducationalDOMWriter extends LOMSimpleDataWriter {

    /**
     * Private constructor.
     */
    private LOMEducationalDOMWriter( ) {

    }

    public static Node buildDOM( LOMEducational educational, boolean scorm ) {

        Element educationalElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            educationalElement = doc.createElement( "imsmd:educational" );

            //Create the interactivity type node
            Node intType = buildVocabularyNode( doc, scorm ? "interactivityType" : "interactivitytype", educational.getInteractivityType( ), scorm );
            educationalElement.appendChild( intType );

            //Create the learning resource type
            Node lrType = buildVocabularyNode( doc, scorm ? "learningResourceType" : "learningresourcetype", educational.getLearningResourceType( ), scorm );
            educationalElement.appendChild( lrType );

            //Create the interactivity level node
            Node intLevel = buildVocabularyNode( doc, scorm ? "interactivityLevel" : "interactivitylevel", educational.getInteractivityLevel( ), scorm );
            educationalElement.appendChild( intLevel );

            //Create the semantic density node
            Node semanticDensity = buildVocabularyNode( doc, scorm ? "semanticDensity" : "semanticdensity", educational.getSemanticDensity( ), scorm );
            educationalElement.appendChild( semanticDensity );

            //Create the intended end user role node
            Node ieuRole = buildVocabularyNode( doc, scorm ? "intendedEndUserRole" : "intendedenduserrole", educational.getIntendedEndUserRole( ), scorm );
            educationalElement.appendChild( ieuRole );

            //Create the context node
            Node context = buildVocabularyNode( doc, "context", educational.getContext( ), scorm );
            educationalElement.appendChild( context );

            //Create the typical age range node
            if( isStringSet( educational.getTypicalAgeRange( ) ) ) {
                Element taRange = doc.createElement( scorm ? "typicalAgeRange" : "imsmd:typicalagerange" );
                taRange.appendChild( buildLangStringNode( doc, educational.getTypicalAgeRange( ), scorm ) );
                educationalElement.appendChild( taRange );
            }

            //Create the difficulty node
            Node difficulty = buildVocabularyNode( doc, "difficulty", educational.getDifficulty( ), scorm );
            educationalElement.appendChild( difficulty );

            //Create the typical learning time node
            Node tlTime = doc.createElement( scorm ? "imsmd:typicalLearningTime" : "imsmd:typicallearningtime" );
            Node dateTime = doc.createElement( scorm ? "imsmd:duration" : "datetime" );
            dateTime.setTextContent( educational.getTypicalLearningTime( ) );
            tlTime.appendChild( dateTime );
            educationalElement.appendChild( tlTime );

            //Create the description node
            if( isStringSet( educational.getDescription( ) ) ) {
                Element description = doc.createElement( "imsmd:description" );
                description.appendChild( buildLangStringNode( doc, educational.getDescription( ), scorm ) );
                educationalElement.appendChild( description );
            }

            //Create the language node
            if( isStringSet( educational.getLanguage( ) ) ) {
                Element language = doc.createElement( "imsmd:language" );
                language.setTextContent( educational.getLanguage( ) );
                educationalElement.appendChild( language );
            }

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return educationalElement;
    }
}
