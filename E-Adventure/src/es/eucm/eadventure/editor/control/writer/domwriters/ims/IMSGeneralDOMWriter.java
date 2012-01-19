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
package es.eucm.eadventure.editor.control.writer.domwriters.ims;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;

public class IMSGeneralDOMWriter extends IMSSimpleDataWriter {

    /**
     * Private constructor.
     */
    private IMSGeneralDOMWriter( ) {

    }

    public static Node buildDOM( IMSGeneral general ) {

        Element generalElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            generalElement = doc.createElement( "general" );

            //Create the title node
            Element title = doc.createElement( "title" );
            title.appendChild( buildLangStringNode( doc, general.getTitle( ) ) );
            generalElement.appendChild( title );

            //Create calatogentry node
            Element catalogentry = doc.createElement( "catalogentry" );
            // create and add catalog
            Element catalog = doc.createElement( "catalog" );
            catalog.setTextContent( general.getCatalog( ) );
            catalogentry.appendChild( catalog );
            // add entry node
            Element entry = doc.createElement( "entry" );
            entry.appendChild( buildLangStringNode( doc, general.getEntry( ) ) );
            catalogentry.appendChild( entry );
            generalElement.appendChild( catalogentry );

            //Create the language node
            Element language = doc.createElement( "language" );
            if( isStringSet( general.getLanguage( ) ) ) {

                language.setTextContent( general.getLanguage( ) );

            }
            else {
                language.setTextContent( "" );
            }
            generalElement.appendChild( language );

            //Create the description node
            Element description = doc.createElement( "description" );
            description.appendChild( buildLangStringNode( doc, general.getDescription( ) ) );
            generalElement.appendChild( description );

            //Create the keyword node
            Element keyword = doc.createElement( "keyword" );
            keyword.appendChild( buildLangStringNode( doc, general.getKeyword( ) ) );
            generalElement.appendChild( keyword );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return generalElement;
    }
}
