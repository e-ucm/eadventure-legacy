/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
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
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class PlayerDOMWriter {

    /**
     * Private constructor.
     */
    private PlayerDOMWriter( ) {

    }

    public static Node buildDOM( Player player ) {

        Node playerNode = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            playerNode = doc.createElement( "player" );

            // Append the documentation (if avalaible)
            if( player.getDocumentation( ) != null ) {
                Node playerDocumentationNode = doc.createElement( "documentation" );
                playerDocumentationNode.appendChild( doc.createTextNode( player.getDocumentation( ) ) );
                playerNode.appendChild( playerDocumentationNode );
            }

            // Append the resources
            for( Resources resources : player.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CHARACTER );
                doc.adoptNode( resourcesNode );
                playerNode.appendChild( resourcesNode );
            }

            // Create the textcolor
            Element textColorNode = doc.createElement( "textcolor" );
            textColorNode.setAttribute( "showsSpeechBubble", ( player.getShowsSpeechBubbles( ) ? "yes" : "no" ) );
            textColorNode.setAttribute( "bubbleBkgColor", player.getBubbleBkgColor( ) );
            textColorNode.setAttribute( "bubbleBorderColor", player.getBubbleBorderColor( ) );

            // Create and append the frontcolor
            Element frontColorElement = doc.createElement( "frontcolor" );
            frontColorElement.setAttribute( "color", player.getTextFrontColor( ) );
            textColorNode.appendChild( frontColorElement );

            // Create and append the bordercolor
            Element borderColoElement = doc.createElement( "bordercolor" );
            borderColoElement.setAttribute( "color", player.getTextBorderColor( ) );
            textColorNode.appendChild( borderColoElement );

            // Append the textcolor
            playerNode.appendChild( textColorNode );

            for (Description description: player.getDescriptions( )){
            
            // Create the description
            Node descriptionNode = doc.createElement( "description" );
            
         // Append the conditions (if available)
            if( description.getConditions( )!=null && !description.getConditions( ).isEmpty( ) ) {
                Node conditionsNode = ConditionsDOMWriter.buildDOM( description.getConditions( ) );
                doc.adoptNode( conditionsNode );
                descriptionNode.appendChild( conditionsNode );
            }

            // Create and append the name, brief description and detailed description
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
            playerNode.appendChild( descriptionNode );
            
            }

            // Create the voice tag
            Element voiceNode = doc.createElement( "voice" );
            // Create and append the voice name and if is alwaysSynthesizer
            voiceNode.setAttribute( "name", player.getVoice( ) );
            if( player.isAlwaysSynthesizer( ) )
                voiceNode.setAttribute( "synthesizeAlways", "yes" );
            else
                voiceNode.setAttribute( "synthesizeAlways", "no" );

            // Append the voice tag

            playerNode.appendChild( voiceNode );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return playerNode;
    }
}
