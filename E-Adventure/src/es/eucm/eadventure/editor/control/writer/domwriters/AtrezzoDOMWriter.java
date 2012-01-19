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
package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class AtrezzoDOMWriter {

    /**
     * Private constructor.
     */
    private AtrezzoDOMWriter( ) {

    }

    public static Node buildDOM( Atrezzo atrezzo ) {

        Element atrezzoElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            atrezzoElement = doc.createElement( "atrezzoobject" );
            atrezzoElement.setAttribute( "id", atrezzo.getId( ) );

            // Append the documentation (if avalaible)
            if( atrezzo.getDocumentation( ) != null ) {
                Node atrezzoDocumentationNode = doc.createElement( "documentation" );
                atrezzoDocumentationNode.appendChild( doc.createTextNode( atrezzo.getDocumentation( ) ) );
                atrezzoElement.appendChild( atrezzoDocumentationNode );
            }

            // Append the resources
            for( Resources resources : atrezzo.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_ITEM );
                doc.adoptNode( resourcesNode );
                atrezzoElement.appendChild( resourcesNode );
            }

            // atrezzo only have name
                // Create the description
                Node descriptionNode = doc.createElement( "description" );
    
                // Create and append the name, brief description and detailed description and its soundPaths
                Element nameNode = doc.createElement( "name" );
                if (atrezzo.getDescription( 0 ).getNameSoundPath( )!=null && !atrezzo.getDescription( 0 ).getNameSoundPath( ).equals( "" )){
                    nameNode.setAttribute( "soundPath", atrezzo.getDescription( 0 ).getNameSoundPath( ) );
                }
                nameNode.appendChild( doc.createTextNode( atrezzo.getDescription( 0 ).getName( ) ) );
                descriptionNode.appendChild( nameNode );
    
                Element briefNode = doc.createElement( "brief" );
               /* if (description.getDescriptionSoundPath( )!=null && !description.getDescriptionSoundPath( ).equals( "" )){
                    briefNode.setAttribute( "soundPath", description.getDescriptionSoundPath( ) );
                }
                briefNode.appendChild( doc.createTextNode( description.getDescription( ) ) );*/
                briefNode.appendChild( doc.createTextNode( "" ) );
                descriptionNode.appendChild( briefNode );
    
                Element detailedNode = doc.createElement( "detailed" );
               /* if (description.getDetailedDescriptionSoundPath( )!=null && !description.getDetailedDescriptionSoundPath( ).equals( "" )){
                    detailedNode.setAttribute( "soundPath", description.getDetailedDescriptionSoundPath( ) );
                }
                detailedNode.appendChild( doc.createTextNode( description.getDetailedDescription( ) ) );*/
                detailedNode.appendChild( doc.createTextNode( "" ) );
                descriptionNode.appendChild( detailedNode );
    
                // Append the description
                atrezzoElement.appendChild( descriptionNode );
            

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return atrezzoElement;
    }

}
