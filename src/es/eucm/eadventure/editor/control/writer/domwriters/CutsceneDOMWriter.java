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
package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.GeneralScene;

public class CutsceneDOMWriter {

    /**
     * Private constructor.
     */
    private CutsceneDOMWriter( ) {

    }

    public static Node buildDOM( Cutscene cutscene, boolean initialScene ) {

        Element cutsceneElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            if( cutscene.getType( ) == GeneralScene.SLIDESCENE )
                cutsceneElement = doc.createElement( "slidescene" );
            else if( cutscene.getType( ) == GeneralScene.VIDEOSCENE )
                cutsceneElement = doc.createElement( "videoscene" );

            // Set the attributes
            cutsceneElement.setAttribute( "id", cutscene.getId( ) );
            if( initialScene )
                cutsceneElement.setAttribute( "start", "yes" );
            else
                cutsceneElement.setAttribute( "start", "no" );

            if( cutscene.getNext( ) == Cutscene.NEWSCENE ) {
                cutsceneElement.setAttribute( "idTarget", cutscene.getTargetId( ) );

                cutsceneElement.setAttribute( "destinyX", String.valueOf( cutscene.getPositionX( ) ) );
                cutsceneElement.setAttribute( "destinyY", String.valueOf( cutscene.getPositionY( ) ) );

                cutsceneElement.setAttribute( "transitionTime", String.valueOf( cutscene.getTransitionTime( ) ) );
                cutsceneElement.setAttribute( "transitionType", String.valueOf( cutscene.getTransitionType( ) ) );
            }

            if( cutscene.getNext( ) == Cutscene.GOBACK )
                cutsceneElement.setAttribute( "next", "go-back" );
            else if( cutscene.getNext( ) == Cutscene.ENDCHAPTER )
                cutsceneElement.setAttribute( "next", "end-chapter" );
            else if( cutscene.getNext( ) == Cutscene.NEWSCENE )
                cutsceneElement.setAttribute( "next", "new-scene" );

            // Append the documentation (if avalaible)
            if( cutscene.getDocumentation( ) != null ) {
                Node cutsceneDocumentationNode = doc.createElement( "documentation" );
                cutsceneDocumentationNode.appendChild( doc.createTextNode( cutscene.getDocumentation( ) ) );
                cutsceneElement.appendChild( cutsceneDocumentationNode );
            }

            if( !cutscene.getEffects( ).isEmpty( ) ) {
                Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, cutscene.getEffects( ) );
                doc.adoptNode( effectsNode );
                cutsceneElement.appendChild( effectsNode );
            }

            // Append the resources
            for( Resources resources : cutscene.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CUTSCENE );
                doc.adoptNode( resourcesNode );
                cutsceneElement.appendChild( resourcesNode );
            }

            // Append the name
            Node nameNode = doc.createElement( "name" );
            nameNode.appendChild( doc.createTextNode( cutscene.getName( ) ) );
            cutsceneElement.appendChild( nameNode );

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return cutsceneElement;
    }
}
