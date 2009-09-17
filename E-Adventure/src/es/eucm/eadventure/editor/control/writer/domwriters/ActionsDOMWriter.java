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
