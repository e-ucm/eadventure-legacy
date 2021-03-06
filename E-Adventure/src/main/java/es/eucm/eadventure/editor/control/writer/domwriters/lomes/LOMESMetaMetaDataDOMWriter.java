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
import es.eucm.eadventure.editor.data.meta.lomes.LOMESMetaMetaData;

public class LOMESMetaMetaDataDOMWriter extends LOMESSimpleDataWriter {

    /**
     * Private constructor.
     */
    private LOMESMetaMetaDataDOMWriter( ) {

    }

    public static Node buildDOM( LOMESMetaMetaData metametadata ) {

        Element metaMetaDataElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create root node
            metaMetaDataElement = doc.createElement( "lomes:metaMetadata" );
            metaMetaDataElement.setAttribute( "vocElement", "metaMetadata" );

            //Create identifier node
            //Create identifier node for each identifier
            for( int i = 0; i < metametadata.getNIdentifier( ); i++ ) {
                metaMetaDataElement.appendChild( LOMESDOMWriter.createOneIdentifier( doc, metametadata.getIdentifier( i ).getCatalog( ), metametadata.getIdentifier( i ).getEntry( ) ) );
            }

            // contribution node
            for( int i = 0; i < metametadata.getContribute( ).getSize( ); i++ ) {
                Element contribution = doc.createElement( "lomes:contribute" );
                //Add role
                contribution.appendChild( buildVocabularyNode( doc, "lomes:role", ( (LOMESLifeCycleContribute) metametadata.getContribute( ).get( i ) ).getRole( ) ) );
                //take all entities for that contribution
                ArrayList<String> ent = ( (LOMESLifeCycleContribute) metametadata.getContribute( ).get( i ) ).getEntity( );
                for( int j = 0; j < ent.size( ); j++ ) {
                    Element entity = doc.createElement( "lomes:entity" );
                    entity.setTextContent( ent.get( j ) );
                    contribution.appendChild( entity );
                }
                //Add date
                Element date = doc.createElement( "lomes:date" );
                date.setAttribute( "vocElement", "date" );
                Element dateTime = doc.createElement( "lomes:dateTime" );
                dateTime.setAttribute( "vocElement", "dateTime" );
                dateTime.setTextContent( ( (LOMESLifeCycleContribute) metametadata.getContribute( ).get( i ) ).getDate( ).getDateTime( ) );
                date.appendChild( dateTime );
                Element description = doc.createElement( "lomes:description" );
                description.appendChild( buildLangStringNode( doc, ( (LOMESLifeCycleContribute) metametadata.getContribute( ).get( i ) ).getDate( ).getDescription( ) ) );
                date.appendChild( description );
                contribution.appendChild( date );

                metaMetaDataElement.appendChild( contribution );
            }

            // metadata scheme
            Element metadatascheme = doc.createElement( "lomes:metadataSchema" );
            metadatascheme.setTextContent( metametadata.getMetadatascheme( ) );
            metaMetaDataElement.appendChild( metadatascheme );

            //Create the language node
            Element language = doc.createElement( "lomes:language" );
            if( isStringSet( metametadata.getLanguage( ) ) ) {

                language.setTextContent( metametadata.getLanguage( ) );

            }
            else {
                language.setTextContent( "" );
            }
            metaMetaDataElement.appendChild( language );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return metaMetaDataElement;
    }

}
