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
import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class ItemDOMWriter {

    /**
     * Private constructor.
     */
    private ItemDOMWriter( ) {

    }

    public static Node buildDOM( Item item ) {

        Element itemElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            itemElement = doc.createElement( "object" );
            itemElement.setAttribute( "id", item.getId( ) );
            itemElement.setAttribute( "returnsWhenDragged", (item.isReturnsWhenDragged( ) ? "yes" : "no" ));

            // Append the documentation (if avalaible)
            if( item.getDocumentation( ) != null ) {
                Node itemDocumentationNode = doc.createElement( "documentation" );
                itemDocumentationNode.appendChild( doc.createTextNode( item.getDocumentation( ) ) );
                itemElement.appendChild( itemDocumentationNode );
            }

            // Append the resources
            for( Resources resources : item.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_ITEM );
                doc.adoptNode( resourcesNode );
                itemElement.appendChild( resourcesNode );
            }

            for (Description description: item.getDescriptions( )){
            
                // Create the description
                Node descriptionNode = doc.createElement( "description" );
                
                // Append the conditions (if available)
                if( description.getConditions( )!=null && !description.getConditions( ).isEmpty( ) ) {
                    Node conditionsNode = ConditionsDOMWriter.buildDOM( description.getConditions( ) );
                    doc.adoptNode( conditionsNode );
                    descriptionNode.appendChild( conditionsNode );
                }
                
                // Create and append the name, brief description and detailed description and its soundPaths
                Element nameNode = doc.createElement( "name" );
    
                if (description.getNameSoundPath( )!=null && !description.getNameSoundPath( ).equals( "" )){
                    nameNode.setAttribute( "soundPath", description.getNameSoundPath( ) );
                }
                nameNode.appendChild( doc.createTextNode( description.getName( ) ) );
                descriptionNode.appendChild( nameNode );
    
                Element briefNode = doc.createElement( "brief" );
                if (description.getDescriptionSoundPath( )!=null && !description.getDescriptionSoundPath( ).equals( "" )){
                    briefNode.setAttribute( "soundPath", description.getDescriptionSoundPath( ) );
                }
                briefNode.appendChild( doc.createTextNode( description.getDescription( ) ) );
                descriptionNode.appendChild( briefNode );
    
                Element detailedNode = doc.createElement( "detailed" );
                if (description.getDetailedDescriptionSoundPath( )!=null && !description.getDetailedDescriptionSoundPath( ).equals( "" )){
                    detailedNode.setAttribute( "soundPath", description.getDetailedDescriptionSoundPath( ) );
                }
                detailedNode.appendChild( doc.createTextNode( description.getDetailedDescription( ) ) );
                descriptionNode.appendChild( detailedNode );
    
                // Append the description
                itemElement.appendChild( descriptionNode );
            
            }

            // Append the actions (if there is at least one)
            if( !item.getActions( ).isEmpty( ) ) {
                // Create the actions node
                Node actionsNode = ActionsDOMWriter.buildDOM( item.getActions( ) );
                doc.adoptNode( actionsNode );
                // Append the actions node
                itemElement.appendChild( actionsNode );
            }

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return itemElement;
    }
}
