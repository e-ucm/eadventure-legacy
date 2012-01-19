/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;

public class LOMESRightsDOMWriter extends LOMESSimpleDataWriter {

    /**
     * Private constructor.
     */
    private LOMESRightsDOMWriter( ) {

    }

    public static Node buildDOM( LOMESRights rights ) {

        Element rightsElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root
            rightsElement = doc.createElement( "lomes:rights" );
            rightsElement.setAttribute( "vocElement", "rigths" );

            // Create cost node
            rightsElement.appendChild( buildVocabularyNode( doc, "lomes:cost", rights.getCost( ) ) );

            // Create copyright and other restrictions
            rightsElement.appendChild( buildVocabularyNode( doc, "lomes:copyrightAndOtherRestrictions", rights.getCopyrightandotherrestrictions( ) ) );

            // Create description node
            Element description = doc.createElement( "lomes:description" );
            description.appendChild( buildLangStringNode( doc, rights.getDescription( ) ) );
            rightsElement.appendChild( description );

            // create access node
            Element access = doc.createElement( "lomes:access" );
            access.setAttribute( "vocElement", "access" );

            // Create access type and access description
            access.appendChild( buildVocabularyNode( doc, "lomes:accessType", rights.getAccessType( ) ) );
            Element accessDescription = doc.createElement( "lomes:description" );
            accessDescription.appendChild( buildLangStringNode( doc, rights.getAccessDescription( ) ) );
            access.appendChild( accessDescription );
            rightsElement.appendChild( access );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return rightsElement;
    }

}
