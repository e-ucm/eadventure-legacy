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

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class ActionsDOMWriter {

    /**
     * Private constructor.
     */
    private ActionsDOMWriter( ) {

    }

    /**
     * Build a node from a list of actions
     * 
     * @param actions
     *            the list of actions
     * @return the xml node with the list of actions
     */
    public static Node buildDOM( List<Action> actions ) {

        Element actionsElement = null;

        try {
            // Create the necessary elements to create the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root node
            actionsElement = doc.createElement( "actions" );

            // Append the actions (if there is at least one)
            if( !actions.isEmpty( ) ) {
                // For every action
                for( Action action : actions ) {
                    Element actionElement = null;

                    // Create the element
                    switch( action.getType( ) ) {
                        case Action.EXAMINE:
                            actionElement = doc.createElement( "examine" );
                            break;
                        case Action.GRAB:
                            actionElement = doc.createElement( "grab" );
                            break;
                        case Action.USE:
                            actionElement = doc.createElement( "use" );
                            break;
                        case Action.TALK_TO:
                            actionElement = doc.createElement( "talk-to" );
                            break;
                        case Action.USE_WITH:
                            actionElement = doc.createElement( "use-with" );
                            actionElement.setAttribute( "idTarget", action.getTargetId( ) );
                            break;
                        case Action.GIVE_TO:
                            actionElement = doc.createElement( "give-to" );
                            actionElement.setAttribute( "idTarget", action.getTargetId( ) );
                            break;
                        case Action.DRAG_TO:
                            actionElement = doc.createElement( "drag-to" );
                            actionElement.setAttribute( "idTarget", action.getTargetId( ) );
                            break;
                        case Action.CUSTOM:
                            actionElement = doc.createElement( "custom" );
                            actionElement.setAttribute( "name", ( (CustomAction) action ).getName( ) );
                            for( Resources resources : ( (CustomAction) action ).getResources( ) ) {
                                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CUSTOM_ACTION );
                                doc.adoptNode( resourcesNode );
                                actionElement.appendChild( resourcesNode );
                            }
                            break;
                        case Action.CUSTOM_INTERACT:
                            actionElement = doc.createElement( "custom-interact" );
                            actionElement.setAttribute( "idTarget", action.getTargetId( ) );
                            actionElement.setAttribute( "name", ( (CustomAction) action ).getName( ) );
                            for( Resources resources : ( (CustomAction) action ).getResources( ) ) {
                                Node resourcesNode = ResourcesDOMWriter.buildDOM( resources, ResourcesDOMWriter.RESOURCES_CUSTOM_ACTION );
                                doc.adoptNode( resourcesNode );
                                actionElement.appendChild( resourcesNode );
                            }
                            break;
                    }

                    actionElement.setAttribute( "needsGoTo", ( action.isNeedsGoTo( ) ? "yes" : "no" ) );
                    actionElement.setAttribute( "keepDistance", "" + action.getKeepDistance( ).intValue( ) );
                    actionElement.setAttribute( "not-effects", action.isActivatedNotEffects( ) ? "yes" : "no" );

                    // Append the documentation (if avalaible)
                    if( action.getDocumentation( ) != null ) {
                        Node actionDocumentationNode = doc.createElement( "documentation" );
                        actionDocumentationNode.appendChild( doc.createTextNode( action.getDocumentation( ) ) );
                        actionElement.appendChild( actionDocumentationNode );
                    }

                    // Append the conditions (if avalaible)
                    if( !action.getConditions( ).isEmpty( ) ) {
                        Node conditionsNode = ConditionsDOMWriter.buildDOM( action.getConditions( ) );
                        doc.adoptNode( conditionsNode );
                        actionElement.appendChild( conditionsNode );
                    }

                    // Append the effects (if avalaible)
                    if( !action.getEffects( ).isEmpty( ) ) {
                        Node effectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, action.getEffects( ) );
                        doc.adoptNode( effectsNode );
                        actionElement.appendChild( effectsNode );
                    }
                    // Append the not effects (if avalaible)
                    if( action.getNotEffects( ) != null && !action.getNotEffects( ).isEmpty( ) ) {
                        Node notEffectsNode = EffectsDOMWriter.buildDOM( EffectsDOMWriter.NOT_EFFECTS, action.getNotEffects( ) );
                        doc.adoptNode( notEffectsNode );
                        actionElement.appendChild( notEffectsNode );
                    }

                    // Append the action element
                    actionsElement.appendChild( actionElement );
                }
            }

        }
        catch( ParserConfigurationException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return actionsElement;
    }
}
