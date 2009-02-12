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
	private ActionsDOMWriter( ) {}

	/**
	 * Build a node from a list of actions
	 * 
	 * @param actions the list of actions
	 * @return the xml node with the list of actions
	 */
	public static Node buildDOM( List<Action> actions  ) {
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
						case Action.USE_WITH:
							actionElement = doc.createElement( "use-with" );
							actionElement.setAttribute( "idTarget", action.getIdTarget( ) );
							break;
						case Action.GIVE_TO:
							actionElement = doc.createElement( "give-to" );
							actionElement.setAttribute( "idTarget", action.getIdTarget( ) );
							break;
						case Action.CUSTOM:
							actionElement = doc.createElement( "custom" );
							actionElement.setAttribute("name", ((CustomAction) action).getName());
							for (Resources resources : ((CustomAction) action).getResources()) {
								Node resourcesNode = ResourcesDOMWriter.buildDOM(resources, ResourcesDOMWriter.RESOURCES_CUSTOM_ACTION);
								doc.adoptNode( resourcesNode );
								actionElement.appendChild(resourcesNode);
							}
							break;
						case Action.CUSTOM_INTERACT:
							actionElement = doc.createElement( "custom-interact" );
							actionElement.setAttribute("idTarget", action.getIdTarget());
							actionElement.setAttribute("name", ((CustomAction) action).getName());
							for (Resources resources : ((CustomAction) action).getResources()){
								Node resourcesNode = ResourcesDOMWriter.buildDOM(resources, ResourcesDOMWriter.RESOURCES_CUSTOM_ACTION);
								doc.adoptNode( resourcesNode );
								actionElement.appendChild(resourcesNode);
							}
							break;							
					}
					actionElement.setAttribute("needsGoTo", (action.isNeedsGoTo() ? "yes" : "no"));
					actionElement.setAttribute("keepDistance", "" + action.getKeepDistance());

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

					// Append the action element
					actionsElement.appendChild( actionElement );
				}
			}

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return actionsElement;
	}
}
