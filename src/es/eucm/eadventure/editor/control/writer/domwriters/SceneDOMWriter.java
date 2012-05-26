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
package es.eucm.eadventure.editor.control.writer.domwriters;

import java.awt.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.auxiliar.TrajectoryFixer;
import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

public class SceneDOMWriter {

    /**
     * Private constructor.
     */
    private SceneDOMWriter( ) {

    }

    public static Node buildDOM( Scene scene, boolean initialScene ) {

        Element sceneElement = null;
        
        if (scene!=null){
            TrajectoryFixer.fixTrajectory( scene );
        }

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            sceneElement = doc.createElement( "scene" );
            sceneElement.setAttribute( "id", scene.getId( ) );
            if( initialScene )
                sceneElement.setAttribute( "start", "yes" );
            else
                sceneElement.setAttribute( "start", "no" );

            sceneElement.setAttribute( "playerLayer", Integer.toString( scene.getPlayerLayer( ) ) );
            sceneElement.setAttribute( "playerScale", Float.toString( scene.getPlayerScale( ) ) );

            // Append the documentation (if avalaible)
            if( scene.getDocumentation( ) != null ) {
                Node sceneDocumentationNode = doc.createElement( "documentation" );
                sceneDocumentationNode.appendChild( doc.createTextNode( scene.getDocumentation( ) ) );
                sceneElement.appendChild( sceneDocumentationNode );
            }

            // Append the resources
            for( Resources resources : scene.getResources( ) ) {
                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_SCENE );
                doc.adoptNode( resourcesNode );
                sceneElement.appendChild( resourcesNode );
            }

            // Append the name
            Node nameNode = doc.createElement( "name" );
            nameNode.appendChild( doc.createTextNode( scene.getName( ) ) );
            sceneElement.appendChild( nameNode );

            // Append the default inital position (if avalaible)
            if( scene.hasDefaultPosition( ) ) {
                Element initialPositionElement = doc.createElement( "default-initial-position" );
                initialPositionElement.setAttribute( "x", String.valueOf( scene.getPositionX( ) ) );
                initialPositionElement.setAttribute( "y", String.valueOf( scene.getPositionY( ) ) );
                sceneElement.appendChild( initialPositionElement );
            }

            // Append the exits (if there is at least one)
            if( !scene.getExits( ).isEmpty( ) ) {
                Node exitsElement = doc.createElement( "exits" );

                // Append every single exit
                for( Exit exit : scene.getExits( ) ) {
                    // Create the exit element
                    Element exitElement = doc.createElement( "exit" );
                    exitElement.setAttribute( "rectangular", ( exit.isRectangular( ) ? "yes" : "no" ) );
                    exitElement.setAttribute( "x", String.valueOf( exit.getX( ) ) );
                    exitElement.setAttribute( "y", String.valueOf( exit.getY( ) ) );
                    exitElement.setAttribute( "width", String.valueOf( exit.getWidth( ) ) );
                    exitElement.setAttribute( "height", String.valueOf( exit.getHeight( ) ) );
                    exitElement.setAttribute( "hasInfluenceArea", ( exit.getInfluenceArea( ).isExists( ) ? "yes" : "no" ) );
                    exitElement.setAttribute( "idTarget", exit.getNextSceneId( ) );
                    exitElement.setAttribute( "destinyY", String.valueOf( exit.getDestinyY( ) ) );
                    exitElement.setAttribute( "destinyX", String.valueOf( exit.getDestinyX( ) ) );
                    exitElement.setAttribute( "transitionType", String.valueOf( exit.getTransitionType( ) ) );
                    exitElement.setAttribute( "transitionTime", String.valueOf( exit.getTransitionTime( ) ) );
                    exitElement.setAttribute( "not-effects", ( exit.isHasNotEffects( ) ? "yes" : "no" ) );

                    if( exit.getInfluenceArea( ).isExists( ) ) {
                        exitElement.setAttribute( "influenceX", String.valueOf( exit.getInfluenceArea( ).getX( ) ) );
                        exitElement.setAttribute( "influenceY", String.valueOf( exit.getInfluenceArea( ).getY( ) ) );
                        exitElement.setAttribute( "influenceWidth", String.valueOf( exit.getInfluenceArea( ).getWidth( ) ) );
                        exitElement.setAttribute( "influenceHeight", String.valueOf( exit.getInfluenceArea( ).getHeight( ) ) );
                    }

                    // Append the documentation (if avalaible)
                    if( exit.getDocumentation( ) != null ) {
                        Node exitDocumentationNode = doc.createElement( "documentation" );
                        exitDocumentationNode.appendChild( doc.createTextNode( exit.getDocumentation( ) ) );
                        exitElement.appendChild( exitDocumentationNode );
                    }

                    //Append the default exit look (if available)
                    ExitLook defaultLook = exit.getDefaultExitLook( );
                    if( defaultLook != null ) {
                        Element exitLook = doc.createElement( "exit-look" );
                        if( defaultLook.getExitText( ) != null )
                            exitLook.setAttribute( "text", defaultLook.getExitText( ) );
                        if( defaultLook.getCursorPath( ) != null )
                            exitLook.setAttribute( "cursor-path", defaultLook.getCursorPath( ) );
                        if( defaultLook.getSoundPath( ) != null )
                            exitLook.setAttribute( "sound-path", defaultLook.getSoundPath( ) );

                        if( defaultLook.getExitText( ) != null || defaultLook.getCursorPath( ) != null )
                            exitElement.appendChild( exitLook );
                    }

                    // Append the next-scene structures
                    for( NextScene nextScene : exit.getNextScenes( ) ) {
                        // Create the next-scene element
                        Element nextSceneElement = doc.createElement( "next-scene" );
                        nextSceneElement.setAttribute( "idTarget", nextScene.getTargetId( ) );

                        // Append the destination position (if avalaible)
                        if( nextScene.hasPlayerPosition( ) ) {
                            nextSceneElement.setAttribute( "x", String.valueOf( nextScene.getPositionX( ) ) );
                            nextSceneElement.setAttribute( "y", String.valueOf( nextScene.getPositionY( ) ) );
                        }

                        nextSceneElement.setAttribute( "transitionTime", String.valueOf( nextScene.getTransitionTime( ) ) );
                        nextSceneElement.setAttribute( "transitionType", String.valueOf( nextScene.getTransitionType( ) ) );

                        // Append the conditions (if avalaible)
                        if( !nextScene.getConditions( ).isEmpty( ) ) {
                            Node conditionsNode = ConditionsDOMWriter.buildDOM( nextScene.getConditions( ) );
                            doc.adoptNode( conditionsNode );
                            nextSceneElement.appendChild( conditionsNode );
                        }

                        //Append the default exit look (if available)
                        ExitLook look = nextScene.getExitLook( );
                        if( look != null ) {
                            Element exitLook = doc.createElement( "exit-look" );
                            if( look.getExitText( ) != null )
                                exitLook.setAttribute( "text", look.getExitText( ) );
                            if( look.getCursorPath( ) != null )
                                exitLook.setAttribute( "cursor-path", look.getCursorPath( ) );
                            if( look.getSoundPath( ) != null )
                                exitLook.setAttribute( "sound-path", look.getSoundPath( ) );
                            if( look.getExitText( ) != null || look.getCursorPath( ) != null )
                                nextSceneElement.appendChild( exitLook );
                        }

                        // Append the effects (if avalaible)
                        if( !nextScene.getEffects( ).isEmpty( ) ) {
                            Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, nextScene.getEffects( ) );
                            doc.adoptNode( effectsNode );
                            nextSceneElement.appendChild( effectsNode );
                        }

                        // Append the post-effects (if avalaible)
                        if( !nextScene.getPostEffects( ).isEmpty( ) ) {
                            Node postEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.POST_EFFECTS, nextScene.getPostEffects( ) );
                            doc.adoptNode( postEffectsNode );
                            nextSceneElement.appendChild( postEffectsNode );
                        }

                        // Append the next scene
                        exitElement.appendChild( nextSceneElement );
                    }

                    if( !exit.isRectangular( ) ) {
                        for( Point point : exit.getPoints( ) ) {
                            Element pointNode = doc.createElement( "point" );
                            pointNode.setAttribute( "x", String.valueOf( (int) point.getX( ) ) );
                            pointNode.setAttribute( "y", String.valueOf( (int) point.getY( ) ) );
                            exitElement.appendChild( pointNode );
                        }
                    }

                    if( exit.getConditions( ) != null && !exit.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( exit.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        exitElement.appendChild( conditionsNode );
                    }

                    if( exit.getEffects( ) != null && !exit.getEffects( ).isEmpty( ) ) {
                        Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, exit.getEffects( ) );
                        doc.adoptNode( effectsNode );
                        exitElement.appendChild( effectsNode );
                    }

                    if( exit.getPostEffects( ) != null && !exit.getPostEffects( ).isEmpty( ) ) {
                        Node postEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.POST_EFFECTS, exit.getPostEffects( ) );
                        doc.adoptNode( postEffectsNode );
                        exitElement.appendChild( postEffectsNode );
                    }

                    if( exit.getNotEffects( ) != null && !exit.getNotEffects( ).isEmpty( ) ) {
                        Node notEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.NOT_EFFECTS, exit.getNotEffects( ) );
                        doc.adoptNode( notEffectsNode );
                        exitElement.appendChild( notEffectsNode );
                    }

                    // Append the exit
                    exitsElement.appendChild( exitElement );
                }
                // Append the list of exits
                sceneElement.appendChild( exitsElement );
            }

            // Add the item references (if there is at least one)
            if( !scene.getItemReferences( ).isEmpty( ) ) {
                Node itemsNode = doc.createElement( "objects" );

                // Append every single item reference
                for( ElementReference itemReference : scene.getItemReferences( ) ) {
                    // Create the item reference element
                    Element itemReferenceElement = doc.createElement( "object-ref" );
                    itemReferenceElement.setAttribute( "idTarget", itemReference.getTargetId( ) );
                    itemReferenceElement.setAttribute( "x", String.valueOf( itemReference.getX( ) ) );
                    itemReferenceElement.setAttribute( "y", String.valueOf( itemReference.getY( ) ) );
                    itemReferenceElement.setAttribute( "scale", String.valueOf( itemReference.getScale( ) ) );
                    if( itemReference.getLayer( ) != -1 )
                        itemReferenceElement.setAttribute( "layer", String.valueOf( itemReference.getLayer( ) ) );
                    if( itemReference.getInfluenceArea( ).isExists( ) ) {
                        itemReferenceElement.setAttribute( "hasInfluenceArea", "yes" );
                        InfluenceArea ia = itemReference.getInfluenceArea( );
                        itemReferenceElement.setAttribute( "influenceX", String.valueOf( ia.getX( ) ) );
                        itemReferenceElement.setAttribute( "influenceY", String.valueOf( ia.getY( ) ) );
                        itemReferenceElement.setAttribute( "influenceWidth", String.valueOf( ia.getWidth( ) ) );
                        itemReferenceElement.setAttribute( "influenceHeight", String.valueOf( ia.getHeight( ) ) );
                    }
                    else {
                        itemReferenceElement.setAttribute( "hasInfluenceArea", "no" );
                    }

                    // Append the documentation (if avalaible)
                    if( itemReference.getDocumentation( ) != null ) {
                        Node itemDocumentationNode = doc.createElement( "documentation" );
                        itemDocumentationNode.appendChild( doc.createTextNode( itemReference.getDocumentation( ) ) );
                        itemReferenceElement.appendChild( itemDocumentationNode );
                    }

                    // Append the conditions (if avalaible)
                    if( !itemReference.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( itemReference.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        itemReferenceElement.appendChild( conditionsNode );
                    }

                    // Append the exit
                    itemsNode.appendChild( itemReferenceElement );
                }
                // Append the list of exits
                sceneElement.appendChild( itemsNode );
            }

            // Add the character references (if there is at least one)
            if( !scene.getCharacterReferences( ).isEmpty( ) ) {
                Node charactersNode = doc.createElement( "characters" );

                // Append every single character reference
                for( ElementReference characterReference : scene.getCharacterReferences( ) ) {
                    // Create the character reference element
                    Element npcReferenceElement = doc.createElement( "character-ref" );
                    npcReferenceElement.setAttribute( "idTarget", characterReference.getTargetId( ) );
                    npcReferenceElement.setAttribute( "x", String.valueOf( characterReference.getX( ) ) );
                    npcReferenceElement.setAttribute( "y", String.valueOf( characterReference.getY( ) ) );
                    npcReferenceElement.setAttribute( "scale", String.valueOf( characterReference.getScale( ) ) );
                    if( characterReference.getLayer( ) != -1 )
                        npcReferenceElement.setAttribute( "layer", String.valueOf( characterReference.getLayer( ) ) );
                    if( characterReference.getInfluenceArea( ).isExists( ) ) {
                        npcReferenceElement.setAttribute( "hasInfluenceArea", "yes" );
                        InfluenceArea ia = characterReference.getInfluenceArea( );
                        npcReferenceElement.setAttribute( "influenceX", String.valueOf( ia.getX( ) ) );
                        npcReferenceElement.setAttribute( "influenceY", String.valueOf( ia.getY( ) ) );
                        npcReferenceElement.setAttribute( "influenceWidth", String.valueOf( ia.getWidth( ) ) );
                        npcReferenceElement.setAttribute( "influenceHeight", String.valueOf( ia.getHeight( ) ) );
                    }
                    else {
                        npcReferenceElement.setAttribute( "hasInfluenceArea", "no" );
                    }

                    // Append the documentation (if avalaible)
                    if( characterReference.getDocumentation( ) != null ) {
                        Node itemDocumentationNode = doc.createElement( "documentation" );
                        itemDocumentationNode.appendChild( doc.createTextNode( characterReference.getDocumentation( ) ) );
                        npcReferenceElement.appendChild( itemDocumentationNode );
                    }

                    // Append the conditions (if avalaible)
                    if( !characterReference.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( characterReference.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        npcReferenceElement.appendChild( conditionsNode );
                    }

                    // Append the exit
                    charactersNode.appendChild( npcReferenceElement );
                }
                // Append the list of exits
                sceneElement.appendChild( charactersNode );
            }

            // Append the exits (if there is at least one)
            if( !scene.getActiveAreas( ).isEmpty( ) ) {
                Node aasElement = doc.createElement( "active-areas" );

                // Append every single exit
                for( ActiveArea activeArea : scene.getActiveAreas( ) ) {
                    // Create the active area element
                    Element aaElement = doc.createElement( "active-area" );
                    if( activeArea.getId( ) != null )
                        aaElement.setAttribute( "id", activeArea.getId( ) );
                    aaElement.setAttribute( "rectangular", ( activeArea.isRectangular( ) ? "yes" : "no" ) );
                    aaElement.setAttribute( "x", String.valueOf( activeArea.getX( ) ) );
                    aaElement.setAttribute( "y", String.valueOf( activeArea.getY( ) ) );
                    aaElement.setAttribute( "width", String.valueOf( activeArea.getWidth( ) ) );
                    aaElement.setAttribute( "height", String.valueOf( activeArea.getHeight( ) ) );
                    if( activeArea.getInfluenceArea( ).isExists( ) ) {
                        aaElement.setAttribute( "hasInfluenceArea", "yes" );
                        InfluenceArea ia = activeArea.getInfluenceArea( );
                        aaElement.setAttribute( "influenceX", String.valueOf( ia.getX( ) ) );
                        aaElement.setAttribute( "influenceY", String.valueOf( ia.getY( ) ) );
                        aaElement.setAttribute( "influenceWidth", String.valueOf( ia.getWidth( ) ) );
                        aaElement.setAttribute( "influenceHeight", String.valueOf( ia.getHeight( ) ) );
                    }
                    else {
                        aaElement.setAttribute( "hasInfluenceArea", "no" );
                    }

                    // Append the documentation (if avalaible)
                    if( activeArea.getDocumentation( ) != null ) {
                        Node exitDocumentationNode = doc.createElement( "documentation" );
                        exitDocumentationNode.appendChild( doc.createTextNode( activeArea.getDocumentation( ) ) );
                        aaElement.appendChild( exitDocumentationNode );
                    }

                    // Append the conditions (if avalaible)
                    if( !activeArea.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( activeArea.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        aaElement.appendChild( conditionsNode );
                    }

                   
                    for (Description description: activeArea.getDescriptions( )){
                        // Create the description
                        Node descriptionNode = doc.createElement( "description" );
                        
                        // Append the conditions (if available)
                        if( description.getConditions( )!=null && !description.getConditions( ).isEmpty( ) ) {
                            Node conditionsNode = ConditionsDOMWriter.buildDOM( description.getConditions( ) );
                            doc.adoptNode( conditionsNode );
                            descriptionNode.appendChild( conditionsNode );
                        }

                     // Create and append the name, brief description and detailed description
                        Element aaNameNode = doc.createElement( "name" );
                        if (description.getNameSoundPath( )!=null && !description.getNameSoundPath( ).equals( "" )){
                            aaNameNode.setAttribute( "soundPath", description.getNameSoundPath( ) );
                        }
                        aaNameNode.appendChild( doc.createTextNode( description.getName( ) ) );
                        descriptionNode.appendChild( aaNameNode );
    
                        Element aaBriefNode = doc.createElement( "brief" );
                        if (description.getDescriptionSoundPath( )!=null && !description.getDescriptionSoundPath( ).equals( "" )){
                            aaBriefNode.setAttribute( "soundPath", description.getDescriptionSoundPath( ) );
                        }
                        aaBriefNode.appendChild( doc.createTextNode( description.getDescription( ) ) );
                        descriptionNode.appendChild( aaBriefNode );
    
                        Element aaDetailedNode = doc.createElement( "detailed" );
                        if (description.getDetailedDescriptionSoundPath( )!=null && !description.getDetailedDescriptionSoundPath( ).equals( "" )){
                            aaDetailedNode.setAttribute( "soundPath", description.getDetailedDescriptionSoundPath( ) );
                        }
                        aaDetailedNode.appendChild( doc.createTextNode( description.getDetailedDescription( ) ) );
                        descriptionNode.appendChild( aaDetailedNode );
    
                        // Append the description
                        aaElement.appendChild( descriptionNode );
                    }

                    // Append the actions (if there is at least one)
                    if( !activeArea.getActions( ).isEmpty( ) ) {
                        Node actionsNode = ActionsDOMWriter.buildDOM( activeArea.getActions( ) );
                        doc.adoptNode( actionsNode );

                        // Append the actions node
                        aaElement.appendChild( actionsNode );
                    }

                    if( !activeArea.isRectangular( ) ) {
                        for( Point point : activeArea.getPoints( ) ) {
                            Element pointNode = doc.createElement( "point" );
                            pointNode.setAttribute( "x", String.valueOf( (int) point.getX( ) ) );
                            pointNode.setAttribute( "y", String.valueOf( (int) point.getY( ) ) );
                            aaElement.appendChild( pointNode );
                        }
                    }

                    // Append the exit
                    aasElement.appendChild( aaElement );
                }
                // Append the list of exits
                sceneElement.appendChild( aasElement );
            }

            // Append the barriers (if there is at least one)
            if( !scene.getBarriers( ).isEmpty( ) ) {
                Node barriersElement = doc.createElement( "barriers" );

                // Append every single barrier
                for( Barrier barrier : scene.getBarriers( ) ) {
                    // Create the active area element
                    Element barrierElement = doc.createElement( "barrier" );
                    barrierElement.setAttribute( "x", String.valueOf( barrier.getX( ) ) );
                    barrierElement.setAttribute( "y", String.valueOf( barrier.getY( ) ) );
                    barrierElement.setAttribute( "width", String.valueOf( barrier.getWidth( ) ) );
                    barrierElement.setAttribute( "height", String.valueOf( barrier.getHeight( ) ) );

                    // Append the documentation (if avalaible)
                    if( barrier.getDocumentation( ) != null ) {
                        Node exitDocumentationNode = doc.createElement( "documentation" );
                        exitDocumentationNode.appendChild( doc.createTextNode( barrier.getDocumentation( ) ) );
                        barrierElement.appendChild( exitDocumentationNode );
                    }

                    // Append the conditions (if avalaible)
                    if( !barrier.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( barrier.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        barrierElement.appendChild( conditionsNode );
                    }

                    // Append the barrier
                    barriersElement.appendChild( barrierElement );
                }
                // Append the list of exits
                sceneElement.appendChild( barriersElement );
            }

            // Add the atrezzo item references (if there is at least one)
            if( !scene.getAtrezzoReferences( ).isEmpty( ) ) {
                Node atrezzoNode = doc.createElement( "atrezzo" );

                // Append every single atrezzo reference
                for( ElementReference atrezzoReference : scene.getAtrezzoReferences( ) ) {
                    // Create the atrezzo reference element
                    Element atrezzoReferenceElement = doc.createElement( "atrezzo-ref" );
                    atrezzoReferenceElement.setAttribute( "idTarget", atrezzoReference.getTargetId( ) );
                    atrezzoReferenceElement.setAttribute( "x", String.valueOf( atrezzoReference.getX( ) ) );
                    atrezzoReferenceElement.setAttribute( "y", String.valueOf( atrezzoReference.getY( ) ) );
                    atrezzoReferenceElement.setAttribute( "scale", String.valueOf( atrezzoReference.getScale( ) ) );
                    if( atrezzoReference.getLayer( ) != -1 )
                        atrezzoReferenceElement.setAttribute( "layer", String.valueOf( atrezzoReference.getLayer( ) ) );

                    // Append the documentation (if avalaible)
                    if( atrezzoReference.getDocumentation( ) != null ) {
                        Node itemDocumentationNode = doc.createElement( "documentation" );
                        itemDocumentationNode.appendChild( doc.createTextNode( atrezzoReference.getDocumentation( ) ) );
                        atrezzoReferenceElement.appendChild( itemDocumentationNode );
                    }

                    // Append the conditions (if avalaible)
                    if( !atrezzoReference.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( atrezzoReference.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        atrezzoReferenceElement.appendChild( conditionsNode );
                    }

                    // Append the atrezzo reference
                    atrezzoNode.appendChild( atrezzoReferenceElement );
                }
                // Append the list of atrezzo references
                sceneElement.appendChild( atrezzoNode );
            }

            if( scene.getTrajectory( ) != null ) {
                Node trajectoryNode = TrajectoryDOMWriter.buildDOM( scene.getTrajectory( ) );
                doc.adoptNode( trajectoryNode );
                sceneElement.appendChild( trajectoryNode );
            }

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return sceneElement;
    }
}
