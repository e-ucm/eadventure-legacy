/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
