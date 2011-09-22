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
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class CharacterDOMWriter {

    /**
     * Private constructor.
     */
    private CharacterDOMWriter( ) {

    }

    public static Node buildDOM( NPC character ) {

        Element characterElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            characterElement = doc.createElement( "character" );
            characterElement.setAttribute( "id", character.getId( ) );

            // Append the documentation (if avalaible)
            if( character.getDocumentation( ) != null ) {
                Node characterDocumentationNode = doc.createElement( "documentation" );
                characterDocumentationNode.appendChild( doc.createTextNode( character.getDocumentation( ) ) );
                characterElement.appendChild( characterDocumentationNode );
            }

            // Append the resources
            for( Resources resources : character.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CHARACTER );
                doc.adoptNode( resourcesNode );
                characterElement.appendChild( resourcesNode );
            }

            // Create the textcolor
            Element textColorNode = doc.createElement( "textcolor" );
            textColorNode.setAttribute( "showsSpeechBubble", ( character.getShowsSpeechBubbles( ) ? "yes" : "no" ) );
            textColorNode.setAttribute( "bubbleBkgColor", character.getBubbleBkgColor( ) );
            textColorNode.setAttribute( "bubbleBorderColor", character.getBubbleBorderColor( ) );

            // Create and append the frontcolor
            Element frontColorElement = doc.createElement( "frontcolor" );
            frontColorElement.setAttribute( "color", character.getTextFrontColor( ) );
            textColorNode.appendChild( frontColorElement );

            // Create and append the bordercolor
            Element borderColoElement = doc.createElement( "bordercolor" );
            borderColoElement.setAttribute( "color", character.getTextBorderColor( ) );
            textColorNode.appendChild( borderColoElement );

            // Append the textcolor
            characterElement.appendChild( textColorNode );

            // Create the description
            Node descriptionNode = doc.createElement( "description" );

            // Create and append the name, brief description and detailed description and its soundPaths
            Element nameNode = doc.createElement( "name" );
            if (character.getNameSoundPath( )!=null && !character.getNameSoundPath( ).equals( "" )){
                nameNode.setAttribute( "soundPath", character.getNameSoundPath( ) );
            }
            nameNode.appendChild( doc.createTextNode( character.getName( ) ) );
            descriptionNode.appendChild( nameNode );

            Element briefNode = doc.createElement( "brief" );
            if (character.getDescriptionSoundPath( )!=null && !character.getDescriptionSoundPath( ).equals( "" )){
                briefNode.setAttribute( "soundPath", character.getDescriptionSoundPath( ) );
            }
            briefNode.appendChild( doc.createTextNode( character.getDescription( ) ) );
            descriptionNode.appendChild( briefNode );

            Element detailedNode = doc.createElement( "detailed" );
            if (character.getDetailedDescriptionSoundPath( )!=null && !character.getDetailedDescriptionSoundPath( ).equals( "" )){
                detailedNode.setAttribute( "soundPath", character.getDetailedDescriptionSoundPath( ) );
            }
            detailedNode.appendChild( doc.createTextNode( character.getDetailedDescription( ) ) );
            descriptionNode.appendChild( detailedNode );

            // Append the description
            characterElement.appendChild( descriptionNode );

            // Create the voice tag
            Element voiceNode = doc.createElement( "voice" );
            // Create and append the voice name and if is alwaysSynthesizer
            voiceNode.setAttribute( "name", character.getVoice( ) );
            if( character.isAlwaysSynthesizer( ) )
                voiceNode.setAttribute( "synthesizeAlways", "yes" );
            else
                voiceNode.setAttribute( "synthesizeAlways", "no" );

            // Append the voice tag

            characterElement.appendChild( voiceNode );
            if( character.getActionsCount( ) > 0 ) {
                Node actionsNode = ActionsDOMWriter.buildDOM( character.getActions( ) );
                doc.adoptNode( actionsNode );
                characterElement.appendChild( actionsNode );
            }
        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return characterElement;
    }
}
